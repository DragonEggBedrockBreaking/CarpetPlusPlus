package uk.debb.carpetplusplus.mixin;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import uk.debb.carpetplusplus.CarpetPlusPlusSettings;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity_sonicBoomDropsEchoShard extends Entity {
    public MixinLivingEntity_sonicBoomDropsEchoShard(EntityType<? extends Entity> entityType, Level level) {
        super(entityType, level);
    }

    /**
     * @author DragonEggBedrockBreaking
     * @reason entities may drop echo shards when hurt by warden
     * @param damageSource the source of damage to the entity
     * @param f the amount of damage the quantity is receiving
     * @param cir the returnable callback info (Boolean)
     */
    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    private void dropShard(DamageSource damageSource, float f, CallbackInfoReturnable<Boolean> cir) {
        // Make sure that this is not run on the client
        if (this.level.isClientSide()) return;
        // Check for the carpet rule, if the entity can take damage, and if the damage is from sonic boom (magical + damage value 10)
        if (!this.isInvulnerableTo(damageSource) && CarpetPlusPlusSettings.sonicBoomDropsEchoShard && damageSource.isMagic() && f == 10.0F) {
            // Spawn a new echo shard entity
            ItemStack itemStack = Items.ECHO_SHARD.getDefaultInstance();
            ItemEntity itemEntity = new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), itemStack);
            this.level.addFreshEntity(itemEntity);
        }
    }
}
