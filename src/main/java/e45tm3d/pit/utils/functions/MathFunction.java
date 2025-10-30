package e45tm3d.pit.utils.functions;

import java.util.Random;

public class MathFunction {

    public static String intToRoman(int num) {
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        StringBuilder roman = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            while (num >= values[i]) {
                roman.append(symbols[i]);
                num -= values[i];
            }
        }

        return roman.toString();
    }

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