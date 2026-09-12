package dev.ssscfw.advancedtools.network;

import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

public final class ModNetworking {
    private ModNetworking() {
    }

    public static void register(RegisterPayloadHandlersEvent event) {
        event.registrar("1").playToServer(
                ToggleMagnetPayload.TYPE,
                ToggleMagnetPayload.STREAM_CODEC,
                ToggleMagnetPayload::handle);
    }
}
