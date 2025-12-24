package ci.graphics;

import arc.*;
import arc.assets.loaders.TextureLoader.*;
import arc.graphics.*;
import arc.graphics.Texture.*;
import arc.graphics.g2d.*;
import arc.math.*;
import ci.world.meta.*;
import mindustry.graphics.Layer;
import mindustry.type.Weather;

import static mindustry.Vars.*;

public class CIEnvRenderer {

    static Color[] sandyColors = {
            Color.valueOf("848773"),
            Color.valueOf("6b5d4f"),
            Color.valueOf("6b7265")
    };

    public static void init() {
        Rand rand = new Rand();

        renderer.addEnvRenderer(CIEnv.sandy, () -> {
            String texturePath = "sprites/distortAlpha.png";//noiseAlpha same cool

            if(!Core.assets.isLoaded(texturePath, Texture.class)){
                Core.assets.load(texturePath, Texture.class, new TextureParameter(){{
                    magFilter = TextureFilter.linear;
                    minFilter = TextureFilter.mipMapLinearLinear;
                    wrapU = TextureWrap.repeat;
                    wrapV = TextureWrap.repeat;
                    genMipMaps = true;
                }});
                Core.assets.finishLoadingAsset(texturePath);
            }

            Texture tex = Core.assets.get(texturePath, Texture.class);

            tex.setWrap(TextureWrap.repeat, TextureWrap.repeat);
            tex.setFilter(TextureFilter.linear, TextureFilter.linear);

            rand.setSeed(((long) world.width() * world.height()));
            Color selectedColor = sandyColors[rand.random(0, 2)];

            Draw.z(state.rules.fog ? Layer.fogOfWar + 1 : Layer.weather - 1);

            Weather.drawNoiseLayers(
                    tex,
                    selectedColor,
                    1000f,
                    0.24f,
                    0.4f,
                    1f,
                    1f,
                    0f,
                    4,
                    -1.3f,
                    0.7f,
                    0.8f,
                    0.9f
            );

            Draw.reset();
        });
    }
}
