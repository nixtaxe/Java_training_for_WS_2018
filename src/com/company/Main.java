package com.company;
import com.company.Solver;

import java.io.FileReader;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception{
        FileReader reader = new FileReader("src\\input.txt");
        Scanner scan = new Scanner(reader);

        // Формат входного файла: размерность матрицы, сама матрица без столбца свободных значений
        // и столбец свободных значений
        int dim;
        dim = scan.nextInt();

        double[][] A = new double[dim][dim];
        for (int i = 0; i < dim; ++i)
            for (int j = 0; j < dim; ++j)
                A[i][j] = scan.nextDouble();

        Solver s = new Solver(dim, A);

        double[] F = new double[dim];
        for (int i = 0; i < dim; ++i)
            F[i] = scan.nextDouble();

        double[] res = s.getSolve(F);

        //Дебаг
        System.out.print(s);
        System.out.print("\nSolution\n");
        for (double val: res)
            System.out.print(String.format("%10.5f", val));
    }
}
