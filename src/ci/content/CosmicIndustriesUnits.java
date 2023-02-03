package ci.content;

import arc.graphics.Color;
import mindustry.ai.types.BuilderAI;
import mindustry.content.Fx;
import mindustry.entities.bullet.ArtilleryBulletType;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.bullet.MissileBulletType;
import mindustry.gen.MechUnit;
import mindustry.gen.Sounds;
import mindustry.gen.UnitEntity;
import mindustry.graphics.Pal;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import mindustry.type.weapons.RepairBeamWeapon;

public class CosmicIndustriesUnits {
    public static UnitType
    //core units
    falcon, hawk,

    //mech
    arrow /**needle its T2*/;

    public static void load() {

        falcon  = new UnitType("falcon") {{
            aiController = BuilderAI::new;
            isEnemy = false;
            constructor = UnitEntity::create;
            lowAltitude = true;
            flying = true;
            mineTier = 1;
            mineSpeed = 7f;
            drag = 0.05f;
            accel = 0.1f;
            speed = 3.6f;
            rotateSpeed = 15f;
            buildSpeed = 0.90f;
            itemCapacity = 40;
            health = 360;
            engineOffset = 7f;
            hitSize = 9f;
            alwaysUnlocked = true;
            outlineColor = Color.darkGray;
            outlineRadius = 3;

            weapons.add(new Weapon(){{
                top = false;
                reload = 45f;
                mirror = false;
                x = 0f;
                y = 0f;
                rotate = false;
                shoot.shots = 2;
                shoot.shotDelay = 3f;

                ejectEffect = Fx.none;

                bullet = new MissileBulletType(3.6f, 24){{
                    width = 5f;
                    height = 8f;
                    lifetime = 50f;
                    recoil = 0.35f;

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

        hawk  = new UnitType("hawk") {{
            aiController = BuilderAI::new;
            isEnemy = false;
            constructor = UnitEntity::create;
            lowAltitude = true;
            flying = true;
            mineTier = 1;
            mineSpeed = 5f;
            drag = 0.05f;
            accel = 0.05f;
            speed = 4f;
            rotateSpeed = 12f;
            buildSpeed = 1f;
            itemCapacity = 20;
            health = 20;
            engineOffset = 3f;
            hitSize = 5f;
            alwaysUnlocked = true;
            outlineColor = Color.black;
            outlineRadius = 3;

            weapons.add(new RepairBeamWeapon(){{
                widthSinMag = 0.11f;
                reload = 20f;
                x = 0f;
                y = 1f;
                rotate = false;
                shootY = 0f;
                beamWidth = 0.7f;
                repairSpeed = 3.1f;
                fractionRepairSpeed = 0.06f;
                aimDst = 0f;
                shootCone = 15f;
                mirror = false;

                targetUnits = false;
                targetBuildings = true;
                autoTarget = false;
                controllable = true;
                laserColor = Pal.accent;
                healColor = Pal.accent;

                bullet = new BulletType(){{
                    maxRange = 60f;
                }};
            }});
        }};

        //mech
        arrow = new UnitType("needle"){{
            constructor = MechUnit::create;
            canBoost = true;
            boostMultiplier = 1.5f;
            speed = 0.40f;
            hitSize = 7f;
            health = 230f;
            buildSpeed = 0.5f;
            armor = 2f;
            outlineColor = Color.darkGray;

            weapons.add(new Weapon("ci-needle-weapon"){{
                reload = 5f;
                x = -4.6f;
                top = false;
                ejectEffect = Fx.casing1;

                bullet = new BasicBulletType(3.4f, 8){{
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