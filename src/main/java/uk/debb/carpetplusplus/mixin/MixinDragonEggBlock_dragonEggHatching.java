package uk.debb.carpetplusplus.mixin;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DragonEggBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import uk.debb.carpetplusplus.CarpetPlusPlusSettings;

@Mixin(DragonEggBlock.class)
public abstract class MixinDragonEggBlock_dragonEggHatching {
    /**
     * @author DragonEggBedrockBreaking
     * @param blockState the state of the dragon egg
     * @param level the level the dragon egg is in
     * @param blockPos the position of the dragon egg
     * @param player the player clicking the dragon egg
     * @param interactionHand the hand that the player is using to click the dragon egg
     * @param blockHitResult the result of clicking the dragon egg
     * @param cir the returnable callback info (Boolean)
     */
    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void hatch(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult, CallbackInfoReturnable<InteractionResult> cir) {
        // Make sure that this is not run on the client
        if (level.isClientSide()) return;
        // Store the block below
        BlockState blockBelow = level.getBlockState(blockPos.below());
        // Create a random object
        Random random = new Random();
        // Check for gamerule and a random chance, and if the block below is magma
        if (CarpetPlusPlusSettings.dragonEggHatching && random.nextInt(11) < 3 && blockBelow.is(Blocks.MAGMA_BLOCK)) {
            // Replace the block
            level.setBlockAndUpdate(blockPos, Blocks.DRAGON_HEAD.defaultBlockState());
            // Return success
            cir.setReturnValue(InteractionResult.SUCCESS);
        }
    }
}
