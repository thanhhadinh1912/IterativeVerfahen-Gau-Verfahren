package algorithmen;

import java.util.Arrays;

public class Jacobi {
    public static final int MAX_ITERATIONS = 100;
    private double[][] M;
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

    public Jacobi(double [][] matrix){M = matrix; }
    public void solve() {
        final long startTime = System.nanoTime();

        int iterations = 0;
        int n = M.length;
        double epsilon = 1e-6;
        double[] X = new double[n]; // Approximations
        double[] P = new double[n]; // Prev
        Arrays.fill(X, 0);
        Arrays.fill(P, 0);

        while (true) {
            for (int i = 0; i < n; i++) {
                double sum = M[i][n]; // b_n

                for (int j = 0; j < n; j++)
                    if (j != i)
                        sum -= M[i][j] * P[j];

                // Update x_i but it's no used in the next row calculation
                // but up to next iteration of the method
                X[i] = 1/M[i][i] * sum;
            }

            System.out.print("X_" + iterations + " = {");
            for (int i = 0; i < n; i++)
                System.out.print(X[i] + " ");
            System.out.println("}");

            iterations++;
            if (iterations == 1) continue;

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
