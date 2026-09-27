package com.hbm.items.special;

import com.hbm.api.energymk2.IBatteryItem;
import com.hbm.items.ItemBase;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import com.hbm.util.I18nUtil;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Random;

public class ItemGlitch extends ItemBase implements IBatteryItem {

	private static final Random rand = new Random();

	public ItemGlitch(Properties properties) {
		super(properties.stacksTo(1).durability(1));
	}

	public ItemGlitch() {
		this(new Properties().stacksTo(1).durability(1));
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);

		if (!world.isClientSide()) {
			switch (rand.nextInt(31)) {
				case 0 -> player.displayClientMessage(Component.translatable("chat.glitch.0"), false);
				case 1 -> player.displayClientMessage(Component.translatable("chat.glitch.1"), false);
				case 2, 3, 4 -> player.hurt(world.damageSources().genericKill(), 1000);
				case 8 -> {
					player.getInventory().add(new ItemStack(ModItems.ammo_container, 10));
					player.displayClientMessage(Component.translatable("chat.glitch.8"), false);
				}
				case 9 -> player.getInventory().add(new ItemStack(ModItems.nuke_advanced_kit, 1));
				case 10 -> player.getInventory().add(new ItemStack(ModItems.nuke_starter_kit, 1));
				case 13 -> {
					player.getInventory().add(new ItemStack(ModItems.bottle_rad));
					player.getInventory().add(new ItemStack(ModItems.geiger_counter));
					player.displayClientMessage(Component.translatable("chat.glitch.13a"), false);
					player.displayClientMessage(Component.translatable("chat.glitch.13b"), false);
				}
				case 14 -> {
					player.getInventory().dropAll();
					world.explode(null, player.getX(), player.getY(), player.getZ(), 5.0F, Level.ExplosionInteraction.BLOCK);
				}
				case 15 -> {
					for (int i = 0; i < 36; i++)
						player.getInventory().add(new ItemStack(net.minecraft.world.level.block.Blocks.DIRT, 64));
				}
				case 16 -> player.displayClientMessage(Component.translatable("chat.glitch.16"), false);
				case 17 -> player.displayClientMessage(Component.translatable("chat.glitch.17"), false);
				case 18 -> player.displayClientMessage(Component.translatable("chat.glitch.18"), false);
				case 19 -> player.displayClientMessage(Component.translatable("chat.glitch.19"), false);
				case 20 -> player.displayClientMessage(Component.translatable("chat.glitch.20"), false);
				case 21 -> {
					player.getInventory().add(new ItemStack(ModItems.missile_nuclear));
					player.displayClientMessage(Component.translatable("chat.glitch.21"), false);
				}
				case 22 -> player.displayClientMessage(Component.translatable("chat.glitch.22"), false);
				case 23 -> player.displayClientMessage(Component.translatable("chat.glitch.23"), false);
				case 24 -> {
					player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 60 * 20, 9));
					player.displayClientMessage(Component.translatable("chat.glitch.24"), false);
				}
				case 25 -> {
					player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 60 * 20, 9));
					player.displayClientMessage(Component.translatable("chat.glitch.25"), false);
				}
				case 26 -> {
					player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60 * 20, 9));
					player.displayClientMessage(Component.translatable("chat.glitch.26"), false);
				}
				case 27 -> world.explode(null, player.getX(), player.getY() - 5, player.getZ(), 8.0F, Level.ExplosionInteraction.BLOCK);
				case 29 -> {
					world.explode(null, player.getX(), player.getY(), player.getZ(), 27.0F, Level.ExplosionInteraction.BLOCK);
					player.displayClientMessage(Component.translatable("chat.glitch.29"), false);
				}
				case 30 -> {
					player.getInventory().add(new ItemStack(ModItems.plate_saturnite));
					player.displayClientMessage(Component.translatable("chat.glitch.30"), false);
				}
			}
		}

		return InteractionResultHolder.sidedSuccess(stack, world.isClientSide());
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> list, TooltipFlag flagIn) {
		list.add(Component.literal(I18nUtil.resolveKey("desc.glitch")));
		list.add(Component.literal(""));
		if (MainRegistry.polaroidID > 0 && MainRegistry.polaroidID < 19)
			list.add(Component.literal(I18nUtil.resolveKey("desc.glitch." + MainRegistry.polaroidID)));
	}

	@Override public void chargeBattery(ItemStack stack, long i) {}
	@Override public void setCharge(ItemStack stack, long i) {}
	@Override public void dischargeBattery(ItemStack stack, long i) {}
	@Override public long getCharge(ItemStack stack) { return 200; }
	@Override public long getMaxCharge(ItemStack stack) { return 200; }
	@Override public long getChargeRate(ItemStack stack) { return 0; }
	@Override public long getDischargeRate(ItemStack stack) { return 200; }
}
