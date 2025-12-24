package ci.content;

import arc.graphics.Color;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.Tmp;
import ci.content.blocks.*;
import ci.planets.*;
import ci.world.meta.CIEnv;
import mindustry.Vars;
import mindustry.content.*;
import mindustry.game.Team;
import mindustry.graphics.g3d.*;
import mindustry.maps.planet.*;
import mindustry.type.Planet;
import mindustry.world.*;
import mindustry.world.meta.BuildVisibility;
import mindustry.world.meta.Env;

public class CIPlanets {
    public static Planet chromis, novia, vermosa, octavia;

    public static void load() {

        Planets.serpulo.hiddenItems.addAll(Vars.content.items()).removeAll(Items.serpuloItems);
        Planets.erekir.hiddenItems.addAll(Vars.content.items()).removeAll(Items.erekirItems);

        chromis = new Planet("chromis", null, 6, 1) {{
            accessible = false;
            solarSystem = this;
            hasAtmosphere = true;

            meshLoader = () -> new SunMesh(
                    this, 4, 5, 0.3, 1.7, 1.2, 1, 1.3f,
                    Color.valueOf("F7F092"),
                    Color.valueOf("FCF36D"),
                    Color.valueOf("F3E73B"),
                    Color.valueOf("F9EB28"),
                    Color.valueOf("E3D51B"),
                    Color.valueOf("CABD09")
            );

            bloom = true;
            atmosphereColor = Color.valueOf("F9EB28");
        }};

        novia = new Planet("novia", chromis, 1f, 3) {{
            solarSystem = chromis;

            atmosphereColor = Color.valueOf("#41384a");
            iconColor = Color.valueOf("#947f6b");
            landCloudColor = Color.valueOf("#6b5d4f");

            accessible = true;
            alwaysUnlocked = true;
            clearSectorOnLose = true;
            allowLaunchToNumbered = false;

            orbitRadius = 58f;
            rotateTime = 30f * 60f;

            unlockedOnLand.add(CIItems.cobalt);
            itemWhitelist.addAll(CIItems.noviaItems);

            startSector = 177;
            sectorSeed = 8;

            hasAtmosphere = true;
            atmosphereRadIn = 0f;
            atmosphereRadOut = 0.3f;

            defaultEnv = Env.terrestrial | CIEnv.sandy;

            generator = new NoviaPlanetGenerator();

            meshLoader = () -> new MultiMesh(
                    new HexMesh(this, 7)
            );

            cloudMeshLoader = () -> new MultiMesh(
                    new HexSkyMesh(this, 6, -0.5f, 0.14f, 6,
                            Color.valueOf("9b9e88").a(0.2f), 2, 0.42f, 1f, 0.6f),
                    new HexSkyMesh(this, 4, 0.6f, 0.15f, 6,
                            Color.valueOf("9b9e88").a(0.25f), 2, 0.42f, 1.2f, 0.5f),
                    new HexSkyMesh(this, 2, 1.2f, 0.16f, 6,
                            Color.valueOf("9b9e88").a(0.15f), 2, 0.42f, 1.4f, 0.4f)
            );

            ruleSetter = r -> {
                r.waveTeam = Team.green;
                r.placeRangeCheck = false;
                r.showSpawns = true;
                r.fog = false;
                r.staticFog = true;
                r.lighting = false;
                r.coreDestroyClear = false;
                r.onlyDepositCore = false;

                r.env = defaultEnv;

                r.blockWhitelist = true;
                r.hideBannedBlocks = true;
                r.bannedBlocks.clear();

                r.bannedBlocks.addAll(Vars.content.blocks().select(block -> block.minfo.mod == null ||
                        !block.minfo.mod.name.equals("cosmic-industries")));
            };
        }};

        vermosa = new Planet("vermosa", chromis, 0.9f, 2) {{
            solarSystem = chromis;

            accessible = false;
            hasAtmosphere = true;

            orbitRadius = 37f;
            rotateTime = 40f * 60f;

            atmosphereRadIn = 0.01f;
            atmosphereRadOut = 0.25f;
            atmosphereColor = Color.valueOf("4A5A3A");

            generator = new VermosaPlanetGenerator();

            meshLoader = () -> new HexMesh(this, 6);

            cloudMeshLoader = () -> new MultiMesh(
                    new HexSkyMesh(this, 3, 0.5f, 0.18f, 6,
                            Color.valueOf("3A4A2A").a(0.75f), 2, 0.45f, 1f, 0.5f),
                    new HexSkyMesh(this, 4, -0.8f, 0.22f, 6,
                            Color.valueOf("2A3A1A").a(0.65f), 2, 0.45f, 1.1f, 0.48f),
                    new HexSkyMesh(this, 5, 1.2f, 0.25f, 6,
                            Color.valueOf("5A6A4A").a(0.55f), 2, 0.42f, 1.15f, 0.52f)
            );
        }};

        octavia = new Planet("octavia", chromis, 0.15f, 0) {{
            solarSystem = chromis;

            accessible = false;
            hasAtmosphere = false;
            updateLighting = false;
            drawOrbit = false;

            orbitRadius = 21f;
            rotateTime = 15f * 60f;

            camRadius = 0.68f * 2f;
            minZoom = 0.6f;
            clipRadius = 2f;

            Block base = CIEnvironmentBlocks.crackedStone;
            Block tint = CIEnvironmentBlocks.slate;

            generator = new AsteroidGenerator();

            meshLoader = () -> {
                Color tinted = tint.mapColor.cpy().a(1f - tint.mapColor.a);
                Seq<GenericMesh> meshes = new Seq<>();
                Color color = base.mapColor;
                Rand rand = new Rand(id + 2);

                meshes.add(new NoiseMesh(
                        this, 0, 2, radius, 2, 0.55f, 0.45f, 14f,
                        color, tinted, 3, 0.6f, 0.38f, 0.5f
                ));

                for(int j = 0; j < 8; j++) {
                    meshes.add(new MatMesh(
                            new NoiseMesh(this, j + 1, 1,
                                    0.022f + rand.random(0.039f) * 2f, 2,
                                    0.6f, 0.38f, 20f,
                                    color, tinted, 3, 0.6f, 0.38f, 0.5f),
                            new Mat3D().setToTranslation(
                                    Tmp.v31.setToRandomDirection(rand)
                                            .setLength(rand.random(0.44f, 1.4f) * 2f)
                            )
                    ));
                }

                return new MultiMesh(meshes.toArray(GenericMesh.class));
            };
        }};
    }
}