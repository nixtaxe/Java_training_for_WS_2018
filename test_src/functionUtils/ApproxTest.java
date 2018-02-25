package functionUtils;

import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.*;

public class ApproxTest {
    private final double eps = 0.00001;

    @Test
    public void testCreationFromFile(){
        InputStream inp = this.getClass().getResourceAsStream("Approx_input.txt");
        Approx a = new Approx(inp);
        assertArrayEquals(new double[]{0, 1}, a.coefs, eps);
    }

    @Test
    public void testApproximationToParabola() {
        Approx a = new Approx(2, new double[]{0, 1, 2, 3, 4, 5},
                                        new double[]{-100, -90, -76, -52, -12, 50});
        assertArrayEquals(new double[]{-97, -3.7, 6.5}, a.coefs, eps);
    }

    @Test
    public void testApproximationToLine(){
        Approx a = new Approx(1, new double[]{1, 2, 3}, new double[]{1, 2, 3});
        assertArrayEquals(new double[]{0, 1}, a.coefs, eps);
    }

}