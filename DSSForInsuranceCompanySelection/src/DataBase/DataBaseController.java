package DataBase;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Controller.Matrix;

public class DataBaseController {

	static Connection connection;
	static Statement statement;
	static boolean foundResults;
	static String query;
	static ResultSet rs;

	public DataBaseController() throws SQLException {

	}

	public void createConnection() throws SQLException {
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			String database = "jdbc:odbc:DSSForInsuranceCompanySelection";

			connection = DriverManager.getConnection(database);
			statement = connection.createStatement();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error!");
		}
	}

	public Matrix getTotalDeathIndemnity() throws SQLException {
		createConnection();

		double value = 0;
		Matrix totalDeathIndemnity = new Matrix(7, 7);
		double[][] matrices = new double[7][7];

		query = "SELECT * FROM Total_Death_Indemnity";
		rs = statement.executeQuery(query);

		if (rs != null) {
			int rowCounter = 0;

			while (rs.next()) {
				for (int coloumnCounter = 0; coloumnCounter < 7; coloumnCounter++) {
					try {
						value = rs.getDouble(coloumnCounter + 1);
						matrices[rowCounter][coloumnCounter] = value;
					} catch (Exception e) {
						System.out.println("exception exist");
					}

				}
				rowCounter++;
			}

			totalDeathIndemnity.setMatrixValues(matrices);

			return totalDeathIndemnity;
		} else {
			connection.close();
		}

		return null;

	}

	public Matrix getInsuranceCompletionIndemnity() throws SQLException {
		createConnection();

		double value = 0;
		Matrix insuranceCompletionIndemnity = new Matrix(7, 7);
		double[][] matrices = new double[7][7];

		query = "SELECT * FROM Insurance_Completion_Indemnity";
		rs = statement.executeQuery(query);

		if (rs != null) {
			int rowCounter = 0;

			while (rs.next()) {
				for (int coloumnCounter = 0; coloumnCounter < 7; coloumnCounter++) {
					try {
						value = rs.getDouble(coloumnCounter + 1);
						matrices[rowCounter][coloumnCounter] = value;
					} catch (Exception e) {
						System.out.println("exception exist");
					}

				}
				rowCounter++;
			}

			insuranceCompletionIndemnity.setMatrixValues(matrices);

			return insuranceCompletionIndemnity;
		} else {
			connection.close();
		}

		return null;
	}

	public Matrix getCancerTreatmentExpenditures() throws SQLException {
		createConnection();

		double value = 0;
		Matrix cancerTreatmentExpenditures = new Matrix(7, 7);
		double[][] matrices = new double[7][7];

		query = "SELECT * FROM Cancer_Treatment_Expenditure";
		rs = statement.executeQuery(query);

		if (rs != null) {
			int rowCounter = 0;

			while (rs.next()) {
				for (int coloumnCounter = 0; coloumnCounter < 7; coloumnCounter++) {
					try {
						value = rs.getDouble(coloumnCounter + 1);
						matrices[rowCounter][coloumnCounter] = value;
					} catch (Exception e) {
						System.out.println("exception exist");
					}

				}
				rowCounter++;
			}

			cancerTreatmentExpenditures.setMatrixValues(matrices);

			return cancerTreatmentExpenditures;
		} else {
			connection.close();
		}

		return null;
	}

	public Matrix getKidneyFailureTreatmentExpenditures() throws SQLException {
		createConnection();

		double value = 0;
		Matrix kidneyFailureTreatmentExpenditures = new Matrix(7, 7);
		double[][] matrices = new double[7][7];

		query = "SELECT * FROM Kidney_Treatment_Expenditure";
		rs = statement.executeQuery(query);

		if (rs != null) {
			int rowCounter = 0;

			while (rs.next()) {
				for (int coloumnCounter = 0; coloumnCounter < 7; coloumnCounter++) {
					try {
						value = rs.getDouble(coloumnCounter + 1);
						matrices[rowCounter][coloumnCounter] = value;
					} catch (Exception e) {
						System.out.println("exception exist");
					}

				}
				rowCounter++;
			}

			kidneyFailureTreatmentExpenditures.setMatrixValues(matrices);

			return kidneyFailureTreatmentExpenditures;
		} else {
			connection.close();
		}

		return null;
	}

	public Matrix getDiscountAtConsructedHealthCareIns() throws SQLException {
		createConnection();

		double value = 0;
		Matrix discountAtConsructedHealthCareIns = new Matrix(7, 7);
		double[][] matrices = new double[7][7];

		query = "SELECT * FROM Discount_at_Health_Care_Institutions";
		rs = statement.executeQuery(query);

		if (rs != null) {
			int rowCounter = 0;

			while (rs.next()) {
				for (int coloumnCounter = 0; coloumnCounter < 7; coloumnCounter++) {
					try {
						value = rs.getDouble(coloumnCounter + 1);
						matrices[rowCounter][coloumnCounter] = value;
					} catch (Exception e) {
						System.out.println("exception exist");
					}

				}
				rowCounter++;
			}

			discountAtConsructedHealthCareIns.setMatrixValues(matrices);

			return discountAtConsructedHealthCareIns;
		} else {
			connection.close();
		}

		return null;
	}

	public Matrix getTeethAndEyeExamination() throws SQLException {
		createConnection();

		double value = 0;
		Matrix teethAndEyeExamination = new Matrix(7, 7);
		double[][] matrices = new double[7][7];

		query = "SELECT * FROM Eye_Teeth_Examination";
		rs = statement.executeQuery(query);

		if (rs != null) {
			int rowCounter = 0;

			while (rs.next()) {
				for (int coloumnCounter = 0; coloumnCounter < 7; coloumnCounter++) {
					try {
						value = rs.getDouble(coloumnCounter + 1);
						matrices[rowCounter][coloumnCounter] = value;
					} catch (Exception e) {
						System.out.println("exception exist");
					}

				}
				rowCounter++;
			}

			teethAndEyeExamination.setMatrixValues(matrices);

			return teethAndEyeExamination;
		} else {
			connection.close();
		}

		return null;
	} 
	
	public String[][] getCostPerYear() throws SQLException{
		createConnection();
		
		String value = "";
		String compname="";
		String [][] matrices = new String[7][2];
		
		query = "SELECT * FROM Cost_Per_Year";
		rs = statement.executeQuery(query);
		
		if (rs != null) {
			int rowCounter = 0;

			while (rs.next()) {
				for(int i =0; i<2;i++)
				{
					if(i==0)
					{
						try {
							compname = rs.getString(1);
							matrices[rowCounter][0] = compname;
						} catch (Exception e) {
							System.out.println("exception exist");
						}
					}else
					{
						try {
							value = ""+rs.getDouble(2);
							matrices[rowCounter][1] = value;
						} catch (Exception e) {
							System.out.println("exception exist");
						}
					}

				}
			rowCounter++;
		}


			return matrices;
		} else {
			connection.close();
		}

		return null;
	}
}
