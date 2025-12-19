package ci.content;

import mindustry.type.*;

import static ci.content.CIPlanets.novia;

public class CISectors {

    public static SectorPreset
    firstWay;

    public static void load() {

        firstWay = new SectorPreset("first-way", novia, 45){{
            alwaysUnlocked = true;
            captureWave = 0;
            difficulty = 0;
            startWaveTimeMultiplier = 0f;
        }};

    }
}