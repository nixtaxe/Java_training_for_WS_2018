package graphic;

import com.company.Approx;

import java.io.InputStream;
import java.util.Locale;
import java.util.Scanner;

public class Model {
    private int approx_degree;
    protected int points_n;
    private double[] X;
    private double[] Y;
    Approx approx;

    public Model(InputStream inp){
        Scanner scan = new Scanner(inp);
        scan.useLocale(Locale.US);

        points_n = scan.nextInt();
        X = new double[points_n + 1];
        for (int i = 0; i <= points_n; ++i)
            X[i] = scan.nextDouble();

        Y = new double[points_n + 1];
        for (int i = 0; i <= points_n; ++i)
            Y[i] = scan.nextDouble();

        approx_degree = points_n - 1;
        approx = new Approx(points_n - 1, X, Y);
    }

    // Getters
    public int getMaxApproxDegree(){
        return points_n - 1;
    }

    public double[] getX(){
        return X.clone();
    }

    public double[] getY(){
        return Y.clone();
    }

    public double getApproxValue(double x){
        return approx.getValue(x);
    }

    // Setters
    public void setApproxDeg(int degree){
        approx_degree = degree;
        approx = new Approx(degree, X, Y);
    }

    public void setApprox(double[] new_Y) {
        Y = new_Y;
        approx = new Approx(approx_degree, X.clone(), Y.clone());
    }

}
