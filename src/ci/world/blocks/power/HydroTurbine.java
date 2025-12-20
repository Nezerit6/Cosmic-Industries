package ci.world.blocks.power;

import arc.math.*;
import arc.util.*;
import mindustry.game.*;
import mindustry.graphics.*;
import mindustry.logic.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.power.*;
import mindustry.world.meta.*;

import static mindustry.Vars.*;

public class HydroTurbine extends PowerGenerator {
    public float pumpAmount = 0.1f;
    public float warmupSpeed = 0.019f;
    public int exclusionRange = 1;

    public HydroTurbine(String name) {
        super(name);
        hasLiquids = true;
        outputsLiquid = true;
        floating = true;
        ambientSound = mindustry.gen.Sounds.hum;
        ambientSoundVolume = 0.06f;
    }

    @Override
    public void setStats() {
        super.setStats();
        stats.add(Stat.output, 60f * pumpAmount * size * size, StatUnit.liquidSecond);
    }

    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation) {
        if(isMultiblock()) {
            Liquid last = null;
            int waterTiles = 0;
            int totalTiles = 0;

            for(Tile other : tile.getLinkedTilesAs(this, tempTiles)) {
                totalTiles++;
                if(other.floor().liquidDrop != null) {
                    waterTiles++;
                    if(last != null && other.floor().liquidDrop != last) return false;
                    last = other.floor().liquidDrop;
                }
            }

            if(waterTiles != totalTiles || last == null) return false;

            return !indexer.getFlagged(team, BlockFlag.generator).contains(other -> {
                if(!(other.block instanceof HydroTurbine) || other.tile() == tile) return false;
                return Math.max(Math.abs(other.tileX() - tile.x), Math.abs(other.tileY() - tile.y)) < size + exclusionRange;
            });
        }
        return tile != null && tile.floor().liquidDrop != null;
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid) {
        super.drawPlace(x, y, rotation, valid);

        Tile tile = world.tile(x, y);
        if(tile == null) return;

        boolean hasConflict = indexer.getFlagged(player.team(), BlockFlag.generator).contains(other -> {
            if(!(other.block instanceof HydroTurbine)) return false;
            return Math.max(Math.abs(other.tileX() - x), Math.abs(other.tileY() - y)) < size + exclusionRange;
        });

        indexer.getFlagged(player.team(), BlockFlag.generator).each(other -> {
            if(!(other.block instanceof HydroTurbine)) return;
            if(Math.max(Math.abs(other.tileX() - x), Math.abs(other.tileY() - y)) < size + exclusionRange){
                Drawf.selected(other, Tmp.c1.set(Pal.remove).a(Mathf.absin(4f, 1f)));
            }
        });

        Drawf.dashSquare(hasConflict ? Pal.remove : Pal.accent, x * tilesize + offset, y * tilesize + offset, (exclusionRange + size / 2f) * tilesize * 2f);
    }

    public class HydroTurbineBuild extends GeneratorBuild {
        public float warmup, totalProgress, amount = 0f;
        public @Nullable Liquid liquidDrop = null;

        @Override
        public void onProximityUpdate() {
            super.onProximityUpdate();
            amount = 0f;
            liquidDrop = null;

            for(Tile other : tile.getLinkedTiles(tempTiles)) {
                if(other.floor().liquidDrop != null) {
                    liquidDrop = other.floor().liquidDrop;
                    amount += other.floor().liquidMultiplier;
                }
            }
        }

        @Override
        public void updateTile() {
            if(liquidDrop != null && amount > 0.001f && liquids.get(liquidDrop) < liquidCapacity - 0.01f) {
                float maxPump = Math.min(liquidCapacity - liquids.get(liquidDrop), amount * pumpAmount * delta());
                liquids.add(liquidDrop, maxPump);
                productionEfficiency = Mathf.clamp(maxPump / (amount * pumpAmount * delta()));
                warmup = Mathf.approachDelta(warmup, 1f, warmupSpeed);
            } else {
                productionEfficiency = 0f;
                warmup = Mathf.approachDelta(warmup, 0f, warmupSpeed);
            }

            totalProgress += warmup * delta();
            if(liquidDrop != null) dumpLiquid(liquidDrop);
        }

        @Override
        public float warmup() {
            return warmup;
        }

        @Override
        public float totalProgress() {
            return totalProgress;
        }

        @Override
        public double sense(LAccess sensor) {
            if(sensor == LAccess.totalLiquids) return liquidDrop == null ? 0f : liquids.get(liquidDrop);
            return super.sense(sensor);
        }
    }
}