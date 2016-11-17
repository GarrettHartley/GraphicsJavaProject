/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs355.view;

import cs355.GUIFunctions;
import cs355.controller.SharedViewInfo;
import cs355.lab5.HomogeneousPoint;
import cs355.lab5.HouseModel;
import cs355.lab5.Line3D;
import cs355.lab5.Matrix;
import cs355.lab5.WireFrame;
import java.awt.Graphics2D;
import cs355.model.Model;
import cs355.model.drawing.Circle;
import cs355.model.drawing.Ellipse;
import cs355.model.drawing.Line;
import cs355.model.drawing.Rectangle;
import cs355.model.drawing.Shape;
import cs355.model.drawing.Square;
import cs355.model.drawing.Triangle;
import cs355.model.image.CS355Image;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
/**
 *
 */
public class View implements ViewRefresher{
    Model model;
    SharedViewInfo sharedInfo;
    private WireFrame houseModel = new HouseModel();


    public View(Model model, SharedViewInfo sharedInfo) {
        this.model = model;
        this.sharedInfo = sharedInfo;
    }

    
    @Override
    public void refreshView(Graphics2D g2dWorld) {

    CS355Image image = model.getImage();
    Graphics2D g2dWorldImage = g2dWorld;
    
    if(image.getHeight()>0 && sharedInfo.isDrawImage()){
    g2dWorldImage = houseToWorldToViewTransform(g2dWorldImage);
    g2dWorldImage.drawImage(model.getImage().getImage(), null, (2048/2)-(image.getWidth()/2),(2048/2)-(image.getHeight()/2) );
    }
    
    Graphics2D g2dWorldTwo = g2dWorld;
    
    
        
        for (Shape s : model.getShapes()){

   
                Graphics2D g2d = objectToWorldToViewTransform(g2dWorld,s);
                g2d.setStroke(new BasicStroke((float)sharedInfo.getScale()));
                if(s instanceof Line){

                    Line currentLine = (Line)s;
                    g2d.setColor(s.getColor());
                    
                    g2d.drawLine(0, 0, (int)currentLine.getEnd().x, (int)currentLine.getEnd().y);
                }
                if(s instanceof Rectangle){

                    
                    Rectangle currentRectangle = (Rectangle)s;
                    int x = -((int)currentRectangle.getWidth()/2);
                    int y = -((int)currentRectangle.getHeight()/2);
                    int width = (int)currentRectangle.getWidth();
                    int height = (int)currentRectangle.getHeight();
                    
                    g2d.setColor(s.getColor());
                    g2d.fillRect(x,y,width, height);
                }
                if(s instanceof Ellipse){

                    Ellipse currentEllipse = (Ellipse)s;
                    int x = -((int)currentEllipse.getWidth()/2);
                    int y = -((int)currentEllipse.getHeight()/2);
                    int width = (int)currentEllipse.getWidth();
                    int height = (int)currentEllipse.getHeight();
                    g2d.setColor(s.getColor());
                    g2d.fillOval(x,y,width, height);
                }
                if(s instanceof Circle){

                    Circle currentCircle = (Circle)s;
                    int radius = (int)currentCircle.getRadius();
                    g2d.setColor(s.getColor());
                    g2d.fillOval(-radius/2,-radius/2,radius, radius);
                }
                if(s instanceof Square){

                    Square currentSquare = (Square)s;
                    int size = (int)currentSquare.getSize();
                    g2d.setColor(s.getColor());
                    g2d.fillRect(-size/2,-size/2,size, size);
                }
                if( s instanceof Triangle){
                    
                    Triangle triangle = (Triangle)s;
                    int xpoints[] = {(int)triangle.getA().x, (int)triangle.getB().x, (int)triangle.getC().x};
                    int ypoints[] = {(int)triangle.getA().y, (int)triangle.getB().y, (int)triangle.getC().y};
                    int npoints = 3;
                    g2d.setColor(s.getColor());
                    Polygon p = new Polygon(xpoints, ypoints, npoints);  // This polygon represents a triangle with the above vertices.
                    g2d.fillPolygon(p);
                }
            }
            if(model.getSelectedShapeIndex()>-1){
                
                
                drawSelect(model.getSelectedShape(),g2dWorld);
                
            }
            //Graphics2D g2d = objectToWorldToViewTransform(g2dWorld,s);
        if(model.isThreeDimensionalView()){
            Iterator<Line3D> iterator = houseModel.getLines();
        while (iterator.hasNext()) {
            
			Line3D line = iterator.next();
			HomogeneousPoint start = new HomogeneousPoint(line.start.x, line.start.y, line.start.z,1);
			HomogeneousPoint end = new HomogeneousPoint(line.end.x, line.end.y, line.end.z,1);
//            Point2D.Double end = getTwoDimensionalPoint(line.end.x, line.end.y, line.end.z);
            
            ArrayList<Point2D.Double> points = getTwoDimensionalLine(start,end);
            
			if(points!=null){
                Graphics2D g2d = houseToWorldToViewTransform(g2dWorldTwo);
                g2d.setColor(Color.white);
                g2d.drawLine((int)points.get(0).x, (int)points.get(0).y, (int)points.get(1).x, (int)points.get(1).y);
			}
		}
        } 
        
        }

    @Override
    public void update(Observable o, Object arg) {
             GUIFunctions.refresh();
    }
    
    public void drawSelect(Shape s,Graphics2D g2dWorld){
//// object to world                    
//                    AffineTransform objToWorld = new AffineTransform();
//                    objToWorld.translate(s.getCenter().x,s.getCenter().y);
//                    objToWorld.rotate(s.getRotation());    
//// World to View               
//                    AffineTransform worldToView = new AffineTransform();
//                    worldToView.scale(1/sharedInfo.getScale(), 1/sharedInfo.getScale());
//                    worldToView.translate(-sharedInfo.getViewH(), -sharedInfo.getViewV());
//// object To World To View
//                    AffineTransform objToWorldToView = new AffineTransform();
//                    objToWorldToView.concatenate(worldToView);
//                    objToWorldToView.concatenate(objToWorld); 
//                    
//                    g2d.setTransform(objToWorldToView);
        Graphics2D g2d = objectToWorldToViewTransform(g2dWorld,s);
        g2d.setStroke(new BasicStroke((float)sharedInfo.getScale()));
        
        if(s instanceof Line){
                    Line currentLine = (Line)s;
                    g2d.setColor(Color.GREEN);  
                    double scaledWidth= 20*sharedInfo.getScale();
                    double scaledCenter=-10*sharedInfo.getScale();
                    g2d.drawOval((int)scaledCenter, (int)scaledCenter, (int)scaledWidth, (int)scaledWidth);
                    
                    g2d.drawOval(((int)currentLine.getEnd().x+(int)scaledCenter), ((int)currentLine.getEnd().y+(int)scaledCenter), (int)scaledWidth,(int)scaledWidth);
         }
                if(s instanceof Rectangle){
                    Rectangle currentRectangle = (Rectangle)s;

                    int width = (int)currentRectangle.getWidth();
                    int height = (int)currentRectangle.getHeight();
                    double scaledWidth= 20*sharedInfo.getScale();
                    double scaledHeight=20*sharedInfo.getScale();
                    if(model.getSelectedShapeIndex()!=-1){
                        if(s.equals(model.getSelectedShape())){
                            g2d.setColor(Color.GREEN);  
                            g2d.drawRect(-width/2, -height/2, width, height);
                            g2d.drawOval(-(int)scaledWidth/2,-(int)height/2-(int)scaledWidth-5, (int)scaledWidth, (int)scaledWidth);
                        }                   
                    }
                }
                if(s instanceof Ellipse){
                    Ellipse currentEllipse = (Ellipse)s;
                    int width = (int)currentEllipse.getWidth();
                    int height = (int)currentEllipse.getHeight();
                    double scaledWidth= 20*sharedInfo.getScale();
                    if(model.getSelectedShapeIndex()!=-1){
                        if(s.equals(model.getSelectedShape())){
                            g2d.setColor(Color.GREEN);  
                            g2d.drawRect(-width/2, -height/2, width, height);
                            g2d.drawOval(-(int)scaledWidth/2,(-(int)height/2-(int)scaledWidth), (int)scaledWidth, (int)scaledWidth);
                        }                   
                    }
                }
                if(s instanceof Circle){
                    Circle currentCircle = (Circle)s;
                    int radius = (int)currentCircle.getRadius();
                    
                    if(model.getSelectedShapeIndex()!=-1){
                        if(s.equals(model.getSelectedShape())){
                            g2d.setColor(Color.GREEN);  
                            g2d.drawRect(-radius/2, -radius/2, radius, radius);
                        }                   
                    }
                }
                if(s instanceof Square){
                    Square currentSquare = (Square)s;
                    int size = (int)currentSquare.getSize();
                    g2d.fillRect(-size/2,-size/2,size, size);
                    double scaledWidth= 20*sharedInfo.getScale();
                    if(model.getSelectedShapeIndex()!=-1){
                        if(s.equals(model.getSelectedShape())){
                            g2d.setColor(Color.GREEN);  
                            g2d.drawRect(-size/2, -size/2, size, size);
                            g2d.drawOval(-(int)scaledWidth/2,-(int)size-(int)scaledWidth, (int)scaledWidth, (int)scaledWidth);
                        }                   
                    }
                }
                if( s instanceof Triangle&&model.getSelectedShapeIndex()!=-1){
                    
                    Triangle triangle = (Triangle)s;
                    g2d.setColor(Color.GREEN); 
                    Line2D.Double a = new Line2D.Double(triangle.getA().x, triangle.getA().y, triangle.getB().x, triangle.getB().y);
                    Line2D.Double b = new Line2D.Double(triangle.getB().x, triangle.getB().y, triangle.getC().x, triangle.getC().y);
                    Line2D.Double c = new Line2D.Double(triangle.getA().x, triangle.getA().y, triangle.getC().x, triangle.getC().y);
                    int xpoints[] = {(int)triangle.getA().x, (int)triangle.getB().x, (int)triangle.getC().x};
                    int ypoints[] = {(int)triangle.getA().y, (int)triangle.getB().y, (int)triangle.getC().y};
                    int npoints = 3;

                    Polygon p = new Polygon(xpoints, ypoints, npoints);  // This polygon represents a triangle with the above vertices.
                    g2d.drawPolygon(p);
//                    g2d.drawOval(-10,((int)p.getBounds().getMinY()-10), (int)(20*sharedInfo.getScale()), (int)(20*sharedInfo.getScale()));
                    g2d.drawOval((int)triangle.getA().x,(int)triangle.getA().y, (int)(20*sharedInfo.getScale()), (int)(20*sharedInfo.getScale()));

//                    triangle.setCircleHeight((int)p.getBounds().getMinY());
                }
            }
    
public Graphics2D objectToWorldToViewTransform(Graphics2D g2d, Shape s){
//        AffineTransform viewToWorldTranslate = new AffineTransform(1,0,0,1,sharedInfo.getViewH(),sharedInfo.getViewV());
//        AffineTransform viewToWorldScale = new AffineTransform(sharedInfo.scale, 0, 0, sharedInfo.scale, 0, 0);
//        
//        Point2D.Double worldPoint = new Point2D.Double();
//        viewToWorldTranslate.concatenate(viewToWorldScale);
//        viewToWorldTranslate.transform(viewPoint, worldPoint);
//        
//        return worldPoint;
    

// object to world                    
//                    AffineTransform objToWorld = new AffineTransform();
                    
                    //objToWorld.translate(s.getCenter().x,s.getCenter().y);
                    //objToWorld.rotate(s.getRotation());    

                    AffineTransform objToWorldTranslate = new AffineTransform(1,0,0,1,s.getCenter().x,s.getCenter().y);
                    AffineTransform objToWorldRotate = new AffineTransform(Math.cos(s.getRotation()), Math.sin(s.getRotation()), -Math.sin(s.getRotation()), Math.cos(s.getRotation()), 0, 0);

// World to View               
                    AffineTransform worldToViewScale = new AffineTransform(1/sharedInfo.getScale(),0,0,1/sharedInfo.getScale(),0,0);
                    AffineTransform worldToViewTranslate = new AffineTransform(1,0,0,1,-sharedInfo.getViewH(),-sharedInfo.getViewV());

// 
                    AffineTransform objToWorldToView = new AffineTransform();

                    objToWorldToView.concatenate(worldToViewScale);
                    objToWorldToView.concatenate(worldToViewTranslate);
                    objToWorldToView.concatenate(objToWorldTranslate);
                    objToWorldToView.concatenate(objToWorldRotate);

                    

                    
                    g2d.setTransform(objToWorldToView);
                    return g2d;
}

public Graphics2D houseToWorldToViewTransform(Graphics2D g2d){

// World to View               
                    AffineTransform worldToViewScale = new AffineTransform(1/sharedInfo.getScale(),0,0,1/sharedInfo.getScale(),0,0);
                    AffineTransform worldToViewTranslate = new AffineTransform(1,0,0,1,-sharedInfo.getViewH(),-sharedInfo.getViewV());
//                    AffineTransform worldToViewScale = new AffineTransform(1,0,0,1,0,0);
//                    AffineTransform worldToViewTranslate = new AffineTransform(1,0,0,1,0,0);
// 
                    AffineTransform WorldToView = new AffineTransform();
//                    WorldToView.concatenate(objToWorldTranslate);

                    WorldToView.concatenate(worldToViewScale);
                    WorldToView.concatenate(worldToViewTranslate);
//                    objToWorldToView.concatenate(objToWorldRotate);

                    

                    
                    g2d.setTransform(WorldToView);
                    return g2d;
}

	public ArrayList<Point2D.Double> getTwoDimensionalLine(HomogeneousPoint start, HomogeneousPoint end){
        
			double cosValue = (double)Math.cos(Math.toRadians(model.getDegree()));
			double sinValue = (double)Math.sin(Math.toRadians(model.getDegree()));
			Matrix rotationMatrix = new Matrix(cosValue,0,sinValue,0,0,1,0,0,-sinValue,0,cosValue,0,0,0,0,1);
			Matrix translationMatrix = new Matrix(1,0,0,-model.getxDisp(),0,1,0,-model.getyDisp(),0,0,1,-model.getzDisp(),0,0,0,1);
			Matrix newMatrix = rotationMatrix.multiply(translationMatrix);
			
//			HomogeneousPoint initialPointX = new HomogeneousPoint(start.x,y,z,1);
			HomogeneousPoint newStartPoint = newMatrix.multiplyPoint(start);
            HomogeneousPoint newEndPoint = newMatrix.multiplyPoint(end);
			
			double zoomy = 1/(double)Math.tan(Math.toRadians(50/2));
			double height = 512;
			double width = 512;
			double near = 1;
			double far = 100;
			double nearClip = (far+near)/(far-near);
			double farClip = (-2*near*far)/(far-near);
			double zoomx = (width*zoomy)/height;
			Matrix clipMatrix = new Matrix(zoomx,0,0,0,0,zoomy,0,0,0,0,nearClip,farClip,0,0,1,0);
			
//			HomogeneousPoint initialClipPoint = new HomogeneousPoint(5,-5,50,1);
			HomogeneousPoint newClipStartPoint = clipMatrix.multiplyPoint(newStartPoint);
            HomogeneousPoint newClipEndPoint = clipMatrix.multiplyPoint(newEndPoint);
			HomogeneousPoint finalStartPoint = null;
            HomogeneousPoint finalEndPoint = null;
            
            ArrayList<Point2D.Double> points = null;
            
            
            
			if(newClipStartPoint.isInNearClipView()&&newClipEndPoint.isInNearClipView()&&isInViewFrustum(newClipStartPoint,newClipEndPoint)){
				double viewWidth=2048;
				double viewHeight=2048;
				Matrix viewPortTransformation = new Matrix(viewWidth/2,0,viewWidth/2,0,0,-viewHeight/2,viewHeight/2,0,0,0,1,0,0,0,0,0);
				
				HomogeneousPoint viewStartPoint = new HomogeneousPoint(newClipStartPoint.getX()/newClipStartPoint.getW(),newClipStartPoint.getY()/newClipStartPoint.getW(),1,0);
				HomogeneousPoint viewEndPoint = new HomogeneousPoint(newClipEndPoint.getX()/newClipEndPoint.getW(),newClipEndPoint.getY()/newClipEndPoint.getW(),1,0);
                
                finalStartPoint = viewPortTransformation.multiplyPoint(viewStartPoint);
                finalEndPoint = viewPortTransformation.multiplyPoint(viewEndPoint);
                
                Point2D.Double startPoint = new Point2D.Double(finalStartPoint.getX(),finalStartPoint.getY());
                Point2D.Double endPoint = new Point2D.Double(finalEndPoint.getX(),finalEndPoint.getY());
                
                points = new ArrayList<Point2D.Double>();
                points.add(startPoint);
                points.add(endPoint);
			}
            if(points==null){
                return null;
            }
			return points;
		}
    
        public boolean isInViewFrustum(HomogeneousPoint newClipStartPoint,HomogeneousPoint newClipEndPoint){
           if(isLeftXClipped(newClipStartPoint,newClipEndPoint)||isRightXClipped(newClipStartPoint,newClipEndPoint)||isTopClipped(newClipStartPoint,newClipEndPoint)||isBotClipped(newClipStartPoint,newClipEndPoint)||isFarClipped(newClipStartPoint,newClipEndPoint)){
            return false;
            }
           return true;
            
        }
        
        public boolean isFarClipped(HomogeneousPoint newClipStartPoint,HomogeneousPoint newClipEndPoint){
            if(newClipStartPoint.isOutFarClipView()&&newClipEndPoint.isOutFarClipView()){
               return true; 
            }
            return false;
        }
    
        public boolean isLeftXClipped(HomogeneousPoint newClipStartPoint,HomogeneousPoint newClipEndPoint){
            if(newClipStartPoint.isOutLeftXClipView()&&newClipEndPoint.isOutLeftXClipView()){
                return true;
            }
            return false;
        }
        
        public boolean isRightXClipped(HomogeneousPoint newClipStartPoint,HomogeneousPoint newClipEndPoint){
            if(newClipStartPoint.isOutRightXClipView()&&newClipEndPoint.isOutRightXClipView()){
                return true;
            }
            return false;
        }
        
        public boolean isTopClipped(HomogeneousPoint newClipStartPoint,HomogeneousPoint newClipEndPoint){
            if(newClipStartPoint.isOutTopYClipView()&&newClipEndPoint.isOutTopYClipView()){
                return true;
            }
            return false; 
        }
        
        public boolean isBotClipped(HomogeneousPoint newClipStartPoint,HomogeneousPoint newClipEndPoint){
            if(newClipStartPoint.isOutBotYClipView()&&newClipEndPoint.isOutBotYClipView()){
                return true;
            }
            return false; 
        }
        
}
