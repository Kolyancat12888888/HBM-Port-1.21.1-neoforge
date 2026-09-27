package com.hbm.lib;

import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.TreeMap;

public class Library {

    public static final double DEG_TO_RAD = Math.PI / 180.0;
    public static final int[] powersOfTen = {1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000};
    public static DecimalFormat numberformat = new DecimalFormat("0.00");

    public static final ForgeDirection POS_X = ForgeDirection.EAST;
    public static final ForgeDirection NEG_X = ForgeDirection.WEST;
    public static final ForgeDirection POS_Y = ForgeDirection.UP;
    public static final ForgeDirection NEG_Y = ForgeDirection.DOWN;
    public static final ForgeDirection POS_Z = ForgeDirection.SOUTH;
    public static final ForgeDirection NEG_Z = ForgeDirection.NORTH;

    public static Map<Integer, String> numbersMap = null;

    public static void initNumbers() {
        numbersMap = new TreeMap<>();
        numbersMap.put(3, "k");
        numbersMap.put(6, "M");
        numbersMap.put(9, "G");
        numbersMap.put(12, "T");
        numbersMap.put(15, "P");
        numbersMap.put(18, "E");
        numbersMap.put(21, "Z");
        numbersMap.put(24, "Y");
        numbersMap.put(27, "R");
        numbersMap.put(30, "Q");
    }

    public static float roundFloat(float number, int decimal) {
        if (decimal < 0 || decimal >= powersOfTen.length) return number;
        return (float) (Math.round(number * powersOfTen[decimal]) / (float) powersOfTen[decimal]);
    }

    public static float roundFloat(double number, int decimal) {
        if (decimal < 0 || decimal >= powersOfTen.length) return (float) number;
        return (float) (Math.round(number * powersOfTen[decimal]) / (float) powersOfTen[decimal]);
    }

    public static String getShortNumber(long l) {
        return getShortNumber(new BigDecimal(l));
    }

    public static String getShortNumber(BigDecimal l) {
        if (numbersMap == null) initNumbers();

        boolean negative = l.signum() < 0;
        if (negative) {
            l = l.negate();
        }

        String result = l.toPlainString();
        BigDecimal c;
        for (Map.Entry<Integer, String> num : numbersMap.entrySet()) {
            c = new BigDecimal("1E" + num.getKey());
            if (l.compareTo(c) >= 0) {
                double res = l.divide(c).doubleValue();
                result = numberformat.format(roundFloat(res, 2)) + num.getValue();
            } else {
                break;
            }
        }

        if (negative) {
            result = "-" + result;
        }

        return result;
    }

    public static boolean checkForHeld(Player player, Item item) {
        return player.getMainHandItem().getItem() == item || player.getOffhandItem().getItem() == item;
    }

    public static String getColor(long a, long b) {
        float fraction = 100F * a / b;
        if (fraction > 75) return "§a";
        if (fraction > 25) return "§e";
        return "§c";
    }

    public static String getColoredMbPercent(long a, long b) {
        String color = getColor(a, b);
        return color + a + " §2/ " + b + " mB " + color + "(" + getPercentage(a / (double) b) + "%)";
    }

    public static String getColoredDurabilityPercent(long a, long b) {
        String color = getColor(a, b);
        return "Durability: " + color + a + " §2/ " + b + " " + color + "(" + getPercentage(a / (double) b) + "%)";
    }

    public static String getPercentage(double fraction) {
        return numberformat.format(roundFloat(fraction * 100D, 2));
    }
}
