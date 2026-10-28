package ci.content;

import arc.audio.Sound;

import static mindustry.Vars.tree;

public class CISounds {
    public static Sound bigLaserShoot, smallLaserShoot, pressImpact, capsuleLand, capsuleThrust, capsuleImpact;

    public static void load() {
        bigLaserShoot = tree.loadSound("bigLaserShoot");
        smallLaserShoot = tree.loadSound("smallLaserShoot");
        pressImpact = tree.loadSound("pressImpact");
        capsuleLand = tree.loadSound("capsuleLand");
        capsuleThrust = tree.loadSound("capsuleThrust");
        capsuleImpact = tree.loadSound("capsuleImpact");
    }
}
