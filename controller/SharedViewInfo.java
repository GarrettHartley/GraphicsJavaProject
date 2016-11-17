/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs355.controller;

/**
 *
 */
public class SharedViewInfo {
    
    double scale = 1;
    double viewH;
    double viewRight;
    int knobSize = 512;
    boolean drawImage = false;
    

    public SharedViewInfo() {
        scale =1;
        viewH=0;
        viewRight=0;  
    }
    
    public void setDrawImage(boolean drawImage){
        this.drawImage = drawImage;
    }
    
    public boolean isDrawImage(){
        return this.drawImage;
    }
    
    public void setScale(double scale){
        this.scale = scale;
    }
    
    public double getScale(){
        return this.scale;
    }

    public double getViewH() {
        return viewH;
    }

    public void setViewH(double viewV) {
        this.viewH = viewV;
    }

    public double getViewV() {
        return viewRight;
    }

    public void setViewV(double viewRight) {
        this.viewRight = viewRight;
    }

    public int getKnobSize() {
        return knobSize;
    }

    public void setKnobSize(int knobSize) {
        this.knobSize = knobSize;
    }
    
    
    
}
