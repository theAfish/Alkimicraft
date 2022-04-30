package net.thenotfish.alkimicraft.entity;

import net.minecraft.entity.Bucketable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.Optional;

public interface Pickable {
    void copyDataToStack(ItemStack itemStack);

    void copyDataFromNbt(NbtCompound nbtCompound);

    ItemStack getBagItem();

    void setFromBucket(boolean bl);


    static void copyDataToStack(MobEntity mobEntity, ItemStack itemStack) {
        NbtCompound nbtCompound = itemStack.getOrCreateNbt();
        if (mobEntity.hasCustomName()) {
            itemStack.setCustomName(mobEntity.getCustomName());
        }

        if (mobEntity.isAiDisabled()) {
            nbtCompound.putBoolean("NoAI", mobEntity.isAiDisabled());
        }

        if (mobEntity.isSilent()) {
            nbtCompound.putBoolean("Silent", mobEntity.isSilent());
        }

        if (mobEntity.hasNoGravity()) {
            nbtCompound.putBoolean("NoGravity", mobEntity.hasNoGravity());
        }

        if (mobEntity.isGlowingLocal()) {
            nbtCompound.putBoolean("Glowing", mobEntity.isGlowingLocal());
        }

        if (mobEntity.isInvulnerable()) {
            nbtCompound.putBoolean("Invulnerable", mobEntity.isInvulnerable());
        }

        nbtCompound.putFloat("Health", mobEntity.getHealth());
    }

    static void copyDataFromNbt(MobEntity mobEntity, NbtCompound nbtCompound) {
        if (nbtCompound.contains("NoAI")) {
            mobEntity.setAiDisabled(nbtCompound.getBoolean("NoAI"));
        }

        if (nbtCompound.contains("Silent")) {
            mobEntity.setSilent(nbtCompound.getBoolean("Silent"));
        }

        if (nbtCompound.contains("NoGravity")) {
            mobEntity.setNoGravity(nbtCompound.getBoolean("NoGravity"));
        }

        if (nbtCompound.contains("Glowing")) {
            mobEntity.setGlowing(nbtCompound.getBoolean("Glowing"));
        }

        if (nbtCompound.contains("Invulnerable")) {
            mobEntity.setInvulnerable(nbtCompound.getBoolean("Invulnerable"));
        }

        if (nbtCompound.contains("Health", 99)) {
            mobEntity.setHealth(nbtCompound.getFloat("Health"));
        }

    }

    static <T extends LivingEntity & Bucketable> Optional<ActionResult> tryPick(PlayerEntity playerEntity, Hand hand, LivingEntity livingEntity) {
        ItemStack itemStack = playerEntity.getStackInHand(hand);
        if (itemStack.isEmpty()) {
            ItemStack itemStack2 = ((Pickable)livingEntity).getBagItem();
            ((Pickable)livingEntity).copyDataToStack(itemStack2);
            ItemStack itemStack3 = ItemUsage.exchangeStack(itemStack, playerEntity, itemStack2, false);
            playerEntity.setStackInHand(hand, itemStack3);
            World world = livingEntity.world;


            livingEntity.discard();
            return Optional.of(ActionResult.success(world.isClient));
        } else {
            return Optional.empty();
        }
    }
}
