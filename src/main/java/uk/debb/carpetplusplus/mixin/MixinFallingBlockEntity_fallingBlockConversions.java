package uk.debb.carpetplusplus.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.debb.carpetplusplus.CarpetPlusPlusSettings;
import uk.debb.carpetplusplus.anvil.AnvilStaticFields;

import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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

    /**
     * @param ci the callback info
     * @author DragonEggBedrockBreaking
     * @reason schedule the block's deletion after 3gt
     */
    @Inject(method = "tick", at = @At("HEAD"))
    private void scheduleDeletion(CallbackInfo ci) {
        // Check for carpet rule and check if it is one of the falling blocks
        if (CarpetPlusPlusSettings.fallingBlockConversions && AnvilStaticFields.unobtainableBlockList.contains(this.blockState.getBlock())) {
            // Create a falling block entity instance
            FallingBlockEntity fallingBlockEntity = (FallingBlockEntity) (Object) this;
            // Schedule deletion in 3gt
            ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
            Runnable task = () -> {
                if (fallingBlockEntity != null) {
                    fallingBlockEntity.discard();
                }
            };
            executorService.schedule(task, 150, TimeUnit.MILLISECONDS);
            executorService.shutdown();
        }
    }

    /**
     * @param arg the item to drop
     * @return either the item or null
     */
    @Nullable
    @Override
    public ItemEntity spawnAtLocation(ItemLike arg) {
        // Check if the players are in the same chunk
        boolean sameChunk = false;
        for (ServerPlayer player : Objects.requireNonNull(level.getServer()).getPlayerList().getPlayers()) {
            if (this.chunkPosition().equals(player.chunkPosition())) {
                sameChunk = true;
                break;
            }
        }
        // Check if the gamerule is enabled, the block is one of the falling blocks, and the player is not in the same chunk
        if (CarpetPlusPlusSettings.fallingBlockConversions && AnvilStaticFields.unobtainableBlockList.contains(this.blockState.getBlock()) && !sameChunk) {
            // Return null
            return null;
        }
        // Return the vanilla value
        return super.spawnAtLocation(arg);
    }
}
