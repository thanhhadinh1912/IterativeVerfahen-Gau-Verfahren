package algorithmen;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;

import java.util.Arrays;

public class SOR {
    public final int MAX_ITERATIONS = 100;
    private double[][] M;

    public SOR(double [][] matrix){M = matrix; }

    private double calculateMachineEpsilon() {
        double machEps = 1.0;

        do {
            machEps /= 2.0;
        } while ((double) (1.0 + (machEps / 2.0)) != 1.0);

        return machEps;
    }
    public double[] eigenwerte(double[][] m) {
        Matrix A = new Matrix(m);
        EigenvalueDecomposition e = A.eig();
        return  e.getRealEigenvalues();
    }
    public double maxeigenwerte(double[] e) {
        double max=0;
        for(int i=0; i<e.length; i++) {
            if(max<Math.abs(e[i])) { max=Math.abs(e[i]);}
        }
        return max;
    }
    public double[][] diagonalmatrix(double[][] a){
        double[][] d = new double[a.length][a[0].length];
        for(int i=0; i<a.length; i++) {
            for(int j=0; j<a[0].length; j++) {
                if(i==j) {
                    d[i][j]=a[i][j];
                }
                else {
                    d[i][j]=0;
                }
            }
        }
        return d;
    }
    public double[][] substraktionmatrix(double[][]d, double[][]a){
        int rows = d.length;
        int cols = d[0].length;
        double diff[][] = new double[rows][cols];

        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                diff[i][j] = d[i][j] - a[i][j];
            }
        }
        return diff;
    }
    public double[][] multiplyMatrices(double[][] firstMatrix, double[][] secondMatrix) {
        double[][] result = new double[firstMatrix.length][secondMatrix[0].length];

        for (int row = 0; row < result.length; row++) {
            for (int col = 0; col < result[row].length; col++) {
                result[row][col] = multiplyMatricesCell(firstMatrix, secondMatrix, row, col);
            }
        }

        return result;
    }
    public double[][] matrixA(double[][] M){
        double[][] a = new double[M.length][M[0].length-1];
        for(int i=0; i<a.length; i++) {
            for(int j=0; j<a[0].length; j++) {
                a[i][j] = M[i][j];
            }
        }
        return a;
    }
    public double multiplyMatricesCell(double[][] firstMatrix, double[][] secondMatrix, int row, int col) {
        double cell = 0;
        for (int i = 0; i < secondMatrix.length; i++) {
            cell += firstMatrix[row][i] * secondMatrix[i][col];
        }
        return cell;
    }
    //inverse matrix of diagonalmatrix
    public double[][] inverse(double[][] diag){
        double[][] inv = new double[diag.length][diag[0].length];
        for(int i=0; i<inv.length; i++) {
            for(int j=0; j<inv[0].length; j++) {
                if(diag[i][j]!=0) {
                    inv[i][j]=1/diag[i][j];

                }
                else {
                    inv[i][j] = 0;
                }
            }
        }
        return inv;
    }
    //find optimal omega
    public double findomega(double[][] M) {
        double omega = 0;
        double[][] a = matrixA(M);
        double[][] d = diagonalmatrix(a);
        double[][] dinverse = inverse(d);
        double[][] dminusa = substraktionmatrix(d,a);
        //D invers * (D-A)
        double[][] mul = multiplyMatrices(dinverse, dminusa);
        //eigenwerte
        double[] e = eigenwerte(mul);
        //find spekraldius
        double spektralradius =  maxeigenwerte(e);
        //omega = 2/(2 - e[0] - e[e.length-1]);
        omega = 2/ (1+Math.sqrt(1-spektralradius));
        return omega;
    }
    public void solve(double omega){
        final long startTime = System.nanoTime();
        int iterations = 0;
        int n = M.length;
        double epsilon = 0.000001;
        double[] X = new double[n]; // Approximations
        double[] P = new double[n]; // previous Variable Values
        Arrays.fill(X, 0);

        System.out.println("omega : " + omega);

        while (true)
        {
            for (int i = 0; i < n; i++)
            {
                double sum = M[i][n]; // b_n

                for (int j = 0; j < n; j++)
                    if (j != i)
                        sum -= M[i][j] * X[j];

                // Update x_i to use in the next row calculation
                X[i] = (1-omega)*X[i] + omega/M[i][i] * sum;
            }

            System.out.print("X_" + iterations + " = {");
            for (int i = 0; i < n; i++)
                System.out.print(X[i] + " ");
            System.out.println("}");

            iterations++;
            if (iterations == 1)
                continue;

            boolean stop = true;
            for (int i = 0; i < n && stop; i++)
                if (Math.abs(X[i] - P[i]) > epsilon)
                    stop = false;

            if (stop || iterations == MAX_ITERATIONS) break;
            P = (double[])X.clone();
        }
        final long duration = System.nanoTime() - startTime;
        System.out.println("Dauer: " + duration +" nanoseconds");

    }
    public void solveopti() {
        double omega = findomega(M);
        solve(omega);
    }
    public void solveunter() {
        solve(0.5);
    }
    public void solveuber() {
        solve(1.15);
    }
}
