package code;

import java.util.function.BinaryOperator;

public class Main {
    public static void main(String[] args) {
        BinaryOperation add = (a, b) -> a + b;
        assert 3 == add.apply(2, 3);
        if (5 == add.apply(2,3)) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }
        BinaryOperation multiply = (a, b) -> a * b;
        assert 3 == multiply.apply(2, 3);
        if (6 == multiply.apply(2,3)) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }

        BinaryOperator<Double> add2 = (a, b) -> a + b;
        System.out.println(add2.apply(0.3, 1.5));
    }
}
