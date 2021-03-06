package net.thenotfish.alkimicraft;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.thenotfish.alkimicraft.init.*;
import net.thenotfish.alkimicraft.init.tags.TagsInit;
import net.thenotfish.alkimicraft.world.biomes.POBiomes;
import net.thenotfish.alkimicraft.world.chunk.DryChunkGenerator;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.block.Blocks;
import net.minecraft.command.CommandException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.thenotfish.alkimicraft.init.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import static net.minecraft.server.command.CommandManager.literal;


public class AlkimiCraft implements ModInitializer {
	private static final Logger LOGGER = LoggerFactory.getLogger(AlkimiCraft.class);

	public static final String MOD_ID = "alkimicraft";
	public static ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(new Identifier(MOD_ID, "item_group"),
			() -> new ItemStack(ItemInit.TIPI_FIRE));
	public static ItemGroup SEEDS_GROUP = FabricItemGroupBuilder.build(new Identifier(MOD_ID, "seeds_group"),
			() -> new ItemStack(ItemInit.DESERT_POPLAR_SEEDS));

	// Dimensions
	private static final RegistryKey<DimensionOptions> DIMENSION_KEY = RegistryKey.of(Registry.DIMENSION_KEY, new Identifier(MOD_ID, "the_dry"));
	public static RegistryKey<World> WORLD_KEY = RegistryKey.of(Registry.WORLD_KEY, DIMENSION_KEY.getValue());
	private static final RegistryKey<DimensionType> DIMENSION_TYPE_KEY = RegistryKey.of(Registry.DIMENSION_TYPE_KEY, new Identifier(MOD_ID, "the_dry"));

	// Biomes


	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		StructureInit.init();
		ItemInit.init();
		BlockInit.init();
		BlockEntityInit.init();
		FluidInit.init();
		RecipeInit.init();
		EntityInit.init();
		TagsInit.init();
		ScreenInit.init();
		POBiomes.init();
		LootTableInit.init();


		// Dimensions
		Registry.register(Registry.CHUNK_GENERATOR, new Identifier(MOD_ID, "the_dry"), DryChunkGenerator.CODEC);
		WORLD_KEY = RegistryKey.of(Registry.WORLD_KEY, new Identifier(MOD_ID, "the_dry"));
		if (System.getProperty("fabric-api.gametest") != null) {
			// The gametest server does not support custom worlds
			return;
		}

		ServerLifecycleEvents.SERVER_STARTED.register(server -> {
			if (true){return;}
			ServerWorld overworld = server.getWorld(World.OVERWORLD);
			ServerWorld world = server.getWorld(WORLD_KEY);

			if (world == null) throw new AssertionError("Test world doesn't exist.");

			Entity entity = EntityType.COW.create(overworld);

			if (!entity.world.getRegistryKey().equals(World.OVERWORLD)) throw new AssertionError("Entity starting world isn't the overworld");

			TeleportTarget target = new TeleportTarget(Vec3d.ZERO, new Vec3d(1, 1, 1), 45f, 60f);

			Entity teleported = FabricDimensions.teleport(entity, world, target);

			if (teleported == null) throw new AssertionError("Entity didn't teleport");

			if (!teleported.world.getRegistryKey().equals(WORLD_KEY)) throw new AssertionError("Target world not reached.");

			if (!teleported.getPos().equals(target.position)) throw new AssertionError("Target Position not reached.");
		});

		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) ->
				dispatcher.register(literal("fabric_dimension_test").executes(AlkimiCraft.this::swapTargeted))
		);
	}


	/* Dimensions */
	private int swapTargeted(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		ServerPlayerEntity player = context.getSource().getPlayer();
		ServerWorld serverWorld = player.getWorld();
		ServerWorld modWorld = getModWorld(context);

		if (serverWorld != modWorld) {
			TeleportTarget target = new TeleportTarget(new Vec3d(0.5, 101, 0.5), Vec3d.ZERO, 0, 0);
			FabricDimensions.teleport(player, modWorld, target);

			if (player.world != modWorld) {
				throw new CommandException(new LiteralText("Teleportation failed!"));
			}

			modWorld.setBlockState(new BlockPos(0, 100, 0), Blocks.DIAMOND_BLOCK.getDefaultState());
			modWorld.setBlockState(new BlockPos(0, 101, 0), Blocks.TORCH.getDefaultState());
		} else {
			TeleportTarget target = new TeleportTarget(new Vec3d(0, 100, 0), Vec3d.ZERO,
					(float) Math.random() * 360 - 180, (float) Math.random() * 360 - 180);
			FabricDimensions.teleport(player, getWorld(context, World.OVERWORLD), target);
		}

		return 1;
	}


	private ServerWorld getModWorld(CommandContext<ServerCommandSource> context) {
		return getWorld(context, WORLD_KEY);
	}

	private ServerWorld getWorld(CommandContext<ServerCommandSource> context, RegistryKey<World> dimensionRegistryKey) {
		return context.getSource().getServer().getWorld(dimensionRegistryKey);
	}
}
