package net.fabricmc.alkimicraft.init;

import net.fabricmc.alkimicraft.AlkimiCraft;
import net.fabricmc.alkimicraft.blocks.*;
import net.fabricmc.alkimicraft.structures.trees.DesertPoplarSaplingGenerator;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.block.*;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.block.sapling.OakSaplingGenerator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

import static net.minecraft.block.cauldron.CauldronBehavior.FILL_WITH_WATER;

public class BlockInit {
    //WoodenBarrel Behaviors
    public static final Map<Item, CauldronBehavior> EMPTY_BARREL_BEHAVIOR = CauldronBehavior.createMap();
    public static final Map<Item, CauldronBehavior> WATER_BARREL_BEHAVIOR = CauldronBehavior.createMap();
    public static final CauldronBehavior BARREL_FILL_WITH_WATER = (state, world, pos, player, hand, stack) -> {
        return CauldronBehavior.fillCauldron(world, pos, player, hand, stack, BlockInit.WOODEN_BARREL_WATER.getDefaultState().with(LeveledCauldronBlock.LEVEL, 3), SoundEvents.ITEM_BUCKET_EMPTY);
    };

//    public static final TestPlant TEST_PLANT = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID, "test_plant"), new TestPlant(FabricBlockSettings.of(Material.PLANT).noCollision().ticksRandomly().strength(0.0F, 0.0F).sounds(BlockSoundGroup.CROP).nonOpaque()));
    public static Block TOXIC_SEWAGE = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID, "toxic_sewage"), new BlockSewage(FluidInit.TOXIC_SEWAGE_STILL, FabricBlockSettings.of(Material.WATER).dropsNothing()){});
//    public static Block SALTY_WATER = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID, "salty_water"), new FluidBlock(FluidInit.SALTY_WATER_STILL, FabricBlockSettings.of(Material.WATER).dropsNothing()){});
//    public static Block FRESH_WATER = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID, "fresh_water"), new FluidBlock(FluidInit.FRESH_WATER_STILL, FabricBlockSettings.of(Material.WATER).dropsNothing()){});

    public static final BlockReed BLOCK_REED = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID, "reed_block"), new BlockReed(AbstractBlock.Settings.of(Material.UNDERWATER_PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS).nonOpaque()));
    public static Block LOAMY_SAND = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID, "loamy_sand"), new Block(AbstractBlock.Settings.of(Material.SOIL).strength(0.5F).sounds(BlockSoundGroup.GRAVEL)));
    public static Block SANDY_LOAM = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID, "sandy_loam"), new Block(AbstractBlock.Settings.of(Material.SOIL).strength(0.5F).sounds(BlockSoundGroup.GRAVEL)));
    public static Block BARREN_LOAM = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID, "barren_loam"), new Block(AbstractBlock.Settings.of(Material.SOIL).strength(0.5F).sounds(BlockSoundGroup.GRAVEL)));
    public static Block SEDIMENTARY_ROCK = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID, "sedimentary_rock"), new Block(AbstractBlock.Settings.of(Material.STONE).strength(1.5F, 6.0F).sounds(BlockSoundGroup.STONE).requiresTool()));
    public static Block ASH_BLOCK = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID, "ash_block"), new Block(AbstractBlock.Settings.of(Material.SOIL).strength(0.5F).sounds(BlockSoundGroup.SAND)));
    public static Block CHARRED_LOG = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID,"charred_log"), createLogBlock(MapColor.BLACK, MapColor.SPRUCE_BROWN));

    public static Block SPARSE_GRASS_BLOCK = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID, "sparse_grass_block"), new SparseGrassBlock(AbstractBlock.Settings.of(Material.SOLID_ORGANIC).ticksRandomly().strength(0.9F).sounds(BlockSoundGroup.GRAVEL)));

    public static Block POROUS_STONE = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID, "porous_stone"), new BlockPorousStone(AbstractBlock.Settings.of(Material.STONE).requiresTool().strength(1.2F, 6.0F).sounds(BlockSoundGroup.STONE)));
    public static Block IRON_COBBLESTONE = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID, "iron_cobblestone"), new BlockIronCobblestone(AbstractBlock.Settings.of(Material.STONE).requiresTool().strength(1.5F, 6.0F).sounds(BlockSoundGroup.STONE)));


    public static Block ARTEMISIA_ARENARIA = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID, "artemisia_arenaria"), new BlockArtemisiaArenaria(AbstractBlock.Settings.of(Material.PLANT).noCollision().ticksRandomly().sounds(BlockSoundGroup.CROP).nonOpaque()));
    public static Block IRON_ROOT_GRASS = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID, "iron_root_grass"), new BlockIronRootGrass(AbstractBlock.Settings.of(Material.PLANT).noCollision().ticksRandomly().sounds(BlockSoundGroup.CROP).nonOpaque()));
    public static Block FERTILE_BEANS = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID, "fertile_beans"), new FertileBeans(AbstractBlock.Settings.of(Material.PLANT).noCollision().ticksRandomly().sounds(BlockSoundGroup.CROP).nonOpaque()));


    public static Block DESERT_POPLAR_LEAVES = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID,"desert_poplar_leaves"), createLeavesBlock(BlockSoundGroup.GRASS));
    public static Block DESERT_POPLAR_LOG = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID,"desert_poplar_log"), createLogBlock(MapColor.OAK_TAN, MapColor.SPRUCE_BROWN));
    public static Block STRIPPED_DESERT_POPLAR_LOG = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID,"stripped_desert_poplar_log"), createLogBlock(MapColor.OAK_TAN, MapColor.YELLOW));
    public static Block DESERT_POPLAR_SAPLING = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID, "desert_poplar_sapling"), new DesertPoplarSaplingBlock(new DesertPoplarSaplingGenerator(), AbstractBlock.Settings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS)));
    public static Block DESERT_POPLAR_PLANKS = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID,"desert_poplar_planks"), new Block(AbstractBlock.Settings.of(Material.WOOD).strength(1f).sounds(BlockSoundGroup.WOOD)));
    public static Block DESERT_POPLAR_STAIRS = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID, "desert_poplar_stairs"), new StairsBlock(DESERT_POPLAR_PLANKS.getDefaultState(), AbstractBlock.Settings.copy(Blocks.OAK_STAIRS)){});
    public static Block DESERT_POPLAR_SLAB = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID, "desert_poplar_slab"), new SlabBlock(AbstractBlock.Settings.copy(Blocks.OAK_SLAB)));
    public static Block DESERT_POPLAR_FENCE = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID, "desert_poplar_fence"), new FenceBlock(AbstractBlock.Settings.copy(Blocks.OAK_FENCE)));
    public static Block DESERT_POPLAR_FENCE_GATE = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID, "desert_poplar_fence_gate"), new FenceGateBlock(AbstractBlock.Settings.copy(Blocks.OAK_FENCE_GATE)));
    public static Block DESERT_POPLAR_BUTTON = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID, "desert_poplar_button"), new WoodenButtonBlock(AbstractBlock.Settings.copy(Blocks.OAK_BUTTON)){});
    public static Block DESERT_POPLAR_PRESSURE_PLATE = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID, "desert_poplar_pressure_plate"), new PressurePlateBlock(PressurePlateBlock.ActivationRule.MOBS, AbstractBlock.Settings.copy(Blocks.OAK_PRESSURE_PLATE)){});
    public static Block DESERT_POPLAR_TRAPDOOR = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID, "desert_poplar_trapdoor"), new TrapdoorBlock(AbstractBlock.Settings.copy(Blocks.OAK_TRAPDOOR)){});
    public static Block DESERT_POPLAR_DOOR = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID, "desert_poplar_door"), new DoorBlock(AbstractBlock.Settings.copy(Blocks.OAK_DOOR)){});


    public static Block JUJUBE_LEAVES = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID,"jujube_leaves"), createLeavesBlock(BlockSoundGroup.GRASS));
    public static JujubeLog JUJUBE_LOG = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID,"jujube_log"), new JujubeLog(AbstractBlock.Settings.of(Material.WOOD).strength(0.5F).sounds(BlockSoundGroup.WOOD).nonOpaque()));
    public static JujubeTreeTop JUJUBE_TOP = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID, "jujube_top"), new JujubeTreeTop(AbstractBlock.Settings.of(Material.PLANT).ticksRandomly().strength(0.4F).sounds(BlockSoundGroup.WOOD).nonOpaque().noCollision()));
    public static Block JUJUBE_PLANKS = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID,"jujube_planks"), new Block(AbstractBlock.Settings.of(Material.WOOD).strength(1f).sounds(BlockSoundGroup.WOOD)));
    public static Block JUJUBE_STAIRS = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID, "jujube_stairs"), new StairsBlock(JUJUBE_PLANKS.getDefaultState(), AbstractBlock.Settings.copy(Blocks.OAK_STAIRS)){});
    public static Block JUJUBE_SLAB = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID, "jujube_slab"), new SlabBlock(AbstractBlock.Settings.copy(Blocks.OAK_SLAB)));

    public static Block SEA_BUCKTHORN = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID, "sea_buckthorn"), new SeaBuckthorn(AbstractBlock.Settings.of(Material.PLANT).noCollision().ticksRandomly().sounds(BlockSoundGroup.CROP).nonOpaque()));
    public static Block PENCIL_PLANT = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID,"pencil_plant"), new PencilPlant(AbstractBlock.Settings.of(Material.WOOD).strength(0.4F).sounds(BlockSoundGroup.WOOD).nonOpaque()));
    public static Block PENCIL_PLANT_TOP = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID, "pencil_plant_top"), new PencilPlantTop((PencilPlant) PENCIL_PLANT, AbstractBlock.Settings.of(Material.PLANT).ticksRandomly().strength(0.4F).sounds(BlockSoundGroup.WOOD).nonOpaque()));
    public static Block PENCIL_PLANT_PLANKS = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID,"pencil_plant_planks"), new PencilPlantPlanks(AbstractBlock.Settings.of(Material.WOOD).strength(1f).sounds(BlockSoundGroup.WOOD)));
    public static Block DETOXIFIED_PENCIL_PLANT_PLANKS = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID,"detoxified_pencil_plant_planks"), new Block(AbstractBlock.Settings.of(Material.WOOD).strength(1f).sounds(BlockSoundGroup.WOOD)));
    public static Block DETOXIFIED_PENCIL_PLANT_STAIRS = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID,"detoxified_pencil_plant_stairs"), new StairsBlock(DETOXIFIED_PENCIL_PLANT_PLANKS.getDefaultState(), AbstractBlock.Settings.copy(Blocks.OAK_STAIRS)){});
    public static Block DETOXIFIED_PENCIL_PLANT_SLAB = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID,"detoxified_pencil_plant_slab"), new SlabBlock(AbstractBlock.Settings.copy(Blocks.OAK_SLAB)));


    public static Block TIPI_FIRE = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID, "tipi_fire"), new TipiFire(true, 1, AbstractBlock.Settings.of(Material.WOOD).strength(1.0f).sounds(BlockSoundGroup.WOOD).luminance(createLightLevelFromLitBlockState(10)).nonOpaque()));
    public static Block PACKING_TABLE = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID, "packing_table"), new PackingTableBlock(AbstractBlock.Settings.of(Material.WOOD).strength(1.0f).sounds(BlockSoundGroup.WOOD)));
    public static final Block PACKED_STICKS = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID, "packed_sticks"), createLogBlock(MapColor.OAK_TAN, MapColor.OAK_TAN));

    public static final Block WOODEN_BARREL = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID,"wooden_barrel"), new WoodenBarrel(AbstractBlock.Settings.of(Material.WOOD).strength(1.0f).sounds(BlockSoundGroup.WOOD)));
    public static final Block WOODEN_BARREL_WATER = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID,"wooden_barrel_water"), new LeveledBarrel(AbstractBlock.Settings.of(Material.WOOD).strength(1.0f).sounds(BlockSoundGroup.WOOD), LeveledCauldronBlock.RAIN_PREDICATE, WATER_BARREL_BEHAVIOR));
    public static final Block WOODEN_BARREL_SEWAGE = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID,"wooden_barrel_sewage"), new LeveledBarrel(AbstractBlock.Settings.of(Material.WOOD).strength(1.0f).sounds(BlockSoundGroup.WOOD), LeveledCauldronBlock.RAIN_PREDICATE, WATER_BARREL_BEHAVIOR));

    public static Block SMALL_GOLD_BUD = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID,"small_gold_bud"), new AmethystClusterBlock(3, 3, AbstractBlock.Settings.of(Material.METAL).strength(1.5f)));
    public static Block MEDIUM_GOLD_BUD = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID,"medium_gold_bud"), new AmethystClusterBlock(4, 3, AbstractBlock.Settings.of(Material.METAL).strength(1.5f)));
    public static Block LARGE_GOLD_BUD = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID,"large_gold_bud"), new AmethystClusterBlock(5, 3, AbstractBlock.Settings.of(Material.METAL).strength(1.5f)));
    public static Block GOLD_CLUSTER = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID,"gold_cluster"), new AmethystClusterBlock(7, 4, AbstractBlock.Settings.of(Material.METAL).strength(1.5f)));
    public static Block GOLD_ORE_DEPOSIT = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID,"gold_ore_deposit"), new BuddingOreBlock(AbstractBlock.Settings.of(Material.METAL).strength(1.5f).ticksRandomly(), SMALL_GOLD_BUD, MEDIUM_GOLD_BUD, LARGE_GOLD_BUD, GOLD_CLUSTER));

    public static Block COPPER_ELSHOLTZIA = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID,"copper_elsholtzia"), new CopperElsholtzia(AbstractBlock.Settings.of(Material.PLANT).strength(0.2f).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque().noCollision()));



    public static void init(){
        initializeCauldron();
        addFlammables();
        addStrippedLogs();
    }

    private static void addFlammables(){
        FlammableBlockRegistry fbr = FlammableBlockRegistry.getDefaultInstance();
        fbr.add(DESERT_POPLAR_LOG, 5, 5);
        fbr.add(DESERT_POPLAR_LEAVES, 5, 5);
        fbr.add(STRIPPED_DESERT_POPLAR_LOG, 5, 5);
        fbr.add(DESERT_POPLAR_PLANKS, 5, 5);
        fbr.add(DESERT_POPLAR_STAIRS, 5, 5);
        fbr.add(DESERT_POPLAR_SLAB, 5, 5);

        fbr.add(JUJUBE_LEAVES, 5, 5);
        fbr.add(JUJUBE_LOG, 5, 5);
        fbr.add(JUJUBE_PLANKS, 5, 5);
        fbr.add(JUJUBE_SLAB, 5, 5);
        fbr.add(JUJUBE_STAIRS, 5, 5);

        fbr.add(PENCIL_PLANT_PLANKS, 10, 10);
        fbr.add(PENCIL_PLANT, 10, 5);
        fbr.add(DETOXIFIED_PENCIL_PLANT_PLANKS, 5, 5);
        fbr.add(DETOXIFIED_PENCIL_PLANT_STAIRS, 5, 5);
        fbr.add(DETOXIFIED_PENCIL_PLANT_SLAB, 5, 5);


        fbr.add(PACKED_STICKS, 5, 5);

    }

    private static void addStrippedLogs(){
        StrippableBlockRegistry.register(DESERT_POPLAR_LOG, STRIPPED_DESERT_POPLAR_LOG);
    }

    private static ToIntFunction<BlockState> createLightLevelFromLitBlockState(int litLevel) {
        return (state) -> {
            return (Boolean)state.get(Properties.LIT) ? litLevel : 0;
        };
    }

    private static LeavesBlock createLeavesBlock(BlockSoundGroup soundGroup) {
        return new LeavesBlock(AbstractBlock.Settings.of(Material.LEAVES).strength(0.2F).ticksRandomly().sounds(soundGroup).nonOpaque().allowsSpawning(BlockInit::canSpawnOnLeaves).suffocates(BlockInit::never).blockVision(BlockInit::never));
    }

    private static PillarBlock createLogBlock(MapColor topMapColor, MapColor sideMapColor) {
        return new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, (state) -> {
            return state.get(PillarBlock.AXIS) == Direction.Axis.Y ? topMapColor : sideMapColor;
        }).strength(2.0F).sounds(BlockSoundGroup.WOOD));
    }

    private static Boolean canSpawnOnLeaves(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) {
        return type == EntityType.OCELOT || type == EntityType.PARROT;
    }

    private static Boolean never(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) {
        return false;
    }
    private static boolean never(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }

    public static void initializeCauldron(){
        // Use static references to the CauldronBehavior.maps
        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(ItemInit.WOODEN_BUCKET, (state, world, pos, player, hand, stack) -> {
            return CauldronBehavior.emptyCauldron(state, world, pos, player, hand, stack, new ItemStack(ItemInit.WATER_WOODEN_BUCKET), (statex) -> {
                return statex.get(LeveledCauldronBlock.LEVEL) == 3;
            }, SoundEvents.ITEM_BUCKET_FILL);
        });
        CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.put(ItemInit.WATER_WOODEN_BUCKET, (state, world, pos, player, hand, stack) -> {
            return fillBarrelWithWoodenBucket(world, pos, player, hand, stack, BlockInit.WOODEN_BARREL_WATER.getDefaultState().with(LeveledCauldronBlock.LEVEL, 3), SoundEvents.ITEM_BUCKET_EMPTY);
        });
        EMPTY_BARREL_BEHAVIOR.put(Items.WATER_BUCKET, BARREL_FILL_WITH_WATER);
        EMPTY_BARREL_BEHAVIOR.put(ItemInit.WATER_WOODEN_BUCKET, (state, world, pos, player, hand, stack) -> {
            return fillBarrelWithWoodenBucket(world, pos, player, hand, stack, BlockInit.WOODEN_BARREL_WATER.getDefaultState().with(LeveledCauldronBlock.LEVEL, 3), SoundEvents.ITEM_BUCKET_EMPTY);
        });
        EMPTY_BARREL_BEHAVIOR.put(Items.POTION, (state, world, pos, player, hand, stack) -> {
            if (PotionUtil.getPotion(stack) != Potions.WATER) {
                return ActionResult.PASS;
            } else {
                if (!world.isClient) {
                    Item item = stack.getItem();
                    player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.GLASS_BOTTLE)));
                    player.incrementStat(Stats.USE_CAULDRON);
                    player.incrementStat(Stats.USED.getOrCreateStat(item));
                    world.setBlockState(pos, BlockInit.WOODEN_BARREL_WATER.getDefaultState());
                    world.playSound((PlayerEntity)null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    world.emitGameEvent((Entity)null, GameEvent.FLUID_PLACE, pos);
                }
                return ActionResult.success(world.isClient);
            }
        });
        WATER_BARREL_BEHAVIOR.put(ItemInit.WOODEN_BUCKET, (state, world, pos, player, hand, stack) -> {
            return emptyBarrel(state, world, pos, player, hand, stack, new ItemStack(ItemInit.WATER_WOODEN_BUCKET), (statex) -> {
                return statex.get(LeveledCauldronBlock.LEVEL) == 3;
            }, SoundEvents.ITEM_BUCKET_FILL);
        });
        WATER_BARREL_BEHAVIOR.put(Items.BUCKET, (state, world, pos, player, hand, stack) -> {
            return emptyBarrel(state, world, pos, player, hand, stack, new ItemStack(Items.WATER_BUCKET), (statex) -> {
                return statex.get(LeveledCauldronBlock.LEVEL) == 3;
            }, SoundEvents.ITEM_BUCKET_FILL);
        });
        WATER_BARREL_BEHAVIOR.put(Items.GLASS_BOTTLE, (state, world, pos, player, hand, stack) -> {
            if (!world.isClient) {
                Item item = stack.getItem();
                player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, PotionUtil.setPotion(new ItemStack(Items.POTION), Potions.WATER)));
                player.incrementStat(Stats.USE_CAULDRON);
                player.incrementStat(Stats.USED.getOrCreateStat(item));
                LeveledBarrel.decrementFluidLevel(state, world, pos);
                world.playSound((PlayerEntity)null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                world.emitGameEvent((Entity)null, GameEvent.FLUID_PICKUP, pos);
            }
            return ActionResult.success(world.isClient);
        });
        WATER_BARREL_BEHAVIOR.put(Items.POTION, (state, world, pos, player, hand, stack) -> {
            if (state.get(LeveledCauldronBlock.LEVEL) != 3 && PotionUtil.getPotion(stack) == Potions.WATER) {
                if (!world.isClient) {
                    player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.GLASS_BOTTLE)));
                    player.incrementStat(Stats.USE_CAULDRON);
                    player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
                    world.setBlockState(pos, state.cycle(LeveledCauldronBlock.LEVEL));
                    world.playSound((PlayerEntity)null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    world.emitGameEvent((Entity)null, GameEvent.FLUID_PLACE, pos);
                }
                return ActionResult.success(world.isClient);
            } else {
                return ActionResult.PASS;
            }
        });
    }

    private static ActionResult emptyBarrel(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack, ItemStack output, Predicate<BlockState> predicate, SoundEvent soundEvent) {
        if (!predicate.test(state)) {
            return ActionResult.PASS;
        } else {
            if (!world.isClient) {
                Item item = stack.getItem();
                player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, output));
                player.incrementStat(Stats.USE_CAULDRON);
                player.incrementStat(Stats.USED.getOrCreateStat(item));
                world.setBlockState(pos, BlockInit.WOODEN_BARREL.getDefaultState());
                world.playSound((PlayerEntity)null, pos, soundEvent, SoundCategory.BLOCKS, 1.0F, 1.0F);
                world.emitGameEvent((Entity)null, GameEvent.FLUID_PICKUP, pos);
            }

            return ActionResult.success(world.isClient);
        }
    }

    private static ActionResult fillBarrelWithWoodenBucket(World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack, BlockState state, SoundEvent soundEvent) {
        if (!world.isClient) {
            Item item = stack.getItem();
            player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(ItemInit.WOODEN_BUCKET)));
            player.incrementStat(Stats.FILL_CAULDRON);
            player.incrementStat(Stats.USED.getOrCreateStat(item));
            world.setBlockState(pos, state);
            world.playSound((PlayerEntity)null, pos, soundEvent, SoundCategory.BLOCKS, 1.0F, 1.0F);
            world.emitGameEvent((Entity)null, GameEvent.FLUID_PLACE, pos);
        }

        return ActionResult.success(world.isClient);
    }
}
