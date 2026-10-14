package ci.content.blocks;

import ci.content.CIItems;
import ci.world.blocks.distribution.CIConveyor;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.distribution.Router;

import static mindustry.type.ItemStack.with;

public class CIDistributionBlocks {
    public static Block
            conveyor, router;

    public static void load() {
        conveyor = new CIConveyor("conveyor"){{
            requirements(Category.distribution, with(CIItems.cobalt, 1));
            health = 45;
            speed = 0.04f;
            displayedSpeed = 6f;
            buildCostMultiplier = 2f;
        }};

        router = new Router("router"){{
            requirements(Category.distribution, with(CIItems.cobalt, 3));
            buildCostMultiplier = 4f;
        }};
    }
}