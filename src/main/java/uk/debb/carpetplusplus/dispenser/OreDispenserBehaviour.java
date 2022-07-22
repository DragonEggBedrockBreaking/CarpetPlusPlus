package uk.debb.carpetplusplus.dispenser;

import it.unimi.dsi.fastutil.objects.AbstractObject2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import uk.debb.carpetplusplus.CarpetPlusPlusSettings;

import java.util.Random;

public class OreDispenserBehaviour extends OptionalDispenseItemBehavior {
    /**
     * @param entry the item in the dispenser and the block in front if ot
     * @return the ore that needs to be made
     * @author DragonEggBedrockBreaking
     * @reason this function gets the block that needs to be made
     */
    private static Block getOreConversion(Object2ObjectMap.Entry<Item, Block> entry) {
        // Creates a map of all of the combinations of resources and blocks,
        // And maps those combinations to the ores they make
        return new Object2ObjectOpenHashMap<Object2ObjectMap.Entry<Item, Block>, Block>() {{
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

    /**
     * @param source the data relating to the dispenser
     * @param stack  the stack of items that the dispenser is going to use
     * @author DragonEggBedrockBreaking
     * @reason runs our conversion code instead of the vanilla, if the conditions are met
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
            Object2ObjectMap.Entry<Item, Block> entry = new AbstractObject2ObjectMap.BasicEntry<Item, Block>(stack.getItem(), frontBlock);
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
