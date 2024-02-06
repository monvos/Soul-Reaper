package dev.monvos.soulreaper.datagen;

import java.util.function.Consumer;

import dev.monvos.soulreaper.SoulReaper;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class SRModRecipeProvider extends RecipeProvider {

  public SRModRecipeProvider(PackOutput p_248933_) {
    super(p_248933_);
  }

  @Override
  protected void buildRecipes(Consumer<FinishedRecipe> p_251297_) {
    ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, SoulReaper.THEREAPER_SWORD.get())
        .define('#', Items.STICK)
        .define('X', SoulReaper.FILLED_SOUL_JAR.get())
        .define('S', Items.DIAMOND_SWORD)
        .pattern(" S ")
        .pattern("XSX")
        .pattern(" # ")
        .unlockedBy("has_filled_soul_jar", has(SoulReaper.FILLED_SOUL_JAR.get()))
        .save(p_251297_);

    ShapedRecipeBuilder.shaped(RecipeCategory.MISC, SoulReaper.EMPTY_SOUL_JAR.get())
        .define('#', ItemTags.PLANKS)
        .define('X', SoulReaper.SOUL_GLASS.get())
        .pattern(" # ")
        .pattern("X X")
        .pattern(" X ")
        .unlockedBy("has_soul_glass", has(SoulReaper.SOUL_GLASS.get()))
        .save(p_251297_);

    SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.SOUL_SAND), RecipeCategory.MISC,
        SoulReaper.SOUL_GLASS.get(), 0.2F, 400)
        .unlockedBy("has_soul_sand", has(Items.SOUL_SAND))
        .save(p_251297_);

  }

}
