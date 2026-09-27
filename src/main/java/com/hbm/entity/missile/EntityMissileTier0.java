package com.hbm.entity.missile;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.BombConfig;
import com.hbm.entity.ModEntities;
import com.hbm.entity.effect.EntityBlackHole;
import com.hbm.entity.effect.EntityCloudFleija;
import com.hbm.entity.effect.EntityEMPBlast;
import com.hbm.entity.logic.EntityNukeExplosionMK3;
import com.hbm.explosion.ExplosionNukeGeneric;
import com.hbm.explosion.ExplosionNukeSmall;
import com.hbm.items.ModItems;
import com.hbm.world.WorldUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.ArrayList;
import java.util.List;

public abstract class EntityMissileTier0 extends EntityMissileBaseNT {

	public EntityMissileTier0(EntityType<? extends EntityMissileTier0> type, Level world) {
		super(type, world);
	}

	public EntityMissileTier0(EntityType<? extends EntityMissileTier0> type, Level world, float x, float y, float z, int a, int b) {
		super(type, world, x, y, z, a, b);
	}

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

	public static class EntityMissileMicro extends EntityMissileTier0 {
		public EntityMissileMicro(EntityType<? extends EntityMissileMicro> type, Level world) { super(type, world); }
		public EntityMissileMicro(Level world, float x, float y, float z, int a, int b) { super(ModEntities.MISSILE_MICRO.get(), world, x, y, z, a, b); }
		@Override public void onMissileImpact(BlockHitResult mop) {
			if (!this.level().isClientSide) {
				ExplosionNukeSmall.explode(this.level(), this.getX(), this.getY() + 0.5, this.getZ(), ExplosionNukeSmall.PARAMS_HIGH);
			}
		}
		@Override public ItemStack getDebrisRareDrop() { return ItemStack.EMPTY; }
		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_micro); }
	}

	public static class EntityMissileSchrabidium extends EntityMissileTier0 {
		public EntityMissileSchrabidium(EntityType<? extends EntityMissileSchrabidium> type, Level world) { super(type, world); }
		public EntityMissileSchrabidium(Level world, float x, float y, float z, int a, int b) { super(ModEntities.MISSILE_SCHRABIDIUM.get(), world, x, y, z, a, b); }
		@Override public void onMissileImpact(BlockHitResult mop) {
			if (!this.level().isClientSide) {
				EntityNukeExplosionMK3 ex = EntityNukeExplosionMK3.statFacFleija(this.level(), this.getX(), this.getY(), this.getZ(), BombConfig.aSchrabRadius);
				WorldUtil.loadAndSpawnEntityInWorld(ex);
				EntityCloudFleija cloud = new EntityCloudFleija(this.level(), (int) BombConfig.aSchrabRadius);
				cloud.setPos(this.getX(), this.getY(), this.getZ());
				this.level().addFreshEntity(cloud);
			}
		}
		@Override public ItemStack getDebrisRareDrop() { return ItemStack.EMPTY; }
		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_schrabidium); }
	}

	public static class EntityMissileBHole extends EntityMissileTier0 {
		public EntityMissileBHole(EntityType<? extends EntityMissileBHole> type, Level world) { super(type, world); }
		public EntityMissileBHole(Level world, float x, float y, float z, int a, int b) { super(ModEntities.MISSILE_BHOLE.get(), world, x, y, z, a, b); }
		@Override public void onMissileImpact(BlockHitResult mop) {
			this.level().explode(this, this.getX(), this.getY(), this.getZ(), 1.5F, Level.ExplosionInteraction.BLOCK);
			EntityBlackHole bl = new EntityBlackHole(this.level(), 1.5F);
			bl.setPos(this.getX(), this.getY(), this.getZ());
			this.level().addFreshEntity(bl);
		}
		@Override public ItemStack getDebrisRareDrop() { return new ItemStack(ModItems.black_hole, 1); }
		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_bhole); }
	}

	public static class EntityMissileTaint extends EntityMissileTier0 {
		public EntityMissileTaint(EntityType<? extends EntityMissileTaint> type, Level world) { super(type, world); }
		public EntityMissileTaint(Level world, float x, float y, float z, int a, int b) { super(ModEntities.MISSILE_TAINT.get(), world, x, y, z, a, b); }
		@Override public void onMissileImpact(BlockHitResult mop) {
			this.level().explode(this, this.getX(), this.getY(), this.getZ(), 10.0F, Level.ExplosionInteraction.BLOCK);
			BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
			for(int i = 0; i < 100; i++) {
				int a = this.random.nextInt(11) + (int) this.getX() - 5;
				int b = this.random.nextInt(11) + (int) this.getY() - 5;
				int c = this.random.nextInt(11) + (int) this.getZ() - 5;
				pos.set(a, b, c);
				BlockState state = this.level().getBlockState(pos);
				if(!state.isAir() && state.getBlock() != Blocks.BEDROCK)
					this.level().setBlock(pos, Blocks.DIRT.defaultBlockState(), 2);
			}
		}
		@Override public ItemStack getDebrisRareDrop() { return ItemStack.EMPTY; }
		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_taint); }
	}

	public static class EntityMissileEMP extends EntityMissileTier0 {
		public EntityMissileEMP(EntityType<? extends EntityMissileEMP> type, Level world) { super(type, world); }
		public EntityMissileEMP(Level world, float x, float y, float z, int a, int b) { super(ModEntities.MISSILE_EMP.get(), world, x, y, z, a, b); }
		@Override public void onMissileImpact(BlockHitResult mop) {
			ExplosionNukeGeneric.empBlast(this.level(), thrower, (int)this.getX(), (int)this.getY(), (int)this.getZ(), 50);
			EntityEMPBlast wave = new EntityEMPBlast(this.level(), 50);
			wave.setPos(this.getX(), this.getY(), this.getZ());
			this.level().addFreshEntity(wave);
		}
		@Override public ItemStack getDebrisRareDrop() { return ItemStack.EMPTY; }
		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_emp); }
	}
}
