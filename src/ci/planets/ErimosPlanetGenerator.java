package ci.planets;

import arc.graphics.*;
import arc.math.geom.*;
import arc.util.noise.*;
import mindustry.game.*;
import mindustry.maps.generators.*;
import mindustry.type.*;
import mindustry.world.*;

import static ci.content.blocks.CIEnvironmentBlocks.*;

public class ErimosPlanetGenerator extends PlanetGenerator {
    public float lowland = 0.4f, plateau = 0.62f;

    static final Color
            glassDark = Color.valueOf("1f1917"),
            glass = Color.valueOf("332926"),
            dolomite = Color.valueOf("524339"),
            sandDark = Color.valueOf("bf9a76"),
            sand = Color.valueOf("d3ae8d"),
            sandLight = Color.valueOf("e3c4a4"),
            sandstoneDark = Color.valueOf("7e5a3c"),
            sandstone = Color.valueOf("a07850"),
            hematite = Color.valueOf("78403a"),
            hematiteLight = Color.valueOf("965f54");

    public Vec3[] craters = {new Vec3(-0.6f, 0.35f, 0.7f).nor(), new Vec3(0.55f, -0.3f, -0.75f).nor()};
    public float[] craterRadii = {0.17f, 0.13f};

    float height, base, ridge, crater;

    void sample(Vec3 p){
        base = Simplex.noise3d(seed, 5, 0.5, 0.9, p.x, p.y, p.z);

        crater = 9f;
        for(int i = 0; i < craters.length; i++) crater = Math.min(crater, p.dst(craters[i]) / craterRadii[i]);

        ridge = Ridged.noise3d(seed + 1, p.x, p.y, p.z, 2, 2f);
        if(base < lowland){
            height = 0.02f;
        }else if(base < plateau){
            height = 0.05f + (base - lowland) * 0.3f;
        }else{
            height = 0.16f + (base - plateau) * 0.5f;
            if(ridge > 0.36f) height += (ridge - 0.36f) * 0.8f;
        }

        if(crater < 0.85f) height = 0.02f;
        else if(crater < 1.25f) height = Math.max(height, 0.16f);
    }

    @Override
    public float getHeight(Vec3 position){
        sample(position);
        return height;
    }

    @Override
    public void getColor(Vec3 position, Color out){
        sample(position);
        float detail = Simplex.noise3d(seed + 2, 3, 0.5, 3.0, position.x, position.y, position.z);

        if(crater < 0.85f || (base < lowland && crater > 1.25f)){
            out.set(detail > 0.58f ? dolomite : detail > 0.45f ? glass : glassDark).a(1f);
        }else if(crater < 1.25f){
            out.set(detail > 0.5f ? sandstone : sandstoneDark).a(1f);
        }else if(base >= plateau && ridge > 0.36f){
            out.set(detail > 0.5f ? hematiteLight : hematite).a(1f);
        }else if(base >= plateau){
            out.set(detail > 0.5f ? sandstone : sandstoneDark).a(1f);
        }else{
            out.set(detail > 0.62f ? sandLight : detail > 0.38f ? sand : sandDark).a(1f);
        }
    }

    public static boolean debugLanding = true;

    @Override
    public boolean allowLanding(Sector sector){
        return debugLanding || super.allowLanding(sector);
    }

    @Override
    public int getSectorSize(Sector sector){
        return 60;
    }

    @Override
    protected void genTile(Vec3 p, TileGen tile){
        float n = Simplex.noise3d(seed + 3, 3, 0.5, 60.0, p.x, p.y, p.z);
        tile.floor = n < 0.45f ? sandstoneFloor : n < 0.58f ? hematiteFloor : n < 0.68f ? dolomiteFloor : tektiteFloor;
    }

    @Override
    protected void generate(){
        tiles.getn(tiles.width / 2, tiles.height / 2).setBlock(sector.planet.defaultCore, Team.sharded);
    }
}