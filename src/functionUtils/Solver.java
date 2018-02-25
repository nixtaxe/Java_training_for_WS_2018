package functionUtils;

public class Solver {
    static int n;
    static double[][] P;
    static double[][] T;

    public Solver(int dimension, double[][] matrix) {
        n = dimension;
        T = matrix;
        P = new double[n][n];
        double d;

        // LU-разложение
        for (int k = 0; k < n; ++k) {
            P[k][k] = 1;
            for (int i = k + 1; i < n; ++i) {
                d = T[i][k] / T[k][k];
                P[i][k] = d;
                for (int j = k; j < n; ++j)
                    T[i][j] -= T[k][j] * d;
            }
        }
    }

    public double[] getSolve(double[] F){
        for (int j = 0; j < n; ++j)
            for (int i = j + 1; i < n; ++i)
                F[i] -= F[j] * P[i][j];

        double[] res = new double[n];
        double sum;
        for (int i = n - 1; i >= 0; --i) {
            sum = 0;
            for (int j = n - 1; j > i; --j)
                sum += res[j] * T[i][j];
            res[i] = (F[i] - sum) / T[i][i];
        }

        return res;
    }

    public String toString(){
        String res = "";
        res += "P array\n";
        for (double[] line: P) {
            for (double val: line)
                res += String.format("%10.5f", val);
            res += "\n";
        }

        res += "T array\n";
        for (double[] line: T) {
            for (double val: line)
                res += String.format("%10.5f", val);
            res += "\n";
        }

        return res;
    }
}
