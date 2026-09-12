package dev.ssscfw.advancedtools.item;

import dev.ssscfw.advancedtools.entity.ThrowingKnifeEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ThrowingKnifeItem extends Item {
    private final boolean poison;

    public ThrowingKnifeItem(boolean poison) {
        super(new Item.Properties().stacksTo(16));
        this.poison = poison;
    }

    public boolean isPoison() {
        return poison;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide) {
            ThrowingKnifeEntity knife = new ThrowingKnifeEntity(player, level);
            knife.setItem(stack.copyWithCount(1));
            knife.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
            level.addFreshEntity(knife);
        }
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.PLAYERS, 0.5F, 0.8F + level.random.nextFloat() * 0.4F);
        if (!player.hasInfiniteMaterials()) {
            stack.shrink(1);
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }
}
