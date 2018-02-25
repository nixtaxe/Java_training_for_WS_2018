package functionUtils;

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

    @Test
    public void testGetValueOfAnPointsArray(){
        Polynom p = new Polynom();
        p.n = 1;
        p.coefs = new double[]{0.0, 1.0};

        double[] res = {0.0, 0.5, 1.0, 1.5, 2.0};
        double[] actual = new double[5];
        int i = 0;
        for (double x = 0.0; x <= 2; x += 0.5, ++i)
            actual[i] = p.getValue(x);
        assertArrayEquals(res, actual, eps);
    }

}