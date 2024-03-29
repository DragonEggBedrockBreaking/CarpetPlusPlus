package uk.debb.carpetplusplus.dispenser;

import it.unimi.dsi.fastutil.objects.*;
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
    public static final ObjectList<Item> oreConversionList = new ObjectArrayList<>() {{
        add(Items.COAL);
        add(Items.RAW_COPPER);
        add(Items.RAW_IRON);
        add(Items.RAW_GOLD);
        add(Items.COPPER_INGOT);
        add(Items.IRON_INGOT);
        add(Items.GOLD_INGOT);
        add(Items.REDSTONE);
        add(Items.LAPIS_LAZULI);
        add(Items.DIAMOND);
        add(Items.EMERALD);
        add(Items.QUARTZ);
        add(Items.GOLD_NUGGET);
    }};

    /**
     * @param entry the item in the dispenser and the block in front if ot
     * @return the ore that needs to be made
     * @author DragonEggBedrockBreaking
     */
    private static Block getOreConversion(Object2ObjectMap.Entry<Item, Block> entry) {
        // Creates a map of all the combinations of resources and blocks,
        // And maps those combinations to the ores they make
        return new Object2ObjectOpenHashMap<Object2ObjectMap.Entry<Item, Block>, Block>() {{
            this.put(new BasicEntry<>(Items.COAL, Blocks.STONE), Blocks.COAL_ORE);
            this.put(new BasicEntry<>(Items.COAL, Blocks.DEEPSLATE), Blocks.DEEPSLATE_COAL_ORE);
            this.put(new BasicEntry<>(Items.RAW_COPPER, Blocks.STONE), Blocks.COPPER_ORE);
            this.put(new BasicEntry<>(Items.RAW_COPPER, Blocks.DEEPSLATE), Blocks.DEEPSLATE_COPPER_ORE);
            this.put(new BasicEntry<>(Items.RAW_IRON, Blocks.STONE), Blocks.IRON_ORE);
            this.put(new BasicEntry<>(Items.RAW_IRON, Blocks.DEEPSLATE), Blocks.DEEPSLATE_IRON_ORE);
            this.put(new BasicEntry<>(Items.RAW_GOLD, Blocks.STONE), Blocks.GOLD_ORE);
            this.put(new BasicEntry<>(Items.RAW_GOLD, Blocks.DEEPSLATE), Blocks.DEEPSLATE_GOLD_ORE);
            this.put(new BasicEntry<>(Items.COPPER_INGOT, Blocks.STONE), Blocks.COPPER_ORE);
            this.put(new BasicEntry<>(Items.COPPER_INGOT, Blocks.DEEPSLATE), Blocks.DEEPSLATE_COPPER_ORE);
            this.put(new BasicEntry<>(Items.IRON_INGOT, Blocks.STONE), Blocks.IRON_ORE);
            this.put(new BasicEntry<>(Items.IRON_INGOT, Blocks.DEEPSLATE), Blocks.DEEPSLATE_IRON_ORE);
            this.put(new BasicEntry<>(Items.GOLD_INGOT, Blocks.STONE), Blocks.GOLD_ORE);
            this.put(new BasicEntry<>(Items.GOLD_INGOT, Blocks.DEEPSLATE), Blocks.DEEPSLATE_GOLD_ORE);
            this.put(new BasicEntry<>(Items.REDSTONE, Blocks.STONE), Blocks.REDSTONE_ORE);
            this.put(new BasicEntry<>(Items.REDSTONE, Blocks.DEEPSLATE), Blocks.DEEPSLATE_REDSTONE_ORE);
            this.put(new BasicEntry<>(Items.LAPIS_LAZULI, Blocks.STONE), Blocks.LAPIS_ORE);
            this.put(new BasicEntry<>(Items.LAPIS_LAZULI, Blocks.DEEPSLATE), Blocks.LAPIS_ORE);
            this.put(new BasicEntry<>(Items.DIAMOND, Blocks.STONE), Blocks.DIAMOND_ORE);
            this.put(new BasicEntry<>(Items.DIAMOND, Blocks.DEEPSLATE), Blocks.DIAMOND_ORE);
            this.put(new BasicEntry<>(Items.EMERALD, Blocks.STONE), Blocks.EMERALD_ORE);
            this.put(new BasicEntry<>(Items.EMERALD, Blocks.DEEPSLATE), Blocks.DEEPSLATE_EMERALD_ORE);
            this.put(new BasicEntry<>(Items.QUARTZ, Blocks.NETHERRACK), Blocks.NETHER_QUARTZ_ORE);
            this.put(new BasicEntry<>(Items.GOLD_NUGGET, Blocks.NETHERRACK), Blocks.NETHER_GOLD_ORE);
            this.put(new BasicEntry<>(Items.GOLD_NUGGET, Blocks.BLACKSTONE), Blocks.GILDED_BLACKSTONE);
        }}.get(entry); // Returns the value of the entry provided in the map
    }

    /**
     * @param source the data relating to the dispenser
     * @param stack  the stack of items that the dispenser is going to use
     * @author DragonEggBedrockBreaking
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
            Object2ObjectMap.Entry<Item, Block> entry = new AbstractObject2ObjectMap.BasicEntry<>(stack.getItem(), frontBlock);
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
