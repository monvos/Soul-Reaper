package dev.monvos.soulreaper.datagen;

import dev.monvos.soulreaper.SoulReaper;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class SRBlockStateProvider extends BlockStateProvider {

  public SRBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
    super(output, SoulReaper.MODID, exFileHelper);
  }

  @Override
  protected void registerStatesAndModels() {
    this.simpleBlockWithItem(SoulReaper.SOUL_GLASS.get(), cubeAll(SoulReaper.SOUL_GLASS.get()));
  }

}
