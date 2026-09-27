package com.hbm.inventory;

import com.hbm.hazard.HazardData;
import com.hbm.hazard.HazardEntry;
import com.hbm.hazard.HazardRegistry;
import com.hbm.hazard.HazardSystem;
import com.hbm.inventory.material.MaterialShapes;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.NTMMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.*;

import static com.hbm.inventory.material.MaterialShapes.*;

public class OreDictManager {

    /* Standard keys */
    public static final String KEY_STICK = "stickWood";
    public static final String KEY_ANYGLASS = "blockGlass";
    public static final String KEY_CLEARGLASS = "blockGlassColorless";
    public static final String KEY_ANYPANE = "paneGlass";
    public static final String KEY_CLEARPANE = "paneGlassColorless";
    public static final String KEY_BRICK = "ingotBrick";
    public static final String KEY_NETHERBRICK = "ingotBrickNether";
    public static final String KEY_SLIME = "slimeball";
    public static final String KEY_LOG = "logWood";
    public static final String KEY_PLANKS = "plankWood";
    public static final String KEY_SLAB = "slabWood";
    public static final String KEY_LEAVES = "treeLeaves";
    public static final String KEY_SAPLING = "treeSapling";
    public static final String KEY_SAND = "sand";
    public static final String KEY_STONE = "stone";
    public static final String KEY_COBBLESTONE = "cobblestone";
    public static final String KEY_GRAVEL = "gravel";
    public static final String KEY_BLACK = "dyeBlack";
    public static final String KEY_RED = "dyeRed";
    public static final String KEY_GREEN = "dyeGreen";
    public static final String KEY_BROWN = "dyeBrown";
    public static final String KEY_BLUE = "dyeBlue";
    public static final String KEY_PURPLE = "dyePurple";
    public static final String KEY_CYAN = "dyeCyan";
    public static final String KEY_LIGHTGRAY = "dyeLightGray";
    public static final String KEY_GRAY = "dyeGray";
    public static final String KEY_PINK = "dyePink";
    public static final String KEY_LIME = "dyeLime";
    public static final String KEY_YELLOW = "dyeYellow";
    public static final String KEY_LIGHTBLUE = "dyeLightBlue";
    public static final String KEY_MAGENTA = "dyeMagenta";
    public static final String KEY_ORANGE = "dyeOrange";
    public static final String KEY_WHITE = "dyeWhite";
    public static final String KEY_OIL_TAR = "oiltar";
    public static final String KEY_CRACK_TAR = "cracktar";
    public static final String KEY_COAL_TAR = "coaltar";
    public static final String KEY_WOOD_TAR = "woodtar";
    public static final String KEY_UNIVERSAL_TANK = "ntmuniversaltank";
    public static final String KEY_HAZARD_TANK = "ntmhazardtank";
    public static final String KEY_UNIVERSAL_BARREL = "ntmuniversalbarrel";
    public static final String KEY_TOOL_SCREWDRIVER = "ntmscrewdriver";
    public static final String KEY_TOOL_HANDDRILL = "ntmhanddrill";
    public static final String KEY_TOOL_CHEMISTRYSET = "ntmchemistryset";
    public static final String KEY_TOOL_TORCH = "ntmtorch";
    public static final String KEY_GLYPHID_MEAT = "glyphidMeat";

    /* Vanilla */
    public static final DictFrame WOOD = new DictFrame("Wood");
    public static final DictFrame BONE = new DictFrame("Bone");
    public static final DictFrame COAL = new DictFrame("Coal");
    public static final DictFrame IRON = new DictFrame("Iron");
    public static final DictFrame GOLD = new DictFrame("Gold");
    public static final DictFrame LAPIS = new DictFrame("Lapis");
    public static final DictFrame REDSTONE = new DictFrame("Redstone");
    public static final DictFrame QUARTZ = new DictFrame("Quartz");
    public static final DictFrame NETHERQUARTZ = new DictFrame("NetherQuartz");
    public static final DictFrame DIAMOND = new DictFrame("Diamond");
    public static final DictFrame EMERALD = new DictFrame("Emerald");

    /* Radioactive */
    public static final DictFrame U = new DictFrame("Uranium");
    public static final DictFrame U233 = new DictFrame("Uranium233", "U233");
    public static final DictFrame U235 = new DictFrame("Uranium235", "U235");
    public static final DictFrame U238 = new DictFrame("Uranium238", "U238");
    public static final DictFrame TH232 = new DictFrame("Thorium232", "Th232", "Thorium");
    public static final DictFrame PU = new DictFrame("Plutonium");
    public static final DictFrame PURG = new DictFrame("PlutoniumRG");
    public static final DictFrame PU238 = new DictFrame("Plutonium238", "Pu238");
    public static final DictFrame PU239 = new DictFrame("Plutonium239", "Pu239");
    public static final DictFrame PU240 = new DictFrame("Plutonium240", "Pu240");
    public static final DictFrame PU241 = new DictFrame("Plutonium241", "Pu241");
    public static final DictFrame AM241 = new DictFrame("Americium241", "Am241");
    public static final DictFrame AM242 = new DictFrame("Americium242", "Am242");
    public static final DictFrame AMRG = new DictFrame("AmericiumRG");
    public static final DictFrame NP237 = new DictFrame("Neptunium237", "Np237", "Neptunium");
    public static final DictFrame PO210 = new DictFrame("Polonium210", "Po210", "Polonium");
    public static final DictFrame TC99 = new DictFrame("Technetium99", "Tc99");
    public static final DictFrame RA226 = new DictFrame("Radium226", "Ra226");
    public static final DictFrame AC227 = new DictFrame("Actinium227", "Ac227");
    public static final DictFrame CO60 = new DictFrame("Cobalt60", "Co60");
    public static final DictFrame AU198 = new DictFrame("Gold198", "Au198");
    public static final DictFrame PB209 = new DictFrame("Lead209", "Pb209");
    public static final DictFrame SA326 = new DictFrame("Schrabidium");
    public static final DictFrame SA327 = new DictFrame("Solinium");
    public static final DictFrame SBD = new DictFrame("Schrabidate");
    public static final DictFrame SRN = new DictFrame("Schraranium");
    public static final DictFrame GH336 = new DictFrame("Ghiorsium336", "Gh336");
    public static final DictFrame MUD = new DictFrame("WatzMud");

    /* Radioactive Fuels */
    public static final DictFrame U_FUEL = new DictFrame("UraniumFuel");
    public static final DictFrame TH_FUEL = new DictFrame("ThoriumFuel");
    public static final DictFrame PU_FUEL = new DictFrame("PlutoniumFuel");
    public static final DictFrame NP_FUEL = new DictFrame("NeptuniumFuel");
    public static final DictFrame MOX_FUEL = new DictFrame("MoxFuel", "Mox");
    public static final DictFrame AM_FUEL = new DictFrame("AmericiumFuel");
    public static final DictFrame SCH_FUEL = new DictFrame("ScharbidiumFuel");
    public static final DictFrame LES_FUEL = new DictFrame("LesFuel", "Les");
    public static final DictFrame HES_FUEL = new DictFrame("HesFuel", "Hes");

    /* Stable Metals */
    public static final DictFrame NITANIUM = new DictFrame("Nitanium");
    public static final DictFrame TI = new DictFrame("Titanium");
    public static final DictFrame CU = new DictFrame("Copper");
    public static final DictFrame MINGRADE = new DictFrame("Mingrade");
    public static final DictFrame W = new DictFrame("Tungsten");
    public static final DictFrame WC = new DictFrame("TungstenCarbide");
    public static final DictFrame AL = new DictFrame("Aluminum");
    public static final DictFrame STEEL = new DictFrame("Steel");
    public static final DictFrame TCALLOY = new DictFrame("TcAlloy");
    public static final DictFrame CDALLOY = new DictFrame("CdAlloy");
    public static final DictFrame BBRONZE = new DictFrame("BismuthBronze");
    public static final DictFrame ABRONZE = new DictFrame("ArsenicBronze");
    public static final DictFrame BSCCO = new DictFrame("BSCCO");
    public static final DictFrame PB = new DictFrame("Lead");
    public static final DictFrame BI = new DictFrame("Bismuth");
    public static final DictFrame CD = new DictFrame("Cadmium");
    public static final DictFrame AS = new DictFrame("Arsenic");
    public static final DictFrame CA = new DictFrame("Calcium");
    public static final DictFrame TA = new DictFrame("Tantalum");
    public static final DictFrame COLTAN = new DictFrame("Coltan");
    public static final DictFrame NB = new DictFrame("Niobium");
    public static final DictFrame BE = new DictFrame("Beryllium");
    public static final DictFrame CO = new DictFrame("Cobalt");
    public static final DictFrame B = new DictFrame("Boron");
    public static final DictFrame SI = new DictFrame("Silicon");
    public static final DictFrame GRAPHITE = new DictFrame("Graphite");
    public static final DictFrame CARBON = new DictFrame("Carbon");
    public static final DictFrame DURA = new DictFrame("DuraSteel");
    public static final DictFrame POLYMER = new DictFrame("Polymer");
    public static final DictFrame BAKELITE = new DictFrame("Bakelite");
    public static final DictFrame PET = new DictFrame("PET");
    public static final DictFrame PC = new DictFrame("Polycarbonate");
    public static final DictFrame PVC = new DictFrame("PVC");
    public static final DictFrame LATEX = new DictFrame("Latex");
    public static final DictFrame RUBBER = new DictFrame("Rubber");
    public static final DictFrame MAGTUNG = new DictFrame("MagnetizedTungsten");
    public static final DictFrame CMB = new DictFrame("CMBSteel");
    public static final DictFrame DESH = new DictFrame("WorkersAlloy");
    public static final DictFrame STAR = new DictFrame("Starmetal");
    public static final DictFrame GUNMETAL = new DictFrame("GunMetal");
    public static final DictFrame WEAPONSTEEL = new DictFrame("WeaponSteel");
    public static final DictFrame BIGMT = new DictFrame("Saturnite");
    public static final DictFrame FERRO = new DictFrame("Ferrouranium");
    public static final DictFrame EUPH = new DictFrame("Euphemium");
    public static final DictFrame DNT = new DictFrame("Dineutronium");
    public static final DictFrame FIBER = new DictFrame("Fiberglass");
    public static final DictFrame ASBESTOS = new DictFrame("Asbestos");
    public static final DictFrame OSMIRIDIUM = new DictFrame("Osmiridium");
    public static final DictFrame S = new DictFrame("Sulfur");

    /* Dust and Gem Ores */
    public static final DictFrame KNO = new DictFrame("Saltpeter");
    public static final DictFrame F = new DictFrame("Fluorite");
    public static final DictFrame LIGNITE = new DictFrame("Lignite");
    public static final DictFrame COALCOKE = new DictFrame("CoalCoke");
    public static final DictFrame PETCOKE = new DictFrame("PetCoke");
    public static final DictFrame LIGCOKE = new DictFrame("LigniteCoke");
    public static final DictFrame CINNABAR = new DictFrame("Cinnabar");
    public static final DictFrame BORAX = new DictFrame("Borax");
    public static final DictFrame CHLOROCALCITE = new DictFrame("Chlorocalcite");
    public static final DictFrame MOLYSITE = new DictFrame("Molysite");
    public static final DictFrame SODALITE = new DictFrame("Sodalite");
    public static final DictFrame VOLCANIC = new DictFrame("Volcanic");
    public static final DictFrame HEMATITE = new DictFrame("Hematite");
    public static final DictFrame MALACHITE = new DictFrame("Malachite");
    public static final DictFrame LIMESTONE = new DictFrame("Limestone");
    public static final DictFrame SLAG = new DictFrame("Slag");
    public static final DictFrame INFERNAL = new DictFrame("InfernalCoal");
    public static final DictFrame BAUXITE = new DictFrame("Bauxite");
    public static final DictFrame CRYOLITE = new DictFrame("Cryolite");
    public static final DictFrame ALEXANDRITE = new DictFrame("Alexandrite");

    /* Hazards, Misc */
    public static final DictFrame LI = new DictFrame("Lithium");
    public static final DictFrame NA = new DictFrame("Sodium");
    public static final DictFrame P_WHITE = new DictFrame("WhitePhosphorus");
    public static final DictFrame P_RED = new DictFrame("RedPhosphorus");
    public static final DictFrame AUSTRALIUM = new DictFrame("Australium");
    public static final DictFrame RAREEARTH = new DictFrame("RareEarth");
    public static final DictFrame LA = new DictFrame("Lanthanum");
    public static final DictFrame AC = new DictFrame("Actinium");
    public static final DictFrame ZR = new DictFrame("Zirconium");
    public static final DictFrame ND = new DictFrame("Neodymium");
    public static final DictFrame CE = new DictFrame("Cerium");
    public static final DictFrame I = new DictFrame("Iodine");
    public static final DictFrame AT = new DictFrame("Astatine");
    public static final DictFrame CS = new DictFrame("Caesium");
    public static final DictFrame SR = new DictFrame("Strontium");
    public static final DictFrame BR = new DictFrame("Bromine");
    public static final DictFrame TS = new DictFrame("Tennessine");
    public static final DictFrame SR90 = new DictFrame("Strontium90", "Sr90");
    public static final DictFrame I131 = new DictFrame("Iodine131", "I131");
    public static final DictFrame XE135 = new DictFrame("Xenon135", "Xe135");
    public static final DictFrame CS137 = new DictFrame("Caesium137", "Cs137");
    public static final DictFrame AT209 = new DictFrame("Astatine209", "At209");

    public static final DictGroup ANY_RUBBER = new DictGroup("AnyRubber", LATEX, RUBBER);
    public static final DictGroup ANY_PLASTIC = new DictGroup("AnyPlastic", POLYMER, BAKELITE);
    public static final DictGroup ANY_HARDPLASTIC = new DictGroup("AnyHardPlastic", PC, PVC);
    public static final DictGroup ANY_BISMOIDBRONZE = new DictGroup("AnyBismoidBronze", BBRONZE, ABRONZE);
    public static final DictGroup ANY_RESISTANTALLOY = new DictGroup("AnyResistantAlloy", TCALLOY, CDALLOY);
    public static final DictFrame ANY_GUNPOWDER = new DictFrame("AnyPropellant");
    public static final DictFrame ANY_SMOKELESS = new DictFrame("AnySmokeless");
    public static final DictFrame ANY_PLASTICEXPLOSIVE = new DictFrame("AnyPlasticexplosive");
    public static final DictFrame ANY_HIGHEXPLOSIVE = new DictFrame("AnyHighexplosive");
    public static final DictFrame ANY_COKE = new DictFrame("AnyCoke", "Coke");
    public static final DictFrame ANY_CONCRETE = new DictFrame("Concrete");
    public static final DictGroup ANY_TAR = new DictGroup("Tar", KEY_OIL_TAR, KEY_COAL_TAR, KEY_CRACK_TAR, KEY_WOOD_TAR);
    public static final DictGroup ANY_BISMOID = new DictGroup("AnyBismoid", BI, AS);
    public static final DictFrame ANY_ASH = new DictFrame("Ash");

    public static final Map<String, List<ItemStack>> TAG_REGISTRY = new HashMap<>();

    public static class DictFrame {
        public String[] mats;
        public List<HazardEntry> hazards = new ArrayList<>();
        public float hazMult = 1.0F;

        public DictFrame(String... mats) {
            this.mats = mats;
        }

        public static DictFrame fromOne(String name) {
            return new DictFrame(name);
        }

        public static DictFrame fromAll(String... names) {
            return new DictFrame(names);
        }

        public DictFrame rad(float rad) {
            return this.haz(new HazardEntry(HazardRegistry.RADIATION, rad));
        }

        public DictFrame hot(float time) {
            return this.haz(new HazardEntry(HazardRegistry.HOT, time));
        }

        public DictFrame blinding(float time) {
            return this.haz(new HazardEntry(HazardRegistry.BLINDING, time));
        }

        public DictFrame asbestos(float asb) {
            return this.haz(new HazardEntry(HazardRegistry.ASBESTOS, asb));
        }

        public DictFrame hydro(float h) {
            return this.haz(new HazardEntry(HazardRegistry.HYDROACTIVE, h));
        }

        public DictFrame coal(float h) {
            return this.haz(new HazardEntry(HazardRegistry.COAL, h));
        }

        public DictFrame explosive(float h) {
            return this.haz(new HazardEntry(HazardRegistry.EXPLOSIVE, h));
        }

        public DictFrame digamma(float h) {
            return this.haz(new HazardEntry(HazardRegistry.DIGAMMA, h));
        }

        public DictFrame haz(HazardEntry hazard) {
            hazards.add(hazard);
            return this;
        }

        public DictFrame any(Object... thing) {
            return makeObject(ANY, thing);
        }

        public DictFrame nugget(Object... nugget) {
            hazMult = HazardRegistry.nugget;
            return makeObject(NUGGET, nugget).makeObject(TINY, nugget);
        }

        public DictFrame ingot(Object... ingot) {
            hazMult = HazardRegistry.ingot;
            return makeObject(INGOT, ingot);
        }

        public DictFrame dustSmall(Object... dustSmall) {
            hazMult = HazardRegistry.powder_tiny;
            return makeObject(DUSTTINY, dustSmall);
        }

        public DictFrame dust(Object... dust) {
            hazMult = HazardRegistry.powder;
            return makeObject(DUST, dust);
        }

        public DictFrame gem(Object... gem) {
            hazMult = HazardRegistry.gem;
            return makeObject(GEM, gem);
        }

        public DictFrame crystal(Object... crystal) {
            hazMult = HazardRegistry.crystal;
            return makeObject(CRYSTAL, crystal);
        }

        public DictFrame plate(Object... plate) {
            hazMult = HazardRegistry.plate;
            return makeObject(PLATE, plate);
        }

        public DictFrame plateCast(Object... plate) {
            hazMult = HazardRegistry.plateCast;
            return makeObject(CASTPLATE, plate);
        }

        public DictFrame billet(Object... billet) {
            hazMult = HazardRegistry.billet;
            return makeObject(BILLET, billet);
        }

        public DictFrame block(Object... block) {
            hazMult = HazardRegistry.block;
            return makeObject(BLOCK, block);
        }

        public DictFrame ore(Object... ore) {
            hazMult = HazardRegistry.ore;
            return makeObject(ORE, ore);
        }

        public DictFrame oreNether(Object... oreNether) {
            hazMult = HazardRegistry.ore;
            return makeObject(ORENETHER, oreNether);
        }

        public DictFrame makeObject(MaterialShapes shape, Object... objects) {
            String tag = shape.name();
            for (Object o : objects) {
                if (o instanceof Item item) registerStack(tag, new ItemStack(item));
                if (o instanceof Block block) registerStack(tag, new ItemStack(block));
                if (o instanceof ItemStack stack) registerStack(tag, stack);
            }
            return this;
        }

        public void registerStack(String tag, ItemStack stack) {
            HazardData sharedData = buildSharedHazardData();
            for (String mat : mats) {
                String key = tag + mat;
                TAG_REGISTRY.computeIfAbsent(key, k -> new ArrayList<>()).add(stack);
                if (sharedData != null) HazardSystem.register(key, sharedData);
            }
            if ("ingot".equals(tag)) {
                for (String mat : mats) {
                    TAG_REGISTRY.computeIfAbsent(mat, k -> new ArrayList<>()).add(stack);
                    if (sharedData != null) HazardSystem.register(mat, sharedData);
                }
            }
        }

        private HazardData buildSharedHazardData() {
            if (hazards.isEmpty() || hazMult <= 0F) return null;
            HazardData data = new HazardData().setMutex(0b1);
            for (HazardEntry hazard : hazards) {
                data.addEntry(hazard.clone(hazMult));
            }
            return data;
        }

        public String any() { return ANY.name() + mats[0]; }
        public String nugget() { return NUGGET.name() + mats[0]; }
        public String tiny() { return TINY.name() + mats[0]; }
        public String bolt() { return BOLT.name() + mats[0]; }
        public String ingot() { return INGOT.name() + mats[0]; }
        public String dustTiny() { return DUSTTINY.name() + mats[0]; }
        public String dust() { return DUST.name() + mats[0]; }
        public String gem() { return GEM.name() + mats[0]; }
        public String crystal() { return CRYSTAL.name() + mats[0]; }
        public String plate() { return PLATE.name() + mats[0]; }
        public String plateCast() { return CASTPLATE.name() + mats[0]; }
        public String plateWelded() { return WELDEDPLATE.name() + mats[0]; }
        public String heavyComp() { return HEAVY_COMPONENT.name() + mats[0]; }
        public String wireFine() { return WIRE.name() + mats[0]; }
        public String wireDense() { return DENSEWIRE.name() + mats[0]; }
        public String billet() { return BILLET.name() + mats[0]; }
        public String block() { return BLOCK.name() + mats[0]; }
        public String ore() { return ORE.name() + mats[0]; }
        public String lightBarrel() { return LIGHTBARREL.name() + mats[0]; }
        public String heavyBarrel() { return HEAVYBARREL.name() + mats[0]; }
        public String lightReceiver() { return LIGHTRECEIVER.name() + mats[0]; }
        public String heavyReceiver() { return HEAVYRECEIVER.name() + mats[0]; }
        public String mechanism() { return MECHANISM.name() + mats[0]; }
        public String stock() { return STOCK.name() + mats[0]; }
        public String grip() { return GRIP.name() + mats[0]; }
        public String wire() { return WIRE.name() + mats[0]; }
        public String part() { return PART.name() + mats[0]; }
    }

    public static class DictGroup {
        private final String groupName;
        private final HashSet<String> names = new HashSet<>();

        public DictGroup(String groupName) {
            this.groupName = groupName;
        }

        public DictGroup(String groupName, String... names) {
            this(groupName);
            this.addNames(names);
        }

        public DictGroup(String groupName, DictFrame... frames) {
            this(groupName);
            this.addFrames(frames);
        }

        public DictGroup addNames(String... names) {
            Collections.addAll(this.names, names);
            return this;
        }

        public DictGroup addFrames(DictFrame... frames) {
            for (DictFrame frame : frames) this.addNames(frame.mats);
            return this;
        }

        public String any() { return ANY.name() + groupName; }
        public String nugget() { return NUGGET.name() + groupName; }
        public String tiny() { return TINY.name() + groupName; }
        public String bolt() { return BOLT.name() + groupName; }
        public String ingot() { return INGOT.name() + groupName; }
        public String dustTiny() { return DUSTTINY.name() + groupName; }
        public String dust() { return DUST.name() + groupName; }
        public String gem() { return GEM.name() + groupName; }
        public String crystal() { return CRYSTAL.name() + groupName; }
        public String plate() { return PLATE.name() + groupName; }
        public String plateCast() { return CASTPLATE.name() + groupName; }
        public String plateWelded() { return WELDEDPLATE.name() + groupName; }
        public String heavyComp() { return HEAVY_COMPONENT.name() + groupName; }
        public String wireFine() { return WIRE.name() + groupName; }
        public String wireDense() { return DENSEWIRE.name() + groupName; }
        public String billet() { return BILLET.name() + groupName; }
        public String block() { return BLOCK.name() + groupName; }
        public String ore() { return ORE.name() + groupName; }
        public String lightBarrel() { return LIGHTBARREL.name() + groupName; }
        public String heavyBarrel() { return HEAVYBARREL.name() + groupName; }
        public String lightReceiver() { return LIGHTRECEIVER.name() + groupName; }
        public String heavyReceiver() { return HEAVYRECEIVER.name() + groupName; }
        public String mechanism() { return MECHANISM.name() + groupName; }
        public String stock() { return STOCK.name() + groupName; }
        public String grip() { return GRIP.name() + groupName; }
        public String wire() { return WIRE.name() + groupName; }
        public String part() { return PART.name() + groupName; }
    }
}
