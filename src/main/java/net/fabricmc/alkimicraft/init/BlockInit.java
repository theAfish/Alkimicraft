package net.fabricmc.alkimicraft.init;

import net.fabricmc.alkimicraft.AlkimiCraft;
import net.fabricmc.alkimicraft.blocks.*;
import net.fabricmc.alkimicraft.structures.trees.DesertPoplarSaplingGenerator;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
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
    public static Block SALTY_WATER = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID, "salty_water"), new FluidBlock(FluidInit.SALTY_WATER_STILL, FabricBlockSettings.of(Material.WATER).dropsNothing()){});
    public static Block FRESH_WATER = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID, "fresh_water"), new FluidBlock(FluidInit.FRESH_WATER_STILL, FabricBlockSettings.of(Material.WATER).dropsNothing()){});

    public static final BlockReed BLOCK_REED = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID, "reed_block"), new BlockReed(AbstractBlock.Settings.of(Material.UNDERWATER_PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS).nonOpaque()));
    public static Block LOAMY_SAND = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID, "loamy_sand"), new Block(AbstractBlock.Settings.of(Material.SOIL).strength(0.5F).sounds(BlockSoundGroup.GRAVEL)));
    public static Block SANDY_LOAM = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID, "sandy_loam"), new Block(AbstractBlock.Settings.of(Material.SOIL).strength(0.6F).sounds(BlockSoundGroup.GRAVEL)));
    public static Block BARREN_LOAM = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID, "barren_loam"), new Block(AbstractBlock.Settings.of(Material.SOIL).strength(0.7F).sounds(BlockSoundGroup.GRAVEL)));
    public static Block SEDIMENTARY_ROCK = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID, "sedimentary_rock"), new Block(AbstractBlock.Settings.of(Material.STONE).strength(1.2F).sounds(BlockSoundGroup.STONE).requiresTool()));
    public static Block ASH_BLOCK = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID, "ash_block"), new Block(AbstractBlock.Settings.of(Material.SOIL).strength(0.5F).sounds(BlockSoundGroup.SAND)));
    public static Block CHARRED_LOG = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID,"charred_log"), createLogBlock(MapColor.BLACK, MapColor.SPRUCE_BROWN));


    public static Block POROUS_STONE = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID, "porous_stone"), new BlockPorousStone(AbstractBlock.Settings.of(Material.STONE).strength(1.0F).sounds(BlockSoundGroup.STONE)));
    public static Block IRON_COBBLESTONE = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID, "iron_cobblestone"), new BlockIronCobblestone(AbstractBlock.Settings.of(Material.STONE).strength(1.0F).sounds(BlockSoundGroup.STONE)));


    public static Block ARTEMISIA_ARENARIA = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID, "artemisia_arenaria"), new BlockArtemisiaArenaria(AbstractBlock.Settings.of(Material.PLANT).noCollision().ticksRandomly().sounds(BlockSoundGroup.CROP).nonOpaque()));
    public static Block IRON_ROOT_GRASS = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID, "iron_root_grass"), new BlockIronRootGrass(AbstractBlock.Settings.of(Material.PLANT).noCollision().ticksRandomly().sounds(BlockSoundGroup.CROP).nonOpaque()));
    public static Block FERTILE_BEANS = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID, "fertile_beans"), new FertileBeans(AbstractBlock.Settings.of(Material.PLANT).noCollision().ticksRandomly().sounds(BlockSoundGroup.CROP).nonOpaque()));


    public static Block DESERT_POPLAR_LEAVES = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID,"desert_poplar_leaves"), createLeavesBlock(BlockSoundGroup.GRASS));
    public static Block DESERT_POPLAR_LOG = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID,"desert_poplar_log"), createLogBlock(MapColor.OAK_TAN, MapColor.SPRUCE_BROWN));
    public static Block DESERT_POPLAR_SAPLING = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID, "desert_poplar_sapling"), new DesertPoplarSaplingBlock(new DesertPoplarSaplingGenerator(), AbstractBlock.Settings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS)));
    public static Block DESERT_POPLAR_PLANKS = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID,"desert_poplar_planks"), new Block(AbstractBlock.Settings.of(Material.WOOD).strength(1f).sounds(BlockSoundGroup.WOOD)));

    public static Block SEA_BUCKTHORN = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID, "sea_buckthorn"), new SeaBuckthorn(AbstractBlock.Settings.of(Material.PLANT).noCollision().ticksRandomly().sounds(BlockSoundGroup.CROP).nonOpaque()));
    public static Block PENCIL_PLANT = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID,"pencil_plant"), new PencilPlant(AbstractBlock.Settings.of(Material.PLANT).strength(0.4F).sounds(BlockSoundGroup.WOOD).nonOpaque()));
    public static Block PENCIL_PLANT_TOP = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID, "pencil_plant_top"), new PencilPlantTop((PencilPlant) PENCIL_PLANT, AbstractBlock.Settings.of(Material.PLANT).ticksRandomly().strength(0.4F).sounds(BlockSoundGroup.WOOD).nonOpaque()));
    public static Block PENCIL_PLANT_PLANKS = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID,"pencil_plant_planks"), new PencilPlantPlanks(AbstractBlock.Settings.of(Material.WOOD).strength(1f).sounds(BlockSoundGroup.WOOD)));

    public static Block TIPI_FIRE = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID, "tipi_fire"), new TipiFire(true, 1, AbstractBlock.Settings.of(Material.WOOD).strength(1.0f).sounds(BlockSoundGroup.WOOD).luminance(createLightLevelFromLitBlockState(10)).nonOpaque()));
    public static Block PACKING_TABLE = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID, "packing_table"), new PackingTableBlock(AbstractBlock.Settings.of(Material.WOOD).strength(1.0f).sounds(BlockSoundGroup.WOOD)));
    public static final Block PACKED_STICKS = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID, "packed_sticks"), createLogBlock(MapColor.OAK_TAN, MapColor.OAK_TAN));

    public static final Block WOODEN_BARREL = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID,"wooden_barrel"), new WoodenBarrel(AbstractBlock.Settings.of(Material.WOOD).strength(1.0f).sounds(BlockSoundGroup.WOOD)));
    public static final Block WOODEN_BARREL_WATER = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID,"wooden_barrel_water"), new LeveledBarrel(AbstractBlock.Settings.of(Material.WOOD).strength(1.0f).sounds(BlockSoundGroup.WOOD), LeveledCauldronBlock.RAIN_PREDICATE, WATER_BARREL_BEHAVIOR));
    public static final Block WOODEN_BARREL_SEWAGE = Registry.register(Registry.BLOCK, new Identifier(AlkimiCraft.MOD_ID,"wooden_barrel_sewage"), new LeveledBarrel(AbstractBlock.Settings.of(Material.WOOD).strength(1.0f).sounds(BlockSoundGroup.WOOD), LeveledCauldronBlock.RAIN_PREDICATE, WATER_BARREL_BEHAVIOR));




    public static void init(){
        initializeCauldron();
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
        CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.put(ItemInit.WATER_WOODEN_BUCKET, FILL_WITH_WATER);
        EMPTY_BARREL_BEHAVIOR.put(Items.WATER_BUCKET, BARREL_FILL_WITH_WATER);
        EMPTY_BARREL_BEHAVIOR.put(ItemInit.WATER_WOODEN_BUCKET, BARREL_FILL_WITH_WATER);
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
}
