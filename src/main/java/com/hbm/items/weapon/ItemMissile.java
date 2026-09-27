package com.hbm.items.weapon;

import com.hbm.handler.MissileStruct;
import com.hbm.items.ItemBase;
import com.hbm.items.ModItems;
import com.hbm.util.I18nUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;

import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

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

	/**
	 * == Chips ==
	 * [0]: inaccuracy
	 *
	 * == Warheads ==
	 * [0]: type
	 * [1]: strength/radius/cluster count
	 * [2]: weight
	 *
	 * == Fuselages ==
	 * [0]: type
	 * [1]: tank size
	 *
	 * == Stability ==
	 * [0]: inaccuracy mod
	 *
	 * == Thrusters ===
	 * [0]: type
	 * [1]: consumption
	 * [2]: lift strength
	 */
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
		//for chips
		ANY,
		//for missile tips and thrusters
		NONE,
		//regular sizes, 1.0m, 1.5m and 2.0m
		SIZE_10(1.0),
		SIZE_15(1.5),
		SIZE_20(2.0),
		// Space-grade
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
		MIRV(null, com.hbm.entity.missile.EntityMissileCustom::mirvSplit),
		APOLLO,
		SATELLITE,

		CUSTOM0, CUSTOM1, CUSTOM2, CUSTOM3, CUSTOM4, CUSTOM5, CUSTOM6, CUSTOM7, CUSTOM8, CUSTOM9;

		public Consumer<com.hbm.entity.missile.EntityMissileCustom> impactCustom = null;
		public Consumer<com.hbm.entity.missile.EntityMissileCustom> updateCustom = null;
		public String labelCustom = null;

		WarheadType() {
		}

		WarheadType(Consumer<com.hbm.entity.missile.EntityMissileCustom> onImpact, Consumer<com.hbm.entity.missile.EntityMissileCustom> onUpdate) {
			impactCustom = onImpact;
			updateCustom = onUpdate;
		}
	}

	public enum FuelType {
		ANY, // Used by space-grade fuselages
		KEROSENE,
		SOLID,
		HYDROGEN,
		XENON,
		BALEFIRE,
		HYDRAZINE,
		METHALOX,
		KEROLOX // oxygen rather than peroxide
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
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> list, TooltipFlag flagIn) {
		if (title != null)
			list.add(Component.literal(ChatFormatting.DARK_PURPLE + "\"" + title + "\""));

		try {
			switch (type) {
				case CHIP ->
						list.add(Component.literal(ChatFormatting.BOLD + I18nUtil.resolveKey("desc.inaccuracy") + " " + ChatFormatting.GRAY + (Float) attributes[0] * 100 + "%"));
				case WARHEAD -> {
					list.add(Component.literal(ChatFormatting.BOLD + I18nUtil.resolveKey("desc.size") + " " + ChatFormatting.GRAY + getSize(bottom)));
					list.add(Component.literal(ChatFormatting.BOLD + I18nUtil.resolveKey("desc.type") + " " + ChatFormatting.GRAY + getWarhead((WarheadType) attributes[0])));
					if (attributes[0] != WarheadType.APOLLO && attributes[0] != WarheadType.SATELLITE)
						list.add(Component.literal(ChatFormatting.BOLD + I18nUtil.resolveKey("desc.strength") + " " + ChatFormatting.RED + (Float) attributes[1]));
					list.add(Component.literal(ChatFormatting.BOLD + I18nUtil.resolveKey("desc.weight") + " " + ChatFormatting.GRAY + (Float) attributes[2] + "t"));
				}
				case FUSELAGE -> {
					list.add(Component.literal(ChatFormatting.BOLD + I18nUtil.resolveKey("desc.topsize") + " " + ChatFormatting.GRAY + getSize(top)));
					list.add(Component.literal(ChatFormatting.BOLD + I18nUtil.resolveKey("desc.botsize") + " " + ChatFormatting.GRAY + getSize(bottom)));
					list.add(Component.literal(ChatFormatting.BOLD + I18nUtil.resolveKey("desc.fueltype") + " " + ChatFormatting.GRAY + getFuel((FuelType) attributes[0])));
					list.add(Component.literal(ChatFormatting.BOLD + I18nUtil.resolveKey("desc.fuelamnt") + " " + ChatFormatting.GRAY + (Float) attributes[1] + "l"));
					list.add(Component.literal(ChatFormatting.BOLD + I18nUtil.resolveKey("desc.mass", mass)));
				}
				case FINS -> {
					list.add(Component.literal(ChatFormatting.BOLD + I18nUtil.resolveKey("desc.size") + " " + ChatFormatting.GRAY + getSize(top)));
					list.add(Component.literal(ChatFormatting.BOLD + I18nUtil.resolveKey("desc.inaccuracy") + " " + ChatFormatting.GRAY + (Float) attributes[0] * 100 + "%"));
				}
				case THRUSTER -> {
					list.add(Component.literal(ChatFormatting.BOLD + I18nUtil.resolveKey("desc.size") + " " + ChatFormatting.GRAY + getSize(top)));
					list.add(Component.literal(ChatFormatting.BOLD + I18nUtil.resolveKey("desc.fuelamnt") + " " + ChatFormatting.GRAY + getFuel((FuelType) attributes[0])));
					if (attributes.length > 3 && attributes[3] != null) list.add(Component.literal(ChatFormatting.BOLD + "Thrust: " + ChatFormatting.GRAY + attributes[3] + "N"));
					if (attributes.length > 4 && attributes[4] != null) list.add(Component.literal(ChatFormatting.BOLD + "ISP: " + ChatFormatting.GRAY + attributes[4] + "s"));
				}
			}
		} catch (Exception ex) {
			list.add(Component.literal("### I AM ERROR ###"));
		}

		if (type != PartType.CHIP)
			list.add(Component.literal(ChatFormatting.BOLD + I18nUtil.resolveKey("desc.health") + " " + ChatFormatting.GREEN + health + "HP"));

		if (this.rarity != null)
			list.add(Component.literal(ChatFormatting.BOLD + I18nUtil.resolveKey("desc.rarity") + " " + ChatFormatting.GRAY + I18nUtil.resolveKey(this.rarity.name)));
		if (author != null)
			list.add(Component.literal(ChatFormatting.WHITE + "  " + I18nUtil.resolveKey("desc.author") + " " + author));
		if (witty != null)
			list.add(Component.literal(ChatFormatting.GOLD + "   " + ChatFormatting.ITALIC + "\"" + witty + "\""));
	}

	public String getSize(PartSize size) {
		if (size == null) return I18nUtil.resolveKey("desc.none");
		return switch (size) {
			case ANY -> I18nUtil.resolveKey("desc.any");
			case SIZE_10 -> "§e1.0m";
			case SIZE_15 -> "§61.5m";
			case SIZE_20 -> "§c2.0m";
			default -> I18nUtil.resolveKey("desc.none");
		};
	}

	public String getWarhead(WarheadType type) {
		if (type == null) return ChatFormatting.BOLD + I18nUtil.resolveKey("desc.na");
		if (type.labelCustom != null) return type.labelCustom;

		return switch (type) {
			case HE -> ChatFormatting.YELLOW + I18nUtil.resolveKey("warhead.he");
			case INC -> ChatFormatting.GOLD + I18nUtil.resolveKey("warhead.inc");
			case CLUSTER -> ChatFormatting.GRAY + I18nUtil.resolveKey("warhead.cluster");
			case BUSTER -> ChatFormatting.WHITE + I18nUtil.resolveKey("warhead.buster");
			case NUCLEAR -> ChatFormatting.DARK_GREEN + I18nUtil.resolveKey("warhead.nuclear");
			case TX -> ChatFormatting.DARK_PURPLE + I18nUtil.resolveKey("warhead.tx");
			case N2 -> ChatFormatting.RED + I18nUtil.resolveKey("warhead.n2");
			case BALEFIRE -> ChatFormatting.GREEN + I18nUtil.resolveKey("warhead.balefire");
			case SCHRAB -> ChatFormatting.AQUA + I18nUtil.resolveKey("warhead.schrab");
			case TAINT -> ChatFormatting.DARK_PURPLE + I18nUtil.resolveKey("warhead.taint");
			case CLOUD -> ChatFormatting.LIGHT_PURPLE + I18nUtil.resolveKey("warhead.cloud");
			case TURBINE -> (System.currentTimeMillis() % 1000 < 500 ? ChatFormatting.RED : ChatFormatting.LIGHT_PURPLE) + I18nUtil.resolveKey("warhead.turbine");
			case VOLCANO -> ChatFormatting.DARK_RED + I18nUtil.resolveKey("warhead.volcano");
			case MIRV -> ChatFormatting.DARK_PURPLE + I18nUtil.resolveKey("warhead.mirv");
			case APOLLO -> (System.currentTimeMillis() % 1000 < 500 ? ChatFormatting.GOLD : ChatFormatting.RED) + I18nUtil.resolveKey("warhead.capsule");
			case SATELLITE -> (System.currentTimeMillis() % 1000 < 500 ? ChatFormatting.GOLD : ChatFormatting.RED) + I18nUtil.resolveKey("warhead.satellite");
			default -> ChatFormatting.BOLD + I18nUtil.resolveKey("desc.na");
		};
	}

	public String getFuel(FuelType type) {
		if (type == null) return "None";
		return switch (type) {
			case ANY -> ChatFormatting.GRAY + "Any Liquid Fuel";
			case KEROSENE -> ChatFormatting.LIGHT_PURPLE + I18nUtil.resolveKey("fuel.kerosene");
			case METHALOX -> ChatFormatting.YELLOW + "Natural Gas / Oxygen";
			case KEROLOX -> ChatFormatting.LIGHT_PURPLE + "Kerosene / Oxygen";
			case SOLID -> ChatFormatting.GOLD + I18nUtil.resolveKey("fuel.solid");
			case HYDROGEN -> ChatFormatting.DARK_AQUA + I18nUtil.resolveKey("fuel.hydrogen");
			case XENON -> ChatFormatting.DARK_PURPLE + I18nUtil.resolveKey("fuel.xenon");
			case BALEFIRE -> ChatFormatting.GREEN + I18nUtil.resolveKey("fuel.balefire");
			case HYDRAZINE -> ChatFormatting.AQUA + "Hydrazine";
		};
	}

	public float getTankSize() {
		if (type != PartType.FUSELAGE || attributes == null || attributes.length < 2) return 0;
		if (attributes[1] instanceof Integer i) return i;
		if (attributes[1] instanceof Float f) return f;
		return 0;
	}

	public ItemMissile copyPart() {
		ItemMissile part = new ItemMissile();
		part.type = this.type;
		part.top = this.top;
		part.bottom = this.bottom;
		part.health = this.health;
		part.attributes = this.attributes;
		part.mass = this.mass;
		return part;
	}

	public ItemMissile copy() {
		return copyPart();
	}

	public ItemMissile setAuthor(String author) {
		this.author = author;
		return this;
	}

	public ItemMissile setTitle(String title) {
		this.title = title;
		return this;
	}

	public ItemMissile setWittyText(String witty) {
		this.witty = witty;
		return this;
	}

	public ItemMissile setHealth(float health) {
		this.health = health;
		return this;
	}

	public ItemMissile setRarity(Rarity rarity) {
		this.rarity = rarity;
		return this;
	}
}
