package ci.content.blocks;

import arc.graphics.Color;
import ci.content.*;
import ci.world.draw.*;
import mindustry.content.Items;
import mindustry.gen.Sounds;
import mindustry.type.*;
import mindustry.world.Block;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.draw.*;
import mindustry.world.meta.*;

import static mindustry.type.ItemStack.with;

public class CICraftingBlocks {
    public static Block
            compositeForge, graphiteKiln;

    public static void load() {

        compositeForge = new GenericCrafter("composite-forge"){{
            requirements(Category.crafting, BuildVisibility.sandboxOnly, with(CIItems.cobalt, 30, CIItems.lithium, 20));

            size = 2;

            craftTime = 85;
            consumeItems(with(CIItems.cobalt, 1, CIItems.lithium, 1));
            outputItem = new ItemStack(CIItems.composite, 1);
            consumePower(0.8f);

            researchCostMultiplier = 0.25f;

            ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.15f;

            drawer = new DrawMulti(new DrawDefault(),
                    new SteamParticles(){{
                        color = Color.lightGray;
                        alpha = 0.7f;
                        particleSize = 2.4f;
                        particles = 17;
                        particleRad = 3.8f;
                        particleLife = 115f;
                        reverse = true;
                        rotateScl = 10f;
                    }});
        }};

        graphiteKiln = new GenericCrafter("graphite-kiln"){{
            requirements(Category.crafting, BuildVisibility.sandboxOnly, with(CIItems.cobalt, 30, CIItems.lithium, 20, CIItems.composite, 5));

            squareSprite = false;
            size = 2;
            craftTime = 160;

            craftEffect = CIFx.CISteam;
            consumeItems(with(Items.coal, 2));
            outputItem = new ItemStack(Items.graphite, 1);
            consumePower(0.5f);

            researchCostMultiplier = 0.5f;

            ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.15f;

            drawer = new DrawMulti(new DrawDefault(), new DrawTemp(){{
                suffix = "-heat";
                color = lightColor = Color.valueOf("ff6214");
            }});
        }};
    }
}