package ci.content;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.util.*;
import ci.graphics.draw3d.*;
import mindustry.entities.*;
import mindustry.content.*;
import mindustry.core.*;
import mindustry.graphics.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;

import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.*;
import static mindustry.Vars.*;

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
    });

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

    public static final Effect landFire = new Effect(32f, 1e9f, e -> {
        if(!(e.data instanceof float[] d)) return;
        float m = (1f - Mathf.pow(0.96f, Math.min(e.time, d.length > 5 ? d[5] : e.time))) / 0.04f, z = Math.max(d[0] + d[3] * m, 1f);
        if(!Perspective.canDraw(z)) return;
        Vec2 p = Perspective.drawPos(e.x + d[1] * m, e.y + d[2] * m, z);
        float size = e.rotation * Perspective.depthScale(z), ang = Mathf.randomSeed(e.id, 360f), off = e.finpow() * 5f * size;
        z(e.time < 14f ? Layer.space - 1f : Layer.space + 1f);
        color(Tmp.c1.set(Color.white).lerp(CIPal.heat, Mathf.clamp(e.fin() * 2.2f)).lerp(CIPal.cinder, Mathf.clamp(e.fin() * 1.8f - 0.6f)), Perspective.fade(z) * Mathf.clamp((Perspective.cameraZ() - z - 40f) / 120f) * e.fout());
        Fill.circle(p.x + trnsx(ang, off), p.y + trnsy(ang, off), (1.4f + e.fin() * 2.2f) * size);
    }).layer(Layer.space + 1f);
    public static final Effect landSmoke = new Effect(120f, 1e9f, e -> {
        if(!(e.data instanceof float[] d)) return;
        float m = (1f - Mathf.pow(0.96f, Math.min(e.time, d.length > 5 ? d[5] : e.time))) / 0.04f, z = Math.max(d[0] + d[3] * m, 1f);
        if(!Perspective.canDraw(z)) return;
        Vec2 p = Perspective.drawPos(e.x + d[1] * m, e.y + d[2] * m, z);
        z(e.time < 10f ? Layer.space - 1f : Layer.space + 1f);
        color(CIPal.smokeLight, CIPal.smokeDark, e.fin());
        alpha(Perspective.fade(z) * Mathf.clamp((Perspective.cameraZ() - z - 40f) / 120f) * Mathf.clamp(e.fout() * 1.6f - 0.1f) * 0.8f * Mathf.clamp(1f - (e.time - d[5]) / 30f));
        Fill.circle(p.x, p.y, (2.2f + e.fin() * 3.4f) * e.rotation * Perspective.depthScale(z));
    }).layer(Layer.space + 1f);
    public static final Effect tileUplift = new Effect(600f, 120f, e -> {
        float r = e.rotation > 0f ? e.rotation : 20f, hold = e.data instanceof Float t ? t : 0f;
        if(e.time > 26f + (hold > 0f ? hold : 36f)) return;
        int tr = (int)(r / tilesize) + 1, cx = World.toTile(e.x), cy = World.toTile(e.y);
        for(int pass = 0; pass < 2; pass++) {
            for(int dy = tr; dy >= -tr; dy--) {
                for(int dx = -tr; dx <= tr; dx++) {
                    Tile tile = world.tile(cx + dx, cy + dy);
                    if(tile == null || tile.block() != Blocks.air || Mathf.randomSeed(tile.pos() * 7L + e.id) > 0.6f) continue;
                    float wx = tile.worldx(), wy = tile.worldy(), d = Mathf.dst(wx, wy, e.x, e.y);
                    if(d > r) continue;
                    float lt = e.time - d / r * 16f;
                    if(lt <= 0f) continue;
                    float lift = hold <= 0f ? lt < 36f ? Mathf.sin(lt / 36f * Mathf.PI) : 0f : Interp.pow2Out.apply(Mathf.clamp(lt / 10f)) * (1f - Interp.smooth.apply(Mathf.clamp((lt - 10f) / (hold * Mathf.randomSeed(tile.pos() + 3L, 0.55f, 1f)))));
                    if(lift <= 0.001f) continue;

                    Floor floor = tile.floor();
                    if(floor.variantRegions == null || floor.variantRegions.length == 0) continue;
                    float h = lift * (8f - d / r * 4f) * Mathf.randomSeed(tile.pos(), 0.6f, 1.3f), push = hold <= 0f ? 0f : lift * Mathf.randomSeed(tile.pos() + 5L, 1f, 3.5f) * (1f - d / r * 0.5f), away = Angles.angle(e.x, e.y, wx, wy);
                    float sx = wx + trnsx(away, push), sy = wy + trnsy(away, push);
                    float tilt = Mathf.randomSeedRange(tile.pos() + e.id, 12f) * lift, size = tilesize * (1f + h * 0.03f), top = sy + h * 0.6f;

                    if(pass == 0) {
                        color(Color.black, 0.6f * lift);
                        Fill.square(wx, wy, tilesize / 2f);
                        color(Pal.shadow, Pal.shadow.a * 1.5f * lift);
                        Fill.square(sx - h * 0.4f, sy - h * 0.4f, size / 2f, tilt);
                    }else{
                        color(Tmp.c1.set(floor.mapColor).mul(0.55f));
                        Fill.rect(sx, (sy - tilesize / 2f + top - size / 2f) / 2f, size, top - size / 2f - (sy - tilesize / 2f), 0f);
                        color(Tmp.c1.set(Color.white).mul(1f + lift * 0.35f), 1f);
                        rect(floor.variantRegions[floor.variant(tile.x, tile.y)], sx, top, size, size, tilt);
                    }
                }
            }
        }
        reset();
    }).layer(Layer.debris);
    public static final Effect impactDust = new Effect(60f, 160f, e -> {
        float r = e.rotation > 0f ? e.rotation : 30f, t = e.time, fade = Mathf.clamp(e.fout() * 3f);
        Tile under = world.tileWorld(e.x, e.y);
        Color dust = under == null ? CIPal.hematite : under.floor().mapColor;

        rand.setSeed(e.id);
        for(int i = 0; i < 20; i++) {
            float ang = i * 18f + rand.range(9f), dist = r * rand.random(0.7f, 1.1f) * Interp.pow3Out.apply(e.fin());
            color(Tmp.c1.set(dust).lerp(Color.white, rand.random(0.15f)).mul(rand.random(0.85f, 1.1f)), 0.8f * e.fout());
            Fill.circle(e.x + trnsx(ang, dist), e.y + trnsy(ang, dist), (2.5f + e.fin() * 6f) * rand.random(0.8f, 1.2f));
        }

        for(int i = 0; i < 12; i++) {
            float ang = rand.random(360f), speed = rand.random(0.3f, 0.8f), vz = rand.random(0.8f, 1.6f), sz = rand.random(0.6f, 1.2f);
            float t1 = 2f * vz / 0.08f, vz2 = vz * 0.35f, tb = Mathf.clamp(t - t1, 0f, 2f * vz2 / 0.08f);
            float h = t < t1 ? vz * t - 0.04f * t * t : vz2 * tb - 0.04f * tb * tb, dist = speed * (Math.min(t, t1) + tb * 0.5f);
            float px = e.x + trnsx(ang, dist), py = e.y + trnsy(ang, dist);
            color(Pal.shadow, Pal.shadow.a * fade);
            Fill.circle(px - h * 0.4f, py - h * 0.4f, sz);
            color(Tmp.c1.set(dust).mul(0.6f), fade);
            Fill.poly(px, py + h * 0.6f, 4, sz * 1.2f, rand.random(360f) + t * 8f);
        }
        reset();
    }).layer(Layer.blockOver);

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