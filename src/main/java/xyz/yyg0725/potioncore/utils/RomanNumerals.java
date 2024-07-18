package xyz.yyg0725.potioncore.utils;

public class RomanNumerals {

    public static String toRoman(int number) {
        if (number <= 0) {
            throw new IllegalArgumentException("Number must > 0");
        } else if (number > 3999) {
            return String.valueOf(number);
        }

        String[] thousands = {"", "M", "MM", "MMM"};
        String[] hundreds = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String[] tens = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String[] units = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};

        return thousands[number / 1000] +
                hundreds[(number % 1000) / 100] +
                tens[(number % 100) / 10] +
                units[number % 10] + "(" + number + ")";
    }
}
