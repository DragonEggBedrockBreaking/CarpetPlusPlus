package uk.debb.carpetplusplus.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MagmaBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import uk.debb.carpetplusplus.CarpetPlusPlusSettings;

import java.util.Random;

@Mixin(MagmaBlock.class)
public abstract class MixinMagmaBlock_renewableTuff extends BlockBehaviour {
    public MixinMagmaBlock_renewableTuff(BlockBehaviour.Properties properties) {
        super(properties);
    }

    /**
     * @param blockState      the state of the magma
     * @param level           the level that the magma is in
     * @param blockPos        the position that the megma is in
     * @param player          the player that is right clicking the magma
     * @param interactionHand the hand that the player is using for the conversion
     * @param blockHitResult  the result of the player using blaze powder on the magma
     * @return the result of the interaction with the magma
     * @author DragonEggBedrockBreaking
     * @reason right clicking magma with blaze powder makes tuff
     */
    @SuppressWarnings("deprecation")
    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        // Make sure that this is not run on the client
        if (level.isClientSide()) return InteractionResult.FAIL;
        // Checks if the carpet rule is enabled, and if the hand has blaze powder (the block is always magma in this class)
        if (CarpetPlusPlusSettings.renewableTuff && player.getItemInHand(interactionHand).is(Items.BLAZE_POWDER)) {
            // If so, there is a 1 in 5 chance that the block will turn to tuff
            Random random = new Random();
            if (random.nextInt(5) == 0) {
                level.setBlockAndUpdate(blockPos, Blocks.TUFF.defaultBlockState());
            }
            // If the code is run, return success
            return InteractionResult.SUCCESS;
        }
        // If the conditions are not met, run vanilla code
        return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
    }
}
