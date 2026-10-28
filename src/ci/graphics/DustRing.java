package ci.graphics;

import arc.graphics.*;
import arc.graphics.gl.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import mindustry.graphics.g3d.*;
import mindustry.type.*;

public class DustRing implements GenericMesh {
    static Shader shader;
    static final Vec3 axisX = new Vec3(), axisY = new Vec3(), axisZ = new Vec3();

    public final Planet planet;
    public final Color color;
    public final float[] inner, outer, density;
    public int particles = 900, trail = 4;
    public float trailLength = 6f, tilt = 22f;
    Mesh mesh;

    public DustRing(Planet planet, Color color, float[] inner, float[] outer, float[] density){
        this.planet = planet;
        this.color = color;
        this.inner = inner;
        this.outer = outer;
        this.density = density;
    }

    Mesh build(){
        Rand rand = new Rand(42);
        FloatSeq data = new FloatSeq();
        for(int band = 0; band < inner.length; band++){
            int count = (int)(particles * density[band]);
            for(int i = 0; i < count; i++){
                float radius = rand.random(inner[band], outer[band]), angle = rand.random(360f), height = rand.range(0.006f), alpha = rand.random(0.35f, 0.8f);
                for(int k = 0; k < trail; k++){
                    data.add(radius, angle - trailLength * k / trail, height, alpha * (1f - (float)k / trail));
                    data.add(radius, angle - trailLength * (k + 1) / trail, height, alpha * (1f - (k + 1f) / trail));
                }
            }
        }
        Mesh mesh = new Mesh(true, data.size / 4, 0, new VertexAttribute(4, "a_data"));
        mesh.setVertices(data.toArray());
        return mesh;
    }

    @Override
    public void render(PlanetParams params, Mat3D projection, Mat3D transform){
        if(mesh == null) mesh = build();
        if(shader == null) shader = new Shader(
                "#define HIGHP\n" +
                        "attribute vec4 a_data;\n" +
                        "uniform mat4 u_proj;\n" +
                        "uniform vec3 u_center, u_sun, u_axisX, u_axisY, u_axisZ;\n" +
                        "uniform float u_radius, u_time;\n" +
                        "uniform vec4 u_color;\n" +
                        "varying vec4 v_color;\n" +
                        "void main(){\n" +
                        "    float angle = radians(a_data.y + u_time * 0.03 / pow(a_data.x, 1.5));\n" +
                        "    vec3 pos = u_center + (u_axisX * cos(angle) * a_data.x + u_axisY * sin(angle) * a_data.x + u_axisZ * a_data.z) * u_radius;\n" +
                        "    vec3 toSun = normalize(u_sun - pos), toCenter = u_center - pos;\n" +
                        "    float along = dot(toCenter, toSun);\n" +
                        "    float lit = along > 0.0 && dot(toCenter, toCenter) - along * along < u_radius * u_radius ? 0.12 : 1.0;\n" +
                        "    v_color = vec4(u_color.rgb * lit, u_color.a * a_data.w);\n" +
                        "    gl_Position = u_proj * vec4(pos, 1.0);\n" +
                        "}",
                "varying vec4 v_color;\n" +
                        "void main(){\n" +
                        "    gl_FragColor = v_color;\n" +
                        "}"
        );

        axisZ.set(Vec3.Y).rotate(Vec3.X, tilt);
        axisY.set(axisZ).crs(Vec3.X).nor();
        axisX.set(axisY).crs(axisZ).nor();
        Color light = planet.solarSystem.lightColor;

        Gl.enable(Gl.blend);
        Gl.blendFunc(Gl.srcAlpha, Gl.oneMinusSrcAlpha);
        Gl.depthMask(false);
        shader.bind();
        shader.setUniformMatrix4("u_proj", projection.val);
        shader.setUniformf("u_center", planet.position);
        shader.setUniformf("u_sun", planet.solarSystem.position);
        shader.setUniformf("u_axisX", axisX);
        shader.setUniformf("u_axisY", axisY);
        shader.setUniformf("u_axisZ", axisZ);
        shader.setUniformf("u_radius", planet.radius);
        shader.setUniformf("u_time", Time.globalTime);
        shader.setUniformf("u_color", color.r * light.r, color.g * light.g, color.b * light.b, params.planet == planet ? 1f - params.uiAlpha * 0.6f : 1f);
        mesh.render(shader, Gl.lines);
        Gl.depthMask(true);
    }

    @Override
    public void dispose(){
        if(mesh != null) mesh.dispose();
    }
}