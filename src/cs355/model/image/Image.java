/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs355.model.image;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 */
public class Image extends CS355Image{
//    CS355Image image = new CS355Image();
   
    public Image(){
        super();
    }
    public Image(int width, int height) {
        super(width, height);
    }
    
    @Override
    public BufferedImage getImage() {
        //convert my image into a buffered image
        //create buffered image same size as mine BufferedImage(width,height,imageType) TYPE_INT_RGB
        BufferedImage newImage = new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_RGB);
        //set pixels of buffered image. 
        WritableRaster rast = newImage.getRaster(); 
        for(int x = 0; x<getWidth(); x++){
            for(int y = 0; y<getHeight(); y++){
                int[] pixel = getPixel(x,y,null);
                
                rast.setSample(x, y,0 ,pixel[0] );
                rast.setSample(x, y,1 ,pixel[1] );
                rast.setSample(x, y,2 ,pixel[2] );
            }
        }
        return newImage;
    }

    @Override
    public void edgeDetection() {
        ArrayList<float[]> sobelXPixels = new ArrayList<float[]>();
        ArrayList<float[]> sobelYPixels = new ArrayList<float[]>();
        
        getSobelXPixels(sobelXPixels);
        getSobelYPixels(sobelYPixels);
  
        for(int col=0;col<this.getHeight();col++){
            for(int row = 0; row<this.getWidth();row++){
                float[] pixelX = sobelXPixels.get(0);
                float[] pixelY = sobelYPixels.get(0);
                          
                if(pixelY[2]!=0){
                    int breakhere=0;
                }
                int[] rgb = new int[3];
                
                
                
               float newValue =(float)Math.sqrt(Math.pow(pixelX[2], 2)+Math.pow(pixelY[2], 2));
                
//                Color c = Color.getHSBColor(hsb[0],hsb[1],hsb[2]);
                newValue = newValue*255.0f;
                if(newValue == 0 ){
                    int breakhere = 0;
                }
                
                getPixel(row,col,rgb);
                rgb[0] = (int)newValue;
                rgb[1] = (int)newValue;
                rgb[2] = (int)newValue;
                normalizeColor(rgb);
                
                //set the pixel
                setPixel(row,col,rgb);
                
                sobelXPixels.remove(0);
                sobelYPixels.remove(0);
            }
        }
        
        
    }
    
    public void getSobelYPixels(ArrayList<float[]> sobelYPixels){
        
        for(int col=0;col<this.getHeight();col++){
            for(int row = 0; row<this.getWidth();row++){
                int[] rgb = new int[3];
                float[] hsb = new float[3];
                             
                float bSum=0;
                //up Middle
                if(isValidPixel(row-1,col)){
                getPixel(row-1,col,rgb);
                float[] hsbTemp = new float[3];
                Color.RGBtoHSB(rgb[0], rgb[1], rgb[2], hsbTemp);
                bSum-=hsbTemp[2]*2.0f;  
                }
                
                //up right
                if(isValidPixel(row-1,col+1)){
                getPixel(row-1,col+1,rgb);
                float[] hsbTemp = new float[3];
                Color.RGBtoHSB(rgb[0], rgb[1], rgb[2], hsbTemp);
                bSum-=hsbTemp[2]; 
                }
                //up left
                if(isValidPixel(row-1,col-1)){
                getPixel(row-1,col-1,rgb);
                float[] hsbTemp = new float[3];
                Color.RGBtoHSB(rgb[0], rgb[1], rgb[2], hsbTemp);
                bSum-=hsbTemp[2];               
                }
                
                //down middle
                if(isValidPixel(row+1,col)){
                getPixel(row+1,col,rgb);
                float[] hsbTemp = new float[3];
                Color.RGBtoHSB(rgb[0], rgb[1], rgb[2], hsbTemp);
                bSum+=hsbTemp[2]*2.0f;                
                }
                
                //down left
                if(isValidPixel(row+1,col-1)){
                getPixel(row+1,col-1,rgb);
                float[] hsbTemp = new float[3];
                Color.RGBtoHSB(rgb[0], rgb[1], rgb[2], hsbTemp);
                bSum+=hsbTemp[2]; 
                }
                //down right 
                if(isValidPixel(row+1,col+1)){
                getPixel(row+1,col+1,rgb);
                float[] hsbTemp = new float[3];
                Color.RGBtoHSB(rgb[0], rgb[1], rgb[2], hsbTemp);
                bSum+=hsbTemp[2]; 
                }

                float[] clone = hsb.clone();

                clone[2]=(bSum)/8.0f;
                if(clone[2]==0){
                    int breakhere=0;
                }
                
                normalizeBrightness(clone);
                sobelYPixels.add(clone);
            }
        }
    }
    
    public void getSobelXPixels(ArrayList<float[]> sobelXPixels){
        for(int col=0;col<this.getHeight();col++){
            for(int row = 0; row<this.getWidth();row++){
                int[] rgb = new int[3];
                float[] hsb = new float[3];
                             
                float bSum=0;
                //leftMiddle
                if(isValidPixel(row,col-1)){
                getPixel(row,col-1,rgb);
                float[] hsbTemp = new float[3];
                Color.RGBtoHSB(rgb[0], rgb[1], rgb[2], hsbTemp);
                bSum-=hsbTemp[2]*2.0f;  
                }
                
                //left top
                if(isValidPixel(row-1,col-1)){
                getPixel(row-1,col-1,rgb);
                float[] hsbTemp = new float[3];
                Color.RGBtoHSB(rgb[0], rgb[1], rgb[2], hsbTemp);
                bSum-=hsbTemp[2]; 
                }
                
                //left bottom
                if(isValidPixel(row+1,col-1)){
                getPixel(row+1,col-1,rgb);
                float[] hsbTemp = new float[3];
                Color.RGBtoHSB(rgb[0], rgb[1], rgb[2], hsbTemp);
                bSum-=hsbTemp[2];               
                }
                
                //right top
                if(isValidPixel(row-1,col+1)){
                getPixel(row-1,col+1,rgb);
                float[] hsbTemp = new float[3];
                Color.RGBtoHSB(rgb[0], rgb[1], rgb[2], hsbTemp);
                bSum+=hsbTemp[2];                
                }
                
                //right
                if(isValidPixel(row,col+1)){
                getPixel(row,col+1,rgb);
                float[] hsbTemp = new float[3];
                Color.RGBtoHSB(rgb[0], rgb[1], rgb[2], hsbTemp);
                bSum+=hsbTemp[2]*2.0f; 
                }
                //right bottom
                if(isValidPixel(row+1,col+1)){
                getPixel(row+1,col+1,rgb);
                float[] hsbTemp = new float[3];
                Color.RGBtoHSB(rgb[0], rgb[1], rgb[2], hsbTemp);
                bSum+=hsbTemp[2]; 
                }
                

                float[] clone = hsb.clone();

                clone[2]=(bSum)/8.0f;
                
                normalizeBrightness(clone);
                sobelXPixels.add(clone);
            }
        }
    }

    @Override
    public void sharpen() {

        ArrayList<int[]> pixels = new ArrayList<int[]>();
        for(int col=0;col<this.getHeight();col++){
            for(int row = 0; row<this.getWidth();row++){
                int[] rgb = new int[3];
                int redSum=0;
                int greenSum=0;
                int blueSum=0;
                //middle
                if(isValidPixel(row,col)){
                getPixel(row,col,rgb);
                redSum+=6*rgb[0];
                greenSum+=6*rgb[1];
                blueSum+=6*rgb[2];
                }
                
                //top
                if(isValidPixel(row-1,col)){
                getPixel(row-1,col,rgb);
                redSum-=rgb[0];
                greenSum-=rgb[1];
                blueSum-=rgb[2]; 
                }
//left
                if(isValidPixel(row,col-1)){
                getPixel(row,col-1,rgb);
                redSum-=rgb[0];
                greenSum-=rgb[1];
                blueSum-=rgb[2];                
                //right
                }
                if(isValidPixel(row,col+1)){
                getPixel(row,col+1,rgb);
                redSum-=rgb[0];
                greenSum-=rgb[1];
                blueSum-=rgb[2];                
                }

                if(isValidPixel(row-1,col)){
                //bottom
                getPixel(row-1,col,rgb);
                redSum-=rgb[0];
                greenSum-=rgb[1];
                blueSum-=rgb[2];
                }
                
                int test = -50;
                int result = (test/9)/2;
                int[] clone = rgb.clone();
                clone[0]=(redSum)/2;
                clone[1]=(greenSum)/2;
                clone[2]=(blueSum)/2;
                
                normalizeColor(clone);
                pixels.add(clone);
//                setPixel(row,col,clone);
   
            }
        }  
        for(int col=0;col<this.getHeight();col++){
            for(int row = 0; row<this.getWidth();row++){
                int[] pixel = pixels.get(0);
                setPixel(row,col,pixel);
                pixels.remove(0);
            }
        }
    }

    @Override
    public void medianBlur() {
        ArrayList<int[]> pixels = new ArrayList<int[]>();

        for(int col=0;col<this.getHeight()-1;col++){
            for(int row = 0; row<this.getWidth()-1;row++){
                int[] rgb = new int[3];
                ArrayList<Integer> redValues = new ArrayList<Integer>();
                ArrayList<Integer> greenValues = new ArrayList<Integer>();
                ArrayList<Integer> blueValues = new ArrayList<Integer>();
                //middle
                if(isValidPixel(row,col)){
                getPixel(row,col,rgb);
                redValues.add(rgb[0]);
                greenValues.add(rgb[1]);
                blueValues.add(rgb[2]);
                }
                //topLeft
                if(isValidPixel(row-1,col-1)){
                getPixel(row-1,col-1,rgb);
                redValues.add(rgb[0]);
                greenValues.add(rgb[1]);
                blueValues.add(rgb[2]);
                }
                
                //top
                if(isValidPixel(row-1,col)){
                getPixel(row-1,col,rgb);
                redValues.add(rgb[0]);
                greenValues.add(rgb[1]);
                blueValues.add(rgb[2]);
                }
                //topRight
                if(isValidPixel(row+1,col+1)){
                getPixel(row+1,col+1,rgb);
                redValues.add(rgb[0]);
                greenValues.add(rgb[1]);
                blueValues.add(rgb[2]);               
                //left
                }
                if(isValidPixel(row,col-1)){
                getPixel(row,col-1,rgb);
                redValues.add(rgb[0]);
                greenValues.add(rgb[1]);
                blueValues.add(rgb[2]);               
                //right
                }
                if(isValidPixel(row,col+1)){
                getPixel(row,col+1,rgb);
                redValues.add(rgb[0]);
                greenValues.add(rgb[1]);
                blueValues.add(rgb[2]);               
                //bottomLeft
                }
                if(isValidPixel(row+1,col-1)){
                getPixel(row+1,col-1,rgb);
                redValues.add(rgb[0]);
                greenValues.add(rgb[1]);
                blueValues.add(rgb[2]); 
                }
                if(isValidPixel(row-1,col)){
                //bottom
                getPixel(row-1,col,rgb);
                redValues.add(rgb[0]);
                greenValues.add(rgb[1]);
                blueValues.add(rgb[2]);
                }
                if(isValidPixel(row+1,col+1)){
                //bottomRight
                getPixel(row+1,col+1,rgb);
                redValues.add(rgb[0]);
                greenValues.add(rgb[1]);
                blueValues.add(rgb[2]); 
                }
                
                Collections.sort(redValues);
                Collections.sort(greenValues);
                Collections.sort(blueValues);
                
                rgb[0]=(int)median(redValues);
                rgb[1]=(int)median(greenValues);
                rgb[2]=(int)median(blueValues);
                pixels.add(rgb);
//                setPixel(row,col,rgb);
            }
        }
        for(int col=0;col<this.getHeight()-1;col++){
            for(int row = 0; row<this.getWidth()-1;row++){
                int[] pixel = pixels.get(0);
                setPixel(row,col,pixel);
                pixels.remove(0);
            }
        }
    }
    
    public double median(ArrayList<Integer> list) {
    int middle = list.size()/2;
    if (list.size()%2 == 1) {
        return list.get(middle);
    } else {
        return ((double)list.get(middle-1) + (double)list.get(middle)) / 2.0;
    }
}

    @Override
    public void uniformBlur() {

        for(int col=0;col<this.getHeight()-1;col++){
            for(int row = 0; row<this.getWidth()-1;row++){
                int[] rgb = new int[3];
                int redSum=0;
                int greenSum=0;
                int blueSum=0;
                //middle
                if(isValidPixel(row,col)){
                getPixel(row,col,rgb);
                redSum+=rgb[0];
                greenSum+=rgb[1];
                blueSum+=rgb[2];
                }
                //topLeft
                if(isValidPixel(row-1,col-1)){
                getPixel(row-1,col-1,rgb);
                redSum+=rgb[0];
                greenSum+=rgb[1];
                blueSum+=rgb[2];
                }
                
                //top
                if(isValidPixel(row-1,col)){
                getPixel(row-1,col,rgb);
                redSum+=rgb[0];
                greenSum+=rgb[1];
                blueSum+=rgb[2]; 
                }
                //topRight
                if(isValidPixel(row+1,col+1)){
                getPixel(row+1,col+1,rgb);
                redSum+=rgb[0];
                greenSum+=rgb[1];
                blueSum+=rgb[2];                
                //left
                }
                if(isValidPixel(row,col-1)){
                getPixel(row,col-1,rgb);
                redSum+=rgb[0];
                greenSum+=rgb[1];
                blueSum+=rgb[2];                
                //right
                }
                if(isValidPixel(row,col+1)){
                getPixel(row,col+1,rgb);
                redSum+=rgb[0];
                greenSum+=rgb[1];
                blueSum+=rgb[2];                
                //bottomLeft
                }
                if(isValidPixel(row+1,col-1)){
                getPixel(row+1,col-1,rgb);
                redSum+=rgb[0];
                greenSum+=rgb[1];
                blueSum+=rgb[2]; 
                }
                if(isValidPixel(row-1,col)){
                //bottom
                getPixel(row-1,col,rgb);
                redSum+=rgb[0];
                greenSum+=rgb[1];
                blueSum+=rgb[2];
                }
                if(isValidPixel(row+1,col+1)){
                //bottomRight
                    getPixel(row+1,col+1,rgb);
                    redSum+=rgb[0];
                    greenSum+=rgb[1];
                    blueSum+=rgb[2]; 
                }
                
                rgb[0]=redSum/9;
                rgb[1]=greenSum/9;
                rgb[2]=blueSum/9;
                normalizeColor(rgb);
                
                setPixel(row,col,rgb);
   
            }
        }

    }
    
    public void normalizeColor(int[] color){
                if(color[0]>255){
                    color[0]=255;
                }else if(color[0]<0){
                    color[0]=0;
                }
                if(color[1]>255){
                    color[1]=255;
                }else if(color[1]<0){
                    color[1]=0;
                } 
                if(color[2]>255){
                    color[2]=255;
                }else if(color[2]<0){
                    color[2]=0;
                } 
    }
    
        public void normalizeBrightness(float[] brightness){
                if(brightness[2]>1){
                    brightness[2]=1;
                }else if(brightness[2]<0){
                    brightness[2]=0;
                }
    }
    
    public boolean isValidPixel(int row,int col){
        if(row>getWidth()-1||row<0){
            return false;
        }
        if(col>getHeight()-1||col<0){
            return false;
        }
        
        return true;
        
    }

    @Override
    public void grayscale() {
        int[] rgb = new int[3];
        float[] hsb = new float[3];
        
        for(int col =0;col<this.getHeight()-1;col++){
            for(int row = 0; row<this.getWidth()-1;row++){
                getPixel(row,col,rgb);
                Color.RGBtoHSB(rgb[0], rgb[1], rgb[2], hsb);

        // Perform operation here
                hsb[1] = 0;
                
                // convert back to rgb
                Color c = Color.getHSBColor(hsb[0],hsb[1],hsb[2]);
                rgb[0] = c.getRed();
                rgb[1] = c.getGreen();
                rgb[2] = c.getBlue();
                
                //set the pixel
                setPixel(row,col,rgb);
                
            } 
        }
    }

    @Override
    public void contrast(int amount) {

        
        for(int col =0;col<this.getHeight()-1;col++){
            for(int row = 0; row<this.getWidth()-1;row++){
                int[] rgb = new int[3];
                float[] hsb = new float[3];
                getPixel(row,col,rgb);
                Color.RGBtoHSB(rgb[0], rgb[1], rgb[2], hsb);

                // Perform operation here
                float firstNumber = (float)Math.pow(((float)amount+100.0)/(float)100.0, 4);
                float secondNumber = hsb[2]-0.5f;
                float thirdNumber = firstNumber*secondNumber;
                float finalNumber = thirdNumber+0.5f;
                hsb[2] = finalNumber;
                if(hsb[2]< 0){
                    hsb[2]= 0;
                }else if(hsb[2]>1){
                    hsb[2]=1;
                }
                // convert back to rgb
                Color c = Color.getHSBColor(hsb[0],hsb[1],hsb[2]);
                rgb[0] = c.getRed();
                rgb[1] = c.getGreen();
                rgb[2] = c.getBlue();
                normalizeColor(rgb);
                //set the pixel
                setPixel(row,col,rgb);
            } 
        }
    }

    @Override
    public void brightness(int amount) {
        

        
        for(int col =0;col<this.getHeight()-1;col++){
            for(int row = 0; row<this.getWidth()-1;row++){
                
                int[] rgb = new int[3];
                float[] hsb = new float[3];
                getPixel(row,col,rgb);
                Color.RGBtoHSB(rgb[0], rgb[1], rgb[2], hsb);
                
// Perform operation here
                float brightnessFactor =(float)amount/100;
                hsb[2] = hsb[2]+(float)brightnessFactor;
                if(hsb[2]>1){
                    hsb[2]=1;
                }
                // convert back to rgb
                Color c = Color.getHSBColor(hsb[0],hsb[1],hsb[2]);
                
                int red = c.getRed();
                if(red>255){
                    red = 255;
                }else if(red<0){
                    red=0;
                }
                int green = c.getGreen();
                if(green>255){
                    green = 255;
                }else if(green<0){
                    green=0;
                }
                int blue = c.getBlue();
                if(blue>255){
                    blue = 255;
                }else if(blue<0){
                    blue=0;
                }
                rgb[0] = red;
                rgb[1] = green;
                rgb[2] = blue;
                
                //set the pixel
                setPixel(row,col,rgb);
                
            } 
        }
    }
    
}
