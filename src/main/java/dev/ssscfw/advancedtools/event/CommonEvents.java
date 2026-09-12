package dev.ssscfw.advancedtools.event;

import dev.ssscfw.advancedtools.item.AreaMiningItem;
import dev.ssscfw.advancedtools.item.MagnetItem;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

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

    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        int range = activeMagnetRange(player);
        if (range <= 0) {
            return;
        }

        AABB area = new AABB(
                player.getX() - range, player.getY() - range, player.getZ() - range,
                player.getX() + range, player.getY() + range, player.getZ() + range);

        for (ItemEntity item : player.serverLevel().getEntitiesOfClass(ItemEntity.class, area, ItemEntity::isAlive)) {
            item.setNoPickUpDelay();
            item.playerTouch(player);
        }

        for (ExperienceOrb orb : player.serverLevel().getEntitiesOfClass(ExperienceOrb.class, area, ExperienceOrb::isAlive)) {
            player.takeXpDelay = 0;
            orb.playerTouch(player);
        }
    }

    private static int activeMagnetRange(ServerPlayer player) {
        int maxRange = 0;
        for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
            ItemStack stack = player.getInventory().getItem(slot);
            if (stack.getItem() instanceof MagnetItem && MagnetItem.isEnabled(stack)) {
                maxRange = Math.max(maxRange, MagnetItem.getRange(stack));
            }
        }
        return maxRange;
    }
}
