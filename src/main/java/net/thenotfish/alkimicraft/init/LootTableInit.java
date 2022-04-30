package net.thenotfish.alkimicraft.init;

import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.block.Blocks;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.BinomialLootNumberProvider;
import net.minecraft.util.Identifier;

public class LootTableInit {

    private static final Identifier GRASS_BLOCK_LOOT_TABLE_ID = Blocks.GRASS_BLOCK.getLootTableId();

    public static void init(){
        LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, id, table, setter) -> {
            if (GRASS_BLOCK_LOOT_TABLE_ID.equals(id)) {
                FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                        .rolls(BinomialLootNumberProvider.create(1, 0.1f))
                        .with(ItemEntry.builder(ItemInit.GRASS_SEEDS));

                table.pool(poolBuilder);
            }
        });
    }

}
