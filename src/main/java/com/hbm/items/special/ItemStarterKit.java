package com.hbm.items.special;

import com.hbm.items.ItemBase;
import com.hbm.items.ModItems;
import com.hbm.util.I18nUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class ItemStarterKit extends ItemBase {

    public ItemStarterKit(Properties properties) {
        super(properties.stacksTo(1));
    }

    public ItemStarterKit() {
        super(new Properties().stacksTo(1));
    }

    private void giveHaz(Level world, Player p, int tier) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.isArmor()) {
                ItemStack cur = p.getItemBySlot(slot);
                if (!cur.isEmpty() && !world.isClientSide()) {
                    p.drop(cur.copy(), false);
                }
            }
        }
        switch (tier) {
            case 0 -> {
                p.setItemSlot(EquipmentSlot.HEAD, new ItemStack(ModItems.hazmat_helmet));
                p.setItemSlot(EquipmentSlot.CHEST, new ItemStack(ModItems.hazmat_plate));
                p.setItemSlot(EquipmentSlot.LEGS, new ItemStack(ModItems.hazmat_legs));
                p.setItemSlot(EquipmentSlot.FEET, new ItemStack(ModItems.hazmat_boots));
            }
            case 1 -> {
                p.setItemSlot(EquipmentSlot.HEAD, new ItemStack(ModItems.hazmat_helmet_red));
                p.setItemSlot(EquipmentSlot.CHEST, new ItemStack(ModItems.hazmat_plate_red));
                p.setItemSlot(EquipmentSlot.LEGS, new ItemStack(ModItems.hazmat_legs_red));
                p.setItemSlot(EquipmentSlot.FEET, new ItemStack(ModItems.hazmat_boots_red));
            }
            case 2 -> {
                p.setItemSlot(EquipmentSlot.HEAD, new ItemStack(ModItems.hazmat_helmet_grey));
                p.setItemSlot(EquipmentSlot.CHEST, new ItemStack(ModItems.hazmat_plate_grey));
                p.setItemSlot(EquipmentSlot.LEGS, new ItemStack(ModItems.hazmat_legs_grey));
                p.setItemSlot(EquipmentSlot.FEET, new ItemStack(ModItems.hazmat_boots_grey));
            }
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!world.isClientSide()) {
            if (this == ModItems.nuke_starter_kit) {
                player.getInventory().add(new ItemStack(ModItems.ingot_uranium, 32));
                player.getInventory().add(new ItemStack(ModItems.powder_yellowcake, 32));
                player.getInventory().add(new ItemStack(ModItems.template_folder, 1));
                player.getInventory().add(new ItemStack(ModItems.radaway, 8));
                player.getInventory().add(new ItemStack(ModItems.radx, 2));
                player.getInventory().add(new ItemStack(ModItems.ingot_steel, 64));
                player.getInventory().add(new ItemStack(ModItems.ingot_lead, 64));
                player.getInventory().add(new ItemStack(ModItems.ingot_copper, 64));
                player.getInventory().add(new ItemStack(ModItems.gas_mask_m65, 1));
                player.getInventory().add(new ItemStack(ModItems.geiger_counter, 1));
                giveHaz(world, player, 1);
            }

            if (this == ModItems.nuke_advanced_kit) {
                player.getInventory().add(new ItemStack(ModItems.powder_yellowcake, 64));
                player.getInventory().add(new ItemStack(ModItems.powder_plutonium, 64));
                player.getInventory().add(new ItemStack(ModItems.ingot_steel, 64));
                player.getInventory().add(new ItemStack(ModItems.ingot_copper, 64));
                player.getInventory().add(new ItemStack(ModItems.ingot_tungsten, 64));
                player.getInventory().add(new ItemStack(ModItems.ingot_lead, 64));
                player.getInventory().add(new ItemStack(ModItems.ingot_polymer, 64));
                player.getInventory().add(new ItemStack(ModItems.pellet_rtg, 3));
                player.getInventory().add(new ItemStack(ModItems.cell, 32));
                player.getInventory().add(new ItemStack(ModItems.rod_empty, 32));
                player.getInventory().add(new ItemStack(ModItems.radaway_strong, 4));
                player.getInventory().add(new ItemStack(ModItems.radx, 4));
                player.getInventory().add(new ItemStack(ModItems.pill_iodine, 1));
                player.getInventory().add(new ItemStack(ModItems.geiger_counter, 1));
                player.getInventory().add(new ItemStack(ModItems.survey_scanner, 1));
                player.getInventory().add(new ItemStack(ModItems.gas_mask_m65, 1));
                giveHaz(world, player, 2);
            }

            if (this == ModItems.gadget_kit) {
                player.getInventory().add(new ItemStack(ModItems.early_explosive_lenses, 4));
                player.getInventory().add(new ItemStack(ModItems.gadget_wireing, 1));
                player.getInventory().add(new ItemStack(ModItems.gadget_core, 1));
                giveHaz(world, player, 0);
            }

            if (this == ModItems.boy_kit) {
                player.getInventory().add(new ItemStack(ModItems.boy_shielding, 1));
                player.getInventory().add(new ItemStack(ModItems.boy_target, 1));
                player.getInventory().add(new ItemStack(ModItems.boy_bullet, 1));
                player.getInventory().add(new ItemStack(ModItems.boy_propellant, 1));
                player.getInventory().add(new ItemStack(ModItems.boy_igniter, 1));
                giveHaz(world, player, 0);
            }

            if (this == ModItems.man_kit) {
                player.getInventory().add(new ItemStack(ModItems.early_explosive_lenses, 4));
                player.getInventory().add(new ItemStack(ModItems.man_igniter, 1));
                player.getInventory().add(new ItemStack(ModItems.man_core, 1));
                giveHaz(world, player, 0);
            }

            if (this == ModItems.mike_kit) {
                player.getInventory().add(new ItemStack(ModItems.explosive_lenses, 4));
                player.getInventory().add(new ItemStack(ModItems.man_core, 1));
                player.getInventory().add(new ItemStack(ModItems.mike_core, 1));
                player.getInventory().add(new ItemStack(ModItems.mike_deut, 1));
                player.getInventory().add(new ItemStack(ModItems.mike_cooling_unit, 1));
                giveHaz(world, player, 1);
            }

            if (this == ModItems.tsar_kit) {
                player.getInventory().add(new ItemStack(ModItems.explosive_lenses, 4));
                player.getInventory().add(new ItemStack(ModItems.man_core, 1));
                player.getInventory().add(new ItemStack(ModItems.tsar_core, 1));
                giveHaz(world, player, 2);
            }

            if (this == ModItems.hazmat_kit) giveHaz(world, player, 0);
            if (this == ModItems.hazmat_red_kit) giveHaz(world, player, 1);
            if (this == ModItems.hazmat_grey_kit) giveHaz(world, player, 2);

            if (this == ModItems.stealth_boy) {
                player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 30 * 20, 1, false, false));
            }

            if (this == ModItems.euphemium_kit) {
                player.getInventory().add(new ItemStack(ModItems.euphemium_helmet, 1));
                player.getInventory().add(new ItemStack(ModItems.euphemium_plate, 1));
                player.getInventory().add(new ItemStack(ModItems.euphemium_legs, 1));
                player.getInventory().add(new ItemStack(ModItems.euphemium_boots, 1));
            }
        }

        world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BUNDLE_DROP_CONTENTS, SoundSource.PLAYERS, 1.0F, 1.0F);
        stack.shrink(1);
        return InteractionResultHolder.sidedSuccess(stack, world.isClientSide());
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
        if (this == ModItems.gadget_kit ||
                this == ModItems.boy_kit ||
                this == ModItems.man_kit ||
                this == ModItems.mike_kit ||
                this == ModItems.tsar_kit ||
                this == ModItems.prototype_kit ||
                this == ModItems.fleija_kit ||
                this == ModItems.solinium_kit ||
                this == ModItems.balefire_kit ||
                this == ModItems.grenade_kit ||
                this == ModItems.missile_kit ||
                this == ModItems.t45_kit ||
                this == ModItems.multi_kit) {
            tooltip.add(Component.literal(I18nUtil.resolveKey("desc.kit.inventory")));
        }
        if (this == ModItems.nuke_starter_kit ||
                this == ModItems.nuke_advanced_kit ||
                this == ModItems.gadget_kit ||
                this == ModItems.boy_kit ||
                this == ModItems.man_kit ||
                this == ModItems.mike_kit ||
                this == ModItems.tsar_kit ||
                this == ModItems.prototype_kit ||
                this == ModItems.fleija_kit ||
                this == ModItems.solinium_kit ||
                this == ModItems.balefire_kit ||
                this == ModItems.hazmat_kit ||
                this == ModItems.hazmat_red_kit ||
                this == ModItems.hazmat_grey_kit) {
            tooltip.add(Component.literal(I18nUtil.resolveKey("desc.kit.armor")));
        }
    }
}
