package dev.ssscfw.advancedtools.item;

import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;

public final class SolidifierItem extends Item {
    public static final int MIN_RANGE = 1;
    public static final int MAX_RANGE = 13;

    private static final String RANGE_KEY = "advancedtools_solidifier_range";
    private static final String ENABLED_KEY = "advancedtools_solidifier_enabled";

    public SolidifierItem() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        int current = getRange(stack);
        int next;
        if (player.isShiftKeyDown()) {
            next = current <= MIN_RANGE ? MAX_RANGE : current - 1;
        } else {
            next = current >= MAX_RANGE ? MIN_RANGE : current + 1;
        }
        setRange(stack, next);

        if (!level.isClientSide) {
            int size = next * 2 + 1;
            player.displayClientMessage(Component.translatable("message.advancedtools.solidifier.range", size, size), true);
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return isEnabled(stack) || super.isFoil(stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        int size = getRange(stack) * 2 + 1;
        tooltip.add(Component.translatable("tooltip.advancedtools.solidifier.range", size, size).withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable(isEnabled(stack)
                ? "tooltip.advancedtools.solidifier.enabled"
                : "tooltip.advancedtools.solidifier.disabled")
                .withStyle(isEnabled(stack) ? ChatFormatting.GREEN : ChatFormatting.RED));
        tooltip.add(Component.translatable("tooltip.advancedtools.solidifier.toggle").withStyle(ChatFormatting.DARK_GRAY));
    }

    public static int getRange(ItemStack stack) {
        CompoundTag tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        if (!tag.contains(RANGE_KEY)) {
            return MIN_RANGE;
        }
        return Math.clamp(tag.getInt(RANGE_KEY), MIN_RANGE, MAX_RANGE);
    }

    public static boolean isEnabled(ItemStack stack) {
        CompoundTag tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        return !tag.contains(ENABLED_KEY) || tag.getBoolean(ENABLED_KEY);
    }

    public static boolean toggle(ItemStack stack) {
        boolean enabled = !isEnabled(stack);
        setEnabled(stack, enabled);
        return enabled;
    }

    private static void setRange(ItemStack stack, int range) {
        int clamped = Math.clamp(range, MIN_RANGE, MAX_RANGE);
        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putInt(RANGE_KEY, clamped));
    }

    private static void setEnabled(ItemStack stack, boolean enabled) {
        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putBoolean(ENABLED_KEY, enabled));
    }
}
