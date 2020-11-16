package algorithmen;

public class GaußElimination {
    private static final double EPSILON = 0.000001;
    private double[][] M;
    // Gaussian elimination with partial pivoting
    public GaußElimination(double[][] M) { this.M = M;}
    public double[] lsolve() {
        final long startTime = System.nanoTime();

        // matrix A
        double[][] A = new double[M.length][M[0].length-1];
        for(int i=0; i<M.length; i++) {
            for(int j=0; j<M[0].length-1; j++) {
                A[i][j] = M[i][j];
            }
        }
        double[] b = new double[M.length];
        for(int i=0; i<M.length; i++) {
            b[i] = M[i][M.length];
        }
        int n = b.length;

        for (int p = 0; p < n; p++) {

            // find pivot row and swap
            int max = p;
            for (int i = p + 1; i < n; i++) {
                if (Math.abs(A[i][p]) > Math.abs(A[max][p])) {
                    max = i;
                }
            }
            double[] temp = A[p]; A[p] = A[max]; A[max] = temp;
            double   t    = b[p]; b[p] = b[max]; b[max] = t;

            // singular or nearly singular
            if (Math.abs(A[p][p]) <= EPSILON) {
                throw new ArithmeticException("Matrix is singular or nearly singular");
            }

            // pivot within A and b
            for (int i = p + 1; i < n; i++) {
                double alpha = A[i][p] / A[p][p];
                b[i] -= alpha * b[p];
                for (int j = p; j < n; j++) {
                    A[i][j] -= alpha * A[p][j];
                }
            }
        }

        // back substitution
        double[] x = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < n; j++) {
                sum += A[i][j] * x[j];
            }
            x[i] = (b[i] - sum) / A[i][i];
            System.out.println("X["+ i +"]: "+ x[i] + " ");
        }
        System.out.println();
        final long duration = System.nanoTime() - startTime;
        System.out.println("Dauer: " + duration +" nanoseconds");
        return x;
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
}
