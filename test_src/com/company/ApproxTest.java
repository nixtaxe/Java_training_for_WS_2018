package com.company;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ApproxTest {
    private final double eps = 0.00001;

    @Before
    public void testCreationFromFile(){
        //...
    }

    @Test
    public void testApproximationToParabola() {
        Approx a = new Approx(2, new double[]{0, 1, 2, 3, 4, 5},
                                        new double[]{-100, -90, -76, -52, -12, 50});
        assertArrayEquals(new double[]{6.5, -3.7, -97}, a.coefs, eps);
    }

    @Test
    public void testApproximationToLine(){
        Approx a = new Approx(1, new double[]{1, 2, 3}, new double[]{1, 2, 3});
        assertArrayEquals(new double[]{1, 0}, a.coefs, eps);
    }

}