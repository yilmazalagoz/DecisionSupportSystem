package Controller;

import java.sql.SQLException;

import DataBase.DataBaseController;

public class MatrixOperationsController {

	DataBaseController dataBaseController;

	public MatrixOperationsController() {
		try {
			dataBaseController = new DataBaseController();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// -------------------- RANKING ALTERNATIVES DE KULLANILAN MATRIX
	// FONKSIYONLARI -------------------------

	/*
	 * Subcriteria comparisondan main criteria comparisona geçerken priority
	 * vektorlerin birleþtirilmesi için kullanýlacak ,2 adet subcriteriasý olan
	 * main criterialar içindir
	 */
	public Matrix mergeMatrixes(Matrix matrixA, Matrix matrixB) {
		Matrix mergedMatrix = null;

		if (matrixesCanBeMerged(matrixA, matrixB)) {
			mergedMatrix = new Matrix(matrixA.getmDimension(),
					(matrixA.getnDimension() + matrixB.getnDimension()));

			for (int mCounter = 1; mCounter <= matrixA.getmDimension(); mCounter++) {
				mergedMatrix.setValueToMatrix(mCounter, 1,
						matrixA.getValueFromMatrix(mCounter, 1));
			}

			for (int mCounter = 1; mCounter <= matrixB.getmDimension(); mCounter++) {
				mergedMatrix.setValueToMatrix(mCounter, 2,
						matrixB.getValueFromMatrix(mCounter, 1));
			}
		}
		return mergedMatrix;
	}

	/*
	 * Subcriteria comparisondan main criteria comparisona geçerken priority
	 * vektorlerin birleþtirilmesi için kullanýlacak , 3 adet subcriteriasý olan
	 * main criterialar içindir
	 */
	public Matrix mergeMatrixes(Matrix matrixA, Matrix matrixB, Matrix matrixC) {
		Matrix mergedMatrix = null;
		if (matrixesCanBeMerged(matrixA, matrixB)
				&& matrixesCanBeMerged(matrixA, matrixC)) {
			mergedMatrix = new Matrix(
					matrixA.getmDimension(),
					(matrixA.getnDimension() + matrixB.getnDimension() + matrixC
							.getnDimension()));

			for (int mCounter = 1; mCounter <= matrixA.getmDimension(); mCounter++) {
				mergedMatrix.setValueToMatrix(mCounter, 1,
						matrixA.getValueFromMatrix(mCounter, 1));
			}

			for (int mCounter = 1; mCounter <= matrixA.getmDimension(); mCounter++) {
				mergedMatrix.setValueToMatrix(mCounter, 2,
						matrixB.getValueFromMatrix(mCounter, 1));
			}

			for (int mCounter = 1; mCounter <= matrixA.getmDimension(); mCounter++) {
				mergedMatrix.setValueToMatrix(mCounter, 3,
						matrixC.getValueFromMatrix(mCounter, 1));
			}

		}

		return mergedMatrix;
	}

	// priority vektorlerin birleþtirilmeye uygun olup olmadýðýný kontrol eder
	private boolean matrixesCanBeMerged(Matrix m1, Matrix m2) {
		if (m1.getmDimension() == m2.getmDimension()
				&& m1.getnDimension() == m2.getnDimension())
			return true;
		else
			return false;
	}

	// iki matrixin matrix çarpým kurallarýna uygunluðunu kontrol eder
	private boolean matrixesCanBeMultiplied(Matrix m1, Matrix m2) {
		if (m1.getnDimension() == m2.getmDimension())
			return true;
		else
			return false;
	}

	// iki matrixi çarpýp sonucunu matrix olarak döndürür.
	public Matrix multiplyMatrixes(Matrix matrixA, Matrix matrixB) {
		Matrix result = null;
		if (matrixesCanBeMultiplied(matrixA, matrixB)) {
			result = new Matrix(matrixA.getmDimension(),
					matrixB.getnDimension());
			double value;

			for (int mCounter = 1; mCounter <= matrixA.getmDimension(); mCounter++) {
				for (int nCounter = 1; nCounter <= matrixB.getnDimension(); nCounter++) {
					value = calculateValue(matrixA, matrixB, mCounter, nCounter);
					result.setValueToMatrix(mCounter, nCounter, value);
				}
			}
		}
		return result;
	}

	// matrix çarpýmýnda sonuc matrixinin herbir hücresindeki deðeri hesaplar
	private double calculateValue(Matrix matrixA, Matrix matrixB, int mCounter,
			int nCounter) {
		double value = 0;
		for (int counter = 1; counter <= matrixA.getnDimension(); counter++) {
			value += matrixA.getValueFromMatrix(mCounter, counter)
					* matrixB.getValueFromMatrix(counter, nCounter);
		}
		return value;

	}

	// ----------------------- BOUNDRY CLASSTA KULLANILAN MATRIX FONKSIYONLARI
	// ----------------------

	/*
	 * alternatifleri sýralamak için gerekli olan eigen vektoru döndürür.
	 * kullanicilarýn girdiði deðerlere dayanarak hesaplandý.
	 */
	public Matrix getPriorityVector(Matrix userMatrix) {
		int m = userMatrix.getmDimension();
		int n = userMatrix.getnDimension();

		Matrix normalizedMatrix;
		Matrix eigenVector;
		Matrix aXVector;

		double lamdaMax;
		double consistencyIndex;
		double consistencyRatio;

		normalizedMatrix = normalizeMatrix(userMatrix); // STEP 1
		eigenVector = findEigenVector(normalizedMatrix, m, n); // STEP 2

		return eigenVector;

	}

	// matrixin consistent olup olmadýðýna bakar
	public boolean isMatrixConsistent(Matrix userMatrix) {
		int m = userMatrix.getmDimension();
		int n = userMatrix.getnDimension();

		if (n == 1 || n == 2) {
			return true;
		} else {
			Matrix normalizedMatrix;
			Matrix eigenVector;
			Matrix aXVector;

			double lamdaMax;
			double consistencyIndex;
			double consistencyRatio;

			normalizedMatrix = normalizeMatrix(userMatrix); // STEP 1
			eigenVector = findEigenVector(normalizedMatrix, m, n); // STEP 2
			aXVector = findAxVector(userMatrix, eigenVector); // STEP 3
			lamdaMax = findLamdaMax(aXVector, eigenVector); // STEP 4
			consistencyIndex = findConsistencyIndex(lamdaMax, n); // STEP 5
			consistencyRatio = calculateConsistenceyRatio(consistencyIndex, n); // STEP
																				// 6

			if (consistencyRatio < 0.1) {
				return true;
			} else {
				return false;
			}
		}
	}

	// kullanicinin girdiði matrixi normalized ediyor // STEP 1
	private Matrix normalizeMatrix(Matrix userMatrix) {
		int m = userMatrix.getmDimension();
		int n = userMatrix.getnDimension();
		Matrix coloumnTotals = new Matrix(1, n);
		Matrix normalizedMatrix = new Matrix(m, n);

		coloumnTotals = takeColoumnTotals(userMatrix, m, n);

		normalizedMatrix = setNormalizedValues(userMatrix, m, n, coloumnTotals);

		return normalizedMatrix;
	}

	// eigen vektoru hesaplar normalized martixin satýr ortalamalarý // STEP 2
	private Matrix findEigenVector(Matrix normalizedMatrix, int m, int n) {
		Matrix eigenVector = new Matrix(m, n);

		eigenVector = takeRowAvarage(normalizedMatrix, m, n);

		return eigenVector;
	}

	// kullanýcýnýn girdiði deðerlerin bulunduðu matrixin sutunlarýnýn toplamýný
	// alýyor
	private Matrix takeColoumnTotals(Matrix userMatrix, int m, int n) {
		double total = 0;

		Matrix totals = new Matrix(1, n);

		for (int coloumnCounter = 1; coloumnCounter <= n; coloumnCounter++) {
			for (int rowCounter = 1; rowCounter <= m; rowCounter++) {
				total += userMatrix.getValueFromMatrix(rowCounter,
						coloumnCounter);
			}

			totals.setValueToMatrix(1, coloumnCounter, total);
			total = 0;
		}

		return totals;
	}

	/*
	 * kullanýcýnýn girdiði matrixteki deðerleri sutunlarýn toplamlarýna bölüyor
	 * ve yeni bir matrixe atýp döndürüyor
	 */
	private Matrix setNormalizedValues(Matrix userMatrix, int m, int n,
			Matrix coloumnTotals) {
		Matrix referenceUserMatrix = userMatrix;
		Matrix referenceColoumnTotals = coloumnTotals;
		Matrix normalizedMatrix = new Matrix(m, n);
		double normalizedValue = 0;

		for (int rowCounter = 1; rowCounter <= m; rowCounter++) {
			for (int coloumnCounter = 1; coloumnCounter <= n; coloumnCounter++) {
				normalizedValue = referenceUserMatrix.getValueFromMatrix(
						rowCounter, coloumnCounter)
						/ referenceColoumnTotals.getValueFromMatrix(1,
								coloumnCounter);
				normalizedMatrix.setValueToMatrix(rowCounter, coloumnCounter,
						normalizedValue);
			}
		}

		return normalizedMatrix;
	}

	// normalized edilmiþ matrixin satýr ortalamasýný alýr
	private Matrix takeRowAvarage(Matrix normalizedMatrix, int m, int n) {
		double rowSum = 0;
		double average = 0;

		Matrix rowAverageMatrix = new Matrix(m, 1);

		for (int rowCounter = 1; rowCounter <= m; rowCounter++) {
			for (int coloumnCounter = 1; coloumnCounter <= n; coloumnCounter++) {
				rowSum += normalizedMatrix.getValueFromMatrix(rowCounter,
						coloumnCounter);
			}
			average = rowSum / n;
			rowAverageMatrix.setValueToMatrix(rowCounter, 1, average);
			average = 0;
			rowSum = 0;
		}
		return rowAverageMatrix;
	}

	// kullanýcýnýn girdiði matrix ile eigen vektoru çarpýp ax vektorunu buluyor
	// // STEP 3
	private Matrix findAxVector(Matrix userMatrix, Matrix eigenVector) {
		Matrix foundAxVector = new Matrix(userMatrix.getmDimension(),
				eigenVector.getnDimension());

		foundAxVector = multiplyMatrixes(userMatrix, eigenVector);

		return foundAxVector;
	}

	// lamda maxý hesaplar // STEP 4
	private double findLamdaMax(Matrix foundAxVector, Matrix eigenVector) {
		Matrix referenceFoundAxVector = foundAxVector;
		Matrix referenceEigenVector = eigenVector;
		double lamdaMax = 0;
		int row = foundAxVector.getmDimension();
		double coloumnTotal = 0;
		double divisionValue = 0;
		double value1;
		double value2;
		for (int counter = 1; counter <= row; counter++) {
			value1 = referenceFoundAxVector.getValueFromMatrix(counter, 1);
			value2 = referenceEigenVector.getValueFromMatrix(counter, 1);
			divisionValue = value1 / value2;
			coloumnTotal += divisionValue;
		}

		lamdaMax = coloumnTotal / row;
		return lamdaMax;

	}

	// consistencey indexi hesaplar // STEP 5
	private double findConsistencyIndex(double lamdaMax, int coloumnNumber) {
		return (lamdaMax - coloumnNumber) / (coloumnNumber - 1);
	}

	// consistency ratioyu hesaplar // STEP 6
	private double calculateConsistenceyRatio(double consistencyIndex, int n) {
		if (n == 3) {
			double ratioIndex = 0.58;
			return consistencyIndex / ratioIndex;
		} else
			return 0;
	}

	public Matrix createUserMatrixFromUserRatios(Matrix userRatios) {
		int numberOfDimension = userRatios.getmDimension();
		Matrix userMatrix = new Matrix(numberOfDimension, numberOfDimension);
		double value;

		for (int rowCounter = 1; rowCounter <= numberOfDimension; rowCounter++) {
			for (int coloumnCounter = 1; coloumnCounter <= numberOfDimension; coloumnCounter++) {
				value = userRatios.getValueFromMatrix(rowCounter, 1)
						/ userRatios.getValueFromMatrix(coloumnCounter, 1);
				userMatrix.setValueToMatrix(rowCounter, coloumnCounter, value);
			}
		}
		return userMatrix;
	}

	public String[][] showBestAlternatives(Matrix mainCriteriaMatrix,
			Matrix indemnitySubCriteriaMatrix, Matrix treatmentSubCriteriaMatrix) {
		// Þirketlerin database de tutulan matrixleri için
		Matrix totalDeathIndemnityMatrix = new Matrix(7, 7);
		Matrix insuranceCompletionIndemnityMatrix = new Matrix(7, 7);

		Matrix cancerTreatmentExpendituresMatrix = new Matrix(7, 7);
		Matrix kidneyFailureTreatmentMatrix = new Matrix(7, 7);
		Matrix discountAtConstructedHealthCareInsMatrix = new Matrix(7, 7);

		Matrix teethAndEyeExaminationMatrix = new Matrix(7, 7);

		// Priority Vectorlerin saklanmasý için
		Matrix totalDeathIndemnityPriorityVector = new Matrix(7, 1);
		Matrix insuranceCompletionIndemnityPriorityVector = new Matrix(7, 1);

		Matrix cancerTreatmentExpendituresPriorityVector = new Matrix(7, 1);
		Matrix kidneyFailureTreatmentPriorityVector = new Matrix(7, 1);
		Matrix discountAtConstructedHealthCareInsPriorityVector = new Matrix(7,
				1);

		Matrix teethAndEyeExaminationPriorityVector = new Matrix(7, 1);

		// User matrixlerinin priority vectorlerinin saklanmasý için

		Matrix mainCriteriaPriorityVector;
		Matrix indemnitySubCriteriaPriorityVector;
		Matrix treatmentSubCriteriaPriorityVector;

		Matrix mergedOfIndemnitySubCriteriaMatrixes;
		Matrix mergedOfTreatmentSubCriteriaMatrixes;

		try {

			// Matrixlerin databaseden çekilmesi ---- bu matrixler zaten
			// consistent olrak tutuluyor
			totalDeathIndemnityMatrix = dataBaseController
					.getTotalDeathIndemnity();
			insuranceCompletionIndemnityMatrix = dataBaseController
					.getInsuranceCompletionIndemnity();

			cancerTreatmentExpendituresMatrix = dataBaseController
					.getCancerTreatmentExpenditures();
			kidneyFailureTreatmentMatrix = dataBaseController
					.getKidneyFailureTreatmentExpenditures();
			discountAtConstructedHealthCareInsMatrix = dataBaseController
					.getDiscountAtConsructedHealthCareIns();

			teethAndEyeExaminationMatrix = dataBaseController
					.getTeethAndEyeExamination();

			// Priority vektorlerin oluþturulmasý
			totalDeathIndemnityPriorityVector = getPriorityVector(totalDeathIndemnityMatrix);
			insuranceCompletionIndemnityPriorityVector = getPriorityVector(insuranceCompletionIndemnityMatrix);

			cancerTreatmentExpendituresPriorityVector = getPriorityVector(cancerTreatmentExpendituresMatrix);
			kidneyFailureTreatmentPriorityVector = getPriorityVector(kidneyFailureTreatmentMatrix);
			discountAtConstructedHealthCareInsPriorityVector = getPriorityVector(discountAtConstructedHealthCareInsMatrix);

			teethAndEyeExaminationPriorityVector = getPriorityVector(teethAndEyeExaminationMatrix);

			mergedOfIndemnitySubCriteriaMatrixes = mergeMatrixes(
					totalDeathIndemnityPriorityVector,
					insuranceCompletionIndemnityPriorityVector);
			mergedOfTreatmentSubCriteriaMatrixes = mergeMatrixes(
					discountAtConstructedHealthCareInsPriorityVector,
					cancerTreatmentExpendituresPriorityVector,
					kidneyFailureTreatmentPriorityVector);

			// User matrixlerinin priority vectorlerinin oluþturulmasý

			mainCriteriaPriorityVector = getPriorityVector(mainCriteriaMatrix);
			indemnitySubCriteriaPriorityVector = getPriorityVector(indemnitySubCriteriaMatrix);
			treatmentSubCriteriaPriorityVector = getPriorityVector(treatmentSubCriteriaMatrix);

			Matrix multipliedMatrixForIndemnityAlternatives = multiplyMatrixes(
					mergedOfIndemnitySubCriteriaMatrixes,
					indemnitySubCriteriaPriorityVector);
			Matrix multipliedMatrixFortreatmentAlternatives = multiplyMatrixes(
					mergedOfTreatmentSubCriteriaMatrixes,
					treatmentSubCriteriaPriorityVector);

			Matrix mergedOfMainCriteriaMatrixes = mergeMatrixes(
					multipliedMatrixForIndemnityAlternatives,
					multipliedMatrixFortreatmentAlternatives,
					teethAndEyeExaminationPriorityVector);

			Matrix benefitMatrix = multiplyMatrixes(
					mergedOfMainCriteriaMatrixes, mainCriteriaPriorityVector);

			String alternatives[][] = getAlternativesAccordingToCostBenefit(benefitMatrix);

			return alternatives;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	private String[][] getAlternativesAccordingToCostBenefit(
			Matrix benefitMatrix) throws NumberFormatException, SQLException {
		
		String normalizedMatrix[][] = new String[7][2];
		String gelenMatrix[][] = new String[7][2];
		double doubleMatrix[][] = new double[7][1];
		double costPerBenefit[][] = new double[7][1];
		
		String result[][] = new String[7][2];

		gelenMatrix = dataBaseController.getCostPerYear();
		normalizedMatrix = Matrix.getNormalizedCost(gelenMatrix);

		for (int i = 1; i <= 7; i++) {
			double value1 = Double.parseDouble(normalizedMatrix[i - 1][1]);
			double value2 = benefitMatrix.getValueFromMatrix(i, 1);
			costPerBenefit[i - 1][0] = value1 / value2;

		}
		
		for (int i = 0; i < 7; i++) {
			result[i][0] =normalizedMatrix[i][0];
			result[i][1] = "" + costPerBenefit[i][0];
			

		}
		return result;
		

	}

}
