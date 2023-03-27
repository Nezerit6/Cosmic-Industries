package ci.content.blocks.power;

import arc.audio.Sound;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.math.Angles;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.util.Log;
import arc.util.Time;
import mindustry.content.Fx;
import mindustry.core.Renderer;
import mindustry.entities.Damage;
import mindustry.entities.Effect;
import mindustry.entities.Lightning;
import mindustry.gen.Building;
import mindustry.gen.Sounds;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.logic.LExecutor;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.blocks.power.PowerNode;

import static arc.math.Mathf.rand;
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


    public MagnesiumNode(String name) {
        super(name);
        laserColor1 = Color.black;
    }


    public class MagnesiumNodeBuild extends PowerNodeBuild{
        @Override
        public void draw(){
            super.draw();
            drawParticles();
        }

        public void drawParticles(){
//            Log.info("drawParticles");
            float currentPower = power.graph.getPowerBalance();
            Log.info("current_power2: " + currentPower);
            Log.info("x: " + x + "|y: " + y);
            for (int i = 0; i < 40; i++) {
                Fx.airBubble.at(x * tilesize + Mathf.range(-5, 5), y * tilesize + Mathf.range(-5, 5), Pal.gray);
                Fx.dooropen.at(100, 100);
            }


            if (currentPower != 0 && Mathf.random() > 0.9) {
                Log.info("here2");
                Fx.airBubble.at(x * tilesize + Mathf.range(-2, 2), y * tilesize + Mathf.range(-2, 2), Pal.gray);
            }
            if (currentPower > LIMIT_POWER) {
                Log.info("here");
//                health -= 1f;
                Damage.damage(x, y,  tilesize, 1);
                getPotentialLinks(tile, player.team(), other -> {
                    Damage.damage(other.x, other.y, tilesize, 1);
                });
            }

        }

        @Override
        public void update() {
            super.update();

            float currentPower = power.graph.getPowerBalance();
            Log.info("1|" + currentPower);
            if (currentPower != 0 && Mathf.random() > 0.9) {
                Log.info("here2");
                Fx.airBubble.at(x * tilesize + Mathf.range(-2, 2), y * tilesize + Mathf.range(-2, 2), Pal.gray);
            }
            if (currentPower > LIMIT_POWER) {
                Log.info("here");
//                health -= 1f;
                getPotentialLinks(tile, team, other -> {
                    Damage.damage(other.x, other.y, 5 * tilesize, 10);
                });
                Damage.damage(x, y,  5 * tilesize, 10);
                Damage.damage(x, y,  10 * tilesize, 10);
            }
        }

        @Override
        public void onDestroyed() {


            for (int i = 0; i < explosionPuddles; i++) {
                v1.trns(Mathf.random(360f), Mathf.random(explosionPuddleRange));
            }
            for (int i = 0; i < lightningAmount; i++) {
                Lightning.create(team, lightningColor, lightningDamage, x, y, Mathf.random(360), lightningLength);
            }

            lightningSound.at(tile, Mathf.random(0.9f, 1.1f));
            super.onDestroyed();
        }
    }


}
