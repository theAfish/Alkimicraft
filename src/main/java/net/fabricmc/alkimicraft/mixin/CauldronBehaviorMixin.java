package net.fabricmc.alkimicraft.mixin;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.fabricmc.alkimicraft.init.ItemInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.function.Predicate;

@Mixin(CauldronBehavior.class)
public interface CauldronBehaviorMixin {
    @Final
    @Shadow
    Map<Item, CauldronBehavior> WATER_CAULDRON_BEHAVIOR = createMap();
    @Final
    @Shadow
    Map<Item, CauldronBehavior> LAVA_CAULDRON_BEHAVIOR = createMap();
    @Final
    @Shadow
    CauldronBehavior FILL_WITH_WATER = null;

//    @Inject(method = "registerBehavior", at = @At("TAIL"))
//    private static void addBehavior(CallbackInfo ci){
//        WATER_CAULDRON_BEHAVIOR.put(ItemInit.WOODEN_BUCKET, (state, world, pos, player, hand, stack) -> {
//            return emptyCauldron(state, world, pos, player, hand, stack, new ItemStack(ItemInit.WATER_WOODEN_BUCKET), (statex) -> {
//                return (Integer)statex.get(LeveledCauldronBlock.LEVEL) == 3;
//            }, SoundEvents.ITEM_BUCKET_FILL);
//        });
//        LAVA_CAULDRON_BEHAVIOR.put(Items.BUCKET, (state, world, pos, player, hand, stack) -> {
//            return emptyCauldron(state, world, pos, player, hand, stack, new ItemStack(Items.LAVA_BUCKET), (statex) -> {
//                return true;
//            }, SoundEvents.ITEM_BUCKET_FILL_LAVA);
//        });
//    }

    @Inject(method = "registerBucketBehavior(Ljava/util/Map;)V", at = @At("TAIL"))
    private static void registerNewBehavior(Map<Item, CauldronBehavior> behavior, CallbackInfo ci){
        behavior.put(ItemInit.WATER_WOODEN_BUCKET, FILL_WITH_WATER);
    }

    @Shadow
    static Object2ObjectOpenHashMap<Item, CauldronBehavior> createMap() {
        return null;
    }

    @Shadow
    static ActionResult emptyCauldron(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack, ItemStack output, Predicate<BlockState> predicate, SoundEvent soundEvent) {
        return null;
    }
}
