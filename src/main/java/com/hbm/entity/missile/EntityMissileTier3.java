package com.hbm.entity.missile;

import com.hbm.api.entity.IRadarDetectableNT;
import com.hbm.entity.ModEntities;
import com.hbm.explosion.ExplosionChaos;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.explosion.ExplosionNT;
import com.hbm.explosion.ExplosionNT.ExAttrib;
import com.hbm.items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

import java.util.ArrayList;
import java.util.List;

public abstract class EntityMissileTier3 extends EntityMissileBaseNT {

	public EntityMissileTier3(EntityType<? extends EntityMissileTier3> type, Level world) { super(type, world); }
	public EntityMissileTier3(EntityType<? extends EntityMissileTier3> type, Level world, float x, float y, float z, int a, int b) { super(type, world, x, y, z, a, b); }

	@Override
	public List<ItemStack> getDebris() {
		List<ItemStack> list = new ArrayList<>();
		list.add(new ItemStack(ModItems.plate_steel, 16));
		list.add(new ItemStack(ModItems.plate_titanium, 10));
		return list;
	}

	@Override
	public String getTranslationKey() {
		return "radar.target.tier3";
	}

	@Override
	public int getBlipLevel() {
		return IRadarDetectableNT.TIER3;
	}

	public static class EntityMissileBurst extends EntityMissileTier3 {
		public EntityMissileBurst(EntityType<? extends EntityMissileBurst> type, Level world) { super(type, world); }
		public EntityMissileBurst(Level world, float x, float y, float z, int a, int b) { super(ModEntities.MISSILE_BURST.get(), world, x, y, z, a, b); }
		@Override public void onMissileImpact(BlockHitResult mop)  {
			this.explodeStandard(50F, 48, false);
		}
		@Override public ItemStack getDebrisRareDrop() { return ItemStack.EMPTY; }
		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_burst); }
	}

	public static class EntityMissileInferno extends EntityMissileTier3 {
		public EntityMissileInferno(EntityType<? extends EntityMissileInferno> type, Level world) { super(type, world); }
		public EntityMissileInferno(Level world, float x, float y, float z, int a, int b) { super(ModEntities.MISSILE_INFERNO.get(), world, x, y, z, a, b); }
		@Override public void onMissileImpact(BlockHitResult mop) {
			this.explodeStandard(50F, 48, true);
			ExplosionChaos.burn(this.level(), thrower, this.blockPosition(), 10);
			ExplosionChaos.flameDeath(this.level(), thrower, this.blockPosition(), 25);
		}
		@Override public ItemStack getDebrisRareDrop() { return ItemStack.EMPTY; }
		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_inferno); }
	}

	public static class EntityMissileRain extends EntityMissileTier3 {
		public EntityMissileRain(EntityType<? extends EntityMissileRain> type, Level world) { super(type, world); }
		public EntityMissileRain(Level world, float x, float y, float z, int a, int b) { super(ModEntities.MISSILE_RAIN.get(), world, x, y, z, a, b); this.isCluster = true; }
		@Override public void onMissileImpact(BlockHitResult mop) {
			this.level().explode(this, this.getX(), this.getY(), this.getZ(), 25F, Level.ExplosionInteraction.BLOCK);
			ExplosionChaos.cluster(this.level(), (int)this.getX(), (int)this.getY(), (int)this.getZ(), 100, 100);
		}
		@Override public void cluster() { this.onMissileImpact(null); }
		@Override public ItemStack getDebrisRareDrop() { return ItemStack.EMPTY; }
		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_rain); }
	}

	public static class EntityMissileDrill extends EntityMissileTier3 {
		public EntityMissileDrill(EntityType<? extends EntityMissileDrill> type, Level world) { super(type, world); }
		public EntityMissileDrill(Level world, float x, float y, float z, int a, int b) { super(ModEntities.MISSILE_DRILL.get(), world, x, y, z, a, b); }
		@Override public void onMissileImpact(BlockHitResult mop) {
			for(int i = 0; i < 30; i++) {
				ExplosionNT explosion = new ExplosionNT(this.level(), this, this.getX(), this.getY() - i, this.getZ(), 10F);
				explosion.addAttrib(ExAttrib.ERRODE);
				explosion.explode();
			}
			ExplosionLarge.spawnParticles(this.level(), this.getX(), this.getY(), this.getZ(), 25);
			ExplosionLarge.spawnShrapnels(this.level(), this.getX(), this.getY(), this.getZ(), 12);
			ExplosionLarge.jolt(this.level(), thrower, this.getX(), this.getY(), this.getZ(), 10, 50, 1);
		}
		@Override public ItemStack getDebrisRareDrop() { return ItemStack.EMPTY; }
		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_drill); }
	}

	public static class EntityMissileShuttle extends EntityMissileTier3 {
		public EntityMissileShuttle(EntityType<? extends EntityMissileShuttle> type, Level world) { super(type, world); }
		public EntityMissileShuttle(Level world, float x, float y, float z, int a, int b) { super(ModEntities.MISSILE_SHUTTLE.get(), world, x, y, z, a, b); }
		@Override public void onMissileImpact(BlockHitResult mop) {
			this.explodeStandard(20F, 64, false);
		}
		@Override public ItemStack getDebrisRareDrop() { return new ItemStack(ModItems.missile_generic); }
		@Override public String getTranslationKey() { return "radar.target.shuttle"; }
		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_shuttle); }
	}
}
