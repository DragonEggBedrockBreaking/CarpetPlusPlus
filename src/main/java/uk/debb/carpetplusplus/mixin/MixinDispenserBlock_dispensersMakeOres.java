package uk.debb.carpetplusplus.mixin;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import it.unimi.dsi.fastutil.objects.AbstractObject2ObjectMap.BasicEntry;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap.Entry;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import uk.debb.carpetplusplus.CarpetPlusPlusSettings;

@Mixin(DispenserBlock.class)
public abstract class MixinDispenserBlock_dispensersMakeOres {
    /**
     * @author DragonEggBedrockBreaking
     * @reason this function gets the block that needs to be made
     * @param entry the item in the dispenser and the block in front if ot
     * @return the ore that needs to be made
     */
    @Unique
    private static Block getOreConversion(Entry<Item, Block> entry) {
        // Creates a map of all of the combinations of resources and blocks,
        // And maps those combinations to the ores they make
        return new Object2ObjectOpenHashMap<Entry<Item, Block>, Block>() {{
            this.put(new BasicEntry<Item, Block>(Items.COAL, Blocks.STONE), Blocks.COAL_ORE);
            this.put(new BasicEntry<Item, Block>(Items.COAL, Blocks.DEEPSLATE), Blocks.DEEPSLATE_COAL_ORE);
            this.put(new BasicEntry<Item, Block>(Items.RAW_COPPER, Blocks.STONE), Blocks.COPPER_ORE);
            this.put(new BasicEntry<Item, Block>(Items.RAW_COPPER, Blocks.DEEPSLATE), Blocks.DEEPSLATE_COPPER_ORE);
            this.put(new BasicEntry<Item, Block>(Items.RAW_IRON, Blocks.STONE), Blocks.IRON_ORE);
            this.put(new BasicEntry<Item, Block>(Items.RAW_IRON, Blocks.DEEPSLATE), Blocks.DEEPSLATE_IRON_ORE);
            this.put(new BasicEntry<Item, Block>(Items.RAW_GOLD, Blocks.STONE), Blocks.GOLD_ORE);
            this.put(new BasicEntry<Item, Block>(Items.RAW_GOLD, Blocks.DEEPSLATE), Blocks.DEEPSLATE_GOLD_ORE);
            this.put(new BasicEntry<Item, Block>(Items.COPPER_INGOT, Blocks.STONE), Blocks.COPPER_ORE);
            this.put(new BasicEntry<Item, Block>(Items.COPPER_INGOT, Blocks.DEEPSLATE), Blocks.DEEPSLATE_COPPER_ORE);
            this.put(new BasicEntry<Item, Block>(Items.IRON_INGOT, Blocks.STONE), Blocks.IRON_ORE);
            this.put(new BasicEntry<Item, Block>(Items.IRON_INGOT, Blocks.DEEPSLATE), Blocks.DEEPSLATE_IRON_ORE);
            this.put(new BasicEntry<Item, Block>(Items.GOLD_INGOT, Blocks.STONE), Blocks.GOLD_ORE);
            this.put(new BasicEntry<Item, Block>(Items.GOLD_INGOT, Blocks.DEEPSLATE), Blocks.DEEPSLATE_GOLD_ORE);
            this.put(new BasicEntry<Item, Block>(Items.REDSTONE, Blocks.STONE), Blocks.REDSTONE_ORE);
            this.put(new BasicEntry<Item, Block>(Items.REDSTONE, Blocks.DEEPSLATE), Blocks.DEEPSLATE_REDSTONE_ORE);
            this.put(new BasicEntry<Item, Block>(Items.LAPIS_LAZULI, Blocks.STONE), Blocks.LAPIS_ORE);
            this.put(new BasicEntry<Item, Block>(Items.LAPIS_LAZULI, Blocks.DEEPSLATE), Blocks.LAPIS_ORE);
            this.put(new BasicEntry<Item, Block>(Items.DIAMOND, Blocks.STONE), Blocks.DIAMOND_ORE);
            this.put(new BasicEntry<Item, Block>(Items.DIAMOND, Blocks.DEEPSLATE), Blocks.DIAMOND_ORE);
            this.put(new BasicEntry<Item, Block>(Items.EMERALD, Blocks.STONE), Blocks.EMERALD_ORE);
            this.put(new BasicEntry<Item, Block>(Items.EMERALD, Blocks.DEEPSLATE), Blocks.DEEPSLATE_EMERALD_ORE);
            this.put(new BasicEntry<Item, Block>(Items.QUARTZ, Blocks.NETHERRACK), Blocks.NETHER_QUARTZ_ORE);
            this.put(new BasicEntry<Item, Block>(Items.GOLD_NUGGET, Blocks.NETHERRACK), Blocks.NETHER_GOLD_ORE);
            this.put(new BasicEntry<Item, Block>(Items.GOLD_NUGGET, Blocks.BLACKSTONE), Blocks.GILDED_BLACKSTONE);
        }}.get(entry); // Returns the value of the entry provided in the map
    }

    // Creates a class for our custom dispenser behaviour
    public static class OreDispenserBehaviour extends OptionalDispenseItemBehavior {
        /**
         * @author DragonEggBedrockBreaking
         * @reason runs our conversion code instead of the vanilla, if the conditions are met
         * @param source the data relating to the dispenser
         * @param stack the stack of items that the dispenser is going to use
         */
        @Override
        protected ItemStack execute(BlockSource source, ItemStack stack) {
            // Make sure that this is not run on the client
            if (source.getLevel().isClientSide()) return super.execute(source, stack);
            // Creates a random object
            Random random = new Random();
            // Checks if the carpet rule is enabled
            // There is also only a 7 in 10 chance that the following code will be executed
            if (CarpetPlusPlusSettings.dispensersMakeOres && random.nextInt(10) > 2) {
                // Stores the position of the block in front of the dispenser
                BlockPos frontBlockPos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
                // Uses the position to get the block in front of the dispenser
                Block frontBlock = source.getLevel().getBlockState(frontBlockPos).getBlock();
                // Creates an entry containing the item that the dispenser is using and the block in front of it
                Entry<Item, Block> entry = new BasicEntry<Item, Block>(stack.getItem(), frontBlock);
                // Gets the block that needs to be converted using the entry
                Block changeTo = getOreConversion(entry);
                // If the combination is valid
                if (changeTo != null) {
                    // Remove an item from the stack
                    stack.split(1);
                    // Change the block in front of the dispenser
                    source.getLevel().setBlockAndUpdate(frontBlockPos, changeTo.defaultBlockState());
                    // Exit the function
                    return stack;
                }
            }
            // If any of the checks fail, run the vanilla code
            return super.execute(source, stack);
        }
    }

    /**
     * @author DragonEggBedrockBreaking
     * @param itemStack the stack of items in the dispensor
     * @param cir the returnable callback info (net.minecraft.core.dispenser.DispenseItemBehavior)
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
