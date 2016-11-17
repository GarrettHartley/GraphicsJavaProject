package cs355.model.drawing;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

/**
 * Add your ellipse code here. You can add fields, but you cannot
 * change the ones that already exist. This includes the names!
 */
public class Ellipse extends Shape {

	// The width of this shape.
	private double width;

	// The height of this shape.
	private double height;

	/**
	 * Basic constructor that sets all fields.
	 * @param color the color for the new shape.
	 * @param center the center of the new shape.
	 * @param width the width of the new shape.
	 * @param height the height of the new shape.
	 */
	public Ellipse(Color color, Point2D.Double center, double width, double height) {

		// Initialize the superclass.
		super(color, center);

		// Set fields.
		this.width = width;
		this.height = height;
	}

	/**
	 * Getter for this shape's width.
	 * @return this shape's width as a double.
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * Setter for this shape's width.
	 * @param width the new width.
	 */
	public void setWidth(double width) {
		this.width = width;
	}

	/**
	 * Getter for this shape's height.
	 * @return this shape's height as a double.
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * Setter for this shape's height.
	 * @param height the new height.
	 */
	public void setHeight(double height) {
		this.height = height;
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
	public boolean pointInShape(Point2D.Double pt, double tolerance, double scale) {
//	Point2D.Double objCoord = new Point2D.Double();
//
//        // create a new transformation (defaults to identity)
//        AffineTransform worldToObj = new AffineTransform();
//        // rotate back from its orientation (last transformation)
//        worldToObj.rotate(-rotation);
//        // translate back from its position in the world (first transformation)
//        worldToObj.translate(-center.x, -center.y);
//        // and transform point from world to object
//        worldToObj.transform(pt, objCoord);

        if (Math.pow(((pt.x)/(width/2)),2)+Math.pow(((pt.y)/(height/2)),2)<1) {
                return true;
        }
        return false;
	}
        
                /**
	 * Getter for this Rectangle's upper left corner.
	 * @return the upper left corner as a Java point.
	 */
	public Point2D.Double getUpperLeft() {
            return new Point2D.Double((center.x*2)-width,(center.y*2)-height);
	}

    @Override
    public boolean pointInHandle(Point2D.Double objCoord, double scale) {
//        Point2D.Double objCoord = new Point2D.Double();
//// create a new transformation (defaults to identity)
//        AffineTransform worldToObj = new AffineTransform();
//// rotate back from its orientation (last transformation)
//        worldToObj.rotate(-rotation);
//// translate back from its position in the world (first transformation)
//        worldToObj.translate(-center.x, -center.y);
//// and transform point from world to object
//        worldToObj.transform(pt, objCoord);
//g2d.drawOval(-5,(-size/2)-15, 10, 10);
        double scaledWidth= 20*scale;

//        g2d.drawOval(-(int)scaledWidth/2,(-(int)height/2-(int)scaledWidth), (int)scaledWidth, (int)scaledWidth);

        Point2D.Double handleCenter = new Point2D.Double(0,(-(int)height/2-((int)scaledWidth)/2));
        double distance = Math.sqrt((handleCenter.x-objCoord.x)*(handleCenter.x-objCoord.x) + (handleCenter.y-objCoord.y)*(handleCenter.y-objCoord.y));
        if (distance<(int)scaledWidth/2) {
                return true;
        }
        return false;
    }
    

}
