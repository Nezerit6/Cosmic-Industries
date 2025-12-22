package ci.content.blocks;

import ci.content.*;
import ci.world.blocks.power.HydroTurbine;
import mindustry.content.Liquids;
import mindustry.gen.Sounds;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.production.SolidPump;
import mindustry.world.draw.*;
import mindustry.world.meta.*;

import static mindustry.type.ItemStack.with;

public class CIPowerBlocks {
    public static Block
            mechanicalTurbine, hydroTurbine;

    public static void load() {
        mechanicalTurbine = new SolidPump("mechanicalTurbine"){{
            requirements(Category.power, BuildVisibility.sandboxOnly, with(CIItems.cobalt, 1));
            squareSprite = false;
            result = Liquids.water;
            pumpAmount = 0.11f;
            size = 2;
            liquidCapacity = 6f;
            rotateSpeed = 1.4f;

            consumePower(1.5f);
        }};

        hydroTurbine = new HydroTurbine("hydroTurbine"){{
            requirements(Category.power, BuildVisibility.sandboxOnly, with(CIItems.cobalt, 50));

            size = 2;
            squareSprite = false;
            powerProduction = 1.2f;
            pumpAmount = 0.1f;
            liquidCapacity = 70f;

            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
            new DrawRegion("-rotor"){{
                        spinSprite = true;
                        rotateSpeed = 2f;
                    }},
                    new DrawLiquidRegion(),
                    new DrawDefault()
            );


            ambientSound = Sounds.hum;
            ambientSoundVolume = 0.06f;
        }};
    }
}