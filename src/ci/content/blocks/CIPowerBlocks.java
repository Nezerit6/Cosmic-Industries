package ci.content.blocks;

import ci.content.*;
import mindustry.content.Liquids;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.production.SolidPump;

import static mindustry.type.ItemStack.with;

public class CIPowerBlocks {
    public static Block
            mechanicalTurbine;

    public static void load() {
        mechanicalTurbine = new SolidPump("mechanicalTurbine"){{
            requirements(Category.power, with(CIItems.iron, 1));
            result = Liquids.water;
            pumpAmount = 0.11f;
            size = 2;
            liquidCapacity = 6f;
            rotateSpeed = 1.4f;

            consumePower(1.5f);
        }};
    }
}