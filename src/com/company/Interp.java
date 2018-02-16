package com.company;
import com.company.Solver;
import com.company.Polynom;

import java.io.FileReader;
import java.util.Scanner;

public class Interp extends Polynom {
    double[] X;
    double[] F;

    public Interp(double[] cur_X, double[] cur_F){
        n = cur_X.length - 1;
        X = cur_X;
        F = cur_F;
        interpolate();
    }

    public Interp(FileReader inp){
        Scanner scan = new Scanner(inp);

        n = scan.nextInt();
        X = new double[n + 1];
        for (int i = 0; i <= n; ++i)
            X[i] = scan.nextDouble();

        F = new double[n + 1];
        for (int i = 0; i <= n; ++i)
            F[i] = scan.nextDouble();

        interpolate();
    }

    private void interpolate(){
        double[][] A = new double[n + 1][n + 1];
        double cur_x = 1;
        for (int i = 0; i <= n; ++i, cur_x = 1)
            for (int j = 0; j <= n; ++j, cur_x *= X[i])
                A[i][j] = cur_x;

        Solver s = new Solver(n + 1, A);
        coefs = s.getSolve(F);
    }

    public String toString(){
        return "Interpolation\n" + super.toString();
    }

}
