package ci.world.blocks.production;

import arc.audio.Sound;
import arc.math.Mathf;
import mindustry.gen.Sounds;
import mindustry.world.blocks.production.GenericCrafter;

public class ImpactCrafter extends GenericCrafter {
    public Sound craftSound = Sounds.drillImpact;
    public float craftSoundVolume = 0.25f;

    public ImpactCrafter(String name) {
        super(name);
    }

    public class ImpactCrafterBuild extends GenericCrafterBuild {

        @Override
        public void craft() {
            super.craft();
            craftSound.at(x, y, Mathf.random(0.85f, 1.05f), craftSoundVolume);
        }
    }
}