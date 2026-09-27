package com.hbm.util;

import com.hbm.capability.HbmLivingProps;
import com.hbm.handler.ArmorUtil;
import com.hbm.handler.HazmatRegistry;
import com.hbm.handler.radiation.ChunkRadiationManager;
import com.hbm.hazard.HazardSystem;
import com.hbm.hazard.type.HazardTypeRadiation;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.lib.ModDamageSource;
import com.hbm.potion.HbmPotion;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;
import net.minecraft.world.entity.animal.horse.ZombieHorse;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class ContaminationUtil {

	public static final String NTM_NEUTRON_NBT_KEY = "ntmNeutron";
	public static final String RAD_MULT_KEY = "hbmradmultiplier";

	public static Class<?>[] immuneEntities = new Class<?>[]{
			MushroomCow.class,
			Zombie.class,
			Skeleton.class,
			Ocelot.class,
			ZombieHorse.class,
			SkeletonHorse.class,
			ArmorStand.class
	};

	/**
	 * Calculates how much radiation can be applied to this entity by calculating resistance
	 */
	public static double calculateRadiationMod(LivingEntity entity) {
		if (HbmPotion.mutation != null && entity.hasEffect(HbmPotion.mutation))
			return 0D;

		double mult = 1D;
		CustomData customData = entity.getItemBySlot(EquipmentSlot.CHEST).get(DataComponents.CUSTOM_DATA);
		if (customData != null && customData.copyTag().contains(RAD_MULT_KEY)) {
			mult = customData.copyTag().getFloat(RAD_MULT_KEY);
		}

		double koeff = 10.0D;
		return Math.pow(koeff, -HazmatRegistry.getResistance(entity)) * mult;
	}

	public static void printGeigerData(Player player) {
		double rawRadMod = ContaminationUtil.calculateRadiationMod(player);
		double eRad = HbmLivingProps.getRadiation(player);
		double rads = 0.0D;
		if (ChunkRadiationManager.proxy != null) {
			rads = ChunkRadiationManager.proxy.getRadiation(player.level(), player.blockPosition());
		}
		double env = getPlayerRads(player);
		double res = (1.0 - rawRadMod) * 100.0;
		double resKoeff = HazmatRegistry.getResistance(player) * 100.0;
		double rec = env * rawRadMod;
		double ar;
		String eRadS, radsS, envS, recS, resS, resKoeffS;
		ar = Math.abs(eRad);
		eRadS = (ar >= 1.0e6 || (ar > 0.0 && ar < 1.0e-3)) ? String.format("%.3e", eRad) : String.format("%.3f", eRad);
		ar = Math.abs(rads);
		radsS = (ar >= 1.0e6 || (ar > 0.0 && ar < 1.0e-3)) ? String.format("%.3e", rads) : String.format("%.3f", rads);
		ar = Math.abs(env);
		envS = (ar >= 1.0e6 || (ar > 0.0 && ar < 1.0e-3)) ? String.format("%.3e", env) : String.format("%.3f", env);
		ar = Math.abs(rec);
		recS = (ar >= 1.0e6 || (ar > 0.0 && ar < 1.0e-3)) ? String.format("%.3e", rec) : String.format("%.3f", rec);
		ar = Math.abs(res);
		resS = (ar >= 1.0e6 || (ar > 0.0 && ar < 1.0e-6)) ? String.format("%.6e", res) : String.format("%.6f", res);
		ar = Math.abs(resKoeff);
		resKoeffS = (ar >= 1.0e6 || (ar > 0.0 && ar < 1.0e-2)) ? String.format("%.2e", resKoeff) : String.format("%.2f", resKoeff);

		String chunkPrefix = getPreffixFromRad(rads);
		String envPrefix = getPreffixFromRad(env);
		String recPrefix = getPreffixFromRad(rec);
		String radPrefix = "";
		String resPrefix = "" + ChatFormatting.WHITE;

		if (eRad < 200) radPrefix += ChatFormatting.GREEN;
		else if (eRad < 400) radPrefix += ChatFormatting.YELLOW;
		else if (eRad < 600) radPrefix += ChatFormatting.GOLD;
		else if (eRad < 800) radPrefix += ChatFormatting.RED;
		else if (eRad < 1000) radPrefix += ChatFormatting.DARK_RED;
		else radPrefix += ChatFormatting.DARK_GRAY;
		if (resKoeff > 0) resPrefix += ChatFormatting.GREEN;

		player.sendSystemMessage(Component.literal("===== ☢ ")
				.append(Component.translatable("geiger.title"))
				.append(Component.literal(" ☢ ====="))
				.setStyle(Style.EMPTY.withColor(ChatFormatting.GOLD)));
		player.sendSystemMessage(Component.translatable("geiger.chunkRad")
				.append(Component.literal(" " + chunkPrefix + radsS + " RAD/s"))
				.setStyle(Style.EMPTY.withColor(ChatFormatting.YELLOW)));
		player.sendSystemMessage(Component.translatable("geiger.envRad")
				.append(Component.literal(" " + envPrefix + envS + " RAD/s"))
				.setStyle(Style.EMPTY.withColor(ChatFormatting.YELLOW)));
		player.sendSystemMessage(Component.translatable("geiger.recievedRad")
				.append(Component.literal(" " + recPrefix + recS + " RAD/s"))
				.setStyle(Style.EMPTY.withColor(ChatFormatting.YELLOW)));
		player.sendSystemMessage(Component.translatable("geiger.playerRad")
				.append(Component.literal(" " + radPrefix + eRadS + " RAD"))
				.setStyle(Style.EMPTY.withColor(ChatFormatting.YELLOW)));
		player.sendSystemMessage(Component.translatable("geiger.playerRes")
				.append(Component.literal(" " + resPrefix + resS + "% (" + resKoeffS + ")"))
				.setStyle(Style.EMPTY.withColor(ChatFormatting.YELLOW)));
	}

	public static void printDosimeterData(Player player) {
		double rads = ContaminationUtil.getActualPlayerRads(player);
		boolean limit = false;

		if (rads > 3.6D) {
			rads = 3.6D;
			limit = true;
		}
		rads = ((int) (1000D * rads)) / 1000D;
		String radsPrefix = getPreffixFromRad(rads);

		player.sendSystemMessage(Component.literal("===== ☢ ")
				.append(Component.translatable("dosimeter.title"))
				.append(Component.literal(" ☢ ====="))
				.setStyle(Style.EMPTY.withColor(ChatFormatting.GOLD)));
		player.sendSystemMessage(Component.translatable("geiger.recievedRad")
				.append(Component.literal(" " + radsPrefix + (limit ? ">" : "") + rads + " RAD/s"))
				.setStyle(Style.EMPTY.withColor(ChatFormatting.YELLOW)));
	}

	public static String getTextColorFromPercent(double percent) {
		if (percent < 0.5)
			return "" + ChatFormatting.GREEN;
		else if (percent < 0.6)
			return "" + ChatFormatting.YELLOW;
		else if (percent < 0.7)
			return "" + ChatFormatting.GOLD;
		else if (percent < 0.8)
			return "" + ChatFormatting.RED;
		else if (percent < 0.9)
			return "" + ChatFormatting.DARK_RED;
		else
			return "" + ChatFormatting.DARK_GRAY;
	}

	public static String getTextColorLung(double percent) {
		if (percent > 0.9)
			return "" + ChatFormatting.GREEN;
		else if (percent > 0.75)
			return "" + ChatFormatting.YELLOW;
		else if (percent > 0.5)
			return "" + ChatFormatting.GOLD;
		else if (percent > 0.25)
			return "" + ChatFormatting.RED;
		else if (percent > 0.1)
			return "" + ChatFormatting.DARK_RED;
		else
			return "" + ChatFormatting.DARK_GRAY;
	}

	public static void printDiagnosticData(Player player) {
		double digamma = ((int) (HbmLivingProps.getDigamma(player) * 1000)) / 1000D;
		double halflife = ((int) ((1D - Math.pow(0.5, digamma)) * 10000)) / 100D;

		player.sendSystemMessage(Component.literal("===== Ϝ ")
				.append(Component.translatable("digamma.title"))
				.append(Component.literal(" Ϝ ====="))
				.setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_PURPLE)));
		player.sendSystemMessage(Component.translatable("digamma.playerDigamma")
				.append(Component.literal(" " + ChatFormatting.RED + digamma + " DRX"))
				.setStyle(Style.EMPTY.withColor(ChatFormatting.LIGHT_PURPLE)));
		player.sendSystemMessage(Component.translatable("digamma.playerHealth")
				.append(Component.literal(getTextColorFromPercent(halflife / 100D) + String.format(" %6.2f", halflife) + "%"))
				.setStyle(Style.EMPTY.withColor(ChatFormatting.LIGHT_PURPLE)));
	}

	public static void printLungDiagnosticData(Player player) {
		float playerAsbestos = 100F - ((int) (10000F * HbmLivingProps.getAsbestos(player) / 1000.0F)) / 100F;
		float playerBlacklung = 100F - ((int) (10000F * HbmLivingProps.getBlackLung(player) / 1000.0F)) / 100F;
		float playerTotal = (playerAsbestos * playerBlacklung / 100F);
		int contagion = HbmLivingProps.getContagion(player);

		player.sendSystemMessage(Component.literal("===== L ")
				.append(Component.translatable("lung_scanner.title"))
				.append(Component.literal(" L ====="))
				.setStyle(Style.EMPTY.withColor(ChatFormatting.WHITE)));
		player.sendSystemMessage(Component.translatable("lung_scanner.player_asbestos_health")
				.setStyle(Style.EMPTY.withColor(ChatFormatting.WHITE))
				.append(Component.literal(String.format(getTextColorLung(playerAsbestos / 100D) + " %6.2f", playerAsbestos) + " %")));
		player.sendSystemMessage(Component.translatable("lung_scanner.player_coal_health")
				.setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_GRAY))
				.append(Component.literal(String.format(getTextColorLung(playerBlacklung / 100D) + " %6.2f", playerBlacklung) + " %")));
		player.sendSystemMessage(Component.translatable("lung_scanner.player_total_health")
				.setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY))
				.append(Component.literal(String.format(getTextColorLung(playerTotal / 100D) + " %6.2f", playerTotal) + " %")));
		player.sendSystemMessage(Component.translatable("lung_scanner.player_mku")
				.setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY))
				.append(Component.translatable(contagion > 0 ? "lung_scanner.pos" : "lung_scanner.neg")));
		if (contagion > 0) {
			player.sendSystemMessage(Component.translatable("lung_scanner.player_mku_duration")
					.setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY))
					.append(Component.literal(" §c" + BobMathUtil.ticksToDateString(contagion, 72000))));
		}
	}

	public static double getActualPlayerRads(LivingEntity entity) {
		return getPlayerRads(entity) * ContaminationUtil.calculateRadiationMod(entity);
	}

	public static double getPlayerRads(LivingEntity entity) {
		double rads = HbmLivingProps.getRadBuf(entity);
		if (entity instanceof Player)
			rads = rads + HbmLivingProps.getNeutron(entity) * 20;
		return rads;
	}

	public static double getNoNeutronPlayerRads(LivingEntity entity) {
		return HbmLivingProps.getRadBuf(entity) * ContaminationUtil.calculateRadiationMod(entity);
	}

	public static boolean isRadItem(ItemStack stack) {
		if (stack == null || stack.isEmpty()) return false;
		return HazardSystem.getRawRadsFromStack(stack) > 0;
	}

	public static float getNeutronRads(ItemStack stack) {
		if (stack != null && !stack.isEmpty() && !isRadItem(stack)) {
			CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
			if (customData != null) {
				CompoundTag nbt = customData.copyTag();
				if (nbt.contains(NTM_NEUTRON_NBT_KEY)) {
					return nbt.getFloat(NTM_NEUTRON_NBT_KEY) * stack.getCount();
				}
			}
		}
		return 0F;
	}

	public static boolean neutronActivateInventory(Player player, float rad, float decay) {
		boolean changed = false;
		for (int slotI = 0; slotI < player.getInventory().items.size(); slotI++) {
			if (slotI != player.getInventory().selected) {
				if (neutronActivateItem(player.getInventory().items.get(slotI), rad, decay)) {
					changed = true;
				}
			}
		}
		for (ItemStack slotA : player.getInventory().armor) {
			if (neutronActivateItem(slotA, rad, decay)) {
				changed = true;
			}
		}
		return changed;
	}

	public static boolean neutronActivateItem(ItemStack stack, float rad, float decay) {
		if (stack == null || stack.isEmpty() || stack.getCount() != 1 || isRadItem(stack)) return false;
		float prevActivation = 0;
		CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
		if (customData != null && customData.copyTag().contains(NTM_NEUTRON_NBT_KEY)) {
			prevActivation = customData.copyTag().getFloat(NTM_NEUTRON_NBT_KEY);
		}

		float newActivation = prevActivation * decay + (rad / stack.getCount());

		if (newActivation < 0.0001F) {
			if (prevActivation > 0) {
				CompoundTag nbt = customData != null ? customData.copyTag() : new CompoundTag();
				nbt.remove(NTM_NEUTRON_NBT_KEY);
				if (nbt.isEmpty()) {
					stack.remove(DataComponents.CUSTOM_DATA);
				} else {
					stack.set(DataComponents.CUSTOM_DATA, CustomData.of(nbt));
				}
				return true;
			}
		} else {
			if (Math.abs(newActivation - prevActivation) > 1e-6) {
				CompoundTag nbt = customData != null ? customData.copyTag() : new CompoundTag();
				nbt.putFloat(NTM_NEUTRON_NBT_KEY, newActivation);
				stack.set(DataComponents.CUSTOM_DATA, CustomData.of(nbt));
				return true;
			}
		}
		return false;
	}

	public static boolean isContaminated(ItemStack stack) {
		if (stack == null || stack.isEmpty()) return false;
		CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
		return customData != null && customData.copyTag().contains(NTM_NEUTRON_NBT_KEY);
	}

	public static String getPreffixFromRad(double rads) {
		String chunkPrefix = "";
		if (rads == 0)
			chunkPrefix += ChatFormatting.GREEN;
		else if (rads < 1)
			chunkPrefix += ChatFormatting.YELLOW;
		else if (rads < 10)
			chunkPrefix += ChatFormatting.GOLD;
		else if (rads < 100)
			chunkPrefix += ChatFormatting.RED;
		else if (rads < 1000)
			chunkPrefix += ChatFormatting.DARK_RED;
		else
			chunkPrefix += ChatFormatting.DARK_GRAY;

		return chunkPrefix;
	}

	public static double getRads(Entity e) {
		if (e instanceof LivingEntity entity)
			return HbmLivingProps.getRadiation(entity);
		return 0.0D;
	}

	public static boolean isRadImmune(Entity e) {
		if (e instanceof LivingEntity livingBase && HbmPotion.mutation != null && livingBase.hasEffect(HbmPotion.mutation))
			return true;
		Class<? extends Entity> entityClass = e.getClass();
		for (Class<?> radImmuneClass : immuneEntities) {
			if (radImmuneClass.isAssignableFrom(entityClass)) return true;
		}
		return false;
	}

	/// ASBESTOS ///
	public static void applyAsbestos(Entity e, int i, int dmg) {
		applyAsbestos(e, i, dmg, 1);
	}

	public static void applyAsbestos(Entity e, int i, int dmg, int chance) {
		if (!(e instanceof LivingEntity entity)) return;
		if (entity instanceof Player player && (player.isCreative() || player.isSpectator())) return;
		if (e.tickCount < 200) return;

		if (ArmorRegistry.hasProtection(entity, EquipmentSlot.HEAD, ArmorRegistry.HazardClass.PARTICLE_FINE)) {
			if (chance > 1) {
				if (entity.level().random.nextInt(chance) == 0) {
					ArmorUtil.damageGasMaskFilter(entity, 1);
				}
			} else {
				ArmorUtil.damageGasMaskFilter(entity, dmg);
			}
		} else {
			HbmLivingProps.incrementAsbestos(entity, i);
		}
	}

	/// COAL ///
	public static void applyCoal(Entity e, int i, int dmg, int chance) {
		if (!(e instanceof LivingEntity entity)) return;
		if (entity instanceof Player player && (player.isCreative() || player.isSpectator())) return;
		if (e.tickCount < 200) return;

		if (ArmorRegistry.hasProtection(entity, EquipmentSlot.HEAD, ArmorRegistry.HazardClass.PARTICLE_COARSE)) {
			if (chance > 1) {
				if (entity.level().random.nextInt(chance) == 0) {
					ArmorUtil.damageGasMaskFilter(entity, 1);
				}
			} else {
				ArmorUtil.damageGasMaskFilter(entity, dmg);
			}
		} else {
			HbmLivingProps.incrementBlackLung(entity, i);
		}
	}

	/// DIGAMMA ///
	public static void applyDigammaData(Entity e, double f) {
		if (!(e instanceof LivingEntity entity)) return;
		if (e instanceof Ocelot) return;
		if (entity instanceof Player player && (player.isCreative() || player.isSpectator())) return;
		if (e.tickCount < 200) return;
		if (HbmPotion.stability != null && entity.hasEffect(HbmPotion.stability)) return;

		if (!(entity instanceof Player player && ArmorUtil.checkForDigamma(player)))
			HbmLivingProps.incrementDigamma(entity, f);
	}

	public static double getDigamma(Entity e) {
		if (!(e instanceof LivingEntity entity)) return 0.0D;
		return HbmLivingProps.getDigamma(entity);
	}

	public static void radiate(Level world, double x, double y, double z, double range, float rad3d) {
		radiate(world, x, y, z, range, rad3d, 0, 0, 0, 0);
	}

	public static void radiate(Level world, double x, double y, double z, double range, float rad3d, float dig3d, float fire3d) {
		radiate(world, x, y, z, range, rad3d, dig3d, fire3d, 0, 0);
	}

	public static void radiate(Level world, double x, double y, double z, double range, float rad3d, float dig3d, float fire3d, float blast3d) {
		radiate(world, x, y, z, range, rad3d, dig3d, fire3d, blast3d, range);
	}

	public static void radiate(Level world, double x, double y, double z, double range, float rad3d, float dig3d, float fire3d, float blast3d, double blastRange) {
		List<Entity> entities = world.getEntitiesOfClass(Entity.class, new AABB(x - range, y - range, z - range, x + range, y + range, z + range));
		BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

		for (Entity e : entities) {
			if (isExplosionExempt(e)) continue;

			Vec3NT vec = Vec3NT.createVectorHelper(e.getX() - x, (e.getY() + e.getEyeHeight()) - y, e.getZ() - z);
			double len = vec.distanceTo(0, 0, 0);
			if (len > range) continue;
			vec = vec.normalize();
			double dmgLen = Math.max(len, range * 0.05D);

			float res = 0;
			for (int i = 1; i < len; i++) {
				int ix = (int) Math.floor(x + vec.x * i);
				int iy = (int) Math.floor(y + vec.y * i);
				int iz = (int) Math.floor(z + vec.z * i);
				pos.set(ix, iy, iz);
				res += world.getBlockState(pos).getBlock().getExplosionResistance();
			}

			if (res < 1) res = 1;
			boolean isLiving = e instanceof LivingEntity;

			if (isLiving && rad3d > 0) {
				float eRads = rad3d;
				eRads /= (float) (dmgLen * dmgLen * Math.sqrt(res));
				contaminate((LivingEntity) e, HazardType.RADIATION, ContaminationType.CREATIVE, eRads);
			}
			if (isLiving && dig3d > 0) {
				float eDig = dig3d;
				eDig /= (float) (dmgLen * dmgLen * dmgLen);
				contaminate((LivingEntity) e, HazardType.DIGAMMA, ContaminationType.DIGAMMA, eDig);
			}

			if (fire3d > 0.025) {
				float fireDmg = fire3d;
				fireDmg /= (float) (dmgLen * dmgLen * res * res);
				if (fireDmg > 0.025) {
					if (fireDmg > 0.1 && e instanceof Player p) {
						if (p.getMainHandItem().getItem() == ModItems.marshmallow && p.getRandom().nextInt((int) Math.max(1, len)) == 0) {
							p.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(ModItems.marshmallow_roasted));
						}
						if (p.getOffhandItem().getItem() == ModItems.marshmallow && p.getRandom().nextInt((int) Math.max(1, len)) == 0) {
							p.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(ModItems.marshmallow_roasted));
						}
					}
					if (!isFireExempt(e)) {
						e.hurt(e.damageSources().inFire(), fireDmg);
						e.setRemainingFireTicks(100);
					}
				}
			}

			if (len < blastRange && blast3d > 0.025) {
				float blastDmg = blast3d;
				blastDmg /= (float) (dmgLen * dmgLen * res);
				if (blastDmg > 0.025) {
					e.hurt(e.damageSources().explosion(null, null), blastDmg);
				}
				e.setDeltaMovement(e.getDeltaMovement().add(vec.x * 0.005D * blastDmg, vec.y * 0.005D * blastDmg, vec.z * 0.005D * blastDmg));
			}
		}
	}

	private static boolean isFireExempt(Entity e) {
		return e instanceof ArmorStand || e instanceof Boat || e instanceof HangingEntity;
	}

	private static boolean isExplosionExempt(Entity e) {
		if (e instanceof Ocelot) {
			return true;
		}
		return e instanceof Player && (((Player) e).isCreative() || ((Player) e).isSpectator());
	}

	public enum HazardType {
		MONOXIDE,
		RADIATION,
		NEUTRON,
		DIGAMMA
	}

	public enum ContaminationType {
		FARADAY,			// preventable by metal armor
		HAZMAT,				// preventable by hazmat
		HAZMAT2,			// preventable by heavy hazmat
		DIGAMMA,			// preventable by fau armor or stability
		DIGAMMA2,			// preventable by robes
		CREATIVE,			// preventable by creative mode, for rad calculation armor piece bonuses still apply
		RAD_BYPASS,			// same as creative but will not apply radiation resistance calculation
		NONE				// not preventable
	}

	public static boolean contaminate(LivingEntity entity, HazardType hazard, ContaminationType cont, double amount) {
		if (hazard == HazardType.RADIATION) {
			double radEnv = HbmLivingProps.getRadEnv(entity);
			HbmLivingProps.setRadEnv(entity, radEnv + amount);
		}

		if (entity instanceof Player player) {
			if (player.isSpectator()) return false;
			switch (cont) {
				case FARADAY:
					if (ArmorUtil.checkForFaraday(player)) return false;
					break;
				case HAZMAT:
					if (ArmorUtil.checkForHazmat(player)) return false;
					break;
				case HAZMAT2:
					if (ArmorUtil.checkForHaz2(player)) return false;
					break;
				case DIGAMMA:
					if (ArmorUtil.checkForDigamma(player)) return false;
					break;
				case DIGAMMA2:
					break;
			}

			if (player.isCreative() && cont != ContaminationType.NONE) {
				if (hazard == HazardType.NEUTRON)
					HbmLivingProps.setNeutron(entity, amount);
				return false;
			}

			if (player.tickCount < 200)
				return false;
		}

		if ((hazard == HazardType.RADIATION || hazard == HazardType.NEUTRON) && isRadImmune(entity)) {
			return false;
		}

		switch (hazard) {
			case MONOXIDE -> entity.hurt(entity.damageSources().generic(), (float) amount);
			case RADIATION -> HbmLivingProps.incrementRadiation(entity, amount * (cont == ContaminationType.RAD_BYPASS ? 1D : calculateRadiationMod(entity)));
			case NEUTRON -> {
				HbmLivingProps.incrementRadiation(entity, amount * (cont == ContaminationType.RAD_BYPASS ? 1D : calculateRadiationMod(entity)));
				HbmLivingProps.setNeutron(entity, amount);
			}
			case DIGAMMA -> applyDigammaData(entity, amount);
		}

		return true;
	}
}
