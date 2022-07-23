package uk.debb.carpetplusplus.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.debb.carpetplusplus.CarpetPlusPlusSettings;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Mixin(PistonBaseBlock.class)
public abstract class MixinPistonBaseBlock_retractableWorldBorder {
    @Shadow protected abstract boolean getNeighborSignal(Level arg, BlockPos arg2, Direction arg3);
    @Shadow @Final public static BooleanProperty EXTENDED;
    @Shadow @Final private boolean isSticky;

    /**
     * @param level the level the piston is
     * @param blockPos the position of the piston
     * @param blockState the state of the piston
     * @param ci the callback info
     * @author DragonEggBedrockBreaking
     */
    @Inject(method = "checkIfExtend", at = @At("HEAD"))
    private void retractWorldBorder(Level level, BlockPos blockPos, BlockState blockState, CallbackInfo ci) {
        // Create variables for whether the border is being faced, where the border is, and how a piston facing it is rotated
        boolean facingBorder = false;
        Direction worldBorderDirection = Direction.UP;
        Rotation pistonRotation = Rotation.NONE;
        // Go through each direction, and set the variables' values based on the direction of the piston
        switch (blockState.getValue(DirectionalBlock.FACING)) {
            case NORTH -> {
                facingBorder = blockPos.getX() > blockPos.getZ() && 0 > blockPos.getZ();
                worldBorderDirection = Direction.NORTH;
            }
            case SOUTH -> {
                facingBorder = blockPos.getX() < blockPos.getZ() && 0 < blockPos.getZ();
                worldBorderDirection = Direction.SOUTH;
                pistonRotation = Rotation.CLOCKWISE_180;
            }
            case WEST -> {
                facingBorder = blockPos.getZ() > blockPos.getX() && 0 > blockPos.getX();
                worldBorderDirection = Direction.WEST;
                pistonRotation = Rotation.COUNTERCLOCKWISE_90;
            }
            case EAST -> {
                facingBorder = blockPos.getZ() < blockPos.getX() && 0 < blockPos.getX();
                worldBorderDirection = Direction.EAST;
                pistonRotation = Rotation.CLOCKWISE_90;
            }
        }
        // Requirements:
        // - It must be retracting (no signal, currently extended)
        // - The carpet rule must be enabled
        // - The piston must be facing the world border
        // - The piston must be a sticky piston
        // - It must be directly next to the border
        if (!this.getNeighborSignal(level, blockPos, blockState.getValue(DirectionalBlock.FACING)) &&
            blockState.getValue(EXTENDED) && CarpetPlusPlusSettings.retractableWorldBorder && facingBorder &&
            this.isSticky && level.getWorldBorder().getDistanceToBorder(blockPos.getX(), blockPos.getZ()) <= 2) {
            // First quickly force the piston to retract
            level.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
            level.setBlockAndUpdate(blockPos, Blocks.STICKY_PISTON.defaultBlockState().rotate(pistonRotation));
            // Then schedule the block to become a barrier block after 20 seconds
            ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
            Direction finalWorldBorderDirection = worldBorderDirection;
            Runnable task = () -> level.setBlockAndUpdate(blockPos.relative(finalWorldBorderDirection), Blocks.BARRIER.defaultBlockState());
            scheduler.schedule(task, 20, TimeUnit.MILLISECONDS);
        }
    }
}
