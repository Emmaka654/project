package org.example.typeOfFiles;

import core.calculation.number.PrefixExpressionOperation;
import core.container.CalculationNumberResults;
import exceptional.WrongFormat;

import java.io.*;

public class TXT {
    public static void solvePlainText(String input, String output) throws IOException {
        PrintWriter out = new PrintWriter(new FileWriter(output));
        File file = new File(input);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            String str = null;
            while ((line = br.readLine()) != null) {
                PrefixExpressionOperation prefixExpressionOperation = PrefixExpressionOperation.getInstance("Calculation");
                String[] masOfLines = line.split(" ");
                for (String masOfLine : masOfLines) {
                    if (Character.isDigit(masOfLine.charAt(0))) {
                        try {
                            prefixExpressionOperation.check(masOfLine);
                        } catch (WrongFormat e) {
                            throw new RuntimeException(e);
                        }
                        CalculationNumberResults calculation = getCalculationNumberResults(prefixExpressionOperation, masOfLine);
                        out.println(calculation);
                    }
                }
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static CalculationNumberResults getCalculationNumberResults(PrefixExpressionOperation prefixExpressionOperation, String masOfLines) {
        return  prefixExpressionOperation.calculation(masOfLines);
    }

}
