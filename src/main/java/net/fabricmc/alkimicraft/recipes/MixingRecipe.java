package net.fabricmc.alkimicraft.recipes;

import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class MixingRecipe implements Recipe<SimpleInventory> {
    final DefaultedList<Ingredient> input;
    final Fluid fluid;
    final int level;
    final ItemStack output;
    final Identifier id;

    public MixingRecipe(DefaultedList<Ingredient> input, Fluid fluid, int level, ItemStack outputStack, Identifier id) {
        this.input = input;
        this.fluid = fluid;
        this.level = level;
        this.output = outputStack;
        this.id = id;
    }

    @Override
    public boolean matches(SimpleInventory inventory, World world) {
        RecipeMatcher recipeMatcher = new RecipeMatcher();

        return false;
    }

    @Override
    public ItemStack craft(SimpleInventory inventory) {
        return null;
    }

    @Override
    public boolean fits(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getOutput() {
        return null;
    }

    @Override
    public Identifier getId() {
        return null;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return null;
    }

    @Override
    public RecipeType<?> getType() {
        return null;
    }
}
