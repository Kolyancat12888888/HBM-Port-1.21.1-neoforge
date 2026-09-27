package com.hbm.items.special;

import com.hbm.entity.mob.EntityDuck;
import com.hbm.entity.mob.EntityHunterChopper;
import com.hbm.entity.mob.EntityUFO;
import com.hbm.entity.mob.botprime.EntityBOTPrimeHead;
import com.hbm.items.ItemBase;
import com.hbm.items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.List;

public class ItemChopper extends ItemBase {

	public ItemChopper(Properties properties) {
		super(properties.stacksTo(1));
	}

	public ItemChopper() {
		super(new Properties().stacksTo(1));
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		Level world = context.getLevel();
		if (world.isClientSide()) {
			return InteractionResult.SUCCESS;
		} else {
			ItemStack stack = context.getItemInHand();
			BlockPos pos = context.getClickedPos();
			Direction facing = context.getClickedFace();
			Player player = context.getPlayer();

			BlockPos spawnPos = pos.relative(facing);
			Entity entity = spawnCreature(world, spawnPos.getX() + 0.5D, spawnPos.getY(), spawnPos.getZ() + 0.5D);

			if (entity != null) {
				if (entity instanceof Mob mob && stack.has(net.minecraft.core.component.DataComponents.CUSTOM_NAME)) {
					mob.setCustomName(stack.getHoverName());
				}

				if (player != null && !player.getAbilities().instabuild) {
					stack.shrink(1);
				}
			}

			return InteractionResult.SUCCESS;
		}
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (world.isClientSide()) {
			return InteractionResultHolder.pass(stack);
		} else {
			BlockHitResult hitResult = getPlayerPOVHitResult(world, player, ClipContext.Fluid.SOURCE_ONLY);

			if (hitResult.getType() == HitResult.Type.MISS) {
				return InteractionResultHolder.pass(stack);
			} else {
				if (hitResult.getType() == HitResult.Type.BLOCK) {
					BlockPos blockPos = hitResult.getBlockPos();
					FluidState fluidState = world.getFluidState(blockPos);

					if (!fluidState.isEmpty()) {
						Entity entity = spawnCreature(world, blockPos.getX() + 0.5D, blockPos.getY(), blockPos.getZ() + 0.5D);

						if (entity != null) {
							if (entity instanceof Mob mob && stack.has(net.minecraft.core.component.DataComponents.CUSTOM_NAME)) {
								mob.setCustomName(stack.getHoverName());
							}

							if (!player.getAbilities().instabuild) {
								stack.shrink(1);
							}
						}
					}
				}

				return InteractionResultHolder.pass(stack);
			}
		}
	}

	public Entity spawnCreature(Level world, double x, double y, double z) {
		Entity entity = null;

		if (this == ModItems.spawn_chopper)
			entity = new EntityHunterChopper(world);

		if (this == ModItems.spawn_worm)
			entity = new EntityBOTPrimeHead(world);

		if (this == ModItems.spawn_ufo) {
			entity = new EntityUFO(world);
			((EntityUFO) entity).scanCooldown = 100;
			y += 35;
		}

		if (this == ModItems.spawn_duck)
			entity = new EntityDuck(world);

		if (entity != null) {
			entity.moveTo(x, y, z, Mth.wrapDegrees(world.random.nextFloat() * 360.0F), 0.0F);
			if (entity instanceof Mob mob) {
				mob.yHeadRot = mob.getYRot();
				mob.yBodyRot = mob.getYRot();
				if (world instanceof net.minecraft.world.level.ServerLevelAccessor serverLevel) {
					mob.finalizeSpawn(serverLevel, world.getCurrentDifficultyAt(BlockPos.containing(x, y, z)), MobSpawnType.SPAWN_EGG, null);
				}
			}
			world.addFreshEntity(entity);
		}

		return entity;
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
		if (this == ModItems.spawn_worm) {
			tooltip.add(Component.literal("Without a player in survival mode"));
			tooltip.add(Component.literal("to target, he struggles around a lot."));
			tooltip.add(Component.literal(""));
			tooltip.add(Component.literal("He's doing his best so please show him"));
			tooltip.add(Component.literal("some consideration."));
		}
	}
}
