package org.example.typeOfFiles;

import core.calculation.number.PrefixExpressionOperation;
import core.container.CalculationNumberResults;
import exceptional.WrongFormat;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JSON {
    public static void solveJSON(String input, String output) throws IOException, JSONException, ParseException {
        Object obj = new JSONParser().parse(new FileReader(input));
        JSONObject jo = (JSONObject) obj;
        String str = (String) jo.get("calculation");

        PrefixExpressionOperation prefixExpressionOperation = PrefixExpressionOperation.getInstance("Calculation");
        if (Character.isDigit(str.charAt(0))) {
            try {
                prefixExpressionOperation.check(str);
            } catch (WrongFormat e) {
                throw new RuntimeException(e);
            }
        }
        CalculationNumberResults calculation = getNumberResults(prefixExpressionOperation, str);

        JSONObject json = new JSONObject();
        FileWriter out = new FileWriter(output);
        json.put("Solve", "calculation");
        json.put("solution: ", calculation);
        out.write(json.toString());
        out.flush();
    }

    public static CalculationNumberResults getNumberResults(PrefixExpressionOperation prefixExpressionOperation, String str) {
        return prefixExpressionOperation.calculation(str);
    }

}
