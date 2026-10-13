package ci;

import mindustry.mod.*;
import ci.content.*;

public class CosmicIndustries extends Mod {

    @Override
    public void loadContent() {

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
}