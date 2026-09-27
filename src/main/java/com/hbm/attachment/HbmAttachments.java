package com.hbm.attachment;

import com.hbm.main.MainRegistry;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class HbmAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, MainRegistry.MODID);

    public static final Supplier<AttachmentType<HbmLivingData>> LIVING_DATA = ATTACHMENT_TYPES.register(
            "living_data",
            () -> AttachmentType.serializable(HbmLivingData::new).build()
    );

    public static void register(IEventBus bus) {
        ATTACHMENT_TYPES.register(bus);
    }
}
