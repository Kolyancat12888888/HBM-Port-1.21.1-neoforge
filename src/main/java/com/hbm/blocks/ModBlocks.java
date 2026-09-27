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
	public static final DeferredBlock<Block> BRICK_CONCRETE = regStone("brick_concrete", 15.0F, 160.0F);
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

	// Bombs & Silos
	public static final DeferredBlock<com.hbm.blocks.bomb.BlockBombMulti> BOMB_BOY = register("bomb_boy", () -> new com.hbm.blocks.bomb.BlockBombMulti(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(5.0F, 50.0F), com.hbm.tileentity.bomb.TileEntityBombMulti.BombType.BOY));
	public static final DeferredBlock<com.hbm.blocks.bomb.BlockBombMulti> BOMB_MAN = register("bomb_man", () -> new com.hbm.blocks.bomb.BlockBombMulti(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(5.0F, 50.0F), com.hbm.tileentity.bomb.TileEntityBombMulti.BombType.MAN));
	public static final DeferredBlock<com.hbm.blocks.bomb.BlockBombMulti> BOMB_MIKE = register("bomb_mike", () -> new com.hbm.blocks.bomb.BlockBombMulti(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(5.0F, 50.0F), com.hbm.tileentity.bomb.TileEntityBombMulti.BombType.MIKE));
	public static final DeferredBlock<com.hbm.blocks.bomb.BlockBombMulti> BOMB_TSAR = register("bomb_tsar", () -> new com.hbm.blocks.bomb.BlockBombMulti(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(5.0F, 50.0F), com.hbm.tileentity.bomb.TileEntityBombMulti.BombType.TSAR));
	public static final DeferredBlock<com.hbm.blocks.bomb.BlockBombMulti> BOMB_GADGET = register("bomb_gadget", () -> new com.hbm.blocks.bomb.BlockBombMulti(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(5.0F, 50.0F), com.hbm.tileentity.bomb.TileEntityBombMulti.BombType.GADGET));
	public static final DeferredBlock<com.hbm.blocks.bomb.BlockLaunchPad> LAUNCH_PAD = register("launch_pad", () -> new com.hbm.blocks.bomb.BlockLaunchPad(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(10.0F, 100.0F)));

	// Direct accessors for compatibility
	public static Block ore_oil;
	public static Block ore_coltan;
	public static Block ore_australium;
	public static Block stone_depth;
	public static Block stone_depth_nether;
	public static Block stone_gneiss;

	public static Block block_beryllium;
	public static Block block_red_copper;
	public static Block block_uranium;
	public static Block block_steel;
	public static Block block_lead;

	public static Block brick_concrete;
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

		block_beryllium = BLOCK_BERYLLIUM.get();
		block_red_copper = BLOCK_RED_COPPER.get();
		block_uranium = BLOCK_URANIUM.get();
		block_steel = BLOCK_STEEL.get();
		block_lead = BLOCK_LEAD.get();

		brick_concrete = BRICK_CONCRETE.get();
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
	}
}
