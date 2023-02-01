package ci;

import mindustry.mod.*;
import ci.content.*;

public class CosmicIndustries extends Mod {

    @Override
    public void loadContent() {

        /**Log.info("Loading some ci content.");*/

        CosmicIndustriesItems.load();
        /**CosmicIndustriesLiquids.load();
        CosmicIndustriesBullets.load();*/
        CosmicIndustriesUnits.load();
        CosmicIndustriesBlocks.load();
        CosmicIndustriesPlanets.load();
        CosmicIndustriesSectors.load();
        CosmicIndustriesTechTree.load();
    }
}