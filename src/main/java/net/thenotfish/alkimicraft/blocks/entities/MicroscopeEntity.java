package net.thenotfish.alkimicraft.blocks.entities;

import net.minecraft.util.math.Vec2f;
import net.thenotfish.alkimicraft.init.BlockEntityInit;
import net.thenotfish.alkimicraft.screen.MicroscopeScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

enum mikros{
    test_mikros
}

public class MicroscopeEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory{
    private final DefaultedList<Vec2f> micro_pos;
    private final DefaultedList<mikros> mikro_type;

    public MicroscopeEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityInit.MICROSCOPE_ENTITY, blockPos, blockState);
        this.micro_pos = DefaultedList.ofSize(1, new Vec2f(1,1));
        this.mikro_type = DefaultedList.ofSize(1, mikros.test_mikros);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return null;
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText(getCachedState().getBlock().getTranslationKey());
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new MicroscopeScreenHandler(i, playerInventory, this);
    }
}
