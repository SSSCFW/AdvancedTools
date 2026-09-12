package dev.ssscfw.advancedtools.registry;

import dev.ssscfw.advancedtools.AdvancedTools;
import dev.ssscfw.advancedtools.entity.ThrowingKnifeEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(Registries.ENTITY_TYPE, AdvancedTools.MOD_ID);

    public static final DeferredHolder<EntityType<?>, EntityType<ThrowingKnifeEntity>> THROWING_KNIFE =
            ENTITY_TYPES.register("throwingknife", () -> EntityType.Builder
                    .<ThrowingKnifeEntity>of(ThrowingKnifeEntity::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(8)
                    .updateInterval(10)
                    .build("throwingknife"));

    private ModEntities() {
    }
}
