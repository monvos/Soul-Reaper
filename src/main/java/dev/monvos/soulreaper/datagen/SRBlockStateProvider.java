package dev.monvos.soulreaper.datagen;

import dev.monvos.soulreaper.SoulReaper;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class SRBlockStateProvider extends BlockStateProvider {

  public SRBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
    super(output, SoulReaper.MODID, exFileHelper);
  }

  @Override
  protected void registerStatesAndModels() {
    simpleBlockWithItemWithRenderType(SoulReaper.SOUL_GLASS.get(), "cutout");
  }

  public void simpleBlockWithItemWithRenderType(Block block, String renderType) {
    simpleBlockWithItem(block,
        models().cubeAll(ForgeRegistries.BLOCKS.getKey(block)
            .getPath(), blockTexture(block))
            .renderType(renderType));
  }

}
