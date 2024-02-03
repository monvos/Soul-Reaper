package dev.monvos.soulreaper.items;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.server.command.TextComponentHelper;

import java.util.List;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

public class TheReaper_Sword extends SwordItem {

  private static final Logger LOGGER = LogUtils.getLogger();
  // private int killCount;
  private static final String KILL_COUNT_TAG = "kills";

  public TheReaper_Sword(Tier p_43269_, int p_43270_, float p_43271_, Properties p_43272_) {
    super(p_43269_, p_43270_, p_43271_, p_43272_);
  }

  @Override
  public void onCraftedBy(ItemStack p_41447_, Level p_41448_, Player p_41449_) {
    CompoundTag tag = p_41447_.getOrCreateTag();
    tag.putInt(KILL_COUNT_TAG, 0);
    p_41447_.setTag(tag);
  }

  public void onMobKill(ItemStack stack) {
    CompoundTag tag = stack.getOrCreateTag();
    int killCount = tag.getInt(KILL_COUNT_TAG);
    killCount = killCount + 1;
    tag.putInt(KILL_COUNT_TAG, killCount);
    stack.setTag(tag);
    LOGGER.info(String.valueOf(tag.getInt(KILL_COUNT_TAG)));
  }

  @Override
  public boolean hurtEnemy(ItemStack stack, LivingEntity entity, LivingEntity player) {
    CompoundTag tag = stack.getOrCreateTag();
    int killCount = tag.getInt(KILL_COUNT_TAG);
    float newDamage = getDamage() + killCount;
    entity.hurt(player.damageSources().generic(), newDamage);
    // player.addEffect(new MobEffectInstance(MobEffects.HEAL, 10));
    return true;
  }

  @Override
  public boolean canBeDepleted() {
    return false;
  }

  @Override
  public void appendHoverText(ItemStack p_41421_, Level p_41422_, List<Component> p_41423_, TooltipFlag p_41424_) {
    CompoundTag tag = p_41421_.getOrCreateTag();
    p_41423_
        .add(TextComponentHelper
            .createComponentTranslation(null, "Souls: " + String.valueOf(tag.getInt(KILL_COUNT_TAG)), new Object())
            .withStyle(ChatFormatting.AQUA));
    p_41423_
        .add(TextComponentHelper
            .createComponentTranslation(null, "+" + String.valueOf(tag.getInt(KILL_COUNT_TAG)) + " Bonus Damage",
                new Object())
            .withStyle(ChatFormatting.GOLD));

    super.appendHoverText(p_41421_, p_41422_, p_41423_, p_41424_);
  }
}