package dev.ssscfw.advancedtools.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class InfiniteHoeItem extends HoeItem {
    public InfiniteHoeItem(Tier tier) {
        super(tier, new Item.Properties().attributes(HoeItem.createAttributes(tier, 0.0F, -3.0F)));
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miner) {
        return true;
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        // Legacy item used setMaxDamage(0): it is fully unbreakable, including when used as a weapon.
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        ItemStack stack = context.getItemInHand();
        int damage = stack.getDamageValue();
        InteractionResult result = super.useOn(context);
        stack.setDamageValue(damage);
        return result;
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return false;
    }
}
