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
            mechanicalPress, siliconeThermalSmelter;

    public static void load() {
        mechanicalPress = new GenericCrafter("mechanicalPress"){{
            requirements(Category.crafting, BuildVisibility.sandboxOnly, with(CIItems.cobalt, 40));
            craftEffect = CIFx.CISteam;
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
            consumeItems(with(CIItems.cobalt, 1));
        }};

        siliconeThermalSmelter = new GenericCrafter("siliconeThermalSmelter"){{
            requirements(Category.crafting, BuildVisibility.sandboxOnly, with(CIItems.cobalt, 40, Items.graphite, 30));
            squareSprite = false;
            outputItem = new ItemStack(Items.silicon, 2);
            craftTime = 85;
            size = 2;
            hasPower = true;
            researchCostMultiplier = 1f;

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

            ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.15f;

            consumePower(0.8f);
            consumeItems(with(Items.graphite, 1, Items.sand, 2));
        }};
    }
}