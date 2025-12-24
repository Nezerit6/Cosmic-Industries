package ci.content.blocks;

import arc.graphics.Color;
import arc.math.Interp;
import ci.content.*;
import mindustry.content.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.entities.part.*;
import mindustry.entities.pattern.*;
import mindustry.gen.Sounds;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.draw.*;
import mindustry.world.meta.BuildVisibility;

import static mindustry.type.ItemStack.with;

public class CIDefenseBlocks {
    public static Block
            // Walls
            cobaltWall, cobaltWallLarge,

    // Turrets
    shoker, chire, splitter, punisher, stormBringer, squall, serpent, arcflash, trident, eradication, radiant;

    public static void load() {
        // ===== WALLS =====
        cobaltWall = new Wall("cobalt-wall"){{
            requirements(Category.defense, BuildVisibility.sandboxOnly, with(CIItems.cobalt, 6));
            size = 1;
            health = 920;
        }};

        cobaltWallLarge = new Wall("cobalt-wall-large"){{
            size = 2;
            health = 1840;
            requirements(Category.defense, BuildVisibility.sandboxOnly, with(CIItems.cobalt, 24));
        }};

        // ===== TURRETS =====
        shoker = new PowerTurret("shoker"){{
            requirements(Category.turret, BuildVisibility.sandboxOnly, with(CIItems.cobalt, 25));
            size = 1;
            health = 80;
            range = 60f;
            reload = 12.5f;
            recoil = 1f;
            shootCone = 40f;
            rotateSpeed = 4f;
            targetAir = true;
            heatColor = Color.red;
            shootSound = Sounds.spark;
            shootEffect = Fx.lightningShoot;
            consumePower(0.4f);

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

            drawer = new DrawTurret("based-");
        }};

        chire = new ItemTurret("chire"){{
            requirements(Category.turret, BuildVisibility.sandboxOnly, with(CIItems.cobalt, 24));
            size = 1;
            health = 150;
            range = 110;
            reload = 25;
            recoil = 1;
            inaccuracy = 9;
            rotateSpeed = 12;
            researchCostMultiplier = 0.10f;
            coolant = consumeCoolant(0.1f);

            ammo(
                    CIItems.cobalt, new BasicBulletType(10f, 4){{
                        width = 6;
                        height = 8;
                        lifetime = 60;
                        ammoPerShot = 2;
                        collidesTeam = true;
                        collidesGround = true;
                    }}
            );

            drawer = new DrawTurret("based-"){{
                parts.add(
                        new RegionPart("-side"){{
                            heatProgress = PartProgress.warmup;
                            progress = PartProgress.warmup;
                            mirror = true;
                            moveX = 2f * 4f / 3f;
                            moveY = -0.5f;
                            moveRot = -40f;
                            under = true;
                            heatColor = Color.red.cpy();
                        }}
                );
            }};
        }};

        splitter = new ItemTurret("splitter"){{
            requirements(Category.turret, BuildVisibility.sandboxOnly, with(CIItems.cobalt, 50, Items.graphite, 10));
            size = 2;
            health = 900;
            range = 210;
            reload = 120f;
            recoil = 0.5f;
            rotateSpeed = 1.4f;

            ammo(
                    CIItems.cobalt, new ArtilleryBulletType(3.8f, 38){{
                        lifetime = 44f;
                        width = height = 12;
                        splashDamage = 16f;
                        splashDamageRadius = 15f;
                        despawnShake = 0.4f;

                        hitColor = backColor = trailColor = Color.valueOf("F6BB64");
                        trailLength = 12;
                        trailWidth = 2f;
                        trailSinScl = 2.5f;
                        trailSinMag = 0.5f;
                        trailEffect = Fx.none;

                        fragBullets = 5;
                        fragVelocityMin = 0.9f;
                        fragRandomSpread = 360;
                        fragLifeMin = 0.9f;
                        fragBullet = new BasicBulletType(3f, 12){{
                            lifetime = 14f;
                            width = 7f;
                            height = 5f;
                            pierceBuilding = true;
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
                        width = height = 12;
                        splashDamage = 16f;
                        splashDamageRadius = 12f;
                        despawnShake = 0.4f;

                        hitColor = backColor = trailColor = Color.valueOf("F6BB64");
                        trailLength = 8;
                        trailWidth = 2f;
                        trailSinScl = 2.5f;
                        trailSinMag = 0.5f;
                        trailEffect = Fx.none;

                        fragBullets = 12;
                        fragVelocityMin = 0.9f;
                        fragRandomSpread = 360;
                        fragLifeMin = 0.9f;
                        fragBullet = new BasicBulletType(5f, 8){{
                            lifetime = 12f;
                            width = 6f;
                            height = 4f;
                            pierceBuilding = true;
                            pierceCap = 2;
                        }};
                    }}
            );

            drawer = new DrawTurret("based-"){{
                parts.add(
                        new RegionPart("-barrel"){{
                            progress = PartProgress.recoil.curve(Interp.pow2In);
                            moveY = -1f;
                            heatColor = Color.valueOf("f03b0e");
                            mirror = false;
                        }},
                        new RegionPart("-front"){{
                            heatProgress = PartProgress.warmup;
                            progress = PartProgress.warmup;
                            mirror = true;
                            moveX = -0.5f * 4f / 3f;
                            under = true;
                        }}
                );
            }};
        }};

        punisher = new LiquidTurret("punisher"){{
            requirements(Category.turret, BuildVisibility.sandboxOnly, with(Items.copper, 110, Items.lead, 65, Items.titanium, 35, Items.silicon, 20));
            size = 2;
            health = 430;
            range = 205;
            reload = 120f;
            recoil = 3f;
            shootY = 6.5f;
            rotateSpeed = 4.2f;
            inaccuracy = 5;
            liquidCapacity = 60;
            targetAir = false;
            squareSprite = false;
            extinguish = false;
            moveWhileCharging = false;
            accurateDelay = false;
            heatColor = Color.valueOf("afeeee");
            shootSound = CISounds.bigLaserShoot;
            loopSound = Sounds.none;
            shootEffect = Fx.none;
            smokeEffect = CIFx.laserSparks;
            shoot.firstShotDelay = 60f;
            consumePower(3.6f);

            ammo(
                    Liquids.cryofluid, new BasicBulletType(12.6f, 36){{
                        lifetime = 17.5f;
                        width = 4;
                        height = 28;
                        hitColor = backColor = trailColor = Color.valueOf("afeeee");
                        trailLength = 3;
                        trailWidth = 1.9f;
                        homingPower = 0.03f;
                        homingDelay = 2f;
                        homingRange = 60f;
                        ammoMultiplier = 0.2f;
                        collidesAir = false;
                        hitEffect = Fx.none;
                        chargeEffect = new MultiEffect(Fx.lancerLaserCharge, Fx.lancerLaserChargeBegin);
                        fragBullets = 1;
                    }}
            );

            drawer = new DrawTurret("based-"){{
                parts.add(
                        new RegionPart("-nozzle"){{
                            progress = PartProgress.warmup;
                            heatProgress = PartProgress.charge;
                            mirror = true;
                            moveRot = 7f;
                            heatColor = Color.valueOf("afeeee");
                            moves.add(new PartMove(PartProgress.recoil, 0f, 0f, -30f));
                        }}
                );
            }};
        }};

        stormBringer = new PowerTurret("stormBringer"){{
            requirements(Category.turret, BuildVisibility.sandboxOnly, with(CIItems.cobalt, 1));
            size = 2;
            health = 600;
            range = 130;
            reload = 420f;
            recoil = 2f;
            inaccuracy = 14;
            rotateSpeed = 7;
            shootSound = Sounds.lasershoot;
            shootEffect = Fx.sparkShoot;
            consumePower(1.4f);
            coolant = consumeCoolant(0.2f);

            shoot.shots = 32;
            shoot.shotDelay = 4;
            shoot.firstShotDelay = 15f;

            shootType = new LaserBoltBulletType(6, 13){{
                lifetime = 20f;
                knockback = 0.3f;
                backColor = Pal.heal;
                frontColor = Color.white;
                hitColor = trailColor = Pal.heal;
                trailLength = 2;
                trailWidth = 1.8f;
                status = StatusEffects.corroded;
                statusDuration = 240f;
                smokeEffect = Fx.none;
                hitEffect = despawnEffect = Fx.hitLaser;
            }};

            drawer = new DrawTurret("based-"){{
                parts.add(
                        new RegionPart("-side"){{
                            progress = PartProgress.warmup;
                            mirror = true;
                            moveY = 4.5f;
                            moves.add(new PartMove(PartProgress.heat, 0f, -4.5f, 0f));
                        }}
                );
            }};
        }};

        squall = new ItemTurret("squall"){{
            requirements(Category.turret, BuildVisibility.sandboxOnly, with(Items.copper, 90, Items.lead, 55, Items.titanium, 40, Items.silicon, 30));
            size = 2;
            health = 460;
            range = 200f;
            reload = 80;
            recoil = 3f;
            recoilTime = reload * 2f;
            inaccuracy = 2.5f;
            shootCone = 24f;
            rotateSpeed = 4.3f;
            targetAir = false;
            ammoPerShot = 2;
            coolantMultiplier = 0.5f;
            shootSound = Sounds.shootBig;
            ammoUseEffect = Fx.casing4;
            shoot = new ShootAlternate(7f);
            shoot.shots = 2;
            coolant = consumeCoolant(0.2f);

            ammo(
                    Items.graphite, new BasicBulletType(5f, 9){{
                        width = 10f;
                        height = 16f;
                        reloadMultiplier = 0.9f;
                        ammoMultiplier = 2;
                        incendChance = 0.01f;
                        incendSpread = 0.2f;
                        incendAmount = 1;
                        collideTerrain = true;
                        shootEffect = Fx.shootBig;
                    }},

                    Items.silicon, new BasicBulletType(3.1f, 13){{
                        width = 10f;
                        height = 16f;
                        lifetime = 90f;
                        reloadMultiplier = 1.25f;
                        ammoMultiplier = 1;
                        incendChance = 0.03f;
                        incendSpread = 0.4f;
                        incendAmount = 1;
                        collideTerrain = true;
                        collidesAir = false;
                        shootEffect = Fx.shootBig;
                    }}
            );

            drawer = new DrawTurret("based-");
        }};

        serpent = new PowerTurret("serpent"){{
            requirements(Category.turret, BuildVisibility.sandboxOnly, with(Items.copper, 75, Items.lead, 90, Items.titanium, 40, Items.silicon, 55));
            size = 2;
            health = 980;
            range = 140;
            reload = 55f;
            recoil = 1f;
            rotateSpeed = 3;
            targetAir = false;
            shootSound = Sounds.laser;
            consumePower(1.4f);
            coolant = consumeCoolant(0.2f);
            shoot = new ShootAlternate(10);
            recoils = 2;

            shootType = new LaserBulletType(50){{
                lifetime = 25f;
                collidesAir = false;
                buildingDamageMultiplier = 0.25f;
                lightningColor = Color.valueOf("feb380");
                lightningLength = 16;
                trailLength = 2;
                trailWidth = 1.8f;
                sideAngle = 45f;
                sideWidth = 1f;
                sideLength = 15f;
                colors = new Color[]{
                        Color.valueOf("feb380").cpy().a(0.4f),
                        Color.valueOf("feb380"),
                        Color.white
                };
            }};

            drawer = new DrawTurret("based-"){{
                for(int i = 0; i < 2; i++){
                    int f = i;
                    parts.add(
                            new RegionPart("-barrel-" + (i == 0 ? "l" : "r")){{
                                progress = PartProgress.recoil;
                                recoilIndex = f;
                                under = true;
                                moveY = -3.5f;
                            }}
                    );
                }
            }};
        }};

        arcflash = new PowerTurret("arcflash"){{
            requirements(Category.turret, BuildVisibility.sandboxOnly, with(CIItems.cobalt, 80, CIItems.cobalt, 60));
            size = 2;
            health = 1000;
            range = 180f;
            reload = 140f;
            shake = 3f;
            cooldownTime = reload - 10f;
            shootSound = Sounds.shockBlast;
            consumePower(3f);

            shootType = new BasicBulletType(){{
                speed = 3f;
                damage = 75f;
                lifetime = 60f;
                width = height = 15f;
                sprite = "large-orb";
                shrinkX = shrinkY = 0f;
                hitColor = Color.valueOf("869cbe");
                backColor = trailColor = Color.valueOf("869cbe");
                frontColor = Color.white;
                trailLength = 12;
                trailWidth = 2.2f;
                trailEffect = Fx.missileTrail;
                trailInterval = 3f;
                trailParam = 4f;
                despawnSound = Sounds.spark;
                shootEffect = new MultiEffect(
                        Fx.shootTitan,
                        new WaveEffect(){{
                            colorTo = Color.valueOf("869cbe");
                            sizeTo = 26f;
                            lifetime = 14f;
                            strokeFrom = 4f;
                        }}
                );
                smokeEffect = Fx.shootSmokeTitan;
                despawnEffect = hitEffect = new ExplosionEffect(){{
                    waveColor = Color.valueOf("869cbe");
                    smokeColor = Color.gray;
                    sparkColor = Color.white;
                    waveStroke = 4f;
                    waveRad = 40f;
                }};

                bulletInterval = 4f;
                lightningColor = Color.valueOf("869cbe");
                lightningDamage = 17;
                lightning = 8;
                lightningLength = 2;
                lightningLengthRand = 8;

                intervalBullet = new LightningBulletType(){{
                    damage = 16;
                    collidesAir = false;
                    ammoMultiplier = 1f;
                    lightningColor = Color.valueOf("869cbe");
                    lightningLength = 3;
                    lightningLengthRand = 6;
                    buildingDamageMultiplier = 0.25f;

                    lightningType = new BulletType(0.0001f, 0f){{
                        lifetime = Fx.lightning.lifetime;
                        hitEffect = Fx.hitLancer;
                        despawnEffect = Fx.none;
                        status = StatusEffects.shocked;
                        statusDuration = 10f;
                        hittable = false;
                        lightColor = Color.white;
                        buildingDamageMultiplier = 0.25f;
                    }};
                }};
            }};

            drawer = new DrawTurret("based-"){{
                parts.add(
                        new RegionPart("-side"){{
                            progress = PartProgress.recoil;
                            mirror = true;
                            moveRot = -10f;
                            moveX = -1f;
                            moveY = 1f;
                        }}
                );
            }};
        }};

        trident = new ItemTurret("trident"){{
            requirements(Category.turret, BuildVisibility.sandboxOnly, with(Items.titanium, 120, Items.plastanium, 40));
            size = 3;
            health = 1670;
            range = 240;
            reload = 140;
            recoil = 2f;
            rotateSpeed = 2.3f;
        }};

        eradication = new PowerTurret("eradication"){{
            requirements(Category.turret, BuildVisibility.sandboxOnly, with(Items.titanium, 120, Items.plastanium, 40));
            size = 3;
            health = 1670;
            range = 240;
            reload = 140;
            recoil = 2f;
            rotateSpeed = 2.3f;
            outlineColor = Pal.darkOutline;
            shootSound = Sounds.release;
            consumePower(1.4f);
            coolant = consumeCoolant(0.2f);
            shoot.shots = 3;
            shoot.shotDelay = 7.8f;

            shootType = new BasicBulletType(){{
                speed = 12f;
                damage = 46f;
                lifetime = 20f;
                width = height = 10f;
                sprite = "large-orb";
                shrinkX = shrinkY = 0f;
                pierceCap = 3;
                hitColor = Pal.lancerLaser;
                backColor = trailColor = CIPal.darkPink;
                frontColor = CIPal.pink;
                trailLength = 3;
                trailWidth = 2.2f;
                trailEffect = Fx.missileTrail;
                trailInterval = 3f;
                trailParam = 4f;
                despawnSound = Sounds.none;
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