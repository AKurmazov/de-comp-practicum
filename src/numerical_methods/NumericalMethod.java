package numerical_methods;

import java.lang.Math;
import java.util.ArrayList;

public class NumericalMethod {

    protected double x0, y0, X, h, con;
    protected ArrayList<Double> yExacts;
    protected ArrayList<Double> yApprox;
    protected ArrayList<Double> ltes;
    protected ArrayList<Double> gtes;
    protected ArrayList<Double> xs;

    NumericalMethod() {}

    protected void initData() {
        yExacts = new ArrayList<>();
        yExacts.add(y0);
        yApprox = new ArrayList<>();
        yApprox.add(y0);
        ltes = new ArrayList<>();
        ltes.add(0.0);
        gtes = new ArrayList<>();
        gtes.add(0.0);
        xs = new ArrayList<>();
        xs.add(x0);
    }

    protected double funcApprox(double x, double y) {
        return Math.pow(y, 2) * Math.exp(x) - 2 * y;
    }

    protected double funcExact(double x) {
        return 1.0 / (Math.exp(x) * (1 + Math.exp(x) * con));
    }

    protected double funcConst(double x, double y) {
        return (1 - y * Math.exp(x)) / Math.exp(2 * x);
    }

    protected double computeGTE(int id) {
        return Math.abs(yExacts.get(id) - yApprox.get(id));
    }

    public ArrayList<Double> getyExacts() {
        return yExacts;
    }

    public ArrayList<Double> getyApprox() {
        return yApprox;
    }

    public ArrayList<Double> getLtes() {
        return ltes;
    }

    public ArrayList<Double> getXs() {
        return xs;
    }

    public double getMaxGTE() {
        double max = 0.0;
        for (double gte : gtes)
            if (gte > max)
                max = gte;

        return max;
    }

}

