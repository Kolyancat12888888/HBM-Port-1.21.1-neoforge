package com.hbm.potion;

import com.hbm.capability.HbmLivingProps;
import com.hbm.main.MainRegistry;
import com.hbm.util.ContaminationUtil;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class HbmPotion {

	public static final DeferredRegister<MobEffect> MOB_EFFECTS =
			DeferredRegister.create(Registries.MOB_EFFECT, MainRegistry.MODID);

	public static class HbmMobEffect extends MobEffect {
		public HbmMobEffect(MobEffectCategory category, int color) {
			super(category, color);
		}
	}

	public static class RadawayEffect extends MobEffect {
		public RadawayEffect() {
			super(MobEffectCategory.BENEFICIAL, 0xFFE400);
		}

		@Override
		public boolean applyEffectTick(LivingEntity entity, int amplifier) {
			if (!entity.level().isClientSide()) {
				HbmLivingProps.incrementRadiation(entity, -(amplifier + 1) * 0.05F);
			}
			return true;
		}

		@Override
		public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
			return true;
		}
	}

	public static class RadiationEffect extends MobEffect {
		public RadiationEffect() {
			super(MobEffectCategory.HARMFUL, 8700200);
		}

		@Override
		public boolean applyEffectTick(LivingEntity entity, int amplifier) {
			if (!entity.level().isClientSide()) {
				ContaminationUtil.contaminate(entity, ContaminationUtil.HazardType.RADIATION, ContaminationUtil.ContaminationType.CREATIVE, (amplifier + 1.0F) * 0.05F);
			}
			return true;
		}

		@Override
		public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
			return true;
		}
	}

	public static class LeadEffect extends MobEffect {
		public LeadEffect() {
			super(MobEffectCategory.HARMFUL, 0x767682);
		}

		@Override
		public boolean applyEffectTick(LivingEntity entity, int amplifier) {
			if (!entity.level().isClientSide()) {
				entity.hurt(entity.damageSources().generic(), amplifier + 1);
			}
			return true;
		}

		@Override
		public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
			return duration % 60 == 0;
		}
	}

	public static class TaintEffect extends MobEffect {
		public TaintEffect() {
			super(MobEffectCategory.HARMFUL, 8388736);
		}

		@Override
		public boolean applyEffectTick(LivingEntity entity, int amplifier) {
			if (!entity.level().isClientSide() && entity.getRandom().nextInt(80) == 0) {
				entity.hurt(entity.damageSources().generic(), amplifier + 1);
			}
			return true;
		}

		@Override
		public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
			return duration % 2 == 0;
		}
	}

	public static final DeferredHolder<MobEffect, MobEffect> TAINT = MOB_EFFECTS.register("taint", TaintEffect::new);
	public static final DeferredHolder<MobEffect, MobEffect> RADIATION = MOB_EFFECTS.register("radiation", RadiationEffect::new);
	public static final DeferredHolder<MobEffect, MobEffect> BANG = MOB_EFFECTS.register("bang", () -> new HbmMobEffect(MobEffectCategory.HARMFUL, 1118481));
	public static final DeferredHolder<MobEffect, MobEffect> MUTATION = MOB_EFFECTS.register("mutation", () -> new HbmMobEffect(MobEffectCategory.BENEFICIAL, 0xFF8132));
	public static final DeferredHolder<MobEffect, MobEffect> RADX = MOB_EFFECTS.register("radx", () -> new HbmMobEffect(MobEffectCategory.BENEFICIAL, 0x225900));
	public static final DeferredHolder<MobEffect, MobEffect> LEAD = MOB_EFFECTS.register("lead", LeadEffect::new);
	public static final DeferredHolder<MobEffect, MobEffect> RADAWAY = MOB_EFFECTS.register("radaway", RadawayEffect::new);
	public static final DeferredHolder<MobEffect, MobEffect> TELEKINESIS = MOB_EFFECTS.register("telekinesis", () -> new HbmMobEffect(MobEffectCategory.HARMFUL, 0x00F3FF));
	public static final DeferredHolder<MobEffect, MobEffect> PHOSPHORUS = MOB_EFFECTS.register("phosphorus", () -> new HbmMobEffect(MobEffectCategory.HARMFUL, 0xFF3A00));
	public static final DeferredHolder<MobEffect, MobEffect> STABILITY = MOB_EFFECTS.register("stability", () -> new HbmMobEffect(MobEffectCategory.BENEFICIAL, 0xD0D0D0));
	public static final DeferredHolder<MobEffect, MobEffect> POTIONSICKNESS = MOB_EFFECTS.register("potionsickness", () -> new HbmMobEffect(MobEffectCategory.BENEFICIAL, 0xFF8080));
	public static final DeferredHolder<MobEffect, MobEffect> DEATH = MOB_EFFECTS.register("death", () -> new HbmMobEffect(MobEffectCategory.BENEFICIAL, 0x111111));

	public static Holder<MobEffect> taint;
	public static Holder<MobEffect> radiation;
	public static Holder<MobEffect> bang;
	public static Holder<MobEffect> mutation;
	public static Holder<MobEffect> radx;
	public static Holder<MobEffect> lead;
	public static Holder<MobEffect> radaway;
	public static Holder<MobEffect> telekinesis;
	public static Holder<MobEffect> phosphorus;
	public static Holder<MobEffect> stability;
	public static Holder<MobEffect> potionsickness;
	public static Holder<MobEffect> death;

	public static void register(IEventBus bus) {
		MOB_EFFECTS.register(bus);
	}

	public static void initAccessors() {
		taint = TAINT;
		radiation = RADIATION;
		bang = BANG;
		mutation = MUTATION;
		radx = RADX;
		lead = LEAD;
		radaway = RADAWAY;
		telekinesis = TELEKINESIS;
		phosphorus = PHOSPHORUS;
		stability = STABILITY;
		potionsickness = POTIONSICKNESS;
		death = DEATH;
	}
}
