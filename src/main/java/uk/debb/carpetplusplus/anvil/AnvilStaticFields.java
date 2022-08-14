package uk.debb.carpetplusplus.anvil;

import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class AnvilStaticFields {
    public static final ObjectList<Block> unobtainableBlockList = new ObjectArrayList<>() {{
        add(Blocks.BEDROCK);
        add(Blocks.END_PORTAL_FRAME);
        add(Blocks.INFESTED_CHISELED_STONE_BRICKS);
        add(Blocks.INFESTED_COBBLESTONE);
        add(Blocks.INFESTED_CRACKED_STONE_BRICKS);
        add(Blocks.INFESTED_DEEPSLATE);
        add(Blocks.INFESTED_STONE);
        add(Blocks.INFESTED_MOSSY_STONE_BRICKS);
        add(Blocks.INFESTED_STONE_BRICKS);
        add(Blocks.SPAWNER);
        add(Blocks.DIRT_PATH);
        add(Blocks.FARMLAND);
        add(Blocks.CHORUS_PLANT);
        add(Blocks.REINFORCED_DEEPSLATE);

        add(Blocks.BARRIER);
        add(Blocks.STRUCTURE_BLOCK);
        add(Blocks.STRUCTURE_VOID);
        add(Blocks.PLAYER_HEAD);
        add(Blocks.COMMAND_BLOCK);
        add(Blocks.CHAIN_COMMAND_BLOCK);
        add(Blocks.REPEATING_COMMAND_BLOCK);
    }};
    public static final Object2ObjectMap<Block, Block> anvilMap = new Object2ObjectOpenHashMap<>() {{
        put(Blocks.ANVIL, Blocks.CHIPPED_ANVIL);
        put(Blocks.CHIPPED_ANVIL, Blocks.DAMAGED_ANVIL);
    }};
}
