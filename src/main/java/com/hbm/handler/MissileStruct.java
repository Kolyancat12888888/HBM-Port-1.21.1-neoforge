package com.hbm.handler;

import com.hbm.items.weapon.ItemMissile;
import com.hbm.items.weapon.ItemMissile.PartType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class MissileStruct {

	public ItemMissile warhead;
	public ItemMissile fuselage;
	public ItemMissile fins;
	public ItemMissile thruster;

	public MissileStruct() { }

	public MissileStruct(ItemStack w, ItemStack f, ItemStack s, ItemStack t) {
		if(w != null && w.getItem() instanceof ItemMissile mw)
			warhead = mw;
		if(f != null && f.getItem() instanceof ItemMissile mf)
			fuselage = mf;
		if(s != null && s.getItem() instanceof ItemMissile ms)
			fins = ms;
		if(t != null && t.getItem() instanceof ItemMissile mt)
			thruster = mt;
	}

	public MissileStruct(Item w, Item f, Item s, Item t) {
		if(w instanceof ItemMissile mw)
			warhead = mw;
		if(f instanceof ItemMissile mf)
			fuselage = mf;
		if(s instanceof ItemMissile ms)
			fins = ms;
		if(t instanceof ItemMissile mt)
			thruster = mt;
	}

	public void writeToByteBuffer(FriendlyByteBuf buf) {
		if(warhead != null && warhead.type == PartType.WARHEAD)
			buf.writeResourceLocation(BuiltInRegistries.ITEM.getKey(warhead));
		else
			buf.writeResourceLocation(ResourceLocation.fromNamespaceAndPath("minecraft", "air"));

		if(fuselage != null && fuselage.type == PartType.FUSELAGE)
			buf.writeResourceLocation(BuiltInRegistries.ITEM.getKey(fuselage));
		else
			buf.writeResourceLocation(ResourceLocation.fromNamespaceAndPath("minecraft", "air"));

		if(fins != null && fins.type == PartType.FINS)
			buf.writeResourceLocation(BuiltInRegistries.ITEM.getKey(fins));
		else
			buf.writeResourceLocation(ResourceLocation.fromNamespaceAndPath("minecraft", "air"));

		if(thruster != null && thruster.type == PartType.THRUSTER)
			buf.writeResourceLocation(BuiltInRegistries.ITEM.getKey(thruster));
		else
			buf.writeResourceLocation(ResourceLocation.fromNamespaceAndPath("minecraft", "air"));
	}

	public static MissileStruct readFromByteBuffer(FriendlyByteBuf buf) {
		MissileStruct multipart = new MissileStruct();

		ResourceLocation w = buf.readResourceLocation();
		ResourceLocation f = buf.readResourceLocation();
		ResourceLocation s = buf.readResourceLocation();
		ResourceLocation t = buf.readResourceLocation();

		Item iw = BuiltInRegistries.ITEM.get(w);
		Item ifus = BuiltInRegistries.ITEM.get(f);
		Item is = BuiltInRegistries.ITEM.get(s);
		Item it = BuiltInRegistries.ITEM.get(t);

		if(iw instanceof ItemMissile mw) multipart.warhead = mw;
		if(ifus instanceof ItemMissile mf) multipart.fuselage = mf;
		if(is instanceof ItemMissile ms) multipart.fins = ms;
		if(it instanceof ItemMissile mt) multipart.thruster = mt;

		return multipart;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == this)
			return true;
		if(!(obj instanceof MissileStruct str))
			return false;
		return this.warhead == str.warhead && this.fuselage == str.fuselage && this.fins == str.fins && this.thruster == str.thruster;
	}

	@Override
	public int hashCode() {
		int hashcode = 17;
		if (warhead != null) hashcode = 31 * hashcode + warhead.hashCode();
		if (fuselage != null) hashcode = 31 * hashcode + fuselage.hashCode();
		if (fins != null) hashcode = 31 * hashcode + fins.hashCode();
		if (thruster != null) hashcode = 31 * hashcode + thruster.hashCode();
		return hashcode;
	}

	public static final StreamCodec<FriendlyByteBuf, MissileStruct> STREAM_CODEC = StreamCodec.of(
			(buf, struct) -> struct.writeToByteBuffer(buf),
			MissileStruct::readFromByteBuffer
	);
}
