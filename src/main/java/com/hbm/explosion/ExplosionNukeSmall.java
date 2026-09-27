package com.hbm.explosion;

import com.hbm.config.BombConfig;
import com.hbm.handler.radiation.ChunkRadiationManager;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class ExplosionNukeSmall {

	public static void explode(Level world, double posX, double posY, double posZ, MukeParams params) {
		if (world == null || world.isClientSide) return;

		world.explode(null, posX, posY, posZ, params.blastRadius, Level.ExplosionInteraction.BLOCK);

		if (params.killRadius > 0) {
			ExplosionNukeGeneric.dealDamage(world, posX, posY, posZ, params.killRadius);
		}

		if (params.miniNuke) {
			float radMod = params.radiationLevel / 3F;
			BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
			for (int i = -2; i <= 2; i++) {
				for (int j = -2; j <= 2; j++) {
					if (Math.abs(i) + Math.abs(j) < 4) {
						ChunkRadiationManager.proxy.incrementRad(
								world,
								mutableBlockPos.set((int) posX + i * 16, (int) posY, (int) posZ + j * 16),
								(50F / (Math.abs(i) + Math.abs(j) + 1)) * radMod
						);
					}
				}
			}
		}
	}

	public static MukeParams PARAMS_SAFE   = new MukeParams() {{ safe = true;  killRadius = 45F; radiationLevel = 2F; }};
	public static MukeParams PARAMS_TOTS   = new MukeParams() {{ blastRadius = 10F; killRadius = 30F; shrapnelCount = 0; resolution = 32; radiationLevel = 1; }};
	public static MukeParams PARAMS_LOW    = new MukeParams() {{ blastRadius = 15F; killRadius = 45F; radiationLevel = 2; }};
	public static MukeParams PARAMS_MEDIUM = new MukeParams() {{ blastRadius = 20F; killRadius = 55F; radiationLevel = 3; }};
	public static MukeParams PARAMS_HIGH   = new MukeParams() {{ miniNuke = false; blastRadius = BombConfig.fatmanRadius; shrapnelCount = 0; }};

	public static class MukeParams {
		public boolean miniNuke = true;
		public boolean safe = false;
		public float blastRadius = 25F;
		public float killRadius = 50F;
		public float radiationLevel = 1F;
		public int shrapnelCount = 25;
		public int resolution = 64;
	}
}
