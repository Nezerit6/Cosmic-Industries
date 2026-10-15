package ci;

import mindustry.mod.*;
import ci.content.*;
import ci.graphics.draw3d.*;

import static mindustry.Vars.headless;

public class CosmicIndustries extends Mod {

    @Override
    public void loadContent() {
        CISounds.load();
        CIItems.load();
        CIBlocks.load();
        CIPlanets.load();
        CITechTree.load();
        /*
         * Initialization
         * - Teams
         * - Status effects
         * - Weather
         * - Items
         * - Liquids
         * - Bullet
         * - Units
         * - Blocks
         * - Planets(Sectors)
         * - Tech tree
         */
    }

    @Override
    public void init() {
        if(!headless) {
            Perspective.init();
            Draw3D.init();
        }
    }
}