package uk.debb.carpetplusplus.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.CaveSpider;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import uk.debb.carpetplusplus.CarpetPlusPlusSettings;

@Mixin(CaveSpider.class)
public abstract class MixinCaveSpider_cobwebWeaving extends Spider {
    public MixinCaveSpider_cobwebWeaving(EntityType<? extends Spider> entityType, Level level) {
        super(entityType, level);
    }

    /**
     * @author DragonEggBedrockBreaking
     */
    @Override
    public void tick() {
        // Make sure that this is not run on the client
        if (this.level.isClientSide()) return;
        // Runs all the usual ticking code first
        // Extends spider because cave spider's ticking code is identical to spider's
        // Therefore cave spider also doesn't override the function
        super.tick();
        // Checks the carpet rule
        if (CarpetPlusPlusSettings.cobwebWeaving) {
            // Stores the position of the spider, then the block they are in
            BlockPos blockPos = new BlockPos(this.getX(), this.getY(), this.getZ());
            BlockState blockIn = this.getLevel().getBlockState(blockPos);
            // If the block is string, convert it to tripwire
            if (blockIn.is(Blocks.TRIPWIRE)) {
                this.getLevel().setBlockAndUpdate(blockPos, Blocks.COBWEB.defaultBlockState());
            }
        }
    }
}
