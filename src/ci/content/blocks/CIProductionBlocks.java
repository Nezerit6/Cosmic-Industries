package ci.content.blocks;

import ci.content.*;
import mindustry.content.Liquids;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.production.*;
import mindustry.world.meta.*;

import static mindustry.type.ItemStack.with;

public class CIProductionBlocks {
    public static Block
            cobaltDrill;

    public static void load() {
        cobaltDrill = new Drill("cobaltDrill"){{
            requirements(Category.production, with(CIItems.cobalt, 12));
            tier = 1;
            drillTime = 770;
            size = 2;
            squareSprite = false;

            researchCost = with(CIItems.cobalt, 10);

            consumeLiquid(Liquids.water, 0.05f).boost();
        }};
    }
}