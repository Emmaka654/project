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
                for (int i = 0; i < masOfLines.length; i++) {
                    if (Character.isDigit(masOfLines[i].charAt(0))) {
                        try {
                            prefixExpressionOperation.check(masOfLines[i]);
                        } catch (WrongFormat e) {
                            throw new RuntimeException(e);
                        }
                        CalculationNumberResults calculation = prefixExpressionOperation.calculation(masOfLines[i]);
                        out.println(calculation);
                    }
                }
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
