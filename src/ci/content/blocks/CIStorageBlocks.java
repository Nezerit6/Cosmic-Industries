package ci.content.blocks;

import ci.content.*;
import ci.world.blocks.defense.RepairTurret;
import mindustry.content.Items;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.storage.*;
import mindustry.world.draw.DrawTurret;
import mindustry.world.meta.BuildVisibility;

import static mindustry.type.ItemStack.with;

public class CIStorageBlocks {
    public static Block
            storage, coreHeart, regeneratingBlaster;

    public static void load() {
        storage = new StorageBlock("Core1"){{
            requirements(Category.effect, BuildVisibility.sandboxOnly, with(CIItems.cobalt, 170));
            size = 3;
            itemCapacity = 500;
            scaledHealth = 35;
        }};

        coreHeart = new CoreBlock("Core2"){{
            requirements(Category.effect, BuildVisibility.sandboxOnly, with(CIItems.cobalt, 800));
            alwaysUnlocked = true;

            isFirstTier = true;
            unitType = CIUnits.falcon;
            health = 2000;
            itemCapacity = 3000;
            size = 4;
            unitCapModifier = 8;
            squareSprite = false;
        }};

        regeneratingBlaster = new RepairTurret("regeneratingBlaster"){{
            requirements(Category.effect, BuildVisibility.sandboxOnly, with(Items.copper, 50));

            consumePower(0.3f);
            size = 1;
            reload = 250f;
            range = 55f;
            healPercent = 18f;
            health = 140;

            rotateSpeed = 3;
            laserColor = Pal.heal;
            healColor = Pal.heal;
            drawer = new DrawTurret("based-");
        }};

    }
}