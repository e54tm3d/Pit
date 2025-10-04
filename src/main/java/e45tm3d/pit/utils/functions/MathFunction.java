package e45tm3d.pit.utils.functions;

import java.util.Random;

public class MathFunction {

    public static double randomDouble(double max, double min) {

        Random r = new Random();

        if (max <= min) {
            return min;
        }

        return min + (max - min) * r.nextDouble();
    }

    public static int time(int hour, int minute, int second) {
        int seconds = 0;
        seconds += second * 20;
        seconds += minute * 60 * 20;
        seconds += hour * 60 * 60 * 20;
        return seconds;
    }

}