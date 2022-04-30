package net.thenotfish.alkimicraft.init;

import net.thenotfish.alkimicraft.AlkimiCraft;
import net.thenotfish.alkimicraft.recipes.MixingRecipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RecipeInit {
    public static RecipeType<MixingRecipe> MIXING_RECIPE = Registry.register(Registry.RECIPE_TYPE, new Identifier(AlkimiCraft.MOD_ID, MixingRecipe.Type.ID), MixingRecipe.Type.INSTANCE);


    public static void init(){
        Registry.register(Registry.RECIPE_SERIALIZER, MixingRecipe.MixingRecipeSerializer.ID,
                MixingRecipe.MixingRecipeSerializer.INSTANCE);
    }
}
