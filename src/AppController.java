package sample;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class AppController {

    public void plot(Stage newStage, double x0, double y0, double X, double step){

        NumberAxis x = new NumberAxis();
        NumberAxis y = new NumberAxis();

        LineChart<Number, Number> MyChart = new LineChart<Number, Number>(x,y);
        XYChart.Series function,euler,improved_euler,runge_kutta;

        XYChart.Series euler_error,impr_error, runge_error;

        int n = (int) ((X-x0)/step);

        double[] axis_x = new double[n];
        double[] exact_func = new double[n];
        double[] euler_meth = new double[n];
        double[] impr_euler = new double[n];
        double[] runge = new double[n];
        double[] euler_er = new double[n];
        double[] impr_er = new double[n];
        double[] runge_er = new double[n];

        axis_x[0] = x0;
        exact_func[0] = y0;
        runge[0] = exact_func[0];
        euler_meth[0] = exact_func[0];
        impr_euler[0] = exact_func[0];
        euler_er[0] = 0;
        impr_er[0] = 0;
        runge_er[0] = 0;

        for (int i = 1; i < n; i++) {
            axis_x[i] = axis_x[i - 1] + step;
        }

        double k0;
        double k1;
        double k0_r;
        double k1_r;
        double k2_r;
        double k3_r;

        for (int i = 1; i < n; i++)
        {
            exact_func[i] = function_orig(axis_x[i]);

            euler_meth[i] = euler_meth[i-1] + step * f(axis_x[i-1],euler_meth[i-1]);

            k0 = f(axis_x[i-1], impr_euler[i-1]);
            k1 = f(axis_x[i-1] + step,impr_euler[i-1] + step*k0);
            impr_euler[i] = impr_euler[i-1] + step * (k0 + k1) / 2;

            k0_r = f(axis_x[i-1], runge[i-1]);
            k1_r = f(axis_x[i-1] + step/2, runge[i-1] + step*k0/2);
            k2_r = f(axis_x[i-1] + step/2, runge[i-1] + step*k1_r/2);
            k3_r = f(axis_x[i-1] + step, runge[i-1] + step*k2_r);
            runge[i] = runge[i-1] + step*(k0_r + 2*k1_r + 2*k2_r + k3_r)/6;
        }

        for (int i = 1; i < n; i++)
        {
            euler_er[i] = Math.abs(exact_func[i] - euler_meth[i]);
            impr_er[i] = Math.abs(exact_func[i] - impr_euler[i]);
            runge_er[i] = Math.abs(exact_func[i] - runge[i]);
        }

        function = new XYChart.Series();
        euler = new XYChart.Series();
        improved_euler = new XYChart.Series();
        runge_kutta = new XYChart.Series();
        function.setName("Function");
        euler.setName("Euler");
        improved_euler.setName("Improved Euler");
        runge_kutta.setName("Runge-Kutta");

        euler_error = new XYChart.Series();
        euler_error.setName("Euler_error");
        impr_error = new XYChart.Series();
        impr_error.setName("Impr_error");
        runge_error = new XYChart.Series();
        runge_error.setName("Runge_error");

        for (int i = 0; i < n; i++)
        {
            function.getData().add(new XYChart.Data(axis_x[i],exact_func[i]));
            euler.getData().add(new XYChart.Data(axis_x[i],euler_meth[i]));
            improved_euler.getData().add(new XYChart.Data(axis_x[i],impr_euler[i]));
            runge_kutta.getData().add(new XYChart.Data(axis_x[i],runge[i]));
            euler_error.getData().add(new XYChart.Data(axis_x[i],euler_er[i]));
            impr_error.getData().add(new XYChart.Data(axis_x[i],impr_er[i]));
            runge_error.getData().add(new XYChart.Data(axis_x[i],runge_er[i]));
        }

        Scene scene = new Scene(MyChart,700,700);
        MyChart.getData().addAll(euler_error,impr_error, runge_error);
        newStage.setScene(scene);

        newStage.show();

    }

    private double function_orig(double x){
        return 5/2 * Math.pow(Math.E,2*x-2) * x + x/2;
    }

    private double f(double x,double y)
    {
        return -x + y*(2*x+1)/x;
    }
}
