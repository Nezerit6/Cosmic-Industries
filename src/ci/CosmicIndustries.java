package ci;

import arc.Core;
import mindustry.mod.*;
import ci.content.*;
import mindustry.ui.dialogs.LanguageDialog;

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