package com.hbm.items.weapon;

import com.hbm.items.ItemBase;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ItemMissileStandard extends ItemBase {

	public final MissileFormFactor formFactor;
	public final MissileTier tier;
	public final MissileFuel fuel;

	public int fuelCap;
	public boolean launchable = true;

	public ItemMissileStandard(Properties properties, MissileFormFactor form, MissileTier tier) {
		this(properties, form, tier, form.defaultFuel);
	}

	public ItemMissileStandard(Properties properties, MissileFormFactor form, MissileTier tier, MissileFuel fuel) {
		super(properties.stacksTo(1));
		this.formFactor = form;
		this.tier = tier;
		this.fuel = fuel;
		this.setFuelCap(this.fuel.defaultCap);
	}

	public ItemMissileStandard notLaunchable() {
		this.launchable = false;
		return this;
	}

	public ItemMissileStandard setFuelCap(int fuelCap) {
		this.fuelCap = fuelCap;
		return this;
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> list, TooltipFlag flagIn) {
		list.add(Component.literal(ChatFormatting.ITALIC + this.tier.display));

		if (!this.launchable) {
			list.add(Component.literal(ChatFormatting.RED + "Not launchable!"));
		} else {
			list.add(Component.literal("Fuel: " + this.fuel.display));
			if (this.fuelCap > 0) {
				list.add(Component.literal("Fuel capacity: " + this.fuelCap + "mB"));
			}
			super.appendHoverText(stack, context, list, flagIn);
		}
	}

	public enum MissileFormFactor {
		ABM(MissileFuel.SOLID),
		MICRO(MissileFuel.SOLID),
		V2(MissileFuel.ETHANOL_PEROXIDE),
		STRONG(MissileFuel.KEROSENE_PEROXIDE),
		HUGE(MissileFuel.KEROSENE_LOXY),
		ATLAS(MissileFuel.JETFUEL_LOXY),
		OTHER(MissileFuel.KEROSENE_PEROXIDE);

		private final MissileFuel defaultFuel;

		MissileFormFactor(MissileFuel defaultFuel) {
			this.defaultFuel = defaultFuel;
		}
	}

	public enum MissileTier {
		TIER0("Tier 0"),
		TIER1("Tier 1"),
		TIER2("Tier 2"),
		TIER3("Tier 3"),
		TIER4("Tier 4");

		public final String display;

		MissileTier(String display) {
			this.display = display;
		}
	}

	public enum MissileFuel {
		SOLID(ChatFormatting.GOLD + "Solid Fuel (pre-fueled)", 0),
		ETHANOL_PEROXIDE(ChatFormatting.AQUA + "Ethanol / Hydrogen Peroxide", 4_000),
		KEROSENE_PEROXIDE(ChatFormatting.BLUE + "Kerosene / Hydrogen Peroxide", 8_000),
		KEROSENE_LOXY(ChatFormatting.LIGHT_PURPLE + "Kerosene / Liquid Oxygen", 12_000),
		JETFUEL_LOXY(ChatFormatting.RED + "Jet Fuel / Liquid Oxygen", 16_000);

		public final String display;
		public final int defaultCap;

		MissileFuel(String display, int defaultCap) {
			this.display = display;
			this.defaultCap = defaultCap;
		}
	}
}
