package ci.content;

import arc.graphics.Color;
import mindustry.ai.types.BuilderAI;
import mindustry.ai.types.FlyingAI;
import mindustry.content.Fx;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.meta.BlockFlag;

public class CIUnits {
    public static UnitType
    //core units
    falcon, T1,

    //mech
    arrow;

    public static void load() {

        falcon  = new UnitType("T2") {{
            aiController = BuilderAI::new;
            isEnemy = false;
            constructor = UnitEntity::create;
            lowAltitude = true;
            flying = true;
            mineTier = 1;
            mineSpeed = 7f;
            drag = 0.05f;
            accel = 0.1f;
            speed = 4.1f;
            rotateSpeed = 15f;
            buildSpeed = 0.90f;
            itemCapacity = 40;
            health = 360;
            engineOffset = 7f;
            hitSize = 9f;
            alwaysUnlocked = true;
            outlineColor = CIPal.dustyOutline;
            outlineRadius = 3;

            weapons.add(new Weapon(){{
                top = false;
                reload = 45f;
                mirror = false;
                x = 0f;
                y = 0f;
                shootY = -0.5f;
                rotate = false;
                shoot.shots = 2;
                shoot.shotDelay = 3f;

                ejectEffect = Fx.none;

                bullet = new MissileBulletType(3.6f, 24){{
                    width = 5f;
                    height = 8f;
                    lifetime = 50f;

                    homingPower = 0.09f;
                    weaveMag = 2.5f;
                    weaveScale = 2;
                    trailLength = 4;
                    trailWidth = 1f;
                    hitColor = backColor = trailColor = Color.valueOf("ffd37f");
                    trailSinScl = 2.5f;
                    trailSinMag = 0.5f;
                    trailEffect = Fx.none;
                    despawnShake = 0f;

                    shootSound = Sounds.missile;
                    shootEffect = Fx.shootSmall;
                    smokeEffect = Fx.shootSmallSmoke;
                    buildingDamageMultiplier = 0.001f;
                }};
            }});
        }};

        //mech
        arrow = new UnitType("arrow"){{
            constructor = MechUnit::create;
            canBoost = true;
            boostMultiplier = 1.5f;
            speed = 0.40f;
            hitSize = 9f;
            health = 230f;
            buildSpeed = 0.5f;
            armor = 2f;
            range = 45f;
            outlineColor = CIPal.dustyOutline;

            weapons.add(new Weapon("ci-arrow-weapon"){{
                reload = 6f;
                x = -4.6f;
                top = false;
                ejectEffect = Fx.casing1;

                bullet = new BasicBulletType(3.4f, 8){{
                    //recoil = 0.1f;
                    height = 6;
                    width = 3;
                    lifetime = 26f;

                    hitColor = backColor = trailColor = Color.valueOf("ffd37f");
                    trailLength = 6;
                    trailWidth = 1f;

                    shootEffect = Fx.none;
                    trailEffect = Fx.none;
                }};
            }});
        }};
    }
}