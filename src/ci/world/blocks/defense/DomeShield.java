package ci.world.blocks.defense;

import arc.func.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.content.*;
import mindustry.entities.Units;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.world.blocks.defense.*;

import static mindustry.Vars.*;

public class DomeShield extends ForceProjector{

    protected static DomeShield paramCircleBlock;
    protected static CircleBuild paramCircleEntity;
    protected static final Cons<Bullet> circleShieldConsumer = bullet -> {
        if(bullet.team != paramCircleEntity.team && bullet.type.absorbable && !bullet.absorbed &&
                bullet.within(paramCircleEntity, paramCircleEntity.realRadius())){
            bullet.absorb();
            paramCircleBlock.absorbEffect.at(bullet);
            paramCircleEntity.hit = 1f;
            paramCircleEntity.buildup += bullet.damage();
        }
    };

    protected static final Cons<Unit> unitConsumer = unit -> {
        float overlapDst = (unit.hitSize/2f + paramCircleEntity.realRadius()) - unit.dst(paramCircleEntity);

        if(overlapDst > 0){
            if(overlapDst > unit.hitSize * 1.5f){
                unit.kill();
            }else{
                unit.vel.setZero();
                unit.move(Tmp.v1.set(unit).sub(paramCircleEntity).setLength(overlapDst + 0.01f));

                if(Mathf.chanceDelta(0.12f * Time.delta)){
                    Fx.circleColorSpark.at(unit.x, unit.y, paramCircleEntity.team.color);
                }
            }
        }
    };

    public DomeShield(String name){
        super(name);
        sides = 24;
        shieldRotation = 0f;
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        Draw.color(Pal.gray);
        Lines.stroke(3f);
        Lines.poly(x * tilesize + offset, y * tilesize + offset, sides, radius, shieldRotation);
        Draw.color(player.team().color);
        Lines.stroke(1f);
        Lines.poly(x * tilesize + offset, y * tilesize + offset, sides, radius, shieldRotation);
        Draw.color();
    }

    public class CircleBuild extends ForceBuild{

        @Override
        public void onRemoved(){
        }

        @Override
        public void updateTile(){
            boolean phaseValid = itemConsumer != null && itemConsumer.efficiency(this) > 0;

            phaseHeat = Mathf.lerpDelta(phaseHeat, Mathf.num(phaseValid), 0.1f);

            if(phaseValid && !broken && timer(timerUse, phaseUseTime) && efficiency > 0){
                consume();
            }

            if(broken){
                radscl = Mathf.approachDelta(radscl, 0f, 2.5f);
            }else{
                radscl = Mathf.lerpDelta(radscl, warmup, 0.08f);
            }


            if(Mathf.chanceDelta(buildup / shieldHealth * 0.1f)){
                mindustry.content.Fx.reactorsmoke.at(x + Mathf.range(tilesize / 2f), y + Mathf.range(tilesize / 2f));
            }

            warmup = Mathf.lerpDelta(warmup, efficiency, 0.1f);

            if(buildup > 0){
                float scale = !broken ? cooldownNormal : cooldownBrokenBase;

                if(coolantConsumer != null && coolantConsumer.efficiency(this) > 0){
                    coolantConsumer.update(this);
                    scale *= (cooldownLiquid * (1f + (liquids.current().heatCapacity - 0.4f) * 0.9f));
                }

                buildup -= delta() * scale;
            }

            if(broken && buildup <= 0){
                broken = false;
            }

            if(buildup >= shieldHealth + phaseShieldBoost * phaseHeat && !broken){
                broken = true;
                buildup = shieldHealth;
                breakSound.at(x, y);
            }

            if(hit > 0f){
                hit -= 1f / 5f * Time.delta;
            }

            deflectBullets();
        }

        @Override
        public void deflectBullets(){
            float realRadius = realRadius();
            if(realRadius > 0 && !broken){
                paramCircleBlock = DomeShield.this;
                paramCircleEntity = this;
                Groups.bullet.intersect(x - realRadius, y - realRadius, realRadius * 2f, realRadius * 2f, circleShieldConsumer);
                Units.nearbyEnemies(team, x, y, realRadius + 10f, unitConsumer);
            }
        }

        public boolean absorbExplosion(float ex, float ey, float damage){
            boolean absorb = !broken && Mathf.within(x, y, ex, ey, realRadius());
            if(absorb){
                absorbEffect.at(ex, ey);
                hit = 1f;
                buildup += damage * 2f;
            }
            return absorb;
        }

        @Override
        public void drawShield(){
            float radius = realRadius();
            if(radius > 0.001f){
                Draw.z(Layer.shields);
                Draw.color(team.color, Color.white, Mathf.clamp(hit));

                if(renderer.animateShields){
                    Fill.poly(x, y, sides, radius, shieldRotation);
                }else{
                    Lines.stroke(1.5f);
                    Draw.alpha(0.09f + Mathf.clamp(0.08f * hit));
                    Fill.poly(x, y, sides, radius, shieldRotation);
                    Draw.alpha(1f);
                    Lines.poly(x, y, sides, radius, shieldRotation);
                }
            }
            Draw.reset();
        }
    }
}