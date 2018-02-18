package com.company;
import com.company.Solver;
import com.company.Polynom;

import java.io.FileReader;
import java.util.Scanner;

public class Interp extends Polynom {
    Solver s;

    public Interp(double[] X, double[] F){
        n = X.length - 1;
        createMatrixSolver((double[]) X.clone(), (double[])F.clone());
        interpolate((double[])F.clone());
    }

    public Interp(FileReader inp){
        Scanner scan = new Scanner(inp);

        n = scan.nextInt();
        double[] X = new double[n + 1];
        for (int i = 0; i <= n; ++i)
            X[i] = scan.nextDouble();

        double[] F = new double[n + 1];
        for (int i = 0; i <= n; ++i)
            F[i] = scan.nextDouble();

        createMatrixSolver((double[]) X.clone(), (double[])F.clone());
        interpolate((double[])F.clone());
    }

    private void createMatrixSolver(double[] X, double[] F){
        double[][] A = new double[n + 1][n + 1];
        double cur_x = 1;
        for (int i = 0; i <= n; ++i, cur_x = 1)
            for (int j = 0; j <= n; ++j, cur_x *= X[i])
                A[i][j] = cur_x;
        s = new Solver(n + 1, A);
    }

    public void interpolate(double[] F){
        coefs = s.getSolve((double[])F.clone());
    }

    public String toString(){
        return "Interpolation\n" + super.toString();
    }

}
