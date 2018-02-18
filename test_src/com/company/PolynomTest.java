package com.company;

import org.junit.Test;

import static org.junit.Assert.*;

public class PolynomTest {
    private final double eps = 0.00001;

    @Test
    public void getValue() {
        Polynom p = new Polynom();
        p.n = 3;
        p.coefs = new double[]{-15, 10, 0, 7};
        double res = p.getValue(1);
        assertEquals(-15 + 10 + 0 + 7, res, eps);

        p.n = 3;
        p.coefs = new double[]{1, 2, 3, 4};
        res = p.getValue(2);
        assertEquals(1 + 2*2 + 4*3 + 8*4, res, eps);
    }
}