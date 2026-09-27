package com.hbm.hazard.transformer;

import com.hbm.hazard.HazardEntry;
import com.hbm.hazard.HazardRegistry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

import java.util.List;

public class HazardTransformerRadiationNBT implements IHazardTransformer {

    public static final String RAD_KEY = "hfrHazRadiation";

    @Override
    public void transformPre(final ItemStack stack, final List<HazardEntry> entries) {
    }

    @Override
    public void transformPost(final ItemStack stack, final List<HazardEntry> entries) {
        CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
        if (customData != null && customData.contains(RAD_KEY)) {
            entries.add(new HazardEntry(HazardRegistry.RADIATION, customData.copyTag().getFloat(RAD_KEY)));
        }
    }
}
