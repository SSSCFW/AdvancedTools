package dev.ssscfw.advancedtools.event;

import dev.ssscfw.advancedtools.item.AreaMiningItem;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

public final class CommonEvents {
    private CommonEvents() {
    }

    public static void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        if (event.getAction() != PlayerInteractEvent.LeftClickBlock.Action.START) {
            return;
        }
        ItemStack stack = event.getItemStack();
        Direction face = event.getFace();
        if (face != null && stack.getItem() instanceof AreaMiningItem item) {
            item.advancedTools$recordMiningFace(stack, face);
        }
    }
}
