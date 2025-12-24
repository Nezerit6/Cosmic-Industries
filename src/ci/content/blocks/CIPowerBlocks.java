package ci.content.blocks;

import ci.content.*;
import ci.world.blocks.power.HydroTurbine;
import mindustry.gen.Sounds;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.power.PowerNode;
import mindustry.world.draw.*;
import mindustry.world.meta.*;

import static mindustry.type.ItemStack.with;

public class CIPowerBlocks {
    public static Block
            hydroTurbine, chargeNode;

    public static void load() {

        hydroTurbine = new HydroTurbine("hydro-turbine"){{
            requirements(Category.power, BuildVisibility.sandboxOnly, with(CIItems.cobalt, 15));

            size = 2;
            squareSprite = false;
            powerProduction = 0.45f;
            pumpAmount = 12f / 60f;
            liquidCapacity = 30f;

            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
            new DrawRegion("-rotor"){{
                        spinSprite = true;
                        rotateSpeed = 1f;
                    }},
                    new DrawLiquidRegion(),
                    new DrawDefault()
            );

            ambientSound = Sounds.hum;
            ambientSoundVolume = 0.06f;
        }};

        chargeNode = new PowerNode("charge-node"){{
            requirements(Category.power, BuildVisibility.sandboxOnly, with(CIItems.lithium, 5));

        }};
    }
}