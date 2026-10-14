package ci.content;

import arc.graphics.Color;
import ci.content.blocks.CIStorageBlocks;
import ci.planets.NoviaPlanetGenerator;
import mindustry.content.Planets;
import mindustry.graphics.g3d.HexMesh;
import mindustry.type.Planet;

public class CIPlanets {
    public static Planet novia;

    public static void load() {
        novia = new Planet("novia", Planets.sun, 1f, 3){{
            generator = new NoviaPlanetGenerator();
            meshLoader = () -> new HexMesh(this, 5);
            defaultCore = CIStorageBlocks.emergencyCapsule;
            atmosphereColor = Color.valueOf("41384a");
            iconColor = Color.valueOf("947f6b");
            landCloudColor = Color.valueOf("6b5d4f");
            orbitRadius = 58f;
            startSector = 15;
            alwaysUnlocked = true;
            accessible = true;
            clearSectorOnLose = true;
            allowLaunchToNumbered = false;
        }};
    }
}