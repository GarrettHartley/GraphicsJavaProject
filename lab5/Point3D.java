package cs355.lab5;

/**
 *
 */
public class Point3D 
{
    public double x;
    public double y;
    public double z;
    
    public Point3D(double newX, double newY, double newZ)
    {
        x = newX;
        y = newY;
        z = newZ;
    }

    double length() 
    {
        return Math.sqrt(x*x+y*y+z*z);
    }
    
    @Override
    public String toString()
    {
        return "X: "+x+", Y: "+y+", Z:"+z;
    }

    void Normalize() 
    {
        Double denominator = Math.sqrt(x*x+y*y+z*z);
        x/=denominator;
        y/=denominator;
        z/=denominator;
    }

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}
    
    
}
