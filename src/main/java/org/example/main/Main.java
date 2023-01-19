package org.example.main;

import core.calculation.number.PrefixExpressionOperation;
import org.example.typeOfFiles.JSON;
import org.example.typeOfFiles.TXT;
import org.example.typeOfFiles.XML;
import org.example.zipAndEncryption.Encryption;
import org.example.zipAndEncryption.JAR;
import org.example.zipAndEncryption.ZIP;
import org.json.JSONException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws IOException, XMLStreamException, ParserConfigurationException, SAXException, NoSuchAlgorithmException, SignatureException, NoSuchProviderException, InvalidKeyException, JSONException, InvalidKeySpecException, org.json.simple.parser.ParseException {
        Scanner scanner = new Scanner(System.in);
        PrefixExpressionOperation prefixExpressionOperation = PrefixExpressionOperation.getInstance("Calculation");
        System.out.println(TXT.getCalculationNumberResults(prefixExpressionOperation, "12+56*14").toString());
        System.out.println("Is the input file zipped? (yes/no)");
        if (scanner.nextLine().equals("yes")) {
            System.out.println("Good! Zip or jar? (zip//jar)");
            if (scanner.nextLine().equals("zip")) {
                ZIP.readZIP("input.zip");
            } else {
                JAR.readJAR("input.jar");
            }
        }
        System.out.println("What type of input file? (txt/xml/json)");
        if (scanner.nextLine().equals("txt")) {
            TXT.solvePlainText("input.txt", "output.txt");
        } else if (scanner.nextLine().equals("xml")) {
            XML.solveXML("input.xml", "output.xml");
        } else {
            JSON.solveJSON("input.json", "output.json");
        }

        System.out.println("Do you want to encrypt the results? (yes/no)");
        if (scanner.nextLine().equals("yes")) {
            Encryption.encryption("output.txt");
        }
        System.out.println("Do you want to archive he results? (yes/no)");
        if (scanner.nextLine().equals("yes")) {
            System.out.println("zip/jar?");
            if (scanner.nextLine().equals("zip")) {
                ZIP.writeZIP("output.txt", "output.zip");
            } else {
                JAR.writeJAR("output.txt", "output.jar");
            }
        }
        System.out.println("Everything has been done!");
    }
}