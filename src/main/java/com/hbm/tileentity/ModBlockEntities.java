package com.hbm.tileentity;

import com.hbm.main.MainRegistry;
import com.hbm.blocks.ModBlocks;
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

	public static final Supplier<BlockEntityType<com.hbm.tileentity.bomb.TileEntityLaunchPad>> LAUNCH_PAD =
			BLOCK_ENTITIES.register("launch_pad", () ->
					BlockEntityType.Builder.of(com.hbm.tileentity.bomb.TileEntityLaunchPad::new, ModBlocks.LAUNCH_PAD.get()).build(null));

	public static void register(IEventBus bus) {
		BLOCK_ENTITIES.register(bus);
	}
}
