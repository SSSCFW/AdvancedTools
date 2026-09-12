package dev.ssscfw.advancedtools.item;

import java.util.List;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public abstract class ChargedSpecialSwordItem extends SpecialSwordItem {
    private static final int USE_DURATION = 72000;
    private final String abilityDescription;
    private final int hungerCost;

    protected ChargedSpecialSwordItem(Tier tier, int durability, int attackDamage, String abilityDescription, int hungerCost) {
        super(tier, durability, attackDamage, false);
        this.abilityDescription = abilityDescription;
        this.hungerCost = hungerCost;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!player.hasInfiniteMaterials() && player.getFoodData().getFoodLevel() <= 6) {
            return InteractionResultHolder.fail(stack);
        }
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return USE_DURATION;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        if (!(entity instanceof Player player)) {
            return;
        }
        int usedTicks = Math.max(0, USE_DURATION - timeLeft);
        if (usedTicks <= 0) {
            return;
        }
        float power = chargePower(usedTicks);
        if (!level.isClientSide) {
            performAbility(level, player, stack, power, usedTicks >= 20);
            InteractionHand hand = player.getMainHandItem() == stack ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
            stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
            if (!player.hasInfiniteMaterials() && hungerCost > 0) {
                player.getFoodData().eat(-hungerCost, 0.0F);
            }
        }
    }

    protected abstract void performAbility(Level level, Player player, ItemStack stack, float power, boolean fullyCharged);

    protected static float chargePower(int ticks) {
        float value = ticks / 20.0F;
        value = (value * value + value * 2.0F) / 3.0F;
        return Math.min(value, 1.0F);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        tooltip.add(Component.literal("Ability: " + abilityDescription));
    }
}
