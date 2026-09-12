package dev.ssscfw.advancedtools.item;

import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.Level;

public final class HolySaberItem extends SpecialSwordItem {
    public HolySaberItem() {
        super(Tiers.GOLD, 280, 5, false);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, level, entity, slot, selected);
        if (!level.isClientSide && entity instanceof Player player && player.getMainHandItem() == stack && player.getHealth() < player.getMaxHealth()) {
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 40, 0));
        }
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        float bonus = target instanceof EnderMan ? 10.0F : target.getType().is(EntityTypeTags.UNDEAD) ? 7.0F : 0.0F;
        AbilityUtil.extraDamage(target, attacker, bonus);
        return true;
    }
}
