package cs355.model.drawing;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

/**
 * Add your triangle code here. You can add fields, but you cannot
 * change the ones that already exist. This includes the names!
 */
public class Triangle extends Shape {

	// The three points of the triangle.
	private Point2D.Double a;
	private Point2D.Double b;
	private Point2D.Double c;

    int circleHeight;
	/**
	 * Basic constructor that sets all fields.
	 * @param color the color for the new shape.
	 * @param center the center of the new shape.
	 * @param a the first point, relative to the center.
	 * @param b the second point, relative to the center.
	 * @param c the third point, relative to the center.
	 */
	public Triangle(Color color, Point2D.Double center, Point2D.Double a,
					Point2D.Double b, Point2D.Double c)
	{

		// Initialize the superclass.
		super(color, center);
		// Set fields.
		this.a = new Point2D.Double(a.x-center.x,a.y-center.y);
		this.b = new Point2D.Double(b.x-center.x,b.y-center.y);
		this.c = new Point2D.Double(c.x-center.x,c.y-center.y);

	}

	/**
	 * Getter for the first point.
	 * @return the first point as a Java point.
	 */
	public Point2D.Double getA() {
		return this.a;
	}

	/**
	 * Setter for the first point.
	 * @param a the new first point.
	 */
	public void setA(Point2D.Double a) {
		this.a = a;
	}

	/**
	 * Getter for the second point.
	 * @return the second point as a Java point.
	 */
	public Point2D.Double getB() {
		return this.b;
	}

	/**
	 * Setter for the second point.
	 * @param b the new second point.
	 */
	public void setB(Point2D.Double b) {
		this.b = b;
	}

	/**
	 * Getter for the third point.
	 * @return the third point as a Java point.
	 */
	public Point2D.Double getC() {
		return this.c;
	}

	/**
	 * Setter for the third point.
	 * @param c the new third point.
	 */
	public void setC(Point2D.Double c) {
		this.c = c;
	}

	/**
	 * Add your code to do an intersection test
	 * here. You shouldn't need the tolerance.
	 * @param pt = the point to test against.
	 * @param tolerance = the allowable tolerance.
	 * @return true if pt is in the shape,
	 *		   false otherwise.
	 */
	@Override
	public boolean pointInShape(Point2D.Double objCoord, double tolerance, double scale) {
//            Point2D.Double objCoord = new Point2D.Double();
//// create a new transformation (defaults to identity)
//        AffineTransform worldToObj = new AffineTransform();
//// rotate back from its orientation (last transformation)
//        worldToObj.rotate(-rotation);
//// translate back from its position in the world (first transformation)
//        worldToObj.translate(-center.x, -center.y);
//// and transform point from world to object
//        worldToObj.transform(pt, objCoord);

                //(q - p0) · (p1 - p0)T > 0
                //(q - p1) · (p2 - p1)T > 0
                //(q - p2) · (p0 - p2)T > 0
            if(isInside(objCoord,a,b)&&isInside(objCoord,b,c)&&isInside(objCoord,c,a)){
               System.out.println("we are inside triangle!");
                return true;
            }
                System.out.println("not in");
            return false;
	}
        
        public boolean isInside(Point2D.Double q, Point2D.Double pointA,Point2D.Double pointB){
            Point2D.Double newPointA =subtractPoints(q,pointA);
            Point2D.Double newPointb =subtractPoints(pointA, pointB);
            if(dotProduct(newPointA,getNormPoint(newPointb))>0){
                return true;
            }
            return false;
        }
        
        public Point2D.Double subtractPoints(Point2D.Double pointA,Point2D.Double pointB){
            Point2D.Double newPoint = new Point2D.Double(pointA.x-pointB.x, pointA.y - pointB.y);
            return newPoint;
        }
        
        public Point2D.Double getNormPoint(Point2D.Double inpoint){
            Point2D.Double newPoint = new Point2D.Double(inpoint.y, -inpoint.x);
            return newPoint;
        }
        
        public double dotProduct(Point2D.Double x, Point2D.Double y){
            double result =(x.x*y.x)+(x.y*y.y);
            return result;
        }
            

    @Override
    public boolean pointInHandle(Point2D.Double objCoord, double scale) {
//                Point2D.Double objCoord = new Point2D.Double();
//// create a new transformation (defaults to identity)
//        AffineTransform worldToObj = new AffineTransform();
//// rotate back from its orientation (last transformation)
//        worldToObj.rotate(-rotation);
//// translate back from its position in the world (first transformation)
//        worldToObj.translate(-center.x, -center.y);
//// and transform point from world to object
//        worldToObj.transform(pt, objCoord);
        //g2d.drawOval(-5,(-size/2)-15, 10, 10);
        int scaledWidth = (int)(20*scale);
                System.out.println("scaledwidth: "+scaledWidth);

        Point2D.Double handleCenter = new Point2D.Double((int)this.getA().x+scaledWidth/2,((int)this.getA().y)+scaledWidth/2);
        System.out.println("distance: "+(handleCenter.x-objCoord.x)*(handleCenter.x-objCoord.x) + (handleCenter.y-objCoord.y)*(handleCenter.y-objCoord.y));
        double distance = Math.sqrt((handleCenter.x-objCoord.x)*(handleCenter.x-objCoord.x) + (handleCenter.y-objCoord.y)*(handleCenter.y-objCoord.y));
        if (distance<scaledWidth/2) {
                return true;
        }
        return false;
    }

    public int getCircleHeight() {
        return circleHeight;
    }

    public void setCircleHeight(int circleHeight) {
        this.circleHeight = circleHeight;
    }
        
        

}
