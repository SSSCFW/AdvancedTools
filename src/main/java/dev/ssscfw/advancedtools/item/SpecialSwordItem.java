package dev.ssscfw.advancedtools.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

/** Base implementation for the legacy special swords; item-specific powers are layered on top during the port. */
public class SpecialSwordItem extends SwordItem {
    private final boolean infinite;

    public SpecialSwordItem(Tier baseTier, int durability, int legacyAttackDamage, boolean infinite) {
        this(new FixedTier(baseTier, durability), legacyAttackDamage, infinite);
    }

    private SpecialSwordItem(Tier tier, int legacyAttackDamage, boolean infinite) {
        super(tier, new Item.Properties().attributes(SwordItem.createAttributes(
                tier,
                Math.round(legacyAttackDamage - tier.getAttackDamageBonus()),
                -2.4F)));
        this.infinite = infinite;
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!infinite) {
            super.postHurtEnemy(stack, target, attacker);
        }
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return !infinite && super.isBarVisible(stack);
    }
}
