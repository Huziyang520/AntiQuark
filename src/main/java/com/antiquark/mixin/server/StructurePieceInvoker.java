package com.antiquark.mixin.server;

import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(StructurePiece.class)
public interface StructurePieceInvoker {
    @Invoker("placeBlock")
    void antiquark$invokePlaceBlock(WorldGenLevel level, BlockState state, int x, int y, int z, BoundingBox box);
}
