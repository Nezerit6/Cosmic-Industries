package ci.content.blocks;

import arc.graphics.Color;
import arc.math.Interp;
import ci.content.*;
import mindustry.content.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.part.DrawPart;
import mindustry.entities.part.RegionPart;
import mindustry.entities.pattern.ShootAlternate;
import mindustry.gen.Sounds;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.draw.*;

import static mindustry.type.ItemStack.with;

public class CIDefenseBlocks {
    public static Block
    // Walls
    ironWall, ironWallLarge,

    // Turrets
    shoker, chire, splitter, punisher, stormBringer, squall, serpent, arcflash, trident, eradication, radiant;

    public static void load() {
        // Walls
        ironWall = new Wall("ironWall"){{
            size = 1;
            health = 920;
            requirements(Category.defense, with(CIItems.iron, 6));
        }};

        ironWallLarge = new Wall("ironWallLarge"){{
            size = 2;
            health = 1840;
            requirements(Category.defense, with(CIItems.iron, 24));
        }};

        // Turrets
        shoker = new PowerTurret("shoker"){{
            requirements(Category.turret, with(CIItems.iron, 25));
            shootType = new LightningBulletType(){{
                damage = 5;
                lightningLength = 10;
                collidesAir = true;
                ammoMultiplier = 1f;
                buildingDamageMultiplier = 0.25f;

                lightningType = new BulletType(0.0001f, 0f){{
                    lifetime = Fx.lightning.lifetime;
                    hitEffect = Fx.hitLancer;
                    despawnEffect = Fx.none;
                    status = StatusEffects.shocked;
                    statusDuration = 10f;
                    hittable = false;
                    lightColor = Color.yellow;
                    collidesAir = true;
                    buildingDamageMultiplier = 0.25f;
                }};
            }};
            reload = 12.5f;
            shootCone = 40f;
            rotateSpeed = 4f;
            targetAir = true;
            range = 60f;
            shootEffect = Fx.lightningShoot;
            heatColor = Color.red;
            recoil = 1f;
            size = 1;
            health = 80;
            shootSound = Sounds.spark;
            consumePower(0.4f);
            drawer = new DrawTurret("based-");
        }};

        chire = new ItemTurret("chire"){{
            requirements(Category.turret, with(CIItems.iron, 24));
            reload = 3;
            ammo(
                    CIItems.iron, new BasicBulletType(10.0f,4){{
                        width = 6;
                        height = 8;
                        lifetime = 60;
                        ammoPerShot = 2;
                        collidesTeam = true;
                        collidesGround = true;
                    }}
            );

            drawer = new DrawTurret("based-"){
                {
                    parts.addAll(

                            new RegionPart("-side") {{
                                heatProgress = PartProgress.warmup;
                                progress = PartProgress.warmup;
                                mirror = true;
                                moveX = 2f * 4f / 3f;
                                moveY = -0.5f;
                                moveRot = -40f;
                                under = true;
                                heatColor = Color.red.cpy();

                            }});
                }};
            size = 1;
            recoil = 1;
            reload = 25;
            range = 110;
            health = 150;
            inaccuracy = 9;
            rotateSpeed = 12;
            coolant = consumeCoolant(0.1f);
            researchCostMultiplier = 0.10f;

        }};

        splitter = new ItemTurret("splitter"){{
            requirements(Category.turret, with(CIItems.iron, 50, Items.graphite, 10));
            health = 900;
            rotateSpeed = 1.4f;
            recoil = 0.5f;
            size = 2;
            range = 210;
            reload = 120f;

            ammo(
                    CIItems.iron, new ArtilleryBulletType(3.8f, 38){{
                        lifetime = 44f;
                        width = 12;
                        height = 12;

                        trailLength = 12;
                        trailWidth = 2f;
                        hitColor = backColor = trailColor = Color.valueOf("F6BB64");
                        trailSinScl = 2.5f;
                        trailSinMag = 0.5f;
                        trailEffect = Fx.none;
                        despawnShake = 0.4f;

                        fragBullets = 5;
                        fragVelocityMin = 0.9f;
                        fragRandomSpread = 360;
                        fragLifeMin = 0.9f;

                        splashDamageRadius = 20f * 0.75f;
                        splashDamage = 16f;
                        fragBullet = new BasicBulletType(3f, 12){{
                            lifetime = 14f;
                            pierceBuilding = true;
                            width = 7f;
                            height = 5f;
                            pierceCap = 3;

                            trailLength = 12;
                            trailWidth = 0.5f;
                            trailSinScl = 2.5f;
                            trailSinMag = 0.5f;
                            trailEffect = Fx.none;
                        }};
                    }},

                    Items.graphite, new ArtilleryBulletType(5f, 34){{
                        lifetime = 46f;
                        width = 12;
                        height = 12;

                        trailLength = 8;
                        trailWidth = 2f;
                        hitColor = backColor = trailColor = Color.valueOf("F6BB64");
                        trailSinScl = 2.5f;
                        trailSinMag = 0.5f;
                        trailEffect = Fx.none;
                        despawnShake = 0.4f;

                        fragBullets = 12;
                        fragVelocityMin = 0.9f;
                        fragRandomSpread = 360;
                        fragLifeMin = 0.9f;

                        splashDamageRadius = 16f * 0.75f;
                        splashDamage = 16f;
                        fragBullet = new BasicBulletType(5f, 8){{
                            lifetime = 12f;
                            pierceBuilding = true;
                            width = 6f;
                            height = 4f;
                            pierceCap = 2;
                        }};
                    }}
            );
            drawer = new DrawTurret("based-"){{
                parts.addAll(
                        new RegionPart("-barrel"){{
                            progress = PartProgress.recoil.curve(Interp.pow2In);
                            moveY = -1f * 4f / 4f;
                            heatColor = Color.valueOf("f03b0e");
                            mirror = false;
                        }},
                        new RegionPart("-front"){{
                            heatProgress = PartProgress.warmup;
                            progress = PartProgress.warmup;
                            mirror = true;
                            moveX = -0.5f * 4f / 3f;
                            moveY = 0f;
                            moveRot = 0f;
                            under = true;
                        }});
            }};
        }};

        punisher = new LiquidTurret("punisher") {{
            requirements(Category.turret, with(Items.copper, 110, Items.lead, 65, Items.titanium, 35, Items.silicon, 20));
            health = 430;
            rotateSpeed = 4.2f;
            recoil = 3f;
            size = 2;
            range = 205;
            reload = 120f;
            shootY = 6.5f;
            heatColor = Color.valueOf("afeeee");
            inaccuracy = 5;
            liquidCapacity = 60;
            squareSprite = false;
            extinguish = false;
            targetAir = false;
            shootSound = CISounds.bigLaserShoot;
            loopSound = Sounds.none;
            shootEffect = Fx.none;
            smokeEffect = CIFx.laserSparks;
            moveWhileCharging = false;
            accurateDelay = false;
            shoot.firstShotDelay = 60f;
            consumePower(3.6f);

            ammo(
                    Liquids.cryofluid, new BasicBulletType(12.6f, 36) {{
                        //despawnEffect = Fx.colorSpark;
                        hitEffect = Fx.none;
                        lifetime = 17.5f;
                        width = 4;
                        height = 28;
                        //splashDamageRadius = 32f * 0.50f;
                        //splashDamage = 20f;
                        chargeEffect = new MultiEffect(Fx.lancerLaserCharge, Fx.lancerLaserChargeBegin);
                        //statusDuration = 240f;
                        hitColor = backColor = trailColor = Color.valueOf("afeeee");
                        trailLength = 3;
                        trailWidth = 1.9f;
                        homingPower = 0.03f;
                        homingDelay = 2f;
                        homingRange = 60f;
                        ammoMultiplier = 2f / 10f;
                        collidesAir = false;
                        fragBullets = 1;

                        fragBullet = new BasicBulletType(20f, 20) {{
                            hitEffect = Fx.none;
                            collidesAir = false;
                            splashDamageRadius = 20f * 0.50f;
                            //chargeEffect = new MultiEffect(Fx.lancerLaserCharge, Fx.lancerLaserChargeBegin);
                            status = StatusEffects.freezing;
                            statusDuration = 160f;
                            frontColor = hitColor = backColor = trailColor = Color.valueOf("afeeee");
                        }};
                    }}
            );

            drawer = new DrawTurret("based-") {{
                parts.addAll(
                        new RegionPart("-nozzle") {{
                            progress = DrawPart.PartProgress.warmup;
                            heatProgress = DrawPart.PartProgress.charge;
                            mirror = true;
                            under = false;
                            moveRot = 7f;
                            moves.add(new DrawPart.PartMove(DrawPart.PartProgress.recoil, 0f, 0f, -30f));
                            heatColor = Color.valueOf("afeeee");
                        }}
                );
            }};
        }};

        stormBringer = new PowerTurret("stormBringer"){{
            requirements(Category.turret, with(CIItems.iron, 1));
            size = 2;
            range = 130;
            recoil = 2f;
            reload = 420f;
            health = 600;
            inaccuracy = 14;
            rotateSpeed = 7;

            //accurateDelay = false;
            shoot.shots = 32;
            shoot.shotDelay = 4;
            shoot.firstShotDelay = 15f;

            shootEffect = Fx.sparkShoot;
            shootSound = Sounds.lasershoot;

            consumePower(1.4f);
            coolant = consumeCoolant(0.2f);

            shootType = new LaserBoltBulletType(6, 13){{
                knockback = 0.3f;
                lifetime = 20f;
                backColor = Pal.heal;
                frontColor = Color.white;
                status = StatusEffects.corroded;
                statusDuration = 240f;
                smokeEffect = Fx.none;
                hitEffect = despawnEffect = Fx.hitLaser;
                hitColor = trailColor = Pal.heal;
                trailLength = 2;
                trailWidth = 1.8f;
            }};

            drawer = new DrawTurret("based-") {{
                parts.addAll(
                        new RegionPart("-side"){{
                            progress = PartProgress.warmup;
                            mirror = true;
                            under = false;
                            moveY = 4.5f;
                            moves.add(new PartMove(PartProgress.heat, 0f, -4.5f, 0f));
                        }}
                );
            }};
        }};

        squall = new ItemTurret("squall"){{
            requirements(Category.turret, with(Items.copper, 90, Items.lead, 55, Items.titanium, 40, Items.silicon, 30));
            ammo(
                    Items.graphite, new BasicBulletType(5f, 9){{
                        width = 10f;
                        height = 16f;
                        shootEffect = Fx.shootBig;
                        reloadMultiplier = 0.9f;
                        ammoMultiplier = 2;

                        incendChance = 0.01f;
                        incendSpread = 0.2f;
                        incendAmount = 1;
                        collideTerrain = true;
                    }},
                    Items.silicon, new BasicBulletType(3.1f, 13){{
                        width = 10f;
                        height = 16f;
                        shootEffect = Fx.shootBig;
                        reloadMultiplier = 1.25f;
                        ammoMultiplier = 1;
                        lifetime = 90f;

                        incendChance = 0.03f;
                        incendSpread = 0.4f;
                        incendAmount = 1;
                        collideTerrain = true;
                        collidesAir = false;
                    }}
            );
            targetAir = false;
            reload = 80;
            recoilTime = reload * 2f;
            coolantMultiplier = 0.5f;
            ammoUseEffect = Fx.casing4;
            range = 200f;
            inaccuracy = 2.5f;
            recoil = 3f;
            shoot = new ShootAlternate(7f);
            size = 2;
            shootCone = 24f;
            shootSound = Sounds.shootBig;
            shoot.shots = 2;
            ammoPerShot = 2;
            rotateSpeed = 4.3f;

            health = 460;
            coolant = consumeCoolant(0.2f);

            drawer = new DrawTurret("based-");
        }};

        //WIIIIIIIIIIIIIP
        serpent = new PowerTurret("serpent"){{
            requirements(Category.turret, with(Items.copper, 75, Items.lead, 90, Items.titanium, 40, Items.silicon, 55));
            size = 2;
            range = 140;
            recoil = 1f;
            targetAir = false;

            reload = 55f;
            health = 980;
            rotateSpeed = 3;
            //shootEffect = smokeEffect = destroyEffect = placeEffect = Fx.none;

            shootSound = Sounds.laser;

            consumePower(1.4f);
            coolant = consumeCoolant(0.2f);
            shootType = new LaserBulletType(50) {{
                buildingDamageMultiplier = 0.25f;
                lifetime = 25f;
                collidesAir = false;
                trailLength = 2;
                trailWidth = 1.8f;
                lightningLength = 16;
                lightningColor = Color.valueOf("feb380");
                sideAngle = 45f;
                sideWidth = 1f;
                recoil = 45;
                recoilTime = 30;
                sideLength = 15f;
                colors = new Color[]{Color.valueOf("feb380").cpy().a(0.4f), Color.valueOf("feb380"), Color.white};
            }};

            shoot = new ShootAlternate(10);

            recoils = 2;
            drawer = new DrawTurret("based-"){{
                for(int i = 0; i < 2; i ++){
                    int f = i;
                    parts.add(new RegionPart("-barrel-" + (i == 0 ? "l" : "r")){{
                        progress = PartProgress.recoil;
                        recoilIndex = f;
                        under = true;
                        moveY = -3.5f;
                    }});
                }
            }};
        }};

        eradication = new PowerTurret("eradication") {{
            requirements(Category.turret, with(Items.titanium, 120, Items.plastanium, 40));

            size = 3;
            range = 240;
            recoil = 2f;
            shoot.shots = 3;
            shoot.shotDelay = 7.8f;
            shootSound = Sounds.release;
            reload = 140;
            health = 1670;
            rotateSpeed = 2.3f;
            consumePower(1.4f);
            coolant = consumeCoolant(0.2f);

            outlineColor = Pal.darkOutline;

            shootType = new BasicBulletType() {{
                hitColor = Pal.lancerLaser;
                despawnSound = Sounds.none;
                sprite = "large-orb";
                pierceCap = 3;
                trailEffect = Fx.missileTrail;
                trailInterval = 3f;
                trailParam = 4f;
                speed = 12f;
                damage = 46f;
                lifetime = 20f;
                width = height = 10f;
                backColor = CIPal.darkPink;
                frontColor = CIPal.pink;
                shrinkX = shrinkY = 0f;
                trailColor = CIPal.darkPink;
                trailLength = 3;
                trailWidth = 2.2f;
                despawnEffect = hitEffect = Fx.none;
                lightningColor = CIPal.pink;
                lightningDamage = 12;
                lightning = 3;
                lightningLength = 2;
                lightningLengthRand = 8;
            }};

            drawer = new DrawTurret("based-");
        }};
    }
}