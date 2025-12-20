package ci.content.blocks;

import ci.content.*;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.distribution.*;

import static mindustry.type.ItemStack.with;

public class CIDistributionBlocks {
    public static Block
            pipeConveyor;

    public static void load() {
        pipeConveyor = new Conveyor("pipeConveyor"){{
            requirements(Category.distribution, with(CIItems.iron, 1));
            health = 30;
            speed = 0.07f;
            displayedSpeed = 6f;
            buildCostMultiplier = 2f;
            researchCost = with(CIItems.iron, 5);
        }};
    }
}