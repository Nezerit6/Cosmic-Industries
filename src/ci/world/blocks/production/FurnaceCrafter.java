package ci.world.blocks.production;

import arc.math.Mathf;
import mindustry.content.Fx;
import mindustry.entities.Effect;

public class FurnaceCrafter extends ImpactCrafter {
    public Effect igniteEffect = Fx.none, coolEffect = Fx.none, spatterEffect = Fx.none;
    public float coolChance = 0.2f;

    public FurnaceCrafter(String name) {
        super(name);
    }

    public class FurnaceCrafterBuild extends ImpactCrafterBuild {
        public boolean lit;
        public float heat;

        @Override
        public void updateTile() {
            super.updateTile();

            if(!lit && warmup > 0.5f) {
                lit = true;
                igniteEffect.at(x, y);
            }else if(lit && warmup < 0.2f) {
                lit = false;
            }

            heat = Math.max(heat - 0.004f * delta(), warmup);
            if(efficiency <= 0f && heat > 0.05f && Mathf.chanceDelta(coolChance * heat)) {
                coolEffect.at(x, y);
            }
        }

        @Override
        public void damage(float damage) {
            super.damage(damage);
            if(heat > 0.1f) {
                spatterEffect.at(x, y);
            }
        }
    }
}