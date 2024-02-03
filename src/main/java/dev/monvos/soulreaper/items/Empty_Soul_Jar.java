package dev.monvos.soulreaper.items;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import dev.monvos.soulreaper.SoulReaper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class Empty_Soul_Jar extends Item {
  private static final Logger LOGGER = LogUtils.getLogger();

  public Empty_Soul_Jar(Properties p_41383_) {
    super(p_41383_);
  }

  public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity entity,
      InteractionHand hand) {
    entity.remove(Entity.RemovalReason.KILLED);
    ItemStack newStack = new ItemStack(SoulReaper.FILLED_SOUL_JAR.get());
    player.setItemInHand(hand, newStack);
    LOGGER.info("TEST");
    return InteractionResult.SUCCESS;
  }
}
