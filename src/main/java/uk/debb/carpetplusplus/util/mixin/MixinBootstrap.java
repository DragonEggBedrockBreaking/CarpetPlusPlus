package uk.debb.carpetplusplus.util.mixin;

import net.minecraft.server.Bootstrap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.debb.carpetplusplus.CarpetPlusPlusServer;

import java.io.IOException;

@Mixin(Bootstrap.class)
public abstract class MixinBootstrap {
    /**
     * @param ci callback info
     * @throws IOException
     * @author DragonEggBedrockBreaking
     * @reason manually run code on initialisation without base api
     */
    @Inject(
            method = "bootStrap",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/core/Registry;freezeBuiltins()V"
            )
    )
    private static void onInitialize(CallbackInfo ci) throws IOException {
        CarpetPlusPlusServer.loadExtension();
    }
}
