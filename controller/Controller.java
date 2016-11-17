/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs355.controller;

import cs355.GUIFunctions;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import cs355.model.Model;
import cs355.model.drawing.Circle;
import cs355.model.drawing.CreateShape;
import cs355.model.drawing.Ellipse;
import cs355.model.drawing.Line;
import cs355.model.drawing.Rectangle;
import cs355.model.drawing.Shape;
import cs355.model.drawing.Square;
import cs355.model.drawing.Triangle;
import cs355.model.image.CS355Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.List;

/**
 *
 * @author Garrett Hartley
 */
public class Controller implements CS355Controller {

    private Model model;
    private String state = "initial";
    private CreateShape createShape;
    private Point2D.Double initialPressed = new Point2D.Double();
    private Point2D.Double initialCenter = new Point2D.Double();
    private double initialRotation;
    String drawState;
    int currentShapeIndex;
    int clickCount = 0;
    SharedViewInfo sharedInfo;
    Point2D.Double trianglePointA = new Point2D.Double();
    Point2D.Double trianglePointB = new Point2D.Double();
    
    float step = 1;
	float degree = 0;
	float xDisp = 0;
	float yDisp = -2;
	float zDisp = -20;

    public Controller(Model model_input, SharedViewInfo sharedInfo) {
        model = model_input;
        createShape = new CreateShape(this.model);
        this.sharedInfo = sharedInfo;
    }

    @Override
    public void colorButtonHit(Color c) {

        model.setColor(c);
        GUIFunctions.changeSelectedColor(c);
        model.changed();
        model.notifyObservers();

    }

    @Override
    public void lineButtonHit() {
        state = "draw";
        this.drawState = "line";
        createShape.setClickCount(0);
    }

    @Override
    public void squareButtonHit() {
        state = "draw";
        this.drawState = "square";
        createShape.setClickCount(0);
    }

    @Override
    public void rectangleButtonHit() {
        state = "draw";
        this.drawState = "rectangle";
        createShape.setClickCount(0);
    }

    @Override
    public void circleButtonHit() {
        state = "draw";
        this.drawState = "circle";
        createShape.setClickCount(0);
    }

    @Override
    public void ellipseButtonHit() {
        state = "draw";
        this.drawState = "ellipse";
        createShape.setClickCount(0);
    }

    @Override
    public void triangleButtonHit() {
        state = "draw";
        this.drawState = "triangle";
        createShape.setClickCount(0);
    }

    @Override
    public void selectButtonHit() {
        state = "select";
    }

    @Override
    public void zoomInButtonHit() {
        if (sharedInfo.getScale() > .25) {
            int newVertKnobLocation = (int) sharedInfo.getViewV() + (int) (sharedInfo.getKnobSize() / 4);
            int newHorizKnobLocation = (int) sharedInfo.getViewH() + (int) (sharedInfo.getKnobSize() / 4);

            GUIFunctions.setHScrollBarKnob(sharedInfo.getKnobSize() / 2);
            GUIFunctions.setVScrollBarKnob(sharedInfo.getKnobSize() / 2);

            if (sharedInfo.getScale() == 4) {
                System.out.println("got into funky scale time");
                GUIFunctions.setHScrollBarPosit(512);
                GUIFunctions.setVScrollBarPosit(512);
            } else {
                GUIFunctions.setHScrollBarPosit(newHorizKnobLocation);
                GUIFunctions.setVScrollBarPosit(newVertKnobLocation);
            }

            sharedInfo.setKnobSize(sharedInfo.getKnobSize() / 2);
            sharedInfo.setScale(sharedInfo.getScale() / 2);
            model.changed();
            model.notifyObservers();
        }
    }

    @Override
    public void zoomOutButtonHit() {
        if (sharedInfo.getScale() < 4) {
            int newVertKnobLocation = (int) sharedInfo.getViewV() - (int) (sharedInfo.getKnobSize() / 2);
            int newHorizKnobLocation = (int) sharedInfo.getViewH() - (int) (sharedInfo.getKnobSize() / 2);

            GUIFunctions.setVScrollBarPosit(0);
            GUIFunctions.setHScrollBarPosit(0);

            GUIFunctions.setVScrollBarKnob(sharedInfo.getKnobSize() * 2);
            GUIFunctions.setHScrollBarKnob(sharedInfo.getKnobSize() * 2);
            GUIFunctions.setHScrollBarPosit(newHorizKnobLocation);
            GUIFunctions.setVScrollBarPosit(newVertKnobLocation);

            sharedInfo.setScale(sharedInfo.getScale() * 2);
            sharedInfo.setKnobSize(sharedInfo.getKnobSize() * 2);

            model.changed();
            model.notifyObservers();
        }
    }

    @Override
    public void hScrollbarChanged(int value) {
        sharedInfo.setViewH(value);
        model.changed();
        model.notifyObservers();
    }

    @Override
    public void vScrollbarChanged(int value) {
        sharedInfo.setViewV(value);
        model.changed();
        model.notifyObservers();
    }

    @Override
    public void openScene(File file) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void toggle3DModelDisplay() {
        model.toggleThreeDimensionalView();
        model.changed();
        model.notifyObservers();
    }

    @Override
    public void keyPressed(Iterator<Integer> iterator) {

        
        while(iterator.hasNext()){
            int next = iterator.next();
            System.out.println("This key pressed: "+next);
            // Move left
		if (next==68) {
			zDisp+= (float) (1)*Math.sin(Math.toRadians(degree));
			xDisp+=(float) (1)*Math.cos(Math.toRadians(degree));
                        model.setzDisp(zDisp);
            model.setxDisp(xDisp);
			System.out.println("Move left");
		}
		// Move right
		if (next==65) {
			zDisp+= (float) (1)*-Math.sin(Math.toRadians(degree));
			xDisp+=(float) (1)*-Math.cos(Math.toRadians(degree));
            model.setzDisp(zDisp);
            model.setxDisp(xDisp);
			System.out.println("Move right");
		}
		// Move forward
		if (next==87) {
			zDisp+= (float) (1)*Math.cos(Math.toRadians(degree));
			xDisp+=(float) (1)*-Math.sin(Math.toRadians(degree));
                        model.setzDisp(zDisp);
            model.setxDisp(xDisp);
			System.out.println("Move forward");

		}
		// Move backward
		if (next==83) {
			zDisp+= (float) (1)*-Math.cos(Math.toRadians(degree));
			xDisp+=(float) (1)*Math.sin(Math.toRadians(degree));
                        model.setzDisp(zDisp);
            model.setxDisp(xDisp);
			System.out.println("turn backward");

		}
		// Turn left
		if (next==69) {
			degree-=3;
            model.setDegree(degree);
			System.out.println("turn left");
		}
		// Turn right
		if (next==81) {
			degree+=3;
            model.setDegree(degree);
		}
		// Move up
		if (next==70) {
			yDisp--;
            model.setyDisp(yDisp);
            			System.out.println("go up");

		}
		// Move down
		if (next==82) {
			yDisp++;
            model.setyDisp(yDisp);
            			System.out.println("go down");

		}
		// Return to the original 'home' position and orientation
		if (next==72) {
			xDisp = 0;
			yDisp = -2;
			zDisp = -20;
			degree = 0;
            model.setxDisp(xDisp);
            model.setyDisp(yDisp);
            model.setzDisp(zDisp);
            model.setDegree(degree);
            System.out.println("home");
		}
        model.changed();
        model.notifyObservers();
        }
        
        
        
    }

    @Override
    public void openImage(File file) {
        model.getImage().open(file);
        model.changed();
        model.notifyObservers();        
    }

    @Override
    public void saveImage(File file) {
        model.save(file);
        model.changed();
        model.notifyObservers();        
    }

    @Override
    public void toggleBackgroundDisplay() {
        if(sharedInfo.isDrawImage()){
            sharedInfo.setDrawImage(false);
            model.changed();
            model.notifyObservers();
        }else{
            sharedInfo.setDrawImage(true);
            model.changed();
            model.notifyObservers();
        }
    }

    @Override
    public void saveDrawing(File file) {
        model.save(file);
        model.changed();
        model.notifyObservers();
    }

    @Override
    public void openDrawing(File file) {
        model.open(file);
        model.changed();
        model.notifyObservers();
    }

    @Override
    public void doDeleteShape() {
        if(model.getSelectedShapeIndex()>-1){
            model.deleteShape(model.getSelectedShapeIndex());
            model.changed();
            model.notifyObservers();
        }
    }

    @Override
    public void doEdgeDetection() {
        model.getImage().edgeDetection();
        model.changed();
        model.notifyObservers();        
    }

    @Override
    public void doSharpen() {
        model.getImage().sharpen();
        model.changed();
        model.notifyObservers();        
    }

    @Override
    public void doMedianBlur() {
        model.getImage().medianBlur();
        model.changed();
        model.notifyObservers();        
    }

    @Override
    public void doUniformBlur() {
        model.getImage().uniformBlur();
        model.changed();
        model.notifyObservers();        
    }

    @Override
    public void doGrayscale() {
        model.getImage().grayscale();
        model.changed();
        model.notifyObservers();        
    }

    @Override
    public void doChangeContrast(int contrastAmountNum) {
        model.getImage().contrast(contrastAmountNum);
        model.changed();
        model.notifyObservers();        
    }

    @Override
    public void doChangeBrightness(int brightnessAmountNum) {
        model.getImage().brightness(brightnessAmountNum);
        model.changed();
        model.notifyObservers();
    
    }

    @Override
    public void doMoveForward() {
        model.moveForward(model.getSelectedShapeIndex());
        model.changed();
        model.notifyObservers();

    }

    @Override
    public void doMoveBackward() {
        model.moveBackward(model.getSelectedShapeIndex());
        model.changed();
        model.notifyObservers();
    }

    @Override
    public void doSendToFront() {
        model.moveToFront(model.getSelectedShapeIndex());
        model.changed();
        model.notifyObservers();
    }

    @Override
    public void doSendtoBack() {
        model.movetoBack(model.getSelectedShapeIndex());
        model.changed();
        model.notifyObservers();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        AffineTransform viewToWorldTranslate = new AffineTransform(1, 0, 0, 1, sharedInfo.getViewH(), sharedInfo.getViewV());
        AffineTransform viewToWorldScale = new AffineTransform(sharedInfo.scale, 0, 0, sharedInfo.scale, 0, 0);

        Point2D.Double viewPoint = new Point2D.Double(e.getX(), e.getY());
        Point2D.Double worldPoint = new Point2D.Double();
        viewToWorldTranslate.concatenate(viewToWorldScale);
        viewToWorldTranslate.transform(viewPoint, worldPoint);

        this.initialPressed = new Point2D.Double(worldPoint.x, worldPoint.y);

        if (state.equals("draw")) {
            createShapeMousePressed(e);
        }
        if (state.equals("select")) {

            List<Shape> shapes = model.getShapes();
            for (int i = shapes.size() - 1; i >= 0; i--) {
                if (model.getSelectedShapeIndex() != -1) {
                    Point2D.Double objectPoint = worldToObjectTransform(worldPoint, model.getSelectedShape());
                    if (model.getSelectedShape().pointInHandle(objectPoint, sharedInfo.getScale())) {
                        if (model.getSelectedShape().getClass().equals(Line.class)) {
                            state = "moveLinePoint";
                            this.drawState = "moveLinePoint";
                            break;
                        }
                        state = "rotate";
                        break;
                    }
                }
                Point2D.Double objectPoint = worldToObjectTransform(worldPoint, shapes.get(i));
                if (shapes.get(i).pointInShape(objectPoint, 10, sharedInfo.getScale())) {
                    model.setSelectedShape(i);
                    Color c = shapes.get(i).getColor();
                    model.setColor(c);
                    GUIFunctions.changeSelectedColor(c);

                    model.changed();
                    model.notifyObservers();
                    break;
                } else {
                    model.setSelectedShape(-1);
                    model.changed();
                    model.notifyObservers();
                }
            }
            if (model.getSelectedShapeIndex() != -1) {
                this.initialRotation = model.getSelectedShape().getRotation();
                this.initialCenter = model.getSelectedShape().getCenter();
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (state.equals("draw")) {
            drawShapeMouseReleased();
        }
        if (state.equals("rotate")) {
            state = "select";
        }
        if (state.equals("moveLinePoint")) {
            state = "select";
        }
        model.changed();
        model.notifyObservers();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (state.equals("draw")) {
            drawShapeMouseDragged(e);
        }
        if (state.equals("select")) {
            moveShapeMouseDragged(e);
        }
        if (state.equals("rotate")) {
            rotateShape(e);
        }
        if (state.equals("moveLinePoint")) {
            drawShapeMouseDragged(e);
        }
        model.changed();
        model.notifyObservers();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        model.changed();
        model.notifyObservers();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    public void drawShapeMouseDragged(MouseEvent e) {

        Point2D.Double worldPoint = viewToWorldTransform(new Point2D.Double(e.getX(), e.getY()));

        if (drawState.equals("moveLinePoint")) {
            Line currentLine = (Line) model.getSelectedShape();

            Point2D.Double end = new Point2D.Double(worldPoint.x, worldPoint.y);
            //problem
            currentLine.setCenter(currentLine.getStayPoint());

            currentLine.setEnd(end);
            model.changed();
            model.notifyObservers();
        }
        if (drawState.equals("line")) {
            Line currentLine = (Line) model.getShapes().get(currentShapeIndex - 1);
            Point2D.Double end = new Point2D.Double(worldPoint.x, worldPoint.y);
            currentLine.setEnd(end);
            model.changed();
            model.notifyObservers();
        }
        if (drawState.equals("rectangle")) {
            // need to go from view to world
            Rectangle currentRectangle = (Rectangle) model.getShapes().get(currentShapeIndex - 1);
            double newCenterX;
            double newCenterY;

            if ((worldPoint.x > initialPressed.x)) {
                newCenterX = initialPressed.x + (worldPoint.x - initialPressed.x) / 2;
                currentRectangle.setWidth(worldPoint.x - initialPressed.x);
            } else {
                newCenterX = initialPressed.x - (initialPressed.x - worldPoint.x) / 2;
                currentRectangle.setWidth(initialPressed.x - worldPoint.x);
            }
            if (worldPoint.y > initialPressed.y) {
                newCenterY = initialPressed.y + (worldPoint.y - initialPressed.y) / 2;
                currentRectangle.setHeight(worldPoint.y - initialPressed.y);
            } else {
                newCenterY = initialPressed.y - (initialPressed.y - worldPoint.y) / 2;
                currentRectangle.setHeight(initialPressed.y - worldPoint.y);
            }

            Point2D.Double newCenter = new Point2D.Double(newCenterX, newCenterY);
            currentRectangle.setCenter(newCenter);

            model.changed();
            model.notifyObservers();
        }

        if (drawState.equals("ellipse")) {

            Ellipse currentEllipse = (Ellipse) model.getShapes().get(currentShapeIndex - 1);
            double newCenterX;
            double newCenterY;

            if ((worldPoint.x > initialPressed.x)) {
                newCenterX = initialPressed.x + (worldPoint.x - initialPressed.x) / 2;
                currentEllipse.setWidth(worldPoint.x - initialPressed.x);
            } else {
                newCenterX = initialPressed.x - (initialPressed.x - worldPoint.x) / 2;
                currentEllipse.setWidth(initialPressed.x - worldPoint.x);
            }
            if (worldPoint.y > initialPressed.y) {
                newCenterY = initialPressed.y + (worldPoint.y - initialPressed.y) / 2;
                currentEllipse.setHeight(worldPoint.y - initialPressed.y);
            } else {
                newCenterY = initialPressed.y - (initialPressed.y - worldPoint.y) / 2;
                currentEllipse.setHeight(initialPressed.y - worldPoint.y);
            }

            Point2D.Double newCenter = new Point2D.Double(newCenterX, newCenterY);
            currentEllipse.setCenter(newCenter);

            model.changed();
            model.notifyObservers();
        }

        if (drawState.equals("square")) {
            Square currentSquare = (Square) model.getShapes().get(currentShapeIndex - 1);
            double newCenterX;
            double newCenterY;
            double newSizeX;
            double newSizeY;
            if ((worldPoint.x > initialPressed.x)) {
                // left of y axis
                newSizeX = (worldPoint.x - initialPressed.x);
            } else {
                // right of y acis
                newSizeX = (initialPressed.x - worldPoint.x);
            }
            if ((worldPoint.y > initialPressed.y)) {
                //above y axis
                newSizeY = (worldPoint.y - initialPressed.y);
            } else {
                //below y acis
                newSizeY = (initialPressed.y - worldPoint.y);
            }

            if ((newSizeY) < (newSizeX)) {
                currentSquare.setSize(newSizeY);
            } else {
                currentSquare.setSize(newSizeX);
            }

            if ((worldPoint.x - initialPressed.x) > 0) {
                // left of y axis
                newCenterX = initialPressed.x + currentSquare.getSize() / 2;
            } else {
                // right of y axis
                newCenterX = initialPressed.x - currentSquare.getSize() / 2;
            }
            if ((worldPoint.y - initialPressed.y) > 0) {
                //above x axis
                newCenterY = initialPressed.y + currentSquare.getSize() / 2;
            } else {
                //below x axis
                newCenterY = initialPressed.y - currentSquare.getSize() / 2;
            }

            Point2D.Double center = new Point2D.Double(newCenterX, newCenterY);
            currentSquare.setCenter(center);
            model.changed();
            model.notifyObservers();
        }
        if (drawState.equals("circle")) {
            Circle currentCircle = (Circle) model.getShapes().get(currentShapeIndex - 1);
            double newCenterX;
            double newCenterY;
            double newSizeX;
            double newSizeY;

            if ((worldPoint.x - initialPressed.x) > 0) {
                newSizeX = (worldPoint.x - initialPressed.x);
            } else {
                newSizeX = (initialPressed.x - worldPoint.x);
            }
            if ((worldPoint.y - initialPressed.y) > 0) {
                newSizeY = (worldPoint.y - initialPressed.y);
            } else {
                newSizeY = (initialPressed.y - worldPoint.y);
            }
            if ((newSizeY) < (newSizeX)) {
                currentCircle.setRadius(newSizeY);
            } else {
                currentCircle.setRadius(newSizeX);
            }

            if ((worldPoint.x - initialPressed.x) > 0) {
                newCenterX = initialPressed.x + currentCircle.getRadius() / 2;
            } else {
                newCenterX = initialPressed.x - currentCircle.getRadius() / 2;
            }
            if ((worldPoint.y - initialPressed.y) > 0) {
                newCenterY = initialPressed.y + currentCircle.getRadius() / 2;
            } else {
                newCenterY = initialPressed.y - currentCircle.getRadius() / 2;
            }
            Point2D.Double newCenter = new Point2D.Double(newCenterX, newCenterY);
            currentCircle.setCenter(newCenter);
            model.changed();
            model.notifyObservers();
        }
    }

    public void createShapeMousePressed(MouseEvent e) {

        Point2D.Double start = initialPressed;
        if (drawState.equals("line")) {
            Color color = model.getColor();
            Point2D.Double end = new Point2D.Double(e.getX(), e.getY());
            Line newLine = new Line(color, start, end);
            currentShapeIndex = model.addShape(newLine);
        }
        if (drawState.equals("rectangle")) {
            Color color = model.getColor();
            Rectangle newRectangle = new Rectangle(color, getCenterOfRectangle((Point2D.Double) start, 1, 1), 1, 1);
            currentShapeIndex = model.addShape(newRectangle);
        }
        if (drawState.equals("circle")) {
            Color color = model.getColor();
            Circle newCircle = new Circle(color, (Point2D.Double) start, 1);
            currentShapeIndex = model.addShape(newCircle);
        }
        if (drawState.equals("square")) {
            Color color = model.getColor();
            Square newSquare = new Square(color, (Point2D.Double) start, 1);
            currentShapeIndex = model.addShape(newSquare);
        }
        if (drawState.equals("ellipse")) {
            Color color = model.getColor();
            Ellipse newEllipse = new Ellipse(color, (Point2D.Double) start, 1, 1);
            currentShapeIndex = model.addShape(newEllipse);
        }
        if (drawState.equals("triangle")) {

            if (clickCount == 0) {
                trianglePointA = new Point2D.Double(initialPressed.x, initialPressed.y);
            }
            if (clickCount == 1) {
                trianglePointB = new Point2D.Double(initialPressed.x, initialPressed.y);
            }
            if (clickCount == 2) {
                Point2D.Double trianglePointC = new Point2D.Double(initialPressed.x, initialPressed.y);
                Triangle triangle = new Triangle(model.getColor(), getCenterOfTriangle(trianglePointA, trianglePointB, trianglePointC),
                        trianglePointA, trianglePointB, trianglePointC);

                model.addShape(triangle);
                model.changed();
                model.notifyObservers();
            }
        }
    }

    public void moveShapeMouseDragged(MouseEvent e) {

        Point2D.Double worldPoint = viewToWorldTransform(new Point2D.Double(e.getX(), e.getY()));

        if (model.getSelectedShapeIndex() != -1) {
            Shape currentShape = model.getSelectedShape();

            if (currentShape.getClass().equals(Line.class)) {
                Line currentLine = (Line) model.getSelectedShape();
                Point2D.Double newCenter = new Point2D.Double((worldPoint.x - (initialPressed.x - initialCenter.x)), worldPoint.y - (initialPressed.y - initialCenter.y));

                currentLine.setCenter(newCenter);

                model.changed();
                model.notifyObservers();
            }
            if (currentShape.getClass().equals(Rectangle.class)) {

                Rectangle currentRectangle = (Rectangle) model.getSelectedShape();
                double newCenterX;
                double newCenterY;

                if ((worldPoint.x > initialPressed.x)) {
                    newCenterX = initialCenter.x + (worldPoint.x - initialPressed.x);
                } else {
                    newCenterX = initialCenter.x - (initialPressed.x - worldPoint.x);
                }
                if (worldPoint.y > initialPressed.y) {
                    newCenterY = initialCenter.y + (worldPoint.y - initialPressed.y);
                } else {
                    newCenterY = initialCenter.y - (initialPressed.y - worldPoint.y);
                }

                Point2D.Double newCenter = new Point2D.Double(newCenterX, newCenterY);
                currentRectangle.setCenter(newCenter);

                model.changed();
                model.notifyObservers();
            }

            if (currentShape.getClass().equals(Ellipse.class)) {

                Ellipse currentEllipse = (Ellipse) model.getSelectedShape();
                double newCenterX;
                double newCenterY;

                if ((worldPoint.x > initialPressed.x)) {
                    newCenterX = initialCenter.x + (worldPoint.x - initialPressed.x);
                } else {
                    newCenterX = initialCenter.x - (initialPressed.x - worldPoint.x);
                }
                if (worldPoint.y > initialPressed.y) {
                    newCenterY = initialCenter.y + (worldPoint.y - initialPressed.y);
                } else {
                    newCenterY = initialCenter.y - (initialPressed.y - worldPoint.y);
                }

                Point2D.Double newCenter = new Point2D.Double(newCenterX, newCenterY);
                currentEllipse.setCenter(newCenter);

                model.changed();
                model.notifyObservers();
            }

            if (currentShape.getClass().equals(Square.class)) {
                Square currentSquare = (Square) model.getSelectedShape();
                double newCenterX;
                double newCenterY;

                if ((worldPoint.x > initialPressed.x)) {
                    // left of y axis
                    newCenterX = initialCenter.x + worldPoint.x - initialPressed.x;
                } else {
                    // right of y axis
                    newCenterX = initialCenter.x - (initialPressed.x - worldPoint.x);
                }

                if ((worldPoint.y > initialPressed.y)) {
                    //above x axis
                    newCenterY = initialCenter.y + worldPoint.y - initialPressed.y;
                } else {
                    //below x axis
                    newCenterY = initialCenter.y - (initialPressed.y - worldPoint.y);
                }

                Point2D.Double center = new Point2D.Double(newCenterX, newCenterY);
                currentSquare.setCenter(center);
                model.changed();
                model.notifyObservers();
            }
            if (currentShape.getClass().equals(Circle.class)) {
                Circle currentCircle = (Circle) model.getSelectedShape();
                double newCenterX;
                double newCenterY;

                if ((worldPoint.x > initialPressed.x)) {
                    // left of y axis
                    newCenterX = initialCenter.x + worldPoint.x - initialPressed.x;
                } else {
                    // right of y axis
                    newCenterX = initialCenter.x - (initialPressed.x - worldPoint.x);
                }

                if ((worldPoint.y > initialPressed.y)) {
                    //above x axis
                    newCenterY = initialCenter.y + worldPoint.y - initialPressed.y;
                } else {
                    //below x axis
                    newCenterY = initialCenter.y - (initialPressed.y - worldPoint.y);

                }
                Point2D.Double upperLeft = new Point2D.Double(newCenterX, newCenterY);
                currentCircle.setCenter(upperLeft);
                model.changed();
                model.notifyObservers();
            }
            if (currentShape.getClass().equals(Triangle.class)) {
                Triangle currentTriangle = (Triangle) model.getSelectedShape();
                double newCenterX;
                double newCenterY;

                if ((worldPoint.x > initialPressed.x)) {
                    newCenterX = initialPressed.x + (worldPoint.x - initialPressed.x);
                } else {
                    newCenterX = initialPressed.x - (initialPressed.x - worldPoint.x);
                }
                if (worldPoint.y > initialPressed.y) {
                    newCenterY = initialPressed.y + (worldPoint.y - initialPressed.y);
                } else {
                    newCenterY = initialPressed.y - (initialPressed.y - worldPoint.y);
                }

                Point2D.Double newCenter = new Point2D.Double(newCenterX, newCenterY);
                currentTriangle.setCenter(newCenter);

                model.changed();
                model.notifyObservers();
            }
        }
    }

    public void drawShapeMouseReleased() {

        if (drawState.equals("triangle")) {
            if (clickCount == 2) {
                clickCount = -1;
//                        drawState = "released in click count = 3";
            }
            clickCount++;
        }

    }

    public void rotateShape(MouseEvent e) {

        Point2D.Double worldPoint = viewToWorldTransform(new Point2D.Double(e.getX(), e.getY()));

        Shape currentShape = model.getSelectedShape();
        if (currentShape.getClass().equals(Line.class)) {
            Line currentLine = (Line) currentShape;
            Point2D.Double end = new Point2D.Double(worldPoint.x, worldPoint.y);
            //problem
            currentLine.setCenter(currentLine.getStayPoint());
            currentLine.setEnd(end);
            model.changed();
            model.notifyObservers();
        } else {

            double angle1 = Math.atan2(initialPressed.y - currentShape.getCenter().y, initialPressed.y - currentShape.getCenter().x);
            double angle2 = Math.atan2(worldPoint.y - currentShape.getCenter().y, worldPoint.x - currentShape.getCenter().x);

            double finalRotation = angle2 - angle1;
            finalRotation -= initialRotation;
            currentShape.setRotation(finalRotation);

            model.changed();
            model.notifyObservers();
        }
    }

    public Point2D.Double viewToWorldTransform(Point2D.Double viewPoint) {
        AffineTransform viewToWorldTranslate = new AffineTransform(1, 0, 0, 1, sharedInfo.getViewH(), sharedInfo.getViewV());
        AffineTransform viewToWorldScale = new AffineTransform(sharedInfo.scale, 0, 0, sharedInfo.scale, 0, 0);

        Point2D.Double worldPoint = new Point2D.Double();
        viewToWorldTranslate.concatenate(viewToWorldScale);
        viewToWorldTranslate.transform(viewPoint, worldPoint);

        return worldPoint;
    }

    public Point2D.Double worldToObjectTransform(Point2D.Double worldPoint, Shape s) {
        Point2D.Double objCoord = new Point2D.Double();
// create a new transformation (defaults to identity)
        AffineTransform worldToObj = new AffineTransform();
//// rotate back from its orientation (last transformation)
//        worldToObj.rotate(-rotation);
//// translate back from its position in the world (first transformation)
//        worldToObj.translate(-center.x, -center.y);
// and transform point from world to object

        AffineTransform objToWorldTranslate = new AffineTransform(1, 0, 0, 1, -s.getCenter().x, -s.getCenter().y);
        AffineTransform objToWorldRotate = new AffineTransform(Math.cos(-s.getRotation()), Math.sin(-s.getRotation()), -Math.sin(-s.getRotation()), Math.cos(-s.getRotation()), 0, 0);
        worldToObj.concatenate(objToWorldRotate);
        worldToObj.concatenate(objToWorldTranslate);

        worldToObj.transform(worldPoint, objCoord);
        return objCoord;
    }

    public Point2D.Double getCenterOfRectangle(Point2D.Double start, double x, double y) {
        return new Point2D.Double((start.x + x) / 2, (start.y + y) / 2);
    }

    public Point2D.Double getCenterOfTriangle(Point2D.Double a, Point2D.Double b, Point2D.Double c) {
        Double x = (a.x + b.x + c.x) / 3;
        Double y = (a.y + b.y + c.y) / 3;
        return new Point2D.Double(x, y);
    }

}
