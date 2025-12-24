package ci.world.draw;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.world.*;
import mindustry.world.draw.DrawBlock;
import mindustry.world.blocks.production.GenericCrafter;

public class DrawTemp extends DrawBlock {
    public String suffix = "-heat";
    public float alpha = 0.8f;
    public float pulseScale = 7.5f;
    public Color color = Color.valueOf("ffffff");
    public float glowMult = 1.2f;

    public float lightRadius = 60f;
    public float lightAlpha = 0.65f;
    public Color lightColor = Color.valueOf("ffffff");

    public TextureRegion heatRegion, glowRegion, topRegion;

    @Override
    public void draw(Building build){
        if(!(build.block instanceof GenericCrafter)) return;

        GenericCrafter.GenericCrafterBuild crafter = (GenericCrafter.GenericCrafterBuild)build;

        float warmup = crafter.warmup();

        if(warmup <= 0.001f) return;

        float pulse = (1f - alpha) + Mathf.absin(crafter.totalProgress(), pulseScale, alpha);

        float finalAlpha = Mathf.clamp(pulse * warmup, 0f, 1f);

        if(topRegion.found()){
            Draw.rect(topRegion, build.x, build.y);
        }

        if(finalAlpha > 0.001f){
            Draw.z(Layer.blockAdditive);
            Draw.blend(Blending.additive);

            Draw.color(color, finalAlpha);
            if(heatRegion.found()) Draw.rect(heatRegion, build.x, build.y);

            Draw.color(color.cpy().mul(glowMult), finalAlpha * 0.8f);
            if(glowRegion.found()) Draw.rect(glowRegion, build.x, build.y);

            Draw.blend();
            Draw.color();
        }
    }

    @Override
    public void drawLight(Building build){
        if(!(build.block instanceof GenericCrafter)) return;

        GenericCrafter.GenericCrafterBuild crafter = (GenericCrafter.GenericCrafterBuild)build;

        float warmup = crafter.warmup();

        if(warmup <= 0.001f) return;

        float pulse = (1f - alpha) + Mathf.absin(crafter.totalProgress(), pulseScale, alpha);

        float lightIntensity = pulse * warmup * lightAlpha;

        Drawf.light(build.x, build.y, lightRadius * warmup, lightColor, lightIntensity);
    }

    @Override
    public void load(Block block){
        heatRegion = Core.atlas.find(block.name + suffix);
        glowRegion = Core.atlas.find(block.name + "-glow");
        topRegion = Core.atlas.find(block.name + "-top");
    }
}