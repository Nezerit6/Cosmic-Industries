package ci.world.blocks.distribution;

import arc.Core;
import arc.graphics.g2d.*;
import mindustry.gen.Unit;
import mindustry.graphics.Layer;
import mindustry.graphics.MultiPacker;
import mindustry.graphics.MultiPacker.PageType;
import mindustry.world.blocks.distribution.Conveyor;

import static mindustry.Vars.*;

public class CIConveyor extends Conveyor {
    public String sheet;
    public TextureRegion[] topRegions = new TextureRegion[5];

    public CIConveyor(String name) {
        super(name);
        sheet = this.name + "-sheet";
    }

    @Override
    public void createIcons(MultiPacker packer) {
        PixmapRegion sheet = Core.atlas.getPixmap(this.sheet);
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 4; j++) {
                String frame = name + "-" + i + "-" + j;
                packer.add(PageType.main, frame, sheet.crop(j * 32, i * 32, 32, 32));
                regions[i][j] = Core.atlas.find(frame);
            }
        }

        if(Core.atlas.has(name + "-top-sheet")) {
            PixmapRegion top = Core.atlas.getPixmap(name + "-top-sheet");
            for(int i = 0; i < 5; i++) {
                packer.add(PageType.main, name + "-top-" + i, top.crop(i * 32, 0, 32, 32));
            }
        }
        super.createIcons(packer);
    }

    @Override
    public void load() {
        super.load();
        for(int i = 0; i < 5; i++) {
            topRegions[i] = Core.atlas.find(name + "-top-" + i);
        }
    }

    public class CIConveyorBuild extends ConveyorBuild {

        @Override
        public void draw() {
            super.draw();
            if(!topRegions[0].found()) return;
            Draw.z(Layer.block + 0.1f);
            Draw.rect(topRegions[blendbits], x, y, tilesize * blendsclx, tilesize * blendscly, rotation * 90);
        }

        @Override
        public void unitOn(Unit unit) {
        }
    }
}