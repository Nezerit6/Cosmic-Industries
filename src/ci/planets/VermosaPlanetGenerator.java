package ci.planets;

import arc.graphics.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import arc.util.noise.*;
import mindustry.content.*;
import mindustry.maps.generators.*;
import mindustry.type.*;

public class VermosaPlanetGenerator extends PlanetGenerator {
    public Seq<HeightPass> heights = new Seq<>();
    public Seq<ColorPass> colors = new Seq<>();

    public float baseHeight = 0f;
    public Color baseColor = Blocks.sporeMoss.mapColor;

    public VermosaPlanetGenerator() {
        heights.add(new NoiseHeight() {{
            octaves = 5;
            persistence = 0.6f;
            magnitude = 0.8f;
            heightOffset = -0.2f;
            offset.set(500, 0, 0);
        }});

        heights.add(new NoiseHeight() {{
            seed = 2;
            octaves = 8;
            persistence = 0.4f;
            magnitude = 0.3f;
            heightOffset = 0f;
            offset.set(0, 500, 0);
        }});

        Mathf.rand.setSeed(15);
        for(int i = 0; i < 4; i++) {
            heights.add(new DotHeight() {{
                dir.setToRandomDirection().y = Mathf.random(1.5f, 2.5f);
                min = 0.99f;
                magnitude = 0.12f;
                interp = Interp.exp5In;
            }});
        }

        heights.add(new SphereHeight() {{
            pos.set(0.7f, 0.5f, 0.3f).nor();
            radius = 0.25f;
            offset = -0.3f;
            set = false;
        }});

        heights.add(new SphereHeight() {{
            pos.set(-0.6f, 0.4f, -0.5f).nor();
            radius = 0.22f;
            offset = -0.28f;
            set = false;
        }});

        Mathf.rand.setSeed(16);
        for(int i = 0; i < 8; i++) {
            heights.add(new SphereHeight() {{
                pos.setToRandomDirection();
                radius = 0.08f + Mathf.random(0.04f);
                offset = -0.15f;
                set = false;
            }});
        }

        heights.add(new ClampHeight(-0.2f, 0.6f));

        colors.add(new NoiseColorPass() {{
            scale = 2.0f;
            persistence = 0.5f;
            octaves = 6;
            magnitude = 1.4f;
            min = 0.3f;
            max = 0.7f;
            out = Blocks.sporeMoss.mapColor;
            offset.set(3000f, 500f, -700f);
        }});

        colors.add(new NoiseColorPass() {{
            seed = 3;
            scale = 1.6f;
            persistence = 0.6f;
            octaves = 5;
            magnitude = 1.2f;
            min = 0.2f;
            max = 0.5f;
            out = Blocks.moss.mapColor;
            offset.set(2000f, 300f, -600f);
        }});

        colors.add(new NoiseColorPass() {{
            seed = 7;
            scale = 1.8f;
            persistence = 0.5f;
            octaves = 4;
            magnitude = 1.3f;
            min = 0.25f;
            max = 0.55f;
            out = Color.valueOf("3A4A2A");
            offset.set(1800f, 400f, -500f);
        }});

        colors.add(new FlatColorPass() {{
            min = -0.2f;
            max = 0.05f;
            out = Blocks.taintedWater.mapColor;
        }});

        colors.add(new FlatColorPass() {{
            min = 0.05f;
            max = 0.15f;
            out = Blocks.darksandTaintedWater.mapColor;
        }});

        colors.add(new FlatColorPass() {{
            min = 0.15f;
            max = 0.35f;
            out = Blocks.sporeMoss.mapColor;
        }});

        colors.add(new FlatColorPass() {{
            min = 0.35f;
            max = 0.5f;
            out = Blocks.moss.mapColor;
        }});

        colors.add(new FlatColorPass() {{
            min = 0.5f;
            max = 1f;
            out = Color.valueOf("5A6A4A");
        }});
    }

    public float rawHeight(Vec3 position) {
        float height = baseHeight;
        for(HeightPass h : heights) {
            height = h.height(position, height);
        }
        return height;
    }

    @Override
    public void generateSector(Sector sector) {
    }

    @Override
    public float getHeight(Vec3 position) {
        return rawHeight(position);
    }

    @Override
    public Color getColor(Vec3 position) {
        Color color = baseColor;
        float height = rawHeight(position);

        for(ColorPass c : colors) {
            Color calculated = c.color(position, height);
            if(calculated != null) color = calculated;
        }
        return color;
    }

    public static abstract class HeightPass {
        public abstract float height(Vec3 position, float current);
    }

    public static class NoiseHeight extends HeightPass {
        public int octaves = 5;
        public float persistence = 0.5f;
        public float magnitude = 1f;
        public float heightOffset = 0f;
        public Vec3 offset = new Vec3();
        public int seed = 0;

        @Override
        public float height(Vec3 position, float current) {
            Tmp.v31.set(position).add(offset);
            float noise = Simplex.noise3d(seed, octaves, persistence, 1f,
                    Tmp.v31.x, Tmp.v31.y, Tmp.v31.z);
            return current + (noise * magnitude) + heightOffset;
        }
    }

    public static class DotHeight extends HeightPass {
        public Vec3 dir = new Vec3();
        public float min = 0.99f;
        public float magnitude = 0.3f;
        public Interp interp = Interp.linear;

        @Override
        public float height(Vec3 position, float current) {
            float dot = position.dot(dir.nor());
            if(dot >= min) {
                float scaled = (dot - min) / (1f - min);
                return current + interp.apply(scaled) * magnitude;
            }
            return current;
        }
    }

    public static class SphereHeight extends HeightPass {
        public Vec3 pos = new Vec3();
        public float radius = 0.1f;
        public float offset = 0f;
        public boolean set = false;

        @Override
        public float height(Vec3 position, float current) {
            float dist = position.dst(pos);
            if(dist < radius) {
                float val = offset - (1f - dist / radius) * 0.5f;
                return set ? val : current + val;
            }
            return current;
        }
    }

    public static class ClampHeight extends HeightPass {
        public float min, max;

        public ClampHeight(float min, float max) {
            this.min = min;
            this.max = max;
        }

        @Override
        public float height(Vec3 position, float current) {
            return Mathf.clamp(current, min, max);
        }
    }

    public static abstract class ColorPass {
        public abstract Color color(Vec3 position, float height);
    }

    public static class FlatColorPass extends ColorPass {
        public float min = 0f;
        public float max = 1f;
        public Color out = Color.white;

        @Override
        public Color color(Vec3 position, float height) {
            if(height >= min && height <= max) {
                return out;
            }
            return null;
        }
    }

    public static class NoiseColorPass extends ColorPass {
        public float scale = 1f;
        public float persistence = 0.5f;
        public int octaves = 3;
        public float magnitude = 1f;
        public float min = 0f;
        public float max = 1f;
        public Color out = Color.white;
        public Vec3 offset = new Vec3();
        public int seed = 0;

        @Override
        public Color color(Vec3 position, float height) {
            Tmp.v31.set(position).add(offset);
            float noise = Simplex.noise3d(seed, octaves, persistence, scale,
                    Tmp.v31.x, Tmp.v31.y, Tmp.v31.z);
            noise = (noise + 1f) / 2f;

            if(noise >= min && noise <= max) {
                return out;
            }
            return null;
        }
    }
}