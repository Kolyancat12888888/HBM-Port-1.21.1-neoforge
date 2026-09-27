package com.hbm.items.machine;

import com.hbm.items.ItemBase;
import com.hbm.util.I18nUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ItemFELCrystal extends ItemBase {

	public EnumWavelengths wavelength = EnumWavelengths.NULL;

	public ItemFELCrystal(Properties properties, EnumWavelengths wavelength) {
		super(properties.stacksTo(1));
		this.wavelength = wavelength;
	}

	public ItemFELCrystal(EnumWavelengths wavelength) {
		this(new Properties(), wavelength);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
		String desc = this.getDescriptionId() + ".desc";
		tooltipComponents.add(Component.literal(I18nUtil.resolveKey(desc)));
		tooltipComponents.add(Component.literal(wavelength.textColor + I18nUtil.resolveKey(wavelength.name) + " - " + wavelength.textColor + I18nUtil.resolveKey(this.wavelength.wavelengthRange)));
	}

	public enum EnumWavelengths {
		NULL("la creatura", "6 dollar", 0x010101, 0x010101, ChatFormatting.WHITE),
		IR("wavelengths.name.ir", "wavelengths.waveRange.ir", 0xBB1010, 0xCC4040, ChatFormatting.RED),
		VISIBLE("wavelengths.name.visible", "wavelengths.waveRange.visible", 0, 0, ChatFormatting.GREEN),
		UV("wavelengths.name.uv", "wavelengths.waveRange.uv", 0x0A1FC4, 0x00EFFF, ChatFormatting.AQUA),
		GAMMA("wavelengths.name.gamma", "wavelengths.waveRange.gamma", 0x150560, 0xEF00FF, ChatFormatting.LIGHT_PURPLE),
		DRX("wavelengths.name.drx", "wavelengths.waveRange.drx", 0xFF0000, 0xFF0000, ChatFormatting.DARK_RED);

		public final String name;
		public final String wavelengthRange;
		public final int renderedBeamColor;
		public final int guiColor;
		public final ChatFormatting textColor;

		EnumWavelengths(String name, String wavelength, int color, int guiColor, ChatFormatting textColor) {
			this.name = name;
			this.wavelengthRange = wavelength;
			this.renderedBeamColor = color;
			this.guiColor = guiColor;
			this.textColor = textColor;
		}
	}
}
