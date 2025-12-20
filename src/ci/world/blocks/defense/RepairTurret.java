package ci.world.blocks.defense;

import arc.*;
import arc.audio.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.logic.*;
import mindustry.type.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.meta.*;

import static mindustry.Vars.*;
import static mindustry.world.blocks.units.RepairTurret.drawBeam;

public class RepairTurret extends Turret{
    public final int timerTarget = timers++;
    public float retargetTime = 5f;

    public float shootCone = 15f;
    public float shootLength = 5f;
    public float reload = 250f;
    public float healPercent = 12f;
    public float beamWidth = 0.7f;
    public float pulseRadius = 6f;
    public float pulseStroke = 2f;
    public float widthSinMag = 0.11f;
    public float widthSinScl = 4f;

    public Color laserColor = Color.valueOf("98ffa9");
    public Color laserTopColor = Color.white.cpy();
    public Color healColor = Pal.heal;
    public Effect healEffect = Fx.healBlockFull;

    public Sound shootSound = Sounds.spellLoop;
    public float shootSoundVolume = 0.9f;

    public TextureRegion laser, laserEnd, laserTop, laserTopEnd;

    public RepairTurret(String name){
        super(name);
        rotateSpeed = 10f;
        coolantMultiplier = 1f;
        envEnabled |= Env.space;
    }

    @Override
    public void load(){
        super.load();
        laser = Core.atlas.find("laser-white");
        laserEnd = Core.atlas.find("laser-white-end");
        laserTop = Core.atlas.find("laser-top");
        laserTopEnd = Core.atlas.find("laser-top-end");
    }

    @Override
    public void setStats(){
        super.setStats();
        stats.add(Stat.repairTime, (int)(100f / healPercent * reload / 60f), StatUnit.seconds);
    }

    @Override
    public void init(){
        super.init();
        updateClipRadius(range + tilesize);
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        super.drawPlace(x, y, rotation, valid);
        Drawf.dashCircle(x * tilesize + offset, y * tilesize + offset, range, laserColor);
    }

    public class RepairTurretBuild extends TurretBuild{
        public @Nullable Building target;
        public float lastX, lastY, heat, strength;
        public float charge = Mathf.random(reload);
        public float coolantMultiplier = 1f;
        public Vec2 offset = new Vec2();

        @Override
        public void updateTile(){
            boolean canHeal = !checkSuppression();
            float eff = efficiency * coolantMultiplier;
            float edelta = eff * delta();

            if(timer(timerTarget, retargetTime)){
                if(target == null || !target.isValid() || !target.damaged() || target.isHealSuppressed()){
                    target = indexer.findTile(team, x, y, range, b -> b.damaged() && b != this && !b.isHealSuppressed(), true);
                }
            }

            if(target != null && coolant != null){
                float maxUsed = coolant.amount;
                Liquid liquid = liquids.current();
                float used = Math.min(Math.min(liquids.get(liquid), maxUsed * Time.delta),
                        Math.max(0, (1f / coolantMultiplier) / liquid.heatCapacity));

                liquids.remove(liquid, used);

                if(Mathf.chance(0.06 * used)){
                    coolEffect.at(x + Mathf.range(size * tilesize / 2f), y + Mathf.range(size * tilesize / 2f));
                }

                coolantMultiplier = 1f + (used * liquid.heatCapacity * coolantMultiplier);
            }

            boolean validTarget = target != null && target.within(this, range) && target.team == team &&
                    target.damaged() && !target.isHealSuppressed() && efficiency > 0.02f;

            if(validTarget){
                float dest = angleTo(target);
                rotation = Angles.moveToward(rotation, dest, rotateSpeed * edelta);
                lastX = target.x;
                lastY = target.y;

                if(Angles.within(rotation, dest, shootCone)){
                    strength = Mathf.lerpDelta(strength, 1f, 0.1f);
                    heat = Mathf.lerpDelta(heat, 1f, 0.08f);
                    charge += heat * delta();

                    if(charge >= reload && canHeal){
                        target.heal(target.maxHealth() * healPercent / 100f * eff);
                        target.recentlyHealed();
                        healEffect.at(target.x, target.y, 0f, healColor, target.block);
                        charge = 0f;
                    }

                    if(!headless && heat > 0.01f){
                        control.sound.loop(shootSound, this, shootSoundVolume);
                    }
                }else{
                    strength = Mathf.lerpDelta(strength, 0, 0.1f);
                    heat = Mathf.lerpDelta(heat, 0, 0.08f);
                }
            }else{
                strength = Mathf.lerpDelta(strength, 0, 0.1f);
                heat = Mathf.lerpDelta(heat, 0, 0.08f);
            }
        }

        @Override
        public boolean shouldConsume(){
            return super.shouldConsume() && target != null;
        }

        @Override
        public double sense(LAccess sensor){
            if(sensor == LAccess.progress) return Mathf.clamp(charge / reload);
            return super.sense(sensor);
        }

        @Override
        public void draw(){
            drawer.draw(this);

            if(heat > 0.01f && !isPayload()){
                Draw.z(Layer.bullet);
                float ang = angleTo(lastX, lastY);

                drawBeam(
                        x + Angles.trnsx(ang, shootLength),
                        y + Angles.trnsy(ang, shootLength),
                        rotation, 0, id, target, team, strength * efficiency,
                        pulseStroke, pulseRadius,
                        beamWidth + Mathf.absin(widthSinScl, widthSinMag),
                        Tmp.v1.set(lastX, lastY), offset,
                        laserColor, laserTopColor,
                        laser, laserEnd, laserTop, laserTopEnd
                );
            }
        }

        @Override
        public void drawSelect(){
            Drawf.dashCircle(x, y, range, laserColor);
        }

        @Override
        public boolean canControl(){
            return false;
        }

        @Override
        public void write(Writes write){
            super.write(write);
            write.f(rotation);
            write.f(charge);
            write.f(heat);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            rotation = read.f();
            charge = read.f();
            heat = read.f();
        }
    }
}