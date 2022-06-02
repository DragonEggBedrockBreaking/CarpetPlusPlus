package uk.debb.carpetplusplus.util.mixin;

import java.io.IOException;
import net.minecraft.server.Bootstrap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.debb.carpetplusplus.CarpetPlusPlusServer;

@Mixin(Bootstrap.class)
public abstract class MixinBootstrap {
    /**
     * @author DragonEggBedrockBreaking
     * @reason manually run code on initialisation without base api
     * @param ci callback info
     * @throws IOException
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
