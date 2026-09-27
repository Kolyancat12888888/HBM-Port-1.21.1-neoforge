package com.hbm.inventory;

import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.tileentity.IUpgradeInfoProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.IItemHandler;

import java.util.Arrays;
import java.util.HashMap;

public class UpgradeManagerNT {

    public BlockEntity owner;
    public ItemStack[] cachedSlots;

    public HashMap<UpgradeType, Integer> upgrades = new HashMap<>();

    public UpgradeManagerNT(BlockEntity te) {
        this.owner = te;
    }

    public void checkSlots(IItemHandler inventory, int start, int end) {
        ItemStack[] allSlots = new ItemStack[inventory.getSlots()];
        for (int i = 0; i < inventory.getSlots(); i++) {
            allSlots[i] = inventory.getStackInSlot(i);
        }
        checkSlotsInternal(owner, allSlots, start, end);
    }

    public void checkSlots(ItemStack[] slots, int start, int end) {
        checkSlotsInternal(owner, slots, start, end);
    }

    private void checkSlotsInternal(BlockEntity te, ItemStack[] slots, int start, int end) {
        if (!(te instanceof IUpgradeInfoProvider upgradable) || slots == null)
            return;

        ItemStack[] upgradeSlots = Arrays.copyOfRange(slots, start, end + 1);

        if (Arrays.equals(upgradeSlots, cachedSlots))
            return;

        cachedSlots = upgradeSlots.clone();
        upgrades.clear();

        for (int i = 0; i <= end - start; i++) {
            if (upgradeSlots[i] != null && !upgradeSlots[i].isEmpty() && upgradeSlots[i].getItem() instanceof ItemMachineUpgrade item) {
                if (upgradable.getValidUpgrades() == null)
                    return;

                if (upgradable.getValidUpgrades().containsKey(item.type)) {
                    Integer levelBefore = upgrades.get(item.type);
                    int upgradeLevel = (levelBefore == null ? 0 : levelBefore);
                    upgradeLevel += item.tier;
                    upgrades.put(item.type, Math.min(upgradeLevel, upgradable.getValidUpgrades().get(item.type)));
                }
            }
        }
    }

    public Integer getLevel(UpgradeType type) {
        return upgrades.getOrDefault(type, 0);
    }
}
