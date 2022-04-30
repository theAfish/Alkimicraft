package net.thenotfish.alkimicraft.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import it.unimi.dsi.fastutil.ints.IntList;
import net.thenotfish.alkimicraft.AlkimiCraft;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class MixingRecipe implements Recipe<Inventory> {
    final DefaultedList<Ingredient> inputs;
    final int level;
    final ItemStack output;
    final Identifier id;

    public MixingRecipe(DefaultedList<Ingredient> input, int level, ItemStack outputStack, Identifier id) {
        this.inputs = input;
        this.level = level;
        this.output = outputStack;
        this.id = id;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        return this.inputs;
    }

    @Override
    public boolean matches(Inventory inventory, World world) {
        RecipeMatcher recipeMatcher = new RecipeMatcher();
        int i = 0;

        for(int j = 0; j < inventory.size(); ++j) {
            ItemStack itemStack = inventory.getStack(j);
            if (!itemStack.isEmpty()) {
                ++i;
                recipeMatcher.addInput(itemStack, 1);
            }
        }

        return i == this.inputs.size() && recipeMatcher.match(this, (IntList)null);
    }

    @Override
    public ItemStack craft(Inventory inventory) {
        return this.output.copy();
    }



    @Override
    public boolean fits(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getOutput() {
        return output;
    }

    public int getLevel(){ return level;}

    @Override
    public Identifier getId() {
        return id;
    }

    public static class Type implements RecipeType<MixingRecipe> {
        // Define ExampleRecipe.Type as a singleton by making its constructor private and exposing an instance.
        private Type() {}
        public static final Type INSTANCE = new Type();

        // This will be needed in step 4
        public static final String ID = "mixing_recipe";
    }
    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    @Override
    public RecipeSerializer<?> getSerializer(){
        return MixingRecipeSerializer.INSTANCE;
    }


    public static class MixingRecipeSerializer implements RecipeSerializer<MixingRecipe>{
        private MixingRecipeSerializer(){}
        public static final MixingRecipeSerializer INSTANCE = new MixingRecipeSerializer();

        public static final Identifier ID = new Identifier(AlkimiCraft.MOD_ID,"mixing_recipe");

        @Override
        // Turns json into Recipe
        public MixingRecipe read(Identifier id, JsonObject json) {

            DefaultedList<Ingredient> inputs = getIngredients(JsonHelper.getArray(json, "ingredients"));
            if (inputs.isEmpty()) {
                throw new JsonParseException("No ingredients for mixing recipe");
            } else if (inputs.size() > 4) {
                throw new JsonParseException("Too many ingredients for mixing recipe");
            } else {
                int level = JsonHelper.getInt(json, "level");
//                String outputName = JsonHelper.getString(json, "result");
//                ItemStack itemStackOut = new ItemStack(Registry.ITEM.getOrEmpty(new Identifier(outputName)).orElseThrow(() -> {
//                    return new IllegalStateException("Item: " + outputName + " does not exist");
//                }));
                ItemStack itemStackOut = ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "result"));
                return new MixingRecipe(inputs, level, itemStackOut, id);

            }
        }

        private static DefaultedList<Ingredient> getIngredients(JsonArray json) {
            DefaultedList<Ingredient> defaultedList = DefaultedList.of();

            for(int i = 0; i < json.size(); ++i) {
                Ingredient ingredient = Ingredient.fromJson(json.get(i));
                if (!ingredient.isEmpty()) {
                    defaultedList.add(ingredient);
                }
            }

            return defaultedList;
        }

        @Override
        // Turns Recipe into PacketByteBuf
        public void write(PacketByteBuf packetByteBuf, MixingRecipe recipe) {
            packetByteBuf.writeVarInt(recipe.inputs.size());

            for (Ingredient ingredient : recipe.inputs) {
                ingredient.write(packetByteBuf);
            }
            packetByteBuf.writeInt(recipe.level);

            packetByteBuf.writeItemStack(recipe.output);
        }

        @Override
        // Turns PacketByteBuf into Recipe
        public MixingRecipe read(Identifier id, PacketByteBuf packetData) {
            int i = packetData.readVarInt();
            DefaultedList<Ingredient> defaultedList = DefaultedList.ofSize(i, Ingredient.EMPTY);
            for(int j = 0; j < defaultedList.size(); ++j) {
                defaultedList.set(j, Ingredient.fromPacket(packetData));
            }
            int level = packetData.readInt();
            ItemStack out = packetData.readItemStack();

            return new MixingRecipe(defaultedList, level, out, id);
        }
    }
}
