package com.hbm.entity.missile;

import com.hbm.api.entity.IRadarDetectableNT;
import com.hbm.config.BombConfig;
import com.hbm.entity.ModEntities;
import com.hbm.entity.effect.EntityNukeTorex;
import com.hbm.entity.logic.EntityNukeExplosionMK5;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.items.ModItems;
import com.hbm.world.WorldUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;

import java.util.ArrayList;
import java.util.List;

public abstract class EntityMissileTier4 extends EntityMissileBaseNT {

	public EntityMissileTier4(EntityType<? extends EntityMissileTier4> type, Level world) { super(type, world); }
	public EntityMissileTier4(EntityType<? extends EntityMissileTier4> type, Level world, float x, float y, float z, int a, int b) { super(type, world, x, y, z, a, b); }

	@Override
	public List<ItemStack> getDebris() {
		List<ItemStack> list = new ArrayList<>();
		list.add(new ItemStack(ModItems.plate_titanium, 16));
		list.add(new ItemStack(ModItems.plate_steel, 20));
		return list;
	}

	@Override
	public String getTranslationKey() {
		return "radar.target.tier4";
	}

	@Override
	public int getBlipLevel() {
		return IRadarDetectableNT.TIER4;
	}

	public static class EntityMissileNuclear extends EntityMissileTier4 {
		public EntityMissileNuclear(EntityType<? extends EntityMissileNuclear> type, Level world) { super(type, world); }
		public EntityMissileNuclear(Level world, float x, float y, float z, int a, int b) { super(ModEntities.MISSILE_NUCLEAR.get(), world, x, y, z, a, b); }
		@Override public void onMissileImpact(BlockHitResult mop) {
			WorldUtil.loadAndSpawnEntityInWorld(EntityNukeExplosionMK5.statFac(this.level(), BombConfig.missileRadius, this.getX(), this.getY(), this.getZ()).setDetonator(thrower));
			EntityNukeTorex.statFac(this.level(), this.getX(), this.getY(), this.getZ(), BombConfig.missileRadius);
		}
		@Override public ItemStack getDebrisRareDrop() { return ItemStack.EMPTY; }
		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_nuclear); }
	}

	public static class EntityMissileMirv extends EntityMissileTier4 {
		public EntityMissileMirv(EntityType<? extends EntityMissileMirv> type, Level world) { super(type, world); }
		public EntityMissileMirv(Level world, float x, float y, float z, int a, int b) { super(ModEntities.MISSILE_NUCLEAR_CLUSTER.get(), world, x, y, z, a, b); }
		@Override public void onMissileImpact(BlockHitResult mop) {
			WorldUtil.loadAndSpawnEntityInWorld(EntityNukeExplosionMK5.statFac(this.level(), BombConfig.missileRadius * 2, this.getX(), this.getY(), this.getZ()).setDetonator(thrower));
			EntityNukeTorex.statFac(this.level(), this.getX(), this.getY(), this.getZ(), BombConfig.missileRadius * 2);
		}
		@Override public ItemStack getDebrisRareDrop() { return ItemStack.EMPTY; }
		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_nuclear_cluster); }
	}

	public static class EntityMissileVolcano extends EntityMissileTier4 {
		public EntityMissileVolcano(EntityType<? extends EntityMissileVolcano> type, Level world) { super(type, world); }
		public EntityMissileVolcano(Level world, float x, float y, float z, int a, int b) { super(ModEntities.MISSILE_VOLCANO.get(), world, x, y, z, a, b); }
		@Override public void onMissileImpact(BlockHitResult mop) {
			ExplosionLarge.explode(this.level(), thrower, this.getX(), this.getY(), this.getZ(), 10.0F, true, true, true);
			BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
			for(int x = -1; x <= 1; x++) for(int y = -1; y <= 1; y++) for(int z = -1; z <= 1; z++)
				this.level().setBlock(pos.set((int) Math.floor(this.getX() + x), (int) Math.floor(this.getY() + y), (int) Math.floor(this.getZ() + z)), Blocks.LAVA.defaultBlockState(), 3);
		}
		@Override public ItemStack getDebrisRareDrop() { return ItemStack.EMPTY; }
		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_volcano); }
	}

	public static class EntityMissileDoomsday extends EntityMissileTier4 {
		public EntityMissileDoomsday(EntityType<? extends EntityMissileDoomsday> type, Level world) { super(type, world); }
		public EntityMissileDoomsday(EntityType<? extends EntityMissileDoomsday> type, Level world, float x, float y, float z, int a, int b) { super(type, world, x, y, z, a, b); }
		public EntityMissileDoomsday(Level world, float x, float y, float z, int a, int b) { super(ModEntities.MISSILE_DOOMSDAY.get(), world, x, y, z, a, b); }
		@Override public void onMissileImpact(BlockHitResult mop) {
			WorldUtil.loadAndSpawnEntityInWorld(EntityNukeExplosionMK5.statFac(this.level(), BombConfig.missileRadius * 2, this.getX(), this.getY(), this.getZ()).moreFallout(100).setDetonator(thrower));
			EntityNukeTorex.statFac(this.level(), this.getX(), this.getY(), this.getZ(), BombConfig.missileRadius * 2);
		}
		@Override public List<ItemStack> getDebris() { return new ArrayList<>(); }
		@Override public ItemStack getDebrisRareDrop() { return ItemStack.EMPTY; }
		@Override public String getTranslationKey() { return "radar.target.doomsday"; }
		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_doomsday); }
	}

	public static class EntityMissileDoomsdayRusted extends EntityMissileDoomsday {
		public EntityMissileDoomsdayRusted(EntityType<? extends EntityMissileDoomsdayRusted> type, Level world) { super(type, world); }
		public EntityMissileDoomsdayRusted(Level world, float x, float y, float z, int a, int b) { super(ModEntities.MISSILE_DOOMSDAY_RUSTED.get(), world, x, y, z, a, b); }
		@Override public void onMissileImpact(BlockHitResult mop) {
			WorldUtil.loadAndSpawnEntityInWorld(EntityNukeExplosionMK5.statFac(this.level(), BombConfig.missileRadius, this.getX(), this.getY(), this.getZ()).moreFallout(100).setDetonator(thrower));
			EntityNukeTorex.statFac(this.level(), this.getX(), this.getY(), this.getZ(), BombConfig.missileRadius);
		}
		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_doomsday_rusted); }
	}

	public static class EntityMissileN2 extends EntityMissileTier4 {
		public EntityMissileN2(EntityType<? extends EntityMissileN2> type, Level world) { super(type, world); }
		public EntityMissileN2(Level world, float x, float y, float z, int a, int b) { super(ModEntities.MISSILE_N2.get(), world, x, y, z, a, b); }
		@Override public void onMissileImpact(BlockHitResult mop) {
			WorldUtil.loadAndSpawnEntityInWorld(EntityNukeExplosionMK5.statFacNoRad(this.level(), (BombConfig.n2Radius / 12) * 5, this.getX(), this.getY(), this.getZ()).setDetonator(thrower));
			if(BombConfig.enableNukeClouds) {
				EntityNukeTorex.statFac(this.level(), this.getX(), this.getY(), this.getZ(), ((float)BombConfig.n2Radius / 12) * 5);
			}
		}
		@Override public ItemStack getDebrisRareDrop() { return ItemStack.EMPTY; }
		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_n2); }
	}

	public static class EntityMissileStealth extends EntityMissileTier4 {
		public EntityMissileStealth(EntityType<? extends EntityMissileStealth> type, Level world) { super(type, world); }
		public EntityMissileStealth(Level world, float x, float y, float z, int a, int b) { super(ModEntities.MISSILE_STEALTH.get(), world, x, y, z, a, b); }
		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_stealth); }
		@Override public boolean canBeSeenBy(Object radar) { return false; }
		@Override public void onMissileImpact(BlockHitResult mop) { this.explodeStandard(20F, 24, false); }
		@Override public ItemStack getDebrisRareDrop() { return ItemStack.EMPTY; }
	}
}
