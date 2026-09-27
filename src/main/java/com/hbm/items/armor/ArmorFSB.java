package com.hbm.items.armor;

import com.hbm.handler.ArmorUtil;
import com.hbm.handler.HazmatRegistry;
import com.hbm.handler.radiation.ChunkRadiationManager;
import com.hbm.items.ModItems;
import com.hbm.potion.HbmPotion;
import com.hbm.util.BobMathUtil;
import com.hbm.util.ContaminationUtil;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class ArmorFSB extends ArmorItem {

	public List<MobEffectInstance> effects = new ArrayList<>();
	public boolean noHelmet = false;
	public boolean vats = false;
	public boolean thermal = false;
	public boolean geigerSound = false;
	public boolean customGeiger = false;
	public boolean hardLanding = false;
	public int dashCount = 0;
	public int stepSize = 0;
	public SoundEvent step;
	public SoundEvent jump;
	public SoundEvent fall;
	public double radResist = 0;

	public ArmorFSB(Holder<ArmorMaterial> material, Type type, Properties properties) {
		super(material, type, properties);
	}

	public static boolean hasFSBArmor(Player player) {
		if (player == null) return false;
		ItemStack plate = player.getItemBySlot(EquipmentSlot.CHEST);
		if (!plate.isEmpty() && plate.getItem() instanceof ArmorFSB chestplate) {
			boolean noHelmet = chestplate.noHelmet;
			for (EquipmentSlot slot : new EquipmentSlot[]{EquipmentSlot.FEET, EquipmentSlot.LEGS, EquipmentSlot.CHEST, EquipmentSlot.HEAD}) {
				if (noHelmet && slot == EquipmentSlot.HEAD) continue;
				ItemStack armor = player.getItemBySlot(slot);
				if (armor.isEmpty() || !(armor.getItem() instanceof ArmorFSB armorFSB)) return false;
				if (armorFSB.getMaterial() != chestplate.getMaterial()) return false;
				if (!armorFSB.isArmorEnabled(armor)) return false;
			}
			return true;
		}
		return false;
	}

	public static boolean hasFSBArmorIgnoreCharge(Player player) {
		if (player == null) return false;
		ItemStack plate = player.getItemBySlot(EquipmentSlot.CHEST);
		if (!plate.isEmpty() && plate.getItem() instanceof ArmorFSB chestplate) {
			boolean noHelmet = chestplate.noHelmet;
			for (EquipmentSlot slot : new EquipmentSlot[]{EquipmentSlot.FEET, EquipmentSlot.LEGS, EquipmentSlot.CHEST, EquipmentSlot.HEAD}) {
				if (noHelmet && slot == EquipmentSlot.HEAD) continue;
				ItemStack armor = player.getItemBySlot(slot);
				if (armor.isEmpty() || !(armor.getItem() instanceof ArmorFSB armorFSB)) return false;
				if (armorFSB.getMaterial() != chestplate.getMaterial()) return false;
			}
			return true;
		}
		return false;
	}

	public boolean isArmorEnabled(ItemStack stack) {
		return true;
	}

	public ArmorFSB enableThermalSight(boolean thermal) {
		this.thermal = thermal;
		return this;
	}

	public ArmorFSB setHasGeigerSound(boolean geiger) {
		this.geigerSound = geiger;
		return this;
	}

	public ArmorFSB setHasCustomGeiger(boolean geiger) {
		this.customGeiger = geiger;
		return this;
	}

	public ArmorFSB setHasHardLanding(boolean hardLanding) {
		this.hardLanding = hardLanding;
		return this;
	}

	public ArmorFSB setDashCount(int dashCount) {
		this.dashCount = dashCount;
		return this;
	}

	public ArmorFSB setStepSize(int stepSize) {
		this.stepSize = stepSize;
		return this;
	}

	public ArmorFSB setStep(SoundEvent step) {
		this.step = step;
		return this;
	}

	public ArmorFSB setJump(SoundEvent jump) {
		this.jump = jump;
		return this;
	}

	public ArmorFSB setFall(SoundEvent fall) {
		this.fall = fall;
		return this;
	}

	public ArmorFSB addEffect(MobEffectInstance effect) {
		effects.add(effect);
		return this;
	}

	public ArmorFSB setNoHelmet(boolean noHelmet) {
		this.noHelmet = noHelmet;
		return this;
	}

	public ArmorFSB enableVATS(boolean vats) {
		this.vats = vats;
		return this;
	}

	public ArmorFSB setRadResist(double fullSet) {
		this.radResist = fullSet;
		return this;
	}

	public ArmorFSB cloneStats(ArmorFSB original) {
		this.effects = original.effects;
		this.noHelmet = original.noHelmet;
		this.vats = original.vats;
		this.thermal = original.thermal;
		this.geigerSound = original.geigerSound;
		this.customGeiger = original.customGeiger;
		this.hardLanding = original.hardLanding;
		this.dashCount = original.dashCount;
		this.stepSize = original.stepSize;
		this.step = original.step;
		this.jump = original.jump;
		this.fall = original.fall;
		this.setRadResist(original.radResist);
		return this;
	}

	public static void handlePlayerTick(Player player) {
		if (player.level().isClientSide) return;
		if (hasFSBArmor(player)) {
			ItemStack plate = player.getItemBySlot(EquipmentSlot.CHEST);
			if (plate.getItem() instanceof ArmorFSB chestplate) {
				for (MobEffectInstance effect : chestplate.effects) {
					player.addEffect(new MobEffectInstance(effect.getEffect(), effect.getDuration(), effect.getAmplifier(), effect.isAmbient(), false));
				}
			}
		}
	}

	public static void handleFall(Player player, float fallDistance) {
		if (hasFSBArmor(player)) {
			ItemStack plate = player.getItemBySlot(EquipmentSlot.CHEST);
			if (plate.getItem() instanceof ArmorFSB chestplate) {
				if (chestplate.hardLanding && fallDistance > 10) {
					AABB bb = player.getBoundingBox().inflate(3, 0, 3);
					List<Entity> entities = player.level().getEntities(player, bb, e -> !(e instanceof ItemEntity));
					for (Entity e : entities) {
						Vec3 vec = new Vec3(player.getX() - e.getX(), 0, player.getZ() - e.getZ());
						if (vec.length() < 3) {
							double intensity = 3 - vec.length();
							e.setDeltaMovement(e.getDeltaMovement().add(vec.x * intensity * -0.5, 0.1 * intensity, vec.z * intensity * -0.5));
							e.hurt(player.damageSources().playerAttack(player), (float) (intensity * 10));
						}
					}
				}
			}
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		super.appendHoverText(stack, context, tooltip, flag);
		if (radResist > 0) {
			tooltip.add(Component.literal("§2Radiation Resistance: §a" + (int)(radResist * 100) + "%"));
		}
		if (geigerSound) tooltip.add(Component.literal("§6Integrated Geiger Counter"));
		if (vats) tooltip.add(Component.literal("§cIntegrated VATS Targeting System"));
		if (thermal) tooltip.add(Component.literal("§cThermal Vision Sensor"));
		if (hardLanding) tooltip.add(Component.literal("§cHydraulic Shock Absorbers (Hard Landing Shockwave)"));
	}
}
