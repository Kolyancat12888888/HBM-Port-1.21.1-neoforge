package com.hbm.util;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3f;

public class Vec3NT extends MutableVec3d {

    public Vec3NT() {
        super(0.0D, 0.0D, 0.0D);
    }

    public static Vec3NT ZERO() {
        return new Vec3NT(0, 0, 0);
    }

    public Vec3NT(double x, double y, double z) {
        super(x, y, z);
    }

    public Vec3NT(Vec3 vec) {
        super(vec.x, vec.y, vec.z);
    }

    public Vec3NT(Vec3NT vec) {
        super(vec.x, vec.y, vec.z);
    }

    public static Vec3NT of(double x, double y, double z) {
        return new Vec3NT(x, y, z);
    }

    public static Vec3NT from(Vec3 v) {
        return new Vec3NT(v.x, v.y, v.z);
    }

    public static Vec3NT fromPitchYaw(Vec2 vec) {
        return fromPitchYaw(vec.x, vec.y);
    }

    public static Vec3NT fromPitchYaw(float pitchDeg, float yawDeg) {
        final float yaw = (float) (-yawDeg * (Math.PI / 180.0) - Math.PI);
        final float pitch = (float) (-pitchDeg * (Math.PI / 180.0));
        final float cy = Mth.cos(yaw);
        final float sy = Mth.sin(yaw);
        final float cp = Mth.cos(pitch);
        final float sp = Mth.sin(pitch);
        return new Vec3NT(sy * cp, sp, cy * cp);
    }

    public static double getMinX(Vec3... vecs) {
        double min = Double.POSITIVE_INFINITY;
        for (Vec3 v : vecs) if (v.x < min) min = v.x;
        return min;
    }

    public static double getMinY(Vec3... vecs) {
        double min = Double.POSITIVE_INFINITY;
        for (Vec3 v : vecs) if (v.y < min) min = v.y;
        return min;
    }

    public static double getMinZ(Vec3... vecs) {
        double min = Double.POSITIVE_INFINITY;
        for (Vec3 v : vecs) if (v.z < min) min = v.z;
        return min;
    }

    public static double getMaxX(Vec3... vecs) {
        double max = Double.NEGATIVE_INFINITY;
        for (Vec3 v : vecs) if (v.x > max) max = v.x;
        return max;
    }

    public static double getMaxY(Vec3... vecs) {
        double max = Double.NEGATIVE_INFINITY;
        for (Vec3 v : vecs) if (v.y > max) max = v.y;
        return max;
    }

    public static double getMaxZ(Vec3... vecs) {
        double max = Double.NEGATIVE_INFINITY;
        for (Vec3 v : vecs) if (v.z > max) max = v.z;
        return max;
    }

    @Override
    public Vec3NT set(double x, double y, double z) {
        super.set(x, y, z);
        return this;
    }

    @Override
    public Vec3NT set(Vec3 v) {
        super.set(v);
        return this;
    }

    @Override
    public Vec3NT setX(double x) {
        super.setX(x);
        return this;
    }

    @Override
    public Vec3NT setY(double y) {
        super.setY(y);
        return this;
    }

    @Override
    public Vec3NT setZ(double z) {
        super.setZ(z);
        return this;
    }

    @Override
    public Vec3NT zero() {
        super.zero();
        return this;
    }

    @Override
    public Vec3NT addSelf(double dx, double dy, double dz) {
        super.addSelf(dx, dy, dz);
        return this;
    }

    @Override
    public Vec3NT addSelf(Vec3 v) {
        super.addSelf(v);
        return this;
    }

    @Override
    public Vec3NT subSelf(double dx, double dy, double dz) {
        super.subSelf(dx, dy, dz);
        return this;
    }

    @Override
    public Vec3NT subSelf(Vec3 v) {
        super.subSelf(v);
        return this;
    }

    @Override
    public Vec3NT scaleSelf(double s) {
        super.scaleSelf(s);
        return this;
    }

    @Override
    public Vec3NT mulAddSelf(double s, Vec3 v) {
        super.mulAddSelf(s, v);
        return this;
    }

    @Override
    public Vec3NT normalizeSelf() {
        super.normalizeSelf();
        return this;
    }

    @Override
    public Vec3NT rotateYawSelf(float yaw) {
        super.rotateYawSelf(yaw);
        return this;
    }

    @Override
    public Vec3NT rotatePitchSelf(float pitch) {
        super.rotatePitchSelf(pitch);
        return this;
    }

    @Override
    public Vec3NT rotateRollSelf(float roll) {
        super.rotateRollSelf(roll);
        return this;
    }

    @Override
    public Vec3NT rotateYawSelf(double yaw) {
        super.rotateYawSelf(yaw);
        return this;
    }

    @Override
    public Vec3NT rotatePitchSelf(double pitch) {
        super.rotatePitchSelf(pitch);
        return this;
    }

    @Override
    public Vec3NT rotateRollSelf(double roll) {
        super.rotateRollSelf(roll);
        return this;
    }

    @Override
    public Vec3NT lerpSelf(Vec3 other, double t) {
        super.lerpSelf(other, t);
        return this;
    }

    @Override
    public Vec3NT add(double x, double y, double z) {
        return new Vec3NT(this.x + x, this.y + y, this.z + z);
    }

    @Override
    public Vec3NT add(Vec3 vec) {
        return new Vec3NT(this.x + vec.x, this.y + vec.y, this.z + vec.z);
    }

    @Override
    public Vec3NT subtract(double x, double y, double z) {
        return new Vec3NT(this.x - x, this.y - y, this.z - z);
    }

    @Override
    public Vec3NT subtract(Vec3 vec) {
        return new Vec3NT(this.x - vec.x, this.y - vec.y, this.z - vec.z);
    }

    @Override
    public Vec3NT scale(double factor) {
        return new Vec3NT(this.x * factor, this.y * factor, this.z * factor);
    }

    @Override
    public Vec3NT normalize() {
        double len = Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
        return (len < 1.0E-4D) ? new Vec3NT(0.0, 0.0, 0.0) : new Vec3NT(this.x / len, this.y / len, this.z / len);
    }

    @Override
    public Vec3NT crossProduct(Vec3 vec) {
        return new Vec3NT(this.y * vec.z - this.z * vec.y, this.z * vec.x - this.x * vec.z, this.x * vec.y - this.y * vec.x);
    }

    @Override
    public Vec3NT rotateYaw(float yaw) {
        double c = Mth.cos(yaw), s = Mth.sin(yaw);
        return new Vec3NT(this.x * c + this.z * s, this.y, this.z * c - this.x * s);
    }

    @Override
    public Vec3NT rotatePitch(float pitch) {
        double c = Mth.cos(pitch), s = Mth.sin(pitch);
        return new Vec3NT(this.x, this.y * c + this.z * s, this.z * c - this.y * s);
    }

    @Override
    public Vec3NT rotateRoll(float roll) {
        double c = Mth.cos(roll), s = Mth.sin(roll);
        return new Vec3NT(this.x * c + this.y * s, this.y * c - this.x * s, this.z);
    }

    @Override
    public Vec3NT rotateYaw(double yaw) {
        double c = Math.cos(yaw), s = Math.sin(yaw);
        return new Vec3NT(this.x * c + this.z * s, this.y, this.z * c - this.x * s);
    }

    @Override
    public Vec3NT rotatePitch(double pitch) {
        double c = Math.cos(pitch), s = Math.sin(pitch);
        return new Vec3NT(this.x, this.y * c + this.z * s, this.z * c - this.y * s);
    }

    @Override
    public Vec3NT rotateRoll(double roll) {
        double c = Math.cos(roll), s = Math.sin(roll);
        return new Vec3NT(this.x * c + this.y * s, this.y * c - this.x * s, this.z);
    }

    @Override
    public Vec3NT lerp(Vec3 other, double t) {
        return new Vec3NT(this.x + (other.x - this.x) * t, this.y + (other.y - this.y) * t, this.z + (other.z - this.z) * t);
    }

    public Vec3NT multiply(double m) {
        set(this.x * m, this.y * m, this.z * m);
        return this;
    }

    public Vec3NT multiply(double mx, double my, double mz) {
        set(this.x * mx, this.y * my, this.z * mz);
        return this;
    }

    public Vec3NT addi(Vec3 v) {
        addSelf(v);
        return this;
    }

    public Vec3NT addi(double dx, double dy, double dz) {
        addSelf(dx, dy, dz);
        return this;
    }

    public double distanceTo(double x, double y, double z) {
        double dx = x - this.x, dy = y - this.y, dz = z - this.z;
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public Vec3NT rotateAroundXRad(double a) {
        double c = Math.cos(a), s = Math.sin(a);
        double ny = this.y * c + this.z * s, nz = this.z * c - this.y * s;
        set(this.x, ny, nz);
        return this;
    }

    public Vec3NT rotateAroundYRad(double a) {
        double c = Math.cos(a), s = Math.sin(a);
        double nx = this.x * c + this.z * s, nz = -this.x * s + this.z * c;
        set(nx, this.y, nz);
        return this;
    }

    public Vec3NT rotateAroundZRad(double a) {
        double c = Math.cos(a), s = Math.sin(a);
        double nx = this.x * c + this.y * s, ny = this.y * c - this.x * s;
        set(nx, ny, this.z);
        return this;
    }

    public Vec3NT rotateAroundXDeg(double d) {
        return rotateAroundXRad(Math.toRadians(d));
    }

    public Vec3NT rotateAroundYDeg(double d) {
        return rotateAroundYRad(Math.toRadians(d));
    }

    public Vec3NT rotateAroundZDeg(double d) {
        return rotateAroundZRad(Math.toRadians(d));
    }

    public Vec3 toVec3d() {
        return toImmutable();
    }

    @Override
    public Vec3NT clone() {
        return (Vec3NT) super.clone();
    }

    public static Vec3NT createVectorHelper(double x, double y, double z) {
        return new Vec3NT(x, y, z);
    }

    public static Vec3NT createVectorHelper(Entity e) {
        return new Vec3NT(e.getX(), e.getY(), e.getZ());
    }

    public double distanceTo(Entity e) {
        return distanceTo(e.getX(), e.getY(), e.getZ());
    }

    public Vec3NT negate() {
        return new Vec3NT(-this.x, -this.y, -this.z);
    }

    public Vec3NT copy() {
        return new Vec3NT(this.x, this.y, this.z);
    }

    public Vec3NT mult(float mult) {
        return new Vec3NT(this.x * mult, this.y * mult, this.z * mult);
    }

    public Vec3NT multd(double mult) {
        return new Vec3NT(this.x * mult, this.y * mult, this.z * mult);
    }

    public Vec3NT interpolate(Vec3 other, double inter) {
        return lerp(other, inter);
    }

    public Vec3NT setComponents(double x, double y, double z) {
        return set(x, y, z);
    }

    public Vec3NT max(double d) {
        return new Vec3NT(Math.max(this.x, d), Math.max(this.y, d), Math.max(this.z, d));
    }

    public Vec3NT min(double d) {
        return new Vec3NT(Math.min(this.x, d), Math.min(this.y, d), Math.min(this.z, d));
    }

    public BlockPos toBlockPos() {
        return new BlockPos(Mth.floor(this.x), Mth.floor(this.y), Mth.floor(this.z));
    }

    public Matrix3f outerProduct(Vec3 other) {
        return new Matrix3f(
                (float) (this.x * other.x), (float) (this.x * other.y), (float) (this.x * other.z),
                (float) (this.y * other.x), (float) (this.y * other.y), (float) (this.y * other.z),
                (float) (this.z * other.x), (float) (this.z * other.y), (float) (this.z * other.z));
    }

    public Vec3NT matTransform(Matrix3f mat) {
        return new Vec3NT(
                mat.m00 * this.x + mat.m01 * this.y + mat.m02 * this.z,
                mat.m10 * this.x + mat.m11 * this.y + mat.m12 * this.z,
                mat.m20 * this.x + mat.m21 * this.y + mat.m22 * this.z);
    }

    @Override
    public String toString() {
        return "Vec3NT[" + this.x + ", " + this.y + ", " + this.z + "]";
    }
}
