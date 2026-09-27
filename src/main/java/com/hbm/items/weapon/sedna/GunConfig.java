package com.hbm.items.weapon.sedna;

import java.util.ArrayList;
import java.util.List;

public class GunConfig {

	public List<Receiver> receivers = new ArrayList<>();
	public int durability = 1000;
	public float recoilPitch = 1.0F;
	public float recoilYaw = 0.5F;
	public boolean hasAimDownSights = true;
	public float zoomFOV = 0.7F;

	public GunConfig addReceiver(Receiver receiver) {
		this.receivers.add(receiver);
		return this;
	}

	public GunConfig setDurability(int dura) {
		this.durability = dura;
		return this;
	}

	public GunConfig setRecoil(float pitch, float yaw) {
		this.recoilPitch = pitch;
		this.recoilYaw = yaw;
		return this;
	}

	public Receiver getReceiver(int index) {
		if (index >= 0 && index < receivers.size()) {
			return receivers.get(index);
		}
		return null;
	}
}
