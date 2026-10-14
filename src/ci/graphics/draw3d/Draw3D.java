package ci.graphics.draw3d;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.graphics.gl.*;
import arc.math.*;
import arc.struct.*;
import mindustry.game.EventType.*;
import mindustry.graphics.*;

import static arc.Core.*;
import static arc.math.Mathf.*;
import static mindustry.Vars.*;

/**
 * Pseudo-3D shadow and bloom queues by MEEPofFaith
 * (https://github.com/MEEPofFaith/prog-mats-java, GPL-3.0).
 */
public class Draw3D{
    public static final float shadowFadeEnd = 300f, zToShadowScl = 3f / shadowFadeEnd;
    public static final float shadowLayer = Layer.flyingUnit + 1;
    public static final float skyBloomLayer = 145f;
    public static final Seq<QueuedBloom> bloomQueue = new Seq<>();
    public static final Seq<Runnable> shadowQueue = new Seq<>();
    public static Shader passThrough;

    public static void init(){
        Events.on(ClientLoadEvent.class, e -> passThrough = new Shader(
                files.internal("shaders/screenspace.vert"),
                tree.get("shaders/passThrough.frag")
        ));

        Events.run(Trigger.drawOver, () -> {
            if(shadowQueue.any()){
                if(passThrough == null){
                    shadowQueue.clear();
                }else{
                    Draw.draw(shadowLayer, () -> {
                        FrameBuffer buffer = renderer.effectBuffer;
                        buffer.begin(Color.clear);
                        Draw.sort(false);
                        Gl.blendEquationSeparate(Gl.funcAdd, Gl.max);

                        for(Runnable s : shadowQueue){
                            s.run();
                        }

                        Draw.sort(true);
                        buffer.end();
                        Gl.blendEquationSeparate(Gl.funcAdd, Gl.funcAdd);

                        buffer.blit(passThrough);
                    });
                    shadowQueue.clear();
                }
            }

            if(bloomQueue.any()){
                bloomQueue.sort(q -> q.layer);
                Bloom bloom = renderer.bloom;
                if(bloom != null){
                    Draw.draw(skyBloomLayer, () -> {
                        bloom.capture();
                        for(QueuedBloom b : bloomQueue){
                            b.draw.run();
                        }
                        bloom.render();
                    });
                }else{
                    for(QueuedBloom b : bloomQueue){
                        b.draw.run();
                    }
                }
                bloomQueue.clear();
            }
        });
    }

    public static float shadowAlpha(float z){
        return Mathf.clamp(1f - z / shadowFadeEnd);
    }

    public static float shadowScale(float z){
        return 1f + zToShadowScl * z;
    }

    public static float layerOffset(float x, float y){
        float max = Math.max(camera.width, camera.height);
        return -dst(x, y, camera.position.x, camera.position.y) / max / 1000f;
    }

    public static float layerOffset(float cx, float cy, float tx, float ty){
        float angleTo = Angles.angle(cx, cy, tx, ty),
                angleCam = Angles.angle(cx, cy, camera.position.x, camera.position.y);
        float angleDist = Angles.angleDist(angleTo, angleCam);
        float max = Math.max(camera.width, camera.height);

        return layerOffset(cx, cy) + dst(cx, cy, tx, ty) * cosDeg(angleDist) / max / 1000f;
    }

    public static void highBloom(Runnable draw){
        highBloom(true, Draw.z(), draw);
    }

    public static void highBloom(float layer, Runnable draw){
        highBloom(true, layer, draw);
    }

    public static void highBloom(boolean bloom, Runnable draw){
        highBloom(bloom, Draw.z(), draw);
    }

    public static void highBloom(boolean bloom, float layer, Runnable draw){
        if(bloom){
            bloomQueue.add(new QueuedBloom(layer, draw));
        }else{
            float z = Draw.z();
            Draw.z(z + 0.01f);
            draw.run();
            Draw.z(z);
        }
    }

    public static void shadow(Runnable draw){
        shadowQueue.add(draw);
    }

    public static class QueuedBloom{
        public float layer;
        public Runnable draw;

        public QueuedBloom(float layer, Runnable draw){
            this.layer = layer;
            this.draw = draw;
        }
    }
}