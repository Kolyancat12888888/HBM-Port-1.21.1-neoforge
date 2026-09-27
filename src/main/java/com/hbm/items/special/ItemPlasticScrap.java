package com.hbm.items.special;

import com.hbm.items.ItemBase;

public class ItemPlasticScrap extends ItemBase {

    protected final ScrapType scrapType;

    public ItemPlasticScrap(Properties properties, ScrapType scrapType) {
        super(properties);
        this.scrapType = scrapType;
    }

    public ItemPlasticScrap(ScrapType scrapType) {
        this(new Properties(), scrapType);
    }

    public ScrapType getScrapType() {
        return scrapType;
    }

    public enum ScrapType {
        BOARD_BLANK,
        BOARD_TRANSISTOR,
        BOARD_CONVERTER,
        BRIDGE_NORTH,
        BRIDGE_SOUTH,
        BRIDGE_IO,
        BRIDGE_BUS,
        BRIDGE_CHIPSET,
        BRIDGE_CMOS,
        BRIDGE_BIOS,
        CPU_REGISTER,
        CPU_CLOCK,
        CPU_LOGIC,
        CPU_CACHE,
        CPU_EXT,
        CPU_SOCKET,
        MEM_SOCKET,
        MEM_16K_A,
        MEM_16K_B,
        MEM_16K_C,
        MEM_16K_D,
        CARD_BOARD,
        CARD_PROCESSOR;

        public static final ScrapType[] VALUES = values();
    }
}
