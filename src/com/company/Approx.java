package com.company;

import com.company.Solver;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;

public class Approx extends Polynom {
    int m;
    double[] X;
    double[] F;

    public Approx(int cur_n, double[] cur_X, double[] cur_F){
        n = cur_n;
        X = cur_X;
        F = cur_F;
        m = X.length - 1;
        approximate();
    }

    public Approx(FileReader inp){
        Scanner scan = new Scanner(inp);

        String line = scan.nextLine();
        n = Integer.parseInt(line);
        line = scan.nextLine();
        String[] str_X = line.split(" ");
        m = str_X.length - 1;
        X = new double[m + 1];
        for (int i = 0; i <= m; ++i)
            X[i] = Double.parseDouble(str_X[i]);

        line = scan.nextLine();
        String[] str_F = line.split(" ");
        F = new double[m + 1];
        for (int i = 0; i <= m; ++i)
            F[i] = Double.parseDouble(str_F[i]);

        approximate();
    }

    private void approximate(){
        double[][] A = new double[n + 1][n + 1];
        double[] new_F = new double[n + 1];
        double[] cur_x = new double[m + 1];
        Arrays.fill(cur_x, 1);

        for (int k = n; k >= 0; --k ){
            for (int i = 0; i <= m; ++i)
                A[n][k] += cur_x[i];
            for (int i = 0; i <= m; ++i)
                new_F[k] += cur_x[i] * F[i];
            for (int i = n - 1, j = k + 1; i >= 0 && j <= n; --i, ++j)
                A[i][j] = A[n][k];
            for (int i = 0; i <= m; ++i)
                cur_x[i] *= X[i];
        }

        for (int k = n - 1; k >= 0; --k){
            for (int i = 0; i <= m; ++i)
                A[k][0] += cur_x[i];
            for (int i = k - 1, j = 1; i >= 0 && j <= n; --i, ++j)
                A[i][j] = A[k][0];
            for (int i = 0; i <= m; ++i)
                cur_x[i] *= X[i];
        }

        Solver s = new Solver(n + 1, A);
        coefs = s.getSolve(new_F);
    }

    public String toString() {
        return "Approximation\n" + super.toString();
    }
}
