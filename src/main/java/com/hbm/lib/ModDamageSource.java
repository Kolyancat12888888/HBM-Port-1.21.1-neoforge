package com.hbm.lib;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class ModDamageSource {

	public static final ResourceKey<DamageType> NUCLEAR_BLAST = create("nuclear_blast");
	public static final ResourceKey<DamageType> BLAST = create("blast");
	public static final ResourceKey<DamageType> MUD_POISONING = create("mud_poisoning");
	public static final ResourceKey<DamageType> ACID = create("acid");
	public static final ResourceKey<DamageType> EUTHANIZED_SELF = create("euthanized_self");
	public static final ResourceKey<DamageType> EUTHANIZED_SELF2 = create("euthanized_self2");
	public static final ResourceKey<DamageType> TAU_BLAST = create("tau_blast");
	public static final ResourceKey<DamageType> DIGAMMA = create("digamma");
	public static final ResourceKey<DamageType> RADIATION = create("radiation");
	public static final ResourceKey<DamageType> SUICIDE = create("suicide");
	public static final ResourceKey<DamageType> RUBBLE = create("rubble");
	public static final ResourceKey<DamageType> SHRAPNEL = create("shrapnel");
	public static final ResourceKey<DamageType> BLACKHOLE = create("blackhole");
	public static final ResourceKey<DamageType> TURBOFAN = create("turbofan");
	public static final ResourceKey<DamageType> METEORITE = create("meteorite");
	public static final ResourceKey<DamageType> BOXCAR = create("boxcar");
	public static final ResourceKey<DamageType> BOAT = create("boat");
	public static final ResourceKey<DamageType> BUILDING = create("building");
	public static final ResourceKey<DamageType> TAINT = create("taint");
	public static final ResourceKey<DamageType> AMS = create("ams");
	public static final ResourceKey<DamageType> AMS_CORE = create("ams_core");
	public static final ResourceKey<DamageType> BROADCAST = create("broadcast");
	public static final ResourceKey<DamageType> BANG = create("bang");
	public static final ResourceKey<DamageType> PC = create("pc");
	public static final ResourceKey<DamageType> CLOUD = create("cloud");
	public static final ResourceKey<DamageType> LEAD = create("lead");
	public static final ResourceKey<DamageType> ENERVATION = create("enervation");
	public static final ResourceKey<DamageType> ELECTRICITY = create("electricity");
	public static final ResourceKey<DamageType> EXHAUST = create("exhaust");
	public static final ResourceKey<DamageType> SPIKES = create("spikes");
	public static final ResourceKey<DamageType> LUNAR = create("lunar");
	public static final ResourceKey<DamageType> SLICER = create("slicer");
	public static final ResourceKey<DamageType> CRUCIBLE = create("crucible");
	public static final ResourceKey<DamageType> MONOXIDE = create("monoxide");
	public static final ResourceKey<DamageType> ASBESTOS = create("asbestos");
	public static final ResourceKey<DamageType> BLACKLUNG = create("blacklung");
	public static final ResourceKey<DamageType> MKU = create("mku");
	public static final ResourceKey<DamageType> VACUUM = create("vacuum");
	public static final ResourceKey<DamageType> OVERDOSE = create("overdose");
	public static final ResourceKey<DamageType> MICROWAVE = create("microwave");
	public static final ResourceKey<DamageType> NITAN = create("nitan");

	private static ResourceKey<DamageType> create(String name) {
		return ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(RefStrings.MODID, name));
	}

	public static DamageSource of(Level level, ResourceKey<DamageType> key) {
		return new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(key));
	}

	public static DamageSource of(Level level, ResourceKey<DamageType> key, Entity causingEntity) {
		return new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(key), causingEntity);
	}

	public static DamageSource of(Level level, ResourceKey<DamageType> key, Entity directEntity, Entity causingEntity) {
		return new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(key), directEntity, causingEntity);
	}
}
