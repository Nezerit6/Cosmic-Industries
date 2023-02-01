package ci.content;



import mindustry.content.Blocks;
import mindustry.content.Items;

import static mindustry.content.TechTree.*;
import static mindustry.content.TechTree.nodeProduce;

public class CosmicIndustriesTechTree {
    public static void load() {
        CosmicIndustriesPlanets.novia.techTree = nodeRoot("Novia", CosmicIndustriesBlocks.coreHeart, () -> {
            nodeProduce(CosmicIndustriesItems.iron, () -> {
                node(CosmicIndustriesItems.hematite, () -> {
                    node(Items.graphite, () -> {
                    });
                });
            });

            nodeProduce(CosmicIndustriesBlocks.rustyDrill, () -> {

                node(CosmicIndustriesBlocks.shoker, () -> {
                });
                node(CosmicIndustriesBlocks.mechanicalPress, () -> {
                });
            });
        });
    }
}