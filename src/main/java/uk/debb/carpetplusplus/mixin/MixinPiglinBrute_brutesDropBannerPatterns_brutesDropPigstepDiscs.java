package uk.debb.carpetplusplus.mixin;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import uk.debb.carpetplusplus.CarpetPlusPlusSettings;

import java.util.Random;

@Mixin(PiglinBrute.class)
public abstract class MixinPiglinBrute_brutesDropBannerPatterns_brutesDropPigstepDiscs extends LivingEntity {
    public MixinPiglinBrute_brutesDropBannerPatterns_brutesDropPigstepDiscs(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    /**
     * @param damageSource the source of the damage that they are taking
     * @author DragonEggBedrockBreaking
     * @reason piglin brutes can drop some things when killed by fortress mobs
     */
    @Override
    public void die(DamageSource damageSource) {
        // Make sure that this is not run on the client
        if (this.level.isClientSide()) return;
        // Get the killer
        Entity killer = damageSource.getEntity();
        // If the killer is a blaze, check the banner gamerule
        if (killer instanceof Blaze && CarpetPlusPlusSettings.brutesDropBannerPatterns) {
            // There is a 1 in 12 change of a piglin banner pattern dropping
            Random random = new Random();
            if (random.nextInt(12) == 0) {
                ItemStack itemStack = Items.PIGLIN_BANNER_PATTERN.getDefaultInstance();
                ItemEntity itemEntity = new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), itemStack);
                this.level.addFreshEntity(itemEntity);
            }
            // If the killer is a wither skeleton, check the banner gamerule
        } else if (killer instanceof WitherSkeleton && CarpetPlusPlusSettings.brutesDropPigstepDiscs) {
            // There is a 1 in 8 change of a piglin banner pattern dropping
            Random random = new Random();
            if (random.nextInt(8) == 0) {
                ItemStack itemStack = Items.MUSIC_DISC_PIGSTEP.getDefaultInstance();
                ItemEntity itemEntity = new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), itemStack);
                this.level.addFreshEntity(itemEntity);
            }
        }
        // Run vanilla death code
        super.die(damageSource);
    }
}
