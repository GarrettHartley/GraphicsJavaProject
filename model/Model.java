/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs355.model;
import cs355.model.drawing.CS355Drawing;
import cs355.model.drawing.CreateShape;
import cs355.model.drawing.Shape;
import cs355.model.image.Image;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/*
 */
public class Model extends CS355Drawing{
    ArrayList<Shape> shapes = new ArrayList<Shape>();
    CreateShape createShape;
    int selectedShape = -1;
    Color color;
    boolean threeDimensionalView = false;
    float step = 1;
	float degree = 0;
	float xDisp = 0;
	float yDisp = -2;
	float zDisp = -20;
    Image image = new Image();

    public void setColor(Color newColor){
        color = newColor;
    }
    
    public Color getColor(){
        return color;
    }
    
    @Override
    public Shape getShape(int index) {
        return shapes.get(index);
    }

    @Override
    public int addShape(Shape s) {
        shapes.add(s);
     
        return shapes.size();
    }

    @Override
    public void deleteShape(int index) {
        if(selectedShape>-1){
            selectedShape--;
            shapes.remove(index);
        }
    }

    @Override
    public void moveToFront(int index) {
        Collections.swap(shapes, index, shapes.size()-1);
        this.setSelectedShape(shapes.size()-1);
    }

    @Override
    public void movetoBack(int index) {
        Collections.swap(shapes, index, 0);
        this.setSelectedShape(0);
    }

    @Override
    public void moveForward(int index) {
        if(index<shapes.size()-1){
         Collections.swap(shapes, index, index+1);
         this.setSelectedShape(index+1);
        }
        
    }

    @Override
    public void moveBackward(int index) {
        if(index>0){
         Collections.swap(shapes, index, index-1);
         this.setSelectedShape(index-1);
        }
        
    }

    @Override
    public List<Shape> getShapes() {
        return shapes;
        }

    @Override
    public List<Shape> getShapesReversed() {
        //return Collections.reverse(shapes);
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setShapes(List<Shape> shapes) {
        this.shapes = (ArrayList)shapes;
        this.notifyObservers();
    }
    
    public int getSelectedShapeIndex(){
        return selectedShape;
    }

    public Shape getSelectedShape() {
       
            return shapes.get(selectedShape);
        
    }

    public void setSelectedShape(int selectedShape) {
        this.selectedShape = selectedShape;
    }

    public CreateShape getCreateShape() {
        return createShape;
    }

    public void setCreateShape(CreateShape createShape) {
        this.createShape = createShape;
    }
    
    public boolean isThreeDimensionalView(){
        return threeDimensionalView;
    }
    
    public void toggleThreeDimensionalView(){
        if(threeDimensionalView){
            threeDimensionalView = false;
        }else{
            threeDimensionalView = true;
        }
    }

    public float getDegree() {
        return degree;
    }

    public void setDegree(float degree) {
        this.degree = degree;
    }

    public double getxDisp() {
        return xDisp;
    }

    public void setxDisp(float xDisp) {
        this.xDisp = xDisp;
    }

    public float getyDisp() {
        return yDisp;
    }

    public void setyDisp(float yDisp) {
        this.yDisp = yDisp;
    }

    public float getzDisp() {
        return zDisp;
    }

    public void setzDisp(float zDisp) {
        this.zDisp = zDisp;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
    

    
    
    
    
  
}
