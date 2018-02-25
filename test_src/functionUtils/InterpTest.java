package functionUtils;

import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.*;

public class InterpTest {
    private final double eps = 0.00001;

    @Test
    public void testCreationFromFile(){
        InputStream inp = this.getClass().getResourceAsStream("Interp_input.txt");
        Interp a = new Interp(inp);
        assertArrayEquals(new double[]{0, 1, 0}, a.coefs, eps);
    }

    @Test
    public void testInterpolateALine() {
        Interp i = new Interp(new double[]{1, 2, 3}, new double[]{1, 2, 3});
        assertArrayEquals(new double[]{0, 1, 0}, i.coefs, eps);
    }

    @Test
    public void testInterpolateAParabola() {
        Interp i = new Interp(new double[]{1, 2, 3}, new double[]{0, 3, 8});
        assertArrayEquals(new double[]{-1, 0, 1}, i.coefs, eps);
    }
}