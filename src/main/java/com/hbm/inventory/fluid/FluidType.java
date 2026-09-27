package com.hbm.inventory.fluid;

import com.hbm.inventory.fluid.trait.FluidTrait;
import com.hbm.main.MainRegistry;
import com.hbm.render.misc.EnumSymbol;
import com.hbm.util.I18nUtil;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class FluidType {

    private int id;
    private String stringId;
    private int color;
    private String unlocalized;
    private String localizedOverride;
    private int guiTint = 0xffffff;

    public int poison;
    public int flammability;
    public int reactivity;
    public EnumSymbol symbol;
    public boolean renderWithTint = false;
    public boolean ffBan = false;

    public static final int ROOM_TEMPERATURE = 20;
    public int temperature = ROOM_TEMPERATURE;

    public HashMap<Class<?>, Object> containers = new HashMap<>();
    public HashMap<Class<? extends FluidTrait>, FluidTrait> traits = new HashMap<>();

    private ResourceLocation texture;

    public FluidType(String name, int color, int p, int f, int r, EnumSymbol symbol) {
        this.stringId = name;
        this.color = color;
        this.unlocalized = "hbmfluid." + name.toLowerCase(Locale.US);
        this.poison = p;
        this.flammability = f;
        this.reactivity = r;
        this.symbol = symbol;
        this.texture = ResourceLocation.fromNamespaceAndPath(MainRegistry.MODID, "textures/gui/fluids/" + name.toLowerCase(Locale.US) + ".png");

        this.id = Fluids.registerSelf(this);
    }

    public FluidType(int forcedId, String name, int color, int p, int f, int r, EnumSymbol symbol) {
        this.stringId = name;
        this.color = color;
        this.unlocalized = "hbmfluid." + name.toLowerCase(Locale.US);
        this.poison = p;
        this.flammability = f;
        this.reactivity = r;
        this.symbol = symbol;
        this.texture = ResourceLocation.fromNamespaceAndPath(MainRegistry.MODID, "textures/gui/fluids/" + name.toLowerCase(Locale.US) + ".png");

        this.id = forcedId;
        Fluids.register(this, forcedId);
    }

    public int getID() {
        return this.id;
    }

    public String getName() {
        return this.stringId;
    }

    public int getColor() {
        return this.color;
    }

    public int getTint() {
        return this.guiTint;
    }

    public String getUnlocalizedName() {
        return this.unlocalized;
    }

    public String getTranslationKey() {
        return this.unlocalized;
    }

    public String getConditionalName() {
        if (this.localizedOverride != null) return this.localizedOverride;
        return this.unlocalized;
    }

    public String getLocalizedName() {
        if (this.localizedOverride != null) return this.localizedOverride;
        return I18nUtil.resolveKey(this.unlocalized);
    }

    public ResourceLocation getTexture() {
        return this.texture;
    }

    public FluidType addTraits(FluidTrait... traits) {
        for (FluidTrait trait : traits) {
            this.traits.put(trait.getClass(), trait);
        }
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T extends FluidTrait> T getTrait(Class<T> clazz) {
        return (T) this.traits.get(clazz);
    }

    public boolean hasTrait(Class<? extends FluidTrait> clazz) {
        return this.traits.containsKey(clazz);
    }

    public FluidType setTemperature(int temp) {
        this.temperature = temp;
        return this;
    }

    public int getTemperature() {
        return this.temperature;
    }

    public void addInfo(List<net.minecraft.network.chat.Component> list) {
        if (this.traits != null) {
            List<String> strList = new java.util.ArrayList<>();
            for (FluidTrait trait : this.traits.values()) {
                trait.addInfo(strList);
            }
            for (String str : strList) {
                list.add(net.minecraft.network.chat.Component.literal(str));
            }
        }
    }
}
