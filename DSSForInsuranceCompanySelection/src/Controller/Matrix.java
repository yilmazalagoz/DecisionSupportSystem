package Controller;


public class Matrix {
	
	private int mDimension;
	
	private int nDimension;
	
	public double[][] matrixValues;
	
	public String [][] mixedMatrix;
	
	public Matrix (int mDimension, int nDimension)
	{
		this.setmDimension(mDimension);
		this.setnDimension(nDimension);
		this.setMatrixValues(new double[mDimension][nDimension] );
		
						
	}
	public int getmDimension() {
		return mDimension;
	}

	public void setmDimension(int mDimension) {
		this.mDimension = mDimension;
	}

	public int getnDimension() {
		return nDimension;
	}

	public void setnDimension(int nDimension) {
		this.nDimension = nDimension;
	}

	public double[][] getMatrixValues() {
		return matrixValues;
	}

	public void setMatrixValues(double[][] matrix) {
		this.matrixValues = matrix;
	}
	
	public void setMixedMatrixValues(String [][] matrix)
	{
		this.mixedMatrix=matrix;
	}

	public void setValueToMatrix(int mDimension , int nDimension, double value) {
		this.matrixValues[mDimension - 1][nDimension - 1] = value;
	}
	
	public double getValueFromMatrix(int mDimension , int nDimension)
	{
		double value = this.matrixValues[mDimension - 1][nDimension - 1];
		return 	value ;
	}
//	public String getValueFromMixedMatrix(int mDimension , int nDimension)
//	{
//		double value=0;
//		String compname="";
//		
//		String costCompanyMatrix[][] = new String[7][2];
//		
//		if(nDimension==2)
//		{
//			value=this.matrixValues[mDimension-1][1];
//			costCompanyMatrix[][]
//		}
//		compname=""+this.matrixValues[mDimension-1][0];
//		return compname;
//	}
	public static String [][] getNormalizedCost(String[][] matrix)
	{
		double total=0;
		
		String normalizedmatrix[][]= new String[7][2];
		
		for(int i=0 ; i<7 ; i++)//satýr
		{
			for(int j=0 ; j<2 ; j++)
			{
				if(j == 1) //sutun
				{
					total += Double.parseDouble((matrix[i][j]));
				}
			}
			
		}
		
		
		for(int i=0 ; i<7 ; i++)
		{
			for(int j=0 ; j<2 ; j++)
			{
				if(j == 0) //sutun
				{
					normalizedmatrix[i][j] = matrix[i][j];			
				}
				
				if(j == 1)
				{
					normalizedmatrix[i][j] = "" + Double.parseDouble(matrix[i][j]) / total;			

				}
			}
			
		}
		return normalizedmatrix;
	}
	
}
