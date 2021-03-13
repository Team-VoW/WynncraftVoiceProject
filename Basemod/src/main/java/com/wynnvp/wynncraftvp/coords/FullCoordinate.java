package com.wynnvp.wynncraftvp.coords;

public class FullCoordinate {
    private final double x;
    private final double y;
    private final double z;

    public FullCoordinate(double x, double y, double z) {
        this.x = x;
        this.z = z;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }
}
