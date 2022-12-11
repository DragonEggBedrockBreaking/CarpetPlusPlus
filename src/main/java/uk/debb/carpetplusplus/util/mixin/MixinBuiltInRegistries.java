package uk.debb.carpetplusplus.util.mixin;

import net.minecraft.core.registries.BuiltInRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.debb.carpetplusplus.CarpetPlusPlusServer;

@Mixin(BuiltInRegistries.class)
public abstract class MixinBuiltInRegistries {
    /**
     * @param ci callback info
     * @author DragonEggBedrockBreaking
     * @reason manually run code on initialisation without base api
     */
    @Inject(
            method = "bootStrap",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/core/registries/BuiltInRegistries;freeze()V"
            )
    )
    private static void onInitialize(CallbackInfo ci) {
        CarpetPlusPlusServer.loadExtension();
    }
}
