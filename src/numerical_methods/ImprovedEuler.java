package numerical_methods;

import java.lang.Math;
import java.util.ArrayList;

public class ImprovedEuler extends  NumericalMethod {

    ImprovedEuler(double x0, double y0, double X, int N) {
        this.x0 = x0;
        this.y0 = y0;
        this.X = X;
        this.h = (X - x0) / ((double) N);;
        this.con = funcConst(x0, y0);
        this.initData();
    }

    public void generateData() {
        double xCur = x0;
        double xEnd = X;

        ArrayList<Double> fs = new ArrayList<>();

        int i = 0;
        while (xCur <= xEnd) {
            if (i > 0) {
                yApprox.add(yApprox.get(i - 1) + h * (fs.get(i - 1) + funcApprox(xCur, yApprox.get(i-1) + h * fs.get(i-1))));
                yExacts.add(funcExact(xs.get(i)));
                ltes.add(computeLTE(i));
                gtes.add(computeGTE(i));
            }

            xCur = xCur + h;
            xs.add(xCur);
            fs.add(funcApprox(xs.get(i), yApprox.get(i)));
            i += 1;
        }

    }

    private double computeLTE(int id) {
        return Math.abs(yExacts.get(id) - (yExacts.get(id - 1) + h * (funcApprox(xs.get(id - 1),
                yExacts.get(id - 1)) + funcApprox(xs.get(id), yExacts.get(id - 1) + h * funcApprox(xs.get(id - 1),
                yExacts.get(id - 1)))) / 2));
    }

}
