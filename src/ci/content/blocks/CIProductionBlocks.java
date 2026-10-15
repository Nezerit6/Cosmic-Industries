package ci.content.blocks;

import ci.content.CIItems;
import ci.world.blocks.production.PileDrill;
import mindustry.type.Category;
import mindustry.world.Block;

import static mindustry.type.ItemStack.with;

public class CIProductionBlocks {
    public static Block
            pileDrill;

    public static void load() {
        pileDrill = new PileDrill("pile-drill"){{
            requirements(Category.production, with(CIItems.hematite, 15));
            size = 2;
            tier = 2;
            drillTime = 1100f;
            health = 160;
        }};
    }
}