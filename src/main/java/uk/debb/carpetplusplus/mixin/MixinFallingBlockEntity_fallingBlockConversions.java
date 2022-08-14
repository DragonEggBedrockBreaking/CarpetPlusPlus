package uk.debb.carpetplusplus.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import uk.debb.carpetplusplus.CarpetPlusPlusSettings;
import uk.debb.carpetplusplus.anvil.AnvilStaticFields;

@Mixin(FallingBlockEntity.class)
public abstract class MixinFallingBlockEntity_fallingBlockConversions extends Entity {
    @Shadow
    private BlockState blockState;

    public MixinFallingBlockEntity_fallingBlockConversions(EntityType<? extends Entity> entityType, Level level) {
        super(entityType, level);
    }

    /**
     * @param arg the state of the falling block
     * @author DragonEggBedrockBreaking
     */
    @Override
    protected void onInsideBlock(BlockState arg) {
        // Check if the gamerule is enabled
        if (CarpetPlusPlusSettings.fallingBlockConversions) {
            // If the block is an anvil
            if (this.blockState.is(Blocks.ANVIL)) {
                // Get all horizontal directions
                for (Direction direction : Direction.Plane.HORIZONTAL) {
                    // Store some data in variables
                    BlockPos pos = this.blockPosition().relative(direction);
                    BlockState state = this.level.getBlockState(pos);
                    // If the block is in a lazy chunk, and the block is unobtainable
                    if (AnvilStaticFields.unobtainableBlockList.contains(state.getBlock()) && !level.shouldTickBlocksAt(pos)) {
                        // Turn the unobtainable block into a falling block
                        FallingBlockEntity.fall(this.level, pos, state);
                    }
                }
            }
        }
    }
}
