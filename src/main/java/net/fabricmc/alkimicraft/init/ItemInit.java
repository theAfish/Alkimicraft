package net.fabricmc.alkimicraft.init;

import net.fabricmc.alkimicraft.AlkimiCraft;
import net.fabricmc.alkimicraft.items.*;
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
    public static final Item DESERT_POPLAR_LEAVES = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID,"desert_poplar_leaves"), new BlockItem(BlockInit.DESERT_POPLAR_LEAVES, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
    public static final Item DESERT_POPLAR_PLANKS = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID,"desert_poplar_planks"), new BlockItem(BlockInit.DESERT_POPLAR_PLANKS, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));

//    public static final Item JUJUBE_TOP = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID, "jujube_top"), new BlockItem(BlockInit.JUJUBE_TOP, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));

    public static final Item JUJUBE_LEAVES = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID, "jujube_leaves"), new BlockItem(BlockInit.JUJUBE_LEAVES, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
    public static final Item JUJUBE_LOG = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID, "jujube_log"), new Item(new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));


    public static final Item PENCIL_PLANT = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID, "pencil_plant"), new BlockItem(BlockInit.PENCIL_PLANT,new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
//    public static final Item PENCIL_PLANT_TOP = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID, "pencil_plant_top"), new BlockItem(BlockInit.PENCIL_PLANT_TOP, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
    public static final Item PENCIL_PLANT_LOG = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID, "pencil_plant_log"), new Item(new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
    public static final Item PENCIL_PLANT_PLANKS = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID,"pencil_plant_planks"), new BlockItem(BlockInit.PENCIL_PLANT_PLANKS, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
    public static final Item DETOXIFIED_PENCIL_PLANT_PLANKS = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID,"detoxified_pencil_plant_planks"), new BlockItem(BlockInit.DETOXIFIED_PENCIL_PLANT_PLANKS, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));


    public static final Item FIRE_PLOUGH = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID, "fire_plough"), new FirePlough(new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
    public static final Item TIPI_FIRE = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID, "tipi_fire"), new BlockItem(BlockInit.TIPI_FIRE, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
    public static final Item PACKING_TABLE = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID, "packing_table"), new BlockItem(BlockInit.PACKING_TABLE, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));
    public static final Item PACKED_STICKS = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID,"packed_sticks"), new BlockItem(BlockInit.PACKED_STICKS, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));

    public static final Item PLANT_ASH = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID, "plant_ash"), new Item(new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));

    public static final Item WOODEN_BARREL = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID,"wooden_barrel"), new BlockItem(BlockInit.WOODEN_BARREL, new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));

    public static final Item THE_DRY_TELEPORTER = Registry.register(Registry.ITEM, new Identifier(AlkimiCraft.MOD_ID,"the_dry_teleporter"), new TheDryTeleporter(new Item.Settings().group(AlkimiCraft.ITEM_GROUP)));


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

    public static void init(){

    }

}
