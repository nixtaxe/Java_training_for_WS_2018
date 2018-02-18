package com.company;

import org.junit.Test;

import java.io.File;
import java.io.FileReader;

import static org.junit.Assert.*;

public class SolverTest {
    private final double eps = 0.00001;

    @Test
    public void testXEqualsF() {
        Solver s = new Solver(2, new double[][]{{1, 0},
                                                         {0, 1}});
        double[] res = s.getSolve(new double[]{-0.5,
                                                11});
        assertArrayEquals(new double[]{-0.5, 11}, res, eps);
    }

    @Test
    public void testSimpleMatrixTransformation(){
        Solver s = new Solver(2, new double[][]{{2, 1},
                                                         {4, 7}});
        double[] res = s.getSolve(new double[] {-1,
                                                13});
        assertArrayEquals(new double[]{-2, 3}, res, eps);
    }
}