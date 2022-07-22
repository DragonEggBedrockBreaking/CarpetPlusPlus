package uk.debb.carpetplusplus.mixin;

import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import uk.debb.carpetplusplus.CarpetPlusPlusSettings;

@Mixin(FallingBlockEntity.class)
public abstract class MixinFallingBlockEntity_fallingBlockConversions extends Entity {
    @Shadow private BlockState blockState;

    public MixinFallingBlockEntity_fallingBlockConversions(EntityType<? extends Entity> entityType, Level level) {
        super(entityType, level);
    }

    @Unique
    private static final ObjectList<Block> unobtainableBlockList = new ObjectArrayList<>() {{
        add(Blocks.BEDROCK);
        add(Blocks.END_PORTAL);
        add(Blocks.INFESTED_CHISELED_STONE_BRICKS);
        add(Blocks.INFESTED_COBBLESTONE);
        add(Blocks.INFESTED_CRACKED_STONE_BRICKS);
        add(Blocks.INFESTED_DEEPSLATE);
        add(Blocks.INFESTED_STONE);
        add(Blocks.INFESTED_MOSSY_STONE_BRICKS);
        add(Blocks.INFESTED_STONE_BRICKS);
        add(Blocks.SPAWNER);
        add(Blocks.DIRT_PATH);
        add(Blocks.FARMLAND);
        add(Blocks.CHORUS_PLANT);
        add(Blocks.REINFORCED_DEEPSLATE);

        add(Blocks.BARRIER);
        add(Blocks.STRUCTURE_BLOCK);
        add(Blocks.STRUCTURE_VOID);
        add(Blocks.PLAYER_HEAD);
        add(Blocks.COMMAND_BLOCK);
        add(Blocks.CHAIN_COMMAND_BLOCK);
        add(Blocks.REPEATING_COMMAND_BLOCK);
    }};

    @Unique
    private static final Object2ObjectMap<Block, Block> anvilMap = new Object2ObjectOpenHashMap<>() {{
       put(Blocks.ANVIL, Blocks.CHIPPED_ANVIL);
       put(Blocks.CHIPPED_ANVIL, Blocks.DAMAGED_ANVIL);
    }};

    /**
     * @param arg the state of the falling block
     * @author DragonEggBedrockBreaking
     */
    @Override
    protected void onInsideBlock(BlockState arg) {
        // Check if the gamerule is enabled
        if (CarpetPlusPlusSettings.fallingBlockConversions) {
            // If the block is one of the first anvils
            if (this.blockState.is(Blocks.ANVIL) || this.blockState.is(Blocks.CHIPPED_ANVIL)) {
                // Get all horizontal directions
                for (Direction direction : Direction.Plane.HORIZONTAL) {
                    // Store some data in variables
                    BlockPos pos = this.blockPosition().relative(direction);
                    BlockState state = this.level.getBlockState(pos);
                    // If the anvil is the next stage down
                    if (state.is(anvilMap.get(this.blockState.getBlock()))) {
                        // Turn the new anvil into a falling block
                        FallingBlockEntity.fall(this.level, pos, state);
                    }
                }
            // However, if it is a last stage anvil
            } else if (this.blockState.is(Blocks.DAMAGED_ANVIL)) {
                // Get all horizontal directions
                for (Direction direction : Direction.Plane.HORIZONTAL) {
                    // Store some data in variables
                    BlockPos pos = this.blockPosition().relative(direction);
                    BlockState state = this.level.getBlockState(pos);
                    // If the block is in a lazy chunk, and the block is unobtainable
                    if (unobtainableBlockList.contains(state.getBlock()) && !level.shouldTickBlocksAt(pos)) {
                        // Turn the unobtainable block into a falling block
                        FallingBlockEntity.fall(this.level, pos, state);
                    }
                }
            }
        }
    }
}
