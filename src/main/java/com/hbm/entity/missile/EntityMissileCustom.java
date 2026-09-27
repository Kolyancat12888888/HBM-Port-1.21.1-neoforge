package com.hbm.entity.missile;

import com.hbm.api.entity.IRadarDetectableNT;
import com.hbm.entity.ModEntities;
import com.hbm.entity.effect.EntityNukeTorex;
import com.hbm.entity.logic.EntityBalefire;
import com.hbm.entity.logic.EntityNukeExplosionMK5;
import com.hbm.entity.logic.IChunkLoader;
import com.hbm.explosion.ExplosionChaos;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.handler.MissileStruct;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.ItemMissile;
import com.hbm.items.weapon.ItemMissile.PartSize;
import com.hbm.items.weapon.ItemMissile.WarheadType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class EntityMissileCustom extends EntityMissileBaseNT implements IChunkLoader {

	public static final EntityDataAccessor<String> WARHEAD = SynchedEntityData.defineId(EntityMissileCustom.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> FUSELAGE = SynchedEntityData.defineId(EntityMissileCustom.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> FINS = SynchedEntityData.defineId(EntityMissileCustom.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> THRUSTER = SynchedEntityData.defineId(EntityMissileCustom.class, EntityDataSerializers.STRING);

	public float fuel;
	public float consumption;

	private static final double[] MIRV_OFF_X = { 0.0,  0.45, -0.45,  0.15, -0.15,  0.15, -0.15 };
	private static final double[] MIRV_OFF_Z = { 0.0,  0.00,  0.00,  0.30, -0.30, -0.30,  0.30 };

	public EntityMissileCustom(EntityType<? extends EntityMissileCustom> type, Level world) {
		super(type, world);
	}

	public EntityMissileCustom(Level world, float x, float y, float z, int a, int b, MissileStruct template) {
		super(ModEntities.MISSILE_CUSTOM.get(), world, x, y, z, a, b);

		if (template.warhead != null) this.entityData.set(WARHEAD, BuiltInRegistries.ITEM.getKey(template.warhead).toString());
		if (template.fuselage != null) this.entityData.set(FUSELAGE, BuiltInRegistries.ITEM.getKey(template.fuselage).toString());
		if (template.thruster != null) this.entityData.set(THRUSTER, BuiltInRegistries.ITEM.getKey(template.thruster).toString());
		if (template.fins != null) this.entityData.set(FINS, BuiltInRegistries.ITEM.getKey(template.fins).toString());

		ItemMissile fuselage = template.fuselage;
		ItemMissile thruster = template.thruster;

		this.fuel = fuselage != null ? fuselage.getTankSize() : 0;
		this.consumption = (thruster != null && thruster.attributes != null && thruster.attributes.length > 1) ? (Float) thruster.attributes[1] : 1.0F;
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		builder.define(WARHEAD, "");
		builder.define(FUSELAGE, "");
		builder.define(FINS, "");
		builder.define(THRUSTER, "");
	}

	@Override
	public void tick() {
		Item warheadItem = getPart(WARHEAD);
		if (warheadItem instanceof ItemMissile part && part.attributes != null && part.attributes.length > 0) {
			WarheadType type = (WarheadType) part.attributes[0];
			if (type != null && type.updateCustom != null) {
				type.updateCustom.accept(this);
				if (!this.level().isClientSide && this.isRemoved()) return;
			}
		}

		if (!this.level().isClientSide) {
			if (this.hasPropulsion()) this.fuel -= this.consumption;
		}

		super.tick();
	}

	@Override
	public boolean hasPropulsion() {
		return this.fuel > 0;
	}

	private Item getPart(EntityDataAccessor<String> param) {
		String key = this.entityData.get(param);
		if (key.isEmpty()) return null;
		return BuiltInRegistries.ITEM.get(ResourceLocation.parse(key));
	}

	@Override
	public void onMissileImpact(BlockHitResult mop) {
		Item warheadItem = getPart(WARHEAD);
		if (!(warheadItem instanceof ItemMissile part) || part.attributes == null) return;

		WarheadType type = (WarheadType) part.attributes[0];
		float strength = (part.attributes.length > 1 && part.attributes[1] instanceof Float f) ? f : 15.0F;

		if (type != null && type.impactCustom != null) {
			type.impactCustom.accept(this);
			return;
		}

		if (type == null) return;

		switch (type) {
			case HE:
				ExplosionLarge.explode(this.level(), thrower, this.getX(), this.getY(), this.getZ(), strength, true, false, true);
				ExplosionLarge.jolt(this.level(), thrower, this.getX(), this.getY(), this.getZ(), strength, (int) (strength * 50), 0.25);
				break;
			case INC:
				ExplosionLarge.explodeFire(this.level(), thrower, this.getX(), this.getY(), this.getZ(), strength, true, false, true);
				ExplosionLarge.jolt(this.level(), thrower, this.getX(), this.getY(), this.getZ(), strength * 1.5, (int) (strength * 50), 0.25);
				break;
			case CLUSTER:
				this.level().explode(this, this.getX(), this.getY(), this.getZ(), strength, Level.ExplosionInteraction.BLOCK);
				ExplosionChaos.cluster(this.level(), (int) this.getX(), (int) this.getY(), (int) this.getZ(), 50, 100);
				break;
			case BUSTER:
				ExplosionLarge.buster(this.level(), thrower, this.getX(), this.getY(), this.getZ(), this.getDeltaMovement(), strength, strength * 4);
				break;
			case NUCLEAR:
			case TX:
			case MIRV:
				this.level().addFreshEntity(EntityNukeExplosionMK5.statFac(this.level(), (int) strength, this.getX(), this.getY(), this.getZ()).setDetonator(thrower));
				EntityNukeTorex.statFac(this.level(), this.getX(), this.getY(), this.getZ(), strength);
				break;
			case BALEFIRE:
				EntityBalefire bf = new EntityBalefire(this.level());
				bf.setPos(this.getX(), this.getY(), this.getZ());
				bf.setDetonator(thrower);
				bf.destructionRange = (int) strength;
				this.level().addFreshEntity(bf);
				EntityNukeTorex.statFacBale(this.level(), this.getX(), this.getY(), this.getZ(), strength);
				break;
			case N2:
				this.level().addFreshEntity(EntityNukeExplosionMK5.statFacNoRad(this.level(), (int) strength, this.getX(), this.getY(), this.getZ()).setDetonator(thrower));
				EntityNukeTorex.statFac(this.level(), this.getX(), this.getY(), this.getZ(), strength);
				break;
			case TAINT:
				int r = (int) strength;
				BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
				for (int i = 0; i < r * 10; i++) {
					int a = this.random.nextInt(r) + (int) this.getX() - (r / 2 - 1);
					int b = this.random.nextInt(r) + (int) this.getY() - (r / 2 - 1);
					int c = this.random.nextInt(r) + (int) this.getZ() - (r / 2 - 1);
					pos.set(a, b, c);
					if (!this.level().getBlockState(pos).isAir() && this.level().getBlockState(pos).getBlock() != Blocks.BEDROCK)
						this.level().setBlock(pos, Blocks.DIRT.defaultBlockState(), 2);
				}
				break;
			case CLOUD:
				ExplosionChaos.spawnChlorine(this.level(), this.getX(), this.getY(), this.getZ(), 750, 2.5, 2);
				break;
			default:
				break;
		}
	}

	public void mirvSplit() {
		if (this.level().isClientSide) return;
		if (this.isRemoved()) return;
		if (this.getDeltaMovement().y < -1D) {
			LivingEntity t = this.thrower;
			for (int i = 0; i < 7; i++) {
				EntityMIRV child = new EntityMIRV(this.level());
				child.setPos(this.getX(), this.getY(), this.getZ());
				child.setDeltaMovement(this.getDeltaMovement().x + MIRV_OFF_X[i], this.getDeltaMovement().y, this.getDeltaMovement().z + MIRV_OFF_Z[i]);
				if (t != null) {
					child.setThrower(t);
				}
				this.level().addFreshEntity(child);
			}
			this.discard();
		}
	}

	@Override
	public String getTranslationKey() {
		Item fuselageItem = getPart(FUSELAGE);
		if (fuselageItem instanceof ItemMissile part) {
			PartSize top = part.top;
			PartSize bottom = part.bottom;
			if (top == PartSize.SIZE_10 && bottom == PartSize.SIZE_10) return "radar.target.custom10";
			if (top == PartSize.SIZE_10 && bottom == PartSize.SIZE_15) return "radar.target.custom1015";
			if (top == PartSize.SIZE_15 && bottom == PartSize.SIZE_15) return "radar.target.custom15";
			if (top == PartSize.SIZE_15 && bottom == PartSize.SIZE_20) return "radar.target.custom1520";
			if (top == PartSize.SIZE_20 && bottom == PartSize.SIZE_20) return "radar.target.custom20";
		}
		return "radar.target.custom";
	}

	@Override
	public int getBlipLevel() {
		Item fuselageItem = getPart(FUSELAGE);
		if (fuselageItem instanceof ItemMissile part) {
			PartSize top = part.top;
			PartSize bottom = part.bottom;
			if (top == PartSize.SIZE_10 && bottom == PartSize.SIZE_10) return IRadarDetectableNT.TIER10;
			if (top == PartSize.SIZE_10 && bottom == PartSize.SIZE_15) return IRadarDetectableNT.TIER10_15;
			if (top == PartSize.SIZE_15 && bottom == PartSize.SIZE_15) return IRadarDetectableNT.TIER15;
			if (top == PartSize.SIZE_15 && bottom == PartSize.SIZE_20) return IRadarDetectableNT.TIER15_20;
			if (top == PartSize.SIZE_20 && bottom == PartSize.SIZE_20) return IRadarDetectableNT.TIER20;
		}
		return IRadarDetectableNT.TIER1;
	}

	@Override public List<ItemStack> getDebris() { return new ArrayList<>(); }
	@Override public ItemStack getDebrisRareDrop() { return ItemStack.EMPTY; }

	@Override
	public ItemStack getMissileItemForInfo() {
		return new ItemStack(ModItems.missile_custom);
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag nbt) {
		super.readAdditionalSaveData(nbt);
		fuel = nbt.getFloat("fuel");
		consumption = nbt.getFloat("consumption");
		this.entityData.set(WARHEAD, nbt.getString("warhead"));
		this.entityData.set(FUSELAGE, nbt.getString("fuselage"));
		this.entityData.set(FINS, nbt.getString("fins"));
		this.entityData.set(THRUSTER, nbt.getString("thruster"));
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag nbt) {
		super.addAdditionalSaveData(nbt);
		nbt.putFloat("fuel", fuel);
		nbt.putFloat("consumption", consumption);
		nbt.putString("warhead", this.entityData.get(WARHEAD));
		nbt.putString("fuselage", this.entityData.get(FUSELAGE));
		nbt.putString("fins", this.entityData.get(FINS));
		nbt.putString("thruster", this.entityData.get(THRUSTER));
	}
}
