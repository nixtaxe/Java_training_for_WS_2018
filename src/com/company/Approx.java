package com.company;

import com.company.Solver;
import java.io.FileReader;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;

public class Approx extends Polynom {
    int m;
    Solver s;
    double[][] X_in_degree;

    public Approx(int cur_n, double[] X, double[] F){
        n = cur_n;
        m = X.length - 1;
        createMatrixSolver((double[]) X.clone(), (double[])F.clone());
        approximate((double[])F.clone());
    }

    public Approx(InputStream inp){
        Scanner scan = new Scanner(inp);

        String line = scan.nextLine();
        n = Integer.parseInt(line);
        line = scan.nextLine();
        String[] str_X = line.split(" ");
        m = str_X.length - 1;
        double[] X = new double[m + 1];
        for (int i = 0; i <= m; ++i)
            X[i] = Double.parseDouble(str_X[i]);

        line = scan.nextLine();
        String[] str_F = line.split(" ");
        double[] F = new double[m + 1];
        for (int i = 0; i <= m; ++i)
            F[i] = Double.parseDouble(str_F[i]);

        createMatrixSolver((double[]) X.clone(), (double[])F.clone());
        approximate((double[])F.clone());
    }

    private void createMatrixSolver(double[] X, double[] F){
        double[][] A = new double[n + 1][n + 1];
        double[] new_F = new double[n + 1];
        int degree = 0;
        X_in_degree = new double[2 * (n + 1)][m + 1];
        Arrays.fill(X_in_degree[0], 1);

        for (int k = n; k >= 0; --k ){
            for (int i = 0; i <= m; ++i)
                A[n][k] += X_in_degree[degree][i];
            
            for (int i = 0; i <= m; ++i)
                new_F[k] += X_in_degree[degree][i] * F[i];
            
            for (int i = n - 1, j = k + 1; i >= 0 && j <= n; --i, ++j)
                A[i][j] = A[n][k];
            
            degree += 1;
            X_in_degree[degree] = (double[])X_in_degree[degree-1].clone();
            for (int i = 0; i <= m; ++i)
                X_in_degree[degree][i] *= X[i];
            
        }

        for (int k = n - 1; k >= 0; --k){
            for (int i = 0; i <= m; ++i)
                A[k][0] += X_in_degree[degree][i];
            
            for (int i = k - 1, j = 1; i >= 0 && j <= n; --i, ++j)
                A[i][j] = A[k][0];
            
            degree += 1;
            X_in_degree[degree] = (double[])X_in_degree[degree-1].clone();
            for (int i = 0; i <= m; ++i)
                X_in_degree[degree][i] *= X[i];
        }

        s = new Solver(n + 1, A);
    }

    public void approximate(double[] F){
        double[] new_F = new double[n + 1];
        for (int i = n, degree = 0; i >= 0; --i, ++degree)
            for (int j = 0; j <= m; ++j)
                new_F[i] += X_in_degree[degree][j] * F[j];

        coefs = new double[n + 1];
        double[] tmp = s.getSolve((double[])new_F.clone());
        // Разворачиваем массив коэффициентов в обратном порядке, так как заполняли особым образом
        for (int i = 0; i <= n; ++i)
            coefs[i] = tmp[n - i];
    }

    public String toString() {
        return "Approximation\n" + super.toString();
    }
}
