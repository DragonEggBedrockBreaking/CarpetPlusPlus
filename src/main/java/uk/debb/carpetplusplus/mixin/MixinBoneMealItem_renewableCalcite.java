package uk.debb.carpetplusplus.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import uk.debb.carpetplusplus.CarpetPlusPlusSettings;

import java.util.Random;

@Mixin(BoneMealItem.class)
public abstract class MixinBoneMealItem_renewableCalcite {
    /**
     * @param useOnContext all of the data for the right click
     * @param cir          the returnable callback info (net.minecraft.world.InteractionResult)
     * @author DragonEggBedrockBreaking
     * @reason bonemealing ice creates calcite
     */
    @Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
    private void boneMealIce(UseOnContext useOnContext, CallbackInfoReturnable<InteractionResult> cir) {
        // Get the level the block is in and make sure that this is not run on the client
        Level level = useOnContext.getLevel();
        if (level.isClientSide()) return;
        // Stores the position of the block to get the type of block
        BlockPos position = useOnContext.getClickedPos();
        BlockState state = level.getBlockState(position);
        // Checks if the carpet rule is enabled, and if the block is an ice block (the hand will always contain bonemeal in this class)
        if (CarpetPlusPlusSettings.renewableCalcite && state.is(Blocks.ICE)) {
            // If so, there is a 1 in 5 chance that the block will turn to calcite
            Random random = new Random();
            if (random.nextInt(5) == 0) {
                level.setBlockAndUpdate(position, Blocks.CALCITE.defaultBlockState());
            }
            // Whether or not the block turns to calcite, bonemeal is taken out of the player's hand
            useOnContext.getItemInHand().shrink(1);
            // Skip the rest of the method for optimisation
            cir.setReturnValue(InteractionResult.SUCCESS);
            cir.cancel();
        }
    }
}
