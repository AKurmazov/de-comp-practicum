package numerical_methods;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;

import java.util.ArrayList;


public class Main extends Application {

    @Override public void start(Stage stage) {
        drawPlots(stage, -4, 1, 4, 30, 110, 130);
    }

    private void drawPlots(Stage stage, double x0, double y0, double X, int N, int NMin, int NMax) {
        stage.setTitle("Computational practicum");

        Euler euler;
        ImprovedEuler impEuler;
        RungeKutta rungeKutta;

        ArrayList<Double> eulerMaxGTE = new ArrayList<>();
        ArrayList<Double> impEulerMaxGTE = new ArrayList<>();
        ArrayList<Double> rungeKuttaMaxGTE = new ArrayList<>();
        ArrayList<Double> nSeries = new ArrayList<>();
        for (int n = NMin; n <= NMax; n++) {
            nSeries.add((double) n);
            euler = new Euler(x0, y0, X, n);
            impEuler = new ImprovedEuler(x0, y0, X, n);
            rungeKutta = new RungeKutta(x0, y0, X, n);

            euler.generateData();
            impEuler.generateData();
            rungeKutta.generateData();

            eulerMaxGTE.add(euler.getMaxGTE());
            impEulerMaxGTE.add(impEuler.getMaxGTE());
            rungeKuttaMaxGTE.add(rungeKutta.getMaxGTE());
        }

        ArrayList<XYChart.Series> maxGTESeries = new ArrayList<>();
        maxGTESeries.add(createSeries("Euler", nSeries, eulerMaxGTE, 0));
        maxGTESeries.add(createSeries("Improved Euler", nSeries, impEulerMaxGTE, 0));
        maxGTESeries.add(createSeries("Runge Kutta", nSeries, rungeKuttaMaxGTE, 0));

        euler = new Euler(x0, y0, X, N);
        euler.generateData();

        impEuler = new ImprovedEuler(x0, y0 , X, N);
        impEuler.generateData();

        rungeKutta = new RungeKutta(x0, y0 , X, N);
        rungeKutta.generateData();

        ArrayList<XYChart.Series> solutionsSeries = new ArrayList<>();
        solutionsSeries.add(createSeries("Exact", euler.getXs(), rungeKutta.getyExacts(), 0));
        solutionsSeries.add(createSeries("Euler", euler.getXs(), euler.getyApprox(), 0));
        solutionsSeries.add(createSeries("Improved Euler", impEuler.getXs(), impEuler.getyApprox(), 0));
        solutionsSeries.add(createSeries("Runge-Kutta", rungeKutta.getXs(), rungeKutta.getyApprox(), 0));

        ArrayList<XYChart.Series> lteSeries = new ArrayList<>();
        lteSeries.add(createSeries("Euler", euler.getXs(), euler.getLtes(), 1));
        lteSeries.add(createSeries("Improved Euler", impEuler.getXs(), impEuler.getLtes(), 1));
        lteSeries.add(createSeries("Runge-Kutta", rungeKutta.getXs(), rungeKutta.getLtes(), 1));

        LineChart<Number, Number> rungeKuttaLC = createLineChart("Solutions", solutionsSeries, 600, 800);
        LineChart<Number, Number> lteLC = createLineChart("LTE", lteSeries,600, 600);
        LineChart<Number, Number> gteLC = createLineChart("GTE", maxGTESeries, 600, 600);

        TextField textFieldX0 = new TextField(Double.toString(x0));
        TextField textFieldY0 = new TextField(Double.toString(y0));
        TextField textFieldXFinal = new TextField(Double.toString(X));
        TextField textFieldN = new TextField(Integer.toString(N));
        TextField textFieldNMin = new TextField(Integer.toString(NMin));
        TextField textFieldNMax = new TextField(Integer.toString(NMax));

        Label labelX0 = new Label("x0:  ");
        Label labelY0 = new Label("y0:  ");
        Label labelXFinal = new Label("X:    ");
        Label labelN = new Label("N:    "  );
        Label labelNFMin = new Label("NMin:  ");
        Label labelNMax = new Label("NMax:  ");

        HBox hBoxX0 = new HBox();
        hBoxX0.setPadding(new Insets(5, 5, 5, 10));
        hBoxX0.getChildren().addAll(labelX0, textFieldX0);
        HBox hBoxY0 = new HBox();
        hBoxY0.setPadding(new Insets(5, 5, 5, 10));
        hBoxY0.getChildren().addAll(labelY0, textFieldY0);
        HBox hBoxXFinal = new HBox();
        hBoxXFinal.setPadding(new Insets(5, 5, 5, 10));
        hBoxXFinal.getChildren().addAll(labelXFinal, textFieldXFinal);
        HBox hBoxN = new HBox();
        hBoxN.setPadding(new Insets(5, 5, 5, 10));
        hBoxN.getChildren().addAll(labelN, textFieldN);
        HBox hBoxNMin = new HBox();
        hBoxNMin.setPadding(new Insets(5, 5, 5, 10));
        hBoxNMin.getChildren().addAll(labelNFMin, textFieldNMin);
        HBox hBoxNMax = new HBox();
        hBoxNMax.setPadding(new Insets(5, 5, 5, 10));
        hBoxNMax.getChildren().addAll(labelNMax, textFieldNMax);

        Button submitButton = new Button("Compute");
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                double x0 = Double.parseDouble(textFieldX0.getText());
                double y0 = Double.parseDouble(textFieldY0.getText());
                double X = Double.parseDouble(textFieldXFinal.getText());
                int N = Integer.parseInt(textFieldN.getText());
                int NMin = Integer.parseInt(textFieldNMin.getText());
                int NMax = Integer.parseInt(textFieldNMax.getText());

                drawPlots(stage, x0, y0, X, N, NMin, NMax);
            }
        });

        VBox vBoxInputs = new VBox();
        vBoxInputs.setPadding(new Insets(25, 5 , 5, 50));
        vBoxInputs.getChildren().addAll(hBoxX0, hBoxY0, hBoxXFinal, hBoxN, hBoxNMin, hBoxNMax, submitButton);

        FlowPane flowPaneSolutions = new FlowPane();
        flowPaneSolutions.getChildren().addAll(rungeKuttaLC, vBoxInputs);

        FlowPane flowPaneErrors = new FlowPane();
        flowPaneErrors.getChildren().addAll(lteLC, gteLC);

        TabPane tabPane = new TabPane();
        Tab tab1 = new Tab("Solutions", flowPaneSolutions);
        Tab tab2 = new Tab("Errors", flowPaneErrors);
        tabPane.getTabs().add(tab1);
        tabPane.getTabs().add(tab2);

        VBox vBoxTabs = new VBox(tabPane);
        Scene scene  = new Scene(vBoxTabs,1200,700);

        stage.setScene(scene);
        stage.show();
    }

    private  XYChart.Series createSeries(String title, ArrayList<Double> xData, ArrayList<Double> yData, int offset) {
        XYChart.Series series = new XYChart.Series();
        series.setName(title);

        for (int i = offset; i < yData.size(); ++i) {
            series.getData().add(new XYChart.Data<Double, Double>(xData.get(i), yData.get(i)));
        }

        return series;
    }

    private LineChart<Number, Number> createLineChart(String title, ArrayList<XYChart.Series> all_series, int height, int width) {
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("X values");
        yAxis.setLabel("Y values");

        final LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis,yAxis);

        lineChart.setTitle(title);
        lineChart.setMinHeight(height);
        lineChart.setMinWidth(width);

        for (XYChart.Series series : all_series) {
            lineChart.getData().add(series);
        }

        return lineChart;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
