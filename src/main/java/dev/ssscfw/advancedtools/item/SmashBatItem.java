package dev.ssscfw.advancedtools.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.phys.Vec3;

public final class SmashBatItem extends SpecialSwordItem {
    public SmashBatItem() {
        super(Tiers.WOOD, 95, 1, false);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        Vec3 away = target.position().subtract(attacker.position());
        if (away.lengthSqr() > 1.0E-6D) {
            Vec3 push = new Vec3(away.x, 0.0D, away.z).normalize().scale(2.5D);
            target.push(push.x, 0.35D, push.z);
        }
        return true;
    }
}
