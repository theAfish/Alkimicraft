package net.fabricmc.alkimicraft.blocks.entities;

import net.fabricmc.alkimicraft.blocks.TipiFire;
import net.fabricmc.alkimicraft.init.BlockEntityInit;
import net.fabricmc.alkimicraft.init.ItemInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.CampfireCookingRecipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Clearable;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Random;

public class TipiFireEntity extends BlockEntity implements Clearable {
    private final DefaultedList<ItemStack> itemsBeingCooked;
    private final DefaultedList<ItemStack> itemsBeingBurned;
    private final int[] cookingTimes;
    private final int[] cookingTotalTimes;

    public TipiFireEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.TIPI_FIRE_ENTITY, pos, state);
        this.itemsBeingCooked = DefaultedList.ofSize(8, ItemStack.EMPTY);
        this.itemsBeingBurned = DefaultedList.ofSize(4, ItemStack.EMPTY);
        this.cookingTimes = new int[8];
        this.cookingTotalTimes = new int[8];
    }

    public static void litServerTick(World world, BlockPos pos, BlockState state, TipiFireEntity tipiFire) {
        boolean bl = false;
        for(int i = 0; i < tipiFire.itemsBeingCooked.size(); ++i) {
            ItemStack itemStack = tipiFire.itemsBeingCooked.get(i);
            if (!itemStack.isEmpty()) {
                bl = true;
                int var10002 = tipiFire.cookingTimes[i]++;
                if (tipiFire.cookingTimes[i] >= tipiFire.cookingTotalTimes[i] && i < 4) {
                    tipiFire.cookingTimes[i]++;
                    Inventory inventory = new SimpleInventory(itemStack);
                    ItemStack itemStack2 = world.getRecipeManager().getFirstMatch(RecipeType.CAMPFIRE_COOKING, inventory, world).map((campfireCookingRecipe) -> campfireCookingRecipe.craft(inventory)).orElse(itemStack);
                    ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), itemStack2);
                    tipiFire.itemsBeingCooked.set(i, ItemStack.EMPTY);
                    world.updateListeners(pos, state, state, Block.NOTIFY_ALL);
                }else if (tipiFire.cookingTimes[i] >= tipiFire.cookingTotalTimes[i]){
                    tipiFire.cookingTimes[i] = tipiFire.cookingTotalTimes[i];
                    tipiFire.itemsBeingCooked.set(i, new ItemStack(ItemInit.PLANT_ASH));
                    world.updateListeners(pos, state, state, Block.NOTIFY_ALL);
                }
            }
        }
        if (bl) {
            markDirty(world, pos, state);
        }
    }

    public static void unlitServerTick(World world, BlockPos pos, BlockState state, TipiFireEntity tipiFire) {
        boolean bl = false;
        for(int i = 0; i < tipiFire.itemsBeingCooked.size(); ++i) {
            if (tipiFire.cookingTimes[i] > 0) {
                bl = true;
                tipiFire.cookingTimes[i] = MathHelper.clamp((tipiFire.cookingTimes[i] - 2), 0, tipiFire.cookingTotalTimes[i]);
            }
        }
        if (bl) {
            markDirty(world, pos, state);
        }

    }

    public static void clientTick(World world, BlockPos pos, BlockState state, TipiFireEntity tipi_fire) {
        Random random = world.random;
        int j;
        if (random.nextFloat() < 0.11F) {
            for(j = 0; j < random.nextInt(5) + 2; ++j) {
                TipiFire.spawnSmokeParticle(world, pos, false);
            }
        }

//        j = (state.get(TipiFire.FACING)).getHorizontal();
//
//        for(int k = 0; k < tipi_fire.itemsBeingCooked.size(); ++k) {
//            if (!(tipi_fire.itemsBeingCooked.get(k)).isEmpty() && random.nextFloat() < 0.2F) {
//                Direction direction = Direction.fromHorizontal(Math.floorMod(k + j, 4));
//                float f = 0.3125F;
//                double d = (double)pos.getX() + 0.5D - (double)((float)direction.getOffsetX() * 0.2F) + (double)((float)direction.rotateYClockwise().getOffsetX() * 0.2F);
//                double e = (double)pos.getY() + 0.2D;
//                double g = (double)pos.getZ() + 0.5D - (double)((float)direction.getOffsetZ() * 0.2F) + (double)((float)direction.rotateYClockwise().getOffsetZ() * 0.2F);
//
//                for(int l = 0; l < 4; ++l) {
//                    world.addParticle(ParticleTypes.SMOKE, d, e, g, 0.0D, 5.0E-4D, 0.0D);
//                }
//            }
//        }
    }

    public DefaultedList<ItemStack> getItemsBeingCooked() {
        return this.itemsBeingCooked;
    }



    public void readNbt(NbtCompound nbt) {
        this.itemsBeingCooked.clear();
        Inventories.readNbt(nbt, this.itemsBeingCooked);
        super.readNbt(nbt);
        int[] js;
        if (nbt.contains("CookingTimes", 11)) {
            js = nbt.getIntArray("CookingTimes");
            System.arraycopy(js, 0, this.cookingTimes, 0, Math.min(this.cookingTotalTimes.length, js.length));
        }

        if (nbt.contains("CookingTotalTimes", 11)) {
            js = nbt.getIntArray("CookingTotalTimes");
            System.arraycopy(js, 0, this.cookingTotalTimes, 0, Math.min(this.cookingTotalTimes.length, js.length));
        }

    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        this.saveInitialChunkData(nbt);
        nbt.putIntArray("CookingTimes", this.cookingTimes);
        nbt.putIntArray("CookingTotalTimes", this.cookingTotalTimes);
        Inventories.writeNbt(nbt, this.itemsBeingCooked);
//        return super.writeNbt(nbt);
    }

    private NbtCompound saveInitialChunkData(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, this.itemsBeingCooked, true);
        return nbt;
    }

    public Optional<CampfireCookingRecipe> getRecipeFor(ItemStack item) {
        return this.itemsBeingCooked.stream().noneMatch(ItemStack::isEmpty) ? Optional.empty() : this.world.getRecipeManager().getFirstMatch(RecipeType.CAMPFIRE_COOKING, new SimpleInventory(new ItemStack[]{item}), this.world);
    }

    public boolean addItem(ItemStack item, int integer) {
        for(int i = 0; i < this.itemsBeingCooked.size()/2; ++i) {
            ItemStack itemStack = this.itemsBeingCooked.get(i);
            if (itemStack.isEmpty()) {
                this.cookingTotalTimes[i] = integer;
                this.cookingTimes[i] = 0;
                this.itemsBeingCooked.set(i, item.split(1));
                this.updateListeners();
                return true;
            }
        }

        return false;
    }

    public boolean addBurningItem(ItemStack item, int burntime){
        for (int i = 4; i < this.itemsBeingCooked.size(); ++i){
            ItemStack itemStack = this.itemsBeingCooked.get(i);
            if (itemStack.isEmpty()){
                this.cookingTotalTimes[i] = burntime;
                this.cookingTimes[i] = 0;
                this.itemsBeingCooked.set(i, item.split(1));
                this.updateListeners();
                return true;
            }
        }
        return false;
    }

    public void getAsh(World world, BlockPos pos){
        for (int i = 4; i < this.itemsBeingCooked.size(); ++i) {
            ItemStack itemStack = itemsBeingCooked.get(i);
            if (itemStack.isOf(ItemInit.PLANT_ASH)) {
                ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), itemStack);
                itemsBeingCooked.set(i, ItemStack.EMPTY);
                this.updateListeners();
            }
        }
    }

    private void updateListeners() {
        this.markDirty();
        this.getWorld().updateListeners(getPos(), getCachedState(), getCachedState(), Block.NOTIFY_ALL);
    }

    @Override
    public void markDirty() {
        super.markDirty();

        if (this.hasWorld() && !this.getWorld().isClient()) {
            ((ServerWorld) world).getChunkManager().markForUpdate(getPos());
        }
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

    @Override
    public void clear() {
        this.itemsBeingCooked.clear();
        this.updateListeners();
    }

    public void spawnItemsBeingCooked() {
        if (this.world != null) {
            this.updateListeners();
        }

    }


}
