package ci.world.blocks.storage;

import arc.audio.Sound;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.struct.*;
import arc.util.Time;
import ci.content.CIUnits;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.UnitType;
import mindustry.world.*;
import mindustry.world.meta.BlockFlag;

public class EnemyCapsuleBase extends Block {

    public UnitType spawnUnitType = CIUnits.arrow;

    public int minSpawnUnits = 1;
    public int maxSpawnUnits = 4;
    public float minConstructTime = 5f * 60f;
    public float maxConstructTime = 17f * 60f;

    public int destroyDivisor = 2;

    public boolean spawnUnits = true;
    public boolean playSpawnSound = true;

    public float spawnSoundVolume = 1.0f;
    public Sound spawnSound = Sounds.respawn;

    public float spawnRadius = 2f;

    public float lightRadius = 30f;
    public float lightIntensity = 0.65f;

    public EnemyCapsuleBase(String name){
        super(name);
        this.solid = true;
        this.update = true;
        this.hasItems = false;
        this.destructible = true;
        this.flags = EnumSet.of(BlockFlag.core);
        this.replaceable = false;
        this.rebuildable = false;
        this.ambientSound = Sounds.extractLoop;
        this.ambientSoundVolume = 0.5f;
    }

    public class EnemyCapsuleBuild extends Building {

        public boolean hasLanded = false;
        public boolean spawnComplete = false;
        public boolean destroyed = false;
        public float spawnProgress = 0f;
        public int actualSpawnCount;
        public float constructTime;

        @Override
        public void created() {
            super.created();
            this.actualSpawnCount = Mathf.random(minSpawnUnits, maxSpawnUnits);
            this.constructTime = Mathf.random(minConstructTime, maxConstructTime);
        }

        @Override
        public void updateTile() {
            if (!hasLanded) {
                hasLanded = true;
                spawnProgress = 0f;
            }

            if(!spawnUnits){
                return;
            }

            if (!spawnComplete) {
                spawnProgress += Time.delta;

                if (spawnProgress >= constructTime) {
                    spawnUnits();
                    spawnComplete = true;
                    kill();
                }
            }
        }

        @Override
        public void draw() {
            Draw.rect(block.region, x, y);

            if(block.teamRegion.found()){
                if(block.teamRegions[team.id] == block.teamRegion) Draw.color(team.color);
                Draw.rect(block.teamRegions[team.id], x, y);
                Draw.color();
            }

            if (!spawnUnits){
                return;
            }

            if (hasLanded && !spawnComplete) {
                float progress = Math.min(spawnProgress / constructTime, 1f);
                Draw.draw(Layer.blockOver, () -> {
                    Drawf.construct(this.x, this.y, this.block.region, 0f, progress, 1f, Time.time + x + y);
                });
            }
        }

        @Override
        public void drawLight(){
            Drawf.light(x, y, lightRadius + 20f * block.size, Pal.accent, lightIntensity + Mathf.absin(20f, 0.1f));
        }

        @Override
        public boolean shouldAmbientSound(){
            return hasLanded && !spawnComplete;
        }

        protected void spawnUnits() {
            if (Vars.net.client() || spawnUnitType == null) return;

            int count = destroyed ? Math.max(1, actualSpawnCount / destroyDivisor) : actualSpawnCount;

            for (int i = 0; i < count; i++) {
                Unit unit = spawnUnitType.create(team);

                float offsetX = Mathf.range(spawnRadius);
                float offsetY = Mathf.range(spawnRadius);

                unit.set(x + offsetX, y + offsetY);
                unit.rotation = Mathf.random(360f);

                unit.add();
                Fx.spawn.at(unit.x, unit.y);
            }

            if (!destroyed && playSpawnSound) {
                spawnSound.at(x, y, spawnSoundVolume);
            }
        }

        @Override
        public void damage(float amount){
            destroyed = true;
            super.damage(amount);
        }

        @Override
        public void onDestroyed() {
            if (!spawnComplete) {
                spawnUnits();
            }
        }
    }
}