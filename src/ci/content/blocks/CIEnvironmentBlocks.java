package ci.content.blocks;

import ci.content.CIItems;
import mindustry.graphics.CacheLayer;
import mindustry.world.Block;
import mindustry.world.blocks.environment.*;

public class CIEnvironmentBlocks {
    public static Block
            hematiteOre, ironOre, duneSand, gert, gertBoulder, gertWall, mercuryMud,
            chugalitra, chugalitraBoulder, chugalitraWall, chugalitraWater,
            echugalite, echugaliteWall, echugaliteWater,
            lechugate, lechugateBoulder, lechugateWall, lechugateWater;

    public static void load() {
        ironOre = new OreBlock(CIItems.iron){{
            oreDefault = true;
            oreThreshold = 0.81f;
            oreScale = 23.47619f;
            variants = 3;
        }};

        hematiteOre = new OreBlock(CIItems.hematite){{
            oreDefault = true;
            oreThreshold = 0.93f;
            oreScale = 21.42365f;
            variants = 3;
        }};

        duneSand = new Floor("duneSand"){{
            variants = 2;
        }};

        gert = new Floor("gert"){{
            variants = 3;
        }};

        gertBoulder = new Prop("gert-boulder"){{
            variants = 2;
        }};

        gertWall = new StaticWall("gert-wall"){{
            variants = 2;
        }};

        mercuryMud = new Floor("mercury-mud"){{
            variants = 3;
        }};

        chugalitra = new Floor("chugalitra"){{
            variants = 3;
        }};

        chugalitraBoulder = new Prop("chugalitraBoulder"){{
            variants = 3;
            chugalitra.asFloor().decoration = this;
        }};

        chugalitraWall = new StaticWall("chugalitraWall"){{
            variants = 3;
        }};

        chugalitraWater = new Floor("chugalitraWater"){{
            speedMultiplier = 0.35f;
            variants = 3;
            isLiquid = true;
            cacheLayer = CacheLayer.water;
            albedo = 0.9f;
            supportsOverlay = true;
        }};

        echugalite = new Floor("echugalite"){{
            variants = 3;
        }};

        echugaliteWall = new StaticWall("echugaliteWall"){{
            variants = 3;
        }};

        echugaliteWater = new Floor("echugaliteWater"){{
            speedMultiplier = 0.35f;
            variants = 3;
            isLiquid = true;
            cacheLayer = CacheLayer.water;
            albedo = 0.9f;
            supportsOverlay = true;
        }};

        lechugate = new Floor("lechugate"){{
            variants = 3;
        }};

        lechugateWall = new StaticWall("lechugateWall"){{
            variants = 3;
        }};

        lechugateBoulder = new Prop("lechugateBoulder"){{
            variants = 3;
        }};

        lechugateWater = new Floor("lechugateWater"){{
            speedMultiplier = 0.35f;
            variants = 3;
            isLiquid = true;
            cacheLayer = CacheLayer.water;
            albedo = 0.9f;
            supportsOverlay = true;
        }};
    }
}