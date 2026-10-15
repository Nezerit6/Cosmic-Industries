package ci.world.blocks.production;

import arc.Core;
import arc.audio.Sound;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.game.Team;
import mindustry.gen.Sounds;
import mindustry.graphics.*;
import mindustry.world.blocks.production.Drill;

public class PileDrill extends Drill {
    public float liftTime = 90f, dropTime = 6f, grindTime = 240f, slowTime = 60f, raiseTime = 50f, coolTime = 110f;
    public float liftScale = 0.35f, shadowOffset = 6f;
    public float spinSpeed = 9f;
    public float impactShake = 2.5f, grindShake = 0.35f;
    public Color coolColor = Color.valueOf("ff7a3d");
    public Effect impactEffect = Fx.mineImpactWave, grindEffect = Fx.mineSmall, coolEffect = Fx.steam;
    public float grindEffectChance = 0.06f;
    public Sound impactSound = Sounds.drillImpact, chargeSound = Sounds.drillCharge;

    public TextureRegion headRegion;

    public PileDrill(String name) {
        super(name);
    }

    @Override
    public void load() {
        super.load();
        headRegion = Core.atlas.find(name + "-head");
    }

    @Override
    public TextureRegion[] icons() {
        return new TextureRegion[]{region, headRegion, teamRegions[Team.sharded.id]};
    }

    public float cycleTime() {
        return liftTime + dropTime + grindTime + slowTime + raiseTime + coolTime;
    }

    public class PileDrillBuild extends DrillBuild {
        public int phase;
        public float phaseTime, spin, mined;

        public float phaseLength() {
            return phaseLength(phase);
        }

        public float phaseLength(int index) {
            return switch(index) {
                case 0 -> liftTime;
                case 1 -> dropTime;
                case 2 -> grindTime;
                case 3 -> slowTime;
                case 4 -> raiseTime;
                default -> coolTime;
            };
        }

        public float phaseProgress() {
            return Mathf.clamp(phaseTime / phaseLength());
        }

        public float headLift() {
            float p = phaseProgress();
            switch(phase) {
                case 0: return Interp.smooth.apply(p);
                case 1: return 1f - Interp.pow3In.apply(p);
                case 4: return Interp.smooth.apply(p);
                case 5: return 1f;
                default: return 0f;
            }
        }

        public float spinSpeed() {
            if(phase == 2) return spinSpeed;
            if(phase == 3) return spinSpeed * (1f - phaseProgress());
            return 0f;
        }

        @Override
        public void updateTile() {
            if(timer(timerDump, dumpTime)) {
                dump(dominantItem != null && items.has(dominantItem) ? dominantItem : null);
            }

            if(dominantItem == null || dominantItems <= 0) {
                warmup = Mathf.approachDelta(warmup, 0f, warmupSpeed);
                return;
            }

            boolean full = items.total() >= itemCapacity;
            boolean active = efficiency > 0 && (!full || phase != 0 || phaseTime > 0f);
            warmup = Mathf.approachDelta(warmup, active ? 1f : 0f, warmupSpeed);
            if(!active) return;

            float delay = getDrillTime(dominantItem);
            float dt = edelta();
            phaseTime += dt;
            timeDrilled += dt;
            lastDrillSpeed = dominantItems * 60f / delay * efficiency;

            if(phase == 2 || phase == 3) {
                mined += dt * (phase == 2 ? 1f : 1f - phaseProgress()) * dominantItems * cycleTime() / delay / (grindTime + slowTime * 0.5f) / 2f;
            }
            spin += spinSpeed() * dt;

            if(phase == 2) {
                if(Mathf.chanceDelta(grindEffectChance)) {
                    grindEffect.at(x + Mathf.range(size * 3f), y + Mathf.range(size * 3f), dominantItem.color);
                }
                Effect.shake(grindShake, 2f, this);
            }

            if(phaseTime < phaseLength()) return;
            phaseTime = 0f;

            switch(phase) {
                case 0 -> chargeSound.at(x, y, Mathf.random(0.9f, 1.1f), 0.4f);
                case 1 -> {
                    impactEffect.at(x, y, dominantItem.color);
                    impactSound.at(x, y, Mathf.random(0.8f, 1f), 0.6f);
                    Effect.shake(impactShake, impactShake * 4f, this);
                    mined += dominantItems * cycleTime() / delay / 2f;
                }
                case 4 -> {
                    int amount = (int)mined;
                    mined -= amount;
                    for(int i = 0; i < amount && items.total() < itemCapacity; i++) {
                        offload(dominantItem);
                    }
                    drillEffect.at(x, y, dominantItem.color);
                }
                case 5 -> coolEffect.at(x, y);
            }

            phase = (phase + 1) % 6;
        }

        @Override
        public float progress() {
            float done = phaseTime;
            for(int i = 0; i < phase; i++) done += phaseLength(i);
            return Mathf.clamp(done / cycleTime());
        }

        @Override
        public void draw() {
            Draw.rect(region, x, y);
            drawDefaultCracks();

            float lift = headLift();
            float scl = 1f + lift * liftScale;
            float rot = spin;

            Draw.z(Layer.blockOver);
            Draw.color(Pal.shadow, Pal.shadow.a * lift);
            Draw.rect(headRegion, x - shadowOffset * lift, y - shadowOffset * lift, headRegion.width * headRegion.scl() * scl, headRegion.height * headRegion.scl() * scl, rot);
            Draw.color();

            Draw.z(Layer.blockOver + 0.1f);
            float w = headRegion.width * headRegion.scl() * scl, h = headRegion.height * headRegion.scl() * scl;
            Draw.rect(headRegion, x, y, w, h, rot);

            if(phase == 5) {
                float heat = 1f - phaseProgress();
                Draw.blend(Blending.additive);
                Draw.color(coolColor, heat * 0.6f);
                Draw.rect(headRegion, x, y, w, h, rot);
                Draw.blend();
                Draw.color();
            }

            TextureRegion team = teamRegions[this.team.id];
            if(team == teamRegion) Draw.color(this.team.color);
            Draw.rect(team, x, y, team.width * team.scl() * scl, team.height * team.scl() * scl, rot);
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