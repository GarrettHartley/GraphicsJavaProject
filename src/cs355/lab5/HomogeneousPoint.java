package cs355.lab5;

public class HomogeneousPoint {
	double coordinates[] = new double[4];
	double x = 0;
	double y = 0;
	double z = 0;
	double w = 1;
	public HomogeneousPoint(double x, double y, double z, double w){
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
		coordinates[0] = x;
		coordinates[1] = y;
		coordinates[2] = z;
		coordinates[3] = w;
	}
	
	public double[] getCoordinates(){
		return coordinates;
	}
	
//	public boolean isInClipView(){
//		if(isInNearClipView()||isInFarClipView()){
//			return true;
//		}
//		return false;
//	}
    
    public boolean isInNearClipView(){
        if(z>-w){
            return true;
        }
        return false;
    }
    
    public boolean isOutFarClipView(){
        if(z<w){
            return false;
        }
        return true; 
    }
    
    public boolean isOutLeftXClipView(){
        if(x<w){
            return  false;
        }
        return true;
    }
    
    public boolean isOutRightXClipView(){
        if(x>-w){
            return  false;
        }
        return true;
    }
    
    public boolean isOutTopYClipView(){
        if(y<w){
            return  false;
        }
        return true;
    }
    
    public boolean isOutBotYClipView(){
        if(y>-w){
            return  false;
        }
        return true;
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

    public double getW() {
        return w;
    }

    public void setW(double w) {
        this.w = w;
    }
    
}
