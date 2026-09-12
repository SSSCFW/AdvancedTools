package dev.ssscfw.advancedtools.item;

import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class UpgradedAxeItem extends AxeItem implements AreaMiningItem {
    private final int maxRange;
    private final boolean infinite;

    public UpgradedAxeItem(Tier baseTier, float durabilityMultiplier, boolean infinite) {
        this(new ScaledTier(baseTier, durabilityMultiplier), rangeFor(baseTier), infinite);
    }

    private UpgradedAxeItem(Tier tier, int maxRange, boolean infinite) {
        super(tier, new Item.Properties().attributes(AxeItem.createAttributes(tier, axeDamage(tier), axeSpeed(tier))));
        this.maxRange = maxRange;
        this.infinite = infinite;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return AreaMiningSupport.cycleRange(level, player, hand, maxRange);
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miner) {
        boolean result = infinite || super.mineBlock(stack, level, state, pos, miner);
        AreaMiningSupport.mineArea(stack, level, state, pos, miner, maxRange, AreaMiningSupport.Kind.AXE);
        return result;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (!infinite) {
            return super.useOn(context);
        }
        ItemStack stack = context.getItemInHand();
        int damage = stack.getDamageValue();
        InteractionResult result = super.useOn(context);
        stack.setDamageValue(damage);
        return result;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        AreaMiningSupport.appendTooltip(stack, maxRange, tooltip);
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return !infinite && super.isBarVisible(stack);
    }

    private static int rangeFor(Tier tier) {
        if (tier == Tiers.WOOD) return 2;
        if (tier == Tiers.STONE) return 4;
        if (tier == Tiers.IRON) return 7;
        return 9;
    }

    private static float axeDamage(Tier tier) {
        Tier base = tier instanceof ScaledTier scaled ? scaled.delegate() : tier;
        if (base == Tiers.WOOD) return 6.0F;
        if (base == Tiers.STONE) return 7.0F;
        if (base == Tiers.IRON) return 6.0F;
        if (base == Tiers.DIAMOND) return 5.0F;
        return 6.0F;
    }

    private static float axeSpeed(Tier tier) {
        Tier base = tier instanceof ScaledTier scaled ? scaled.delegate() : tier;
        if (base == Tiers.WOOD || base == Tiers.STONE) return -3.2F;
        if (base == Tiers.IRON) return -3.1F;
        return -3.0F;
    }
}
