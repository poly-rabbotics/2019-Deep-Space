public class Vector {
    byte x0, y0, x1, y1;
    public Vector(x0, y0, x1, y1) {
        this.x0 = x0;
        this.y0 = y0;
        this.x1 = x1;
        this.y1 = y1;
    }
    public byte getX0()
    {
        return x0;
    }
    public byte getY0()
    {
        return y0;
    }
    public byte getX1()
    {
        return x1;
    }
    public byte getY1()
    {
        return y1;
    }
    public double getSlope()
    {
        return (x1-x0)/(y1-y0);
    }
}