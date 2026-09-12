package dev.ssscfw.advancedtools.item;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;

public interface AreaMiningItem {
    default void advancedTools$recordMiningFace(ItemStack stack, Direction face) {
        AreaMiningSupport.setFace(stack, face);
    }
}
