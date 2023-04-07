package ci.content;

import arc.graphics.Color;
import arc.math.Interp;
import ci.content.blocks.power.MagnesiumNode;
import ci.world.draw.DrawTemp;
import ci.world.draw.SteamParticles;
import mindustry.content.*;
import mindustry.entities.Effect;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.part.RegionPart;
import mindustry.gen.Sounds;
import mindustry.graphics.CacheLayer;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.defense.turrets.PowerTurret;
import mindustry.world.blocks.distribution.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.blocks.liquid.Conduit;
import mindustry.world.blocks.liquid.LiquidRouter;
import mindustry.world.blocks.power.ConsumeGenerator;
import mindustry.world.blocks.power.PowerNode;
import mindustry.world.blocks.production.*;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.blocks.storage.StorageBlock;
import mindustry.world.draw.*;
import mindustry.world.meta.Attribute;
import mindustry.world.meta.BuildVisibility;

import static mindustry.type.ItemStack.with;

public class CosmicIndustriesBlocks {
    public Effect effect;
    public static Block

    //distribution
    pipeConveyor,
    magnesiumDuct, magnesiumJunction, magnesiumRouter,

    //environment
    hematiteOre, ironOre, duneSand, gert, gertBoulder, gertWall, mercuryMud,
    chugalitra, chugalitraBoulder, chugalitraWall, chugalitraWater, echugalite, echugaliteWall, echugaliteWater, lechugate, lechugateBoulder, lechugateWall, lechugateWater, magnesiumOre,

    //liquid
    lazyPump, liquidCistern, liquidLargeCistern,
    magnesiumConduit, magnesiumPump,

    //power
    mechanicalTurbine,
    magnesiumNode, magneticEnergySeparator,

    //production
    rustyDrill,

    miniDrill, miniMiller,

    //crafting
    misuneseSmelter, mechanicalPress, siliconeThermalSmelter,

    //turrets
    shoker, splitter, GelelectronBlaster,
    dissecter, salvx,

    //defense
    ironWall, ironWallLarge,

    //storage
    storage,
    coreHeart, corePixel;
    public static void load(){
        //distribution
        pipeConveyor = new Conveyor("pipeConveyor"){{
            requirements(Category.distribution, with(CosmicIndustriesItems.iron, 1));
            health = 30;
            speed = 0.07f;
            displayedSpeed = 6f;
            buildCostMultiplier = 2f;
            researchCost = with(CosmicIndustriesItems.iron, 5);
            }};

        magnesiumDuct = new Duct("magnesiumDuct"){{
            requirements(Category.distribution, with(CosmicIndustriesItems.magnesium, 1));
            health = 2;
            speed = 3.6f;
            researchCost = with(CosmicIndustriesItems.magnesium, 5);
        }};

        magnesiumJunction = new Junction("magnesiumJunction"){{
            requirements(Category.distribution, with(CosmicIndustriesItems.magnesium, 2));
            speed = 3;
            capacity = 4;
            health = 5;
            buildCostMultiplier = 6f;
        }};

        magnesiumRouter = new Router("magnesiumRouter"){{
            requirements(Category.distribution, with(CosmicIndustriesItems.magnesium, 3));
            health = 5;
            speed = 3f;
            regionRotated1 = 1;
            solid = false;
            researchCost = with(CosmicIndustriesItems.magnesium, 30);
        }};

        //environment

        ironOre = new OreBlock(CosmicIndustriesItems.iron) {{
            oreDefault = true;
            oreThreshold = 0.81f;
            oreScale = 23.47619f;
            variants = 3;
        }};
        hematiteOre = new OreBlock(CosmicIndustriesItems.hematite) {{
            oreDefault = true;
            oreThreshold = 0.93f;
            oreScale = 21.42365f;
            variants = 3;
        }};

        duneSand = new Floor("duneSand") {{
            variants = 2;
        }};

        gert = new Floor("gert"){{
            variants = 3;
        }};

        gertBoulder = new Prop("gert-boulder"){{
           variants = 2;
        }};

        gertWall = new StaticWall("gert-wall"){{
            variants = 2;
        }};

        mercuryMud = new Floor("mercury-mud"){{
           variants = 3;
        }};


        chugalitra = new Floor("chugalitra") {{
            variants = 3;
        }};

        chugalitraBoulder = new Prop("chugalitraBoulder"){{
            variants = 3;
            chugalitra.asFloor().decoration = this;
        }};

        chugalitraWall = new StaticWall("chugalitraWall") {{
            variants = 3;
        }};

        chugalitraWater = new Floor("chugalitraWater") {{
            speedMultiplier = 0.35f;
            variants = 3;
            isLiquid = true;
            cacheLayer = CacheLayer.water;
            albedo = 0.9f;
            supportsOverlay = true;
        }};

        echugalite = new Floor("echugalite"){{
            variants = 3;
        }};

        echugaliteWall = new StaticWall("echugaliteWall"){{
            variants = 3;
        }};

        echugaliteWater = new Floor("echugaliteWater"){{
            speedMultiplier = 0.35f;
            variants = 3;
            isLiquid = true;
            cacheLayer = CacheLayer.water;
            albedo = 0.9f;
            supportsOverlay = true;
        }};

        lechugate = new Floor("lechugate"){{
            variants = 3;
        }};

        lechugateWall = new StaticWall("lechugateWall"){{
            variants = 3;
        }};

        lechugateBoulder = new Prop("lechugateBoulder"){{
            variants = 3;
        }};

        lechugateWater = new Floor("lechugateWater"){{
            speedMultiplier = 0.35f;
            variants = 3;
            isLiquid = true;
            cacheLayer = CacheLayer.water;
            albedo = 0.9f;
            supportsOverlay = true;
        }};

        magnesiumOre = new OreBlock(CosmicIndustriesItems.magnesium){{
            oreDefault = true;
            oreThreshold = 0.51f;
            oreScale = 21.47619f;
            variants = 3;
        }};

        //liquid
        magnesiumConduit = new Conduit("magnesiumConduit"){{
            requirements(Category.liquid, with(CosmicIndustriesItems.magnesium, 4));
            leaks = true;
            health = 10;
            liquidCapacity = 5;
            liquidPressure = 2;
            placeableLiquid = true;
        }};

        magnesiumPump = new Pump("magnesiumPump"){{
            requirements(Category.liquid, with(CosmicIndustriesItems.magnesium, 8));
            size = 1;
            pumpAmount = 0.05f;
            squareSprite = false;
            placeableLiquid = true;
            liquidCapacity = 5;
        }};

        lazyPump = new Pump("lazyPump"){{
            requirements(Category.liquid, with(CosmicIndustriesItems.iron, 10));
            size = 1;
            pumpAmount = 3f / 60f;
            squareSprite = false;
            placeableLiquid = true;
        }};

        //todo create new class
        liquidCistern = new LiquidRouter("liquidCistern"){{
            requirements(Category.liquid, with(CosmicIndustriesItems.iron, 70, CosmicIndustriesItems.hematite, 20));
            size = 2;
            solid = true;
            liquidCapacity = 350;
            liquidPadding = 1.5f;
            health = 300;
            squareSprite = false;
        }};


        liquidLargeCistern = new LiquidRouter("liquidLargeCistern"){{
            requirements(Category.liquid, with(CosmicIndustriesItems.iron, 110, CosmicIndustriesItems.hematite, 45));
            size = 3;
            solid = true;
            liquidCapacity = 800f;
            liquidPadding = 2f;
            health = 550;
            squareSprite = false;
        }};

        //power

        mechanicalTurbine = new SolidPump("mechanicalTurbine"){{
            requirements(Category.power, with(CosmicIndustriesItems.iron, 1));
            result = Liquids.water;
            pumpAmount = 0.11f;
            size = 2;
            liquidCapacity = 6f;
            rotateSpeed = 1.4f;

            consumePower(1.5f);
        }};

        magnesiumNode = new MagnesiumNode("magnesiumNode"){{
            requirements(Category.power, with(CosmicIndustriesItems.magnesium, 10));
            health = 10;
            baseExplosiveness = 2;
            laserRange = 5;
            hasPower = true;
            outputsPower = true;
            laserColor1 = Color.black;
            laserColor2 = Color.valueOf("5c5e9e");


            maxNodes = 5;
            consumePowerBuffered(750f);
        }};

        magneticEnergySeparator = new ConsumeGenerator("magneticEnergySeparator"){{
            requirements(Category.power, with(CosmicIndustriesItems.magnesium, 75));
            size = 2;
            powerProduction = 1;

            drawer = new DrawMulti(new DrawDefault(),
                    new DrawRegion(){{
                       suffix = ("-bottom");
                    }},
                    new DrawRegion(){{
                        spinSprite = true;
                        rotateSpeed = 4f;
                        suffix = ("-rotator");
                    }},
                    new DrawRegion(){{
                        suffix = ("-top");
                    }},
                    new DrawGlowRegion(){{
                        color = Color.valueOf("ff6ef8");
                        suffix = ("-glow");
                    }});

            consumeItem(CosmicIndustriesItems.magnesium, 1);
            consumeLiquid(Liquids.water, 1);
        }};

        //production

        miniDrill = new Drill("miniDrill"){{
            requirements(Category.production, with(CosmicIndustriesItems.magnesium, 10));
            size = 1;
            health = 15;
            drillTime = 500;
            tier = 1;
            rotateSpeed = 2;
            researchCostMultiplier = 0.1f;
            liquidCapacity = 25;
            consumeLiquid(Liquids.water, 0.3f).boost();
        }};

        miniMiller = new WallCrafter("miniMiller"){{
            requirements(Category.production, with(CosmicIndustriesItems.magnesium, 15));
            size = 1;
            health = 20;
            drillTime = 420;
            attribute = Attribute.sand;
            rotateSpeed = 2;
            consumePower(0.08f);
        }};

        //crafting
        misuneseSmelter = new GenericCrafter("misuneseSmelter"){{
            requirements(Category.crafting, with(CosmicIndustriesItems.magnesium, 80));
            craftEffect = Fx.smeltsmoke;
            outputItem = new ItemStack(CosmicIndustriesItems.misunese, 1);
            craftTime = 150;
            size = 1;
            hasPower = true;

            drawer = new DrawMulti(new DrawDefault(),
                    new DrawGlowRegion(){{
                        suffix = ("glow");
                        color = Color.valueOf("ca70e6");
                    }});

            ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.15f;

            consumePower(0.33f);
            consumeItems(with(CosmicIndustriesItems.magnesium, 1, Items.sand, 4));
        }};

        mechanicalPress = new GenericCrafter("mechanicalPress"){{
            requirements(Category.crafting, with(CosmicIndustriesItems.iron, 40));
            craftEffect = CosmicIndustriesFx.CISteam;
            squareSprite = false;
            outputItem = new ItemStack(Items.graphite, 2);
            craftTime = 160;
            size = 2;
            hasPower = true;
            researchCostMultiplier = 0.3f;

            drawer = new DrawMulti(new DrawDefault(), new DrawTemp());

            ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.15f;

            consumePower(0.5f);
            consumeItems(with(CosmicIndustriesItems.hematite, 1));
        }};

        siliconeThermalSmelter = new GenericCrafter("siliconeThermalSmelter"){{
                requirements(Category.crafting, with(CosmicIndustriesItems.iron, 40, Items.graphite, 30));
                squareSprite = false;
                outputItem = new ItemStack(Items.silicon, 2);
                craftTime = 85;
                size = 2;
                hasPower = true;
                researchCostMultiplier = 1f;

                drawer = new DrawMulti(new DrawDefault(),
                new SteamParticles() {{
                    color = Color.lightGray;
                    alpha = 0.7f;
                    particleSize = 2.4f;
                    particles = 17;
                    particleRad = 3.8f;
                    particleLife = 115f;
                    reverse = true;
                    rotateScl = 10f;
                }});

                ambientSound = Sounds.smelter;
                ambientSoundVolume = 0.15f;

                consumePower(0.8f);
                consumeItems(with(Items.graphite, 1, Items.sand, 2));

            }};
        //turrets

        shoker = new PowerTurret("shoker"){{
            requirements(Category.turret, with(CosmicIndustriesItems.iron, 25));
            shootType = new LightningBulletType() {{
                damage = 5;
                lightningLength = 10;
                collidesAir = true;
                ammoMultiplier = 1f;
                buildingDamageMultiplier = 0.25f;

                lightningType = new BulletType(0.0001f, 0f) {{
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
            drawer = new DrawTurret("novia-");
        }};

        splitter = new ItemTurret("splitter") {{
                requirements(Category.turret, with(CosmicIndustriesItems.iron, 50, Items.graphite, 10));
                health = 900;
                rotateSpeed = 1.4f;
                recoil = 0.5f;
                size = 2;
                range = 210;
                reload = 120f;

                ammo(
                        CosmicIndustriesItems.iron, new ArtilleryBulletType(3.8f, 38) {{
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
                            fragBullet = new BasicBulletType(3f, 12) {{
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

                        Items.graphite, new ArtilleryBulletType(5f, 34) {{
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
                            fragBullet = new BasicBulletType(5f, 8) {{
                                lifetime = 12f;
                                pierceBuilding = true;
                                width = 6f;
                                height = 4f;
                                pierceCap = 2;
                            }};
                        }}
                );
                drawer = new DrawTurret("novia-") {{
                    parts.addAll(
                            new RegionPart("-barrel") {{
                                progress = PartProgress.recoil.curve(Interp.pow2In);
                                moveY = -1f * 4f / 4f;
                                heatColor = Color.valueOf("f03b0e");
                                mirror = false;
                            }},
                            new RegionPart("-front") {{
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

        //todo balance
        GelelectronBlaster = new PowerTurret("GelelectronBlaster"){{
                requirements(Category.turret, with(CosmicIndustriesItems.iron, 110, CosmicIndustriesItems.hematite, 65, CosmicIndustriesItems.asfrit, 20));
                health = 930;
                rotateSpeed = 1.9f;
                recoil = 1f;
                size = 2;
                range = 235;
                reload = 120f;
                shootY = 6.5f;
                heatColor = Color.cyan;
                hasLiquids = true;
                liquidCapacity = 25f;
                squareSprite = false;
                shootSound = Sounds.laser;
                shootEffect = Fx.none;
                smokeEffect = Fx.none;
                targetAir = false;
                moveWhileCharging = false;
                accurateDelay = false;
                shoot.firstShotDelay = 60f;

                consumePower(1f);
                consumeLiquid(Liquids.water, 0.5f);

                shootType = new BasicBulletType(7.2f,90){{
                    despawnEffect = Fx.colorSpark;
                    hitEffect = Fx.none;
                    lifetime = 32f;
                    width = 8;
                    height = 24;
                    ammoMultiplier = 1;

                    splashDamageRadius = 32f * 0.75f;
                    splashDamage = 75f;

                    chargeEffect = new MultiEffect(Fx.lancerLaserCharge, Fx.lancerLaserChargeBegin);

                    status = StatusEffects.wet;
                    statusDuration = 360f;

                    knockback = 0.55f;
                    hitColor = backColor = trailColor = Color.cyan;
                    trailLength = 6;
                    trailWidth = 2.6f;

                    homingPower = 0.8f;
                    homingDelay = 10f;
                    homingRange = 60f;

                    collidesAir = false;
                    }};

                drawer = new DrawTurret("novia-") {{
                    parts.addAll(
                            new RegionPart("-nozzle") {{
                                progress = PartProgress.warmup;
                                heatProgress = PartProgress.charge;
                                mirror = true;
                                under = false;
                                moveRot = 7f;
                                moves.add(new PartMove(PartProgress.recoil, 0f, 0f, -30f));
                                heatColor = Color.cyan;
                            }});
                }};
            }};

        dissecter = new ItemTurret("dissecter") {{
            requirements(Category.turret, with(CosmicIndustriesItems.magnesium, 45));
            ammo(
                    CosmicIndustriesItems.magnesium, new MissileBulletType(6f, 112) {{
                        consumePower(10f);
                        despawnEffect = Fx.instBomb;
                        height = 80f;
                        width = 38f;
                        lifetime = 50;
                        sprite = "ci-sword";
                    }});

            reload = 30;
            shootCone = 5;
            rotateSpeed = 1.5f;
            targetGround = true;
            targetAir = true;
            range = 250;
            recoil = 2.5f;
            size = 3;
            health = 60;
            ammoPerShot = 1;
            outlineColor = Color.valueOf("000000");

            //TODO shoot sound
            shootSound = Sounds.artillery;
            shootY = 0;
            heatColor = Color.valueOf("a488eb");
            shake = 5;
            hasPower = true;

            drawer = new DrawTurret("octavia-") {{
                parts.add(new RegionPart("-front") {{
                    progress = PartProgress.warmup;
                    moveRot = 0f;
                    mirror = true;
                    moves.add(new PartMove(PartProgress.recoil, 6f, 0f, 0f));
                }});
            }};
            limitRange();

        }};

        salvx = new ItemTurret("salvx"){{
                requirements(Category.turret, with(CosmicIndustriesItems.magnesium, 10));
                reload = 45;
                rotateSpeed = 10;
                targetGround = true;
                targetAir = true;
                range = 120;
                recoil = 1.3f;
                size = 2;
                health = 20;
                heatColor = Color.valueOf("a488eb");
                outlineRadius = 0;

                ammoPerShot = 1;
                shootSound = Sounds.shootBig;
                shootY = 3;
                shoot.shots = 4;
                shoot.shotDelay = 3;
                shootCone = 20;

                ammo(
                        CosmicIndustriesItems.magnesium, new MissileBulletType(6f, 5) {{
                            height = 15;
                            width = 10;
                            lifetime = 20;
                            frontColor = trailColor = Color.valueOf("a488eb");
                            trailLength = 5;
                        }});

                drawer = new DrawTurret("octavia-");

            }};

        //defence

        ironWall = new Wall("ironWall") {{
            size = 1;
            health = 920;
            requirements(Category.defense, with(CosmicIndustriesItems.iron, 6));
        }};
        ironWallLarge = new Wall("ironWallLarge") {{
            size = 2;
            health = 1840;
            requirements(Category.defense, with(CosmicIndustriesItems.iron, 24));
        }};

        //drills

        rustyDrill = new Drill("rustyDrill") {{
            requirements(Category.production, with(CosmicIndustriesItems.iron, 24));
            tier = 1;
            drillTime = 350;
            size = 2;
            alwaysUnlocked = true;
        }};

        //storage

        //todo rename
        storage = new StorageBlock("storage"){{
            requirements(Category.effect, with(CosmicIndustriesItems.iron, 170, CosmicIndustriesItems.hematite, 70));
            size = 3;
            itemCapacity = 500;
            scaledHealth = 35;
        }};

        coreHeart = new CoreBlock("coreHeart") {{
            requirements(Category.effect, BuildVisibility.editorOnly, with(CosmicIndustriesItems.iron, 800, CosmicIndustriesItems.hematite, 1200));
            alwaysUnlocked = true;

            isFirstTier = true;
            unitType = CosmicIndustriesUnits.falcon;
            health = 2000;
            itemCapacity = 3000;
            size = 4;
            unitCapModifier = 8;
            squareSprite = false;
        }};

        corePixel = new CoreBlock("corePixel") {{
            requirements(Category.effect, BuildVisibility.editorOnly, with(CosmicIndustriesItems.magnesium, 500));
            alwaysUnlocked = true;

            isFirstTier = true;
            unitType = CosmicIndustriesUnits.hawk;
            health = 250;
            itemCapacity = 500;
            size = 2;
            unitCapModifier = 4;
            squareSprite = false;
        }};
    }
}