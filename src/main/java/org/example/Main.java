package org.example;

import core.calculation.number.PrefixExpressionOperation;
import core.container.CalculationNumberResults;
import exceptional.WrongFormat;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        PrintWriter out = new PrintWriter(new FileWriter("output.txt"));
        PrefixExpressionOperation prefixExpressionOperation = PrefixExpressionOperation.getInstance("Calculation");
        // Create an expression
        String s = "1 + 2 + 4 * 10 - 3";
        // Check the expression for errors
        try {
            prefixExpressionOperation.check(s);
        } catch (WrongFormat e) {
            throw new RuntimeException(e);
        }
        // Start calculating results
        CalculationNumberResults calculation = prefixExpressionOperation.calculation(s);
        System.out.println(calculation);
    }
}