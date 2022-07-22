package uk.debb.carpetplusplus.mixin;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.DispenserBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import uk.debb.carpetplusplus.dispenser.OreDispenserBehaviour;

@Mixin(DispenserBlock.class)
public abstract class MixinDispenserBlock_dispensersMakeOres {
    /**
     * @param itemStack the stack of items in the dispensor
     * @param cir       the returnable callback info (net.minecraft.core.dispenser.DispenseItemBehavior)
     * @author DragonEggBedrockBreaking
     */
    @Inject(method = "getDispenseMethod", at = @At("HEAD"), cancellable = true)
    private void editDispenseMethod(ItemStack itemStack, CallbackInfoReturnable<DispenseItemBehavior> cir) {
        // Create a list of the possible items in the dispenser
        ObjectList<Item> oreConversionList = new ObjectArrayList<Item>() {{
            this.add(Items.COAL);
            this.add(Items.RAW_COPPER);
            this.add(Items.RAW_IRON);
            this.add(Items.RAW_GOLD);
            this.add(Items.COPPER_INGOT);
            this.add(Items.IRON_INGOT);
            this.add(Items.GOLD_INGOT);
            this.add(Items.REDSTONE);
            this.add(Items.LAPIS_LAZULI);
            this.add(Items.DIAMOND);
            this.add(Items.EMERALD);
            this.add(Items.QUARTZ);
            this.add(Items.GOLD_NUGGET);
        }};
        // If the item in the dispenser is one of them, run our custom code
        // Note that we are unable to check whether the block in front is correct here, that is checked later
        if (oreConversionList.contains(itemStack.getItem())) {
            // Sets the return value to that of our custom code
            cir.setReturnValue(new OreDispenserBehaviour());
        }
    }
}
