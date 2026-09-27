package com.hbm.items;

public class ItemBakedBase extends ItemBase {

    protected String texturePath;

    public ItemBakedBase(Properties properties, String texturePath) {
        super(properties);
        this.texturePath = texturePath;
    }

    public ItemBakedBase(Properties properties) {
        super(properties);
    }

    public ItemBakedBase(String texturePath) {
        this(new Properties(), texturePath);
    }

    public ItemBakedBase() {
        this(new Properties());
    }

    public String getTexturePath() {
        return texturePath;
    }
}
