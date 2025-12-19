package ci.content;



import mindustry.content.Items;

import static mindustry.content.TechTree.*;
import static mindustry.content.TechTree.nodeProduce;

public class CITechTree {
    public static void load() {
        CIPlanets.novia.techTree = nodeRoot("Novia", CIBlocks.coreHeart, () -> {
            nodeProduce(CIItems.iron, () -> {
                node(CIItems.hematite, () -> {
                    node(Items.graphite, () -> {
                    });
                });
            });

            nodeProduce(CIBlocks.rustyDrill, () -> {

                node(CIBlocks.shoker, () -> {
                });
                node(CIBlocks.mechanicalPress, () -> {
                });
            });
        });
    }
}