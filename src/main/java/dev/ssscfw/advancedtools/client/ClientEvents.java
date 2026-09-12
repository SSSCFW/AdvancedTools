package dev.ssscfw.advancedtools.client;

import dev.ssscfw.advancedtools.AdvancedTools;
import dev.ssscfw.advancedtools.entity.HighSkeletonEntity;
import dev.ssscfw.advancedtools.entity.SkeletonSniperEntity;
import dev.ssscfw.advancedtools.registry.ModEntities;
import net.minecraft.client.renderer.entity.CreeperRenderer;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Zombie;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = AdvancedTools.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientEvents {
    private static final ResourceLocation HIGH_SKELETON = texture("hskeleton.png");
    private static final ResourceLocation SKELETON_SNIPER = texture("skeletons.png");
    private static final ResourceLocation ZOMBIE_WARRIOR = texture("zombiew.png");
    private static final ResourceLocation FIRE_ZOMBIE = texture("fzombie.png");
    private static final ResourceLocation HIGH_SPEED_CREEPER = texture("hscreeper.png");
    private static final ResourceLocation GOLD_CREEPER = texture("gcreeper.png");

    private ClientEvents() {
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.THROWING_KNIFE.get(), context -> new ThrownItemRenderer<>(context, 1.0F, true));
        event.registerEntityRenderer(ModEntities.HIGH_SKELETON.get(), context -> new SkeletonRenderer<HighSkeletonEntity>(context) {
            @Override public ResourceLocation getTextureLocation(HighSkeletonEntity entity) { return HIGH_SKELETON; }
        });
        event.registerEntityRenderer(ModEntities.SKELETON_SNIPER.get(), context -> new SkeletonRenderer<SkeletonSniperEntity>(context) {
            @Override public ResourceLocation getTextureLocation(SkeletonSniperEntity entity) { return SKELETON_SNIPER; }
        });
        event.registerEntityRenderer(ModEntities.ZOMBIE_WARRIOR.get(), context -> new ZombieRenderer(context) {
            @Override public ResourceLocation getTextureLocation(Zombie entity) { return ZOMBIE_WARRIOR; }
        });
        event.registerEntityRenderer(ModEntities.FIRE_ZOMBIE.get(), context -> new ZombieRenderer(context) {
            @Override public ResourceLocation getTextureLocation(Zombie entity) { return FIRE_ZOMBIE; }
        });
        event.registerEntityRenderer(ModEntities.HIGH_SPEED_CREEPER.get(), context -> new CreeperRenderer(context) {
            @Override public ResourceLocation getTextureLocation(Creeper entity) { return HIGH_SPEED_CREEPER; }
        });
        event.registerEntityRenderer(ModEntities.GOLD_CREEPER.get(), context -> new CreeperRenderer(context) {
            @Override public ResourceLocation getTextureLocation(Creeper entity) { return GOLD_CREEPER; }
        });
    }

    private static ResourceLocation texture(String file) {
        return ResourceLocation.fromNamespaceAndPath(AdvancedTools.MOD_ID, "textures/mob/" + file);
    }
}
