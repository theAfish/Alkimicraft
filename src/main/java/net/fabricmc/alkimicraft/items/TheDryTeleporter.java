package net.fabricmc.alkimicraft.items;

import net.fabricmc.alkimicraft.AlkimiCraft;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potions;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TheDryTeleporter extends Item {
    public TheDryTeleporter(Settings settings) {
        super(settings);
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (world instanceof ServerWorld) {
            int x = (int)player.getPos().x;
            int z = (int)player.getPos().z;
            if (world.getRegistryKey() == World.OVERWORLD) {
                CommandManager commandManager = player.getServer().getCommandManager();
                ServerCommandSource serverCommandSource = player.getServer().getCommandSource();
                commandManager.execute(serverCommandSource, String.format("execute as @p in alkimicraft:the_dry run teleport %s %s %s", x, 150, z));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING,500));
                itemStack.decrement(1);
                return TypedActionResult.success(itemStack, world.isClient());
            }
//            else if (world.getRegistryKey() == AlkimiCraft.WORLD_KEY){
//                CommandManager commandManager = player.getServer().getCommandManager();
//                ServerCommandSource serverCommandSource = player.getServer().getCommandSource();
//                commandManager.execute(serverCommandSource, String.format("execute as @p in minecraft:overworld run teleport %s %s %s", x,120, z));
//                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING,500));
//                return TypedActionResult.success(itemStack, world.isClient());
//            }
        }
        return TypedActionResult.fail(itemStack);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(new TranslatableText("the_dry_teleporter.tooltip1").formatted(Formatting.RED));
        tooltip.add(new TranslatableText("the_dry_teleporter.tooltip2"));
        tooltip.add(new TranslatableText("the_dry_teleporter.tooltip21"));
        tooltip.add(new TranslatableText("the_dry_teleporter.tooltip22"));
        tooltip.add(new TranslatableText("the_dry_teleporter.tooltip23"));
        tooltip.add(new TranslatableText("the_dry_teleporter.tooltip24"));
        tooltip.add(new TranslatableText("the_dry_teleporter.tooltip3").formatted(Formatting.DARK_RED));
        tooltip.add(new TranslatableText("the_dry_teleporter.tooltip31").formatted(Formatting.DARK_RED));
        tooltip.add(new TranslatableText("the_dry_teleporter.tooltip32").formatted(Formatting.DARK_RED));
        tooltip.add(new TranslatableText("the_dry_teleporter.tooltip33").formatted(Formatting.DARK_RED));
    }
}
