package com.hbm.items.weapon.sedna;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class Receiver {

	public int index;
	public float baseDamage = 10.0F;
	public int delayAfterFire = 10;
	public int delayAfterDryFire = 10;
	public int roundsPerCycle = 1;
	public float splitProjectiles = 1.0F;
	public float spreadInnate = 0.0F;
	public float spreadPenaltyHipfire = 0.02F;
	public boolean refireOnHold = false;
	public boolean ejectOnFire = true;
	public int magCapacity = 10;
	public SoundEvent fireSound;
	public float fireVolume = 1.0F;
	public float firePitch = 1.0F;
	public Vec3 projectileOffset = Vec3.ZERO;

	public Receiver(int index) {
		this.index = index;
	}

	public Receiver setDamage(float dmg) {
		this.baseDamage = dmg;
		return this;
	}

	public Receiver setDelay(int delay) {
		this.delayAfterFire = delay;
		return this;
	}

	public Receiver setRoundsPerCycle(int rounds) {
		this.roundsPerCycle = rounds;
		return this;
	}

	public Receiver setSpread(float spread) {
		this.spreadInnate = spread;
		return this;
	}

	public Receiver setAuto(boolean auto) {
		this.refireOnHold = auto;
		return this;
	}

	public Receiver setMagSize(int size) {
		this.magCapacity = size;
		return this;
	}

	public Receiver setSound(SoundEvent sound) {
		this.fireSound = sound;
		return this;
	}
}
