package ci.world.draw;

import arc.*;
import arc.graphics.g2d.*;
import arc.math.*;
import mindustry.gen.*;
import mindustry.world.*;
import mindustry.world.draw.DrawBlock;

public class DrawTemp extends DrawBlock {
    public String suffix = "-top";
    public float alpha = 0.5f, scale = 7.5f;
    public TextureRegion region;

    @Override
    public void draw(Building build){
        Draw.alpha(Mathf.clamp(Mathf.absin(build.totalProgress(), scale, alpha) * build.warmup(), 0f, 0.5f));
        Draw.rect(region, build.x, build.y);
        Draw.reset();
    }

    @Override
    public void load(Block block){
        region = Core.atlas.find(block.name + suffix);
    }
}