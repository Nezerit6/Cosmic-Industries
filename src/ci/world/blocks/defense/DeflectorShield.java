package ci.world.blocks.defense;

import arc.*;
import arc.func.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.graphics.gl.*;
import arc.math.*;
import arc.struct.*;
import arc.util.*;
import ci.content.CIPal;
import mindustry.content.*;
import mindustry.entities.Units;
import mindustry.game.EventType;
import mindustry.gen.*;
import mindustry.graphics.*;

public class DeflectorShield extends DomeShield{
    public float repelForce = 0.15f;
    public float maxRepelForce = 1.5f;
    public float shieldDamageMultiplier = 0.7f;
    public boolean repelBullets = true;
    public boolean repelUnits = true;
    public Color shieldColor = CIPal.darkPink;

    private static final FrameBuffer fieldBuffer = new FrameBuffer();
    public static final Seq<Runnable> shieldRuns = new Seq<>();

    {
        Events.run(EventType.Trigger.draw, () -> {
            fieldBuffer.resize(Core.graphics.getWidth(), Core.graphics.getHeight());
            Seq<Runnable> buffer = shieldRuns.copy();
            shieldRuns.clear();

            Draw.draw(Layer.shields, () -> {
                Draw.flush();
                fieldBuffer.begin(Color.clear);
                buffer.each(Runnable::run);
                fieldBuffer.end();

                Draw.blend(Blending.additive);
                TextureRegion region = new TextureRegion(fieldBuffer.getTexture());
                Draw.rect(region, Core.camera.position.x, Core.camera.position.y, Core.camera.width, -Core.camera.height);
                Draw.blend();
                Draw.flush();
                Draw.color();
            });
        });
    }

    protected static DeflectorShield paramRepelBlock;
    protected static DeflectorBuild paramRepelEntity;

    protected static final Cons<Bullet> repelConsumer = bullet -> {
        if(bullet.team != paramRepelEntity.team && bullet.type.absorbable && !bullet.absorbed &&
                bullet.within(paramRepelEntity, paramRepelEntity.realRadius())){

            if(!paramRepelEntity.processedBullets.contains(bullet.id)){
                paramRepelEntity.buildup += bullet.damage() * paramRepelBlock.shieldDamageMultiplier;
                paramRepelEntity.hit = 1f;
                paramRepelEntity.processedBullets.add(bullet.id);
                Fx.absorb.at(bullet.x, bullet.y, paramRepelEntity.team.color);
            }

            float dist = bullet.dst(paramRepelEntity);
            float radius = paramRepelEntity.realRadius();
            float penetration = 1f - (dist / radius);
            float force = Mathf.lerp(paramRepelBlock.repelForce, paramRepelBlock.maxRepelForce, penetration);
            float angle = Angles.angle(paramRepelEntity.x, paramRepelEntity.y, bullet.x, bullet.y);

            bullet.vel.add(Angles.trnsx(angle, force), Angles.trnsy(angle, force));
        }
    };

    protected static final Cons<Unit> unitRepelConsumer = unit -> {
        float radius = paramRepelEntity.realRadius();
        float overlapDst = (unit.hitSize / 2f + radius) - unit.dst(paramRepelEntity);

        if(overlapDst > 0){
            float penetration = overlapDst / radius;
            float force = Mathf.lerp(paramRepelBlock.repelForce, paramRepelBlock.maxRepelForce, penetration);

            unit.vel.setZero();
            unit.move(Tmp.v1.set(unit).sub(paramRepelEntity).setLength(force));

            if(Mathf.chanceDelta(0.12f * Time.delta)){
                Fx.circleColorSpark.at(unit.x, unit.y, paramRepelEntity.team.color);
            }
        }
    };

    public DeflectorShield(String name){
        super(name);
    }

    public class DeflectorBuild extends CircleBuild{
        public IntSet processedBullets = new IntSet();
        private float cleanupTimer = 0f;

        @Override
        public void updateTile(){
            super.updateTile();

            cleanupTimer += delta();
            if(cleanupTimer >= 60f){
                cleanupTimer = 0f;

                IntSet toRemove = new IntSet();
                processedBullets.each(id -> {
                    boolean exists = false;
                    for(Bullet b : Groups.bullet){
                        if(b.id == id){
                            exists = true;
                            break;
                        }
                    }
                    if(!exists) toRemove.add(id);
                });

                toRemove.each(processedBullets::remove);
            }
        }

        @Override
        public void deflectBullets(){
            float realRadius = realRadius();
            if(realRadius > 0 && !broken){
                paramRepelBlock = DeflectorShield.this;
                paramRepelEntity = this;

                if(paramRepelBlock.repelBullets){
                    Groups.bullet.intersect(
                            x - realRadius,
                            y - realRadius,
                            realRadius * 2f,
                            realRadius * 2f,
                            repelConsumer
                    );
                }

                if(paramRepelBlock.repelUnits){
                    Units.nearbyEnemies(team, x, y, realRadius + 10f, unitRepelConsumer);
                }
            }
        }

        @Override
        public void drawShield(){
            float radius = realRadius();
            if(radius > 0.001f){
                Color color = shieldColor;
                float hitAmount = hit;
                shieldRuns.add(() -> {
                    Draw.color(color, Color.white, Mathf.clamp(hitAmount) * 0.5f);
                    Fill.poly(x, y, sides, radius, shieldRotation);
                });
            }
        }
    }
}