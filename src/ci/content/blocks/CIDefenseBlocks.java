package ci.content.blocks;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.util.Time;
import ci.content.*;
import mindustry.content.*;
import mindustry.entities.bullet.*;
import mindustry.entities.part.*;
import mindustry.entities.pattern.*;
import mindustry.gen.*;
import mindustry.graphics.*;
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
    spark,


    stormBringer, squall, serpent, trident, eradication, radiant;

    public static void load() {
        // Walls
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

        // Turrets
        spark = new PowerTurret("spark"){{
            requirements(Category.turret, BuildVisibility.sandboxOnly, with(CIItems.cobalith, 1));

            outlineColor = CIPal.dustyOutline;
            size = 1;
            health = 140;
            range = 130f;
            reload = 25f;
            recoil = 1f;
            rotateSpeed = 6f;
            targetAir = true;
            shootY = 3f;

            consumePower(1.5f);
            drawer = new DrawTurret("based-");

            shootType = new BasicBulletType(4f, 9f) {
                {
                    hitEffect = Fx.hitLancer;
                    despawnEffect = Fx.hitLancer;
                    lifetime = 32.5f;
                    shrinkX = shrinkY = 0f;
                    width = height = 3f;

                    trailWidth = 2.5f;
                    trailLength = 3;

                    lightRadius = 35f;
                    lightOpacity = 0.7f;
                }

                @Override
                public void draw(Bullet b){
                    super.draw(b);

                    float mult = b.fin();
                    float sin = Mathf.absin(Time.time, 2f, 0.3f);

                    float flareWidth = 3f;
                    float flareLength = 9f * (mult + sin);
                    float flareInnerScl = 0.5f;
                    float flareInnerLenScl = 0.7f;
                    float angle = Time.time * 3f + b.rotation();

                    Draw.z(Layer.bullet + 0.1f);

                    Draw.color(backColor);
                    for(int i = 0; i < 4; i++){
                        Drawf.tri(b.x, b.y, flareWidth, flareLength, i * 90 + 45 + angle);
                    }

                    Draw.color(frontColor);
                    for(int i = 0; i < 4; i++){
                        Drawf.tri(b.x, b.y, flareWidth * flareInnerScl, flareLength * flareInnerLenScl, i * 90 + 45 + angle);
                    }
                    Draw.reset();
                }};
        }};

        // may be deleted
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