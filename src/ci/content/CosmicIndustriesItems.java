package ci.content;

import arc.graphics.*;
import arc.struct.Seq;
import mindustry.type.*;

public class CosmicIndustriesItems {
        public static Item hematite, iron, lithium,
        magnesium, misunese;

    public static final Seq<Item> noviaItems = new Seq<>();
    public static final Seq<Item> octaviaItems = new Seq<>();
    public static void load() {
        hematite = new Item("hematite", Color.valueOf("ffc88cff")) {{
            hardness = 1;
        }};
        iron = new Item("iron", Color.valueOf("5b5b5bff")) {{
            hardness = 1;
        }};
        lithium = new Item("lithium") {{
            explosiveness = 2;
        }};
        noviaItems.addAll(
        iron, hematite, lithium
        );

        magnesium = new Item("magnesium") {{
            hardness = 1;
            explosiveness = 0.01f;
            charge = 0.05;

        }};

        misunese = new Item("misunese", Color.valueOf("ca70e5")){{
            hardness = 2;
        }};

        octaviaItems.addAll(
                magnesium, misunese
        );
    }
}
