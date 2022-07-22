package uk.debb.carpetplusplus.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.warden.Emerging;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.debb.carpetplusplus.CarpetPlusPlusSettings;

import java.util.Random;

@Mixin(Emerging.class)
public abstract class MixinEmerging_emergingWardenDropsDiscFragments<E extends Warden> {
    /**
     * @param serverLevel the level that the warden is in
     * @param warden      the warden
     * @param l           ???
     * @param ci          the callback info
     * @author DragonEggBedrockBreaking
     * @reason drop between 2 and 5 disc fragments when the warden emerges
     */
    @Inject(method = "stop(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/monster/warden/Warden;J)V", at = @At("RETURN"))
    private void dropFragment(ServerLevel serverLevel, E warden, long l, CallbackInfo ci) {
        // Make sure that this is not run on the client
        if (warden.getLevel().isClientSide()) return;
        // Check for the carpet rule
        if (CarpetPlusPlusSettings.emergingWardenDropsDiscFragments) {
            // Get a random object
            Random random = new Random();
            // Iterate between 2 and 5 times
            for (int i = 0; i < random.nextInt(1, 4); ++i) {
                // Spawn a new fragment entity
                ItemStack itemStack = Items.DISC_FRAGMENT_5.getDefaultInstance();
                ItemEntity itemEntity = new ItemEntity(warden.getLevel(), warden.getX(), warden.getY(), warden.getZ(), itemStack);
                warden.getLevel().addFreshEntity(itemEntity);
            }
        }
    }
}
