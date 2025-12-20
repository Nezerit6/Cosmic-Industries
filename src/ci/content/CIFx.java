package ci.content;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.Mathf;
import mindustry.entities.*;

import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.*;

public class CIFx {

    public static final Effect none = new Effect(0, 0f, e -> {
    }),
            CISteam = new Effect(65f, e -> {
                alpha(0.9f);
                color(Color.lightGray);

                randLenVectors(e.id, 12, 0.1f + e.fin() * 8f, (x, y) -> {
                    Fill.circle(e.x + x, e.y + y, 0.1f + e.fslope() * 1.5f);
                });
            }),

            laserSparks = new Effect(11f, e -> {
                color(Color.white, e.color, e.fin());
                stroke(e.fout() * 1.1f + 0.5f);
                randLenVectors(e.id, 5, 15f * e.fin(), e.rotation, 4f, (x, y) -> {
                    lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.fslope() * 2f + 0.5f);
                });
            });
}