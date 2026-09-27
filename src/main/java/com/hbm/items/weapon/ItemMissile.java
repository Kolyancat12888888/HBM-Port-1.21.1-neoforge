package com.hbm.items.weapon;

import com.hbm.items.ItemBase;
import com.hbm.util.I18nUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.HashMap;
import java.util.List;

public class ItemMissile extends ItemBase {

	public PartType type;
	public PartSize top;
	public PartSize bottom;
	public Rarity rarity;
	public float health;
	public int mass = 0;
	protected String title;
	protected String author;
	protected String witty;

	public static HashMap<Integer, ItemMissile> parts = new HashMap<>();

	public Object[] attributes;

	public ItemMissile(Properties properties) {
		super(properties.stacksTo(1));
	}

	public ItemMissile() {
		this(new Properties());
	}

	public enum PartType {
		CHIP,
		WARHEAD,
		FUSELAGE,
		FINS,
		THRUSTER
	}

	public enum PartSize {
		ANY,
		NONE,
		SIZE_10(1.0),
		SIZE_15(1.5),
		SIZE_20(2.0),
		SIZE_25(2.5),
		SIZE_30(3.0);

		PartSize() {
			this.radius = 0;
		}

		PartSize(double radius) {
			this.radius = radius;
		}

		public double radius;
	}

	public enum WarheadType {
		HE,
		INC,
		BUSTER,
		CLUSTER,
		NUCLEAR,
		TX,
		N2,
		BALEFIRE,
		SCHRAB,
		TAINT,
		CLOUD,
		VOLCANO,
		TURBINE,
		MIRV,
		APOLLO,
		SATELLITE,
		CUSTOM0, CUSTOM1, CUSTOM2, CUSTOM3, CUSTOM4, CUSTOM5, CUSTOM6, CUSTOM7, CUSTOM8, CUSTOM9
	}

	public enum FuelType {
		ANY,
		KEROSENE,
		SOLID,
		HYDROGEN,
		XENON,
		BALEFIRE,
		HYDRAZINE,
		METHALOX,
		KEROLOX
	}

	public enum Rarity {
		COMMON("rarity.common"),
		UNCOMMON("rarity.uncommon"),
		RARE("rarity.rare"),
		EPIC("rarity.epic"),
		LEGENDARY("rarity.legendary"),
		SEWS_CLOTHES_AND_SUCKS_HORSE_COCK("rarity.strange");

		public final String name;

		Rarity(String name) {
			this.name = name;
		}
	}

	public ItemMissile makeChip(float inaccuracy) {
		this.type = PartType.CHIP;
		this.top = PartSize.ANY;
		this.bottom = PartSize.ANY;
		this.attributes = new Object[] { inaccuracy };
		parts.put(this.hashCode(), this);
		return this;
	}

	public ItemMissile makeWarhead(WarheadType type, float punch, float weight, PartSize size) {
		this.type = PartType.WARHEAD;
		this.top = PartSize.NONE;
		this.bottom = size;
		this.attributes = new Object[] { type, punch, weight };
		parts.put(this.hashCode(), this);
		return this;
	}

	public ItemMissile makeFuselage(FuelType type, float fuel, int mass, PartSize top, PartSize bottom) {
		this.type = PartType.FUSELAGE;
		this.top = top;
		this.bottom = bottom;
		this.mass = mass;
		attributes = new Object[] { type, fuel };
		parts.put(this.hashCode(), this);
		return this;
	}

	public ItemMissile makeStability(float inaccuracy, PartSize size) {
		this.type = PartType.FINS;
		this.top = size;
		this.bottom = size;
		this.attributes = new Object[] { inaccuracy };
		parts.put(this.hashCode(), this);
		return this;
	}

	public ItemMissile makeThruster(FuelType type, float consumption, float lift, PartSize size) {
		this.type = PartType.THRUSTER;
		this.top = size;
		this.bottom = PartSize.NONE;
		this.attributes = new Object[] { type, consumption, lift };
		parts.put(this.hashCode(), this);
		return this;
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
		if (title != null) {
			tooltipComponents.add(Component.literal(ChatFormatting.DARK_PURPLE + "\"" + title + "\""));
		}

		if (type != null && attributes != null) {
			switch (type) {
				case CHIP -> tooltipComponents.add(Component.literal(ChatFormatting.BOLD + I18nUtil.resolveKey("desc.inaccuracy") + " " + ChatFormatting.GRAY + (Float) attributes[0] * 100 + "%"));
				case WARHEAD -> {
					tooltipComponents.add(Component.literal(ChatFormatting.BOLD + I18nUtil.resolveKey("desc.size") + " " + ChatFormatting.GRAY + getSize(bottom)));
					tooltipComponents.add(Component.literal(ChatFormatting.BOLD + I18nUtil.resolveKey("desc.type") + " " + ChatFormatting.GRAY + getWarhead((WarheadType) attributes[0])));
					if (attributes[0] != WarheadType.APOLLO && attributes[0] != WarheadType.SATELLITE)
						tooltipComponents.add(Component.literal(ChatFormatting.BOLD + I18nUtil.resolveKey("desc.strength") + " " + ChatFormatting.RED + (Float) attributes[1]));
					tooltipComponents.add(Component.literal(ChatFormatting.BOLD + I18nUtil.resolveKey("desc.weight") + " " + ChatFormatting.GRAY + (Float) attributes[2] + "t"));
				}
				case FUSELAGE -> {
					tooltipComponents.add(Component.literal(ChatFormatting.BOLD + I18nUtil.resolveKey("desc.topsize") + " " + ChatFormatting.GRAY + getSize(top)));
					tooltipComponents.add(Component.literal(ChatFormatting.BOLD + I18nUtil.resolveKey("desc.botsize") + " " + ChatFormatting.GRAY + getSize(bottom)));
					tooltipComponents.add(Component.literal(ChatFormatting.BOLD + I18nUtil.resolveKey("desc.fueltype") + " " + ChatFormatting.GRAY + getFuel((FuelType) attributes[0])));
					tooltipComponents.add(Component.literal(ChatFormatting.BOLD + I18nUtil.resolveKey("desc.fuelamnt") + " " + ChatFormatting.GRAY + (Float) attributes[1] + "l"));
					tooltipComponents.add(Component.literal(ChatFormatting.BOLD + I18nUtil.resolveKey("desc.mass", mass)));
				}
				case FINS -> {
					tooltipComponents.add(Component.literal(ChatFormatting.BOLD + I18nUtil.resolveKey("desc.size") + " " + ChatFormatting.GRAY + getSize(bottom)));
					tooltipComponents.add(Component.literal(ChatFormatting.BOLD + I18nUtil.resolveKey("desc.inaccuracyMod") + " " + ChatFormatting.GRAY + (Float) attributes[0] * 100 + "%"));
				}
				case THRUSTER -> {
					tooltipComponents.add(Component.literal(ChatFormatting.BOLD + I18nUtil.resolveKey("desc.size") + " " + ChatFormatting.GRAY + getSize(top)));
					tooltipComponents.add(Component.literal(ChatFormatting.BOLD + I18nUtil.resolveKey("desc.fueltype") + " " + ChatFormatting.GRAY + getFuel((FuelType) attributes[0])));
					tooltipComponents.add(Component.literal(ChatFormatting.BOLD + I18nUtil.resolveKey("desc.consumption") + " " + ChatFormatting.GRAY + (Float) attributes[1] + "l/t"));
					tooltipComponents.add(Component.literal(ChatFormatting.BOLD + I18nUtil.resolveKey("desc.thrust") + " " + ChatFormatting.GRAY + (Float) attributes[2] + "t"));
				}
			}
		}
	}

	public static String getSize(PartSize size) {
		if (size == null) return "Unknown";
		return switch (size) {
			case SIZE_10 -> "1.0m";
			case SIZE_15 -> "1.5m";
			case SIZE_20 -> "2.0m";
			case SIZE_25 -> "2.5m";
			case SIZE_30 -> "3.0m";
			default -> "Any";
		};
	}

	public static String getWarhead(WarheadType type) {
		return type != null ? type.name() : "None";
	}

	public static String getFuel(FuelType type) {
		return type != null ? type.name() : "None";
	}
}
