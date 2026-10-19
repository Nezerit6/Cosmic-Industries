package ci.content.blocks;

import ci.content.CIItems;
import ci.world.blocks.environment.CICliff;
import ci.world.blocks.environment.CIFloor;
import mindustry.content.Items;
import mindustry.world.Block;
import mindustry.world.blocks.environment.*;

public class CIEnvironmentBlocks {
    public static Block
            sandstoneFloor, dolomiteFloor, hematiteFloor, tektiteFloor,
            sandstoneWall, dolomiteWall, hematiteRock, tektiteWall,
            coalOre, hematiteOre, cliff;

    public static void load() {
        sandstoneFloor = new CIFloor("sandstone-floor", 3){{
            itemDrop = Items.sand;
        }};
        dolomiteFloor = new CIFloor("dolomite-floor", 4);
        hematiteFloor = new CIFloor("hematite-floor", 4);
        tektiteFloor = new CIFloor("tektite-floor", 4);

        sandstoneWall = new StaticWall("sandstone-wall"){{
            variants = 2;
        }};
        dolomiteWall = new StaticWall("dolomite-wall"){{
            variants = 2;
        }};
        hematiteRock = new StaticWall("hematite-rock"){{
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

        cliff = new CICliff("cliff");
    }
}