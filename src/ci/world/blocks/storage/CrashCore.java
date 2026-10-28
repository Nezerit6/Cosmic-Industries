package ci.world.blocks.storage;

import arc.audio.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import ci.content.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.storage.*;

import static mindustry.Vars.*;

public class CrashCore extends CoreBlock {
    protected Effect defaultLaunchEffect;
    protected Sound defaultLandSound;

    public CrashCore(String name) {
        super(name);
    }

    @Override
    public void load() {
        super.load();
        TextureRegion t = thruster1;
        thruster1 = thruster2;
        thruster2 = t;
    }

    @Override
    public void init() {
        defaultLaunchEffect = launchEffect;
        defaultLandSound = landSound;
        super.init();
    }

    @Override
    public TextureRegion[] icons() {
        return new TextureRegion[]{region, teamRegions[Team.sharded.id]};
    }

    @Override
    public boolean displayShadow(Tile tile) {
        return super.displayShadow(tile) && !(tile.build instanceof CrashCoreBuild b && b.falling());
    }

    public class CrashCoreBuild extends CoreBuild {
        public @Nullable CILanding landing;

        public boolean falling() {
            return custom() && landing.falling;
        }

        public boolean custom() {
            return landing != null && !renderer.isLaunching();
        }

        public void landDust(float amount) {
            landParticleTimer += amount * Time.delta;
            if(landParticleTimer < 1f) return;
            landParticleTimer = 0f;
            tile.getLinkedTiles(t -> {
                if(Mathf.chance(0.4f)) Fx.coreLandDust.at(t.worldx(), t.worldy(), angleTo(t.worldx(), t.worldy()) + Mathf.range(30f), Tmp.c1.set(t.floor().mapColor).mul(1.1f + Mathf.range(0.1f)));
            });
        }

        public void thrusters(float x, float y, float rotation, float frame) {
            drawLandingThrusters(x, y, rotation, frame);
        }

        @Override
        public float launchDuration() {
            return renderer.isLaunching() ? super.launchDuration() : CILanding.duration;
        }

        @Override
        public void beginLaunch(boolean launching) {
            if(!launching && landing == null) landing = new CILanding(this);
            if(launching) landing = null;
            launchEffect = landing != null ? Fx.none : defaultLaunchEffect;
            landSound = landing != null ? CISounds.capsuleLand : defaultLandSound;
            super.beginLaunch(launching);
            if(landing != null) landing.begin();
        }

        @Override
        public float zoomLaunch() {
            return custom() ? landing.zoom() : super.zoomLaunch();
        }

        @Override
        public void updateLaunch() {
            if(custom()) landing.update();
            else super.updateLaunch();
        }

        @Override
        public void drawLaunch() {
            if(!custom()) {
                super.drawLaunch();
                return;
            }
            float ox = x, oy = y;
            landing.center(Tmp.v1);
            x = Tmp.v1.x;
            y = Tmp.v1.y;
            try {
                super.drawLaunch();
            } finally {
                x = ox;
                y = oy;
            }
        }

        @Override
        public void drawLanding(float x, float y) {
            if(custom()) landing.draw(x, y);
            else super.drawLanding(x, y);
        }

        @Override
        public void requestSpawn(Player player) {
            if(!falling()) super.requestSpawn(player);
        }

        @Override
        public void draw() {
            if(!falling()) super.draw();
        }

        @Override
        public void drawLight() {
            if(!falling()) super.drawLight();
        }

        @Override
        public void endLaunch() {
            super.endLaunch();
            if(renderer.isLaunching()) return;
            if(landing != null) landing.end();
            if(state.rules.sector != null && state.rules.sector.planet.techTree != null) {
                state.rules.sector.planet.techTree.each(n -> {
                    if(n.content instanceof Item) items.set((Item)n.content, storageCapacity);
                });
            }
        }
    }
}