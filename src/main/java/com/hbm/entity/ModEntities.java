package com.hbm.entity;

import com.hbm.entity.projectile.EntityBulletBaseMK4;
import com.hbm.entity.projectile.EntityGrenadeGeneric;
import com.hbm.main.MainRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntities {

	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
			DeferredRegister.create(Registries.ENTITY_TYPE, MainRegistry.MODID);

	public static final Supplier<EntityType<EntityBulletBaseMK4>> BULLET_MK4 =
			ENTITY_TYPES.register("bullet_mk4", () ->
					EntityType.Builder.<EntityBulletBaseMK4>of(EntityBulletBaseMK4::new, MobCategory.MISC)
							.sized(0.25F, 0.25F)
							.clientTrackingRange(256)
							.updateInterval(1)
							.build("bullet_mk4"));

	public static final Supplier<EntityType<EntityGrenadeGeneric>> GRENADE_GENERIC =
			ENTITY_TYPES.register("grenade_generic", () ->
					EntityType.Builder.<EntityGrenadeGeneric>of(EntityGrenadeGeneric::new, MobCategory.MISC)
							.sized(0.25F, 0.25F)
							.clientTrackingRange(64)
							.updateInterval(3)
							.build("grenade_generic"));

	public static final Supplier<EntityType<com.hbm.entity.missile.EntityMissileGeneric>> MISSILE_GENERIC =
			ENTITY_TYPES.register("missile_generic", () ->
					EntityType.Builder.<com.hbm.entity.missile.EntityMissileGeneric>of(com.hbm.entity.missile.EntityMissileGeneric::new, MobCategory.MISC)
							.sized(1.0F, 3.5F)
							.clientTrackingRange(512)
							.updateInterval(1)
							.build("missile_generic"));

	public static void register(IEventBus bus) {
		ENTITY_TYPES.register(bus);
	}
}
