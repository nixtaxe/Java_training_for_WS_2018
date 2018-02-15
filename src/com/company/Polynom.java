package com.company;

public class Polynom {
    int n;
    double[] coefs;

    double getValue(double x){
        double x_in_degree = 1;
        double res = 0;
        for (int i = 0; i <= n; ++i){
            res += coefs[i] * x_in_degree;
            x_in_degree *= x;
        }

        return res;
    }
}
