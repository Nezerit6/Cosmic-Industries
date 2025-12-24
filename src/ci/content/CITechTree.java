package ci.content;

import ci.content.blocks.*;
import mindustry.content.Items;

import static mindustry.content.TechTree.*;
import static mindustry.content.TechTree.nodeProduce;

public class CITechTree {
    public static void load() {
        CIPlanets.novia.techTree = nodeRoot("Novia", CIStorageBlocks.coreHeart, () -> {
            nodeProduce(CIItems.cobalt, () -> {
                node(CIItems.lithium, () -> {
                    node(CIItems.cobalith, () -> {
                    });
                });
                node(Items.coal, () -> {
                    node(Items.graphite);
                });
            });

            nodeProduce(CIProductionBlocks.cobaltDrill, () -> {
                node(CICraftingBlocks.cobalithForge, () -> {
                    node(CICraftingBlocks.graphiteKiln, () -> {
                    });
                });
                node(CIPowerBlocks.hydroTurbine, () -> {
                    node(CIPowerBlocks.chargeNode, () -> {
                    });
                });
            });

            nodeProduce(CIDistributionBlocks.pipeConveyor, () -> {
            });
        });
    }
}