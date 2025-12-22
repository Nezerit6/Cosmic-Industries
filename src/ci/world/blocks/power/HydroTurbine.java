package ci.world.blocks.power;

import arc.math.*;
import arc.math.geom.*;
import arc.util.*;
import mindustry.game.*;
import mindustry.graphics.*;
import mindustry.logic.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.power.*;
import mindustry.world.meta.*;

import static mindustry.Vars.*;

public class HydroTurbine extends PowerGenerator {
    public Rect exclusionRect = new Rect(0, 0, 4, 4);

    public float pumpAmount = 0.1f;
    public float warmupSpeed = 0.019f;

    public HydroTurbine(String name) {
        super(name);
        hasLiquids = true;
        outputsLiquid = true;
        floating = true;
        ambientSound = mindustry.gen.Sounds.hum;
        ambientSoundVolume = 0.06f;
    }

    @Override
    public void setStats() {
        super.setStats();
        stats.add(Stat.output, 60f * pumpAmount * size * size, StatUnit.liquidSecond);
    }

    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation) {
        if(isMultiblock()) {
            Liquid last = null;
            int waterTiles = 0;
            int totalTiles = 0;

            for(Tile other : tile.getLinkedTilesAs(this, tempTiles)) {
                totalTiles++;
                if(other.floor().liquidDrop != null) {
                    waterTiles++;
                    if(last != null && other.floor().liquidDrop != last) return false;
                    last = other.floor().liquidDrop;
                }
            }

            if(waterTiles != totalTiles || last == null) return false;

            return !hasConflict(tile, team);
        }
        return tile != null && tile.floor().liquidDrop != null;
    }

    protected boolean hasConflict(Tile tile, Team team) {
        for(int i = 0; i < exclusionRect.width; i++) {
            for(int j = 0; j < exclusionRect.height; j++) {
                Tile nearby = tile.nearby(
                        (int) (exclusionRect.x + i - exclusionRect.width/2 + 1),
                        (int) (exclusionRect.y + j - exclusionRect.height/2 + 1)
                );
                if(nearby != null && nearby.build != null && nearby.build.block instanceof HydroTurbine && nearby != tile) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid) {
        super.drawPlace(x, y, rotation, valid);

        Tile tile = world.tile(x, y);
        if(tile == null) return;

        boolean hasConflict = hasConflict(tile, player.team());

        for(int i = 0; i < exclusionRect.width; i++) {
            for(int j = 0; j < exclusionRect.height; j++) {
                Tile nearby = tile.nearby(
                        (int) (exclusionRect.x + i - exclusionRect.width/2 + 1),
                        (int) (exclusionRect.y + j - exclusionRect.height/2 + 1)
                );
                if(nearby != null && nearby.build != null && nearby.build.block instanceof HydroTurbine) {
                    Drawf.selected(nearby.build, Tmp.c1.set(Pal.remove).a(Mathf.absin(4f, 1f)));
                }
            }
        }

        drawExclusionOverlay(x * tilesize + offset, y * tilesize + offset, hasConflict);
    }

    public void drawExclusionOverlay(float x, float y, boolean conflict) {
        Drawf.dashRect(
                conflict ? Pal.remove : Pal.accent,
                x + (exclusionRect.x - exclusionRect.width/2f) * tilesize,
                y + (exclusionRect.y - exclusionRect.height/2f) * tilesize,
                exclusionRect.width * tilesize,
                exclusionRect.height * tilesize
        );
    }

    public class HydroTurbineBuild extends GeneratorBuild {
        public float warmup, totalProgress, amount = 0f;
        public @Nullable Liquid liquidDrop = null;

        @Override
        public void onProximityUpdate() {
            super.onProximityUpdate();
            amount = 0f;
            liquidDrop = null;

            for(Tile other : tile.getLinkedTiles(tempTiles)) {
                if(other.floor().liquidDrop != null) {
                    liquidDrop = other.floor().liquidDrop;
                    amount += other.floor().liquidMultiplier;
                }
            }
        }

        @Override
        public void updateTile() {
            if(liquidDrop != null && amount > 0.001f && liquids.get(liquidDrop) < liquidCapacity - 0.01f) {
                float maxPump = Math.min(liquidCapacity - liquids.get(liquidDrop), amount * pumpAmount * delta());
                liquids.add(liquidDrop, maxPump);
                productionEfficiency = Mathf.clamp(maxPump / (amount * pumpAmount * delta()));
                warmup = Mathf.approachDelta(warmup, 1f, warmupSpeed);
            } else {
                productionEfficiency = 0f;
                warmup = Mathf.approachDelta(warmup, 0f, warmupSpeed);
            }

            totalProgress += warmup * delta();
            if(liquidDrop != null) dumpLiquid(liquidDrop);
        }

        @Override
        public float warmup() {
            return warmup;
        }

        @Override
        public float totalProgress() {
            return totalProgress;
        }

        @Override
        public double sense(LAccess sensor) {
            if(sensor == LAccess.totalLiquids) return liquidDrop == null ? 0f : liquids.get(liquidDrop);
            return super.sense(sensor);
        }
    }
}