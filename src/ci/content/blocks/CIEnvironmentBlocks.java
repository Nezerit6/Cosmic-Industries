package ci.content.blocks;

import ci.content.CIItems;
import mindustry.graphics.CacheLayer;
import mindustry.world.Block;
import mindustry.world.blocks.environment.*;

public class CIEnvironmentBlocks {
    public static Block
            hematiteOre, ironOre, cobaltOre, iridiumOre,

    duneSand, gert, mercuryMud, graysand, dune, dehydrate,
            chugalitra, echugalite, lechugate,
            pinkgrass, darkgrass, pinkstone,

    gertWall, chugalitraWall, echugaliteWall, lechugateWall,
            dunecliffWall, darkmossWall, lightmossWall,

    gertBoulder, chugalitraBoulder, lechugateBoulder,

    chugalitraWater, echugaliteWater, lechugateWater;

    public static void load() {
        ironOre = new OreBlock(CIItems.iron){{
            oreDefault = true;
            oreThreshold = 0.93f;
            oreScale = 21.42365f;
            variants = 3;
        }};

        hematiteOre = new OreBlock(CIItems.hematite){{
            oreDefault = true;
            oreThreshold = 0.93f;
            oreScale = 21.42365f;
            variants = 3;
        }};

        cobaltOre = new OreBlock(CIItems.cobalt){{
            oreDefault = true;
            oreThreshold = 0.93f;
            oreScale = 21.42365f;
            variants = 3;
        }};

        iridiumOre = new OreBlock(CIItems.iridium){{
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

        mercuryMud = new Floor("mercury-mud"){{
            variants = 3;
        }};

        graysand = new Floor("graysand"){{
            variants = 3;
        }};

        dune = new Floor("dune"){{
            variants = 3;
        }};

        dehydrate = new Floor("dehydrate"){{
            variants = 3;
        }};

        chugalitra = new Floor("chugalitra"){{
            variants = 3;
        }};

        echugalite = new Floor("echugalite"){{
            variants = 3;
        }};

        lechugate = new Floor("lechugate"){{
            variants = 3;
        }};

        pinkgrass = new Floor("pinkgrass"){{
            variants = 3;
        }};

        darkgrass = new Floor("darkgrass"){{
            variants = 3;
        }};

        pinkstone = new Floor("pinkstone"){{
            variants = 3;
        }};

        gertBoulder = new Prop("gert-boulder"){{
            variants = 2;
            squareSprite = true;
            gert.asFloor().decoration = this;
        }};

        chugalitraBoulder = new Prop("chugalitraBoulder"){{
            variants = 3;
            squareSprite = true;
            chugalitra.asFloor().decoration = this;
        }};

        lechugateBoulder = new Prop("lechugateBoulder"){{
            variants = 3;
            squareSprite = true;
            lechugate.asFloor().decoration = this;
        }};

        gertWall = new StaticWall("gert-wall"){{
            variants = 2;
        }};

        chugalitraWall = new StaticWall("chugalitraWall"){{
            variants = 3;
        }};

        echugaliteWall = new StaticWall("echugaliteWall"){{
            variants = 3;
        }};

        lechugateWall = new StaticWall("lechugateWall"){{
            variants = 3;
        }};

        dunecliffWall = new StaticWall("dunecliffWall"){{
            variants = 2;
        }};

        darkmossWall = new StaticWall("darkmossWall"){{
            variants = 2;
        }};

        lightmossWall = new StaticWall("lightmossWall"){{
            variants = 2;
        }};

        chugalitraWater = new Floor("chugalitraWater"){{
            speedMultiplier = 0.35f;
            variants = 3;
            isLiquid = true;
            cacheLayer = CacheLayer.water;
            albedo = 0.9f;
            supportsOverlay = true;
        }};

        echugaliteWater = new Floor("echugaliteWater"){{
            speedMultiplier = 0.35f;
            variants = 3;
            isLiquid = true;
            cacheLayer = CacheLayer.water;
            albedo = 0.9f;
            supportsOverlay = true;
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