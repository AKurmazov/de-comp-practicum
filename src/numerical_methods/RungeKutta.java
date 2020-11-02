package numerical_methods;

import java.lang.Math;
import java.util.ArrayList;

public class RungeKutta extends NumericalMethod {

    RungeKutta(double x0, double y0, double X, int N) {
        this.x0 = x0;
        this.y0 = y0;
        this.X = X;
        this.h = (X - x0) / (double) N;
        this.con = funcConst(x0, y0);
        this.initData();
    }

    public void generateData() {
        double xCur = x0;
        double xEnd = X;

        int i = 0;
        double k1a = 0, k2a = 0, k3a = 0, k4a = 0;
        double k1e = 0, k2e = 0, k3e = 0, k4e = 0;
        while (xCur <= xEnd) {
            if (i > 0) {
                yApprox.add(yApprox.get(i - 1) + h / 6 * (k1a + 2 * (k2a + k3a) + k4a));
                yExacts.add(funcExact(xs.get(i)));
                ltes.add(computeLTE(i, k1e, k2e, k3e, k4e));
                gtes.add(computeGTE(i));
            }

            xCur = xCur + h;
            xs.add(xCur);

            k1a = k1(i, yApprox);
            k2a = k2(i, yApprox, k1a);
            k3a = k3(i, yApprox, k2a);
            k4a = k4(i, yApprox, k3a);

            k1e = k1(i, yExacts);
            k2e = k2(i, yExacts, k1e);
            k3e = k3(i, yExacts, k2e);
            k4e = k4(i, yExacts, k3e);

            i += 1;
        }

    }

    private double k1(int id, ArrayList<Double> ySource) {
        return funcApprox(xs.get(id), ySource.get(id));
    }

    private double k2(int id, ArrayList<Double> ySource, double k1) {
        return funcApprox(xs.get(id) + h / 2, ySource.get(id) + h / 2 * k1);
    }

    private double k3(int id, ArrayList<Double> ySource, double k2) {
        return funcApprox(xs.get(id) + h / 2, ySource.get(id) + h / 2 * k2);
    }

    private double k4(int id, ArrayList<Double> ySource, double k3) {
        return funcApprox(xs.get(id) + h, ySource.get(id) + h * k3);
    }

    private double computeLTE(int id, double k1, double k2, double k3, double k4) {
        return Math.abs(yExacts.get(id) - (yExacts.get(id - 1) + h / 6 * (k1 + 2 * (k2 + k3) + k4)));
    }

}
