package ci.content.blocks.power;

import arc.audio.Sound;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.util.Log;
import mindustry.content.Fx;
import mindustry.core.Renderer;
import mindustry.entities.Damage;
import mindustry.entities.Effect;
import mindustry.entities.Lightning;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Sounds;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.world.blocks.power.PowerNode;

import static mindustry.Vars.*;

public class MagnesiumNode extends PowerNode {
    public Effect energyEffect = Fx.airBubble;
    public float generateEnergyEffectRange = 2f;
    public Color lightningColor = Pal.surge;
    public Sound lightningSound = Sounds.spark;
    public float lightningDamage = 20f;
    public int lightningLength = 7;
    public int lightningAmount = 10;
    public int explosionPuddles = 10;
    public float explosionPuddleRange = tilesize * 2f;
    public int LIMIT_POWER = 1_000;
    public final Vec2 v1 = new Vec2();
    public Color backupColour = Color.yellow;


    public MagnesiumNode(String name) {
        super(name);

    }


    public class MagnesiumNodeBuild extends PowerNodeBuild{
        @Override
        public void draw(){
            super.draw();

            if(Mathf.zero(Renderer.laserOpacity) || isPayload()) return;

            Draw.z(Layer.power);
            float temp_val = determineAmountOfBubbles(getCurrentPower());
            if (temp_val == 0)
                setupColor(0f);
            else if (temp_val <= 10) {
                setupColor(0.5f);
            } else {
                setupColor(1f);
            }

            for(int i = 0; i < power.links.size; i++){
                Building link = world.build(power.links.get(i));

                if(!linkValid(this, link)) continue;

                if(link.block instanceof PowerNode && link.id >= id) continue;

                drawLaser(x, y, link.x, link.y, size, link.block.size);
            }

            Draw.reset();
            drawParticles();
        }

        public void drawParticles(){
//            Log.info("drawParticles");

            float currentPower = getCurrentPower();



            if (currentPower != 0 && Mathf.random() > 0.99) {
                Log.info("ENERGY " + currentPower);
                int amount = determineAmountOfBubbles(currentPower);
                for (int i = 0; i < (amount > 10 ? 10 : amount); i++) {
                    Fx.airBubble.at(x  + Mathf.range(-2, 2), y + Mathf.range(-2, 2), Pal.bulletYellow);
                }
            }
            if (currentPower > LIMIT_POWER) {
                Log.info("LIMIT_POWER");
//                health -= 1f;
                Damage.damage(x, y,  tilesize, 1);
                getPotentialLinks(tile, player.team(), other -> {
                    Damage.damage(other.x, other.y, tilesize, 1);
                });
            }

        }

        public float getCurrentPower(){
            return power.graph.getPowerBalance();
        }

        public int determineAmountOfBubbles(float power){
            return Mathf.round(power / 10);
        }


        private void changeColor() {
            float currentPower = power.graph.getPowerBalance();
            if (currentPower == 0) {
                laserColor2 = Color.black;
            } else {
                laserColor2 = backupColour;
            }
        }

        @Override
        public void created(){ // Called when one is placed/loaded in the world
            changeColor();

            super.created();
        }

        @Override
        public void onDestroyed() {


            for (int i = 0; i < explosionPuddles; i++) {
                v1.trns(Mathf.random(360f), Mathf.random(explosionPuddleRange));
            }
            Team team1 = null;
            for (int i = 0; i < lightningAmount; i++) {
                Lightning.create(team1, lightningColor, lightningDamage, x, y, Mathf.random(360), lightningLength);
            }

            lightningSound.at(tile, Mathf.random(0.9f, 1.1f));
            super.onDestroyed();
        }
    }


}
