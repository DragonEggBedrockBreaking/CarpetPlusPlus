package uk.debb.carpetplusplus.mixin;

import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import uk.debb.carpetplusplus.dispenser.OreDispenserBehaviour;

@Mixin(DispenserBlock.class)
public abstract class MixinDispenserBlock_dispensersMakeOres {
    /**
     * @param itemStack the stack of items in the dispenser
     * @param cir       the returnable callback info (net.minecraft.core.dispenser.DispenseItemBehavior)
     * @author DragonEggBedrockBreaking
     */
    @Inject(method = "getDispenseMethod", at = @At("HEAD"), cancellable = true)
    private void editDispenseMethod(ItemStack itemStack, CallbackInfoReturnable<DispenseItemBehavior> cir) {
        // If the item in the dispenser is one of them, run our custom code
        // Note that we are unable to check whether the block in front is correct here, that is checked later
        if (OreDispenserBehaviour.oreConversionList.contains(itemStack.getItem())) {
            // Sets the return value to that of our custom code
            cir.setReturnValue(new OreDispenserBehaviour());
        }
    }
}
