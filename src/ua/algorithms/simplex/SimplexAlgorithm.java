package ua.algorithms.simplex;

import java.text.DecimalFormat;

public class SimplexAlgorithm {

	public static final double MAX = 999999999;

	/**
	 * Method to perform the step by step Implementation of the simplex
	 * algorithm
	 * 
	 * @param ingrdtNameArray
	 * @param quantityArray
	 * @param prodNameArray
	 * @param profitArray
	 * @param allQuantities
	 * @return
	 */
	public String performSimplex(String ingrdtNameArray[],
			double quantityArray[], String prodNameArray[],
			double profitArray[], double allQuantities[][]) {
		try {
			int ingredients = ingrdtNameArray.length;
			int products = profitArray.length;

			double tabule[][] = createTwoDMatrix(quantityArray, profitArray,
					allQuantities, ingredients, products);

			DecimalFormat decimalFormat = new DecimalFormat("#####.##");

			for (int i = 0; i < ingredients + 1; i++) {
				for (int j = 0; j < (ingredients + products + 1); j++) {
					System.out.print(decimalFormat.format(tabule[i][j]) + "\t");
				}
				System.out.println("");
			}

			// Apply gaussian till all the coefficients become 0's or negative
			boolean isCoeffNeg = false;

			long startTime = System.nanoTime();
			int numberOfItr = 0;
			while (!isCoeffNeg) {
				tabule = applyGaussian(tabule, products, ingredients);
				numberOfItr++;
				System.out
						.println("------------------------------------------------");
				for (int i = 0; i < ingredients + 1; i++) {
					for (int j = 0; j < (ingredients + products + 1); j++) {
						System.out.print(decimalFormat.format(tabule[i][j])
								+ "\t");
					}
					System.out.println("");
				}

				isCoeffNeg = checkForNegCoeff(tabule);
			}

			long endTime = System.nanoTime();
			long diff = endTime - startTime;

			System.out.println("Method Name:- TotalTime :- " + diff);

			String finalStatement = printMatrix(tabule, prodNameArray,
					products, ingredients, diff, numberOfItr);

			System.out.println(finalStatement);

			return finalStatement;

		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * Method to form a single table from all the individual arrays
	 * 
	 * @param quantityArray
	 * @param profitArray
	 * @param allQuantities
	 * @param ingredients
	 * @param products
	 * @return
	 */
	private double[][] createTwoDMatrix(double quantityArray[],
			double profitArray[], double allQuantities[][], int ingredients,
			int products) {

		double tabule[][] = new double[ingredients + 1][products + ingredients
				+ 1];

		// adding all the quantities in the tabule
		for (int i = 0; i < ingredients; i++) {
			for (int j = 0; j < products; j++) {
				tabule[i][j] = allQuantities[i][j];
			}
		}

		// creating an identity matrix of size ingredientsXingredients this is
		// for slack variables
		for (int i = 0; i < ingredients; i++) {
			tabule[i][products + i] = 1;
		}

		// adding the profits to the last row
		for (int i = 0; i < products; i++) {
			tabule[ingredients][i] = profitArray[i];
		}

		// adding last colomn with the quantity
		for (int i = 0; i < ingredients; i++) {
			tabule[i][products + ingredients] = quantityArray[i];
		}
		return tabule;
	}

	/**
	 * Method to find the pivot row
	 * 
	 * @param tabule
	 * @param products
	 * @param ingredients
	 * @param pivotCol
	 * @return
	 */
	private int findPivotRow(double tabule[][], int products, int ingredients,
			int pivotCol) {
		int pivotRow = 0;
		double minRatio;

		if (tabule[pivotRow][pivotCol] != 0 && tabule[pivotRow][pivotCol] > 0) {
			minRatio = tabule[pivotRow][products + ingredients]
					/ tabule[pivotRow][pivotCol];
		} else {
			minRatio = MAX;
		}

		for (int i = 0; i < ingredients; i++) {
			if (tabule[i][pivotCol] > 0
					&& minRatio > tabule[i][products + ingredients]
							/ tabule[i][pivotCol]) {
				minRatio = tabule[i][products + ingredients]
						/ tabule[i][pivotCol];
				pivotRow = i;
			}
		}
		return pivotRow;
	}

	/**
	 * Method to find the pivot column
	 * 
	 * @param tabule
	 * @param products
	 * @return
	 */
	private int findPivotColoum(double tabule[][], int products) {

		int pivotCol = 0;

		// first element of last row
		double maxVal = tabule[tabule.length - 1][0];

		for (int j = 0; j < products; j++) {
			if (maxVal < tabule[tabule.length - 1][j]) {
				pivotCol = j;
				maxVal = tabule[tabule.length - 1][j];
			}
		}
		return pivotCol;
	}

	/**
	 * Method perform gaussian elimination w.r.t the identified element at
	 * pivot[row][column]
	 * 
	 * @param tabule
	 * @param products
	 * @param ingredients
	 * @return
	 */
	private double[][] applyGaussian(double tabule[][], int products,
			int ingredients) {
		int pivotCol = findPivotColoum(tabule, products);
		int pivotRow = findPivotRow(tabule, products, ingredients, pivotCol);

		for (int i = 0; i < ingredients + 1; i++) {
			for (int j = 0; j < (products + ingredients + 1); j++) {
				if (i != pivotRow && j != pivotCol) {
					tabule[i][j] -= ((tabule[pivotRow][j] * tabule[i][pivotCol]) / tabule[pivotRow][pivotCol]);
				}

			}
		}

		for (int i = 0; i < ingredients + 1; i++) {
			if (i != pivotRow) {
				tabule[i][pivotCol] = 0;
			}
		}

		for (int j = 0; j < (products + ingredients + 1); j++) {
			if (j != pivotCol) {
				tabule[pivotRow][j] /= tabule[pivotRow][pivotCol];
			}

		}

		// making the pivot block 1
		tabule[pivotRow][pivotCol] = 1;
		return tabule;
	}

	/**
	 * Method to check for negative coefficients in the last row of the table
	 * 
	 * @param tabule
	 * @return
	 */
	private boolean checkForNegCoeff(double[][] tabule) {
		int numRows;
		int numCols;
		boolean returnVal = false;
		if (tabule != null) {
			numRows = tabule.length;
			numCols = tabule[0].length;

			for (int j = 0; j < numCols; j++) {
				if (tabule[numRows - 1][j] > 0) {
					returnVal = false;
					break;
				} else {
					returnVal = true;
				}
			}

		}
		return returnVal;
	}

	/**
	 * Method to parse the table after the simplex algorithm terminates and form
	 * a conclusion
	 * 
	 * @param tabule
	 * @param prodNames
	 * @param products
	 * @param ingredients
	 * @return
	 */
	private String printMatrix(double tabule[][], String prodNames[],
			int products, int ingredients, long time, int numberOfItr) {
		StringBuffer finalStatement = new StringBuffer();

		DecimalFormat decimalFormat = new DecimalFormat("#####.##");

		for (int j = 0; j < products; j++) {
			for (int i = 0; i < ingredients; i++) {
				if (tabule[i][j] == 1) {
					String productName = prodNames[j];
					double noOfUnits = Math.abs(tabule[i][products
							+ ingredients]);
					if (tabule[tabule.length - 1][j] == 0)
						finalStatement.append(decimalFormat.format(noOfUnits))
								.append(" units of ").append(productName);
					else
						finalStatement.append("0").append(" units of ")
								.append(productName);

					finalStatement.append(", ");
				}
			}

		}

		finalStatement.append(" will give you the maximum profit of ");

		double maxProfit = Math
				.abs(tabule[ingredients][products + ingredients]);

		finalStatement.append("$").append(decimalFormat.format(maxProfit));

		/*// adding time to final statement
		finalStatement.append("\n").append("Time taken for the process is")
				.append(" ").append(time).append("ns").append(" ")
				.append("Number of iterations are").append(" ")
				.append(numberOfItr);*/

		return finalStatement.toString();
	}

}
