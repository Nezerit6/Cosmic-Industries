package ci.content;

import arc.graphics.Color;
import arc.graphics.Colors;
import arc.math.Interp;
import ci.world.draw.DrawTemp;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.content.StatusEffects;
import mindustry.entities.bullet.*;
import mindustry.entities.part.RegionPart;
import mindustry.gen.Sounds;
import mindustry.graphics.CacheLayer;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.blocks.defense.Wall;
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
import mindustry.world.draw.*;
import mindustry.world.meta.Attribute;
import mindustry.world.meta.BuildVisibility;

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
    rustyDrill,

    miniDrill, miniMiller,

    //crafting
    misuneseSmelter, mechanicalPress,

    //turrets
    shoker, splitter,
    dissecter, salvx,

    //defense
    ironWall, ironWallLarge,

    //storage
    coreHeart, corePixel;
    public static void load(){
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

        mechanicalPress = new GenericCrafter("mechanicalPress"){{
            requirements(Category.crafting, with(CosmicIndustriesItems.iron, 40));
            craftEffect = CosmicIndustriesFx.CISteam;
            squareSprite = false;
            outputItem = new ItemStack(Items.graphite, 2);
            craftTime = 230;
            size = 2;
            hasPower = true;
            researchCostMultiplier = 0.3f;

            drawer = new DrawMulti(new DrawDefault(), new DrawTemp());

                    /**new SteamParticles(){{
                        color = Color.valueOf("ffffff");
                        alpha = 0.5f;
                        particleSize = 2f;
                        particles = 35;
                        particleRad = 24f;
                        particleLife = 400f;
                        reverse = true;
                        rotateScl = 5;
                    }});*/

            ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.15f;

            consumePower(0.5f);
            consumeItems(with(CosmicIndustriesItems.hematite, 1));
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
            health = 90;
            rotateSpeed = 1.4f;
            recoil = 0.5f;
            size = 2;
            range = 210;
            reload = 120f;

            squareSprite = false;

            ammo(
                    CosmicIndustriesItems.iron, new BasicBulletType(4.6f, 36) {{
                        lifetime = 44f;
                        width = 12;
                        height = 10;

                        trailLength = 12;
                        trailWidth = 2f;
                        hitColor = backColor = trailColor = Color.darkGray;
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
                        fragBullet = new BasicBulletType(4.6f,12) {{
                            lifetime = 8f;
                            pierceBuilding = true;
                            width = 6f;
                            height = 4f;
                            pierceCap = 2;
                        }};
                    }});
            drawer = new DrawTurret("novia-") {
                {
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
                }
            };
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
            tier = 2;
            drillTime = 350;
            size = 2;
            alwaysUnlocked = true;
        }};

        //storage

        coreHeart = new CoreBlock("coreHeart") {{
            requirements(Category.effect, BuildVisibility.editorOnly, with(CosmicIndustriesItems.iron, 800, CosmicIndustriesItems.hematite, 1200));
            alwaysUnlocked = true;

            isFirstTier = true;
            unitType = CosmicIndustriesUnits.falcon;
            health = 2000;
            itemCapacity = 3000;
            size = 4;
            unitCapModifier = 8;
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
        }};
    }
}