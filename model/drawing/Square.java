package cs355.model.drawing;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

/**
 * Add your square code here. You can add fields, but you cannot change the ones
 * that already exist. This includes the names!
 */
public class Square extends Shape {

    // The size of this Square.
    private double size;

        // The upper left corner of the square
    // private Point2D.Double upperLeft;
    /**
     * Basic constructor that sets all fields.
     *
     * @param color the color for the new shape.
     * @param center the center of the new shape.
     * @param size the size of the new shape.
     */
    public Square(Color color, Point2D.Double center, double size) {
        // Initialize the superclass.
        super(color, center);
        System.out.println("this is initial center: " + center);
               // this.upperLeft = center;
        // Set the field.
        this.size = size;
    }

    /**
     * Getter for this Square's size.
     *
     * @return the size as a double.
     */
    public double getSize() {
        return size;
    }

    /**
     * Setter for this Square's size.
     *
     * @param size the new size.
     */
    public void setSize(double size) {
        this.size = size;
    }

    /**
     * Add your code to do an intersection test here. You shouldn't need the
     * tolerance.
     *
     * @param pt = the point to test against.
     * @param tolerance = the allowable tolerance.
     * @return true if pt is in the shape, false otherwise.
     */
    @Override
    public boolean pointInShape(Point2D.Double objCoord, double tolerance, double scale) {
//        Point2D.Double objCoord = new Point2D.Double();
//// create a new transformation (defaults to identity)
//        AffineTransform worldToObj = new AffineTransform();
//// rotate back from its orientation (last transformation)
//        worldToObj.rotate(-rotation);
//// translate back from its position in the world (first transformation)
//        worldToObj.translate(-center.x, -center.y);
//// and transform point from world to object
//        worldToObj.transform(pt, objCoord);

        if (objCoord.x < size / 2 && objCoord.x > -size / 2) {
            if (objCoord.y < size / 2 && objCoord.y > - size / 2) {
                return true;
            }
        }
        return false;

    }

    /* Setter for this Rectangle's upper left corner.
     * @param upperLeft the new upper left corner.
     //	 */
//	public void setUpperLeft(Point2D.Double upperLeft) {
//		this.upperLeft = upperLeft;
//	}
    /**
     * Getter for this Rectangle's upper left corner.
     *
     * @return the upper left corner as a Java point.
     */
    public Point2D.Double getUpperLeft() {
        Point2D.Double upperLeft = new Point2D.Double(center.x - size / 2, center.y - size / 2);
        return upperLeft;
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
//        //g2d.drawOval(-5,(-size/2)-15, 10, 10);
//        System.out.println(pt);
        double scaledWidth= 20*scale;

        Point2D.Double handleCenter = new Point2D.Double(0,-(int)size-(int)scaledWidth/2);
        double distance = Math.sqrt((handleCenter.x-objCoord.x)*(handleCenter.x-objCoord.x) + (handleCenter.y-objCoord.y)*(handleCenter.y-objCoord.y));
        if (distance<scaledWidth/2) {
                return true;
        }
        return false;
    }

}
