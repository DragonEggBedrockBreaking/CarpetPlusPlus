package uk.debb.carpetplusplus.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BigDripleafBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SmallDripleafBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.debb.carpetplusplus.CarpetPlusPlusSettings;

@Mixin(BigDripleafBlock.class)
public abstract class MixinBigDripleafBlock_dripleafCrushing {
    /**
     * @param blockState the state of the big dripleaf
     * @param level      the level the big dripleaf is in
     * @param blockPos   the position of the big dripleaf
     * @param entity     the entity on top of the dripleaf
     * @param ci         the callback info
     * @author DragonEggBedrockBreaking
     * @reason iron golems crush big dripleaves
     */
    @Inject(method = "entityInside", at = @At("HEAD"), cancellable = true)
    private void crushDripleaf(BlockState blockState, Level level, BlockPos blockPos, Entity entity, CallbackInfo ci) {
        // Make sure that this is not run on the client
        if (level.isClientSide()) return;
        // Check if the entity is an iron golem and check the carpet rule
        if (CarpetPlusPlusSettings.dripleafCrushing && entity instanceof IronGolem) {
            // Variable for the current block (stored as a stem)
            BlockState state = Blocks.BIG_DRIPLEAF_STEM.defaultBlockState();
            // Variable for the position of the block
            BlockPos pos = blockPos;
            // Iterate over the blocks below
            while (state.is(Blocks.BIG_DRIPLEAF_STEM)) {
                // Set the block to air, since it is either a big dripleaf or a big dripleaf stem
                level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                // Go down one block
                pos = pos.below();
                // Update the state
                state = level.getBlockState(pos);
            }
            // When the block is no longer a stem, place a small dripleaf above
            SmallDripleafBlock.placeAt(level, Blocks.SMALL_DRIPLEAF.defaultBlockState(), pos.above(), 3);
        }
    }
}
