package algorithmen;

import java.util.Arrays;

public class GaußSeidel {
    public static final int MAX_ITERATIONS = 100;
    private double[][] M;


    public GaußSeidel(double [][] matrix){M = matrix; }

    //testen ob Matrix A diagonaldominant ist
    public boolean checkDominant(){
        boolean e = true;
        for (int i = 0; i < M.length; ++i){
            double max = 0;
            double diagEl = Math.abs(M[i][i]);
            for (int j = 0; j < M[0].length-1; ++j){
                if (i == j) { continue; }
                if (max<Math.abs(M[i][j])){
                    max = Math.abs(M[i][j]);
                }
            }
            if (max > diagEl) {
                e = false;

            }
        }
        return e;
    }
    /**
     * Berechnen das Maschinen-Epsilon
     */
    private double calculateMachineEpsilon() {
        double machEps = 1.0;

        do {
            machEps /= 2.0;
        } while ((double) (1.0 + (machEps / 2.0)) != 1.0);

        return machEps;
    }

    //printMatrix
    public void print(){
        int n = M.length;
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n + 1; j++)
                System.out.print(M[i][j] + " ");
            System.out.println();
        }
    }
    public void solve(){
        final long startTime = System.nanoTime();
        int iterations = 0;
        int n = M.length;
        double epsilon = 0.000001;
        double[] X = new double[n]; // Approximations
        double[] P = new double[n]; // previous Variable Values
        Arrays.fill(X, 0);

        while (true)
        {
            for (int i = 0; i < n; i++)
            {
                double sum = M[i][n]; // b_n

                for (int j = 0; j < n; j++)
                    if (j != i)
                        sum -= M[i][j] * X[j];

                // Update x_i to use in the next row calculation
                X[i] = 1/M[i][i] * sum;
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
}
