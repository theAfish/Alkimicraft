package net.thenotfish.alkimicraft.screen;

import net.thenotfish.alkimicraft.init.ScreenInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.Slot;
import net.minecraft.world.World;

public class MicroscopeScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    protected final World world;

    public MicroscopeScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(5));
    }

    public MicroscopeScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(ScreenInit.MICROSCOPE_SCREEN_HANDLER, syncId);
//        checkSize(inventory, 5);
        this.inventory = inventory;
        this.world = playerInventory.player.world;
        //some inventories do custom logic when a player opens it.
//        this.propertyDelegate = propertyDelegate;
        inventory.onOpen(playerInventory.player);

        //This will place the slot in the correct locations for a 3x3 Grid. The slots exist on both server and client!
        //This will not render the background of the slots however, this is the Screens job
        int m;
        int l;
        //Our inventory
//        for (m = 0; m < 2; ++m) {
//            for (l = 0; l < 2; ++l) {
//                this.addSlot(new Slot(inventory, l + m * 2, 44 + l * 18, 25 + m * 18));
//            }
//        }
//        this.addSlot(new FurnaceOutputSlot(playerInventory.player, inventory, 4, 120, 34));

        //The player inventory
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
            }
        }
        //The player Hotbar
        for (m = 0; m < 9; ++m) {
            this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 142));
        }

    }


    @Override
    public boolean canUse(PlayerEntity playerEntity) {
        return this.inventory.canPlayerUse(playerEntity);
    }
}
