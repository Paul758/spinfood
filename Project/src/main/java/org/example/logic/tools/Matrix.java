package org.example.logic.tools;

import java.util.Arrays;

public class Matrix {
    private final double[][] data;
    private final int size;
    private int lines;
    boolean[] isRowCovered;
    boolean[] isColCovered;

    public Matrix(double[][] data) {
        this.size = data.length;
        this.data = Arrays.copyOf(data, size);
        this.lines = 0;

        this.isRowCovered = new boolean[size];
        this.isColCovered = new boolean[size];
    }

    public void coverRowsAndCols() {
        int[] rowZeroCount = new int[size];
        int[] colZeroCount = new int[size];

        while (hasUncoveredZeros()) {
            for (int i = 0; i < size; i++) {
                if (isRowCovered[i]) rowZeroCount[i] = -1;
                else rowZeroCount[i] = countAvailableZeros(getRow(i), isColCovered);
                if (isColCovered[i]) colZeroCount[i] = -1;
                else colZeroCount[i] = countAvailableZeros(getCol(i), isRowCovered);
            }

            int rowIndex = getIndexWithMaxZeros(rowZeroCount, isRowCovered);
            int colIndex = getIndexWithMaxZeros(colZeroCount, isColCovered);

            if (rowZeroCount[rowIndex] > colZeroCount[colIndex]) {
                isRowCovered[rowIndex] = true;
            } else {
                isColCovered[colIndex] = true;
            }
            lines++;
        }
    }

    private boolean hasUncoveredZeros() {
        for (int i = 0; i < size; i++) {
            if (isRowCovered[i]) continue;
            for (int j = 0; j < size; j++) {
                if (data[i][j] == 0 && !isColCovered[j]) {
                    return true;
                }
            }
        }
        return false;
    }

    private double[] getRow(int index) {
        return data[index];
    }

    private double[] getCol(int index) {
        double[] col = new double[size];
        for (int i = 0; i < size; i++) {
            col[i] = data[i][index];
        }
        return col;
    }

    private double getMinValue(double[] values) {
        double minValue = Double.MAX_VALUE;
        for (double value : values) {
            if (value < minValue) {
                minValue = value;
            }
        }
        return minValue;
    }

    public void subtractMinValueFromRow(int rowIndex) {
        double minValue = getMinValue(getRow(rowIndex));
        for (int i = 0; i < size; i++) {
            data[rowIndex][i] -= minValue;
        }
    }

    public void subtractMinValueFromCol(int colIndex) {
        double minValue = getMinValue(getCol(colIndex));
        for (int i = 0; i < size; i++) {
            data[i][colIndex] -= minValue;
        }
    }

    public double findMinUncoveredValue() {
        double minValue = Double.MAX_VALUE;
        for (int i = 0; i < size; i++) {
            if (isRowCovered[i]) continue;
            for (int j = 0; j < size; j++) {
                if (data[i][j] < minValue && !isColCovered[j]) {
                    minValue = data[i][j];
                }
            }
        }
        return minValue;
    }

    public void subtractFromUncoveredRows(double value) {
        for (int i = 0; i < size; i++) {
            if (isRowCovered[i]) continue;
            for (int j = 0; j < size; j++) {
                data[i][j] -= value;
            }
        }
    }

    public void addToCoveredCol(double value) {
        for (int i = 0; i < size; i++) {
            if (!isColCovered[i]) continue;
            for (int j = 0; j < size; j++) {
                data[j][i] += value;
            }
        }
    }

    private int countAvailableZeros(double[] values, boolean[] isCovered) {
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (values[i] == 0 && !isCovered[i]) {
                count++;
            }
        }
        return count;
    }

    private int countZeros(double[] entries) {
        int count = 0;
        for (double entry : entries) {
            if (entry == 0d) {
                count++;
            }
        }
        return count;
    }

    public int getSize() {
        return size;
    }

    public int getLines() {
        return lines;
    }

    public void print() {
        for (double[] row : data) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println();
    }

    public int[][] findAssignment() {
        int[][] assignment = new int[size][2];
        int[] rowZeroCount = new int[size];
        int[] colZeroCount = new int[size];
        boolean[] isRowAssigned = new boolean[size];
        boolean[] isColAssigned = new boolean[size];

        for (int i = 0; i < size; i++) {
            rowZeroCount[i] = countZeros(getRow(i));
            colZeroCount[i] = countZeros(getCol(i));
        }

        for (int i = 0; i < size; i++) {
            int rowIndex = getIndexWithMinZeros(rowZeroCount, isRowAssigned);
            int colIndex = getIndexWithMinZeros(colZeroCount, isColAssigned);

            if (rowZeroCount[rowIndex] < colZeroCount[colIndex]) {
                colIndex = getIndexOfZero(getRow(rowIndex), isColAssigned);
            } else {
                rowIndex = getIndexOfZero(getCol(colIndex), isRowAssigned);
            }

            assignment[i][0] = rowIndex;
            assignment[i][1] = colIndex;
            isRowAssigned[rowIndex] = true;
            isColAssigned[colIndex] = true;
        }

        return assignment;
    }

    private int getIndexWithMinZeros(int[] zeroCount, boolean[] unavailable) {
        int index = 0;
        int count = Integer.MAX_VALUE;

        for (int i = 0; i < size; i++) {
            if (zeroCount[i] < count && !unavailable[i]) {
                count = zeroCount[i];
                index = i;
            }
        }

        return index;
    }

    private int getIndexWithMaxZeros(int[] zeroCount, boolean[] unavailable) {
        int index = 0;
        int count = Integer.MIN_VALUE;

        for (int i = 0; i < size; i++) {
            if (zeroCount[i] > count && !unavailable[i]) {
                count = zeroCount[i];
                index = i;
            }
        }

        return index;
    }

    private int getIndexOfZero(double[] values, boolean[] unavailable) {
        for (int i = 0; i < size; i++) {
            if (values[i] == 0 && !unavailable[i]) {
                return i;
            }
        }
        throw new IllegalStateException("No available zero was found");
    }
}
