package ci.content;

import arc.graphics.Color;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import ci.content.blocks.*;
import ci.planets.*;
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
    public static Planet chromis, novia;

    public static void load() {


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

            startSector = 177;
            sectorSeed = 8;

            hasAtmosphere = true;
            atmosphereRadIn = 0f;
            atmosphereRadOut = 0.3f;

            defaultEnv = Env.terrestrial;

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

                r.blockWhitelist = false;
                r.hideBannedBlocks = true;
                r.bannedBlocks.clear();

                r.bannedBlocks.addAll(Vars.content.blocks().select(block -> {
                    boolean onlyCosInd = block.minfo.mod != null && block.minfo.mod.name.equals("ci");
                    boolean sandboxOnly = block.buildVisibility == BuildVisibility.sandboxOnly;
                    return !onlyCosInd && !sandboxOnly;
                }));
            };
        }};

    }
}