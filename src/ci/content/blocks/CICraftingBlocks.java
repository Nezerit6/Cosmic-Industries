package ci.content.blocks;

import ci.content.CIFx;
import ci.content.CIItems;
import ci.world.blocks.production.*;
import ci.world.draw.DrawFurnaceFire;
import mindustry.content.Items;
import mindustry.gen.Sounds;
import mindustry.type.*;
import mindustry.world.Block;
import mindustry.world.draw.*;

import static mindustry.type.ItemStack.with;

public class CICraftingBlocks {
    public static Block
            graphitePress, glassFurnace;

    public static void load() {
        graphitePress = new ImpactCrafter("graphite-press"){{
            requirements(Category.crafting, with(CIItems.cobalt, 60, CIItems.hematite, 25));
            size = 2;
            health = 220;
            craftTime = 90f;
            craftEffect = CIFx.pressCrush;
            updateEffect = CIFx.pressDust;
            updateEffectChance = 0.04f;
            ambientSound = Sounds.loopMachine;
            ambientSoundVolume = 0.03f;
            outputItem = new ItemStack(Items.graphite, 1);
            consumeItem(Items.coal, 2);
        }};

        glassFurnace = new FurnaceCrafter("glass-furnace"){{
            requirements(Category.crafting, with(CIItems.cobalt, 80, CIItems.hematite, 40, CIItems.lithium, 30));
            size = 2;
            health = 240;
            craftTime = 60f;
            hasPower = true;
            craftEffect = CIFx.furnaceFlare;
            craftSound = Sounds.shootFlame;
            craftSoundVolume = 0.12f;
            igniteEffect = CIFx.furnaceIgnite;
            coolEffect = CIFx.furnaceCool;
            spatterEffect = CIFx.meltSpatter;
            drawer = new DrawMulti(new DrawDefault(), new DrawFurnaceFire());
            ambientSound = Sounds.loopSmelter;
            ambientSoundVolume = 0.05f;
            outputItem = new ItemStack(CIItems.lithiumGlass, 1);
            consumeItems(with(Items.sand, 2, CIItems.lithium, 1));
            consumePower(0.6f);
        }};
    }
}