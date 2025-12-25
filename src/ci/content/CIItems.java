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
            cost = 0.8f;
            alwaysUnlocked = true;
        }};

        lithium = new Item("lithium", Color.valueOf("a8c5e3")){{
            hardness = 2;
            cost = 1.2f;
            alwaysUnlocked = true;
        }};

        cobalith = new Item("cobalith", Color.valueOf("804054")){{
            cost = 1.5f;
            alwaysUnlocked = true;
        }};

        noviaItems.addAll(
                cobalt, lithium, cobalith, Items.coal, Items.graphite
        );
    }
}