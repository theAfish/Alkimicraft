package net.thenotfish.alkimicraft.blocks.entities;

import net.thenotfish.alkimicraft.blocks.AbstractWoodenBarrel;
import net.thenotfish.alkimicraft.blocks.LeveledBarrel;
import net.thenotfish.alkimicraft.init.BlockEntityInit;
import net.thenotfish.alkimicraft.init.RecipeInit;
import net.thenotfish.alkimicraft.recipes.MixingRecipe;
import net.thenotfish.alkimicraft.screen.WoodenBarrelScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class WoodenBarrelEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(5, ItemStack.EMPTY);
    private final RecipeType<? extends MixingRecipe> recipeType = RecipeInit.MIXING_RECIPE;
    int mixingTime = 0;
    int maxTime = 40;
    protected final PropertyDelegate propertyDelegate;

    public WoodenBarrelEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.WOODEN_BARREL_ENTITY, pos, state);

        this.propertyDelegate = new PropertyDelegate() {
            public int get(int index) {
                return switch (index) {
                    case 0 -> WoodenBarrelEntity.this.mixingTime;
                    case 1 -> WoodenBarrelEntity.this.maxTime;
                    default -> 0;
                };
            }

            public void set(int index, int value) {
                switch (index) {
                    case 0 -> WoodenBarrelEntity.this.mixingTime = value;
                    case 1 -> WoodenBarrelEntity.this.maxTime = value;
                }

            }

            public int size() {
                return 2;
            }
        };
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    public boolean hasItem(Item item){
        return inventory.stream().anyMatch(itemIn -> itemIn.isOf(item));
    }

    public void setItem(ItemStack itemStack){
        inventory.add(itemStack);
    }

    private boolean canSetOutput(ItemStack itemStack){
        if (itemStack.isEmpty()){
            return false;
        }else {
            ItemStack itemInSlot = inventory.get(4);
            if (itemInSlot.isEmpty()){ return true; }
            else if (!itemInSlot.isItemEqualIgnoreDamage(itemStack)){
                return false;
            }else return itemInSlot.getCount() + itemStack.getCount() < itemInSlot.getMaxCount();
        }
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText(getCachedState().getBlock().getTranslationKey());
    }

    private static int getLevel(World world, RecipeType<? extends MixingRecipe> recipeType, Inventory inventory) {
        return world.getRecipeManager().getFirstMatch(recipeType, inventory, world).map(MixingRecipe::getLevel).orElse(0);
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        //We provide *this* to the screenHandler as our class Implements Inventory
        //Only the Server has the Inventory at the start, this will be synced to the client in the ScreenHandler
        return new WoodenBarrelScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }


    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, this.inventory);
        this.mixingTime = nbt.getShort("MixingTime");
//        this.maxTime = nbt.getShort("MaxTime");
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, this.inventory);
        nbt.putShort("MixingTime", (short)this.mixingTime);
//        nbt.putShort("MaxTime", (short)this.maxTime);
//        return nbt;
    }

    public static void tick(World world, BlockPos blockPos, BlockState blockState, WoodenBarrelEntity blockEntity) {
        SimpleInventory inventory = new SimpleInventory(blockEntity.inventory.get(0), blockEntity.inventory.get(1),blockEntity.inventory.get(2),blockEntity.inventory.get(3));
        int requiredLevel = getLevel(world, MixingRecipe.Type.INSTANCE, inventory);
        int currentLevel = AbstractWoodenBarrel.getLevel(blockState);
        ItemStack outputSlot = blockEntity.inventory.get(4);
        Optional<MixingRecipe> match = world.getRecipeManager()
                .getFirstMatch(MixingRecipe.Type.INSTANCE, inventory, world);
        if (match.isPresent() && currentLevel >= requiredLevel){
            if (blockEntity.mixingTime == blockEntity.maxTime){
                ItemStack itemStack2Out = match.get().craft(inventory);
                if (outputSlot.isEmpty()){
                    blockEntity.inventory.set(4, itemStack2Out);
                } else if (outputSlot.isOf(itemStack2Out.getItem())){
                    outputSlot.increment(itemStack2Out.getCount());
                }
                for (int i=0; i < 4; ++i){
                    blockEntity.inventory.get(i).decrement(1);
                }
                LeveledBarrel.decrementFluidLevel(blockState, world, blockPos, requiredLevel);
                blockEntity.mixingTime = 0;
            } else { blockEntity.mixingTime += 1;}
        }

        markDirty(world, blockPos, blockState);
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }
}
