package dev.monvos.soulreaper.datagen;

import dev.monvos.soulreaper.SoulReaper;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = SoulReaper.MODID)
public class DataGenerators {

  private DataGenerators() {

  }

  @SubscribeEvent
  public static void gatherData(GatherDataEvent event) {
    DataGenerator generator = event.getGenerator();
    PackOutput packOutput = generator.getPackOutput();
    ExistingFileHelper fileHelper = event.getExistingFileHelper();

    generator.addProvider(event.includeClient(), new SRItemModelProvider(packOutput, fileHelper));
  }
}
