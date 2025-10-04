package e45tm3d.pit.utils.functions;

import java.util.Random;

public class MathFunction {

    public static double randomDouble(double max, double min) {
        Random r = new Random();
        // Ensure max is greater than min to avoid negative or zero range
        if (max <= min) {
            return min; // or throw an appropriate exception
        }
        // Use proper nextDouble implementation (returns 0.0 to 1.0) and scale to the desired range
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