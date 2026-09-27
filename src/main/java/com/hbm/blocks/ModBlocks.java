package com.hbm.blocks;

import com.hbm.main.MainRegistry;
import com.hbm.blocks.machine.CoreComponent;
import com.hbm.blocks.machine.CoreCore;
import com.hbm.blocks.machine.ReactorZirnox;
import com.hbm.blocks.machine.ZirnoxDestroyed;
import com.hbm.blocks.machine.rbmk.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ModBlocks {

	public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MainRegistry.MODID);
	public static final List<Block> ALL_BLOCKS = new ArrayList<>();

	private static <T extends Block> DeferredBlock<T> register(String name, Supplier<T> supplier) {
		return BLOCKS.register(name, () -> {
			T block = supplier.get();
			ALL_BLOCKS.add(block);
			return block;
		});
	}

	private static DeferredBlock<Block> reg(String name, float hardness, float resistance) {
		return register(name, () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(hardness, resistance)));
	}

	private static DeferredBlock<Block> regStone(String name, float hardness, float resistance) {
		return register(name, () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(hardness, resistance)));
	}

	// Ores & Strata
	public static final DeferredBlock<Block> ORE_OIL = regStone("ore_oil", 3.0F, 5.0F);
	public static final DeferredBlock<Block> ORE_COLTAN = regStone("ore_coltan", 5.0F, 10.0F);
	public static final DeferredBlock<Block> ORE_AUSTRALIAM = regStone("ore_australium", 4.0F, 15.0F);
	public static final DeferredBlock<Block> STONE_DEPTH = regStone("stone_depth", 10.0F, 50.0F);
	public static final DeferredBlock<Block> STONE_DEPTH_NETHER = regStone("stone_depth_nether", 10.0F, 50.0F);
	public static final DeferredBlock<Block> STONE_GNEISS = regStone("stone_gneiss", 8.0F, 30.0F);

	// Metal Blocks
	public static final DeferredBlock<Block> BLOCK_BERYLLIUM = reg("block_beryllium", 5.0F, 10.0F);
	public static final DeferredBlock<Block> BLOCK_RED_COPPER = reg("block_red_copper", 4.0F, 10.0F);
	public static final DeferredBlock<Block> BLOCK_URANIUM = reg("block_uranium", 5.0F, 10.0F);
	public static final DeferredBlock<Block> BLOCK_STEEL = reg("block_steel", 5.0F, 15.0F);
	public static final DeferredBlock<Block> BLOCK_LEAD = reg("block_lead", 4.0F, 20.0F);

	// Concrete & Structural
	public static final DeferredBlock<Block> CONCRETE = regStone("concrete", 15.0F, 160.0F);
	public static final DeferredBlock<Block> CONCRETE_SMOOTH = regStone("concrete_smooth", 15.0F, 160.0F);
	public static final DeferredBlock<Block> BRICK_CONCRETE = regStone("brick_concrete", 15.0F, 160.0F);
	public static final DeferredBlock<Block> BRICK_CONCRETE_BROKEN = regStone("brick_concrete_broken", 10.0F, 80.0F);
	public static final DeferredBlock<Block> REINFORCED_STONE = regStone("reinforced_stone", 15.0F, 100.0F);
	public static final DeferredBlock<Block> REINFORCED_BRICK = regStone("reinforced_brick", 15.0F, 300.0F);

	// RBMK Reactor Columns
	public static final DeferredBlock<BlockRBMKRod> RBMK_ROD = register("rbmk_rod", BlockRBMKRod::new);
	public static final DeferredBlock<BlockRBMKControl> RBMK_CONTROL = register("rbmk_control", BlockRBMKControl::new);
	public static final DeferredBlock<BlockRBMKBoiler> RBMK_BOILER = register("rbmk_boiler", BlockRBMKBoiler::new);
	public static final DeferredBlock<BlockRBMKModerator> RBMK_MODERATOR = register("rbmk_moderator", BlockRBMKModerator::new);
	public static final DeferredBlock<BlockRBMKAbsorber> RBMK_ABSORBER = register("rbmk_absorber", BlockRBMKAbsorber::new);
	public static final DeferredBlock<BlockRBMKCooler> RBMK_COOLER = register("rbmk_cooler", BlockRBMKCooler::new);
	public static final DeferredBlock<BlockRBMKHeater> RBMK_HEATER = register("rbmk_heater", BlockRBMKHeater::new);
	public static final DeferredBlock<BlockRBMKOutgasser> RBMK_OUTGASSER = register("rbmk_outgasser", BlockRBMKOutgasser::new);
	public static final DeferredBlock<BlockRBMKDebris> RBMK_DEBRIS = register("rbmk_debris", BlockRBMKDebris::new);

	// DFC Dark Fusion Core Components
	public static final DeferredBlock<CoreCore> DFC_CORE = register("dfc_core", CoreCore::new);
	public static final DeferredBlock<CoreComponent> DFC_EMITTER = register("dfc_emitter", () -> new CoreComponent(CoreComponent.ComponentType.EMITTER));
	public static final DeferredBlock<CoreComponent> DFC_RECEIVER = register("dfc_receiver", () -> new CoreComponent(CoreComponent.ComponentType.RECEIVER));
	public static final DeferredBlock<CoreComponent> DFC_INJECTOR = register("dfc_injector", () -> new CoreComponent(CoreComponent.ComponentType.INJECTOR));
	public static final DeferredBlock<CoreComponent> DFC_STABILIZER = register("dfc_stabilizer", () -> new CoreComponent(CoreComponent.ComponentType.STABILIZER));

	// Zirnox Reactor
	public static final DeferredBlock<ReactorZirnox> REACTOR_ZIRNOX = register("reactor_zirnox", ReactorZirnox::new);
	public static final DeferredBlock<ZirnoxDestroyed> ZIRNOX_DESTROYED = register("zirnox_destroyed", ZirnoxDestroyed::new);

	// Processing Machinery
	public static final DeferredBlock<com.hbm.blocks.machine.BlockMachineCentrifuge> MACHINE_CENTRIFUGE = register("machine_centrifuge", () -> new com.hbm.blocks.machine.BlockMachineCentrifuge(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(5.0F, 10.0F)));
	public static final DeferredBlock<com.hbm.blocks.machine.BlockMachineBlastFurnace> MACHINE_BLAST_FURNACE = register("machine_blast_furnace", () -> new com.hbm.blocks.machine.BlockMachineBlastFurnace(BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS).strength(5.0F, 20.0F)));
	public static final DeferredBlock<com.hbm.blocks.machine.BlockMachineChemplant> MACHINE_CHEMPLANT = register("machine_chemplant", () -> new com.hbm.blocks.machine.BlockMachineChemplant(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(5.0F, 10.0F)));
	public static final DeferredBlock<com.hbm.blocks.machine.BlockMachineElectrolyser> MACHINE_ELECTROLYSER = register("machine_electrolyser", () -> new com.hbm.blocks.machine.BlockMachineElectrolyser(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(5.0F, 10.0F)));
	public static final DeferredBlock<com.hbm.blocks.machine.BlockMachineShredder> MACHINE_SHREDDER = register("machine_shredder", () -> new com.hbm.blocks.machine.BlockMachineShredder(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(5.0F, 10.0F)));
	public static final DeferredBlock<com.hbm.blocks.machine.BlockMachineCrystallizer> MACHINE_CRYSTALLIZER = register("machine_crystallizer", () -> new com.hbm.blocks.machine.BlockMachineCrystallizer(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(5.0F, 10.0F)));
	public static final DeferredBlock<com.hbm.blocks.machine.BlockMachineEPress> MACHINE_EPRESS = register("machine_epress", () -> new com.hbm.blocks.machine.BlockMachineEPress(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(5.0F, 10.0F)));
	public static final DeferredBlock<com.hbm.blocks.machine.BlockMachineAssemblyMachine> MACHINE_ASSEMBLY_MACHINE = register("machine_assembly_machine", () -> new com.hbm.blocks.machine.BlockMachineAssemblyMachine(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(5.0F, 10.0F)));
	public static final DeferredBlock<com.hbm.blocks.machine.BlockMachineSiren> MACHINE_SIREN = register("machine_siren", () -> new com.hbm.blocks.machine.BlockMachineSiren(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(3.0F, 10.0F)));
	public static final DeferredBlock<com.hbm.blocks.machine.BlockMachineBattery> MACHINE_BATTERY = register("machine_battery", () -> new com.hbm.blocks.machine.BlockMachineBattery(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(5.0F, 10.0F)));
	public static final DeferredBlock<com.hbm.blocks.machine.BlockMachineRTG> MACHINE_RTG = register("machine_rtg", () -> new com.hbm.blocks.machine.BlockMachineRTG(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(5.0F, 50.0F)));
	public static final DeferredBlock<com.hbm.blocks.machine.BlockMachineTurbine> MACHINE_TURBINE = register("machine_turbine", () -> new com.hbm.blocks.machine.BlockMachineTurbine(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(5.0F, 20.0F)));
	public static final DeferredBlock<com.hbm.blocks.machine.BlockMachineIndustrialTurbine> MACHINE_INDUSTRIAL_TURBINE = register("machine_industrial_turbine", () -> new com.hbm.blocks.machine.BlockMachineIndustrialTurbine(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(10.0F, 50.0F)));
	public static final DeferredBlock<com.hbm.blocks.machine.BlockMachineIGenerator> MACHINE_GENERATOR = register("machine_generator", () -> new com.hbm.blocks.machine.BlockMachineIGenerator(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(5.0F, 20.0F)));
	public static final DeferredBlock<com.hbm.blocks.machine.BlockMachineFluidTank> MACHINE_FLUID_TANK = register("machine_fluid_tank", () -> new com.hbm.blocks.machine.BlockMachineFluidTank(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(4.0F, 20.0F)));
	public static final DeferredBlock<com.hbm.blocks.machine.BlockMachineStorageTank> MACHINE_STORAGE_TANK = register("machine_storage_tank", () -> new com.hbm.blocks.machine.BlockMachineStorageTank(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(10.0F, 50.0F)));
	public static final DeferredBlock<com.hbm.blocks.machine.BlockMachineGasFlare> MACHINE_GAS_FLARE = register("machine_gas_flare", () -> new com.hbm.blocks.machine.BlockMachineGasFlare(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(3.0F, 10.0F)));
	public static final DeferredBlock<com.hbm.blocks.machine.BlockMachinePump> MACHINE_PUMP = register("machine_pump", () -> new com.hbm.blocks.machine.BlockMachinePump(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(5.0F, 20.0F)));

	// Power Grid & Fluid Logistics
	public static final DeferredBlock<com.hbm.blocks.network.BlockCable> CABLE = register("cable", () -> new com.hbm.blocks.network.BlockCable(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(1.0F, 2.0F)));
	public static final DeferredBlock<com.hbm.blocks.network.BlockPylon> PYLON = register("pylon", () -> new com.hbm.blocks.network.BlockPylon(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(5.0F, 50.0F)));
	public static final DeferredBlock<com.hbm.blocks.network.BlockPylon> PYLON_MEDIUM = register("pylon_medium", () -> new com.hbm.blocks.network.BlockPylon(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(8.0F, 80.0F)));
	public static final DeferredBlock<com.hbm.blocks.network.BlockPylon> PYLON_LARGE = register("pylon_large", () -> new com.hbm.blocks.network.BlockPylon(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(12.0F, 120.0F)));
	public static final DeferredBlock<com.hbm.blocks.network.BlockSubstation> SUBSTATION = register("substation", () -> new com.hbm.blocks.network.BlockSubstation(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(10.0F, 100.0F)));
	public static final DeferredBlock<com.hbm.blocks.network.BlockPipe> PIPE = register("pipe", () -> new com.hbm.blocks.network.BlockPipe(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(1.5F, 5.0F)));

	// Bombs & Silos
	public static final DeferredBlock<com.hbm.blocks.bomb.BlockBombMulti> BOMB_BOY = register("bomb_boy", () -> new com.hbm.blocks.bomb.BlockBombMulti(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(5.0F, 50.0F), com.hbm.tileentity.bomb.TileEntityBombMulti.BombType.BOY));
	public static final DeferredBlock<com.hbm.blocks.bomb.BlockBombMulti> BOMB_MAN = register("bomb_man", () -> new com.hbm.blocks.bomb.BlockBombMulti(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(5.0F, 50.0F), com.hbm.tileentity.bomb.TileEntityBombMulti.BombType.MAN));
	public static final DeferredBlock<com.hbm.blocks.bomb.BlockBombMulti> BOMB_MIKE = register("bomb_mike", () -> new com.hbm.blocks.bomb.BlockBombMulti(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(5.0F, 50.0F), com.hbm.tileentity.bomb.TileEntityBombMulti.BombType.MIKE));
	public static final DeferredBlock<com.hbm.blocks.bomb.BlockBombMulti> BOMB_TSAR = register("bomb_tsar", () -> new com.hbm.blocks.bomb.BlockBombMulti(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(5.0F, 50.0F), com.hbm.tileentity.bomb.TileEntityBombMulti.BombType.TSAR));
	public static final DeferredBlock<com.hbm.blocks.bomb.BlockBombMulti> BOMB_GADGET = register("bomb_gadget", () -> new com.hbm.blocks.bomb.BlockBombMulti(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(5.0F, 50.0F), com.hbm.tileentity.bomb.TileEntityBombMulti.BombType.GADGET));

	public static final DeferredBlock<com.hbm.blocks.bomb.BlockNukeBoy> NUKE_BOY = register("nuke_boy", () -> new com.hbm.blocks.bomb.BlockNukeBoy(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(5.0F, 6000.0F)));
	public static final DeferredBlock<com.hbm.blocks.bomb.BlockNukeMan> NUKE_MAN = register("nuke_man", () -> new com.hbm.blocks.bomb.BlockNukeMan(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(5.0F, 6000.0F)));
	public static final DeferredBlock<com.hbm.blocks.bomb.BlockNukeMike> NUKE_MIKE = register("nuke_mike", () -> new com.hbm.blocks.bomb.BlockNukeMike(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(5.0F, 6000.0F)));
	public static final DeferredBlock<com.hbm.blocks.bomb.BlockNukeTsar> NUKE_TSAR = register("nuke_tsar", () -> new com.hbm.blocks.bomb.BlockNukeTsar(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(5.0F, 6000.0F)));
	public static final DeferredBlock<com.hbm.blocks.bomb.BlockNukeGadget> NUKE_GADGET = register("nuke_gadget", () -> new com.hbm.blocks.bomb.BlockNukeGadget(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(5.0F, 6000.0F)));
	public static final DeferredBlock<com.hbm.blocks.bomb.BlockNukeFleija> NUKE_FLEIJA = register("nuke_fleija", () -> new com.hbm.blocks.bomb.BlockNukeFleija(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(5.0F, 6000.0F)));
	public static final DeferredBlock<com.hbm.blocks.bomb.BlockNukeBalefire> NUKE_BALEFIRE = register("nuke_fstbmb", () -> new com.hbm.blocks.bomb.BlockNukeBalefire(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(5.0F, 6000.0F)));
	public static final DeferredBlock<com.hbm.blocks.bomb.BlockNukeN2> NUKE_N2 = register("nuke_n2", () -> new com.hbm.blocks.bomb.BlockNukeN2(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(5.0F, 6000.0F)));
	public static final DeferredBlock<com.hbm.blocks.bomb.BlockNukeSolinium> NUKE_SOLINIUM = register("nuke_solinium", () -> new com.hbm.blocks.bomb.BlockNukeSolinium(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(5.0F, 6000.0F)));
	public static final DeferredBlock<com.hbm.blocks.bomb.BlockNukePrototype> NUKE_PROTOTYPE = register("nuke_prototype", () -> new com.hbm.blocks.bomb.BlockNukePrototype(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(5.0F, 6000.0F)));
	public static final DeferredBlock<com.hbm.blocks.bomb.BlockNukeCustom> NUKE_CUSTOM = register("nuke_custom", () -> new com.hbm.blocks.bomb.BlockNukeCustom(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(5.0F, 6000.0F)));

	public static final DeferredBlock<com.hbm.blocks.bomb.BlockLaunchPad> LAUNCH_PAD = register("launch_pad", () -> new com.hbm.blocks.bomb.BlockLaunchPad(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(10.0F, 100.0F)));
	public static final DeferredBlock<com.hbm.blocks.bomb.BlockLaunchPadLarge> LAUNCH_PAD_LARGE = register("launch_pad_large", () -> new com.hbm.blocks.bomb.BlockLaunchPadLarge(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(10.0F, 100.0F)));
	public static final DeferredBlock<com.hbm.blocks.bomb.BlockLaunchTable> LAUNCH_TABLE = register("launch_table", () -> new com.hbm.blocks.bomb.BlockLaunchTable(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(10.0F, 100.0F)));
	public static final DeferredBlock<com.hbm.blocks.bomb.BlockCompactLauncher> COMPACT_LAUNCHER = register("compact_launcher", () -> new com.hbm.blocks.bomb.BlockCompactLauncher(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(10.0F, 100.0F)));

	// Fallout, Waste & Post-Explosion Strata
	public static final DeferredBlock<Block> WASTE_EARTH = regStone("waste_earth", 0.6F, 0.6F);
	public static final DeferredBlock<Block> WASTE_MYCELIUM = regStone("waste_mycelium", 0.6F, 0.6F);
	public static final DeferredBlock<Block> WASTE_TRINITITE = regStone("waste_trinitite", 0.8F, 1.0F);
	public static final DeferredBlock<Block> WASTE_TRINITITE_RED = regStone("waste_trinitite_red", 0.8F, 1.0F);
	public static final DeferredBlock<Block> WASTE_LOG = register("waste_log", () -> new net.minecraft.world.level.block.RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG).strength(2.0F)));
	public static final DeferredBlock<Block> WASTE_PLANKS = regStone("waste_planks", 2.0F, 3.0F);
	public static final DeferredBlock<Block> FROZEN_GRASS = regStone("frozen_grass", 0.6F, 0.6F);
	public static final DeferredBlock<Block> FROZEN_DIRT = regStone("frozen_dirt", 0.6F, 0.6F);
	public static final DeferredBlock<Block> FROZEN_LOG = register("frozen_log", () -> new net.minecraft.world.level.block.RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG).strength(2.0F)));
	public static final DeferredBlock<Block> FROZEN_PLANKS = regStone("frozen_planks", 2.0F, 3.0F);
	public static final DeferredBlock<Block> TEKTITE = regStone("tektite", 5.0F, 50.0F);
	public static final DeferredBlock<Block> SELLAFIELD = regStone("sellafield", 10.0F, 50.0F);
	public static final DeferredBlock<Block> SELLAFIELD_SLAKED = regStone("sellafield_slaked", 8.0F, 40.0F);
	public static final DeferredBlock<Block> TAINT = regStone("taint", 2.0F, 10.0F);
	public static final DeferredBlock<Block> BALEFIRE = regStone("balefire", 0.0F, 0.0F);
	public static final DeferredBlock<Block> GRAVEL_OBSIDIAN = regStone("gravel_obsidian", 5.0F, 50.0F);
	public static final DeferredBlock<Block> BLOCK_SCRAP = regStone("block_scrap", 3.0F, 10.0F);
	public static final DeferredBlock<Block> BLOCK_ELECTRICAL_SCRAP = regStone("block_electrical_scrap", 3.0F, 10.0F);
	public static final DeferredBlock<Block> BRICK_OBSIDIAN = regStone("brick_obsidian", 15.0F, 120.0F);
	public static final DeferredBlock<Block> ORE_URANIUM = regStone("ore_uranium", 3.0F, 10.0F);
	public static final DeferredBlock<Block> ORE_URANIUM_SCORCHED = regStone("ore_uranium_scorched", 3.0F, 10.0F);
	public static final DeferredBlock<Block> ORE_SCHRABIDIUM = regStone("ore_schrabidium", 5.0F, 20.0F);
	public static final DeferredBlock<Block> ORE_NETHER_URANIUM = regStone("ore_nether_uranium", 3.0F, 10.0F);
	public static final DeferredBlock<Block> ORE_NETHER_URANIUM_SCORCHED = regStone("ore_nether_uranium_scorched", 3.0F, 10.0F);
	public static final DeferredBlock<Block> ORE_NETHER_SCHRABIDIUM = regStone("ore_nether_schrabidium", 5.0F, 20.0F);
	public static final DeferredBlock<Block> WASTE_LEAVES = register("waste_leaves", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).strength(0.2F)));
	public static final DeferredBlock<Block> WASTE_GRASS_TALL = register("waste_grass_tall", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).noCollission().instabreak()));
	public static final DeferredBlock<Block> MUSH = register("mush", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.BROWN_MUSHROOM).noCollission().instabreak()));

	// Direct accessors for compatibility
	public static Block ore_oil;
	public static Block ore_coltan;
	public static Block ore_australium;
	public static Block stone_depth;
	public static Block stone_depth_nether;
	public static Block stone_gneiss;

	public static Block waste_earth;
	public static Block waste_mycelium;
	public static Block waste_trinitite;
	public static Block waste_trinitite_red;
	public static Block waste_leaves;
	public static Block waste_grass_tall;
	public static Block mush;
	public static Block waste_log;
	public static Block waste_planks;
	public static Block frozen_grass;
	public static Block frozen_dirt;
	public static Block frozen_log;
	public static Block frozen_planks;
	public static Block tektite;
	public static Block sellafield;
	public static Block sellafield_slaked;
	public static Block taint;
	public static Block balefire;
	public static Block gravel_obsidian;
	public static Block block_scrap;
	public static Block block_electrical_scrap;
	public static Block brick_obsidian;
	public static Block ore_uranium;
	public static Block ore_uranium_scorched;
	public static Block ore_schrabidium;
	public static Block ore_nether_uranium;
	public static Block ore_nether_uranium_scorched;
	public static Block ore_nether_schrabidium;

	public static Block block_beryllium;
	public static Block block_red_copper;
	public static Block block_uranium;
	public static Block block_steel;
	public static Block block_lead;

	public static Block concrete;
	public static Block concrete_smooth;
	public static Block brick_concrete;
	public static Block brick_concrete_broken;
	public static Block reinforced_stone;
	public static Block reinforced_brick;

	public static Block rbmk_rod;
	public static Block rbmk_control;
	public static Block rbmk_boiler;
	public static Block rbmk_moderator;
	public static Block rbmk_absorber;
	public static Block rbmk_cooler;
	public static Block rbmk_heater;
	public static Block rbmk_outgasser;
	public static Block rbmk_debris;

	public static Block dfc_core;
	public static Block dfc_emitter;
	public static Block dfc_receiver;
	public static Block dfc_injector;
	public static Block dfc_stabilizer;

	public static Block reactor_zirnox;
	public static Block zirnox_destroyed;

	public static Block machine_rtg;
	public static Block machine_turbine;
	public static Block machine_industrial_turbine;
	public static Block machine_generator;
	public static Block machine_fluid_tank;
	public static Block machine_storage_tank;
	public static Block machine_gas_flare;
	public static Block machine_pump;

	public static Block cable;
	public static Block pylon;
	public static Block pylon_medium;
	public static Block pylon_large;
	public static Block substation;
	public static Block pipe;

	public static void register(IEventBus bus) {
		BLOCKS.register(bus);
	}

	public static void initAccessors() {
		ore_oil = ORE_OIL.get();
		ore_coltan = ORE_COLTAN.get();
		ore_australium = ORE_AUSTRALIAM.get();
		stone_depth = STONE_DEPTH.get();
		stone_depth_nether = STONE_DEPTH_NETHER.get();
		stone_gneiss = STONE_GNEISS.get();

		waste_earth = WASTE_EARTH.get();
		waste_mycelium = WASTE_MYCELIUM.get();
		waste_trinitite = WASTE_TRINITITE.get();
		waste_trinitite_red = WASTE_TRINITITE_RED.get();
		waste_leaves = WASTE_LEAVES.get();
		waste_grass_tall = WASTE_GRASS_TALL.get();
		mush = MUSH.get();
		waste_log = WASTE_LOG.get();
		waste_planks = WASTE_PLANKS.get();
		frozen_grass = FROZEN_GRASS.get();
		frozen_dirt = FROZEN_DIRT.get();
		frozen_log = FROZEN_LOG.get();
		frozen_planks = FROZEN_PLANKS.get();
		tektite = TEKTITE.get();
		sellafield = SELLAFIELD.get();
		sellafield_slaked = SELLAFIELD_SLAKED.get();
		taint = TAINT.get();
		balefire = BALEFIRE.get();
		gravel_obsidian = GRAVEL_OBSIDIAN.get();
		block_scrap = BLOCK_SCRAP.get();
		block_electrical_scrap = BLOCK_ELECTRICAL_SCRAP.get();
		brick_obsidian = BRICK_OBSIDIAN.get();
		ore_uranium = ORE_URANIUM.get();
		ore_uranium_scorched = ORE_URANIUM_SCORCHED.get();
		ore_schrabidium = ORE_SCHRABIDIUM.get();
		ore_nether_uranium = ORE_NETHER_URANIUM.get();
		ore_nether_uranium_scorched = ORE_NETHER_URANIUM_SCORCHED.get();
		ore_nether_schrabidium = ORE_NETHER_SCHRABIDIUM.get();

		block_beryllium = BLOCK_BERYLLIUM.get();
		block_red_copper = BLOCK_RED_COPPER.get();
		block_uranium = BLOCK_URANIUM.get();
		block_steel = BLOCK_STEEL.get();
		block_lead = BLOCK_LEAD.get();

		concrete = CONCRETE.get();
		concrete_smooth = CONCRETE_SMOOTH.get();
		brick_concrete = BRICK_CONCRETE.get();
		brick_concrete_broken = BRICK_CONCRETE_BROKEN.get();
		reinforced_stone = REINFORCED_STONE.get();
		reinforced_brick = REINFORCED_BRICK.get();

		rbmk_rod = RBMK_ROD.get();
		rbmk_control = RBMK_CONTROL.get();
		rbmk_boiler = RBMK_BOILER.get();
		rbmk_moderator = RBMK_MODERATOR.get();
		rbmk_absorber = RBMK_ABSORBER.get();
		rbmk_cooler = RBMK_COOLER.get();
		rbmk_heater = RBMK_HEATER.get();
		rbmk_outgasser = RBMK_OUTGASSER.get();
		rbmk_debris = RBMK_DEBRIS.get();

		dfc_core = DFC_CORE.get();
		dfc_emitter = DFC_EMITTER.get();
		dfc_receiver = DFC_RECEIVER.get();
		dfc_injector = DFC_INJECTOR.get();
		dfc_stabilizer = DFC_STABILIZER.get();

		reactor_zirnox = REACTOR_ZIRNOX.get();
		zirnox_destroyed = ZIRNOX_DESTROYED.get();

		machine_rtg = MACHINE_RTG.get();
		machine_turbine = MACHINE_TURBINE.get();
		machine_industrial_turbine = MACHINE_INDUSTRIAL_TURBINE.get();
		machine_generator = MACHINE_GENERATOR.get();
		machine_fluid_tank = MACHINE_FLUID_TANK.get();
		machine_storage_tank = MACHINE_STORAGE_TANK.get();
		machine_gas_flare = MACHINE_GAS_FLARE.get();
		machine_pump = MACHINE_PUMP.get();

		cable = CABLE.get();
		pylon = PYLON.get();
		pylon_medium = PYLON_MEDIUM.get();
		pylon_large = PYLON_LARGE.get();
		substation = SUBSTATION.get();
		pipe = PIPE.get();
	}
}
