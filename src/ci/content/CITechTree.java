package ci.content;

import ci.content.blocks.*;
import mindustry.content.Items;

import static mindustry.content.TechTree.*;
import static mindustry.content.TechTree.nodeProduce;

public class CITechTree {
    public static void load() {
        CIPlanets.novia.techTree = nodeRoot("Novia", CIStorageBlocks.emergencyCapsule, () -> {
            nodeProduce(CIItems.cobalt, () -> {
                node(CIItems.lithium, () -> {
                    node(CIItems.cobalith, () -> {
                    });
                });
                node(CIItems.hematite);
                node(Items.coal, () -> {
                    node(Items.graphite);
                });
            });

            node(CICraftingBlocks.graphitePress, () -> {
                node(CICraftingBlocks.glassFurnace, () -> {
                });
            });

            node(CIDistributionBlocks.conveyor, () -> {
                node(CIDistributionBlocks.router);
            });

            node(CIDefenseBlocks.hematiteWall, () -> {
                node(CIDefenseBlocks.hematiteWallLarge);
            });
        });
    }
}