package net.thenotfish.alkimicraft.mixin;

//import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.client.network.ClientPlayNetworkHandler;
        import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
    @Unique
    private static Logger FABRIC_LOGGER = LogManager.getLogger();

//    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/network/packet/s2c/play/BlockEntityUpdateS2CPacket;getBlockEntityType()I", ordinal = 0), method = "onBlockEntityUpdate", cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
//    public void onBlockEntityUpdate(BlockEntityUpdateS2CPacket packet, CallbackInfo info, BlockPos blockPos, BlockEntity entity) {
//        if (entity instanceof BlockEntityClientSerializable) {
//            if (packet.getBlockEntityType() == 127) {
//                BlockEntityClientSerializable serializable = (BlockEntityClientSerializable) entity;
//                String id = packet.getNbt().getString("id");
//
//                if (id != null) {
//                    Identifier otherIdObj = BlockEntityType.getId(entity.getType());
//
//                    if (otherIdObj == null) {
//                        FABRIC_LOGGER.error(entity.getClass() + " is missing a mapping! This is a bug!");
//                        info.cancel();
//                        return;
//                    }
//
//                    String otherId = otherIdObj.toString();
//
//                    if (otherId.equals(id)) {
//                        serializable.fromClientTag(packet.getNbt());
//                    }
//                }
//            }
//
//            info.cancel();
//        }
//    }

//    @Redirect(method = "onChunkData", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/BlockEntity;readNbt(Lnet/minecraft/nbt/NbtCompound;)V"))
//    public void deserializeBlockEntityChunkData(BlockEntity blockEntity, NbtCompound tag) {
//        if (blockEntity instanceof BlockEntityClientSerializable) {
//            ((BlockEntityClientSerializable) blockEntity).fromClientTag(tag);
//        } else {
//            blockEntity.readNbt(tag);
//        }
//    }
}
