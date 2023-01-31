package ci.content;

import arc.graphics.Color;
import arc.math.Interp;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.content.StatusEffects;
import mindustry.entities.bullet.*;
import mindustry.entities.part.RegionPart;
import mindustry.entities.pattern.ShootSpread;
import mindustry.gen.Sounds;
import mindustry.graphics.CacheLayer;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.blocks.defense.turrets.ContinuousTurret;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.defense.turrets.PowerTurret;
import mindustry.world.blocks.distribution.Duct;
import mindustry.world.blocks.distribution.Junction;
import mindustry.world.blocks.distribution.Router;
import mindustry.world.blocks.environment.*;
import mindustry.world.blocks.liquid.Conduit;
import mindustry.world.blocks.power.ConsumeGenerator;
import mindustry.world.blocks.power.PowerNode;
import mindustry.world.blocks.production.Drill;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.blocks.production.Pump;
import mindustry.world.blocks.production.WallCrafter;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.draw.DrawDefault;
import mindustry.world.draw.DrawFlame;
import mindustry.world.draw.DrawMulti;
import mindustry.world.draw.DrawTurret;
import mindustry.world.meta.Attribute;
import mindustry.world.meta.BuildVisibility;
import mindustry.world.meta.Env;

import static mindustry.type.ItemStack.with;

public class CosmicIndustriesBlocks {
    public static Block

    //distribution
    magnesiumDuct, magnesiumJunction, magnesiumRouter,

    //environment
    hematiteOre, ironOre, duneSand,
    chugalitra, chugalitraBoulder, chugalitraWall, chugalitraWater, echugalite, echugaliteWall, echugaliteWater, lechugate, lechugateBoulder, lechugateWall, lechugateWater, magnesiumOre,

    //liquid
    magnesiumConduit, magnesiumPump,

    //power
    magnesiumNode, magneticEnergySeparator,

    //production
    ironDrill,

    miniDrill, miniMiller,

    //crafting
    litiumSmelter,
    misuneseSmelter,

    //turrets
    shoker, cidel, test, plasma, trident,
    dissecter, salvx,

    //defense
    ironWall, ironWallLarge,

    //storage
    coreHeart, corePixel;
    public static void load () {

        //distribution
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

        chugalitra = new Floor("chugalitra") {{
            variants = 3;
            itemDrop = Items.sand;
        }};

        chugalitraBoulder = new Prop("chugalitraBoulder"){{
           variants = 3;
           chugalitra.asFloor().decoration = this;
        }};

        chugalitraWall = new StaticWall("chugalitraWall"){{
           variants = 3;
        }};

        chugalitraWater = new Floor("chugalitraWater"){{
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
           size = 1;
           pumpAmount = 0.05f;
           squareSprite = false;
           placeableLiquid = true;
           liquidCapacity = 5;
        }};

        //power

        magnesiumNode = new PowerNode("magnesiumNode"){{
            requirements(Category.power, with(CosmicIndustriesItems.magnesium, 10));
            health = 10;
            baseExplosiveness = 2;
            laserRange = 5;
            hasPower = true;
            outputsPower = true;
            laserColor1 = Color.valueOf("a488eb");
            laserColor2 = Color.valueOf("5c5e9e");
            maxNodes = 5;
            consumePowerBuffered(750f);
        }};

        magneticEnergySeparator = new ConsumeGenerator("magneticEnergySeparator"){{
            size = 2;
            powerProduction = 1;
            //TODO fix
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

        litiumSmelter = new GenericCrafter("litiumSmelter"){{
            requirements(Category.crafting, with(CosmicIndustriesItems.iron, 80));
            craftTime = 70f;
            size = 2;
            hasPower = true;
            hasLiquids = false;
            consumeItems(with(Items.silicon, 2, CosmicIndustriesItems.iron, 1));
            outputItem = new ItemStack(CosmicIndustriesItems.lithium, 2);
            drawer = new DrawMulti(new DrawDefault(), new DrawFlame(Color.valueOf("ffef84")));
            ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.09f;
            craftEffect = Fx.smeltsmoke;
            consumePower(0.80f);
        }};

        misuneseSmelter = new GenericCrafter("misuneseSmelter"){{
            requirements(Category.crafting, with(CosmicIndustriesItems.magnesium, 80));
            craftEffect = Fx.smeltsmoke;
            outputItem = new ItemStack(CosmicIndustriesItems.misunese, 1);
            craftTime = 150;
            size = 1;
            hasPower = true;

            //todo drawers

            ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.15f;

            consumePower(0.33f);
            consumeItems(with(CosmicIndustriesItems.magnesium, 1, Items.sand, 4));
        }};

        //turrets

        shoker = new PowerTurret("shoker") {{
        requirements(Category.turret, with(CosmicIndustriesItems.iron, 25));
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
            drawer = new DrawTurret("novia-");
    }};

        cidel = new ItemTurret("cidel") {{
            requirements(Category.turret, with(CosmicIndustriesItems.iron, 60));
            health = 120;
            rotateSpeed = 2.6f;
            recoil = 0.6f;
            size = 2;
            range = 140;
            reload = 125f;
            ammo (
                    CosmicIndustriesItems.iron, new BasicBulletType(4f, 40) {{
                            lifetime = 32f;
                            fragBullets = 16;
                            fragVelocityMin = 0.9f;
                            fragRandomSpread = 360;
                            fragLifeMin = 1.1f;
                            width = 11;
                            height = 11;
                            fragBullet = new BasicBulletType() {{
                                damage = 15f;
                                speed = 3.4f;
                                lifetime = 24f;
                                pierceBuilding = true;
                                width = 6f;
                                height = 6f;
                            }};
                        }});
            drawer = new DrawTurret("novia-"){
                {
                    parts.addAll(
                            new RegionPart("-barrel") {{
                                progress = PartProgress.recoil.curve(Interp.pow2In);
                                moveY = -5f * 4f / 4f;
                                heatColor = Color.valueOf("f03b0e");
                                mirror = false;
                            }},
                            new RegionPart("-front") {{
                                heatProgress = PartProgress.warmup;
                                progress = PartProgress.warmup;
                                mirror = true;
                                moveX = 2f * 4f / 3f;
                                moveY = -0.5f;
                                moveRot = -40f;
                                under = true;
                            }});
                }};
        }};

        test = new ItemTurret("test") {{
        requirements(Category.turret, with(CosmicIndustriesItems.iron, 170, CosmicIndustriesItems.hematite, 80));
        ammo(
        CosmicIndustriesItems.hematite,  new BasicBulletType(4.3f, 105){{
            shoot = new ShootSpread(13, 4f);
            shoot.shotDelay = 3;
            height = 12f;
            width = 11f;
            lifetime = 73f;

            }});

            reload = 65f;
            shootCone = 40f;
            rotateSpeed = 3f;
            targetAir = true;
            targetGround = false;
            range = 325f;
            recoil = 1.3f;
            size = 3;
            health = 445;
            ammoPerShot = 2;
            shootSound = Sounds.artillery;
            consumePower(0.5f);
            shootY = 3;
            drawer = new DrawTurret("novia-"){{
                parts.add(new RegionPart("-front"){{
                    progress = PartProgress.warmup;
                    moveRot = -12f;
                    mirror = true;
                    moves.add(new PartMove(PartProgress.recoil, 0f, -3f, -6f));
                }});
            }};
            limitRange();
        }};

        plasma = new ContinuousTurret("plasma") {{
            requirements(Category.turret, with(CosmicIndustriesItems.iron, 240, CosmicIndustriesItems.hematite, 120, CosmicIndustriesItems.lithium, 40));
            shootType = new PointLaserBulletType(){{
                damage = 83f;
                buildingDamageMultiplier = 0.3f;
                hitColor = Color.valueOf("fda981");
            }};
            shootSound = Sounds.none;
            loopSoundVolume = 1f;
            loopSound = Sounds.laserbeam;
            shootWarmupSpeed = 0.08f;
            shootCone = 360f;
            aimChangeSpeed = 999f;
            rotateSpeed = 999f;
            shootY = 0.1f;
            outlineColor = Pal.darkOutline;
            size = 3;
            envEnabled |= Env.space;
            range = 230f;
            scaledHealth = 210;
            health = 570;
            consumePower(3.4f);
            drawer = new DrawTurret("novia-");
        }};

        trident = new ItemTurret("trident"){{
            requirements(Category.turret, with(CosmicIndustriesItems.iron, 160, CosmicIndustriesItems.lithium, 200));
            ammo(
                    CosmicIndustriesItems.iron,  new BasicBulletType(5f, 40){{
                        height = 9f;
                        width = 7f;
                        lifetime = 64;
                        inaccuracy = 5;
                    }});

            reload = 360f;
            shootCone = 40f;
            rotateSpeed = 3.7f;
            targetAir = true;
            targetGround = false;
            range = 400f;
            recoil = 2f;
            size = 3;
            health = 420;
            ammoPerShot = 1;
            shootSound = Sounds.artillery;
            shootY = 2;
            shoot.shots = 16;
            shoot.shotDelay = 6f;
            drawer = new DrawTurret("novia-"){{
                parts.add(new RegionPart("-front"){{
                    progress = PartProgress.warmup;
                    moveRot = -6f;
                    mirror = true;
                    moves.add(new PartMove(PartProgress.recoil, 0f, 3f, -3f));
                }});
            }};
            limitRange();
        }};

        dissecter = new ItemTurret("dissecter"){{
            requirements(Category.turret, with(CosmicIndustriesItems.magnesium, 45));
            ammo(
                    CosmicIndustriesItems.magnesium,  new MissileBulletType(6f, 112){{
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

            drawer = new DrawTurret("octavia-"){{
                parts.add(new RegionPart("-front"){{
                    progress = PartProgress.warmup;
                    moveRot = 0f;
                    mirror = true;
                    moves.add(new PartMove(PartProgress.recoil, 6f, 0f, 0f));
                }});
            }};
            limitRange();

        }};


        //defence

        ironWall = new Wall("ironWall") {{
            size = 1;
            health = 920;
            requirements(Category.defense, with(CosmicIndustriesItems.iron,6));
        }};
        ironWallLarge = new Wall("ironWallLarge") {{
            size = 2;
            health = 1840;
            requirements(Category.defense, with(CosmicIndustriesItems.iron,24));
        }};

        //drills

        ironDrill = new Drill("ironDrill") {{
            requirements(Category.production, with(CosmicIndustriesItems.iron, 24));
            tier = 2;
            drillTime = 350;
            size = 2;
            alwaysUnlocked = true;
        }};

        //storage

        coreHeart = new CoreBlock("coreHeart") {{
            requirements(Category.effect, BuildVisibility.editorOnly, with( CosmicIndustriesItems.iron, 800, CosmicIndustriesItems.hematite, 1200));
            alwaysUnlocked = true;

            isFirstTier = true;
            unitType = CosmicIndustriesUnits.falcon;
            health = 2000;
            itemCapacity = 3000;
            size = 4;
            unitCapModifier = 8;
        }};

        corePixel = new CoreBlock("corePixel"){{
            requirements(Category.effect, BuildVisibility.editorOnly, with( CosmicIndustriesItems.magnesium, 500));
            alwaysUnlocked = true;

            isFirstTier = true;
            squareSprite = false;
            unitType = CosmicIndustriesUnits.hawk;
            health = 250;
            itemCapacity = 500;
            size = 2;
            unitCapModifier = 4;
        }};
    }
}
