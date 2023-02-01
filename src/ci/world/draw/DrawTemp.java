package ci.world.draw;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import mindustry.gen.Building;
import mindustry.world.Block;
import mindustry.world.draw.DrawBlock;

public class DrawTemp extends DrawBlock {
    public String suffix = "-top";
    public float alpha = 1f, scale = 12f;
    public TextureRegion region;

    @Override
    public void draw(Building build){
        Draw.alpha(Mathf.clamp(Mathf.absin(build.totalProgress(), scale, alpha) * build.warmup(), 0.6f, 1f));
        Draw.rect(region, build.x, build.y);
        Draw.reset();
    }

    @Override
    public void load(Block block){
        region = Core.atlas.find(block.name + suffix);
    }
}