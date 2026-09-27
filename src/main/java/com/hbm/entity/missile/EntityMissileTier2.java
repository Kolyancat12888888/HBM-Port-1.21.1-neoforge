package com.hbm.entity.missile;

import com.hbm.api.entity.IRadarDetectableNT;
import com.hbm.entity.ModEntities;
import com.hbm.entity.logic.EntityEMP;
import com.hbm.explosion.ExplosionChaos;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

import java.util.ArrayList;
import java.util.List;

public abstract class EntityMissileTier2 extends EntityMissileBaseNT {

	public EntityMissileTier2(EntityType<? extends EntityMissileTier2> type, Level world) { super(type, world); }
	public EntityMissileTier2(EntityType<? extends EntityMissileTier2> type, Level world, float x, float y, float z, int a, int b) { super(type, world, x, y, z, a, b); }

	@Override
	public List<ItemStack> getDebris() {
		List<ItemStack> list = new ArrayList<>();
		list.add(new ItemStack(ModItems.plate_steel, 10));
		list.add(new ItemStack(ModItems.plate_titanium, 6));
		return list;
	}

	@Override
	public String getTranslationKey() {
		return "radar.target.tier2";
	}

	@Override
	public int getBlipLevel() {
		return IRadarDetectableNT.TIER2;
	}

	public static class EntityMissileStrong extends EntityMissileTier2 {
		public EntityMissileStrong(EntityType<? extends EntityMissileStrong> type, Level world) { super(type, world); }
		public EntityMissileStrong(Level world, float x, float y, float z, int a, int b) { super(ModEntities.MISSILE_STRONG.get(), world, x, y, z, a, b); }
		@Override public void onMissileImpact(BlockHitResult mop)  {
			this.explodeStandard(30F, 32, false);
		}
		@Override public ItemStack getDebrisRareDrop() { return ItemStack.EMPTY; }
		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_strong); }
	}

	public static class EntityMissileIncendiaryStrong extends EntityMissileTier2 {
		public EntityMissileIncendiaryStrong(EntityType<? extends EntityMissileIncendiaryStrong> type, Level world) { super(type, world); }
		public EntityMissileIncendiaryStrong(Level world, float x, float y, float z, int a, int b) { super(ModEntities.MISSILE_INCENDIARY_STRONG.get(), world, x, y, z, a, b); }
		@Override public void onMissileImpact(BlockHitResult mop) {
			this.explodeStandard(30F, 32, true);
			ExplosionChaos.flameDeath(this.level(), thrower, this.blockPosition(), 25);
		}
		@Override public ItemStack getDebrisRareDrop() { return ItemStack.EMPTY; }
		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_incendiary_strong); }
	}

	public static class EntityMissileClusterStrong extends EntityMissileTier2 {
		public EntityMissileClusterStrong(EntityType<? extends EntityMissileClusterStrong> type, Level world) { super(type, world); }
		public EntityMissileClusterStrong(Level world, float x, float y, float z, int a, int b) { super(ModEntities.MISSILE_CLUSTER_STRONG.get(), world, x, y, z, a, b); this.isCluster = true; }
		@Override public void onMissileImpact(BlockHitResult mop) {
			this.level().explode(this, this.getX(), this.getY(), this.getZ(), 15F, Level.ExplosionInteraction.BLOCK);
			ExplosionChaos.cluster(this.level(), (int) this.getX(), (int) this.getY(), (int) this.getZ(), 50, 100);
		}
		@Override public void cluster() { this.onMissileImpact(null); }
		@Override public ItemStack getDebrisRareDrop() { return ItemStack.EMPTY; }
		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_cluster_strong); }
	}

	public static class EntityMissileBusterStrong extends EntityMissileTier2 {
		public EntityMissileBusterStrong(EntityType<? extends EntityMissileBusterStrong> type, Level world) { super(type, world); }
		public EntityMissileBusterStrong(Level world, float x, float y, float z, int a, int b) { super(ModEntities.MISSILE_BUSTER_STRONG.get(), world, x, y, z, a, b); }
		@Override public void onMissileImpact(BlockHitResult mop) {
			for (int i = 0; i < 20; i++) this.level().explode(this, this.getX(), this.getY() - i, this.getZ(), 7.5F, Level.ExplosionInteraction.BLOCK);
			ExplosionLarge.spawnParticles(this.level(), this.getX(), this.getY(), this.getZ(), 8);
			ExplosionLarge.spawnShrapnels(this.level(), this.getX(), this.getY(), this.getZ(), 8);
			ExplosionLarge.spawnRubble(this.level(), this.getX(), this.getY(), this.getZ(), 8);
		}
		@Override public ItemStack getDebrisRareDrop() { return ItemStack.EMPTY; }
		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_buster_strong); }
	}

	public static class EntityMissileEMPStrong extends EntityMissileTier2 {
		public EntityMissileEMPStrong(EntityType<? extends EntityMissileEMPStrong> type, Level world) { super(type, world); }
		public EntityMissileEMPStrong(Level world, float x, float y, float z, int a, int b) { super(ModEntities.MISSILE_EMP_STRONG.get(), world, x, y, z, a, b); }
		@Override public void onMissileImpact(BlockHitResult mop) {
			EntityEMP emp = new EntityEMP(this.level());
			emp.setPos(this.getX(), this.getY(), this.getZ());
			this.level().addFreshEntity(emp);
		}
		@Override public ItemStack getDebrisRareDrop() { return ItemStack.EMPTY; }
		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_emp_strong); }
	}
}
