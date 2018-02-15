package com.company;
import com.company.Solver;

import java.io.FileReader;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception{
        FileReader reader = new FileReader("C:\\Projects\\Java_training_for_WS_2018\\src\\input.txt");
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

        //Дебаг
        System.out.print(s);
    }
}
