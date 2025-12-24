package ci.content;

import arc.Core;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.struct.Seq;
import arc.util.*;
import ci.content.blocks.CIStorageBlocks;
import mindustry.content.*;
import mindustry.entities.Effect;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.world.Block;
import mindustry.world.Tile;

import static mindustry.Vars.*;

public class CapsuleFall {

    public static Block fallBlock = CIStorageBlocks.enemyCapsule;

    public static void capsuleFall(float x, float y) {
        capsuleFall(x, y, Team.crux, Mathf.random(120f, 150f));
    }

    public static void capsuleFall(float x, float y, Team team, float fallTime) {
        if (team == null) team = Team.crux;
        FallingCapsuleData data = new FallingCapsuleData();
        data.targetX = x;
        data.targetY = y;
        data.team = team;
        data.fallTime = fallTime;
        FallingCapsuleRenderer.add(data);
    }

    public static class FallingCapsuleData {
        public float targetX, targetY, fallTime, lifetime;
        public Team team;
        public boolean landed;

        public float fin() {
            return Math.min(lifetime / fallTime, 1f);
        }
    }

    public static class FallingCapsuleRenderer {
        public static Seq<FallingCapsuleData> falling = new Seq<>();

        public static void add(FallingCapsuleData data) {
            falling.add(data);
        }

        public static void update() {
            falling.each(data -> {
                if (!state.isPaused()) data.lifetime += Time.delta;

                float scale = Mathf.lerp(4.0f, 1.0f, data.fin());

                if (scale <= 1.0f && !data.landed) {
                    data.landed = true;
                    Tile tile = world.tileWorld(data.targetX, data.targetY);

                    if (tile != null && tile.block().isAir() && fallBlock != null) {
                        tile.setNet(fallBlock, data.team, 0);
                        Fx.explosion.at(data.targetX, data.targetY, 16f);
                        Effect.shake(3f, 3f, data.targetX, data.targetY);
                        Sounds.boom.at(data.targetX, data.targetY);
                    } else {
                        Fx.explosion.at(data.targetX, data.targetY, 24f);
                        Effect.shake(2f, 2f, data.targetX, data.targetY);
                        Sounds.explosionbig.at(data.targetX, data.targetY);
                    }
                }
            });

            falling.removeAll(data -> data.landed);
        }

        public static void draw() {
            falling.each(data -> {
                TextureRegion region = fallBlock != null ? fallBlock.fullIcon : Core.atlas.find("launchpod");
                float fin = data.fin();
                float scale = Mathf.lerp(4.0f, 1.0f, fin);
                float elevation = (1f - fin) * 200f;
                float rotation = Mathf.clamp(fin * 1.05f) * 180f;

                Draw.z(Layer.darkness);
                Draw.alpha(Mathf.lerp(0.2f, 0.6f, fin));
                Draw.scl(scale);
                Drawf.shadow(region, data.targetX, data.targetY, rotation);
                Draw.reset();

                Draw.z(Layer.flyingUnit + elevation * 0.01f);
                Draw.alpha(Mathf.clamp(fin * 2f));
                Draw.scl(scale);
                Drawf.spinSprite(region, data.targetX, data.targetY + elevation, rotation);
                Draw.reset();

                Drawf.light(data.targetX, data.targetY, 80f, data.team.color, 0.5f * fin);
            });
        }
    }
}