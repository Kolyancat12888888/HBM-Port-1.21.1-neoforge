package com.hbm.util;

import net.minecraft.network.chat.Component;

public class I18nUtil {

    public static String resolveKey(String s, Object... args) {
        return Component.translatable(s, args).getString();
    }

    public static Component resolveComponent(String s, Object... args) {
        return Component.translatable(s, args);
    }

    public static String[] resolveKeyArray(String s, Object... args) {
        return resolveKey(s, args).split("\\$");
    }
}
