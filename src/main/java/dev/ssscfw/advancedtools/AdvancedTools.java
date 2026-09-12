package dev.ssscfw.advancedtools;

import dev.ssscfw.advancedtools.event.CommonEvents;
import dev.ssscfw.advancedtools.registry.ModItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;

@Mod(AdvancedTools.MOD_ID)
public final class AdvancedTools {
    public static final String MOD_ID = "advancedtools";

    public AdvancedTools(IEventBus modBus, ModContainer modContainer) {
        ModItems.ITEMS.register(modBus);
        ModItems.CREATIVE_TABS.register(modBus);
        NeoForge.EVENT_BUS.addListener(CommonEvents::onLeftClickBlock);
    }
}
