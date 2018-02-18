package com.company;

public class Polynom {
    int n;
    // Коэффициенты располагаются от младшей степени к старшей
    double[] coefs;

    public double getValue(double x){
        final double eps = 0.00001;
        double x_in_degree = Math.pow(x, 0);
        double res = 0;
        for (int i = 0; i <= n; ++i){
            res += coefs[i] * x_in_degree;
            x_in_degree *= x;
        }

        return res;
    }

    public String toString() {
        String res = "";
        for (double val: coefs)
            res += String.format("%10.5f", val);
        res += "\n";
        return res;
    }
}
