package ci.content;

import arc.graphics.Color;
import arc.math.Rand;
import arc.math.geom.Mat3D;
import arc.struct.Seq;
import arc.util.Tmp;
import ci.content.blocks.CIStorageBlocks;
import ci.graphics.DustRing;
import ci.planets.ErimosPlanetGenerator;
import mindustry.content.Blocks;
import mindustry.graphics.g3d.*;
import mindustry.type.Planet;
import mindustry.world.meta.Env;

public class CIPlanets {
    public static Planet chromis, erimos, novia, octavia;

    public static void load() {
        chromis = new Planet("chromis", null, 4.5f){{
            bloom = true;
            accessible = false;
            lightColor = Color.valueOf("ffd9a8");
            meshLoader = () -> new SunMesh(
                    this, 4, 5, 0.3, 1.7, 1.2, 1, 1.1f,
                    Color.valueOf("e86a2e"),
                    Color.valueOf("f2873c"),
                    Color.valueOf("f9a650"),
                    Color.valueOf("fdbf6a"),
                    Color.valueOf("ffd48e"),
                    Color.valueOf("ffe5b8")
            );
        }};

        erimos = new Planet("erimos", chromis, 1f, 3){{
            generator = new ErimosPlanetGenerator();
            meshLoader = () -> new HexMesh(this, 6);
            cloudMeshLoader = () -> new DustRing(this, Color.valueOf("d3ae8d"),
                    new float[]{1.65f, 1.75f, 1.84f, 2f, 2.1f},
                    new float[]{1.74f, 1.83f, 1.96f, 2.06f, 2.15f},
                    new float[]{0.6f, 1f, 1.2f, 0.7f, 0.35f});
            camRadius = 0.6f;
            defaultCore = CIStorageBlocks.emergencyCapsule;
            atmosphereColor = Color.valueOf("1a2030");
            atmosphereRadIn = 0.02f;
            atmosphereRadOut = 0.14f;
            iconColor = Color.valueOf("947f6b");
            landCloudColor = Color.valueOf("6b5d4f");
            orbitRadius = 40f;
            startSector = 15;
            alwaysUnlocked = true;
            accessible = true;
            clearSectorOnLose = true;
            allowLaunchToNumbered = false;
        }};

        novia = asteroid("novia", chromis, 62f, 0.17f, 1f, 3, 20f, 0.45f, 4, 0.5f, 0.85f,
                Color.valueOf("6b7265"), Color.valueOf("3a2e2b"),
                Color.valueOf("403930"), Blocks.water.mapColor, Color.valueOf("848773"), Color.valueOf("5a5465"));

        octavia = asteroid("octavia", chromis, 75f, 0.15f, 2f, 0, 14f, 0.5f, 8, 0.44f, 1.4f,
                Color.valueOf("848773"), Color.valueOf("5a5465"),
                Color.valueOf("848773"));
    }

    static Planet asteroid(String name, Planet parent, float orbit, float radius, float scale, int seed, float bodyMag, float tintThresh,
                           int pieces, float spreadMin, float spreadMax, Color base, Color tint, Color... pieceColors) {
        return new Planet(name, parent, radius){{
            orbitRadius = orbit;
            hasAtmosphere = false;
            updateLighting = false;
            camRadius = 0.68f * scale;
            minZoom = 0.6f;
            accessible = false;
            clipRadius = 2f;
            defaultEnv = Env.space;
            icon = "commandRally";
            iconColor = base;

            meshLoader = () -> {
                Color tinted = tint.cpy().a(1f - tint.a);
                Seq<GenericMesh> meshes = new Seq<>();
                Rand rand = new Rand(id + 2);

                meshes.add(new NoiseMesh(
                        this, seed, 2, radius, 2, 0.55f, 0.45f, bodyMag,
                        base, tinted, 3, 0.6f, 0.38f, tintThresh
                ));

                for(int j = 0; j < pieces; j++) {
                    meshes.add(new MatMesh(
                            new NoiseMesh(this, seed + j + 1, 1, 0.022f + rand.random(0.039f) * scale, 2,
                                    0.6f, 0.38f, 20f,
                                    pieceColors[j % pieceColors.length], tinted, 3, 0.6f, 0.38f, tintThresh),
                            new Mat3D().setToTranslation(Tmp.v31.setToRandomDirection(rand).setLength(rand.random(spreadMin, spreadMax) * scale))
                    ));
                }

                return new MultiMesh(meshes.toArray(GenericMesh.class));
            };
        }};
    }
}