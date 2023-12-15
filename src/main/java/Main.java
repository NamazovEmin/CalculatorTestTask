import java.util.Arrays;
import java.util.Scanner;

public class Main {

    private static String mathOperations = "[-+*/]";
    private static String arabic = "arabic";
    private static String roman = "roman";
    private static int numberSizeMin = 1;
    private static int numberSizeMax = 10;

    public static void main(String[] args) {
        String input = getInput();
        String result = calc(input);
        System.out.println(result);
    }

    private static String getInput() {
        Scanner in = new Scanner(System.in);
        return in.next();
    }

    public static String calc(String input) {
        String mathOperation = checkToMathOperation(input);
        String[] split = input.split(mathOperations);
        checkNumberCount(split);
        String numberFormat = checkNumberFormat(split);
        checkNumberSize(numberFormat ,split);
        return operationResult(mathOperation, numberFormat, split);
    }

    private static void checkNumberSize(String numberFormat, String[] split) {
        if (numberFormat.equals(roman)) {
            if (RomanArabicConverter.romanToArabic(split[0]) < numberSizeMin
                    || RomanArabicConverter.romanToArabic(split[0]) > numberSizeMax
                    || RomanArabicConverter.romanToArabic(split[1]) < numberSizeMin
                    || RomanArabicConverter.romanToArabic(split[1]) > numberSizeMax) {
                throw new RuntimeException("Число должно быть от 1 до 10");
            }
        } else if (Integer.parseInt(split[0]) < numberSizeMin
                || Integer.parseInt(split[0]) > numberSizeMax
                || Integer.parseInt(split[1]) < numberSizeMin
                || Integer.parseInt(split[1]) > numberSizeMax){
                throw new RuntimeException("Число должно быть от 1 до 10");
        }
    }

    private static String operationResult(String mathOperation, String numberFormat, String[] split) {
        if (numberFormat.equals(roman)) {
            int result = doMathOperation(mathOperation, RomanArabicConverter.romanToArabic(split[0]),
                    RomanArabicConverter.romanToArabic(split[1]));
            return RomanArabicConverter.arabicToRoman(result);
        } else {
            return String.valueOf(doMathOperation(mathOperation, Integer.parseInt(split[0]), Integer.parseInt(split[1])));
        }
    }

    private static int doMathOperation(String mathOperation, int num1, int num2) {
        return switch (mathOperation) {
            case ("+") -> num1 + num2;
            case ("-") -> num1 - num2;
            case ("*") -> num1 * num2;
            case ("/") -> num1 / num2;
            default -> throw new RuntimeException();
        };
    }

    private static String checkNumberFormat(String[] split) {
     if (Arrays.stream(split).filter(i -> RomanArabicConverter.romanToArabic(i) != 0).count() == split.length) {
         return roman;
     }
        try {
            Arrays.stream(split).forEach(Integer::parseInt);
            return arabic;
        } catch (Exception e) {
            throw new RuntimeException("Введите только арабские или римские числа");
        }
    }

    private static void checkNumberCount(String[] split) {
        if(split.length != 2) {
            throw new RuntimeException("Операция производиться только между двумя числами");
        }
    }

    private static String checkToMathOperation(String input) {
        if (input.contains("+")) {
            return "+";
        }
        if (input.contains("-")) {
            return "-";
        }
        if (input.contains("*")) {
            return "*";
        }
        if (input.contains("/")) {
            return "/";
        }
        throw new RuntimeException("Нету знака математической операции");
    }
}
