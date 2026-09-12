package dev.ssscfw.advancedtools.item;

import dev.ssscfw.advancedtools.entity.BlazeBladeFireball;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.Level;

public final class BlazeBladeItem extends HeatManagedSpecialSwordItem {
    public BlazeBladeItem() {
        super(Tiers.DIAMOND, 1200, 4, "Fire Ball");
    }

    @Override
    protected void performAbility(Level level, Player player, ItemStack stack, float power, boolean fullyCharged) {
        BlazeBladeFireball fireball = new BlazeBladeFireball(level, player, player.getLookAngle(), fullyCharged);
        level.addFreshEntity(fireball);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.igniteForSeconds(4.0F);
        return true;
    }
}
