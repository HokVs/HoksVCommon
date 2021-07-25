package dev.hoksv.common.spigot.location;


public class LazyLocation {
    public int x;
    public int y;
    public int z;

    public LazyLocation(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public LazyLocation(float x, float y, float z) {
        this.x = (int) x;
        this.y = (int) y;
        this.z = (int) z;
    }

    public LazyLocation(double x, double y, double z) {
        this.x = (int) x;
        this.y = (int) y;
        this.z = (int) z;
    }


    public int getX() {
        return x;
    }



    public void setX(int x) {
        this.x = x;
    }


    public int getY() {
        return y;
    }


    public void setY(int y) {
        this.y = y;
    }


    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    @Override
    public String toString() {
        return this.x + "," + this.y + "," + this.z;
    }
}
