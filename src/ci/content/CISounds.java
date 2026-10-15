package ci.content;

import arc.audio.Sound;

import static mindustry.Vars.tree;

public class CISounds {
    public static Sound bigLaserShoot, smallLaserShoot, pressImpact;

    public static void load() {
        bigLaserShoot = tree.loadSound("bigLaserShoot");
        smallLaserShoot = tree.loadSound("smallLaserShoot");
        pressImpact = tree.loadSound("pressImpact");
    }
}