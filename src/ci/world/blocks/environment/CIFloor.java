package ci.world.blocks.environment;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import mindustry.graphics.*;
import mindustry.graphics.MultiPacker.*;
import mindustry.world.blocks.environment.*;

public class CIFloor extends Floor{
    public String stencil = "ci-edge-stencil";
    public int edgeVariants = 4;
    public TextureRegion[][][] edgeRegions;

    public CIFloor(String name, int variants){
        super(name, variants);
    }

    @Override
    public void createIcons(MultiPacker packer){
        if(blendGroup == this){
            PixmapRegion image = Core.atlas.getPixmap(icons()[0]);
            for(int i = 1; i <= edgeVariants; i++){
                String edgeName = edgeName(i);
                if(Core.atlas.has(edgeName)) continue;
                PixmapRegion edge = Core.atlas.getPixmap(Core.atlas.find(stencil + i, "edge-stencil"));
                Pixmap result = new Pixmap(edge.width, edge.height);
                for(int x = 0; x < edge.width; x++){
                    for(int y = 0; y < edge.height; y++){
                        result.set(x, y, Color.muli(edge.get(x, y), image.get(x % image.width, y % image.height)));
                    }
                }
                packer.add(PageType.environment, edgeName, result);
                result.dispose();
            }
        }
        super.createIcons(packer);
    }

    @Override
    public void load(){
        super.load();
        edgeRegions = new TextureRegion[edgeVariants][][];
        for(int i = 0; i < edgeVariants; i++){
            edgeRegions[i] = Core.atlas.has(edgeName(i + 1)) ? Core.atlas.find(edgeName(i + 1)).split(32, 32) : edges;
        }
    }

    @Override
    protected TextureRegion[][] edges(int x, int y){
        return blendGroup instanceof CIFloor f ? f.edgeRegions[Mathf.randomSeed(Point2.pack(x, y), 0, f.edgeVariants - 1)] : super.edges(x, y);
    }

    String edgeName(int i){
        return i == 1 ? name + "-edge" : name + "-edge" + i;
    }
}