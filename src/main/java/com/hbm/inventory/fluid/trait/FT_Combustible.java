package com.hbm.inventory.fluid.trait;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.util.BobMathUtil;
import com.hbm.util.I18nUtil;
import net.minecraft.ChatFormatting;

import java.io.IOException;
import java.util.List;

public class FT_Combustible extends FluidTrait {
	
	protected FuelGrade fuelGrade;
	protected long combustionEnergy;
	
	public FT_Combustible() { }
	
	public FT_Combustible(FuelGrade grade, long energy) {
		this.fuelGrade = grade;
		this.combustionEnergy = energy;
	}
	
	@Override
	public void addInfo(List<String> info) {
		super.addInfo(info);
		info.add(ChatFormatting.GOLD + "[" + I18nUtil.resolveKey("trait.combustable") + "]");
		
		if (combustionEnergy > 0) {
			info.add(I18nUtil.resolveKey("trait.combustable.desc") + ": " + BobMathUtil.getShortNumber(combustionEnergy));
			info.add(I18nUtil.resolveKey("trait.combustable.desc2") + ": " + I18nUtil.resolveKey(this.fuelGrade.getGrade()));
		}
	}
	
	public long getCombustionEnergy() {
		return this.combustionEnergy;
	}
	
	public FuelGrade getGrade() {
		return this.fuelGrade;
	}
	
	public enum FuelGrade {
		LOW("trait.combustable.low"),
		MEDIUM("trait.combustable.medium"),
		HIGH("trait.combustable.high"),
		AERO("trait.combustable.avi"),
		GAS("trait.combustable.gas");

		public static final FuelGrade[] VALUES = values();
		private final String grade;
		
		FuelGrade(String grade) {
			this.grade = grade;
		}
		
		public String getGrade() {
			return this.grade;
		}
	}

	@Override
	public void serializeJSON(JsonWriter writer) throws IOException {
		writer.name("energy").value(combustionEnergy);
		writer.name("grade").value(fuelGrade.name());
	}
	
	@Override
	public void deserializeJSON(JsonObject obj) {
		this.combustionEnergy = obj.get("energy").getAsLong();
		this.fuelGrade = FuelGrade.valueOf(obj.get("grade").getAsString());
	}
}
