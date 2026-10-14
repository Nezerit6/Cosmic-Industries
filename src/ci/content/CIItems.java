package ci.content;

import arc.graphics.*;
import arc.struct.Seq;
import mindustry.content.Items;
import mindustry.type.*;

public class CIItems {
    public static Item cobalt, lithium, cobalith, hematite, lithiumGlass;

    public static final Seq<Item> noviaItems = new Seq<>();

    public static void load() {

        cobalt = new Item("cobalt", Color.valueOf("7a8c9e")){{
            hardness = 1;
            cost = 0.7f;
            healthScaling = 0.3f;
            alwaysUnlocked = true;
        }};

        lithium = new Item("lithium", Color.valueOf("a8c5e3")){{
            hardness = 1;
            cost = 1.2f;
            charge = 0.5f;
        }};

        cobalith = new Item("cobalith", Color.valueOf("804054")){{
            cost = 1.5f;
        }};

        hematite = new Item("hematite", CIPal.hematite){{
            hardness = 2;
            cost = 1f;
            healthScaling = 0.6f;
            alwaysUnlocked = true;
        }};

        lithiumGlass = new Item("lithium-glass", Color.valueOf("c9c4f0")){{
            cost = 1.4f;
            charge = 0.2f;
        }};

        noviaItems.addAll(
                cobalt, lithium, cobalith, hematite, lithiumGlass, Items.coal, Items.graphite
        );
    }
}