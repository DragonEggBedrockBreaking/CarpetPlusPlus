package uk.debb.carpetplusplus.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.debb.carpetplusplus.CarpetPlusPlusSettings;

@Mixin(PistonBaseBlock.class)
public abstract class MixinPistonBaseBlock_pistonTranslocation {
    @Shadow
    protected abstract boolean getNeighborSignal(Level arg, BlockPos arg2, Direction arg3);
    @Shadow @Final
    public static BooleanProperty EXTENDED;

    /**
     * @param level the level the piston is
     * @param blockPos the position of the piston
     * @param blockState the state of the piston
     * @param ci the callback info
     * @author DragonEggBedrockBreaking
     */
    @Inject(method = "checkIfExtend", at = @At("HEAD"))
    private void retractWorldBorder(Level level, BlockPos blockPos, BlockState blockState, CallbackInfo ci) {
        // Create variable for the direction
        Direction direction = null;
        // Go through each direction, and set the variable's value based on the direction of the piston
        switch (blockState.getValue(DirectionalBlock.FACING)) {
            case NORTH -> {
                direction = Direction.NORTH;
            }
            case SOUTH -> {
                direction = Direction.SOUTH;
            }
            case WEST -> {
                direction = Direction.WEST;
            }
            case EAST -> {
                direction = Direction.EAST;
            }
            case UP -> {
                direction = Direction.UP;
            }
            case DOWN -> {
                direction = Direction.DOWN;
            }
        }
        // Requirements:
        // - It must be retracting (no signal, currently extended)
        // - The carpet rule must be enabled
        if (!this.getNeighborSignal(level, blockPos, blockState.getValue(DirectionalBlock.FACING)) &&
                blockState.getValue(EXTENDED) && CarpetPlusPlusSettings.pistonTranslocation) {
            // Iterate through the entities
            for (Entity entity : level.getEntities(null, new AABB(blockPos.relative(direction)))) {
                // Move the entity to the other side
                entity.moveTo(blockPos.relative(direction.getOpposite()), entity.getXRot(), entity.getYRot());
            }
        }
    }
}
