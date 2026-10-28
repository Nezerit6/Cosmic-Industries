package ci.world.blocks.storage;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;
import ci.content.*;
import ci.graphics.draw3d.*;
import ci.world.blocks.storage.CrashCore.*;
import mindustry.content.*;
import mindustry.gen.*;
import mindustry.graphics.*;

import static mindustry.Vars.*;

public class CILanding {
    static final int fall = 190, burnLen = 50, settle = 85;
    static final float openTick = fall - 50 - 18;
    public static final float duration = fall + settle;
    public final float[] dist = new float[fall + 1], vel = new float[fall + 1], thrust = new float[fall + 1], turn = new float[fall + 1];
    public final Seq<float[]> trails = new Seq<>();

    public final CrashCoreBuild b;
    public final CrashCore block;
    public final Vec3 cap = new Vec3(), capVel = new Vec3();
    public float drift = 650f, brakeLag, camDist, height, tx, ty, dir, jolt, joltV, lastBurn, camX, camY, camZ, kick, kickV, fireTimer, smokeTimer;
    public boolean falling;

    public CILanding(CrashCoreBuild b) {
        this.b = b;
        block = (CrashCore)b.block;
    }

    public float tick() {
        return renderer.getLandTimeIn() * duration;
    }

    public float sample(float[] table, float tick) {
        float f = Mathf.clamp(tick, 0f, fall);
        int i = (int)f;
        return Mathf.lerp(table[i], table[Math.min(i + 1, fall)], f - i);
    }

    public Vec3 pos(float tick, Vec3 out) {
        float z = height * (1f - sample(dist, tick) / dist[fall]), r = drift * (z / height) * (z / height);
        return out.set(tx + Angles.trnsx(dir, r), ty + Angles.trnsy(dir, r), z);
    }

    public Vec3 velocity(float tick, Vec3 out) {
        pos(tick + 0.5f, out);
        float x = out.x, y = out.y, z = out.z;
        pos(tick - 0.5f, out);
        return out.set(x - out.x, y - out.y, z - out.z);
    }

    public void profile() {
        java.util.Arrays.fill(thrust, 0f);
        for(float t = fall - burnLen; t < fall - 10f; ) {
            float len = Mathf.random(6f, 10f), power = Mathf.random(0.55f, 1f);
            for(int i = (int)t; i <= Math.min(t + len, fall); i++) {
                thrust[i] = Math.max(thrust[i], power * Mathf.clamp(Math.min((i - t) / 2.5f, (t + len - i) / 3f)));
            }
            t += len + Mathf.random(4f, 9f);
        }
        float v = 0.65f, d = 0f, w = Mathf.random(2f, 3.2f) * Mathf.randomSign(), a = 0f;
        for(int i = 0; i <= fall; i++) {
            dist[i] = d;
            vel[i] = v;
            turn[i] = a;
            v = Math.max(v + 0.014f * (1f - v * v) - 0.05f * thrust[i], 0.3f);
            d += v;
            w *= 1f - 0.08f * thrust[i];
            a += w;
        }
        for(int i = 0; i <= fall; i++) turn[i] -= a;
    }

    public float burn(float tick) {
        return tick < 0f || tick > fall ? 0f : sample(thrust, tick);
    }

    public float heat(float tick) {
        float v = sample(vel, tick), zf = pos(tick, Tmp.v33).z / height;
        return v * v * Mathf.clamp(1.3f - zf) * Mathf.clamp(zf / 0.3f) * (1f - 0.8f * burn(tick));
    }

    public float damage(float tick) {
        return Mathf.clamp(1f - pos(tick, Tmp.v33).z / height / 0.3f);
    }

    public float rot(float tick) {
        return sample(turn, tick) + jolt * Mathf.clamp((fall - tick) / 8f);
    }

    public float gap() {
        return camDist * (0.6f + 0.4f * Mathf.clamp(1f - cap.z / (camDist * 3f)));
    }

    public float screenZ() {
        return Math.max(Core.graphics.getWidth(), Core.graphics.getHeight()) / (2f * (float)Math.tan(Perspective.fov / 2f * Mathf.degRad));
    }

    public Vec2 center(Vec2 out) {
        if(!falling) return out.set(tx, ty);
        pos(tick(), cap);
        return out.set(Perspective.drawPos(cap.x, cap.y, cap.z));
    }

    public void begin() {
        tx = b.x;
        ty = b.y;
        falling = true;
        profile();
        trails.clear();
        jolt = joltV = lastBurn = 0f;
        dir = Mathf.random(360f);
        fireTimer = smokeTimer = kick = kickV = 0f;
        camDist = screenZ() / Scl.scl(block.landZoomTo);
        height = camDist * 12f;
        renderer.blocks.updateShadow(b);
        pos(0f, cap);
        velocity(0f, capVel);
        camX = cap.x;
        camY = cap.y;
        brakeLag = 0f;
        camZ = cap.z + gap();
    }

    public float zoom() {
        Core.camera.position.set(camX, camY);
        return screenZ() / camZ * (1f + kick);
    }

    public void update() {
        float tick = tick();
        if(falling && tick >= fall) impact();
        if(falling) {
            pos(tick, cap);
            velocity(tick, capVel);
        } else {
            cap.set(tx, ty, 0f);
            capVel.setZero();
        }

        float follow = 1f - Mathf.pow(0.8f, Time.delta);
        brakeLag = (brakeLag + (falling ? burn(tick) * 3.5f * Time.delta : 0f)) * Mathf.pow(0.93f, Time.delta);
        camX += (cap.x - camX) * follow;
        camY += (cap.y - camY) * follow;
        camZ = cap.z + gap() + brakeLag;
        kickV += (-kick * 0.015f - kickV * 0.12f) * Time.delta;
        kick += kickV * Time.delta;

        if(!falling) {
            b.thrusterTime = 1f;
            return;
        }

        float heat = heat(tick), burn = burn(tick), hs = block.size * tilesize / 2f;
        fireTimer += 3f * heat * Time.delta;
        smokeTimer += (1.2f * heat + 0.7f * damage(tick)) * Time.delta;
        while(fireTimer >= 1f) {
            fireTimer -= 1f;
            trail(CIFx.landFire, 0.9f, Mathf.random(0.9f, 1.3f), 0f, hs * 0.9f);
        }
        while(smokeTimer >= 1f) {
            smokeTimer -= 1f;
            trail(CIFx.landSmoke, 0.8f, Mathf.random(0.9f, 1.3f), hs * 0.9f, hs * 1.3f);
        }

        joltV *= Mathf.pow(0.9f, Time.delta);
        jolt = (jolt + joltV * Time.delta) * Mathf.pow(0.985f, Time.delta);
        if(burn > 0.15f && lastBurn <= 0.15f) {
            joltV += Mathf.range(2.5f);
            renderer.shake(block.size * 1.3f, 16f);
            CISounds.capsuleThrust.at(camX, camY, Mathf.random(0.9f, 1.1f), 0.8f * Math.max(burn, burn(tick + 4f)));
            nozzles(tick, hs, true);
        }
        if(burn < 0.15f && lastBurn >= 0.15f) {
            nozzles(tick, hs, false);
            for(int i = 0; i < 4; i++) {
                Tmp.v1.trns(rot(tick) + i * 90f, hs + 2f);
                CIFx.landSmoke.at(cap.x + Tmp.v1.x, cap.y + Tmp.v1.y, 1.5f, track(0.7f));
            }
        }
        lastBurn = burn;
        if(burn <= 0f) return;
        b.landDust(0.5f * burn * Mathf.clamp(1.5f - cap.z / camDist));
    }

    public void nozzles(float tick, float hs, boolean start) {
        float k = Perspective.depthScale(cap.z);
        for(int i = 0; i < 4; i++) {
            float a = rot(tick) + i * 90f;
            Tmp.v1.trns(a, hs + 2f).add(cap.x, cap.y);
            Vec2 p = Perspective.drawPos(Tmp.v1.x, Tmp.v1.y, cap.z);
            if(start) {
                Fx.sparkShoot.at(p.x, p.y, a, b.team.color);
                Fx.shootBigSmoke.at(p.x, p.y, a);
            } else {
                Fx.smokePuff.at(p.x, p.y, a);
                Fx.fireSmoke.at(p.x + Angles.trnsx(a, 2f * k), p.y + Angles.trnsy(a, 2f * k));
            }
        }
    }

    public void trail(mindustry.entities.Effect effect, float keep, float size, float from, float to) {
        Tmp.v1.rnd(Mathf.random(from, to));
        effect.at(cap.x + Tmp.v1.x, cap.y + Tmp.v1.y, size, track(keep));
    }

    public float[] track(float keep) {
        float[] d = {cap.z, capVel.x * keep, capVel.y * keep, capVel.z * keep, Time.time, Float.MAX_VALUE};
        trails.add(d);
        return d;
    }

    public void impact() {
        falling = false;
        if(!net.client()) b.health = Math.max(b.health - b.maxHealth * Mathf.random(0.25f, 0.4f), 1f);
        trails.each(d -> d[5] = Time.time - d[4]);
        trails.clear();
        renderer.blocks.updateShadow(b);
        b.thrusterTime = 1f;
        kickV += 0.09f;
        float hs = block.size * tilesize / 2f;
        renderer.shake(block.size * 5f, 60f);
        CISounds.capsuleImpact.play();
        CIFx.tileUplift.at(tx, ty, hs + tilesize * 7f, 200f);
        CIFx.impactDust.at(tx, ty, hs * 9f);
        for(int i = 0; i < 10; i++) {
            float a = i * 36f + Mathf.range(12f);
            Tmp.v1.trns(a, hs).add(tx, ty);
            Fx.sparkShoot.at(Tmp.v1.x, Tmp.v1.y, a, CIPal.ember);
        }
        Fx.blockExplosionSmoke.at(tx, ty);
        for(int i = 0; i < 14; i++) {
            float ang = Mathf.random(360f), sp = Mathf.random(0.8f, 1.6f);
            Tmp.v1.trns(ang, hs * Mathf.random(0.9f, 1.2f));
            (i < 5 ? CIFx.landFire : CIFx.landSmoke).at(tx + Tmp.v1.x, ty + Tmp.v1.y, Mathf.random(1f, 1.4f), new float[]{2f, Angles.trnsx(ang, sp), Angles.trnsy(ang, sp), Mathf.random(0.2f, 0.6f), Time.time, Float.MAX_VALUE});
        }
    }

    public void draw(float x, float y) {
        if(!falling) return;

        float tick = tick(), rot = rot(tick), heat = heat(tick), burn = burn(tick);
        pos(tick, cap);
        TextureRegion region = block.region;
        float h = cap.z, w = region.width * region.scl(), ss = w * Draw3D.shadowScale(h);

        Draw.color(Pal.shadow, Pal.shadow.a * Draw3D.shadowAlpha(h));
        Draw.rect(region, cap.x + Angles.trnsx(225f, h), cap.y + Angles.trnsy(225f, h), ss, ss, rot);

        Vec2 p = Perspective.drawPos(cap.x, cap.y, h);
        float px = p.x, py = p.y, k = Perspective.depthScale(h);

        Draw.blend(Blending.additive);
        Fill.light(px, py, 16, w * k * 1.2f, Tmp.c1.set(CIPal.heat).a(0.55f * heat), Tmp.c2.set(CIPal.heat).a(0f));
        Draw.blend();

        float strength = k * burn * (0.95f + Mathf.absin(2f, 0.1f));
        for(int i = 0; i < 4 && burn > 0f; i++) {
            Tmp.v1.trns(i * 90f + rot, (block.size * tilesize / 2f + 1f) * k + strength * 2f);
            Draw.color(b.team.color);
            Fill.circle(px + Tmp.v1.x, py + Tmp.v1.y, 6f * strength);
            Tmp.v1.setLength((block.size * tilesize / 2f + 1f) * k + strength * 0.5f);
            Draw.color(Color.white);
            Fill.circle(px + Tmp.v1.x, py + Tmp.v1.y, 3.5f * strength);
        }

        float open = Mathf.clamp((tick - openTick) / 12f);
        Draw.color();
        Draw.scl(k);
        Draw.mixcol(CIPal.heat, 0.45f * heat);
        b.thrusters(px, py, rot, open);
        Drawf.spinSprite(region, px, py, rot);
        Draw.alpha(Interp.pow4In.apply(open));
        b.thrusters(px, py, rot, open);
        Draw.alpha(1f);
        if(block.teamRegions[b.team.id] == block.teamRegion) Draw.color(b.team.color);
        Drawf.spinSprite(block.teamRegions[b.team.id], px, py, rot);
        Draw.reset();
        Draw.scl();
    }

    public void end() {
        renderer.shakeTime = renderer.shakeIntensity = 0f;
        if(falling) impact();
    }
}