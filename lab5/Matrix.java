/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs355.lab5;

public class Matrix {

	double array[][] = new double[4][4];
	
	public Matrix(){
		
	}
	
	public Matrix(double aa,double ab,double ac,double ad,double ba,double bb,double bc,double bd,double ca,double cb,
			double cc,double cd,double da,double db,double dc,double dd){
		array[0][0]= aa;
		array[0][1]= ab;
		array[0][2]= ac;
		array[0][3]= ad;
		array[1][0]= ba;
		array[1][1]= bb;
		array[1][2]= bc;
		array[1][3]= bd;
		array[2][0]= ca;
		array[2][1]= cb;
		array[2][2]= cc;
		array[2][3]= cd;
		array[3][0]= da;
		array[3][1]= db;
		array[3][2]= dc;
		array[3][3]= dd;
	}
	
	public double[][] getArray(){
		return this.array;
	}
	
	public void setArray(double[][] newArray){
		array = newArray;
	}
	
	public Matrix multiply(Matrix matrix){
		double inputArray[][] = matrix.getArray();
		double[][] resultArray = new double[4][4];
		Matrix resultMatrix = new Matrix();
		for (int i = 0; i < 4; i++)
			   for (int j = 0; j < 4; j++)
			      for (int k = 0; k < 4; k++)
			         resultArray[i][j] += this.array[i][k] * inputArray[k][j];
		resultMatrix.setArray(resultArray);
		return resultMatrix;
	}
	
	public HomogeneousPoint multiplyPoint(HomogeneousPoint point){
		double[] resultArray = new double[4];
		double[] coordinates = point.getCoordinates();
		for (int row = 0; row < 4; row++)
			   for (int col = 0; col < 4; col++)
			         resultArray[row] += this.array[row][col] * coordinates[col];
		
		return new HomogeneousPoint(resultArray[0],resultArray[1],resultArray[2], resultArray[3]);
		
	}
	
}

