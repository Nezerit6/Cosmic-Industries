package ci.content.blocks;

import ci.content.CIItems;
import mindustry.content.Liquids;
import mindustry.graphics.CacheLayer;
import mindustry.world.Block;
import mindustry.world.blocks.environment.*;

public class CIEnvironmentBlocks {

    public static Block
            crackedStone, crackedStoneWall, crackedStoneWater,
            drySoil, drySoilWall, drySoilWater,
            hardenedClay, hardenedClayWall, hardenedClayWater, hardenedClayBoulder,
            slate, slateWall, slateWater, slateBoulder,
            sandstone, sandstoneWall, sandstoneWater, sandstoneBoulder,
            cobaltOre;

    public static void load() {

        cobaltOre = new OreBlock(CIItems.cobalt){{
            oreDefault = true;
            oreThreshold = 0.9f;
            oreScale = 20f;
            variants = 3;
        }};

        crackedStone = new Floor("cracked-stone"){{
            variants = 3;
        }};

        crackedStoneWall = new StaticWall("cracked-stone-wall"){{
            variants = 2;
        }};

        crackedStoneWater = new Floor("cracked-stone-water"){{
            variants = 3;
            isLiquid = true;
            liquidDrop = Liquids.water;
            liquidMultiplier = 0.5f;
            speedMultiplier = 0.35f;
            cacheLayer = CacheLayer.water;
            supportsOverlay = true;
        }};

        drySoil = new Floor("dry-soil"){{
            variants = 4;
        }};

        drySoilWall = new StaticWall("dry-soil-wall"){{
            variants = 2;
        }};

        drySoilWater = new Floor("dry-soil-water"){{
            variants = 4;
            isLiquid = true;
            liquidDrop = Liquids.water;
            liquidMultiplier = 0.5f;
            speedMultiplier = 0.35f;
            cacheLayer = CacheLayer.water;
            supportsOverlay = true;
        }};

        hardenedClay = new Floor("hardened-clay"){{
            variants = 3;
        }};

        hardenedClayWall = new StaticWall("hardened-clay-wall"){{
            variants = 3;
        }};

        hardenedClayWater = new Floor("hardened-clay-water"){{
            variants = 3;
            isLiquid = true;
            liquidDrop = Liquids.water;
            liquidMultiplier = 0.5f;
            speedMultiplier = 0.35f;
            cacheLayer = CacheLayer.water;
            supportsOverlay = true;
        }};

        hardenedClayBoulder = new Prop("hardened-clay-boulder"){{
            variants = 2;
            hardenedClay.asFloor().decoration = this;
        }};

        slate = new Floor("slate"){{
            variants = 3;
        }};

        slateWall = new StaticWall("slate-wall"){{
            variants = 2;
        }};

        slateBoulder = new Prop("slate-boulder"){{
            variants = 1;
            slate.asFloor().decoration = this;
        }};

        sandstone = new Floor("sandstone"){{
            variants = 3;
        }};

        sandstoneWall = new StaticWall("sandstone-wall"){{
            variants = 3;
        }};

        sandstoneWater = new Floor("sandstone-water"){{
            variants = 3;
            isLiquid = true;
            liquidDrop = Liquids.water;
            liquidMultiplier = 0.5f;
            speedMultiplier = 0.35f;
            cacheLayer = CacheLayer.water;
            supportsOverlay = true;
        }};

        sandstoneBoulder = new Prop("sandstone-boulder"){{
            variants = 3;
            sandstone.asFloor().decoration = this;
        }};
    }
}