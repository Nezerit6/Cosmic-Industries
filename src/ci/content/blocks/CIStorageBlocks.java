package ci.content.blocks;

import ci.content.*;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.storage.*;
import mindustry.world.meta.BuildVisibility;

import static mindustry.type.ItemStack.with;

public class CIStorageBlocks {
    public static Block
            storage, coreHeart;

    public static void load() {
        storage = new StorageBlock("Core1"){{
            requirements(Category.effect, BuildVisibility.sandboxOnly, with(CIItems.cobalt, 170));
            size = 3;
            itemCapacity = 500;
            scaledHealth = 35;
        }};

        coreHeart = new CoreBlock("Core2"){{
            requirements(Category.effect, BuildVisibility.sandboxOnly, with(CIItems.cobalt, 800, CIItems.lithium, 500, CIItems.cobalith, 300));
            alwaysUnlocked = true;

            isFirstTier = true;
            health = 2000;
            itemCapacity = 3000;
            size = 4;
            unitCapModifier = 8;
            squareSprite = false;
        }};

    }
}