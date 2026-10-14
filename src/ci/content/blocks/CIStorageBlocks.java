package ci.content.blocks;

import ci.content.CIItems;
import mindustry.content.UnitTypes;
import mindustry.type.Category;
import mindustry.world.Block;
import ci.world.blocks.storage.CrashCore;
import mindustry.world.meta.BuildVisibility;

import static mindustry.type.ItemStack.with;

public class CIStorageBlocks {
    public static Block
            emergencyCapsule;

    public static void load() {
        emergencyCapsule = new CrashCore("emergency-capsule"){{
            requirements(Category.effect, BuildVisibility.editorOnly, with(CIItems.cobalt, 600, CIItems.hematite, 300));
            alwaysUnlocked = true;
            isFirstTier = true;
            size = 2;
            health = 900;
            itemCapacity = 2500;
            unitCapModifier = 6;
            unitType = UnitTypes.alpha;
        }};
    }
}