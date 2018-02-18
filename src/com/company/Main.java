package com.company;
import com.company.Solver;
import com.company.Interp;
import com.company.Approx;

import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception{
        // Проверка класса Interp
        FileReader inp = new FileReader("src\\Interp_input.txt");
        Interp x = new Interp(inp);
        System.out.print(x);
        Interp y = new Interp(new double[]{1, 2, 3}, new double[]{1, 2, 3});
        System.out.print(y);

        // Проверка метода getValue в Polynom
        System.out.println("getValue check: " + String.format("%10.5f", x.getValue(5)));

        // Проверка класса Approx
        inp = new FileReader("src\\Approx_input.txt");
        Approx a = new Approx(inp);
        System.out.print(a);

    }
}
