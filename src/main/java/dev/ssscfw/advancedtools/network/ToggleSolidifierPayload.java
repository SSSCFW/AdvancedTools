package dev.ssscfw.advancedtools.network;

import dev.ssscfw.advancedtools.AdvancedTools;
import dev.ssscfw.advancedtools.item.SolidifierItem;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ToggleSolidifierPayload() implements CustomPacketPayload {
    public static final ToggleSolidifierPayload INSTANCE = new ToggleSolidifierPayload();
    public static final Type<ToggleSolidifierPayload> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(AdvancedTools.MOD_ID, "toggle_solidifier"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ToggleSolidifierPayload> STREAM_CODEC = StreamCodec.unit(INSTANCE);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(ToggleSolidifierPayload payload, IPayloadContext context) {
        if (!(context.player() instanceof ServerPlayer player)) {
            return;
        }

        ItemStack stack = player.getMainHandItem();
        if (!(stack.getItem() instanceof SolidifierItem)) {
            stack = player.getOffhandItem();
        }
        if (!(stack.getItem() instanceof SolidifierItem)) {
            return;
        }

        boolean enabled = SolidifierItem.toggle(stack);
        player.getInventory().setChanged();
        player.displayClientMessage(Component.translatable(enabled
                ? "message.advancedtools.solidifier.enabled"
                : "message.advancedtools.solidifier.disabled"), true);
    }
}
