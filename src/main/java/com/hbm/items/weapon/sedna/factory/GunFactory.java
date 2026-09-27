package com.hbm.items.weapon.sedna.factory;

import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.items.weapon.sedna.GunConfig;
import com.hbm.items.weapon.sedna.ItemGunBaseSedna;
import com.hbm.items.weapon.sedna.Receiver;
import com.hbm.util.DamageResistanceHandler.DamageClass;
import net.minecraft.world.item.Item;

public class GunFactory {

	public static final BulletConfig B9_STANDARD = new BulletConfig().setDamage(6.0F).setVelocity(4.0F).setSpread(0.01F);
	public static final BulletConfig B44_STANDARD = new BulletConfig().setDamage(18.0F).setVelocity(5.0F).setSpread(0.005F);
	public static final BulletConfig B50_STANDARD = new BulletConfig().setDamage(55.0F).setVelocity(8.0F).setArmorPiercing(0.5F);
	public static final BulletConfig B12GA_BUCK = new BulletConfig().setDamage(4.0F).setVelocity(3.5F).setProjectiles(8).setSpread(0.08F);
	public static final BulletConfig B_MINI_NUKE = new BulletConfig().setDamage(250.0F).setVelocity(2.0F).setGravity(0.05F);

	static {
		B_MINI_NUKE.dmgClass = DamageClass.EXPLOSIVE;
		B_MINI_NUKE.onImpact = (bullet, mop) -> {
			if (!bullet.level().isClientSide) {
				bullet.level().explode(bullet, bullet.getX(), bullet.getY(), bullet.getZ(), 15.0F, net.minecraft.world.level.Level.ExplosionInteraction.BLOCK);
				com.hbm.explosion.ExplosionNukeGeneric.waste(bullet.level(), bullet.blockPosition(), 50);
				bullet.discard();
			}
		};
	}

	public static ItemGunBaseSedna create9mmPistol() {
		GunConfig config = new GunConfig().setDurability(800).setRecoil(1.2F, 0.4F);
		config.addReceiver(new Receiver(0).setDamage(6.0F).setDelay(6).setMagSize(15));
		return new ItemGunBaseSedna(new Item.Properties(), config, B9_STANDARD);
	}

	public static ItemGunBaseSedna create44Revolver() {
		GunConfig config = new GunConfig().setDurability(1200).setRecoil(3.5F, 1.0F);
		config.addReceiver(new Receiver(0).setDamage(18.0F).setDelay(14).setMagSize(6));
		return new ItemGunBaseSedna(new Item.Properties(), config, B44_STANDARD);
	}

	public static ItemGunBaseSedna create50BMGAntiMateriel() {
		GunConfig config = new GunConfig().setDurability(600).setRecoil(7.0F, 2.0F);
		config.addReceiver(new Receiver(0).setDamage(55.0F).setDelay(30).setMagSize(5));
		return new ItemGunBaseSedna(new Item.Properties(), config, B50_STANDARD);
	}

	public static ItemGunBaseSedna create12gaShotgun() {
		GunConfig config = new GunConfig().setDurability(900).setRecoil(4.5F, 1.5F);
		config.addReceiver(new Receiver(0).setDamage(4.0F).setDelay(18).setMagSize(8).setSpread(0.05F));
		return new ItemGunBaseSedna(new Item.Properties(), config, B12GA_BUCK);
	}

	public static ItemGunBaseSedna createFatMan() {
		GunConfig config = new GunConfig().setDurability(150).setRecoil(8.0F, 2.0F);
		config.addReceiver(new Receiver(0).setDamage(250.0F).setDelay(50).setMagSize(1));
		return new ItemGunBaseSedna(new Item.Properties(), config, B_MINI_NUKE);
	}
}
