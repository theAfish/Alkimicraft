package net.fabricmc.alkimicraft.init;

import net.fabricmc.alkimicraft.AlkimiCraft;
import net.fabricmc.alkimicraft.blocks.PackingTableBlock;
import net.fabricmc.alkimicraft.items.*;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemInit {

//    public static final TestItem TEST_ITEM = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID, "test_item"), new TestItem(new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
//    public static final ItemSeedTest TEST_SEEDS = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID, "test_seeds"), new ItemSeedTest(BlockInit.TEST_PLANT,new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));

    // Buckets
    public static Item TOXIC_SEWAGE_BUCKET = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID, "toxic_sewage_bucket"), new BucketItem(FluidInit.TOXIC_SEWAGE_STILL, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1).group(AlkimiCraft.ITEM_GROUP)));
//    public static Item SALTY_WATER_BUCKET = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID, "salty_water_bucket"), new BucketItem(FluidInit.SALTY_WATER_STILL, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1).group(AlkimiCraft.ITEM_GROUP)));
//    public static Item FRESH_WATER_BUCKET = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID, "fresh_water_bucket"), new BucketItem(FluidInit.FRESH_WATER_STILL, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1).group(AlkimiCraft.ITEM_GROUP)));

    public static final Item WOODEN_BUCKET = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID, "wooden_bucket"), new WoodenBucket(Fluids.EMPTY, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1).group(AlkimiCraft.ITEM_GROUP)));
    public static final Item WATER_WOODEN_BUCKET = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID, "water_wooden_bucket"), new WoodenBucket(Fluids.WATER, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1).group(AlkimiCraft.ITEM_GROUP)));
    public static final Item SEWAGE_WOODEN_BUCKET = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID, "sewage_wooden_bucket"), new WoodenBucket(FluidInit.TOXIC_SEWAGE_STILL, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1).group(AlkimiCraft.ITEM_GROUP)));


    public static final Item LOAMY_SAND = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID, "loamy_sand"), new BlockItem(BlockInit.LOAMY_SAND, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
    public static final Item SANDY_LOAM = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID, "sandy_loam"), new BlockItem(BlockInit.SANDY_LOAM, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
    public static final Item BARREN_LOAM = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID, "barren_loam"), new BlockItem(BlockInit.BARREN_LOAM, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
    public static final Item SEDIMENTARY_ROCK = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID, "sedimentary_rock"), new BlockItem(BlockInit.SEDIMENTARY_ROCK, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
    public static final Item ASH_BLOCK = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID, "ash_block"), new BlockItem(BlockInit.ASH_BLOCK, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
    public static final Item CHARRED_LOG = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID,"charred_log"), new BlockItem(BlockInit.CHARRED_LOG, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));

    public static final Item SPARSE_GRASS_BLOCK = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID, "sparse_grass_block"), new BlockItem(BlockInit.SPARSE_GRASS_BLOCK, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));


    public static final Item ITEM_POROUS_STONE = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID, "porous_stone_item"), new BlockItem(BlockInit.POROUS_STONE, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
    public static final Item ITEM_IRON_COBBLESTONE = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID, "iron_cobblestone_item"), new BlockItem(BlockInit.IRON_COBBLESTONE, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));

    public static final Item HERB = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID, "herb"), new Item(new Item.Settings().group(AlkimiCraft.ITEM_GROUP).food(FoodComponents.DRIED_KELP)));

    public static final Item REED = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID, "reed_sticks"), new Item(new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
//    public static final Item DESERT_POPLAR_SAPLING = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID, "desert_poplar_sapling"), new BlockItem(BlockInit.DESERT_POPLAR_SAPLING, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
    public static final Item DESERT_POPLAR_LOG = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID,"desert_poplar_log"), new BlockItem(BlockInit.DESERT_POPLAR_LOG, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
    public static final Item STRIPPED_DESERT_POPLAR_LOG = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID,"stripped_desert_poplar_log"), new BlockItem(BlockInit.STRIPPED_DESERT_POPLAR_LOG, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
    public static final Item DESERT_POPLAR_LEAVES = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID,"desert_poplar_leaves"), new BlockItem(BlockInit.DESERT_POPLAR_LEAVES, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
    public static final Item DESERT_POPLAR_PLANKS = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID,"desert_poplar_planks"), new BlockItem(BlockInit.DESERT_POPLAR_PLANKS, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
    public static final Item DESERT_POPLAR_STAIRS = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID,"desert_poplar_stairs"), new BlockItem(BlockInit.DESERT_POPLAR_STAIRS, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
    public static final Item DESERT_POPLAR_SLAB = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID,"desert_poplar_slab"), new BlockItem(BlockInit.DESERT_POPLAR_SLAB, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
    public static final Item DESERT_POPLAR_FENCE = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID,"desert_poplar_fence"), new BlockItem(BlockInit.DESERT_POPLAR_FENCE, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
    public static final Item DESERT_POPLAR_FENCE_GATE = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID,"desert_poplar_fence_gate"), new BlockItem(BlockInit.DESERT_POPLAR_FENCE_GATE, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
    public static final Item DESERT_POPLAR_BUTTON = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID,"desert_poplar_button"), new BlockItem(BlockInit.DESERT_POPLAR_BUTTON, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
    public static final Item DESERT_POPLAR_PRESSURE_PLATE = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID,"desert_poplar_pressure_plate"), new BlockItem(BlockInit.DESERT_POPLAR_PRESSURE_PLATE, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
    public static final Item DESERT_POPLAR_TRAPDOOR = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID,"desert_poplar_trapdoor"), new BlockItem(BlockInit.DESERT_POPLAR_TRAPDOOR, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
    public static final Item DESERT_POPLAR_DOOR = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID,"desert_poplar_door"), new BlockItem(BlockInit.DESERT_POPLAR_DOOR, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));

    public static final Item STAR_LAUREL_LOG = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID,"star_laurel/star_laurel_log"), new BlockItem(BlockInit.STAR_LAUREL_LOG, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
    public static final Item STRIPPED_STAR_LAUREL_LOG = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID,"star_laurel/stripped_star_laurel_log"), new BlockItem(BlockInit.STRIPPED_STAR_LAUREL_LOG, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
    public static final Item STAR_LAUREL_LEAVES = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID,"star_laurel/star_laurel_leaves"), new BlockItem(BlockInit.STAR_LAUREL_LEAVES, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
    public static final Item FLOWERING_STAR_LAUREL_LEAVES = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID,"star_laurel/flowering_star_laurel_leaves"), new BlockItem(BlockInit.FLOWERING_STAR_LAUREL_LEAVES, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
    public static final Item STAR_LAUREL_PLANKS = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID,"star_laurel/star_laurel_planks"), new BlockItem(BlockInit.STAR_LAUREL_PLANKS  , new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
    public static final Item STAR_LAUREL_STAIRS = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID,"star_laurel/star_laurel_stairs"), new BlockItem(BlockInit.STAR_LAUREL_STAIRS, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
    public static final Item STAR_LAUREL_SLAB = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID,"star_laurel/star_laurel_slab"), new BlockItem(BlockInit.STAR_LAUREL_SLAB, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
    public static final Item STAR_LAUREL_FENCE = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID,"star_laurel/star_laurel_fence"), new BlockItem(BlockInit.STAR_LAUREL_FENCE, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
    public static final Item STAR_LAUREL_FENCE_GATE = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID,"star_laurel/star_laurel_fence_gate"), new BlockItem(BlockInit.STAR_LAUREL_FENCE_GATE, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
    public static final Item STAR_LAUREL_BUTTON = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID,"star_laurel/star_laurel_button"), new BlockItem(BlockInit.STAR_LAUREL_BUTTON, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
    public static final Item STAR_LAUREL_PRESSURE_PLATE = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID,"star_laurel/star_laurel_pressure_plate"), new BlockItem(BlockInit.STAR_LAUREL_PRESSURE_PLATE, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
    public static final Item STAR_LAUREL_TRAPDOOR = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID,"star_laurel/star_laurel_trapdoor"), new BlockItem(BlockInit.STAR_LAUREL_TRAPDOOR, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
    public static final Item STAR_LAUREL_DOOR = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID,"star_laurel/star_laurel_door"), new BlockItem(BlockInit.STAR_LAUREL_DOOR, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
    public static final Item STAR_LAUREL_SAPLING = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID, "star_laurel/star_laurel_sapling"), new BlockItem(BlockInit.STAR_LAUREL_SAPLING, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));


//    public static final Item JUJUBE_TOP = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID, "jujube_top"), new BlockItem(BlockInit.JUJUBE_TOP, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));

    public static final Item JUJUBE_LEAVES = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID, "jujube_leaves"), new BlockItem(BlockInit.JUJUBE_LEAVES, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
    public static final Item JUJUBE_LOG = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID, "jujube_log"), new Item(new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
    public static final Item JUJUBE_PLANKS = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID,"jujube_planks"), new BlockItem(BlockInit.JUJUBE_PLANKS, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
    public static final Item JUJUBE_STAIRS = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID,"jujube_stairs"), new BlockItem(BlockInit.JUJUBE_STAIRS, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
    public static final Item JUJUBE_SLAB = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID,"jujube_slab"), new BlockItem(BlockInit.JUJUBE_SLAB, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));


    public static final Item PENCIL_PLANT = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID, "pencil_plant"), new BlockItem(BlockInit.PENCIL_PLANT,new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
//    public static final Item PENCIL_PLANT_TOP = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID, "pencil_plant_top"), new BlockItem(BlockInit.PENCIL_PLANT_TOP, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
    public static final Item PENCIL_PLANT_LOG = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID, "pencil_plant_log"), new Item(new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
    public static final Item PENCIL_PLANT_PLANKS = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID,"pencil_plant_planks"), new BlockItem(BlockInit.PENCIL_PLANT_PLANKS, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
    public static final Item DETOXIFIED_PENCIL_PLANT_PLANKS = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID,"detoxified_pencil_plant_planks"), new BlockItem(BlockInit.DETOXIFIED_PENCIL_PLANT_PLANKS, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
    public static final Item DETOXIFIED_PENCIL_PLANT_STAIRS = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID,"detoxified_pencil_plant_stairs"), new BlockItem(BlockInit.DETOXIFIED_PENCIL_PLANT_STAIRS, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
    public static final Item DETOXIFIED_PENCIL_PLANT_SLAB = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID,"detoxified_pencil_plant_slab"), new BlockItem(BlockInit.DETOXIFIED_PENCIL_PLANT_SLAB, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));

    public static final Item SNOW_LANTERN_PLANT = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID, "snow_lantern_plant"), new BlockItem(BlockInit.SNOW_LANTERN_PLANT,new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));


    public static final Item FIRE_PLOUGH = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID, "fire_plough"), new FirePlough(new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
    public static final Item TIPI_FIRE = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID, "tipi_fire"), new BlockItem(BlockInit.TIPI_FIRE, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
    public static final Item PACKING_TABLE = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID, "packing_table"), new BlockItem(BlockInit.PACKING_TABLE, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
    public static final Item PACKED_STICKS = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID,"packed_sticks"), new BlockItem(BlockInit.PACKED_STICKS, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));

    public static final Item PLANT_ASH = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID, "plant_ash"), new Item(new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));

    public static final Item WOODEN_BARREL = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID,"wooden_barrel"), new BlockItem(BlockInit.WOODEN_BARREL, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));

    public static final Item THE_DRY_TELEPORTER = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID,"the_dry_teleporter"), new TheDryTeleporter(new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
    public static final Item SMALL_GOLD_BUD = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID,"small_gold_bud"), new BlockItem(BlockInit.SMALL_GOLD_BUD, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
    public static final Item MEDIUM_GOLD_BUD = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID,"medium_gold_bud"), new BlockItem(BlockInit.MEDIUM_GOLD_BUD, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
    public static final Item LARGE_GOLD_BUD = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID,"large_gold_bud"), new BlockItem(BlockInit.LARGE_GOLD_BUD, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
    public static final Item GOLD_CLUSTER = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID,"gold_cluster"), new BlockItem(BlockInit.GOLD_CLUSTER, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
    public static final Item GOLD_ORE_DEPOSIT = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID,"gold_ore_deposit"), new BlockItem(BlockInit.GOLD_ORE_DEPOSIT, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));

    public static final Item COPPER_SHELL = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID, "copper_shell"), new Item(new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
    public static final Item COPPER_NUGGET = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID, "copper_nugget"), new Item(new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
    public static final Item COPPOISE_IN_BAG = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID, "coppoise_in_bag"), new CreatureInBag(EntityInit.COPPOISE, new Item.Settings().group(AlkimiCraft.ITEM_GROUP).maxCount(1)));

    public static final Item MICROSCOPE = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID,"microscope"), new BlockItem(BlockInit.MICROSCOPE, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));

    // Spawn eggs
    public static final Item COPPOISE_SPAWN_EGG = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID, "coppoise_spawn_egg"), new SpawnEggItem(EntityInit.COPPOISE, 15172180, 5882659, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));

    // Seeds
    public static final Item IRON_ROOT_GRASS_SEED = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID, "iron_root_grass_seeds"), new AliasedBlockItem(BlockInit.IRON_ROOT_GRASS,new Item.Settings().group(AlkimiCraft.SEEDS_GROUP)){
        @Override public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {tooltip.add( new TranslatableText("iron_root_grass_seed.tooltips").formatted(Formatting.GREEN));}});
    public static final Item ARTEMISIA_ARENARIA_SEEDS = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID, "artemisia_arenaria_seeds"), new AliasedBlockItem(BlockInit.ARTEMISIA_ARENARIA,new Item.Settings().group(AlkimiCraft.SEEDS_GROUP)){
        @Override public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {tooltip.add( new TranslatableText("artemisia_arenaria_seeds.tooltips").formatted(Formatting.GREEN));}});
    public static final Item PENCIL_PLANT_SEEDS = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID, "pencil_plant_seeds"), new AliasedBlockItem(BlockInit.PENCIL_PLANT_TOP, new Item.Settings().group(AlkimiCraft.SEEDS_GROUP)){
        @Override public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {tooltip.add( new TranslatableText("pencil_plant_seeds.tooltips").formatted(Formatting.GREEN));}});
    public static final Item SEA_BUCKTHORN_FRUITS = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID, "sea_buckthorn_fruits"), new AliasedBlockItem(BlockInit.SEA_BUCKTHORN,new Item.Settings().group(AlkimiCraft.SEEDS_GROUP).food(FoodComponents.SWEET_BERRIES)){
        @Override public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {tooltip.add( new TranslatableText("sea_buckthorn_fruits.tooltips").formatted(Formatting.GREEN));}});
    public static final Item DESERT_POPLAR_SEEDS = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID, "desert_poplar_seeds"), new AliasedBlockItem(BlockInit.DESERT_POPLAR_SAPLING, new Item.Settings().group(AlkimiCraft.SEEDS_GROUP)){
        @Override public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {tooltip.add( new TranslatableText("desert_poplar_seeds.tooltips").formatted(Formatting.GREEN));}});
    public static final Item REED_SEEDS = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID, "reed_seeds"), new AliasedBlockItem(BlockInit.BLOCK_REED,new Item.Settings().group(AlkimiCraft.SEEDS_GROUP)){
        @Override public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {tooltip.add( new TranslatableText("reed_seeds.tooltips").formatted(Formatting.GREEN));}});
    public static final Item FERTILE_BEANS_SEEDS = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID, "fertile_beans_seeds"), new AliasedBlockItem(BlockInit.FERTILE_BEANS,new Item.Settings().group(AlkimiCraft.SEEDS_GROUP)){
        @Override public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {tooltip.add( new TranslatableText("fertile_beans_seeds.tooltips").formatted(Formatting.GREEN));}});
    public static final Item GRASS_SEEDS = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID, "grass_seeds"), new GrassSeeds(new Item.Settings().group(AlkimiCraft.SEEDS_GROUP)));
    public static final Item JUJUBE = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID, "jujube"), new AliasedBlockItem(BlockInit.JUJUBE_TOP, new Item.Settings().group(AlkimiCraft.SEEDS_GROUP).food(FoodComponents.APPLE)){
        @Override public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {tooltip.add( new TranslatableText("jujube.tooltips").formatted(Formatting.GREEN));}});
    public static final Item COPPER_ELSHOLTZIA_SEEDS = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID, "copper_elsholtzia_seeds"), new AliasedBlockItem(BlockInit.COPPER_ELSHOLTZIA, new Item.Settings().group(AlkimiCraft.SEEDS_GROUP)){
        @Override public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {tooltip.add( new TranslatableText("copper_elsholtzia_seeds.tooltips").formatted(Formatting.GREEN));}});
    public static final Item STAR_LAUREL_SEEDS = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID, "star_laurel/star_laurel_seeds"), new AliasedBlockItem(BlockInit.STAR_LAUREL_SEEDLING, new Item.Settings().group(AlkimiCraft.SEEDS_GROUP)){
        @Override public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {tooltip.add( new TranslatableText("star_laurel_seeds.tooltips").formatted(Formatting.GREEN));}});


    public static void init(){
        addFuel();
    }

    private static void addFuel(){
        FuelRegistry.INSTANCE.add(DESERT_POPLAR_LOG, 300);
        FuelRegistry.INSTANCE.add(STRIPPED_DESERT_POPLAR_LOG, 300);
        FuelRegistry.INSTANCE.add(DESERT_POPLAR_PLANKS, 300);
        FuelRegistry.INSTANCE.add(DESERT_POPLAR_SLAB, 300);
        FuelRegistry.INSTANCE.add(DESERT_POPLAR_STAIRS, 300);

        FuelRegistry.INSTANCE.add(JUJUBE_LOG, 200);
        FuelRegistry.INSTANCE.add(JUJUBE_PLANKS, 200);
        FuelRegistry.INSTANCE.add(JUJUBE_STAIRS, 200);
        FuelRegistry.INSTANCE.add(JUJUBE_SLAB, 200);

        FuelRegistry.INSTANCE.add(PENCIL_PLANT_LOG, 900);
        FuelRegistry.INSTANCE.add(PENCIL_PLANT_PLANKS, 900);
        FuelRegistry.INSTANCE.add(DETOXIFIED_PENCIL_PLANT_PLANKS, 300);
        FuelRegistry.INSTANCE.add(DETOXIFIED_PENCIL_PLANT_STAIRS, 300);
        FuelRegistry.INSTANCE.add(DETOXIFIED_PENCIL_PLANT_SLAB, 300);

        FuelRegistry.INSTANCE.add(PACKED_STICKS, 300);
        FuelRegistry.INSTANCE.add(PACKING_TABLE, 400);
        FuelRegistry.INSTANCE.add(FIRE_PLOUGH, 150);
    }

}
