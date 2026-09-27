package com.hbm.hazard;

import com.hbm.blocks.ModBlocks;
import com.hbm.hazard.modifier.HazardModifierFuelRadiation;
import com.hbm.hazard.modifier.HazardModifierRBMKHot;
import com.hbm.hazard.modifier.HazardModifierRBMKRadiation;
import com.hbm.hazard.modifier.HazardModifierRTGRadiation;
import com.hbm.hazard.transformer.HazardTransformerRadiationNBT;
import com.hbm.hazard.type.*;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemBreedingRod;
import com.hbm.items.machine.ItemPWRFuel;
import com.hbm.items.machine.ItemZirnoxRod;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

public class HazardRegistry {

    // Constants & standard isotope rates
    public static final float co60 = 30.0F;
    public static final float sr90 = 15.0F;
    public static final float tc99 = 2.75F;
    public static final float i131 = 150.0F;
    public static final float xe135 = 1250.0F;
    public static final float cs137 = 20.0F;
    public static final float au198 = 500.0F;
    public static final float pb209 = 10000.0F;
    public static final float at209 = 7500.0F;
    public static final float po210 = 75.0F;
    public static final float ra226 = 7.5F;
    public static final float ac227 = 30.0F;
    public static final float th232 = 0.1F;
    public static final float thf = 1.75F;
    public static final float u = 0.35F;
    public static final float u233 = 5.0F;
    public static final float u235 = 1.0F;
    public static final float u238 = 0.25F;
    public static final float uf = 0.5F;
    public static final float uzh = 0.125F;
    public static final float np237 = 2.5F;
    public static final float npf = 1.5F;
    public static final float pu = 7.5F;
    public static final float purg = 6.25F;
    public static final float pu238 = 10.0F;
    public static final float pu239 = 5.0F;
    public static final float pu240 = 7.5F;
    public static final float pu241 = 25.0F;
    public static final float puf = 4.25F;
    public static final float am241 = 8.5F;
    public static final float am242 = 9.5F;
    public static final float amrg = 9.0F;
    public static final float amf = 4.75F;
    public static final float mox = 2.5F;
    public static final float sa326 = 15.0F;
    public static final float sa327 = 17.5F;
    public static final float saf = 5.85F;
    public static final float sas3 = 5F;
    public static final float gh336 = 5.0F;
    public static final float mud = 1.0F;
    public static final float radsource_mult = 3.0F;
    public static final float pobe = po210 * radsource_mult;
    public static final float rabe = ra226 * radsource_mult;
    public static final float pube = pu238 * radsource_mult;
    public static final float zfb_bi = u235 * 0.35F;
    public static final float zfb_pu241 = pu241 * 0.5F;
    public static final float zfb_am_mix = amrg * 0.5F;
    public static final float bf = 300_000.0F;
    public static final float bfb = 500_000.0F;

    public static final float sr = sa326 * 0.1F;
    public static final float sb = sa326 * 0.1F;
    public static final float trx = 25.0F;
    public static final float trn = 0.1F;
    public static final float wst = 15.0F;
    public static final float wstv = 7.5F;
    public static final float yc = u;
    public static final float fo = 10F;

    public static final float nugget = 0.1F;
    public static final float ingot = 1.0F;
    public static final float gem = 1.0F;
    public static final float plate = ingot;
    public static final float plateCast = plate * 3;
    public static final float powder_mult = 3.0F;
    public static final float powder = ingot * powder_mult;
    public static final float powder_tiny = nugget * powder_mult;
    public static final float ore = ingot;
    public static final float block = 10.0F;
    public static final float crystal = block;
    public static final float billet = 0.5F;
    public static final float rtg = billet * 3;
    public static final float rod = 0.5F;
    public static final float rod_dual = rod * 2;
    public static final float rod_quad = rod * 4;
    public static final float rod_rbmk = rod * 8;

    public static final IHazardType RADIATION = new HazardTypeRadiation();
    public static final IHazardType CONTAMINATING = new HazardTypeContaminating();
    public static final IHazardType DIGAMMA = new HazardTypeDigamma();
    public static final IHazardType HOT = new HazardTypeHot();
    public static final IHazardType BLINDING = new HazardTypeBlinding();
    public static final IHazardType ASBESTOS = new HazardTypeAsbestos();
    public static final IHazardType COAL = new HazardTypeCoal();
    public static final IHazardType HYDROACTIVE = new HazardTypeHydroactive();
    public static final IHazardType EXPLOSIVE = new HazardTypeExplosive();
    public static final IHazardType TOXIC = new HazardTypeToxic();
    public static final IHazardType COLD = new HazardTypeCold();

    public static HazardData makeData() { return new HazardData(); }
    public static HazardData makeData(IHazardType hazard) { return new HazardData().addEntry(hazard); }
    public static HazardData makeData(IHazardType hazard, double level) { return new HazardData().addEntry(hazard, level); }
    public static HazardData makeData(IHazardType hazard, double level, boolean override) { return new HazardData().addEntry(hazard, level, override); }

    public static void registerItems() {
        // Explosives
        HazardSystem.register(Items.GUNPOWDER, makeData(EXPLOSIVE, 1F));
        HazardSystem.register(Blocks.TNT, makeData(EXPLOSIVE, 4F));
        HazardSystem.register(Items.PUMPKIN_PIE, makeData(EXPLOSIVE, 1F));

        // Coal & dust
        HazardSystem.register("dustCoal", makeData(COAL, powder));
        HazardSystem.register("dustTinyCoal", makeData(COAL, powder_tiny));
        HazardSystem.register("dustLignite", makeData(COAL, powder));
        HazardSystem.register("dustTinyLignite", makeData(COAL, powder_tiny));
        HazardSystem.register(ModItems.powder_coal, makeData(COAL, powder));

        // Radiative & nuclear items
        HazardSystem.register(ModItems.ingot_uranium, makeData(RADIATION, u * ingot));
        HazardSystem.register(ModItems.powder_uranium, makeData(RADIATION, u * powder).addEntry(CONTAMINATING, u * powder));
        HazardSystem.register(ModItems.nugget_uranium, makeData(RADIATION, u * nugget));
        HazardSystem.register(ModItems.powder_yellowcake, makeData(RADIATION, yc * powder).addEntry(CONTAMINATING, yc * powder));
        HazardSystem.register(ModItems.powder_plutonium, makeData(RADIATION, pu * powder).addEntry(CONTAMINATING, pu * powder));
        HazardSystem.register(ModItems.powder_thorium, makeData(RADIATION, th232 * powder).addEntry(CONTAMINATING, th232 * powder));
        HazardSystem.register(ModItems.powder_schrabidium, makeData().addEntry(RADIATION, sa326 * powder).addEntry(BLINDING, 5F * powder).addEntry(CONTAMINATING, sa326 * powder));
        HazardSystem.register(ModItems.nugget_schrabidium, makeData().addEntry(RADIATION, sa326 * nugget).addEntry(BLINDING, 5F * nugget));
        HazardSystem.register(ModItems.crystal_schrabidium, makeData().addEntry(RADIATION, sa326 * crystal).addEntry(BLINDING, 5F * crystal));
        HazardSystem.register(ModItems.nugget_neptunium, makeData(RADIATION, np237 * nugget));
        HazardSystem.register(ModItems.nugget_ra226, makeData(RADIATION, ra226 * nugget));
        HazardSystem.register(ModItems.ingot_schraranium, makeData(RADIATION, (sa326 + u) * 0.5F));
        HazardSystem.register(ModItems.pellet_rtg, makeData(RADIATION, pu238 * rtg));

        // Nuke bomb parts
        HazardSystem.register(ModItems.boy_propellant, makeData(EXPLOSIVE, 2F));
        HazardSystem.register(ModItems.gadget_core, makeData(RADIATION, pu239 * nugget * 10));
        HazardSystem.register(ModItems.boy_target, makeData(RADIATION, u235 * ingot * 2));
        HazardSystem.register(ModItems.boy_bullet, makeData(RADIATION, u235 * ingot));
        HazardSystem.register(ModItems.man_core, makeData(RADIATION, pu239 * nugget * 10));
        HazardSystem.register(ModItems.mike_core, makeData(RADIATION, u238 * nugget * 10));
        HazardSystem.register(ModItems.tsar_core, makeData(RADIATION, pu239 * nugget * 15));

        // Asbestos
        HazardSystem.register(ModBlocks.reinforced_stone, makeData(ASBESTOS, 1F));
        HazardSystem.register(ModBlocks.reinforced_brick, makeData(ASBESTOS, 1F));

        // Crystals & Hot
        HazardSystem.register(ModItems.ingot_phosphorus, makeData(HOT, 2F));
        HazardSystem.register(ModBlocks.block_uranium, makeData(RADIATION, u * block));
    }

    public static void registerTrafos() {
        HazardSystem.trafos.add(new HazardTransformerRadiationNBT());
    }

    public static void registerRBMKPellet(Item pellet, float base, float dep) { registerRBMKPellet(pellet, base, dep, false, 0F, 0F); }
    public static void registerRBMKPellet(Item pellet, float base, float dep, boolean linear, float blinding, float digamma) {
        HazardData data = new HazardData();
        data.addEntry(new HazardEntry(RADIATION, base).addMod(new HazardModifierRBMKRadiation(dep, linear)));
        if (blinding > 0) data.addEntry(new HazardEntry(BLINDING, blinding));
        if (digamma > 0) data.addEntry(new HazardEntry(DIGAMMA, digamma));
        HazardSystem.register(pellet, data);
    }

    public static void registerRBMKRod(Item rod, float base, float dep) { registerRBMK(rod, base, dep, true, false, 0F, 0F); }
    public static void registerRBMKRod(Item rod, float base, float dep, float blinding) { registerRBMK(rod, base, dep, true, false, blinding, 0F); }

    public static void registerRBMK(Item rod, float base, float dep, boolean hot, boolean linear, float blinding, float digamma) {
        HazardData data = new HazardData();
        data.addEntry(new HazardEntry(RADIATION, base).addMod(new HazardModifierRBMKRadiation(dep, linear)));
        if (hot) data.addEntry(new HazardEntry(HOT, 0).addMod(new HazardModifierRBMKHot()));
        if (blinding > 0) data.addEntry(new HazardEntry(BLINDING, blinding));
        if (digamma > 0) data.addEntry(new HazardEntry(DIGAMMA, digamma));
        HazardSystem.register(rod, data);
    }

    public static void registerRTGPellet(Item pellet, float base, float target, float hot, float blinding) {
        HazardData data = new HazardData();
        data.addEntry(new HazardEntry(RADIATION, base).addMod(new HazardModifierRTGRadiation(target)));
        if (hot > 0) data.addEntry(new HazardEntry(HOT, hot));
        if (blinding > 0) data.addEntry(new HazardEntry(BLINDING, blinding));
        HazardSystem.register(pellet, data);
    }

    public static void registerOtherFuel(Item fuel, float base, float target, boolean blinding) {
        HazardData data = new HazardData();
        data.addEntry(new HazardEntry(RADIATION, base).addMod(new HazardModifierFuelRadiation(target)));
        if (blinding) data.addEntry(BLINDING, 20F);
        HazardSystem.register(fuel, data);
    }
}
