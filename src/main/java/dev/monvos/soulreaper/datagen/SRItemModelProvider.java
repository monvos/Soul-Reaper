package dev.monvos.soulreaper.datagen;

import dev.monvos.soulreaper.SoulReaper;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class SRItemModelProvider extends ItemModelProvider {

  public SRItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
    super(output, SoulReaper.MODID, existingFileHelper);
  }

  @Override
  protected void registerModels() {
    basicItem(SoulReaper.EMPTY_SOUL_JAR.getId());
    basicItem(SoulReaper.FILLED_SOUL_JAR.getId());
    handHeldItem(SoulReaper.THEREAPER_SWORD.getId());
  }

  public ItemModelBuilder handHeldItem(ResourceLocation item) {
    return getBuilder(item.toString())
        .parent(new ModelFile.UncheckedModelFile("item/handheld"))
        .texture("layer0", new ResourceLocation(item.getNamespace(), "item/" + item.getPath()));
  }

}
