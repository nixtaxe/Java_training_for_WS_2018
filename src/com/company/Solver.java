package com.company;

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

    public String toString(){
        String res = "";
        res += "P array\n";

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j)
                res += String.format("%10.5f", P[i][j]);
            res += "\n";
        }

        res += "T array\n";

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j)
                res += String.format("%10.5f", T[i][j]);
            res += "\n";
        }

        return res;
    }
}
