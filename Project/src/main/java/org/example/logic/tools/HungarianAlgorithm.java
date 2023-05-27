package org.example.logic.tools;

/**
 * @see "https://brilliant.org/wiki/hungarian-matching/"
 */
public class HungarianAlgorithm {
    public static int[][] match(double[][] data) {
        if (!isQuadratic(data)) {
            throw new IllegalArgumentException("Matrix for Hungarian Algorithmen has to be quadratic");
        }

        // Create initial matrix
        Matrix matrix = new Matrix(data);

        // Subtract the smallest entry in each row from all the other entries in the row.
        // This will make the smallest entry in the row now equal to 0.
        for (int i = 0; i < matrix.getSize(); i++) {
            matrix.subtractMinValueFromRow(i);
        }

        // Subtract the smallest entry in each column from all the other entries in the column.
        // This will make the smallest entry in the column now equal to 0.
        for (int i = 0; i < matrix.getSize(); i++) {
            matrix.subtractMinValueFromCol(i);
        }

        // Draw lines through the row and columns that have the 0 entries such that the fewest lines possible are drawn.
        matrix.coverRowsAndCols();

        // If there are n lines drawn, an optimal assignment of zeros is possible and the algorithm is finished.
        // If the number of lines is less tha n, then the optimal number of zeroes is not yet reached.
        while (matrix.getLines() < matrix.getSize()) {

            //Find the smallest entry not covered by any line. Subtract this entry from each row that isn’t crossed out,
            // and then add it to each column that is crossed out.
            double smallestValue = matrix.findMinUncoveredValue();
            matrix.subtractFromUncoveredRows(smallestValue);
            matrix.addToCoveredCol(smallestValue);
            matrix.coverRowsAndCols();
        }

        return matrix.findAssignment();
    }

    private static boolean isQuadratic(double[][] array) {
        int numRows = array.length;
        for (double[] row : array) {
            if (row.length != numRows) {
                return false;
            }
        }
        return true;
    }
}
