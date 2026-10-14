package ci.content.blocks;

import ci.content.CIItems;
import mindustry.content.Items;
import mindustry.world.Block;
import mindustry.world.blocks.environment.*;

public class CIEnvironmentBlocks {
    public static Block
            sandstoneFloor, concreteFloor, dolomiteFloor, hematiteFloor, tektiteFloor,
            sandstoneWall, concreteWall, dolomiteWall, oldHematiteRock, tektiteWall,
            coalOre, hematiteOre;

    public static void load() {
        sandstoneFloor = new Floor("sandstone-floor", 3){{
            itemDrop = Items.sand;
        }};
        concreteFloor = new Floor("concrete-floor", 4);
        dolomiteFloor = new Floor("dolomite-floor", 4);
        hematiteFloor = new Floor("hematite-floor", 4);
        tektiteFloor = new Floor("tektite-floor", 4);

        sandstoneWall = new StaticWall("sandstone-wall"){{
            variants = 2;
        }};
        concreteWall = new StaticWall("concrete-wall"){{
            variants = 4;
        }};
        dolomiteWall = new StaticWall("dolomite-wall"){{
            variants = 2;
        }};
        oldHematiteRock = new StaticWall("old-hematite-rock"){{
            variants = 4;
        }};
        tektiteWall = new StaticWall("tektite-wall"){{
            variants = 2;
        }};

        coalOre = new OreBlock("ore-coal", Items.coal){{
            oreDefault = true;
            oreThreshold = 0.86f;
            oreScale = 24f;
        }};
        hematiteOre = new OreBlock("ore-hematite", CIItems.hematite){{
            oreDefault = true;
            oreThreshold = 0.86f;
            oreScale = 22f;
        }};
    }
}