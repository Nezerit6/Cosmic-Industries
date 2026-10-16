package ci.content;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.util.*;
import ci.graphics.draw3d.*;
import mindustry.entities.*;
import mindustry.graphics.*;

import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.*;

public class CIFx {
    public static final Effect

            pressCrush = new Effect(38f, e -> {
        color(CIPal.spark, CIPal.flame, e.fin());
        randLenVectors(e.id, 9, 3f + e.finpow() * 17f, (x, y) -> {
            float ang = Mathf.angle(x, y);
            stroke(e.fout() * 1.3f);
            lineAngle(e.x + x, e.y + y, ang, e.fout() * 3.5f + 0.5f);
        });

        color(Pal.darkerGray, Color.valueOf("3a3a3a"), e.fin());
        alpha(e.fout() * 0.8f);
        randLenVectors(e.id + 1, 6, 2f + e.fin() * 10f, (x, y) -> Fill.circle(e.x + x, e.y + y, 0.6f + e.fout() * 2f));

        reset();
    }),

    pressDust = new Effect(70f, e -> {
        color(Color.valueOf("4b4b4b"), Color.valueOf("6e6e6e"), e.fin());
        alpha(e.fslope() * 0.7f);
        randLenVectors(e.id, 2, 2f + e.fin() * 6f, 90f, 25f, (x, y) -> Fill.circle(e.x + x, e.y + y + e.fin() * 6f, 0.4f + e.fslope() * 1.4f));
        reset();
    }),

    furnaceFlare = new Effect(40f, e -> {
        randLenVectors(e.id, 7, e.finpow() * 9f, 90f, 10f, (x, y) -> {
            float h = Mathf.clamp(y / 9f);
            color(CIPal.melt, CIPal.flame, Mathf.clamp(e.fin() + h * 0.5f));
            Fill.circle(e.x + x, e.y + y, (1.6f - h * 0.9f) * e.fout());
        });

        color(CIPal.spark, CIPal.flame, e.fin());
        randLenVectors(e.id + 1, 6, 2f + e.finpow() * 14f, 90f, 55f, (x, y) -> {
            stroke(e.fout() * 1.1f);
            lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.fout() * 3f + 0.4f);
        });

        color(Pal.darkerGray);
        alpha(e.fslope() * 0.5f);
        randLenVectors(e.id + 2, 3, 6f + e.fin() * 6f, 90f, 15f, (x, y) -> Fill.circle(e.x + x, e.y + y, 0.6f + e.fin() * 1.6f));
        reset();
    }),

    furnaceIgnite = new Effect(45f, e -> {
        float f = e.fin();
        if(f < 0.3f) {
            float k = f / 0.3f;
            color(CIPal.spark);
            alpha(k);
            randLenVectors(e.id, 10, (1f - k) * 7f + 1f, (x, y) -> Fill.circle(e.x + x, e.y + y, 0.35f));
        }else{
            float k = (f - 0.3f) / 0.7f;
            color(CIPal.melt, CIPal.flame, k);
            alpha(1f - k);
            Fill.circle(e.x, e.y, 1.2f + k * 2.2f);
            randLenVectors(e.id + 1, 8, 1f + Interp.pow2Out.apply(k) * 8f, (x, y) -> Fill.circle(e.x + x, e.y + y, (1f - k) * 0.9f));
        }
        reset();
    }),

    furnaceCool = new Effect(90f, e -> {
        color(Color.valueOf("c8beb4"), Color.valueOf("6a625c"), e.fin());
        alpha(e.fslope() * 0.8f);
        for(int i = 0; i < 3; i++) {
            float h = e.fin() * 9f - i * 1.6f;
            if(h < 0f) continue;
            Fill.circle(e.x + Mathf.sin(h * 0.9f + e.id, 1f, 1.1f + h * 0.12f), e.y + h, 0.5f + h * 0.15f);
        }
        reset();
    }),

    meltSpatter = new Effect(34f, e -> {
        for(int i = 0; i < 7; i++) {
            float ang = Mathf.randomSeed(e.id + i, 360f), dist = 4f + Mathf.randomSeed(e.id + i * 3, 7f);
            float f = e.fin();
            float px = e.x + Angles.trnsx(ang, dist * f), py = e.y + Angles.trnsy(ang, dist * f) + Mathf.sin(f * Mathf.PI) * 2.5f;
            color(CIPal.melt, CIPal.cinder, f);
            Fill.circle(px, py, f < 0.9f ? 0.75f : 1.1f * (1f - f) * 10f);
        }
        reset();
    }),

    crashScorch = new Effect(900f, 120f, e -> {
        color(Color.black);
        for(int i = 0; i < 10; i++) {
            float ang = Mathf.randomSeed(e.id + i, 360f), len = Mathf.randomSeed(e.id + i * 3, 4f, 15f);
            alpha(0.22f * e.fout());
            Fill.circle(e.x + trnsx(ang, len), e.y + trnsy(ang, len), Mathf.randomSeed(e.id + i * 7, 5f, 10f));
        }
        alpha(0.3f * e.fout());
        Fill.circle(e.x, e.y, 13f);
    }).layer(Layer.scorch),

    crashHeat = new Effect(170f, 80f, e -> {
        if(!(e.data instanceof TextureRegion)) return;
        mixcol(Tmp.c2.set(CIPal.heat).lerp(CIPal.ember, e.fin()), 0.75f * e.fout());
        alpha(e.fout());
        rect((TextureRegion)e.data, e.x, e.y);
        mixcol();
    }).layer(Layer.blockOver),

    crashFlash = new Effect(26f, 140f, e -> {
        blend(Blending.additive);
        Fill.light(e.x, e.y, 16, e.rotation * (0.6f + e.finpow() * 0.8f), Tmp.c2.set(Color.white).lerp(CIPal.heat, e.fin()).a(e.fout()), Tmp.c3.set(CIPal.heat).a(0f));
        blend();
    }).layer(Layer.flyingUnit + 2f),

    crashFire = new Effect(32f, 1e9f, e -> {
        float z = (Float)e.data * Perspective.cameraZ(), size = e.rotation * Perspective.depthScale(z), ang = Mathf.randomSeed(e.id, 360f), d = e.finpow() * 7f * size;
        Vec2 p = Perspective.drawPos(e.x, e.y, z);
        z(e.time < 8f ? Layer.space - 1f : Layer.space + 1f);
        color(Tmp.c1.set(Color.white).lerp(CIPal.heat, Mathf.clamp(e.fin() * 2.2f)).lerp(CIPal.ash, Mathf.clamp(e.fin() * 1.8f - 0.7f)), Perspective.fade(z) * e.fout());
        Fill.circle(p.x + trnsx(ang, d), p.y + trnsy(ang, d), (1.6f + e.fin() * 2.6f) * size);
    }).layer(Layer.space + 1f),

    crashSmoke = new Effect(220f, 1e9f, e -> {
        float z = (Float)e.data * Perspective.cameraZ();
        Vec2 p = Perspective.drawPos(e.x, e.y, z);
        z(e.time < 8f ? Layer.space - 1f : Layer.space + 1f);
        color(Color.gray, Perspective.fade(z) * Mathf.clamp(e.fout() * 1.6f - 0.1f) * 0.8f);
        Fill.circle(p.x, p.y, (3.4f - e.fin() * 2f) * e.rotation * Perspective.depthScale(z));
    }).layer(Layer.space + 1f);

    private static final Rand rand = new Rand();

    public static final Effect pileStrike = new Effect(80f, 120f, e -> {
        float r = e.rotation, k = e.fin();
        rand.setSeed(e.id);
        for(int i = 0; i < 8; i++) {
            float ang = i * 45f + rand.range(15f);
            crack(e.x + trnsx(ang, r * 0.8f), e.y + trnsy(ang, r * 0.8f), ang, r * (0.6f + rand.random(0.6f)) * Interp.pow5Out.apply(Math.min(k * 4f, 1f)), 1.2f, 3, 1f - Interp.pow2In.apply(k), 0f);
        }
        float ring = Math.min(k * 2.5f, 1f);
        color(CIPal.smokeLight, CIPal.ash, ring);
        alpha(1f - ring);
        stroke(3f * (1f - ring));
        Lines.circle(e.x, e.y, r + r * 1.6f * Interp.pow3Out.apply(ring));
        for(int i = 0; i < 18; i++) {
            float ang = i * 20f + rand.range(8f), dist = r * (0.9f + rand.random(1.2f) * Interp.pow3Out.apply(k));
            color(CIPal.smokeLight, CIPal.smokeDark, k);
            alpha((1f - k) * 0.5f);
            Fill.circle(e.x + trnsx(ang, dist), e.y + trnsy(ang, dist), 1.2f + k * 3.5f * rand.random(0.6f, 1f));
        }
        for(int i = 0; i < 10; i++) {
            float ang = rand.random(360f), dist = r * (0.5f + rand.random(1.5f)), life = rand.random(0.4f, 0.8f);
            float f = Mathf.clamp(k / life);
            if(f >= 1f) continue;
            float d = r * 0.8f + dist * Interp.pow3Out.apply(f);
            float px = e.x + trnsx(ang, d), py = e.y + trnsy(ang, d);
            color(e.color, Tmp.c1.set(e.color).mul(0.5f), f);
            stroke(1.2f * (1f - f) + 0.3f);
            lineAngle(px, py, ang + 180f, 3f * (1f - f));
            Fill.square(px, py, 0.6f + (1f - f) * 0.8f, 45f);
        }
        reset();
    });
    public static final Effect pileGrind = new Effect(60f, e -> {
        e.scaled(24f, s -> {
            float len = Interp.pow2Out.apply(s.fin()) * 9f;
            color(e.color, Tmp.c1.set(e.color).mul(0.55f), s.fin());
            Fill.square(e.x + trnsx(e.rotation, len), e.y + trnsy(e.rotation, len), 0.4f + s.fout() * 0.8f, 45f);

            if(Mathf.randomSeed(e.id) < 0.5f) {
                float sa = e.rotation + Mathf.randomSeedRange(e.id + 1, 20f), sl = len * 1.4f;
                color(CIPal.melt, CIPal.heat, s.fin());
                stroke(0.6f * s.fout() + 0.2f);
                lineAngle(e.x + trnsx(sa, sl), e.y + trnsy(sa, sl), sa, 1f + s.fout() * 2.5f);
            }
        });

        if(Mathf.randomSeed(e.id + 2) < 0.35f) {
            float dist = Interp.pow2Out.apply(e.fin()) * 8f;
            color(CIPal.smokeLight, CIPal.smokeDark, e.fin());
            alpha(Mathf.slope(e.fin()) * 0.4f);
            Fill.circle(e.x + trnsx(e.rotation, dist), e.y + trnsy(e.rotation, dist), 0.8f + e.fin() * 2.4f);
        }
        reset();
    });

    private static void crack(float x, float y, float ang, float len, float width, int segments, float fade, float glow) {
        float px = x, py = y;
        for(int s = 1; s <= segments; s++) {
            float k = s / (float)segments, a = ang + rand.range(14f);
            float nx = px + trnsx(a, len / segments), ny = py + trnsy(a, len / segments), w = width * (1.15f - k);
            color(CIPal.crust, 0.9f * fade);
            stroke(w + 0.6f);
            line(px, py, nx, ny);
            if(glow > 0f) {
                color(Tmp.c1.set(CIPal.melt).lerp(CIPal.heat, k).lerp(CIPal.cinder, 1f - glow), glow * fade);
                stroke(w * 0.45f);
                line(px, py, nx, ny);
            }
            px = nx;
            py = ny;
        }
    }
}