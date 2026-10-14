package ci.world.blocks.storage;

import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.scene.ui.layout.Scl;
import arc.util.*;
import ci.content.*;
import ci.graphics.draw3d.*;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.gen.Sounds;
import mindustry.graphics.*;
import mindustry.type.Item;
import mindustry.world.Tile;
import mindustry.world.blocks.storage.CoreBlock;

import static mindustry.Vars.*;

public class CrashCore extends CoreBlock {
    public CrashCore(String name) {
        super(name);
        landDuration = 130f;
        landZoomInterp = Interp.pow3In;
    }

    public static boolean falling() {
        return renderer.getLandTime() > 0f && !renderer.isLaunching();
    }

    @Override
    public boolean displayShadow(Tile tile) {
        return !falling() && super.displayShadow(tile);
    }

    public class CrashCoreBuild extends CoreBuild {
        public Vec3 pos = new Vec3();
        public float size;

        @Override
        public void beginLaunch(boolean launching) {
            super.beginLaunch(launching);
            pos.setZero();
            if(!launching) renderer.blocks.updateShadow(this);
        }

        @Override
        public void updateLaunch() {
            if(!falling()) {
                super.updateLaunch();
                return;
            }
            if(pos.z <= 0f) return;

            for(int i = 0; i < 3; i++) {
                if(i == 2 && !Mathf.chanceDelta(0.4f)) break;
                Tmp.v1.rnd(Mathf.random(8f, 11f) * size);
                (i < 2 ? CIFx.crashFire : CIFx.crashSmoke).at(pos.x + Tmp.v1.x, pos.y + Tmp.v1.y, size, pos.z / Perspective.cameraZ());
            }
        }

        @Override
        public void drawLight() {
            if(!falling()) super.drawLight();
        }

        @Override
        public void draw() {
            if(!falling()) super.draw();
        }

        @Override
        public void drawLanding(float x, float y) {
            if(!falling()) {
                super.drawLanding(x, y);
                return;
            }

            float e = 1f - Interp.pow3In.apply(renderer.getLandTimeIn()), cz = Perspective.cameraZ();
            float rot = e * (540f + Mathf.randomSeed(id, 360f));
            size = Scl.scl(4f) / renderer.getDisplayScale();
            pos.set(x - 0.12f * cz * e, y + 0.045f * cz * e, 0.5f * cz * e);

            float shx = pos.x + Angles.trnsx(225f, pos.z), shy = pos.y + Angles.trnsy(225f, pos.z), shs = size * Draw3D.shadowScale(pos.z), sha = Draw3D.shadowAlpha(pos.z);
            Draw3D.shadow(() -> {
                Draw.color(Pal.shadow, Pal.shadow.a * sha);
                Draw.rect(region, shx, shy, region.width * region.scl() * shs, region.height * region.scl() * shs, rot);
                Draw.color();
            });

            Vec2 p = Perspective.drawPos(pos.x, pos.y, pos.z);
            float px = p.x, py = p.y, s = size * Perspective.depthScale(pos.z);

            Fill.light(px, py, 12, 30f * s, Tmp.c2.set(CIPal.heat).a(0.55f), Tmp.c3.set(CIPal.heat).a(0f));

            Draw.color();
            Draw.scl(s);
            Draw.mixcol(CIPal.heat, 0.5f * (0.85f + Mathf.absin(2f, 0.15f)));
            drawLandingThrusters(px, py, rot, 1f);
            Drawf.spinSprite(region, px, py, rot);
            if(teamRegions[team.id] == teamRegion) Draw.color(team.color);
            Drawf.spinSprite(teamRegions[team.id], px, py, rot);
            Draw.reset();
            Draw.scl();
        }

        @Override
        public void endLaunch() {
            super.endLaunch();
            if(renderer.isLaunching()) return;

            renderer.blocks.updateShadow(this);
            if(state.rules.sector != null && state.rules.sector.planet.techTree != null) {
                state.rules.sector.planet.techTree.each(n -> {
                    if(n.content instanceof Item) items.set((Item)n.content, storageCapacity);
                });
            }
            Effect.shake(9f, 50f, x, y);
            Fx.spawnShockwave.at(x, y, 70f);
            CIFx.crashFlash.at(x, y, 36f);
            CIFx.crashScorch.at(x, y);
            CIFx.crashHeat.at(x, y, 0f, region);
            Sounds.wreckFallBig.at(x, y);
            for(int i = 0; i < 6; i++) {
                Fx.rocketSmokeLarge.at(x + Mathf.range(16f), y + Mathf.range(16f), 1.4f);
            }
            tile.getLinkedTiles(t -> Fx.coreLandDust.at(t.worldx(), t.worldy(), angleTo(t.worldx(), t.worldy()) + Mathf.range(30f), Tmp.c1.set(t.floor().mapColor).mul(1.4f)));
        }
    }
}