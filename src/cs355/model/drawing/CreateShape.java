/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs355.model.drawing;

import cs355.model.Model;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

/**
 *
 */
public class CreateShape {
    Color color;
    String drawState;
    Point2D.Double initialPressed = new Point2D.Double();
    Point2D.Double initialCenter = new Point2D.Double();
    double initialRotation;
    Model model;
    int currentShapeIndex;
    int clickCount=0;


    
    Point2D.Double trianglePointA = new Point2D.Double();
    Point2D.Double trianglePointB = new Point2D.Double();
    
    public CreateShape(Model model){
        this.model=model;
    }
    
    public String getDrawState() {
        return drawState;
    }

    public void setDrawState(String drawState) {
        this.drawState = drawState;
    }
    
    public void createShapeMousePressed(MouseEvent e){
        Point2D.Double start = new Point2D.Double(e.getX(),e.getY());
        initialPressed = start;

        if(drawState.equals("line")){
            Color color = model.getColor();
            Point2D.Double end = new Point2D.Double(e.getX(),e.getY());
            Line newLine = new Line(color,start,end);
            currentShapeIndex = model.addShape(newLine);
        }
        if(drawState.equals("rectangle")){
            Color color = model.getColor();
            Rectangle newRectangle = new Rectangle(color,getCenterOfRectangle((Point2D.Double)start,(double)e.getX(),(double)e.getY())
                    ,1,1);
            currentShapeIndex = model.addShape(newRectangle);
        }
        if(drawState.equals("circle")){
            Color color = model.getColor();
            Circle newCircle = new Circle(color,(Point2D.Double)start,1);
            currentShapeIndex = model.addShape(newCircle);
        }
        if(drawState.equals("square")){
            Color color = model.getColor();
            Square newSquare = new Square(color,(Point2D.Double)start,1);
            currentShapeIndex = model.addShape(newSquare);
        }
        if(drawState.equals("ellipse")){
            Color color = model.getColor();
            Ellipse newEllipse = new Ellipse(color,(Point2D.Double)start,1,1);
            currentShapeIndex = model.addShape(newEllipse); 
        }
        if(drawState.equals("triangle")){

                if(clickCount==0){
                    trianglePointA = new Point2D.Double(initialPressed.x,initialPressed.y);
                }
                if(clickCount==1){
                    trianglePointB = new Point2D.Double(initialPressed.x,initialPressed.y);
                }
                if(clickCount==2){
                Point2D.Double trianglePointC = new Point2D.Double(initialPressed.x,initialPressed.y);
                Triangle triangle = new Triangle(model.getColor(),getCenterOfTriangle(trianglePointA,trianglePointB,trianglePointC),
                trianglePointA,trianglePointB,trianglePointC);
                
                model.addShape(triangle);
                model.changed();
                model.notifyObservers();
            }
        }      
    }
    
    public void drawShapeMouseReleased(){
        
                if(drawState.equals("triangle")){
                    if(clickCount==2){
                        clickCount = -1;
//                        drawState = "released in click count = 3";
                    }
                     clickCount++;
                }
        
    }
    
    public void drawShapeMouseDragged(MouseEvent e){
        
        if(drawState.equals("moveLinePoint")){
            Line currentLine = (Line)model.getSelectedShape();
            Point2D.Double end = new Point2D.Double(e.getX(),e.getY());
            
            currentLine.setCenter(currentLine.getStayPoint());
            
            currentLine.setEnd(end);
            model.changed();
            model.notifyObservers();
        }
        if(drawState.equals("line")){
           Line currentLine = (Line)model.getShapes().get(currentShapeIndex-1);
           Point2D.Double end = new Point2D.Double(e.getX(),e.getY());
           currentLine.setEnd(end);
           model.changed();
           model.notifyObservers();
        }
        if(drawState.equals("rectangle")){
            Rectangle currentRectangle = (Rectangle)model.getShapes().get(currentShapeIndex-1);
            double newCenterX;
            double newCenterY;
            
            if((e.getX()>initialPressed.x)){
                newCenterX = initialPressed.x+(e.getX()-initialPressed.x)/2;
                currentRectangle.setWidth(e.getX()-initialPressed.x);
            }else{
                newCenterX = initialPressed.x-(initialPressed.x-e.getX())/2;
                currentRectangle.setWidth(initialPressed.x-e.getX());
            }
            if(e.getY()>initialPressed.y){
               newCenterY = initialPressed.y+(e.getY()-initialPressed.y)/2;
               currentRectangle.setHeight(e.getY()-initialPressed.y); 
            }else{
                newCenterY = initialPressed.y-(initialPressed.y-e.getY())/2;
                currentRectangle.setHeight(initialPressed.y-e.getY());  
            }
        
            Point2D.Double newCenter = new Point2D.Double(newCenterX,newCenterY);
            currentRectangle.setCenter(newCenter); 
            
            model.changed();
            model.notifyObservers();
        }
        
        if(drawState.equals("ellipse")){
                       
            Ellipse currentEllipse = (Ellipse)model.getShapes().get(currentShapeIndex-1);
            double newCenterX;
            double newCenterY;
            
            if((e.getX()>initialPressed.x)){
                newCenterX = initialPressed.x+(e.getX()-initialPressed.x)/2;
                currentEllipse.setWidth(e.getX()-initialPressed.x);
            }else{
                newCenterX = initialPressed.x-(initialPressed.x-e.getX())/2;
                currentEllipse.setWidth(initialPressed.x-e.getX());
            }
            if(e.getY()>initialPressed.y){
               newCenterY = initialPressed.y+(e.getY()-initialPressed.y)/2;
               currentEllipse.setHeight(e.getY()-initialPressed.y); 
            }else{
                newCenterY = initialPressed.y-(initialPressed.y-e.getY())/2;
                currentEllipse.setHeight(initialPressed.y-e.getY());  
            }
        
            Point2D.Double newCenter = new Point2D.Double(newCenterX,newCenterY);
            currentEllipse.setCenter(newCenter); 
            
            model.changed();
            model.notifyObservers();
        }
        
        if(drawState.equals("square")){
            Square currentSquare = (Square)model.getShapes().get(currentShapeIndex-1);
            double newCenterX;
            double newCenterY;
            double newSizeX;
            double newSizeY;
            if((e.getX()>initialPressed.x)){
                // left of y axis
                newSizeX=(e.getX()-initialPressed.x);
            }else{
                // right of y acis
                newSizeX = (initialPressed.x-e.getX());
            }
            if((e.getY()>initialPressed.y)){
               //above y axis
               newSizeY = (e.getY()-initialPressed.y); 
            }else{
                //below y acis
                newSizeY = (initialPressed.y-e.getY());  
            }
            
            if((newSizeY)<(newSizeX)){
              currentSquare.setSize(newSizeY); 
            }else{
              currentSquare.setSize(newSizeX);
            }
            
            if((e.getX()-initialPressed.x)>0){
                // left of y axis
                newCenterX = initialPressed.x+currentSquare.getSize()/2;
            }else{
                // right of y axis
                newCenterX = initialPressed.x - currentSquare.getSize()/2;
            }
            if((e.getY()-initialPressed.y)>0){
                //above x axis
               newCenterY = initialPressed.y+currentSquare.getSize()/2;
            }else{
               //below x axis
               newCenterY = initialPressed.y-currentSquare.getSize()/2;
            }
            
            Point2D.Double center = new Point2D.Double(newCenterX,newCenterY);
            currentSquare.setCenter(center);
            model.changed();
            model.notifyObservers(); 
        }
        if(drawState.equals("circle")){
            Circle currentCircle = (Circle)model.getShapes().get(currentShapeIndex-1);
            double newCenterX;
            double newCenterY;
            double newSizeX;
            double newSizeY;
            
            if((e.getX()-initialPressed.x)>0){
                newSizeX=(e.getX()-initialPressed.x);
            }else{
                newSizeX = (initialPressed.x-e.getX());
            }
            if((e.getY()-initialPressed.y)>0){
               newSizeY = (e.getY()-initialPressed.y); 
            }else{
                newSizeY = (initialPressed.y-e.getY());  
            }
            if((newSizeY)<(newSizeX)){
              currentCircle.setRadius(newSizeY); 
            }else{
              currentCircle.setRadius(newSizeX);
            }
            
            if((e.getX()-initialPressed.x)>0){
                newCenterX = initialPressed.x+currentCircle.getRadius()/2;
            }else{
                newCenterX = initialPressed.x - currentCircle.getRadius()/2;
            }
            if((e.getY()-initialPressed.y)>0){
               newCenterY = initialPressed.y + currentCircle.getRadius()/2;
            }else{
                newCenterY = initialPressed.y - currentCircle.getRadius()/2;
            }
            Point2D.Double newCenter = new Point2D.Double(newCenterX,newCenterY);
            currentCircle.setCenter(newCenter); 
            model.changed();
            model.notifyObservers(); 
        }
    }
    
//     public void moveShapeMouseDragged(MouseEvent e){
//        if(model.getSelectedShapeIndex()!=-1){     
//        Shape currentShape = model.getSelectedShape();
//         
//         if(currentShape.getClass().equals(Line.class)){ 
//           Line currentLine = (Line)model.getSelectedShape();
//           Point2D.Double newCenter = new Point2D.Double((e.getX()-(initialPressed.x-initialCenter.x)),e.getY()-(initialPressed.y-initialCenter.y));
//           
//           currentLine.setCenter(newCenter);
//          
//           model.changed();
//           model.notifyObservers();
//        }
//        if(currentShape.getClass().equals(Rectangle.class)){ 
//            
//            Rectangle currentRectangle = (Rectangle)model.getSelectedShape();
//            double newCenterX;
//            double newCenterY;
//            
//            if((e.getX()>initialPressed.x)){
//                newCenterX = initialCenter.x+(e.getX()-initialPressed.x);
//            }else{
//                newCenterX = initialCenter.x-(initialPressed.x-e.getX());
//            }
//            if(e.getY()>initialPressed.y){
//               newCenterY = initialCenter.y+(e.getY()-initialPressed.y);
//            }else{
//                newCenterY = initialCenter.y-(initialPressed.y-e.getY());
//            }
//
//            Point2D.Double newCenter = new Point2D.Double(newCenterX,newCenterY);
//            currentRectangle.setCenter(newCenter); 
//            
//            model.changed();
//            model.notifyObservers();
//        }
//        
//       if(currentShape.getClass().equals(Ellipse.class)){ 
//                       
//            Ellipse currentEllipse = (Ellipse)model.getSelectedShape();
//            double newCenterX;
//            double newCenterY;
//        
//            if((e.getX()>initialPressed.x)){
//                newCenterX = initialCenter.x+(e.getX()-initialPressed.x);
//            }else{
//                newCenterX = initialCenter.x-(initialPressed.x-e.getX());
//            }
//            if(e.getY()>initialPressed.y){
//               newCenterY = initialCenter.y+(e.getY()-initialPressed.y);
//            }else{
//                newCenterY = initialCenter.y-(initialPressed.y-e.getY());
//            }
//        
//            Point2D.Double newCenter = new Point2D.Double(newCenterX,newCenterY);
//            currentEllipse.setCenter(newCenter); 
//            
//            model.changed();
//            model.notifyObservers();
//        }
//        
//        if(currentShape.getClass().equals(Square.class)){ 
//            Square currentSquare = (Square)model.getSelectedShape();
//            double newCenterX;
//            double newCenterY;
//            
//            if((e.getX()>initialPressed.x)){
//                // left of y axis
//                newCenterX = initialCenter.x+e.getX()-initialPressed.x;
//            }else{
//                // right of y axis
//                newCenterX = initialCenter.x-(initialPressed.x - e.getX());
//            }
//            
//            if((e.getY()>initialPressed.y)){
//                //above x axis
//               newCenterY = initialCenter.y+e.getY()-initialPressed.y;
//            }else{
//                //below x axis
//                newCenterY = initialCenter.y-(initialPressed.y-e.getY());
//
//            }
//            
//            Point2D.Double center = new Point2D.Double(newCenterX,newCenterY);
//            currentSquare.setCenter(center);
//            model.changed();
//            model.notifyObservers(); 
//        }
//        if(currentShape.getClass().equals(Circle.class)){
//            Circle currentCircle = (Circle)model.getSelectedShape();
//            double newCenterX;
//            double newCenterY;
//            
//            if((e.getX()>initialPressed.x)){
//                // left of y axis
//                newCenterX = initialCenter.x+e.getX()-initialPressed.x;
//            }else{
//                // right of y axis
//                newCenterX = initialCenter.x-(initialPressed.x - e.getX());
//            }
//            
//            if((e.getY()>initialPressed.y)){
//                //above x axis
//               newCenterY = initialCenter.y+e.getY()-initialPressed.y;
//            }else{
//                //below x axis
//                newCenterY = initialCenter.y-(initialPressed.y-e.getY());
//
//            }
//            Point2D.Double upperLeft = new Point2D.Double(newCenterX,newCenterY);
//            currentCircle.setCenter(upperLeft); 
//            model.changed();
//            model.notifyObservers(); 
//        }
//        if(currentShape.getClass().equals(Triangle.class)){
//            Triangle currentTriangle = (Triangle)model.getSelectedShape();
//            double newCenterX;
//            double newCenterY;
//            
//            if((e.getX()>initialPressed.x)){
//                newCenterX = initialPressed.x+(e.getX()-initialPressed.x);
//            }else{
//                newCenterX = initialPressed.x-(initialPressed.x-e.getX());
//            }
//            if(e.getY()>initialPressed.y){
//               newCenterY = initialPressed.y+(e.getY()-initialPressed.y);
//            }else{
//                newCenterY = initialPressed.y-(initialPressed.y-e.getY());
//            }
//        
//            Point2D.Double newCenter = new Point2D.Double(newCenterX,newCenterY);
//            currentTriangle.setCenter(newCenter); 
//            
//            model.changed();
//            model.notifyObservers();
//        }
//        }
//    }
     
    public void rotateShape(MouseEvent e){
        
        Shape currentShape = model.getSelectedShape();
        if(currentShape.getClass().equals(Line.class)){
           Line currentLine = (Line)currentShape;
           Point2D.Double end = new Point2D.Double(e.getX(),e.getY());
           currentLine.setCenter(currentLine.getStayPoint());
           currentLine.setEnd(end);
           model.changed();
           model.notifyObservers();
        }else{
        
            double angle1 = Math.atan2(initialPressed.y - currentShape.center.y, initialPressed.y - currentShape.center.x);
            double angle2 = Math.atan2(e.getY() - currentShape.center.y, e.getX() - currentShape.center.x);

            double finalRotation = angle2 - angle1; 
            finalRotation -= initialRotation;
            currentShape.setRotation(finalRotation);
            
            model.changed();
            model.notifyObservers(); 
        }
    }
    
    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }  
    
    public Point2D.Double getCenterOfTriangle(Point2D.Double a, Point2D.Double b,Point2D.Double c){
        Double x = (a.x+b.x+c.x)/3;
        Double y = (a.y+b.y+c.y)/3;
        return new Point2D.Double(x,y);
    }
    
    public Point2D.Double getCenterOfRectangle(Point2D.Double start,double x, double y){
        return new Point2D.Double((start.x+x)/2,(start.y+y)/2);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Point2D.Double getInitialCenter() {
        return initialCenter;
    }

    public void setInitialCenter(Point2D.Double initialCenter) {
        this.initialCenter = initialCenter;
    }
    
    public void setInitialPressed(MouseEvent e){
        this.initialPressed = new Point2D.Double(e.getX(),e.getY());
    }

    public double getInitialRotation() {
        return initialRotation;
    }

    public void setInitialRotation(double initialRotation) {
        this.initialRotation = initialRotation;
    }
    
    
    
    
    
}
