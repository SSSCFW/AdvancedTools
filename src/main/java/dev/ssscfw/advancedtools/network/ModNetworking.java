package dev.ssscfw.advancedtools.network;

import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

public final class ModNetworking {
    private ModNetworking() {
    }

    public static void register(RegisterPayloadHandlersEvent event) {
        var registrar = event.registrar("1");
        registrar.playToServer(
                ToggleMagnetPayload.TYPE,
                ToggleMagnetPayload.STREAM_CODEC,
                ToggleMagnetPayload::handle);
        registrar.playToServer(
                ToggleSolidifierPayload.TYPE,
                ToggleSolidifierPayload.STREAM_CODEC,
                ToggleSolidifierPayload::handle);
    }
}
