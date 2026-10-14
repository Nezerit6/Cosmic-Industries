package ci.planets;

import arc.graphics.Color;
import arc.math.Mathf;
import arc.math.geom.Vec3;
import arc.struct.Seq;
import arc.util.noise.Simplex;
import ci.content.blocks.CIEnvironmentBlocks;
import ci.content.blocks.CIStorageBlocks;
import mindustry.content.Blocks;
import mindustry.maps.generators.PlanetGenerator;
import mindustry.world.Block;
import mindustry.world.TileGen;

import static mindustry.Vars.state;

public class ErimosPlanetGenerator extends PlanetGenerator {
    public Block[]
            floors = {CIEnvironmentBlocks.tektiteFloor, CIEnvironmentBlocks.sandstoneFloor, CIEnvironmentBlocks.hematiteFloor, CIEnvironmentBlocks.dolomiteFloor},
            walls = {CIEnvironmentBlocks.tektiteWall, CIEnvironmentBlocks.sandstoneWall, CIEnvironmentBlocks.hematiteRock, CIEnvironmentBlocks.dolomiteWall};

    public float rawHeight(Vec3 position) {
        return Simplex.noise3d(seed, 6, 0.5, 1f / 2.5f, position.x, position.y, position.z);
    }

    public int index(Vec3 position) {
        return Mathf.clamp((int)(rawHeight(position) * floors.length), 0, floors.length - 1);
    }

    @Override
    public float getHeight(Vec3 position) {
        return Math.max(rawHeight(position) - 0.3f, 0f) * 0.4f;
    }

    @Override
    public void getColor(Vec3 position, Color out) {
        out.set(floors[index(position)].mapColor);
    }

    @Override
    protected void genTile(Vec3 position, TileGen tile) {
        int i = index(position);
        tile.floor = floors[i];
        float wall = Simplex.noise3d(seed + 1, 4, 0.5, 1f / 0.09f, position.x, position.y, position.z);
        if(wall > 0.62f) {
            tile.block = walls[i];
        }
    }

    @Override
    protected void generate() {
        int cx = width / 2, cy = height / 2;
        int sx = width / 8, sy = height / 8;

        distort(10f, 12f);
        erase(cx, cy, 14);
        erase(sx, sy, 8);
        for(int i = 0; i <= 40; i++) {
            erase((int)Mathf.lerp(cx, sx, i / 40f), (int)Mathf.lerp(cy, sy, i / 40f), 3);
        }

        ores(Seq.with(CIEnvironmentBlocks.coalOre, CIEnvironmentBlocks.hematiteOre));
        decoration(0.02f);

        tiles.getn(sx, sy).setOverlay(Blocks.spawn);
        tiles.getn(cx, cy).setBlock(CIStorageBlocks.emergencyCapsule, state.rules.defaultTeam);

        state.rules.waves = true;
        state.rules.waveSpacing = 60f * 90f;
        state.rules.env = sector.planet.defaultEnv;
    }
}