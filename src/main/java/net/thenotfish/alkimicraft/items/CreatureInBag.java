package net.thenotfish.alkimicraft.items;

import net.thenotfish.alkimicraft.entity.Pickable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

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
