package org.example.typeOfFiles;

import core.calculation.number.PrefixExpressionOperation;
import core.container.CalculationNumberResults;
import exceptional.WrongFormat;
import org.apache.logging.log4j.core.parser.ParseException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JSON {
    public static void solveJSON(String input, String output) throws IOException, ParseException, JSONException {
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
        CalculationNumberResults calculation = prefixExpressionOperation.calculation(str);

        JSONObject json = new JSONObject();
        FileWriter out = new FileWriter(output);
        json.put("Solve", "calculation");
        json.put("solution: ", calculation);
        out.write(json.toString());
        out.flush();
    }

}
