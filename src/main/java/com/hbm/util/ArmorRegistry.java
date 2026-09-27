package com.hbm.util;

import com.hbm.api.item.IGasMask;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.*;

public class ArmorRegistry {

    public static final HashMap<Item, ArrayList<HazardClass>> hazardClasses = new HashMap<>();

    public static void registerHazard(Item item, HazardClass... hazards) {
        hazardClasses.put(item, new ArrayList<>(Arrays.asList(hazards)));
    }

    public static boolean hasAllProtection(LivingEntity entity, EquipmentSlot slot, HazardClass... clazz) {
        ItemStack stack = entity.getItemBySlot(slot);
        if (stack.isEmpty()) return false;

        List<HazardClass> list = getProtectionFromItem(stack);
        return list.containsAll(Arrays.asList(clazz));
    }

    public static boolean hasAnyProtection(LivingEntity entity, EquipmentSlot slot, HazardClass... clazz) {
        ItemStack stack = entity.getItemBySlot(slot);
        if (stack.isEmpty()) return false;

        List<HazardClass> list = getProtectionFromItem(stack);
        if (list == null) return false;

        for (HazardClass haz : clazz) {
            if (list.contains(haz)) return true;
        }
        return false;
    }

    public static boolean hasProtection(LivingEntity entity, EquipmentSlot slot, HazardClass clazz) {
        ItemStack stack = entity.getItemBySlot(slot);
        if (stack.isEmpty()) return false;

        List<HazardClass> list = getProtectionFromItem(stack);
        if (list == null) return false;

        return list.contains(clazz);
    }

    public static List<HazardClass> getProtectionFromItem(ItemStack stack) {
        List<HazardClass> prot = new ArrayList<>();
        Item item = stack.getItem();

        if (hazardClasses.containsKey(item)) {
            prot.addAll(hazardClasses.get(item));
        }

        if (item instanceof IGasMask mask) {
            ItemStack filter = mask.getFilter(stack);
            if (!filter.isEmpty()) {
                ArrayList<HazardClass> filProtList = hazardClasses.get(filter.getItem());
                if (filProtList != null) {
                    List<HazardClass> filProt = new ArrayList<>(filProtList);
                    for (HazardClass c : mask.getBlacklist(stack)) {
                        filProt.remove(c);
                    }
                    prot.addAll(filProt);
                }
            }
        }

        return prot;
    }

    public enum HazardClass {
        GAS_LUNG("hazard.gasChlorine"),
        GAS_MONOXIDE("hazard.gasMonoxide"),
        GAS_INERT("hazard.gasInert"),
        PARTICLE_COARSE("hazard.particleCoarse"),
        PARTICLE_FINE("hazard.particleFine"),
        BACTERIA("hazard.bacteria"),
        NERVE_AGENT("hazard.nerveAgent"),
        GAS_BLISTERING("hazard.corrosive"),
        SAND("hazard.sand"),
        LIGHT("hazard.light");

        public final String lang;

        HazardClass(String lang) {
            this.lang = lang;
        }
    }
}
