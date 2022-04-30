package net.thenotfish.alkimicraft.mixin;

import net.thenotfish.alkimicraft.blocks.IFluidLoggable;
import net.thenotfish.alkimicraft.blocks.LoggableFluidsEnum;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.thenotfish.alkimicraft.blocks.BlockProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public abstract class BlockMixin extends AbstractBlock {

    public BlockMixin(AbstractBlock.Settings properties)
    {
        super(properties);
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    public void init(AbstractBlock.Settings properties, CallbackInfo ci)
    {
        if (this instanceof IFluidLoggable)
        {
            this.setDefaultState(this.getDefaultState().with(BlockProperties.fluidLogged, LoggableFluidsEnum.EMPTY));
        }
    }

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;appendProperties(Lnet/minecraft/state/StateManager$Builder;)V"))
    public void appendProperties(Block block, StateManager.Builder<Block, BlockState> builder)
    {
        appendProperties(builder);
        if (this instanceof IFluidLoggable)
        {
            builder.add(BlockProperties.fluidLogged);
        }
    }

    @Shadow
    public abstract BlockState getDefaultState();

    @Shadow
    protected abstract void appendProperties(StateManager.Builder<Block, BlockState> builder);

    @Shadow
    protected abstract void setDefaultState(BlockState state);
}
