package com.antiquark.mixin.server;

import com.antiquark.AntiQuark;
import com.antiquark.module.world.EndPortalPlacementModule;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.structures.StrongholdPieces;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(StrongholdPieces.PortalRoom.class)
public abstract class StructurePieceEndPortalMixin {
    @Inject(method = "postProcess", at = @At("RETURN"))
    private void antiquark$containEndPortal(WorldGenLevel level, StructureManager structureManager,
        ChunkGenerator generator, RandomSource random, BoundingBox box, ChunkPos chunkPos,
        BlockPos pos, CallbackInfo ci) {
        if (!AntiQuark.ZETA.modules.isEnabled(EndPortalPlacementModule.class)) {
            return;
        }
        for (int x = 3; x <= 7; x++) {
            for (int y = 3; y <= 4; y++) {
                for (int z = 8; z <= 12; z++) {
                    boolean portalWindow = x >= 4 && x <= 6 && y == 3 && z >= 9 && z <= 11;
                    if (!portalWindow) {
                        ((StructurePieceInvoker) (Object) this)
                            .antiquark$invokePlaceBlock(level, Blocks.AIR.defaultBlockState(), x, y, z, box);
                    }
                }
            }
        }
    }
}
