package com.hbm.tileentity;

import com.hbm.main.MainRegistry;
import com.hbm.blocks.ModBlocks;
import com.hbm.tileentity.bomb.*;
import com.hbm.tileentity.machine.TileEntityCore;
import com.hbm.tileentity.machine.TileEntityCoreEmitter;
import com.hbm.tileentity.machine.TileEntityCoreInjector;
import com.hbm.tileentity.machine.TileEntityCoreReceiver;
import com.hbm.tileentity.machine.TileEntityCoreStabilizer;
import com.hbm.tileentity.machine.TileEntityReactorZirnox;
import com.hbm.tileentity.machine.TileEntityZirnoxDestroyed;
import com.hbm.tileentity.machine.rbmk.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {

	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
			DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MainRegistry.MODID);

	// RBMK
	public static final Supplier<BlockEntityType<TileEntityRBMKRod>> RBMK_ROD =
			BLOCK_ENTITIES.register("rbmk_rod", () ->
					BlockEntityType.Builder.of(TileEntityRBMKRod::new, ModBlocks.RBMK_ROD.get()).build(null));

	public static final Supplier<BlockEntityType<TileEntityRBMKControl>> RBMK_CONTROL =
			BLOCK_ENTITIES.register("rbmk_control", () ->
					BlockEntityType.Builder.of(TileEntityRBMKControl::new, ModBlocks.RBMK_CONTROL.get()).build(null));

	public static final Supplier<BlockEntityType<TileEntityRBMKBoiler>> RBMK_BOILER =
			BLOCK_ENTITIES.register("rbmk_boiler", () ->
					BlockEntityType.Builder.of(TileEntityRBMKBoiler::new, ModBlocks.RBMK_BOILER.get()).build(null));

	public static final Supplier<BlockEntityType<TileEntityRBMKModerator>> RBMK_MODERATOR =
			BLOCK_ENTITIES.register("rbmk_moderator", () ->
					BlockEntityType.Builder.of(TileEntityRBMKModerator::new, ModBlocks.RBMK_MODERATOR.get()).build(null));

	public static final Supplier<BlockEntityType<TileEntityRBMKAbsorber>> RBMK_ABSORBER =
			BLOCK_ENTITIES.register("rbmk_absorber", () ->
					BlockEntityType.Builder.of(TileEntityRBMKAbsorber::new, ModBlocks.RBMK_ABSORBER.get()).build(null));

	public static final Supplier<BlockEntityType<TileEntityRBMKCooler>> RBMK_COOLER =
			BLOCK_ENTITIES.register("rbmk_cooler", () ->
					BlockEntityType.Builder.of(TileEntityRBMKCooler::new, ModBlocks.RBMK_COOLER.get()).build(null));

	public static final Supplier<BlockEntityType<TileEntityRBMKHeater>> RBMK_HEATER =
			BLOCK_ENTITIES.register("rbmk_heater", () ->
					BlockEntityType.Builder.of(TileEntityRBMKHeater::new, ModBlocks.RBMK_HEATER.get()).build(null));

	public static final Supplier<BlockEntityType<TileEntityRBMKOutgasser>> RBMK_OUTGASSER =
			BLOCK_ENTITIES.register("rbmk_outgasser", () ->
					BlockEntityType.Builder.of(TileEntityRBMKOutgasser::new, ModBlocks.RBMK_OUTGASSER.get()).build(null));

	public static final Supplier<BlockEntityType<TileEntityRBMKDebris>> RBMK_DEBRIS =
			BLOCK_ENTITIES.register("rbmk_debris", () ->
					BlockEntityType.Builder.of(TileEntityRBMKDebris::new, ModBlocks.RBMK_DEBRIS.get()).build(null));

	// DFC Dark Fusion Core
	public static final Supplier<BlockEntityType<TileEntityCore>> DFC_CORE =
			BLOCK_ENTITIES.register("dfc_core", () ->
					BlockEntityType.Builder.of(TileEntityCore::new, ModBlocks.DFC_CORE.get()).build(null));

	public static final Supplier<BlockEntityType<TileEntityCoreEmitter>> DFC_EMITTER =
			BLOCK_ENTITIES.register("dfc_emitter", () ->
					BlockEntityType.Builder.of(TileEntityCoreEmitter::new, ModBlocks.DFC_EMITTER.get()).build(null));

	public static final Supplier<BlockEntityType<TileEntityCoreReceiver>> DFC_RECEIVER =
			BLOCK_ENTITIES.register("dfc_receiver", () ->
					BlockEntityType.Builder.of(TileEntityCoreReceiver::new, ModBlocks.DFC_RECEIVER.get()).build(null));

	public static final Supplier<BlockEntityType<TileEntityCoreInjector>> DFC_INJECTOR =
			BLOCK_ENTITIES.register("dfc_injector", () ->
					BlockEntityType.Builder.of(TileEntityCoreInjector::new, ModBlocks.DFC_INJECTOR.get()).build(null));

	public static final Supplier<BlockEntityType<TileEntityCoreStabilizer>> DFC_STABILIZER =
			BLOCK_ENTITIES.register("dfc_stabilizer", () ->
					BlockEntityType.Builder.of(TileEntityCoreStabilizer::new, ModBlocks.DFC_STABILIZER.get()).build(null));

	// Zirnox Reactor
	public static final Supplier<BlockEntityType<TileEntityReactorZirnox>> REACTOR_ZIRNOX =
			BLOCK_ENTITIES.register("reactor_zirnox", () ->
					BlockEntityType.Builder.of(TileEntityReactorZirnox::new, ModBlocks.REACTOR_ZIRNOX.get()).build(null));

	public static final Supplier<BlockEntityType<TileEntityZirnoxDestroyed>> ZIRNOX_DESTROYED =
			BLOCK_ENTITIES.register("zirnox_destroyed", () ->
					BlockEntityType.Builder.of(TileEntityZirnoxDestroyed::new, ModBlocks.ZIRNOX_DESTROYED.get()).build(null));

	// Processing Machinery
	public static final Supplier<BlockEntityType<com.hbm.tileentity.machine.TileEntityMachineCentrifuge>> CENTRIFUGE =
			BLOCK_ENTITIES.register("machine_centrifuge", () ->
					BlockEntityType.Builder.of(com.hbm.tileentity.machine.TileEntityMachineCentrifuge::new, ModBlocks.MACHINE_CENTRIFUGE.get()).build(null));

	public static final Supplier<BlockEntityType<com.hbm.tileentity.machine.TileEntityMachineBlastFurnace>> BLAST_FURNACE =
			BLOCK_ENTITIES.register("machine_blast_furnace", () ->
					BlockEntityType.Builder.of(com.hbm.tileentity.machine.TileEntityMachineBlastFurnace::new, ModBlocks.MACHINE_BLAST_FURNACE.get()).build(null));

	public static final Supplier<BlockEntityType<com.hbm.tileentity.machine.TileEntityMachineChemicalPlant>> CHEMPLANT =
			BLOCK_ENTITIES.register("machine_chemplant", () ->
					BlockEntityType.Builder.of(com.hbm.tileentity.machine.TileEntityMachineChemicalPlant::new, ModBlocks.MACHINE_CHEMPLANT.get()).build(null));

	public static final Supplier<BlockEntityType<com.hbm.tileentity.machine.TileEntityElectrolyser>> ELECTROLYSER =
			BLOCK_ENTITIES.register("machine_electrolyser", () ->
					BlockEntityType.Builder.of(com.hbm.tileentity.machine.TileEntityElectrolyser::new, ModBlocks.MACHINE_ELECTROLYSER.get()).build(null));

	public static final Supplier<BlockEntityType<com.hbm.tileentity.machine.TileEntityMachineShredder>> SHREDDER =
			BLOCK_ENTITIES.register("machine_shredder", () ->
					BlockEntityType.Builder.of(com.hbm.tileentity.machine.TileEntityMachineShredder::new, ModBlocks.MACHINE_SHREDDER.get()).build(null));

	public static final Supplier<BlockEntityType<com.hbm.tileentity.machine.TileEntityMachineCrystallizer>> CRYSTALLIZER =
			BLOCK_ENTITIES.register("machine_crystallizer", () ->
					BlockEntityType.Builder.of(com.hbm.tileentity.machine.TileEntityMachineCrystallizer::new, ModBlocks.MACHINE_CRYSTALLIZER.get()).build(null));

	public static final Supplier<BlockEntityType<com.hbm.tileentity.machine.TileEntityMachineEPress>> EPRESS =
			BLOCK_ENTITIES.register("machine_epress", () ->
					BlockEntityType.Builder.of(com.hbm.tileentity.machine.TileEntityMachineEPress::new, ModBlocks.MACHINE_EPRESS.get()).build(null));

	public static final Supplier<BlockEntityType<com.hbm.tileentity.machine.TileEntityMachineAssemblyMachine>> ASSEMBLY_MACHINE =
			BLOCK_ENTITIES.register("machine_assembly_machine", () ->
					BlockEntityType.Builder.of(com.hbm.tileentity.machine.TileEntityMachineAssemblyMachine::new, ModBlocks.MACHINE_ASSEMBLY_MACHINE.get()).build(null));

	public static final Supplier<BlockEntityType<com.hbm.tileentity.machine.TileEntityMachineSiren>> SIREN =
			BLOCK_ENTITIES.register("machine_siren", () ->
					BlockEntityType.Builder.of(com.hbm.tileentity.machine.TileEntityMachineSiren::new, ModBlocks.MACHINE_SIREN.get()).build(null));

	public static final Supplier<BlockEntityType<com.hbm.tileentity.machine.TileEntityMachineBattery>> BATTERY =
			BLOCK_ENTITIES.register("machine_battery", () ->
					BlockEntityType.Builder.of(com.hbm.tileentity.machine.TileEntityMachineBattery::new, ModBlocks.MACHINE_BATTERY.get()).build(null));

	// Nuclear Bombs & Missiles
	public static final Supplier<BlockEntityType<com.hbm.tileentity.bomb.TileEntityBombMulti>> BOMB_MULTI =
			BLOCK_ENTITIES.register("bomb_multi", () ->
					BlockEntityType.Builder.of(com.hbm.tileentity.bomb.TileEntityBombMulti::new,
							ModBlocks.BOMB_BOY.get(),
							ModBlocks.BOMB_MAN.get(),
							ModBlocks.BOMB_MIKE.get(),
							ModBlocks.BOMB_TSAR.get(),
							ModBlocks.BOMB_GADGET.get()).build(null));

	public static final Supplier<BlockEntityType<TileEntityNukeBoy>> NUKE_BOY =
			BLOCK_ENTITIES.register("nuke_boy", () ->
					BlockEntityType.Builder.of(TileEntityNukeBoy::new, ModBlocks.NUKE_BOY.get()).build(null));

	public static final Supplier<BlockEntityType<TileEntityNukeMan>> NUKE_MAN =
			BLOCK_ENTITIES.register("nuke_man", () ->
					BlockEntityType.Builder.of(TileEntityNukeMan::new, ModBlocks.NUKE_MAN.get()).build(null));

	public static final Supplier<BlockEntityType<TileEntityNukeMike>> NUKE_MIKE =
			BLOCK_ENTITIES.register("nuke_mike", () ->
					BlockEntityType.Builder.of(TileEntityNukeMike::new, ModBlocks.NUKE_MIKE.get()).build(null));

	public static final Supplier<BlockEntityType<TileEntityNukeTsar>> NUKE_TSAR =
			BLOCK_ENTITIES.register("nuke_tsar", () ->
					BlockEntityType.Builder.of(TileEntityNukeTsar::new, ModBlocks.NUKE_TSAR.get()).build(null));

	public static final Supplier<BlockEntityType<TileEntityNukeGadget>> NUKE_GADGET =
			BLOCK_ENTITIES.register("nuke_gadget", () ->
					BlockEntityType.Builder.of(TileEntityNukeGadget::new, ModBlocks.NUKE_GADGET.get()).build(null));

	public static final Supplier<BlockEntityType<TileEntityNukeFleija>> NUKE_FLEIJA =
			BLOCK_ENTITIES.register("nuke_fleija", () ->
					BlockEntityType.Builder.of(TileEntityNukeFleija::new, ModBlocks.NUKE_FLEIJA.get()).build(null));

	public static final Supplier<BlockEntityType<TileEntityNukeBalefire>> NUKE_BALEFIRE =
			BLOCK_ENTITIES.register("nuke_fstbmb", () ->
					BlockEntityType.Builder.of(TileEntityNukeBalefire::new, ModBlocks.NUKE_BALEFIRE.get()).build(null));

	public static final Supplier<BlockEntityType<TileEntityNukeN2>> NUKE_N2 =
			BLOCK_ENTITIES.register("nuke_n2", () ->
					BlockEntityType.Builder.of(TileEntityNukeN2::new, ModBlocks.NUKE_N2.get()).build(null));

	public static final Supplier<BlockEntityType<TileEntityNukeSolinium>> NUKE_SOLINIUM =
			BLOCK_ENTITIES.register("nuke_solinium", () ->
					BlockEntityType.Builder.of(TileEntityNukeSolinium::new, ModBlocks.NUKE_SOLINIUM.get()).build(null));

	public static final Supplier<BlockEntityType<TileEntityNukePrototype>> NUKE_PROTOTYPE =
			BLOCK_ENTITIES.register("nuke_prototype", () ->
					BlockEntityType.Builder.of(TileEntityNukePrototype::new, ModBlocks.NUKE_PROTOTYPE.get()).build(null));

	public static final Supplier<BlockEntityType<TileEntityNukeCustom>> NUKE_CUSTOM =
			BLOCK_ENTITIES.register("nuke_custom", () ->
					BlockEntityType.Builder.of(TileEntityNukeCustom::new, ModBlocks.NUKE_CUSTOM.get()).build(null));

	public static final Supplier<BlockEntityType<TileEntityLaunchPad>> LAUNCH_PAD =
			BLOCK_ENTITIES.register("launch_pad", () ->
					BlockEntityType.Builder.of(TileEntityLaunchPad::new, ModBlocks.LAUNCH_PAD.get()).build(null));

	public static final Supplier<BlockEntityType<TileEntityLaunchPadLarge>> LAUNCH_PAD_LARGE =
			BLOCK_ENTITIES.register("launch_pad_large", () ->
					BlockEntityType.Builder.of(TileEntityLaunchPadLarge::new, ModBlocks.LAUNCH_PAD_LARGE.get()).build(null));

	public static final Supplier<BlockEntityType<TileEntityLaunchTable>> LAUNCH_TABLE =
			BLOCK_ENTITIES.register("launch_table", () ->
					BlockEntityType.Builder.of(TileEntityLaunchTable::new, ModBlocks.LAUNCH_TABLE.get()).build(null));

	public static final Supplier<BlockEntityType<TileEntityCompactLauncher>> COMPACT_LAUNCHER =
			BLOCK_ENTITIES.register("compact_launcher", () ->
					BlockEntityType.Builder.of(TileEntityCompactLauncher::new, ModBlocks.COMPACT_LAUNCHER.get()).build(null));

	// Power Grid & Fluid Logistics
	public static final Supplier<BlockEntityType<com.hbm.tileentity.network.energy.TileEntityCableBaseNT>> CABLE =
			BLOCK_ENTITIES.register("cable", () ->
					BlockEntityType.Builder.of(com.hbm.tileentity.network.energy.TileEntityCableBaseNT::new, ModBlocks.CABLE.get()).build(null));

	public static final Supplier<BlockEntityType<com.hbm.tileentity.network.energy.TileEntityPylon>> PYLON =
			BLOCK_ENTITIES.register("pylon", () ->
					BlockEntityType.Builder.of(com.hbm.tileentity.network.energy.TileEntityPylon::new,
							ModBlocks.PYLON.get(),
							ModBlocks.PYLON_MEDIUM.get(),
							ModBlocks.PYLON_LARGE.get()).build(null));

	public static final Supplier<BlockEntityType<com.hbm.tileentity.network.energy.TileEntitySubstation>> SUBSTATION =
			BLOCK_ENTITIES.register("substation", () ->
					BlockEntityType.Builder.of(com.hbm.tileentity.network.energy.TileEntitySubstation::new, ModBlocks.SUBSTATION.get()).build(null));

	public static final Supplier<BlockEntityType<com.hbm.tileentity.network.TileEntityPipeBaseNT>> PIPE =
			BLOCK_ENTITIES.register("pipe", () ->
					BlockEntityType.Builder.of(com.hbm.tileentity.network.TileEntityPipeBaseNT::new, ModBlocks.PIPE.get()).build(null));

	public static final Supplier<BlockEntityType<com.hbm.tileentity.machine.TileEntityMachineRTG>> RTG =
			BLOCK_ENTITIES.register("machine_rtg", () ->
					BlockEntityType.Builder.of(com.hbm.tileentity.machine.TileEntityMachineRTG::new, ModBlocks.MACHINE_RTG.get()).build(null));

	public static final Supplier<BlockEntityType<com.hbm.tileentity.machine.TileEntityMachineTurbine>> TURBINE =
			BLOCK_ENTITIES.register("machine_turbine", () ->
					BlockEntityType.Builder.of(com.hbm.tileentity.machine.TileEntityMachineTurbine::new, ModBlocks.MACHINE_TURBINE.get()).build(null));

	public static final Supplier<BlockEntityType<com.hbm.tileentity.machine.TileEntityMachineIndustrialTurbine>> INDUSTRIAL_TURBINE =
			BLOCK_ENTITIES.register("machine_industrial_turbine", () ->
					BlockEntityType.Builder.of(com.hbm.tileentity.machine.TileEntityMachineIndustrialTurbine::new, ModBlocks.MACHINE_INDUSTRIAL_TURBINE.get()).build(null));

	public static final Supplier<BlockEntityType<com.hbm.tileentity.machine.TileEntityMachineIGenerator>> GENERATOR =
			BLOCK_ENTITIES.register("machine_generator", () ->
					BlockEntityType.Builder.of(com.hbm.tileentity.machine.TileEntityMachineIGenerator::new, ModBlocks.MACHINE_GENERATOR.get()).build(null));

	public static final Supplier<BlockEntityType<com.hbm.tileentity.machine.TileEntityMachineFluidTank>> FLUID_TANK =
			BLOCK_ENTITIES.register("machine_fluid_tank", () ->
					BlockEntityType.Builder.of(com.hbm.tileentity.machine.TileEntityMachineFluidTank::new, ModBlocks.MACHINE_FLUID_TANK.get()).build(null));

	public static final Supplier<BlockEntityType<com.hbm.tileentity.machine.TileEntityMachineStorageTank>> STORAGE_TANK =
			BLOCK_ENTITIES.register("machine_storage_tank", () ->
					BlockEntityType.Builder.of(com.hbm.tileentity.machine.TileEntityMachineStorageTank::new, ModBlocks.MACHINE_STORAGE_TANK.get()).build(null));

	public static final Supplier<BlockEntityType<com.hbm.tileentity.machine.TileEntityMachineGasFlare>> GAS_FLARE =
			BLOCK_ENTITIES.register("machine_gas_flare", () ->
					BlockEntityType.Builder.of(com.hbm.tileentity.machine.TileEntityMachineGasFlare::new, ModBlocks.MACHINE_GAS_FLARE.get()).build(null));

	public static final Supplier<BlockEntityType<com.hbm.tileentity.machine.TileEntityPump>> PUMP =
			BLOCK_ENTITIES.register("machine_pump", () ->
					BlockEntityType.Builder.of(com.hbm.tileentity.machine.TileEntityPump::new, ModBlocks.MACHINE_PUMP.get()).build(null));

	public static void register(IEventBus bus) {
		BLOCK_ENTITIES.register(bus);
	}
}
