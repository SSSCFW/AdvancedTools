package dev.ssscfw.advancedtools.network;

import dev.ssscfw.advancedtools.AdvancedTools;
import dev.ssscfw.advancedtools.item.MagnetItem;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ToggleMagnetPayload() implements CustomPacketPayload {
    public static final ToggleMagnetPayload INSTANCE = new ToggleMagnetPayload();
    public static final Type<ToggleMagnetPayload> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(AdvancedTools.MOD_ID, "toggle_magnet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ToggleMagnetPayload> STREAM_CODEC = StreamCodec.unit(INSTANCE);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(ToggleMagnetPayload payload, IPayloadContext context) {
        if (!(context.player() instanceof ServerPlayer player)) {
            return;
        }

        ItemStack stack = player.getMainHandItem();
        if (!(stack.getItem() instanceof MagnetItem)) {
            stack = player.getOffhandItem();
        }
        if (!(stack.getItem() instanceof MagnetItem)) {
            return;
        }

        boolean enabled = MagnetItem.toggle(stack);
        player.getInventory().setChanged();
        player.displayClientMessage(Component.translatable(enabled
                ? "message.advancedtools.magnet.enabled"
                : "message.advancedtools.magnet.disabled"), true);
    }
}
