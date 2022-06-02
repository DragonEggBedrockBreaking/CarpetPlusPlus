package uk.debb.carpetplusplus.mixin;

import java.util.Random;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import uk.debb.carpetplusplus.CarpetPlusPlusSettings;

@Mixin(Husk.class)
public abstract class MixinHusk_shedSand_dropRedSand extends Entity {
    public MixinHusk_shedSand_dropRedSand(EntityType<? extends Entity> entityType, Level level) {
        super(entityType, level);
    }

    /**
     * @author DragonEggBedrockBreaking
     * @reason husks shed some sand when they move
     * @param moverType how the husk is being moved
     * @param vec3 how far the husk is being moved
     */
    @Override
    public void move(MoverType moverType, Vec3 vec3) {
        // Make sure that this is not run on the client
        if (this.level.isClientSide()) return;
        // Gets the block that the husk is on top of
        BlockState blockOn = level.getBlockState(this.getOnPos());
        // A lot of checks
        // - The carpet rule is enabled
        // - The husk is on top of either sand or red sand
        // - The husk is moving itself (not being pushed by piston, player, etc.)
        // - The husk is not standing still
        // - The husk is not moving much vertically
        if (CarpetPlusPlusSettings.husksShedSand && (blockOn.is(Blocks.SAND) || blockOn.is(Blocks.RED_SAND)) &&
            moverType == MoverType.SELF && (vec3.x != 0 || vec3.z != 0) && Math.abs(vec3.y) <= 0.2) {
            // If the conditions are met, there is a 1 in 130 chance of a sand item spawning
            Random random = new Random();
            if (random.nextInt(130) == 0) {
                ItemStack itemStack = Items.SAND.getDefaultInstance();
                ItemEntity itemEntity = new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), itemStack);
                this.level.addFreshEntity(itemEntity);
            }
        }
        super.move(moverType, vec3);
    }

    /**
     * @author DragonEggBedrockBreaking
     * @reason husks drop red sand when hurt
     * @param DamageSource the source of the damage that they are taking
     * @param f how much damage they are taking
     * @return whether the husk is hurt
     */
    @Override
    public boolean hurt(DamageSource damageSource, float f) {
        // Make sure that this is not run on the client
        if (this.level.isClientSide()) return false;
        // Check whether the mob is hurt
        boolean success = super.hurt(damageSource, f);
        // If the mob is hurt and the gamerule is enabled...
        if (success && CarpetPlusPlusSettings.husksDropRedSand) {
            // There is a 1 in 3 change of a red sand entity dropping
            Random random = new Random();
            if (random.nextInt(3) == 0) {
                ItemStack itemStack = Items.RED_SAND.getDefaultInstance();
                ItemEntity itemEntity = new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), itemStack);
                this.level.addFreshEntity(itemEntity);
            }
        }
        // Return vanilla value
        return success;
    }
}
