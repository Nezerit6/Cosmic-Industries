package ci.content.blocks;

import ci.content.CIItems;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.liquid.Conduit;
import mindustry.world.blocks.liquid.LiquidRouter;
import mindustry.world.blocks.production.Pump;

import static mindustry.type.ItemStack.with;

public class CILiquidBlocks {
    public static Block
            lazyPump, liquidCistern, liquidLargeCistern;

    public static void load() {
        lazyPump = new Pump("lazyPump"){{
            requirements(Category.liquid, with(CIItems.iron, 10));
            size = 1;
            pumpAmount = 3f / 60f;
            squareSprite = false;
            placeableLiquid = true;
        }};

        liquidCistern = new LiquidRouter("liquidCistern"){{
            requirements(Category.liquid, with(CIItems.iron, 70, CIItems.hematite, 20));
            size = 2;
            solid = true;
            liquidCapacity = 350;
            liquidPadding = 1.5f;
            health = 300;
            squareSprite = false;
        }};

        liquidLargeCistern = new LiquidRouter("liquidLargeCistern"){{
            requirements(Category.liquid, with(CIItems.iron, 110, CIItems.hematite, 45));
            size = 3;
            solid = true;
            liquidCapacity = 800f;
            liquidPadding = 2f;
            health = 550;
            squareSprite = false;
        }};
    }
}