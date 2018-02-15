package com.company;
import com.company.Solver;
import com.company.Interp;

import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception{
        FileReader reader = new FileReader("src\\Solver_input.txt");
        Scanner scan = new Scanner(reader);

        // Формат входного файла: размерность матрицы, сама матрица без столбца свободных значений
        // и столбец свободных значений
        int dim;
        dim = scan.nextInt();

        double[][] A = new double[dim][dim];
        for (int i = 0; i < dim; ++i)
            for (int j = 0; j < dim; ++j)
                A[i][j] = scan.nextDouble();

        // Проверка класса Solver
        Solver s = new Solver(dim, A);
        System.out.print(s);

        double[] F = new double[dim];
        for (int i = 0; i < dim; ++i)
            F[i] = scan.nextDouble();

        double[] res = s.getSolve(F);
        System.out.print("Solution\n");
        for (double val: res)
            System.out.print(String.format("%10.5f", val));
        System.out.print("\n");

        // Проверка класса Interp
        FileReader inp = new FileReader("src\\Interp_input.txt");
        Interp x = new Interp(inp);
        System.out.print(x);
        Interp y = new Interp(new double[]{1, 2, 3}, new double[]{1, 2, 3});
        System.out.print(y);

        // Проверка метода getValue в Polynom
        System.out.println("getValue check: " + String.format("%10.5f", x.getValue(5)));

    }
}
