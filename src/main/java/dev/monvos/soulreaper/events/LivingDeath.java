package dev.monvos.soulreaper.events;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import dev.monvos.soulreaper.items.TheReaper_Sword;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class LivingDeath {
  private static final Logger LOGGER = LogUtils.getLogger();

  @SubscribeEvent
  public static void on(LivingDeathEvent event) {
    if (event.getEntity().getKillCredit() instanceof Player) {
      Player player = (Player) event.getEntity().getKillCredit();
      LOGGER.info(player.toString());
      ItemStack mainHandItem = player.getMainHandItem();
      if (!mainHandItem.isEmpty() && mainHandItem.getItem() instanceof TheReaper_Sword) {
        LOGGER.info("KILL!");
        ((TheReaper_Sword) mainHandItem.getItem()).onMobKill(mainHandItem);
      }
    }

  }

}
