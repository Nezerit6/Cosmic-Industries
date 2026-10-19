package ci.world.blocks.environment;

import arc.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;
import mindustry.content.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.ui.*;
import mindustry.world.*;

import static mindustry.Vars.*;

public class CICliff extends Block{
    public static final int up = 0, down = 1;
    static final byte opData = 5;
    static final int[] masks = {0, 1, 2, 5, 3, 1, 6, 5, 4, 8, 2, 5, 7, 8, 6, 5};
    static final float[] verts = new float[20];
    static final int[][] arms = {{}, {1, 3}, {0, 2}, {1, 3}, {0, 2}, {2, 3}, {0, 3}, {0, 1}, {2, 1}, {1, 0}, {1, 2}, {3, 2}, {3, 0}};

    public int variants = 3;
    public TextureRegion[][][] faces = new TextureRegion[2][13][], tapers = new TextureRegion[2][13][3];
    public TextureRegion marker;

    public CICliff(String name){
        super(name);
        breakable = alwaysReplace = false;
        solid = true;
        cacheLayer = CacheLayer.walls;
        fillsTile = false;
        hasShadow = false;
        saveData = saveConfig = true;
        editorConfigurable = true;
        lastConfig = up;
    }

    @Override
    public void load(){
        super.load();
        TextureRegion sheet = Core.atlas.find(name + "-sheet");
        TextureRegion[][] split = sheet.split(sheet.width / 12, sheet.width / 12);
        for(int m = 0; m < 2; m++){
            for(int f = 1; f <= 12; f++){
                faces[m][f] = new TextureRegion[variants];
                for(int v = 0; v < variants; v++) faces[m][f][v] = split[f - 1][m * variants + v];
                for(int t = 0; t < 3; t++) tapers[m][f][t] = split[f - 1][variants * 2 + m * 3 + t];
            }
        }
        marker = Core.atlas.find(name + "-marker");
    }

    @Override
    public void drawBase(Tile tile){
        int f = tile.data >= 16 ? tile.data & 15 : face(tile);
        if(f == 0){
            Draw.rect(marker, tile.drawx(), tile.drawy());
            return;
        }
        int m = mode(tile), t = 0;
        for(int i = 0; i < 2; i++){
            Tile other = tile.nearby(arms[f][i]);
            if(other != null && other.block() instanceof CICliff && mode(other) != m) t |= 1 << i;
        }
        TextureRegion r = t == 0 ? faces[m][f][Mathf.randomSeed(tile.pos(), 0, variants - 1)] : tapers[m][f][t - 1];
        float h = r.width * Draw.scl / 2f;
        for(int i = 0; i < 4; i++){
            int qx = i % 2, qy = i / 2;
            float x = tile.drawx() - h + qx * h, y = tile.drawy() - h + qy * h;
            float u = Mathf.lerp(r.u, r.u2, qx / 2f), u2 = Mathf.lerp(r.u, r.u2, (qx + 1) / 2f);
            float v = Mathf.lerp(r.v2, r.v, qy / 2f), v2 = Mathf.lerp(r.v2, r.v, (qy + 1) / 2f);
            vert(0, x, y, color(tile, qx - 1, qy - 1), u, v);
            vert(5, x, y + h, color(tile, qx - 1, qy), u, v2);
            vert(10, x + h, y + h, color(tile, qx, qy), u2, v2);
            vert(15, x + h, y, color(tile, qx, qy - 1), u2, v);
            Draw.vert(r.texture, verts, 0, 20);
        }
    }

    static void vert(int i, float x, float y, float color, float u, float v){
        verts[i] = x;
        verts[i + 1] = y;
        verts[i + 2] = color;
        verts[i + 3] = u;
        verts[i + 4] = v;
    }

    static float color(Tile tile, int dx, int dy){
        Tile other = tile.nearby(dx, dy);
        return Tmp.c1.set((other == null ? tile : other).floor().mapColor).mul(1.5f).toFloatBits();
    }

    @Override
    public int minimapColor(Tile tile){
        return Tmp.c1.set(tile.floor().mapColor).mul(1.2f).rgba();
    }

    @Override
    public void buildEditorConfig(Table table){
        table.table(t -> {
            t.defaults().growX().height(42f).pad(2f);
            t.button("@ci.cliff.up", Icon.upOpen, Styles.flatTogglet, () -> lastConfig = up)
                    .update(b -> b.setChecked(!Integer.valueOf(down).equals(lastConfig))).margin(12f);
            t.button("@ci.cliff.down", Icon.downOpen, Styles.flatTogglet, () -> lastConfig = down)
                    .update(b -> b.setChecked(Integer.valueOf(down).equals(lastConfig))).margin(12f);
        }).growX().row();
        table.button("@ci.cliff.bake", Icon.ok, Styles.cleart, CICliff::bake).growX().height(42f).pad(2f).margin(12f);
    }

    @Override
    public void placeEnded(Tile tile, @Nullable Unit builder, int rotation, @Nullable Object config){
        tile.data = (byte)(Integer.valueOf(down).equals(config) ? 1 : 0);
    }

    @Override
    public Object getConfig(Tile tile){
        return mode(tile);
    }

    @Override
    public void editorPicked(Tile tile){
        lastConfig = mode(tile);
    }

    public static int mode(Tile tile){
        return tile.data == 1 || tile.data >= 32 ? down : up;
    }

    public static int face(Tile tile){
        int mask = 0;
        for(int i = 0; i < 4; i++) if(!cliff(tile.nearby(i))) mask |= 1 << i;
        if(mask != 0) return masks[mask];
        for(int i = 0; i < 4; i++) if(!cliff(tile.nearby(Geometry.d8edge(i)))) return 9 + i;
        return 0;
    }

    static boolean cliff(Tile tile){
        return tile == null || tile.block() instanceof CICliff;
    }

    public static void bake(){
        IntSeq remove = new IntSeq();
        boolean editing = state.isEditor();
        world.tiles.eachTile(tile -> {
            if(!(tile.block() instanceof CICliff) || tile.data >= 16) return;
            int f = face(tile);
            if(f == 0){
                remove.add(tile.pos());
                return;
            }
            if(editing) editor.addTileOp(TileOp.get(tile.x, tile.y, opData, TileOpData.get(tile.data, tile.floorData, tile.overlayData)));
            tile.data = (byte)((mode(tile) == down ? 32 : 16) + f);
        });
        for(int i = 0; i < remove.size; i++) world.tile(remove.get(i)).setBlock(Blocks.air);
        if(editing){
            editor.flushOp();
            editor.renderer.resize(world.width(), world.height());
        }
    }
}