package View;

import java.sql.SQLException;
import javax.swing.*;
import javax.xml.bind.ParseConversionEvent;
import javax.xml.soap.MessageFactory;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;

import Controller.MatrixOperationsController;
import Controller.Matrix;
import DataBase.DataBaseController;

@SuppressWarnings("serial")
public class MainPage extends JFrame {

	public JLabel title;
	public JLabel credits;
	public JButton showSubcriteria;
	public JButton nextButton;
	public JButton previousButton;
	public JButton calculateButton;
	public JButton checkConsistencyButton;

	public JPanel messagesPanel;
	public JPanel mainPanel;
	public JPanel indemnitySubCriteriaPanel;
	public JPanel treatmentSubCriteriaPanel;
	public JPanel extrasSubCriteriaPanel;

	JLabel mainLabel1;
	JLabel mainLabel2;
	JLabel mainLabel3;

	JLabel mainLabel12;
	JLabel mainLabel22;
	JLabel mainLabel32;

	JTextField mainTextField[][] = new JTextField[3][3];

	JLabel indemnityLabel1;
	JLabel indemnityLabel2;

	JLabel indemnityLabel12;
	JLabel indemnityLabel22;

	JTextField indemnityTextField[][] = new JTextField[2][2];

	JLabel treatmentLabel1;
	JLabel treatmentLabel2;
	JLabel treatmentLabel3;
	JLabel treatmentLabel12;
	JLabel treatmentLabel22;
	JLabel treatmentLabel32;

	JTextField treatmentTextField[][] = new JTextField[3][3];

	JLabel extrasLabel1;

	JLabel direction;

	Matrix mainCriteriaRatios;
	Matrix indemnityCriteriaRatios;
	Matrix treatmentCriteriaRatios;

	Matrix mainCriteriaMatrix;
	Matrix indemnitySubCriteriaMatrix;
	Matrix treatmentSubCriteriaMatrix;

	Matrix rankedBestAlternatives;

	MatrixOperationsController matrixOperationsController;
	DataBaseController databaseController;

	public static int activePanel = 0;

	public MainPage() throws SQLException {
		initComponents();
		addActionListener();
	}

	private void initComponents() throws SQLException {
		matrixOperationsController = new MatrixOperationsController();

		databaseController = new DataBaseController();

		// Main form properties
		this.setTitle("DSS FOR INSURANCE COMPANY SELECTION");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Title

		title = new JLabel();
		title.setFont(new java.awt.Font("Tahoma", 1, 20));
		title.setText("DSS FOR LIFE INSURANCE COMPANY SELECTION");
		title.setBounds(150, 10, 800, 40);
		title.setVisible(true);
		
		credits = new JLabel();
		credits.setFont(new java.awt.Font("Verdana", 1, 20));
		credits.setText("<html>Credits:<br><h3> Melike Ateþ <br> Mevlüt Dikmen <br> Aylin Kaçar <br> " +
				"Veli Asým Öztürk</h3><html>");
		credits.setBounds(500, 530, 200, 200);
		credits.setVisible(true);

		showSubcriteria = new JButton("Show Subcriteria");
		showSubcriteria.setBounds(350, 300, 220, 40);

		nextButton = new JButton("Next Subcriteria");
		nextButton.setBounds(500, 300, 220, 40);
		nextButton.setVisible(false);

		previousButton = new JButton("Previous Subcriteria");
		previousButton.setBounds(150, 300, 220, 40);
		previousButton.setVisible(false);

		calculateButton = new JButton("<html><center>Show<br>Alternatives</center><html>");
		calculateButton.setBounds(960, 480, 115, 100);
		calculateButton.setEnabled(false);
		calculateButton.setVisible(false);

		checkConsistencyButton = new JButton("<html><center>Check<br>Consistency</center><html>");
		checkConsistencyButton.setBounds(960, 360, 115, 100);
		checkConsistencyButton.setVisible(true);
		checkConsistencyButton.setEnabled(false);

		// Defining Panels

		// Messages Panel

		messagesPanel = new JPanel();
		messagesPanel.setLayout(null);
		messagesPanel.setBounds(875, 80, 200, 250);
		messagesPanel.setBorder(BorderFactory.createLoweredSoftBevelBorder());
		messagesPanel.setVisible(true);

		// Main Panel
		mainPanel = new JPanel();
		mainPanel.setLayout(null);
		mainPanel.setVisible(true);
		mainPanel.setBounds(50, 80, 780, 200);
		mainPanel.setBorder(BorderFactory.createRaisedBevelBorder());

		// Indemnity Panel
		indemnitySubCriteriaPanel = new JPanel();
		indemnitySubCriteriaPanel.setLayout(null);
		indemnitySubCriteriaPanel.setBounds(50, 360, 780, 175);
		indemnitySubCriteriaPanel.setBorder(BorderFactory
				.createRaisedBevelBorder());
		indemnitySubCriteriaPanel.setVisible(false);

		// Treatment Panel
		treatmentSubCriteriaPanel = new JPanel();
		treatmentSubCriteriaPanel.setLayout(null);
		treatmentSubCriteriaPanel.setBounds(50, 360, 900, 200);
		treatmentSubCriteriaPanel.setBorder(BorderFactory
				.createRaisedBevelBorder());
		treatmentSubCriteriaPanel.setVisible(false);

		// Extras Panel
		extrasSubCriteriaPanel = new JPanel();
		extrasSubCriteriaPanel.setLayout(null);
		extrasSubCriteriaPanel.setBounds(50, 360, 470, 100);
		extrasSubCriteriaPanel.setBorder(BorderFactory
				.createRaisedBevelBorder());
		extrasSubCriteriaPanel.setVisible(false);

		// Main criteria components
		mainLabel1 = new JLabel();
		mainLabel2 = new JLabel();
		mainLabel3 = new JLabel();

		mainLabel12 = new JLabel();
		mainLabel22 = new JLabel();
		mainLabel32 = new JLabel();

		mainLabel1.setFont(new java.awt.Font("Tahoma", 1, 13));
		mainLabel1.setText("Indemnity Alternatives");
		mainLabel1.setBounds(30, 60, 200, 30);
		mainLabel1.setVisible(true);

		mainLabel2.setFont(new java.awt.Font("Tahoma", 1, 13));
		mainLabel2.setText("Treatment");
		mainLabel2.setBounds(30, 100, 200, 30);
		mainLabel2.setVisible(true);

		mainLabel3.setFont(new java.awt.Font("Tahoma", 1, 13));
		mainLabel3.setText("Extras");
		mainLabel3.setBounds(30, 140, 200, 30);
		mainLabel3.setVisible(true);

		mainLabel12.setFont(new java.awt.Font("Tahoma", 1, 13));
		mainLabel12.setText("Indemnity Alternatives");
		mainLabel12.setBounds(220, 30, 180, 30);
		mainLabel12.setVisible(true);

		mainLabel22.setFont(new java.awt.Font("Tahoma", 1, 13));
		mainLabel22.setText("Treatment");
		mainLabel22.setBounds(400, 30, 180, 30);
		mainLabel22.setVisible(true);

		mainLabel32.setFont(new java.awt.Font("Tahoma", 1, 13));
		mainLabel32.setText("Extras");
		mainLabel32.setBounds(580, 30, 180, 30);
		mainLabel32.setVisible(true);

		// Indemnity sub criteria components
		indemnityLabel1 = new JLabel();
		indemnityLabel2 = new JLabel();

		indemnityLabel12 = new JLabel();
		indemnityLabel22 = new JLabel();

		indemnityLabel1.setFont(new java.awt.Font("Tahoma", 1, 13));
		indemnityLabel1.setText("Total Death Indemnity");
		indemnityLabel1.setBounds(30, 60, 260, 30);
		indemnityLabel1.setVisible(true);

		indemnityLabel2.setFont(new java.awt.Font("Tahoma", 1, 13));
		indemnityLabel2.setText("Insurance Completion Indemnity");
		indemnityLabel2.setBounds(30, 100, 260, 30);
		indemnityLabel2.setVisible(true);

		indemnityLabel12.setFont(new java.awt.Font("Tahoma", 1, 13));
		indemnityLabel12.setText("Total Death Indemnity");
		indemnityLabel12.setBounds(300, 30, 200, 30);
		indemnityLabel12.setVisible(true);

		indemnityLabel22.setFont(new java.awt.Font("Tahoma", 1, 13));
		indemnityLabel22.setText("Insurance Completion Indemnity");
		indemnityLabel22.setBounds(500, 30, 260, 30);
		indemnityLabel22.setVisible(true);

		// Treatment sub criteria component
		treatmentLabel1 = new JLabel();
		treatmentLabel2 = new JLabel();
		treatmentLabel3 = new JLabel();
		treatmentLabel12 = new JLabel();
		treatmentLabel22 = new JLabel();
		treatmentLabel32 = new JLabel();

		treatmentLabel1.setFont(new java.awt.Font("Tahoma", 1, 13));
		treatmentLabel1.setText("Cancer Treatment Expenditures");
		treatmentLabel1.setBounds(30, 60, 260, 30);
		treatmentLabel1.setVisible(true);

		treatmentLabel2.setFont(new java.awt.Font("Tahoma", 1, 13));
		treatmentLabel2.setText("Kidney Failure Treatment");
		treatmentLabel2.setBounds(30, 100, 260, 30);
		treatmentLabel2.setVisible(true);

		treatmentLabel3.setFont(new java.awt.Font("Tahoma", 1, 13));
		treatmentLabel3
				.setText("<html>Discount At Contracted Health <br>Care Organization");
		treatmentLabel3.setBounds(30, 140, 380, 30);
		treatmentLabel3.setVisible(true);

		treatmentLabel12.setFont(new java.awt.Font("Tahoma", 1, 13));
		treatmentLabel12.setText("<html>Cancer Treatment <br>Expenditures");
		treatmentLabel12.setBounds(275, 20, 260, 30);
		treatmentLabel12.setVisible(true);

		treatmentLabel22.setFont(new java.awt.Font("Tahoma", 1, 13));
		treatmentLabel22.setText("Kidney Failure Treatment");
		treatmentLabel22.setBounds(475, 20, 260, 30);
		treatmentLabel22.setVisible(true);

		treatmentLabel32.setFont(new java.awt.Font("Tahoma", 1, 13));
		treatmentLabel32
				.setText("<html>Discount At Contracted Health <br>Care Organization");
		treatmentLabel32.setBounds(675, 20, 380, 30);
		treatmentLabel32.setVisible(true);

		// Extras sub criteria components
		extrasLabel1 = new JLabel();

		extrasLabel1.setFont(new java.awt.Font("Tahoma", 1, 13));
		extrasLabel1.setText("Teeth and eye examination");
		extrasLabel1.setBounds(30, 30, 200, 30);
		extrasLabel1.setVisible(true);

		// Messages component
		direction = new JLabel();

		direction.setFont(new java.awt.Font("Tahoma", 1, 11));
		direction
				.setText("<html><br><h2>Information</h2>Please give weights to the criteria according to" +
						" your concern.<br><br>Please enter only numeric values to the textboxes<br><br>" +
						" Use 'Enter' button on your keyboard after giving a weight <br><br><br> <html>");
		direction.setBounds(10, 15, 190, 220);
		direction.setVisible(true);

		// COMPONENTLER ÝLGÝLÝ PANELLERE EKLENÝYOR----------------

		fillComponents();
		mainPanel.add(mainLabel1);
		mainPanel.add(mainLabel2);
		mainPanel.add(mainLabel3);
		mainPanel.add(mainLabel12);
		mainPanel.add(mainLabel22);
		mainPanel.add(mainLabel32);

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				mainPanel.add(mainTextField[i][j]);
			}
		}

		indemnitySubCriteriaPanel.add(indemnityLabel1);
		indemnitySubCriteriaPanel.add(indemnityLabel2);
		indemnitySubCriteriaPanel.add(indemnityLabel12);
		indemnitySubCriteriaPanel.add(indemnityLabel22);

		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				indemnitySubCriteriaPanel.add(indemnityTextField[i][j]);
			}
		}

		treatmentSubCriteriaPanel.add(treatmentLabel1);
		treatmentSubCriteriaPanel.add(treatmentLabel2);
		treatmentSubCriteriaPanel.add(treatmentLabel3);
		treatmentSubCriteriaPanel.add(treatmentLabel12);
		treatmentSubCriteriaPanel.add(treatmentLabel22);
		treatmentSubCriteriaPanel.add(treatmentLabel32);

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				treatmentSubCriteriaPanel.add(treatmentTextField[i][j]);
			}
		}

		extrasSubCriteriaPanel.add(extrasLabel1);

		messagesPanel.add(direction);

		// COMPONENTLER ÝLGÝLÝ PANELLERE EKLENDÝ------------------

		// Paneller forma ekleniyor

		this.add(title);
		this.add(credits);
		this.add(mainPanel);
		this.add(messagesPanel);
		this.add(previousButton);
		this.add(nextButton);
		this.add(checkConsistencyButton);
		this.add(calculateButton);
		this.add(indemnitySubCriteriaPanel);
		this.add(treatmentSubCriteriaPanel);
		this.add(extrasSubCriteriaPanel);

		this.setVisible(true);

	}

	public void setButtonUsability() {
		switch (activePanel) {
		case 0: {
			previousButton.setVisible(false);
			nextButton.setVisible(false);
		}
			break;

		case 1: {
			previousButton.setVisible(true);
			nextButton.setVisible(true);
			previousButton.setEnabled(false);
			nextButton.setEnabled(true);
		}
			break;
		case 2: {
			previousButton.setEnabled(true);
			nextButton.setEnabled(true);

		}

			break;
		case 3: {
			previousButton.setEnabled(true);
			nextButton.setEnabled(false);

		}
			break;
		default:
			break;
		}
	}

	private void fillComponents() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				mainTextField[i][j] = new JTextField();
				treatmentTextField[i][j] = new JTextField();
			}
		}

		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				indemnityTextField[i][j] = new JTextField();
			}
		}

		mainTextField[0][0].setBounds(220, 60, 150, 30);
		mainTextField[0][0].setEnabled(false);
		mainTextField[0][0].setText("" + 1);
		mainTextField[1][0].setBounds(220, 100, 150, 30);
		mainTextField[1][0].setEnabled(false);
		mainTextField[2][0].setBounds(220, 140, 150, 30);
		mainTextField[2][0].setEnabled(false);

		mainTextField[0][1].setBounds(400, 60, 150, 30);
		mainTextField[1][1].setBounds(400, 100, 150, 30);
		mainTextField[1][1].setEnabled(false);
		mainTextField[1][1].setText("" + 1);
		mainTextField[2][1].setBounds(400, 140, 150, 30);

		mainTextField[0][2].setBounds(580, 60, 150, 30);
		mainTextField[1][2].setBounds(580, 100, 150, 30);
		mainTextField[2][2].setBounds(580, 140, 150, 30);
		mainTextField[2][2].setEnabled(false);
		mainTextField[2][2].setText("" + 1);
		mainTextField[2][1].setEnabled(false);

		treatmentTextField[0][0].setBounds(275, 60, 180, 30);
		treatmentTextField[0][0].setText("" + 1);
		treatmentTextField[0][0].setEnabled(false);
		treatmentTextField[1][0].setEnabled(false);
		treatmentTextField[2][0].setEnabled(false);
		treatmentTextField[1][0].setBounds(275, 100, 180, 30);
		treatmentTextField[2][0].setBounds(275, 140, 180, 30);

		treatmentTextField[0][1].setBounds(475, 60, 180, 30);
		treatmentTextField[1][1].setBounds(475, 100, 180, 30);
		treatmentTextField[1][1].setText("" + 1);
		treatmentTextField[1][1].setEnabled(false);
		treatmentTextField[2][1].setBounds(475, 140, 180, 30);

		treatmentTextField[0][2].setBounds(675, 60, 180, 30);
		treatmentTextField[1][2].setBounds(675, 100, 180, 30);
		treatmentTextField[2][2].setBounds(675, 140, 180, 30);
		treatmentTextField[2][2].setText("" + 1);
		treatmentTextField[2][2].setEnabled(false);
		treatmentTextField[2][1].setEnabled(false);

		indemnityTextField[0][0].setBounds(300, 60, 180, 30);
		indemnityTextField[0][0].setText("" + 1);
		indemnityTextField[1][0].setBounds(300, 110, 180, 30);
		indemnityTextField[0][1].setBounds(500, 60, 180, 30);
		indemnityTextField[1][1].setBounds(500, 110, 180, 30);
		indemnityTextField[1][1].setText("" + 1);

		indemnityTextField[1][1].setEnabled(false);
		indemnityTextField[0][0].setEnabled(false);
		indemnityTextField[1][0].setEnabled(false);

	}

	public void addActionListener() {

		// --------------------------------
		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(activePanel == 1)
				{
					indemnitySubCriteriaPanel.setVisible(false);
					treatmentSubCriteriaPanel.setVisible(true);
					activePanel = 2;
					setButtonUsability();
					checkConsistencyButton.setEnabled(false);					
				}
				if(activePanel == 2)
				{
					treatmentSubCriteriaPanel.setVisible(false);
					extrasSubCriteriaPanel.setVisible(true);
					activePanel = 3;
					setButtonUsability();
					checkConsistencyButton.setEnabled(false);					
				}

			}
		});

		previousButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(activePanel == 2)
				{
					indemnitySubCriteriaPanel.setVisible(true);
					treatmentSubCriteriaPanel.setVisible(false);
					activePanel = 1;
					setButtonUsability();
					checkConsistencyButton.setEnabled(false);					
				}
				if(activePanel == 3)
				{
					treatmentSubCriteriaPanel.setVisible(true);
					extrasSubCriteriaPanel.setVisible(false);
					activePanel = 2;
					setButtonUsability();
					checkConsistencyButton.setEnabled(false);					
				}
				
				
				
			}
		});

		// ---------------------------
		mainTextField[1][2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Double.parseDouble(mainTextField[1][2].getText()) == 0
						|| Double.parseDouble(mainTextField[1][2].getText()) > 9) {
					JOptionPane
							.showMessageDialog(null,
									"The weights should be greater than 0 and less than or equal to 9.");
				} else {
					mainTextField[2][1].setText(""
							+ (1 / Double.parseDouble((mainTextField[1][2]
									.getText()))));
					if (allCriteriaIsRankedForThreeCriteria(mainTextField)) {
						checkConsistencyButton.setEnabled(true);
					}
				}
			}

		});

		mainTextField[0][1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Double.parseDouble(mainTextField[0][1].getText()) == 0
						|| Double.parseDouble(mainTextField[0][1].getText()) > 9) {
					JOptionPane
							.showMessageDialog(null,
									"The weights should be greater than 0 and less than or equal to 9.");
				} else {
					mainTextField[1][0].setText(""
							+ (1 / Double.parseDouble((mainTextField[0][1]
									.getText()))));
					if (allCriteriaIsRankedForThreeCriteria(mainTextField)) {
						checkConsistencyButton.setEnabled(true);
					}
				}
			}

		});

		mainTextField[0][2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Double.parseDouble(mainTextField[0][2].getText()) == 0
						|| Double.parseDouble(mainTextField[0][2].getText()) > 9) {
					JOptionPane
							.showMessageDialog(null,
									"The weights should be greater than 0 and less than or equal to 9.");
				} else {
					mainTextField[2][0].setText(""
							+ (1 / Double.parseDouble((mainTextField[0][2]
									.getText()))));
					if (allCriteriaIsRankedForThreeCriteria(mainTextField)) {
						checkConsistencyButton.setEnabled(true);
					}
				}
			}

		});

		// ----------------------------------

		treatmentTextField[1][2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Double.parseDouble(treatmentTextField[1][2].getText()) == 0
						|| Double.parseDouble(treatmentTextField[1][2]
								.getText()) > 9) {
					JOptionPane
							.showMessageDialog(null,
									"The weights should be greater than 0 and less than or equal to 9.");
				} else {
					treatmentTextField[2][1].setText(""
							+ (1 / Double.parseDouble((treatmentTextField[1][2]
									.getText()))));
					if (allCriteriaIsRankedForThreeCriteria(treatmentTextField)) {
						checkConsistencyButton.setEnabled(true);
					}
				}
			}

		});

		treatmentTextField[0][1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Double.parseDouble(treatmentTextField[0][1].getText()) == 0
						|| Double.parseDouble(treatmentTextField[0][1]
								.getText()) > 9) {
					JOptionPane
							.showMessageDialog(null,
									"The weights should be greater than 0 and less than or equal to 9.");
				} else {
					treatmentTextField[1][0].setText(""
							+ (1 / Double.parseDouble((treatmentTextField[0][1]
									.getText()))));
					if (allCriteriaIsRankedForThreeCriteria(treatmentTextField)) {
						checkConsistencyButton.setEnabled(true);
					}
				}
			}

		});

		treatmentTextField[0][2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Double.parseDouble(treatmentTextField[0][2].getText()) == 0
						|| Double.parseDouble(treatmentTextField[0][2]
								.getText()) > 9) {
					JOptionPane
							.showMessageDialog(null,
									"The weights should be greater than 0 and less than or equal to 9.");
				} else {
					treatmentTextField[2][0].setText(""
							+ (1 / Double.parseDouble((treatmentTextField[0][2]
									.getText()))));
					if (allCriteriaIsRankedForThreeCriteria(treatmentTextField)) {
						checkConsistencyButton.setEnabled(true);
					}
				}
			}

		});

		// ------------------------------------

		indemnityTextField[0][1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Double.parseDouble(indemnityTextField[0][1].getText()) == 0
						|| Double.parseDouble(indemnityTextField[0][1]
								.getText()) > 9) {
					JOptionPane
							.showMessageDialog(null,
									"The weights should be greater than 0 and less than or equal to 9.");
				} else {
					indemnityTextField[1][0].setText(""
							+ (1 / Double.parseDouble((indemnityTextField[0][1]
									.getText()))));
					if (allCriteriaIsRankedForTwoCriteria(indemnityTextField)) {
						checkConsistencyButton.setEnabled(true);
					}
				}
			}

		});

		// --------------------------------------

		checkConsistencyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int lastActivePanel = activePanel;
				if (lastActivePanel == 0) {
					Boolean isMatrixConsistent = matrixOperationsController
							.isMatrixConsistent(getUserMainCriteriaMatrix());

					if (isMatrixConsistent) {
						JOptionPane.showMessageDialog(null, "Consistent !!!");
						indemnitySubCriteriaPanel.setVisible(true);
						activePanel = 1;
						setButtonUsability();
						checkConsistencyButton.setEnabled(false);
					} else {
						JOptionPane
								.showMessageDialog(null,
										"Your comparison is not consistent, please change your weights.");

					}

				}

				if (lastActivePanel == 1) {
					Boolean isMatrixConsistent = matrixOperationsController
							.isMatrixConsistent(getUserIndemnitySubcriteriaMatrix());

					if (isMatrixConsistent) {
						JOptionPane.showMessageDialog(null, "Consistent !!!");
						indemnitySubCriteriaPanel.setVisible(false);
						treatmentSubCriteriaPanel.setVisible(true);
						activePanel = 2;
						setButtonUsability();
						checkConsistencyButton.setEnabled(false);
					}
					

				}
				if (lastActivePanel == 2) {
					Boolean isMatrixConsistent = matrixOperationsController
							.isMatrixConsistent(getUserIndemnitySubcriteriaMatrix());

					if (isMatrixConsistent) {
						JOptionPane.showMessageDialog(null, "Consistent !!!");
						treatmentSubCriteriaPanel.setVisible(false);
						extrasSubCriteriaPanel.setVisible(true);
						activePanel = 3;
						setButtonUsability();
						checkConsistencyButton.setEnabled(false);
						calculateButton.setEnabled(true);
						calculateButton.setVisible(true);
					}
				}
			}
		});

		calculateButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Matrix mainCriteriaMatrix;
				Matrix indemnitySubCriteriaMatrix;
				Matrix treatmentSubCriteriaMatrix;

				mainCriteriaMatrix = getUserMainCriteriaMatrix();
				indemnitySubCriteriaMatrix = getUserIndemnitySubcriteriaMatrix();
				treatmentSubCriteriaMatrix = getUserTreatmentSubcriteriaMatrix();

				String bestAlternatives[][] = matrixOperationsController
						.showBestAlternatives(mainCriteriaMatrix,
								indemnitySubCriteriaMatrix,
								treatmentSubCriteriaMatrix);

				direction.setText("<html><h3>Company Name      Benefit<br></h3><html>" );
				for(int i= 0 ; i<7 ; i++)
				{
					direction.setText( direction.getText() + "<html> <br> <html> " +bestAlternatives[i][0] + "      " + bestAlternatives[i][1]);
				}

			}
		});

	}

	private boolean allCriteriaIsRankedForThreeCriteria(
			JTextField[][] textFieldMatrix) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (textFieldMatrix[i][j].getText().isEmpty()) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean allCriteriaIsRankedForTwoCriteria(
			JTextField[][] textFieldMatrix) {
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				if (textFieldMatrix[i][j].getText().isEmpty()) {
					return false;
				}
			}
		}
		return true;
	}





	private Matrix getUserMainCriteriaMatrix() {
		Matrix userMainCriteriaMatrix = new Matrix(3, 3);

		double value = 0;

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				value = Double.parseDouble(mainTextField[i][j].getText());
				userMainCriteriaMatrix.setValueToMatrix(i + 1, j + 1, value);
			}
		}

		return userMainCriteriaMatrix;
	}

	private Matrix getUserIndemnitySubcriteriaMatrix() {
		Matrix userIndemnitySubcriteriaMatrix = new Matrix(2, 2);

		for (int i = 1; i <= 2; i++) {
			for (int j = 1; j <= 2; j++) {
				userIndemnitySubcriteriaMatrix.setValueToMatrix(i, j,
						Double.parseDouble(indemnityTextField[i - 1][j - 1]
								.getText()));
			}
		}

		return userIndemnitySubcriteriaMatrix;
	}

	private Matrix getUserTreatmentSubcriteriaMatrix() {
		Matrix userTreatmentSubcriteriaMatrix = new Matrix(3, 3);

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				userTreatmentSubcriteriaMatrix.setValueToMatrix(i + 1, j + 1,
						Double.parseDouble(treatmentTextField[i][j].getText()));
			}
		}

		return userTreatmentSubcriteriaMatrix;
	}

	public static void main(String[] args) throws SQLException {
		MainPage page = new MainPage();
		ImagePanel panel = new ImagePanel(
				new ImageIcon("img\\bg.png").getImage());
		page.getContentPane().add(panel);
		page.pack();
		page.setVisible(true);

	}

}

class ImagePanel extends JPanel {
	public JLabel wtitle;
	public JLabel welcomeLb;
	private Image img;

	public ImagePanel(String img) {
		this(new ImageIcon(img).getImage());

	}

	public ImagePanel(Image img) {
		this.img = img;

		Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setSize(size);

		setLayout(null);
	}

	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, null);
	}

}
