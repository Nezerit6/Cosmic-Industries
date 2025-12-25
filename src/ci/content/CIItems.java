package ci.content;

import arc.graphics.*;
import arc.struct.Seq;
import mindustry.content.Items;
import mindustry.type.*;

public class CIItems {
    public static Item cobalt, lithium, cobalith;

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

        noviaItems.addAll(
                cobalt, lithium, cobalith, Items.coal, Items.graphite
        );
    }
}