package dev.ssscfw.advancedtools.item;

import net.minecraft.world.item.Item;

public class ThrowingKnifeItem extends Item {
    private final boolean poison;

    public ThrowingKnifeItem(boolean poison) {
        super(new Item.Properties().stacksTo(16));
        this.poison = poison;
    }

    public boolean isPoison() {
        return poison;
    }
}
