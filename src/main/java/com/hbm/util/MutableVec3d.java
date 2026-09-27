package com.hbm.util;

import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class MutableVec3d implements Cloneable {
    public double x;
    public double y;
    public double z;

    private static final double DEG2RAD = Math.PI / 180.0;

    public MutableVec3d() {
        this(0.0D, 0.0D, 0.0D);
    }

    public MutableVec3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public MutableVec3d(Vec3 other) {
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
    }

    public static MutableVec3d fromPitchYawMutable(Vec2 vec) {
        return fromPitchYawMutable(vec.x, vec.y);
    }

    public static MutableVec3d fromPitchYawMutable(float pitchDeg, float yawDeg) {
        final float yaw = (float) (-yawDeg * DEG2RAD - Math.PI);
        final float pitch = (float) (-pitchDeg * DEG2RAD);
        final float cy = Mth.cos(yaw);
        final float sy = Mth.sin(yaw);
        final float cp = Mth.cos(pitch);
        final float sp = Mth.sin(pitch);
        return new MutableVec3d(sy * cp, sp, cy * cp);
    }

    public MutableVec3d set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public MutableVec3d set(Vec3 v) {
        return set(v.x, v.y, v.z);
    }

    public MutableVec3d setX(double x) {
        this.x = x;
        return this;
    }

    public MutableVec3d setY(double y) {
        this.y = y;
        return this;
    }

    public MutableVec3d setZ(double z) {
        this.z = z;
        return this;
    }

    public MutableVec3d zero() {
        return set(0.0D, 0.0D, 0.0D);
    }

    public MutableVec3d addSelf(double dx, double dy, double dz) {
        this.x += dx;
        this.y += dy;
        this.z += dz;
        return this;
    }

    public MutableVec3d addSelf(Vec3 v) {
        return addSelf(v.x, v.y, v.z);
    }

    public MutableVec3d subSelf(double dx, double dy, double dz) {
        this.x -= dx;
        this.y -= dy;
        this.z -= dz;
        return this;
    }

    public MutableVec3d subSelf(Vec3 v) {
        return subSelf(v.x, v.y, v.z);
    }

    public MutableVec3d scaleSelf(double s) {
        this.x *= s;
        this.y *= s;
        this.z *= s;
        return this;
    }

    public MutableVec3d mulAddSelf(double s, Vec3 v) {
        this.x += s * v.x;
        this.y += s * v.y;
        this.z += s * v.z;
        return this;
    }

    public MutableVec3d normalizeSelf() {
        double len = Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
        if (len < 1.0E-4D) return set(0.0D, 0.0D, 0.0D);
        double inv = 1.0D / len;
        return scaleSelf(inv);
    }

    public MutableVec3d rotateYawSelf(float yaw) {
        double c = Mth.cos(yaw);
        double s = Mth.sin(yaw);
        double nx = this.x * c + this.z * s;
        double nz = this.z * c - this.x * s;
        return set(nx, this.y, nz);
    }

    public MutableVec3d rotatePitchSelf(float pitch) {
        double c = Mth.cos(pitch);
        double s = Mth.sin(pitch);
        double ny = this.y * c + this.z * s;
        double nz = this.z * c - this.y * s;
        return set(this.x, ny, nz);
    }

    public MutableVec3d rotateRollSelf(float roll) {
        double c = Mth.cos(roll);
        double s = Mth.sin(roll);
        double nx = this.x * c + this.y * s;
        double ny = this.y * c - this.x * s;
        return set(nx, ny, this.z);
    }

    public MutableVec3d rotateYawSelf(double yaw) {
        double c = Math.cos(yaw);
        double s = Math.sin(yaw);
        double nx = this.x * c + this.z * s;
        double nz = this.z * c - this.x * s;
        return set(nx, this.y, nz);
    }

    public MutableVec3d rotatePitchSelf(double pitch) {
        double c = Math.cos(pitch);
        double s = Math.sin(pitch);
        double ny = this.y * c + this.z * s;
        double nz = this.z * c - this.y * s;
        return set(this.x, ny, nz);
    }

    public MutableVec3d rotateRollSelf(double roll) {
        double c = Math.cos(roll);
        double s = Math.sin(roll);
        double nx = this.x * c + this.y * s;
        double ny = this.y * c - this.x * s;
        return set(nx, ny, this.z);
    }

    public MutableVec3d lerpSelf(Vec3 other, double t) {
        this.x += (other.x - this.x) * t;
        this.y += (other.y - this.y) * t;
        this.z += (other.z - this.z) * t;
        return this;
    }

    public MutableVec3d add(double x, double y, double z) {
        return new MutableVec3d(this.x + x, this.y + y, this.z + z);
    }

    public MutableVec3d add(Vec3 vec) {
        return new MutableVec3d(this.x + vec.x, this.y + vec.y, this.z + vec.z);
    }

    public MutableVec3d subtract(double x, double y, double z) {
        return new MutableVec3d(this.x - x, this.y - y, this.z - z);
    }

    public MutableVec3d subtract(Vec3 vec) {
        return new MutableVec3d(this.x - vec.x, this.y - vec.y, this.z - vec.z);
    }

    public MutableVec3d scale(double factor) {
        return new MutableVec3d(this.x * factor, this.y * factor, this.z * factor);
    }

    public MutableVec3d normalize() {
        double len = Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
        return (len < 1.0E-4D) ? new MutableVec3d(0.0, 0.0, 0.0) : new MutableVec3d(this.x / len, this.y / len, this.z / len);
    }

    public MutableVec3d crossProduct(Vec3 vec) {
        return new MutableVec3d(this.y * vec.z - this.z * vec.y, this.z * vec.x - this.x * vec.z, this.x * vec.y - this.y * vec.x);
    }

    public MutableVec3d rotateYaw(float yaw) {
        double c = Mth.cos(yaw);
        double s = Mth.sin(yaw);
        return new MutableVec3d(this.x * c + this.z * s, this.y, this.z * c - this.x * s);
    }

    public MutableVec3d rotatePitch(float pitch) {
        double c = Mth.cos(pitch);
        double s = Mth.sin(pitch);
        return new MutableVec3d(this.x, this.y * c + this.z * s, this.z * c - this.y * s);
    }

    public MutableVec3d rotateRoll(float roll) {
        double c = Mth.cos(roll);
        double s = Mth.sin(roll);
        return new MutableVec3d(this.x * c + this.y * s, this.y * c - this.x * s, this.z);
    }

    public MutableVec3d rotateYaw(double yaw) {
        double c = Math.cos(yaw);
        double s = Math.sin(yaw);
        return new MutableVec3d(this.x * c + this.z * s, this.y, this.z * c - this.x * s);
    }

    public MutableVec3d rotatePitch(double pitch) {
        double c = Math.cos(pitch);
        double s = Math.sin(pitch);
        return new MutableVec3d(this.x, this.y * c + this.z * s, this.z * c - this.y * s);
    }

    public MutableVec3d rotateRoll(double roll) {
        double c = Math.cos(roll);
        double s = Math.sin(roll);
        return new MutableVec3d(this.x * c + this.y * s, this.y * c - this.x * s, this.z);
    }

    public MutableVec3d lerp(Vec3 other, double t) {
        return new MutableVec3d(this.x + (other.x - this.x) * t, this.y + (other.y - this.y) * t, this.z + (other.z - this.z) * t);
    }

    public Vec3i toVec3i() {
        return new Vec3i(Mth.floor(this.x), Mth.floor(this.y), Mth.floor(this.z));
    }

    public Vec3 toImmutable() {
        return new Vec3(this.x, this.y, this.z);
    }

    public Vec3 toVec3() {
        return toImmutable();
    }

    @Override
    public MutableVec3d clone() {
        try {
            return (MutableVec3d) super.clone();
        } catch (CloneNotSupportedException e) {
            return new MutableVec3d(this.x, this.y, this.z);
        }
    }
}
