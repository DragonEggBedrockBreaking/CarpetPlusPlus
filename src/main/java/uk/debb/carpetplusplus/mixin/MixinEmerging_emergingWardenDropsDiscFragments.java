package uk.debb.carpetplusplus.mixin;

import java.util.Random;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.warden.Emerging;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import uk.debb.carpetplusplus.CarpetPlusPlusSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Emerging.class)
public abstract class MixinEmerging_emergingWardenDropsDiscFragments<E extends Warden> {
    /**
     * @author DragonEggBedrockBreaking
     * @reason drop between 2 and 5 disc fragments when the warden emerges
     * @param serverLevel the level that the warden is in
     * @param warden the warden
     * @param l ???
     * @param ci the callback info
     */
    @Inject(method = "stop", at = @At("RETURN"), cancellable = true)
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
