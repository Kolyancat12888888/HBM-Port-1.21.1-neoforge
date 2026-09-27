package com.hbm.entity;

import com.hbm.entity.missile.*;
import com.hbm.entity.missile.EntityMissileTier0.*;
import com.hbm.entity.missile.EntityMissileTier1.*;
import com.hbm.entity.missile.EntityMissileTier2.*;
import com.hbm.entity.missile.EntityMissileTier3.*;
import com.hbm.entity.missile.EntityMissileTier4.*;
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

	public static final Supplier<EntityType<com.hbm.entity.logic.EntityNukeExplosionMK5>> EXPLOSION_MK5 =
			ENTITY_TYPES.register("explosion_mk5", () ->
					EntityType.Builder.<com.hbm.entity.logic.EntityNukeExplosionMK5>of(com.hbm.entity.logic.EntityNukeExplosionMK5::new, MobCategory.MISC)
							.sized(0.1F, 0.1F)
							.clientTrackingRange(1000)
							.updateInterval(1)
							.build("explosion_mk5"));

	// Tier 0 Missiles
	public static final Supplier<EntityType<EntityMissileMicro>> MISSILE_MICRO =
			ENTITY_TYPES.register("missile_micro", () ->
					EntityType.Builder.<EntityMissileMicro>of(EntityMissileMicro::new, MobCategory.MISC).sized(1.0F, 2.0F).clientTrackingRange(512).updateInterval(1).build("missile_micro"));

	public static final Supplier<EntityType<EntityMissileSchrabidium>> MISSILE_SCHRABIDIUM =
			ENTITY_TYPES.register("missile_schrabidium", () ->
					EntityType.Builder.<EntityMissileSchrabidium>of(EntityMissileSchrabidium::new, MobCategory.MISC).sized(1.0F, 2.0F).clientTrackingRange(512).updateInterval(1).build("missile_schrabidium"));

	public static final Supplier<EntityType<EntityMissileBHole>> MISSILE_BHOLE =
			ENTITY_TYPES.register("missile_bhole", () ->
					EntityType.Builder.<EntityMissileBHole>of(EntityMissileBHole::new, MobCategory.MISC).sized(1.0F, 2.0F).clientTrackingRange(512).updateInterval(1).build("missile_bhole"));

	public static final Supplier<EntityType<EntityMissileTaint>> MISSILE_TAINT =
			ENTITY_TYPES.register("missile_taint", () ->
					EntityType.Builder.<EntityMissileTaint>of(EntityMissileTaint::new, MobCategory.MISC).sized(1.0F, 2.0F).clientTrackingRange(512).updateInterval(1).build("missile_taint"));

	public static final Supplier<EntityType<EntityMissileEMP>> MISSILE_EMP =
			ENTITY_TYPES.register("missile_emp", () ->
					EntityType.Builder.<EntityMissileEMP>of(EntityMissileEMP::new, MobCategory.MISC).sized(1.0F, 2.0F).clientTrackingRange(512).updateInterval(1).build("missile_emp"));

	// Tier 1 Missiles
	public static final Supplier<EntityType<EntityMissileGeneric>> MISSILE_GENERIC =
			ENTITY_TYPES.register("missile_generic", () ->
					EntityType.Builder.<EntityMissileGeneric>of(EntityMissileGeneric::new, MobCategory.MISC).sized(1.0F, 3.5F).clientTrackingRange(512).updateInterval(1).build("missile_generic"));

	public static final Supplier<EntityType<EntityMissileDecoy>> MISSILE_DECOY =
			ENTITY_TYPES.register("missile_decoy", () ->
					EntityType.Builder.<EntityMissileDecoy>of(EntityMissileDecoy::new, MobCategory.MISC).sized(1.0F, 3.5F).clientTrackingRange(512).updateInterval(1).build("missile_decoy"));

	public static final Supplier<EntityType<EntityMissileIncendiary>> MISSILE_INCENDIARY =
			ENTITY_TYPES.register("missile_incendiary", () ->
					EntityType.Builder.<EntityMissileIncendiary>of(EntityMissileIncendiary::new, MobCategory.MISC).sized(1.0F, 3.5F).clientTrackingRange(512).updateInterval(1).build("missile_incendiary"));

	public static final Supplier<EntityType<EntityMissileCluster>> MISSILE_CLUSTER =
			ENTITY_TYPES.register("missile_cluster", () ->
					EntityType.Builder.<EntityMissileCluster>of(EntityMissileCluster::new, MobCategory.MISC).sized(1.0F, 3.5F).clientTrackingRange(512).updateInterval(1).build("missile_cluster"));

	public static final Supplier<EntityType<EntityMissileBunkerBuster>> MISSILE_BUSTER =
			ENTITY_TYPES.register("missile_buster", () ->
					EntityType.Builder.<EntityMissileBunkerBuster>of(EntityMissileBunkerBuster::new, MobCategory.MISC).sized(1.0F, 3.5F).clientTrackingRange(512).updateInterval(1).build("missile_buster"));

	// Tier 2 Missiles
	public static final Supplier<EntityType<EntityMissileStrong>> MISSILE_STRONG =
			ENTITY_TYPES.register("missile_strong", () ->
					EntityType.Builder.<EntityMissileStrong>of(EntityMissileStrong::new, MobCategory.MISC).sized(1.5F, 5.0F).clientTrackingRange(512).updateInterval(1).build("missile_strong"));

	public static final Supplier<EntityType<EntityMissileIncendiaryStrong>> MISSILE_INCENDIARY_STRONG =
			ENTITY_TYPES.register("missile_incendiary_strong", () ->
					EntityType.Builder.<EntityMissileIncendiaryStrong>of(EntityMissileIncendiaryStrong::new, MobCategory.MISC).sized(1.5F, 5.0F).clientTrackingRange(512).updateInterval(1).build("missile_incendiary_strong"));

	public static final Supplier<EntityType<EntityMissileClusterStrong>> MISSILE_CLUSTER_STRONG =
			ENTITY_TYPES.register("missile_cluster_strong", () ->
					EntityType.Builder.<EntityMissileClusterStrong>of(EntityMissileClusterStrong::new, MobCategory.MISC).sized(1.5F, 5.0F).clientTrackingRange(512).updateInterval(1).build("missile_cluster_strong"));

	public static final Supplier<EntityType<EntityMissileBusterStrong>> MISSILE_BUSTER_STRONG =
			ENTITY_TYPES.register("missile_buster_strong", () ->
					EntityType.Builder.<EntityMissileBusterStrong>of(EntityMissileBusterStrong::new, MobCategory.MISC).sized(1.5F, 5.0F).clientTrackingRange(512).updateInterval(1).build("missile_buster_strong"));

	public static final Supplier<EntityType<EntityMissileEMPStrong>> MISSILE_EMP_STRONG =
			ENTITY_TYPES.register("missile_emp_strong", () ->
					EntityType.Builder.<EntityMissileEMPStrong>of(EntityMissileEMPStrong::new, MobCategory.MISC).sized(1.5F, 5.0F).clientTrackingRange(512).updateInterval(1).build("missile_emp_strong"));

	// Tier 3 Missiles
	public static final Supplier<EntityType<EntityMissileBurst>> MISSILE_BURST =
			ENTITY_TYPES.register("missile_burst", () ->
					EntityType.Builder.<EntityMissileBurst>of(EntityMissileBurst::new, MobCategory.MISC).sized(2.0F, 7.5F).clientTrackingRange(512).updateInterval(1).build("missile_burst"));

	public static final Supplier<EntityType<EntityMissileInferno>> MISSILE_INFERNO =
			ENTITY_TYPES.register("missile_inferno", () ->
					EntityType.Builder.<EntityMissileInferno>of(EntityMissileInferno::new, MobCategory.MISC).sized(2.0F, 7.5F).clientTrackingRange(512).updateInterval(1).build("missile_inferno"));

	public static final Supplier<EntityType<EntityMissileRain>> MISSILE_RAIN =
			ENTITY_TYPES.register("missile_rain", () ->
					EntityType.Builder.<EntityMissileRain>of(EntityMissileRain::new, MobCategory.MISC).sized(2.0F, 7.5F).clientTrackingRange(512).updateInterval(1).build("missile_rain"));

	public static final Supplier<EntityType<EntityMissileDrill>> MISSILE_DRILL =
			ENTITY_TYPES.register("missile_drill", () ->
					EntityType.Builder.<EntityMissileDrill>of(EntityMissileDrill::new, MobCategory.MISC).sized(2.0F, 7.5F).clientTrackingRange(512).updateInterval(1).build("missile_drill"));

	public static final Supplier<EntityType<EntityMissileShuttle>> MISSILE_SHUTTLE =
			ENTITY_TYPES.register("missile_shuttle", () ->
					EntityType.Builder.<EntityMissileShuttle>of(EntityMissileShuttle::new, MobCategory.MISC).sized(2.0F, 7.5F).clientTrackingRange(512).updateInterval(1).build("missile_shuttle"));

	// Tier 4 Missiles
	public static final Supplier<EntityType<EntityMissileNuclear>> MISSILE_NUCLEAR =
			ENTITY_TYPES.register("missile_nuclear", () ->
					EntityType.Builder.<EntityMissileNuclear>of(EntityMissileNuclear::new, MobCategory.MISC).sized(2.5F, 10.0F).clientTrackingRange(1000).updateInterval(1).build("missile_nuclear"));

	public static final Supplier<EntityType<EntityMissileMirv>> MISSILE_NUCLEAR_CLUSTER =
			ENTITY_TYPES.register("missile_nuclear_cluster", () ->
					EntityType.Builder.<EntityMissileMirv>of(EntityMissileMirv::new, MobCategory.MISC).sized(2.5F, 10.0F).clientTrackingRange(1000).updateInterval(1).build("missile_nuclear_cluster"));

	public static final Supplier<EntityType<EntityMissileVolcano>> MISSILE_VOLCANO =
			ENTITY_TYPES.register("missile_volcano", () ->
					EntityType.Builder.<EntityMissileVolcano>of(EntityMissileVolcano::new, MobCategory.MISC).sized(2.5F, 10.0F).clientTrackingRange(1000).updateInterval(1).build("missile_volcano"));

	public static final Supplier<EntityType<EntityMissileDoomsday>> MISSILE_DOOMSDAY =
			ENTITY_TYPES.register("missile_doomsday", () ->
					EntityType.Builder.<EntityMissileDoomsday>of(EntityMissileDoomsday::new, MobCategory.MISC).sized(2.5F, 10.0F).clientTrackingRange(1000).updateInterval(1).build("missile_doomsday"));

	public static final Supplier<EntityType<EntityMissileDoomsdayRusted>> MISSILE_DOOMSDAY_RUSTED =
			ENTITY_TYPES.register("missile_doomsday_rusted", () ->
					EntityType.Builder.<EntityMissileDoomsdayRusted>of(EntityMissileDoomsdayRusted::new, MobCategory.MISC).sized(2.5F, 10.0F).clientTrackingRange(1000).updateInterval(1).build("missile_doomsday_rusted"));

	public static final Supplier<EntityType<EntityMissileN2>> MISSILE_N2 =
			ENTITY_TYPES.register("missile_n2", () ->
					EntityType.Builder.<EntityMissileN2>of(EntityMissileN2::new, MobCategory.MISC).sized(2.5F, 10.0F).clientTrackingRange(1000).updateInterval(1).build("missile_n2"));

	public static final Supplier<EntityType<EntityMissileStealth>> MISSILE_STEALTH =
			ENTITY_TYPES.register("missile_stealth", () ->
					EntityType.Builder.<EntityMissileStealth>of(EntityMissileStealth::new, MobCategory.MISC).sized(1.5F, 5.0F).clientTrackingRange(512).updateInterval(1).build("missile_stealth"));

	// Custom & Anti-Ballistic
	public static final Supplier<EntityType<EntityMissileCustom>> MISSILE_CUSTOM =
			ENTITY_TYPES.register("missile_custom", () ->
					EntityType.Builder.<EntityMissileCustom>of(EntityMissileCustom::new, MobCategory.MISC).sized(1.5F, 5.0F).clientTrackingRange(1000).updateInterval(1).build("missile_custom"));

	public static final Supplier<EntityType<EntityMissileAntiBallistic>> MISSILE_ANTI_BALLISTIC =
			ENTITY_TYPES.register("missile_anti_ballistic", () ->
					EntityType.Builder.<EntityMissileAntiBallistic>of(EntityMissileAntiBallistic::new, MobCategory.MISC).sized(1.0F, 8.0F).clientTrackingRange(1000).updateInterval(1).build("missile_anti_ballistic"));

	public static final Supplier<EntityType<EntityMIRV>> MIRVLET =
			ENTITY_TYPES.register("mirvlet", () ->
					EntityType.Builder.<EntityMIRV>of(EntityMIRV::new, MobCategory.MISC).sized(0.5F, 1.0F).clientTrackingRange(1000).updateInterval(1).build("mirvlet"));

	public static void register(IEventBus bus) {
		ENTITY_TYPES.register(bus);
	}
}
