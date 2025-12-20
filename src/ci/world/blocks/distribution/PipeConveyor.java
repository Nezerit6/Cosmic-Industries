package ci.world.blocks.distribution;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.util.Eachable;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.Unit;
import mindustry.graphics.Layer;
import mindustry.world.blocks.distribution.Conveyor;

import static mindustry.Vars.tilesize;

public class PipeConveyor extends Conveyor {
    public TextureRegion[] topRegions;
    public TextureRegion fullIconRegion;

    public PipeConveyor(String name) {
        super(name);
    }

    @Override
    public void load() {
        super.load();

        topRegions = new TextureRegion[5];
        for(int i = 0; i < 5; i++) {
            topRegions[i] = Core.atlas.find(name + "-top-" + i);
        }

        fullIconRegion = Core.atlas.find(name);
    }

    @Override
    public TextureRegion[] icons() {
        return new TextureRegion[]{fullIconRegion};
    }

    @Override
    public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list){
        int[] bits = getTiling(plan, list);

        if(bits == null) return;

        Draw.rect(fullIconRegion, plan.drawx(), plan.drawy(),
                fullIconRegion.width * bits[1] * fullIconRegion.scl(),
                fullIconRegion.height * bits[2] * fullIconRegion.scl(),
                plan.rotation * 90);

        TextureRegion topRegion = topRegions[Math.min(bits[0], 4)];
        Draw.rect(topRegion, plan.drawx(), plan.drawy(),
                topRegion.width * bits[1] * topRegion.scl(),
                topRegion.height * bits[2] * topRegion.scl(),
                plan.rotation * 90);
    }

    public class PipeConveyorBuild extends ConveyorBuild {

        @Override
        public void unitOn(Unit unit) {
        }

        @Override
        public void draw() {
            super.draw();

            int topIndex = Math.min(blendbits, 4);
            Draw.z(Layer.block + 0.1f);
            Draw.rect(topRegions[topIndex], x, y,
                    tilesize * blendsclx, tilesize * blendscly, rotation * 90);
        }
    }
}