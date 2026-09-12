package dev.ssscfw.advancedtools.item;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;

public class NegiItem extends SwordItem {
    private static final FoodProperties FOOD = new FoodProperties.Builder()
            .nutrition(1)
            .saturationModifier(0.1F)
            .build();

    public NegiItem() {
        this(new FixedTier(Tiers.WOOD, 87));
    }

    private NegiItem(Tier tier) {
        super(tier, new Item.Properties()
                .food(FOOD)
                .attributes(SwordItem.createAttributes(tier, 3, -2.4F)));
    }
}
