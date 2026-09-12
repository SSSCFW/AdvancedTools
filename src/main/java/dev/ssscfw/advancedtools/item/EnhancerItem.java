package dev.ssscfw.advancedtools.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

public class EnhancerItem extends Item {
    public EnhancerItem(Rarity rarity) {
        super(new Item.Properties().rarity(rarity));
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
}
