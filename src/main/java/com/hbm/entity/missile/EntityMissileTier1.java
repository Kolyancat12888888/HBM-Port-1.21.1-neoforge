package com.hbm.entity.missile;

import com.hbm.api.entity.IRadarDetectableNT;
import com.hbm.entity.ModEntities;
import com.hbm.explosion.ExplosionChaos;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.items.ModItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

import java.util.ArrayList;
import java.util.List;

public abstract class EntityMissileTier1 extends EntityMissileBaseNT {

	public EntityMissileTier1(EntityType<? extends EntityMissileTier1> type, Level world) { super(type, world); }
	public EntityMissileTier1(EntityType<? extends EntityMissileTier1> type, Level world, float x, float y, float z, int a, int b) { super(type, world, x, y, z, a, b); }

	@Override
	public List<ItemStack> getDebris() {
		List<ItemStack> list = new ArrayList<>();
		list.add(new ItemStack(ModItems.plate_titanium, 4));
		return list;
	}

	@Override
	protected float getContrailScale() {
		return 0.5F;
	}

	public static class EntityMissileGeneric extends EntityMissileTier1 {
		public EntityMissileGeneric(EntityType<? extends EntityMissileGeneric> type, Level world) { super(type, world); }
		public EntityMissileGeneric(Level world, float x, float y, float z, int a, int b) { super(ModEntities.MISSILE_GENERIC.get(), world, x, y, z, a, b); }
		@Override public void onMissileImpact(BlockHitResult mop) {
			this.explodeStandard(15F, 24, false);
		}
		@Override public ItemStack getDebrisRareDrop() { return ItemStack.EMPTY; }
		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_generic); }
	}

	public static class EntityMissileDecoy extends EntityMissileTier1 {
		public EntityMissileDecoy(EntityType<? extends EntityMissileDecoy> type, Level world) { super(type, world); }
		public EntityMissileDecoy(Level world, float x, float y, float z, int a, int b) { super(ModEntities.MISSILE_DECOY.get(), world, x, y, z, a, b); }
		@Override public void onMissileImpact(BlockHitResult mop) { this.level().explode(this, this.getX(), this.getY(), this.getZ(), 4F, Level.ExplosionInteraction.BLOCK); }
		@Override public ItemStack getDebrisRareDrop() { return new ItemStack(ModItems.ingot_steel); }
		@Override public String getTranslationKey() { return "radar.target.tier4"; }
		@Override public int getBlipLevel() { return IRadarDetectableNT.TIER4; }
		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_decoy); }
	}

	public static class EntityMissileIncendiary extends EntityMissileTier1 {
		public EntityMissileIncendiary(EntityType<? extends EntityMissileIncendiary> type, Level world) { super(type, world); }
		public EntityMissileIncendiary(Level world, float x, float y, float z, int a, int b) { super(ModEntities.MISSILE_INCENDIARY.get(), world, x, y, z, a, b); }
		@Override public void onMissileImpact(BlockHitResult mop) {
			this.explodeStandard(15F, 24, true);
		}
		@Override public ItemStack getDebrisRareDrop() { return ItemStack.EMPTY; }
		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_incendiary); }
	}

	public static class EntityMissileCluster extends EntityMissileTier1 {
		public EntityMissileCluster(EntityType<? extends EntityMissileCluster> type, Level world) { super(type, world); }
		public EntityMissileCluster(Level world, float x, float y, float z, int a, int b) { super(ModEntities.MISSILE_CLUSTER.get(), world, x, y, z, a, b); this.isCluster = true; }
		@Override public void onMissileImpact(BlockHitResult mop) {
			this.level().explode(this, this.getX(), this.getY(), this.getZ(), 5F, Level.ExplosionInteraction.BLOCK);
			ExplosionChaos.cluster(this.level(), (int)this.getX(), (int)this.getY(), (int)this.getZ(), 25, 100);
		}
		@Override public void cluster() { this.onMissileImpact(null); }
		@Override public ItemStack getDebrisRareDrop() { return ItemStack.EMPTY; }
		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_cluster); }
	}

	public static class EntityMissileBunkerBuster extends EntityMissileTier1 {
		public EntityMissileBunkerBuster(EntityType<? extends EntityMissileBunkerBuster> type, Level world) { super(type, world); }
		public EntityMissileBunkerBuster(Level world, float x, float y, float z, int a, int b) { super(ModEntities.MISSILE_BUSTER.get(), world, x, y, z, a, b); }
		@Override public void onMissileImpact(BlockHitResult mop) {
			for(int i = 0; i < 15; i++) this.level().explode(this, this.getX(), this.getY() - i, this.getZ(), 5F, Level.ExplosionInteraction.BLOCK);
			ExplosionLarge.spawnParticles(this.level(), this.getX(), this.getY(), this.getZ(), 5);
			ExplosionLarge.spawnShrapnels(this.level(), this.getX(), this.getY(), this.getZ(), 5);
			ExplosionLarge.spawnRubble(this.level(), this.getX(), this.getY(), this.getZ(), 5);
		}
		@Override public ItemStack getDebrisRareDrop() { return ItemStack.EMPTY; }
		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_buster); }
	}
}
