package ci.content;



import static mindustry.content.TechTree.*;

public class CosmicIndustriesTechTree {
    public static void load() {
        CosmicIndustriesPlanets.novia.techTree = nodeRoot("Novia", CosmicIndustriesBlocks.coreHeart, () -> {
            nodeProduce(CosmicIndustriesItems.iron, () -> {
                node(CosmicIndustriesItems.hematite, () -> {
                });
            });
            node(CosmicIndustriesBlocks.ironDrill, () -> {
            });
            node(CosmicIndustriesBlocks.shoker, () -> {
                 node(CosmicIndustriesBlocks.ironWall, () -> {
                      node(CosmicIndustriesBlocks.ironWallLarge, () -> {
                      });
                 });
            });
        });
    }
}