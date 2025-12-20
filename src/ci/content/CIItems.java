package ci.content;

import arc.graphics.*;
import arc.struct.Seq;
import mindustry.type.*;

public class CIItems {
    public static Item hematite, iron, lithium, asfrit, cobalt, iridium;

    public static final Seq<Item> noviaItems = new Seq<>();

    public static void load() {
        hematite = new Item("hematite", Color.valueOf("ffc88cff")) {{
            hardness = 1;
        }};

        iron = new Item("iron", Color.valueOf("5b5b5bff")) {{
            hardness = 1;
            alwaysUnlocked = true;
        }};

        lithium = new Item("lithium") {{
            explosiveness = 2;
        }};

        asfrit = new Item("asfrit", Color.yellow){{
            hardness = 2;
        }};

        cobalt = new Item("cobalt", Color.valueOf("6b8ac9ff")){{
            hardness = 1;
        }};

        iridium = new Item("iridium", Color.valueOf("d4d4d4ff")){{
            hardness = 2;
        }};

        noviaItems.addAll(
                iron, hematite, lithium, asfrit, cobalt, iridium
        );
    }
}