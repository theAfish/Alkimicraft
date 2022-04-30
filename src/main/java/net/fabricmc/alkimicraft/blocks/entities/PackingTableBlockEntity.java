package net.fabricmc.alkimicraft.blocks.entities;

import net.fabricmc.alkimicraft.init.BlockEntityInit;
import net.fabricmc.alkimicraft.init.ItemInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.Clearable;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class PackingTableBlockEntity extends BlockEntity implements Clearable{
    private final DefaultedList<ItemStack> itemsWaitingPacked;
    private Boolean canPack = false;
    private Boolean packFinished = false;

    public PackingTableBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.PACKING_TABLE_BLOCK_ENTITY, pos, state);
        this.itemsWaitingPacked = DefaultedList.ofSize(1, ItemStack.EMPTY);
    }

    public DefaultedList<ItemStack> getItemsWaitingPacked() {
        return itemsWaitingPacked;
    }

    public Boolean getPackFinished(){
        return packFinished;
    }

    @Override
    public void writeNbt(NbtCompound tag) {
        this.saveInitialChunkData(tag);
        tag.putBoolean("packFinished", packFinished);
//        return tag;
    }

    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
        this.itemsWaitingPacked.clear();
        Inventories.readNbt(tag, this.itemsWaitingPacked);
        packFinished = tag.getBoolean("packFinished");
    }

    private NbtCompound saveInitialChunkData(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, this.itemsWaitingPacked, true);
        return nbt;
    }


    public boolean addItem(ItemStack item) {
        for(int i = 0; i < this.itemsWaitingPacked.size(); ++i) {
            ItemStack itemStack = this.itemsWaitingPacked.get(i);
            if (itemStack.isEmpty()) {
                if (item.isOf(Items.STICK) && item.getCount() == 64){
                    canPack = true;
                }
                this.itemsWaitingPacked.set(i, item.split(item.getCount()));
                packFinished = false;
                updateListeners();
                return true;
            }else if (canPack){
                this.itemsWaitingPacked.set(i, ItemInit.PACKED_STICKS.getDefaultStack());
                canPack = false;
                packFinished = true;
                updateListeners();
            }
            else {
                ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), itemStack);
                itemsWaitingPacked.set(i, ItemStack.EMPTY);
                packFinished = false;
                updateListeners();
            }
        }
        return false;
    }

    private void updateListeners() {
        this.markDirty();
        this.getWorld().updateListeners(getPos(), getCachedState(), getCachedState(), Block.NOTIFY_ALL);
    }

    @Override
    public void clear() {
        this.itemsWaitingPacked.clear();
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
