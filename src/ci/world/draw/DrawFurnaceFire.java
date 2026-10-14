package ci.world.draw;

import ci.content.CIPal;
import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.Time;
import mindustry.gen.Building;
import mindustry.graphics.*;
import mindustry.world.draw.DrawBlock;

public class DrawFurnaceFire extends DrawBlock {
    public Color cold = CIPal.cinder, melt = Color.valueOf("ffb066"), core = CIPal.melt;
    public float radius = 1.5f, lightRadius = 20f;

    @Override
    public void draw(Building build) {
        float w = build.warmup();
        if(w <= 0.001f) return;

        float flick = Mathf.absin(Time.time + build.id * 13f, 4f, 0.12f);
        Draw.z(Layer.block + 0.01f);
        Draw.color(cold, w);
        Fill.circle(build.x, build.y, radius);
        Draw.color(melt, w);
        Fill.circle(build.x, build.y, radius * 0.75f + flick);
        Draw.color(core, w);
        Fill.circle(build.x, build.y, radius * 0.35f + flick);
        Draw.reset();
    }

    @Override
    public void drawLight(Building build) {
        Drawf.light(build.x, build.y, (lightRadius + Mathf.absin(5f, 2f)) * build.warmup(), melt, 0.7f);
    }
}