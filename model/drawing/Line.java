package cs355.model.drawing;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

/**
 * Add your line code here. You can add fields, but you cannot change the ones
 * that already exist. This includes the names!
 */
public class Line extends Shape {

    // The ending point of the line.
    private Point2D.Double end;

    private Point2D.Double movePoint;
    private Point2D.Double stayPoint;

    /**
     * Basic constructor that sets all fields.
     *
     * @param color the color for the new shape.
     * @param start the starting point.
     * @param end the ending point.
     */
    public Line(Color color, Point2D.Double start, Point2D.Double end) {

        // Initialize the superclass.
        super(color, start);

        // Set the field.
        Point2D.Double newEnd = new Point2D.Double(end.x - start.x, end.y - start.y);
        this.end = newEnd;
    }

    /**
     * Getter for this Line's ending point.
     *
     * @return the ending point as a Java point.
     */
    public Point2D.Double getEnd() {
        return end;
    }

    /**
     * Setter for this Line's ending point.
     *
     * @param end the new ending point for the Line.
     */
    public void setEnd(Point2D.Double end) {
        Point2D.Double newEnd = new Point2D.Double(end.x - center.x, end.y - center.y);
        this.end = newEnd;
    }

    /**
     * Add your code to do an intersection test here. You <i>will</i> need the
     * tolerance.
     *
     * @param pt = the point to test against.
     * @param tolerance = the allowable tolerance.
     * @return true if pt is in the shape, false otherwise.
     */
    @Override
    public boolean pointInShape(Point2D.Double objCoord, double tolerance, double scale) {
        Point2D.Double objCenter = worldToObjectTransform(center, this);

        Point2D.Double n = getN(end, objCenter);

        Point2D.Double centerEndpoint = new Point2D.Double(objCoord.x - objCenter.x, objCoord.y - objCenter.y);
        Point2D.Double endEndpoint = new Point2D.Double(objCoord.x - end.x, objCoord.y - end.y);
        Point2D.Double newN = new Point2D.Double(n.y, -n.x);

        if (Math.abs(dotProduct(objCoord, n) - dotProduct(objCenter, n)) < tolerance*scale
                && dotProduct(centerEndpoint, newN) > 1 && dotProduct(endEndpoint, newN) < 1) {
            return true;
        }
        return false;
    }

    public Point2D.Double worldToObjectTransform(Point2D.Double worldPoint, Shape s) {
        Point2D.Double objCoord = new Point2D.Double();
        AffineTransform worldToObj = new AffineTransform();
        AffineTransform objToWorldTranslate = new AffineTransform(1, 0, 0, 1, -s.getCenter().x, -s.getCenter().y);
        AffineTransform objToWorldRotate = new AffineTransform(Math.cos(-s.getRotation()), Math.sin(-s.getRotation()), -Math.sin(-s.getRotation()), Math.cos(-s.getRotation()), 0, 0);
        worldToObj.concatenate(objToWorldRotate);
        worldToObj.concatenate(objToWorldTranslate);

        worldToObj.transform(worldPoint, objCoord);
        return objCoord;
    }

    public Point2D.Double getN(Point2D.Double a, Point2D.Double b) {
        Point2D.Double newPoint = new Point2D.Double(b.x - a.x, b.y - a.y);
        double x = newPoint.x / Math.sqrt(Math.pow(newPoint.x, 2) + Math.pow(newPoint.y, 2));
        double y = newPoint.y / Math.sqrt(Math.pow(newPoint.x, 2) + Math.pow(newPoint.y, 2));
        return new Point2D.Double(y, -x);
    }

    public double dotProduct(Point2D.Double x, Point2D.Double y) {
        return (x.x * y.x) + (x.y * y.y);
    }

    public boolean betweenEndpoints(Point2D.Double q, Point2D.Double n) {
        Point2D.Double newPoint = new Point2D.Double(q.x - center.x, q.y - center.y);
        Point2D.Double newN = new Point2D.Double(n.y, -n.x);
        if (dotProduct(newPoint, newN) > 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean pointInHandle(Point2D.Double objCoord, double scale) {
        AffineTransform objToWorldTranslate = new AffineTransform(1, 0, 0, 1, center.x, center.y);
        AffineTransform objToWorldRotate = new AffineTransform(Math.cos(rotation), Math.sin(rotation), -Math.sin(rotation), Math.cos(rotation), 0, 0);
        AffineTransform objToWorld = new AffineTransform();
        objToWorld.concatenate(objToWorldTranslate);
        objToWorld.concatenate(objToWorldRotate);
        Point2D.Double pt = new Point2D.Double();
        objToWorld.transform(objCoord, pt);
        Point2D.Double objCenter = worldToObjectTransform(this.center, this);
        double scaledWidth = 20 * scale;
        double scaledCenter = -5 * scale;
        
        double distance = Math.sqrt(((this.end.x - scaledCenter) - objCoord.x) * ((this.end.x - scaledCenter) - objCoord.x) + ((this.end.y - scaledCenter) - objCoord.y) * ((this.end.y - scaledCenter) - objCoord.y));
        if (distance < scaledWidth/2) {
            stayPoint = center;
            return true;
        }
        double distanceTwo = Math.sqrt(((objCenter.x - scaledCenter) - objCoord.x) * ((objCenter.x - scaledCenter) - objCoord.x) + ((objCenter.y - scaledCenter) - objCoord.y) * ((objCenter.y - scaledCenter) - objCoord.y));
        if (distanceTwo < scaledWidth/2) {
            stayPoint = new Point2D.Double(this.end.x + pt.x, this.end.y + pt.y);
            return true;
        }
        return false;
    }

    public Point2D.Double getMovePoint() {
        return movePoint;
    }

    public void setMovePoint(Point2D.Double movePoint) {
        this.movePoint = movePoint;
    }

    public Point2D.Double getStayPoint() {
        return stayPoint;
    }

    public void setStayPoint(Point2D.Double stayPoint) {
        this.stayPoint = stayPoint;
    }

}
