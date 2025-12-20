package ci.content;

import ci.content.blocks.*;
import mindustry.content.Items;

import static mindustry.content.TechTree.*;
import static mindustry.content.TechTree.nodeProduce;

public class CITechTree {
    public static void load() {
        CIPlanets.novia.techTree = nodeRoot("Novia", CIStorageBlocks.coreHeart, () -> {
            nodeProduce(CIItems.iron, () -> {
                node(CIItems.hematite, () -> {
                    node(Items.graphite, () -> {
                    });
                });
            });

            nodeProduce(CIProductionBlocks.rustyDrill, () -> {

                node(CIDefenseBlocks.shoker, () -> {
                });
                node(CICraftingBlocks.mechanicalPress, () -> {
                });
            });
        });
    }
}