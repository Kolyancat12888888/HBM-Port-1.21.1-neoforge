package com.hbm.tileentity;

import com.hbm.items.machine.ItemMachineUpgrade;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;
import java.util.List;

public interface IUpgradeInfoProvider {

    boolean canProvideInfo(ItemMachineUpgrade.UpgradeType type, int level, boolean extendedInfo);
    void provideInfo(ItemMachineUpgrade.UpgradeType type, int level, List<String> info, boolean extendedInfo);
    HashMap<ItemMachineUpgrade.UpgradeType, Integer> getValidUpgrades();

    static Component getStandardLabel(Block block) {
        return Component.literal(">>> ").withStyle(ChatFormatting.YELLOW)
                .append(block.getName())
                .append(Component.literal(" <<<").withStyle(ChatFormatting.YELLOW));
    }

    String KEY_ACID = "upgrade.acid";
    String KEY_BURN = "upgrade.burn";
    String KEY_CONSUMPTION = "upgrade.consumption";
    String KEY_COOLANT_CONSUMPTION = "upgrade.coolantConsumption";
    String KEY_DELAY = "upgrade.delay";
    String KEY_SPEED = "upgrade.speed";
    String KEY_EFFICIENCY = "upgrade.efficiency";
    String KEY_PRODUCTIVITY = "upgrade.productivity";
    String KEY_FORTUNE = "upgrade.fortune";
    String KEY_RANGE = "upgrade.range";
}
