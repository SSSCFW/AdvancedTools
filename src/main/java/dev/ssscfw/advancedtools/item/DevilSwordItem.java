package dev.ssscfw.advancedtools.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.Level;

public final class DevilSwordItem extends SpecialSwordItem {
    public DevilSwordItem() {
        super(Tiers.DIAMOND, 427, 1, false);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, level, entity, slot, selected);
        if (level.isClientSide || !(entity instanceof Player player) || player.getMainHandItem() != stack || player.hasEffect(MobEffects.STRENGTH)) {
            return;
        }
        if (player.getHealth() > 1.0F) {
            player.setHealth(Math.max(1.0F, player.getHealth() - 1.0F));
            player.addEffect(new MobEffectInstance(MobEffects.STRENGTH, 59, 1));
        } else {
            player.addEffect(new MobEffectInstance(MobEffects.STRENGTH, 19, 1));
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide && player.getHealth() > 1.0F) {
            player.setHealth(1.0F);
        }
        return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        attacker.heal(2.0F);
        if (target instanceof Player) {
            float missing = target.getMaxHealth() - target.getHealth();
            if (missing >= 19.0F) {
                AbilityUtil.extraDamage(target, attacker, 10.0F);
            } else if (missing >= 10.0F) {
                AbilityUtil.extraDamage(target, attacker, 1.0F);
            }
        }
        return true;
    }
}
