package ci.content.blocks;

import ci.content.*;
import ci.world.blocks.distribution.PipeConveyor;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.meta.*;

import static mindustry.type.ItemStack.with;

public class CIDistributionBlocks {
    public static Block
            pipeConveyor;

    public static void load() {
        pipeConveyor = new PipeConveyor("pipeConveyor"){{
            requirements(Category.distribution, BuildVisibility.sandboxOnly, with(CIItems.cobalt, 1));
            health = 45;
            speed = 0.04f;
            displayedSpeed = 6f;
            buildCostMultiplier = 2f;
            researchCost = with(CIItems.cobalt, 5);
        }};
    }
}