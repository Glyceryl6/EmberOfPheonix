package com.glyceryl.emberphoenix.common.world.structures;

import com.glyceryl.emberphoenix.registry.EPStructures;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.ScatteredFeaturePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;

import java.util.Random;

@SuppressWarnings("unused")
public class VolcanoPiece extends ScatteredFeaturePiece {

    private static final Block blockEnrichedLava = Blocks.LAVA;
    private static final Block blockCasing = Blocks.BASALT;
    int xCenter, zCenter;
    int sizeDeviation;

    public VolcanoPiece(Random random, int x, int z) {
        super(EPStructures.PIECE_VOLCANO, x, 40, z, 128, 40, 128, getRandomHorizontalDirection(random));
        this.setOrientation(null);
        xCenter = x;
        zCenter = z;
        sizeDeviation = random.nextInt(8);
    }

    public VolcanoPiece(StructurePieceSerializationContext context, CompoundTag piece) {
        super(EPStructures.PIECE_VOLCANO, piece);
        xCenter = piece.getInt("xCenter");
        zCenter =  piece.getInt("zCenter");
        sizeDeviation =  piece.getInt("sizeDev");
    }

    protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tagCompound) {
        super.addAdditionalSaveData(context, tagCompound);
        tagCompound.putInt("xCenter", xCenter);
        tagCompound.putInt("zCenter", zCenter);
        tagCompound.putInt("sizeDev", sizeDeviation);
    }

    @Override
    public void postProcess(WorldGenLevel world, StructureFeatureManager manager, ChunkGenerator generator, Random rand, BoundingBox bb, ChunkPos chunkPos, BlockPos blockPos) {
        int size = 64;
        int baseHeight = 25;
        int lavaNodeHeight = 25;
        int sizeDeviation = rand.nextInt(8);
        int[] sinCoefficients = {rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1};
        int numBumps = rand.nextInt(5) + 1;
        int xCoordinate = (chunkPos.x << 4);
        int zCoordinate = (chunkPos.z << 4);
        for(int x = 15; x >= 0; x--) {
            for(int z = 15; z >= 0; z--) {
                int realXCoordinate = (xCoordinate) + x;
                int realZCoordinate = (zCoordinate) + z;
                int x2 = realXCoordinate * realXCoordinate;
                int z2 = realZCoordinate * realZCoordinate;
                int crackle = rand.nextInt(2);
                double radius = getRadius(Math.sqrt(x2 + z2), realXCoordinate, realZCoordinate, numBumps, sinCoefficients);
                double func = 1 / (Math.pow(1.028, radius - (size - sizeDeviation) * 3)) + baseHeight
                        - 8 / (Math.pow(1.09, radius - ((size - sizeDeviation) / 2.6))) - Math.pow(1.7, radius - size * .9);
                for (int y = 254; y >= 1; y--) {
                    boolean underSurface = func >= y + crackle;
                    boolean innerLayer = func >= y + crackle && radius < 5 && y > lavaNodeHeight;
                    if (innerLayer) {
                        setBlockState(world, blockEnrichedLava.defaultBlockState(), realXCoordinate, y, realZCoordinate, bb);
                    } else if (underSurface) {
                        setBlockState(world, blockEnrichedLava.defaultBlockState(), realXCoordinate, y, realZCoordinate, bb);
                    }
                    double sphereRadius = (radius * radius) + (y - lavaNodeHeight) * (y - lavaNodeHeight);
                    if (sphereRadius < 23 * 23) {
                        setBlockState(world, blockEnrichedLava.defaultBlockState(), realXCoordinate, y, realZCoordinate, bb);
                    } else if (sphereRadius < 25 * 25) {
                        setBlockState(world, blockCasing.defaultBlockState(), realXCoordinate, y, realZCoordinate, bb);
                    }
                    if (innerLayer) {
                        setBlockState(world, blockEnrichedLava.defaultBlockState(), realXCoordinate, y, realZCoordinate, bb);
                    }
                }
            }
        }
    }

    protected void setBlockState(WorldGenLevel worldIn, BlockState blockState, int x, int y, int z, BoundingBox boundingBox) {
        worldIn.setBlock(new BlockPos(x,y,z), blockState, 2);
    }

    private double getRadius(double base, int x, int z, int bumps, int[] random) {
        double radians = Math.atan2(x, z);
        int extras = 0;
        for (int i = 2; i < Math.min(5, bumps) + 2; i++){
            extras += random[i-2] * base * Math.sin(i * radians) * 0.0125;
        }

        return base + extras;
    }

}