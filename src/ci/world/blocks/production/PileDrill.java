package ci.world.blocks.production;

import arc.Core;
import arc.audio.Sound;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.io.*;
import ci.content.CIFx;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.gen.Sounds;
import mindustry.graphics.*;
import mindustry.type.Item;
import mindustry.world.blocks.production.Drill;

import static mindustry.Vars.tilesize;

public class PileDrill extends Drill {
    public static final int lift = 0, strike = 1, drill = 2, slow = 3, raise = 4, cool = 5;

    public float[] phaseTimes = {90f, 6f, 480f, 60f, 60f, 120f};
    public float liftScale = 0.35f, shadowOffset = 6f, spinSpeed = 9f;
    public float impactShake = 4f, impactVolume = 1f, drillShake = 0.6f;
    public float grindChance = 0.18f, coolEffectChance = 0.07f;
    public float heatAlpha = 0.65f;
    public Color heatColor = Color.valueOf("ff5512");
    public Sound impactSound = Sounds.drillImpact, impactCrackSound = Sounds.rockBreak;
    public Effect impactEffect = CIFx.pileStrike, grindEffect = CIFx.pileGrind, coolEffect = Fx.steam;

    public TextureRegion headRegion;

    public PileDrill(String name) {
        super(name);
        hardnessDrillMultiplier = 0f;
        ambientSoundVolume = 0.08f;
    }

    @Override
    public void init() {
        int tiles = size * size;
        drillTime = cycleTime() * 2f * tiles / (tiles + 1);
        super.init();
    }

    @Override
    public void load() {
        super.load();
        headRegion = Core.atlas.find(name + "-head");
    }

    @Override
    public TextureRegion[] icons() {
        return new TextureRegion[]{region, headRegion};
    }

    @Override
    public float getDrillTime(Item item) {
        return cycleTime() * 2f * returnCount / (returnCount + 1);
    }

    public float cycleTime() {
        float sum = 0f;
        for(float t : phaseTimes) sum += t;
        return sum;
    }

    public class PileDrillBuild extends DrillBuild {
        public int phase;
        public float phaseTime, mined, spin;

        public float phaseProgress() {
            return Mathf.clamp(phaseTime / phaseTimes[phase]);
        }

        public float height() {
            return phase == lift ? Interp.smooth.apply(phaseProgress()) : phase == strike ? 1f - Interp.pow3In.apply(phaseProgress()) : 0f;
        }

        public float spinFract() {
            return phase == drill ? 1f : phase == slow ? 1f - phaseProgress() : 0f;
        }

        public float heat() {
            return switch(phase) {
                case drill -> Interp.pow2Out.apply(phaseProgress());
                case slow, raise -> 1f;
                case cool -> 1f - Interp.pow2In.apply(phaseProgress());
                default -> 0f;
            };
        }

        public float headRadius() {
            return size * tilesize * 0.3f;
        }

        @Override
        public boolean shouldConsume() {
            return enabled && dominantItem != null && (phase != lift || phaseTime > 0f || items.total() < itemCapacity);
        }

        @Override
        public boolean shouldAmbientSound() {
            return efficiency > 0.01f && spinFract() > 0f;
        }

        @Override
        public float ambientVolume() {
            return super.ambientVolume() * spinFract();
        }

        @Override
        public void onProximityUpdate() {
            super.onProximityUpdate();
            if(dominantItem == null) {
                phase = lift;
                phaseTime = mined = 0f;
            }
        }

        @Override
        public float progress() {
            float done = phaseTime;
            for(int i = 0; i < phase; i++) done += phaseTimes[i];
            return Mathf.clamp(done / cycleTime());
        }

        @Override
        public void updateTile() {
            if(timer(timerDump, dumpTime / timeScale)) dump(dominantItem != null && items.has(dominantItem) ? dominantItem : null);

            if(dominantItem == null || dominantItems <= 0 || efficiency <= 0f) {
                lastDrillSpeed = 0f;
                warmup = Mathf.approachDelta(warmup, 0f, warmupSpeed);
                return;
            }

            float speed = Mathf.sqr(Mathf.lerp(1f, liquidBoostIntensity, optionalEfficiency) * efficiency), dt = delta() * speed;
            warmup = Mathf.approachDelta(warmup, 1f, warmupSpeed);
            lastDrillSpeed = (dominantItems + 1) / 2f / cycleTime() * speed;
            phaseTime += dt;
            spin += spinSpeed * spinFract() * dt;

            if(wasVisible) {
                float f = spinFract(), a = Mathf.random(360f), r = headRadius();
                if(Mathf.chanceDelta(grindChance * f)) grindEffect.at(x + Angles.trnsx(a, r), y + Angles.trnsy(a, r), a, dominantItem.color);
                if(f > 0f) Effect.shake(drillShake * f, 2f, this);
                if(phase == cool && Mathf.chanceDelta(coolEffectChance * heat())) coolEffect.at(x + Mathf.range(r * 0.6f), y + Mathf.range(r * 0.6f));
            }

            if(phaseTime < phaseTimes[phase]) return;
            phaseTime -= phaseTimes[phase];

            if(phase == strike && wasVisible) {
                impactEffect.at(x, y, headRadius(), dominantItem.color);
                impactSound.at(x, y, Mathf.random(0.8f, 1f), impactVolume);
                impactCrackSound.at(x, y, Mathf.random(0.7f, 0.9f), impactVolume * 0.6f);
                Effect.shake(impactShake, impactShake * 4f, this);
            } else if(phase == slow) {
                mined += Mathf.random(1, dominantItems);
                int out = 0;
                for(; out < (int)mined && items.total() < itemCapacity; out++) offload(dominantItem);
                mined -= out;
                if(out > 0 && wasVisible) drillEffect.at(x, y, dominantItem.color);
            }

            phase = (phase + 1) % phaseTimes.length;
            if(phase == lift && items.total() >= itemCapacity) phaseTime = 0f;
        }

        @Override
        public void draw() {
            Draw.rect(region, x, y);
            drawDefaultCracks();

            float h = height();
            Draw.scl(1f + h * liftScale);

            if(h > 0.001f) {
                Draw.z(Layer.blockOver);
                Draw.color(Pal.shadow, Pal.shadow.a * h);
                Draw.rect(headRegion, x - shadowOffset * h, y - shadowOffset * h, spin);
                Draw.color();
            }

            Draw.z(Layer.blockOver + 0.1f);
            Drawf.spinSprite(headRegion, x, y, spin);
            if(dominantItem != null && drawMineItem) {
                Draw.color(dominantItem.color);
                Drawf.spinSprite(itemRegion, x, y, spin);
            }

            float heat = heat() * heatAlpha;
            if(heat > 0.001f) {
                float r = Mathf.mod(spin, 90f), t = r / 90f;
                Draw.blend(Blending.additive);
                Draw.color(heatColor, heat * (1f - t));
                Draw.rect(headRegion, x, y, r);
                Draw.alpha(heat * t);
                Draw.rect(headRegion, x, y, r - 90f);
                Draw.blend();
                Drawf.light(x, y, headRadius() * 3f, heatColor, heat);
            }

            Draw.reset();
            Draw.z(Layer.block);
        }

        @Override
        public void write(Writes write) {
            super.write(write);
            write.b(phase);
            write.f(phaseTime);
            write.f(mined);
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            if(revision >= 2) {
                phase = read.b();
                phaseTime = read.f();
                mined = read.f();
            }
        }

        @Override
        public byte version() {
            return 2;
        }
    }
}