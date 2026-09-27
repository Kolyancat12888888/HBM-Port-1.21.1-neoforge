package com.hbm.items.special;

import com.hbm.inventory.material.MaterialShapes;
import com.hbm.inventory.material.NTMMaterial;
import com.hbm.items.ItemBase;
import com.hbm.util.I18nUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;

import java.util.List;
import java.util.Locale;

import static com.hbm.inventory.material.Mats.*;
import static com.hbm.items.special.ItemBedrockOreNew.ProcessingTrait.*;

public class ItemBedrockOreNew extends ItemBase {

    protected final BedrockOreGrade grade;
    protected final BedrockOreType type;

    public ItemBedrockOreNew(Properties properties, BedrockOreGrade grade, BedrockOreType type) {
        super(properties);
        this.grade = grade;
        this.type = type;
    }

    public ItemBedrockOreNew(BedrockOreGrade grade, BedrockOreType type) {
        this(new Properties(), grade, type);
    }

    public BedrockOreGrade getGrade() {
        return grade;
    }

    public BedrockOreType getType() {
        return type;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        for (ProcessingTrait trait : grade.traits) {
            tooltipComponents.add(Component.literal(I18nUtil.resolveKey(this.getDescriptionId() + ".trait." + trait.name().toLowerCase(Locale.US))));
        }
    }

    public static class BedrockOreOutput {
        public NTMMaterial mat;
        public int amount;
        public BedrockOreOutput(NTMMaterial mat, int amount) {
            this.mat = mat;
            this.amount = amount;
        }
    }

    public static BedrockOreOutput o(NTMMaterial mat, int amount) {
        return new BedrockOreOutput(mat, amount);
    }

    public enum BedrockOreType {
        LIGHT_METAL(	0xFFFFFF, 0x353535, "light",	o(MAT_IRON, 9),		o(MAT_COPPER, 9),	o(MAT_TITANIUM, 6),	o(MAT_BAUXITE, 9),	o(MAT_CRYOLITE, 3),	o(MAT_CHLOROCALCITE, 5),	o(MAT_LITHIUM, 5),		o(MAT_SODIUM, 3),		o(MAT_CHLOROCALCITE, 6),	o(MAT_LITHIUM, 6),		o(MAT_SODIUM, 6)),
        HEAVY_METAL(	0x868686, 0x000000, "heavy",	o(MAT_TUNGSTEN, 9),	o(MAT_LEAD, 9),		o(MAT_GOLD, 2),		o(MAT_GOLD, 2),			o(MAT_BERYLLIUM, 3),	o(MAT_TUNGSTEN, 9),			o(MAT_LEAD, 9),			o(MAT_GOLD, 5),			o(MAT_BISMUTH, 2),			o(MAT_TANTALIUM, 2),	o(MAT_GOLD, 6)),
        RARE_EARTH(		0xE6E6B6, 0x1C1C00, "rare",		o(MAT_COBALT, 5),	o(MAT_RAREEARTH, 5),o(MAT_BORON, 5),	o(MAT_LANTHANIUM, 3),	o(MAT_NIOBIUM, 4),		o(MAT_NEODYMIUM, 3),		o(MAT_STRONTIUM, 3),	o(MAT_ZIRCONIUM, 3),	o(MAT_NIOBIUM, 5),			o(MAT_NEODYMIUM, 5),	o(MAT_STRONTIUM, 3)),
        ACTINIDE(		0xC1C7BD, 0x2B3227, "actinide",	o(MAT_URANIUM, 4),	o(MAT_THORIUM, 4),	o(MAT_RADIUM, 2),	o(MAT_RADIUM, 2),		o(MAT_POLONIUM, 2),		o(MAT_RADIUM, 2),			o(MAT_RADIUM, 2),		o(MAT_POLONIUM, 2),		o(MAT_TECHNETIUM, 1),		o(MAT_TECHNETIUM, 1),	o(MAT_U238, 1)),
        NON_METAL(		0xAFAFAF, 0x0F0F0F, "nonmetal",	o(MAT_COAL, 9),		o(MAT_SULFUR, 9),	o(MAT_LIGNITE, 9),	o(MAT_KNO, 6),			o(MAT_FLUORITE, 6),		o(MAT_PHOSPHORUS, 5),		o(MAT_FLUORITE, 6),		o(MAT_SULFUR, 6),		o(MAT_CHLOROCALCITE, 6),	o(MAT_SILICON, 2),		o(MAT_SILICON, 2)),
        CRYSTALLINE(	0xE2FFFA, 0x1E8A77, "crystal",	o(MAT_REDSTONE, 9),	o(MAT_CINNABAR, 4),	o(MAT_SODALITE, 9),	o(MAT_ASBESTOS, 6),		o(MAT_DIAMOND, 3),		o(MAT_CINNABAR, 3),			o(MAT_ASBESTOS, 5),		o(MAT_EMERALD, 3),		o(MAT_BORAX, 3),			o(MAT_MOLYSITE, 3),		o(MAT_SODALITE, 9));

        public static final BedrockOreType[] VALUES = values();

        public final int light;
        public final int dark;
        public final String suffix;
        public final BedrockOreOutput primary1;
        public final BedrockOreOutput primary2;
        public final BedrockOreOutput byproductAcid1;
        public final BedrockOreOutput byproductAcid2;
        public final BedrockOreOutput byproductAcid3;
        public final BedrockOreOutput byproductSolvent1;
        public final BedrockOreOutput byproductSolvent2;
        public final BedrockOreOutput byproductSolvent3;
        public final BedrockOreOutput byproductRad1;
        public final BedrockOreOutput byproductRad2;
        public final BedrockOreOutput byproductRad3;

        BedrockOreType(int light, int dark, String suffix, BedrockOreOutput p1, BedrockOreOutput p2, BedrockOreOutput bA1, BedrockOreOutput bA2, BedrockOreOutput bA3, BedrockOreOutput bS1, BedrockOreOutput bS2, BedrockOreOutput bS3, BedrockOreOutput bR1, BedrockOreOutput bR2, BedrockOreOutput bR3) {
            this.light = light;
            this.dark = dark;
            this.suffix = suffix;
            this.primary1 = p1; this.primary2 = p2;
            this.byproductAcid1 = bA1; this.byproductAcid2 = bA2; this.byproductAcid3 = bA3;
            this.byproductSolvent1 = bS1; this.byproductSolvent2 = bS2; this.byproductSolvent3 = bS3;
            this.byproductRad1 = bR1; this.byproductRad2 = bR2; this.byproductRad3 = bR3;
        }
    }

    public static final int none = 0xFFFFFF;
    public static final int roasted = 0xCFCFCF;
    public static final int arc = 0xC3A2A2;
    public static final int washed = 0xDBE2CB;

    public enum ProcessingTrait {
        ROASTED,
        ARC,
        WASHED,
        CENTRIFUGED,
        SULFURIC,
        SOLVENT,
        RAD;

        public static final ProcessingTrait[] VALUES = values();
    }

    public enum BedrockOreGrade {
        BASE(none, "base"),
        BASE_ROASTED(roasted, "base", ROASTED),
        BASE_WASHED(washed, "base", WASHED),
        PRIMARY(none, "primary", CENTRIFUGED),
        PRIMARY_ROASTED(roasted, "primary", ROASTED),
        PRIMARY_SULFURIC(0xFFFFD3, "primary", SULFURIC),
        PRIMARY_NOSULFURIC(0xD3D4FF, "primary", CENTRIFUGED, SULFURIC),
        PRIMARY_SOLVENT(0xD3F0FF, "primary", SOLVENT),
        PRIMARY_NOSOLVENT(0xFFDED3, "primary", CENTRIFUGED, SOLVENT),
        PRIMARY_RAD(0xECFFD3, "primary", RAD),
        PRIMARY_NORAD(0xEBD3FF, "primary", CENTRIFUGED, RAD),
        PRIMARY_FIRST(0xFFD3D4, "primary", CENTRIFUGED),
        PRIMARY_SECOND(0xD3FFEB, "primary", CENTRIFUGED),
        CRUMBS(none, "crumbs", CENTRIFUGED),

        SULFURIC_BYPRODUCT(none, "sulfuric", CENTRIFUGED, SULFURIC),
        SULFURIC_ROASTED(roasted, "sulfuric", ROASTED, SULFURIC),
        SULFURIC_ARC(arc, "sulfuric", ARC, SULFURIC),
        SULFURIC_WASHED(washed, "sulfuric", WASHED, SULFURIC),

        SOLVENT_BYPRODUCT(none, "solvent", CENTRIFUGED, SOLVENT),
        SOLVENT_ROASTED(roasted, "solvent", ROASTED, SOLVENT),
        SOLVENT_ARC(arc, "solvent", ARC, SOLVENT),
        SOLVENT_WASHED(washed, "solvent", WASHED, SOLVENT),

        RAD_BYPRODUCT(none, "rad", CENTRIFUGED, RAD),
        RAD_ROASTED(roasted, "rad", ROASTED, RAD),
        RAD_ARC(arc, "rad", ARC, RAD),
        RAD_WASHED(washed, "rad", WASHED, RAD);

        public static final BedrockOreGrade[] VALUES = values();

        public final int tint;
        public final String prefix;
        public final ProcessingTrait[] traits;

        BedrockOreGrade(int tint, String prefix, ProcessingTrait... traits) {
            this.tint = tint;
            this.prefix = prefix;
            this.traits = traits;
        }
    }
}
