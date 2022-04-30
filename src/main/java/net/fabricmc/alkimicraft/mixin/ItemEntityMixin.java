package net.fabricmc.alkimicraft.mixin;

import net.fabricmc.alkimicraft.init.EntityInit;
import net.fabricmc.alkimicraft.init.ItemInit;
import net.fabricmc.alkimicraft.items.CreatureInBag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {

    @Shadow public abstract ItemStack getStack();

    public ItemEntityMixin(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;floor(D)I"))
    private void spawnCoppoise(CallbackInfo ci){
        if (!this.world.isClient && this.getStack().isOf(ItemInit.COPPOISE_IN_BAG) && this.onGround){
            CreatureInBag.spawnEntity((ServerWorld) world, EntityInit.COPPOISE, this.getStack(), getBlockPos());
        }
    }
}
