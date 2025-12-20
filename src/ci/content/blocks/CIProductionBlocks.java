package ci.content.blocks;

import ci.content.*;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.production.*;

import static mindustry.type.ItemStack.with;

public class CIProductionBlocks {
    public static Block
            rustyDrill;

    public static void load() {
        rustyDrill = new Drill("rustyDrill"){{
            requirements(Category.production, with(CIItems.iron, 24));
            tier = 1;
            drillTime = 350;
            size = 2;
            alwaysUnlocked = true;
        }};
    }
}