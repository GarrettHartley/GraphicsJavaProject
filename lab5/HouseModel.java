/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs355.lab5;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 */
public class HouseModel extends WireFrame
{
    List<Line3D> lines = new ArrayList<>();
    
    public HouseModel()
    {
//            //Make the object:
        //Floor
        lines.add(new Line3D(new Point3D(-5,0,-5), new Point3D(5,0,-5)));
        lines.add(new Line3D(new Point3D(5,0,-5), new Point3D(5,0,5)));
        lines.add(new Line3D(new Point3D(5,0,5), new Point3D(-5,0,5)));
        lines.add(new Line3D(new Point3D(-5,0,5), new Point3D(-5,0,-5)));
        //Ceiling
        lines.add(new Line3D(new Point3D(-5,5,-5), new Point3D(5,5,-5)));
        lines.add(new Line3D(new Point3D(5,5,-5), new Point3D(5,5,5)));
        lines.add(new Line3D(new Point3D(5,5,5), new Point3D(-5,5,5)));
        lines.add(new Line3D(new Point3D(-5,5,5), new Point3D(-5,5,-5)));
        //Walls
        lines.add(new Line3D(new Point3D(-5,5,-5), new Point3D(-5,0,-5)));
        lines.add(new Line3D(new Point3D(5,5,-5), new Point3D(5,0,-5)));
        lines.add(new Line3D(new Point3D(5,5,5), new Point3D(5,0,5)));
        lines.add(new Line3D(new Point3D(-5,5,5), new Point3D(-5,0,5)));        
        //Roof
        lines.add(new Line3D(new Point3D(-5,5,-5), new Point3D(0,8,-5)));
        lines.add(new Line3D(new Point3D(0,8,-5), new Point3D(5,5,-5)));
        lines.add(new Line3D(new Point3D(-5,5,5), new Point3D(0,8,5)));
        lines.add(new Line3D(new Point3D(0,8,5), new Point3D(5,5,5)));
        lines.add(new Line3D(new Point3D(0,8,-5), new Point3D(0,8,5)));
//        Door
        lines.add(new Line3D(new Point3D(1,0,5), new Point3D(1,3,5)));
        lines.add(new Line3D(new Point3D(-1,0,5), new Point3D(-1,3,5)));
        lines.add(new Line3D(new Point3D(1,3,5), new Point3D(-1,3,5)));
    }
    
    
    public Iterator<Line3D> getLines()
    {
        return lines.iterator();
    }
}

