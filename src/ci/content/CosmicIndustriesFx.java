package ci.content;

import arc.graphics.*;
import arc.graphics.g2d.*;
import mindustry.entities.*;
import mindustry.graphics.Drawf;

import static arc.graphics.g2d.Draw.*;
import static arc.math.Angles.*;

public class CosmicIndustriesFx {

    public static final Effect none = new Effect(0, 0f, e -> {
    }),
            CISteam = new Effect(35f, e -> {
                alpha(0.9f);
                color(Color.lightGray);

                randLenVectors(e.id, 12, 0.1f + e.fin() * 8f, (x, y) -> {
                    Fill.circle(e.x + x, e.y + y, 0.1f + e.fslope() * 1.5f);
                });
            });
}