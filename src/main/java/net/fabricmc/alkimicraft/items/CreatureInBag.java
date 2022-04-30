package net.fabricmc.alkimicraft.items;

import net.fabricmc.alkimicraft.entity.Pickable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.entity.Bucketable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ClickType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.MobSpawnerLogic;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.checkerframework.checker.units.qual.A;

import java.util.Objects;

public class CreatureInBag extends Item {
    private final EntityType<?> entityType;

    public CreatureInBag(EntityType<?> entityType, Settings settings) {
        super(settings);
        this.entityType = entityType;
    }

    public ActionResult useOnBlock(ItemUsageContext itemUsageContext) {
        World world = itemUsageContext.getWorld();
        ItemStack itemStack = itemUsageContext.getStack();
        BlockPos blockPos = itemUsageContext.getBlockPos();
        PlayerEntity playerEntity = itemUsageContext.getPlayer();
        if (world instanceof ServerWorld) {
            spawnEntity((ServerWorld)world, this.entityType, itemStack, blockPos);
            world.emitGameEvent(playerEntity, GameEvent.ENTITY_PLACE, blockPos);
            return ActionResult.CONSUME;
        }
        else {
            return ActionResult.SUCCESS;
        }
    }

    public static void spawnEntity(ServerWorld serverWorld, EntityType<?> entityType, ItemStack itemStack, BlockPos blockPos) {
        Entity entity = entityType.spawnFromItemStack(serverWorld, itemStack, null, blockPos, SpawnReason.BUCKET, true, false);
        if (entity instanceof Pickable pickable) {
            pickable.copyDataFromNbt(itemStack.getOrCreateNbt());
            pickable.setFromBucket(true);
            itemStack.decrement(1);
        }

    }
}
