package ci.content;

import ci.content.blocks.*;

public class CIBlocks {

    public static void load(){
        CIEnvironmentBlocks.load();
        CIDistributionBlocks.load();
        CILiquidBlocks.load();
        CIPowerBlocks.load();
        CIProductionBlocks.load();
        CICraftingBlocks.load();
        CIDefenseBlocks.load();
        CIStorageBlocks.load();
    }
}