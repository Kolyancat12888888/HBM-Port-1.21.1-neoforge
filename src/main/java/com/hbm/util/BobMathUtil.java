package com.hbm.util;

import com.hbm.lib.ForgeDirection;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector4f;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.ToIntFunction;

public class BobMathUtil {

    public static int min(int... nums) {
        int smallest = Integer.MAX_VALUE;
        for (int num : nums) if (num < smallest) smallest = num;
        return smallest;
    }

    public static double squirt(double x) {
        return Math.sqrt(x + 1D / ((x + 2D) * (x + 2D))) - 1D / (x + 2D);
    }

    public static int max(int... nums) {
        int largest = Integer.MIN_VALUE;
        for (int num : nums) if (num > largest) largest = num;
        return largest;
    }

    public static long min(long... nums) {
        long smallest = Long.MAX_VALUE;
        for (long num : nums) if (num < smallest) smallest = num;
        return smallest;
    }

    public static long max(long... nums) {
        long largest = Long.MIN_VALUE;
        for (long num : nums) if (num > largest) largest = num;
        return largest;
    }

    public static float min(float... nums) {
        float smallest = Float.MAX_VALUE;
        for (float num : nums) if (num < smallest) smallest = num;
        return smallest;
    }

    public static float max(float... nums) {
        float largest = Float.MIN_VALUE;
        for (float num : nums) if (num > largest) largest = num;
        return largest;
    }

    public static double min(double... nums) {
        double smallest = Double.MAX_VALUE;
        for (double num : nums) if (num < smallest) smallest = num;
        return smallest;
    }

    public static double max(double... nums) {
        double largest = Double.MIN_VALUE;
        for (double num : nums) if (num > largest) largest = num;
        return largest;
    }

    public static String getShortNumber(long number) {
        if (number < 1000) {
            return String.valueOf(number);
        } else if (number < 1_000_000) {
            return String.format(Locale.US, "%.2fk", number / 1000.0);
        } else if (number < 1_000_000_000) {
            return String.format(Locale.US, "%.2fM", number / 1_000_000.0);
        } else if (number < 1_000_000_000_000L) {
            return String.format(Locale.US, "%.2fG", number / 1_000_000_000.0);
        } else if (number < 1_000_000_000_000_000L) {
            return String.format(Locale.US, "%.2fT", number / 1_000_000_000_000.0);
        } else if (number < 1_000_000_000_000_000_000L) {
            return String.format(Locale.US, "%.2fE", number / 1_000_000_000_000_000.0);
        } else {
            return "INFINITE";
        }
    }

    public static double sqrt(double x) {
        return Math.sqrt(x + 1D / ((x + 2D) * (x + 2D))) - 1D / (x + 2D);
    }

    public static double roundDecimal(double num, int digits) {
        if (digits < 0)
            throw new IllegalArgumentException("Attempted negative number in non-negative field! Attempted value: " + digits);

        return new BigDecimal(num).setScale(digits, RoundingMode.HALF_UP).doubleValue();
    }

    public static String format(int amount) {
        return String.format(Locale.US, "%,d", amount);
    }

    public static String format(long amount) {
        return String.format(Locale.US, "%,d", amount);
    }

    public static String format(Number amount) {
        return String.format(Locale.US, "%,d", amount);
    }

    public static boolean getBlink() {
        return System.currentTimeMillis() % 1000 < 500;
    }

    public static double getAngleFrom2DVecs(double x1, double z1, double x2, double z2) {
        double upper = x1 * x2 + z1 * z2;
        double lower = Math.sqrt(x1 * x1 + z1 * z1) * Math.sqrt(x2 * x2 + z2 * z2);
        double result = Math.toDegrees(Math.cos(upper / lower));
        if (result >= 180) result -= 180;
        return result;
    }

    public static double angularDifference(double alpha, double beta) {
        double delta = (beta - alpha + 180) % 360 - 180;
        return delta < -180 ? delta + 360 : delta;
    }

    public static double clampedLerp(double start, double end, double delta) {
        if (delta < 0.0D) {
            return start;
        } else {
            return delta > 1.0D ? end : lerp(delta, start, end);
        }
    }

    public static double getCrossAngle(Vec3 vel, Vec3 rel) {
        vel = vel.normalize();
        rel = rel.normalize();
        double angle = Math.toDegrees(Math.acos(vel.dot(rel)));
        if (angle >= 180) angle -= 180;
        return angle;
    }

    public static double perlinFade(double value) {
        return value * value * value * (value * (value * 6.0D - 15.0D) + 10.0D);
    }

    public static double perlinFadeDerivative(double value) {
        return 30.0D * value * value * (value - 1.0D) * (value - 1.0D);
    }

    public static Vec3 getDirectionFromAxisAngle(float pitch, float yaw, double length) {
        double ox = -Mth.sin(yaw / 180.0F * (float) Math.PI) * Mth.cos(pitch / 180.0F * (float) Math.PI) * length;
        double oz = Mth.cos(yaw / 180.0F * (float) Math.PI) * Mth.cos(pitch / 180.0F * (float) Math.PI) * length;
        double oy = -Mth.sin(pitch / 180.0F * (float) Math.PI) * length;

        return new Vec3(ox, oy, oz);
    }

    public static float remap(float num, float min1, float max1, float min2, float max2) {
        return ((num - min1) / (max1 - min1)) * (max2 - min2) + min2;
    }

    public static float remap01(float num, float min1, float max1) {
        return (num - min1) / (max1 - min1);
    }

    public static float remap01_clamp(float num, float min1, float max1) {
        return Mth.clamp((num - min1) / (max1 - min1), 0, 1);
    }

    public static ForgeDirection[] getShuffledDirs() {
        ForgeDirection[] dirs = new ForgeDirection[6];
        List<Integer> indices = new ArrayList<>(List.of(0, 1, 2, 3, 4, 5));
        Collections.shuffle(indices);
        for (int i = 0; i < 6; i++) {
            dirs[i] = ForgeDirection.getOrientation(indices.get(i));
        }
        return dirs;
    }

    public static double lerp(double delta, double start, double end) {
        return start + delta * (end - start);
    }

    public static Vec3 lerp(Vec3 vec0, Vec3 vec1, float interp) {
        return new Vec3(
                vec0.x + (vec1.x - vec0.x) * interp,
                vec0.y + (vec1.y - vec0.y) * interp,
                vec0.z + (vec1.z - vec0.z) * interp);
    }

    public static double clerp(double delta, double start, double end) {
        double angle = ((((end - start) % 360) + 540) % 360) - 180;
        return start + angle * delta;
    }

    public static double lerp2(double deltaX, double deltaY, double x0y0, double x1y0, double x0y1, double x1y1) {
        return lerp(deltaY, lerp(deltaX, x0y0, x1y0), lerp(deltaX, x0y1, x1y1));
    }

    public static double lerp3(double deltaX, double deltaY, double deltaZ, double x0y0z0, double x1y0z0, double x0y1z0, double x1y1z0, double x0y0z1, double x1y0z1, double x0y1z1, double x1y1z1) {
        return lerp(deltaZ, lerp2(deltaX, deltaY, x0y0z0, x1y0z0, x0y1z0, x1y1z0), lerp2(deltaX, deltaY, x0y0z1, x1y0z1, x0y1z1, x1y1z1));
    }

    public static double getLerpProgress(double value, double start, double end) {
        return (value - start) / (end - start);
    }

    public static double lerpFromProgress(double lerpValue, double lerpStart, double lerpEnd, double start, double end) {
        return lerp(getLerpProgress(lerpValue, lerpStart, lerpEnd), start, end);
    }

    public static Vec3 getEulerAngles(Vec3 vec) {
        double yaw = Math.toDegrees(Math.atan2(vec.x, vec.z));
        double sqrt = Math.sqrt(vec.x * vec.x + vec.z * vec.z);
        double pitch = Math.toDegrees(Math.atan2(vec.y, sqrt));
        return new Vec3(yaw, pitch - 90, 0);
    }

    public static Vec3 getVectorFromAngle(float yaw, float pitch) {
        float f = Mth.cos(-yaw * ((float)Math.PI / 180F) - (float)Math.PI);
        float f1 = Mth.sin(-yaw * ((float)Math.PI / 180F) - (float)Math.PI);
        float f2 = -Mth.cos(-pitch * ((float)Math.PI / 180F));
        float f3 = Mth.sin(-pitch * ((float)Math.PI / 180F));
        return new Vec3(f1 * f2, f3, f * f2);
    }

    public static Vec3 getVectorFromAngle(Vec3 vec) {
        return getVectorFromAngle((float) vec.x, (float) vec.y);
    }

    public static void matrixFromQuat(Matrix3f m, Quaternionf q) {
        m.rotation(q);
    }

    public static boolean epsilonEquals(float num1, float num2, float eps) {
        return Math.abs(num1 - num2) < eps;
    }

    public static boolean epsilonEquals(double num1, double num2, double eps) {
        return Math.abs(num1 - num2) < eps;
    }

    public static boolean epsilonEquals(Vec3 a, Vec3 b, double eps) {
        double dx = Math.abs(a.x - b.x);
        double dy = Math.abs(a.y - b.y);
        double dz = Math.abs(a.z - b.z);
        return dx < eps && dy < eps && dz < eps;
    }

    public static int absMaxIdx(double... numbers) {
        int idx = 0;
        double max = -Double.MAX_VALUE;
        for (int i = 0; i < numbers.length; i++) {
            double num = Math.abs(numbers[i]);
            if (num > max) {
                idx = i;
                max = num;
            }
        }
        return idx;
    }

    public static Vec3 randVecInCone(Vec3 coneDirection, float angle) {
        return randVecInCone(coneDirection, angle, ThreadLocalRandom.current());
    }

    public static Vec3 randVecInCone(Vec3 coneDirection, float angle, Random rand) {
        Vec3 up = new Vec3(0, 1, 0);
        float pitch = (float) Math.toRadians(rand.nextFloat() * (angle + rand.nextFloat() * angle));
        float yaw = (float) Math.toRadians(rand.nextFloat() * 360);
        Vec3 rotated = up.xRot(pitch).yRot(yaw);

        Vec3 direction = new Vec3(coneDirection.x, coneDirection.y, coneDirection.z);
        Vec3 angles = getEulerAngles(direction);
        return rotated.xRot((float) Math.toRadians(angles.y - 90)).yRot((float) Math.toRadians(angles.x));
    }

    public static Vec3 mix(Vec3 a, Vec3 b, float amount) {
        return new Vec3(a.x + (b.x - a.x) * amount, a.y + (b.y - a.y) * amount, a.z + (b.z - a.z) * amount);
    }

    public static Vec3 mat4Transform(Vec3 vec, @Nullable Matrix4f mat) {
        if (mat != null) {
            Vector4f v = mat.transform(new Vector4f((float) vec.x, (float) vec.y, (float) vec.z, 1.0f));
            return new Vec3(v.x, v.y, v.z);
        }
        return vec;
    }

    public static String toPercentage(float amount, float total) {
        return NumberFormat.getPercentInstance().format(amount / total);
    }

    public static double convertScale(double toScale, double oldMin, double oldMax, double newMin, double newMax) {
        double prevRange = oldMax - oldMin;
        double newRange = newMax - newMin;
        return (((toScale - oldMin) * newRange) / prevRange) + newMin;
    }

    public static String[] ticksToDate(long ticks, int tickHour) {
        int tickDay = 24 * tickHour;
        int tickYear = 365 * tickDay;
        double tickMinute = tickHour / 60D;
        double tickSecond = tickHour / 3600D;

        final String[] dateOut = new String[5];
        long year = Math.floorDiv(ticks, tickYear);
        int day = (int) Math.floorDiv(ticks - tickYear * year, tickDay);
        int h = (int) Math.floorDiv(ticks - tickYear * year - tickDay * day, tickHour);
        int min = (int) Math.floor((ticks - tickYear * year - tickDay * day - tickHour * h) / tickMinute);
        int s = (int) Math.floor((ticks - tickYear * year - tickDay * day - tickHour * h - min * tickMinute) / tickSecond);
        dateOut[0] = String.valueOf(year);
        dateOut[1] = String.valueOf(day);
        dateOut[2] = String.valueOf(h);
        dateOut[3] = String.valueOf(min);
        dateOut[4] = String.valueOf(s);
        return dateOut;
    }

    public static String[] ticksToDate(long ticks) {
        return ticksToDate(ticks, 1000);
    }

    public static String ticksToDateString(long ticks, int tickHour) {
        return toDate(ticksToDate(ticks, tickHour));
    }

    public static String toDate(String[] input) {
        if (!input[0].equals("0"))
            return input[0] + "y " + input[1] + "d " + input[2] + "h " + input[3] + "m " + input[4] + "s";
        else if (!input[1].equals("0"))
            return input[1] + "d " + input[2] + "h " + input[3] + "m " + input[4] + "s";
        else if (!input[2].equals("0"))
            return input[2] + "h " + input[3] + "m " + input[4] + "s";
        else if (!input[3].equals("0"))
            return input[3] + "m " + input[4] + "s";
        else
            return input[4] + "s";
    }

    public static int interpolateColor(int colorA, int colorB, float percentB) {
        float rA = (colorA >> 16 & 0xFF);
        float gA = (colorA >> 8 & 0xFF);
        float bA = (colorA & 0xFF);
        float rB = (colorB >> 16 & 0xFF);
        float gB = (colorB >> 8 & 0xFF);
        float bB = (colorB & 0xFF);

        float r = rA + (rB - rA) * percentB;
        float g = gA + (gB - gA) * percentB;
        float b = bA + (bB - bA) * percentB;
        return (((int) r & 0xFF) << 16) | (((int) g & 0xFF) << 8) | ((int) b & 0xFF);
    }

    public static double interp(double x, double y, float interp) {
        return x + (y - x) * interp;
    }

    public static double interp(double x, double y, double interp) {
        return x + (y - x) * interp;
    }

    public static int[] intCollectionToArray(Collection<Integer> in) {
        return intCollectionToArray(in, i -> (int) i);
    }

    public static int[] intCollectionToArray(Collection<Integer> in, ToIntFunction<? super Object> mapper) {
        return Arrays.stream(in.toArray()).mapToInt(mapper).toArray();
    }

    public static int[] collectionToIntArray(Collection<?> in, ToIntFunction<? super Object> mapper) {
        return Arrays.stream(in.toArray()).mapToInt(mapper).toArray();
    }

    public static void shuffleIntArray(int[] array) {
        Random rand = ThreadLocalRandom.current();
        for (int i = array.length - 1; i > 0; i--) {
            int r = rand.nextInt(i + 1);
            int temp = array[r];
            array[r] = array[i];
            array[i] = temp;
        }
    }

    public static void reverseIntArray(int[] array) {
        int len = array.length;
        for (int i = 0; i < len / 2; i++) {
            int temp = array[i];
            array[i] = array[len - 1 - i];
            array[len - 1 - i] = temp;
        }
    }

    public static double sps(double x) {
        return Math.sin(Math.PI / 2D * Math.cos(x));
    }

    public static double safeClamp(double val, double min, double max) {
        val = Mth.clamp(val, min, max);
        if (Double.isNaN(val)) {
            val = (min + max) / 2D;
        }
        return val;
    }

    public static float safeClamp(float val, float min, float max) {
        val = Mth.clamp(val, min, max);
        if (Double.isNaN(val)) {
            val = (float) ((min + max) / 2D);
        }
        return val;
    }
}
