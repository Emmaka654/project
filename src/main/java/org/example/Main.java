package org.example;

import core.calculation.number.PrefixExpressionOperation;
import core.container.CalculationNumberResults;
import exceptional.WrongFormat;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) throws IOException, XMLStreamException, ParserConfigurationException, SAXException, NoSuchAlgorithmException, SignatureException, NoSuchProviderException, InvalidKeyException {

        solvePlainText("input.txt", "output.txt");
        solveXML("input.xml", "output.xml");
//        solveJSON ("input.xml", "output.xml");
        readZIP("input.zip");
        writeZIP("output.txt", "output.zip");
        readJAR("input.jar");
        writeJAR("output.txt", "output.jar");
//        encryption("output.txt");
    }

    private static void solvePlainText(String input, String output) throws IOException {
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

    public static void solveXML(String input, String path) throws IOException, SAXException, ParserConfigurationException, XMLStreamException {
        DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = docBuilder.parse(input);
        Node root = document.getDocumentElement();
        NodeList segments = root.getChildNodes();

        XMLOutputFactory output = XMLOutputFactory.newInstance();
        XMLStreamWriter writer = output.createXMLStreamWriter(new FileWriter(path));
        writer.writeStartDocument("UTF-8", "1.0");
        writer.writeStartElement("Calculate");
        writer.writeCharacters("Solution");
        writer.writeStartElement("SolveCalculation");

        for (int i = 0; i < segments.getLength(); i++) {
            Node segment = segments.item(i);
            if (segment.getNodeType() != Node.TEXT_NODE) {
                NodeList segmentPoints = segment.getChildNodes();
                for (int j = 0; j < segmentPoints.getLength(); j++) {
                    Node segmentPoint = segmentPoints.item(j);
                    if (segmentPoint.getNodeType() != Node.TEXT_NODE) {
                        PrefixExpressionOperation prefixExpressionOperation = PrefixExpressionOperation.getInstance("Calculation");
                        String str = segmentPoint.getChildNodes().item(0).getTextContent();
                        if (Character.isDigit(str.charAt(0))) {
                            try {
                                prefixExpressionOperation.check(str);
                            } catch (WrongFormat e) {
                                throw new RuntimeException(e);
                            }
                            CalculationNumberResults calculation = prefixExpressionOperation.calculation(str);
                            writer.writeCharacters(String.valueOf(calculation));
                        }
                    }
                }
            }
        }
        writer.writeEndElement();
        writer.writeEndDocument();
        writer.flush();
    }

//    public static void solveJSON(String input, String output) throws IOException, JSONException {
//        Object obj = new JSONParser().parse(new FileReader(input));
//        JSONObject jo = (JSONObject) obj;
//        String str = (String) jo.get("calculation");
//
//        JSONObject json = new JSONObject();
//        FileWriter out = new FileWriter(output);
//        json.put("Solve", "calculation");
//        json.put("solution: ", );
//        out.write(json.toJSONString());
//        out.flush();
//    }

    public static void readZIP(String arh) throws IOException {
        ZipInputStream zin = new ZipInputStream(new FileInputStream(arh));
        ZipEntry entry;
        while ((entry = zin.getNextEntry()) != null) {
            String name = entry.getName();
            FileOutputStream fout = new FileOutputStream("new" + name);
            for (int c = zin.read(); c != -1; c = zin.read()) {
                fout.write(c);
            }
            fout.flush();
            zin.closeEntry();
            fout.close();
        }
    }

    public static void writeZIP(String fileToArh, String arh) throws IOException {
        ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(arh));
        FileInputStream fis = new FileInputStream(fileToArh);
        ZipEntry zipEntry = new ZipEntry("out.txt");
        zipOutputStream.putNextEntry(zipEntry);
        byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        zipOutputStream.write(buffer);
        fis.close();
        zipOutputStream.close();
    }

    public static void readJAR(String path) throws IOException {
        JarInputStream zin = new JarInputStream(new FileInputStream(path));
        JarEntry entry;
        while ((entry = zin.getNextJarEntry()) != null) {
            String name = entry.getName();
            FileOutputStream fout = new FileOutputStream("new" + name);
            for (int c = zin.read(); c != -1; c = zin.read()) {
                fout.write(c);
            }
            fout.flush();
            zin.closeEntry();
            fout.close();
        }
    }

    public static void writeJAR(String fileToArh, String arh) throws IOException {
        JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(arh));
        FileInputStream fis = new FileInputStream(fileToArh);
        JarEntry jarEntry = new JarEntry("out.txt");
        jarOutputStream.putNextEntry(jarEntry);
        byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        jarOutputStream.write(buffer);
        fis.close();
        jarOutputStream.close();
    }

//    public static void encryption(String file) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, IOException, SignatureException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
//        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA", "SUN");
//        SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
//        keyGen.initialize(1024, random);
//        KeyPair pair = keyGen.generateKeyPair();
//        PrivateKey priv = pair.getPrivate();
//        PublicKey pub = pair.getPublic();
//        Signature dsa = Signature.getInstance("SHA1withDSA", "SUN");
//        dsa.initSign(priv);
//        FileInputStream fis = new FileInputStream(file);
//        BufferedInputStream bufin = new BufferedInputStream(fis);
//        byte[] buffer = new byte[1024];
//        int len;
//        while (bufin.available() != 0) {
//            len = bufin.read(buffer);
//            dsa.update(buffer, 0, len);
//        }
//        bufin.close();
//        byte[] realSig = dsa.sign();
//        saveToFile(realSig, "signature.txt");
//        byte[] key = pub.getEncoded();
//        saveToFile(key, "pubkey.txt");
//
//
//        byte[] encKey = readFromFile("pubkey.txt");
//        X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encKey);
//        KeyFactory keyFactory = KeyFactory.getInstance("DSA", "SUN");
//        PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);
//        byte[] sigToVerify = readFromFile("signature.txt");
//        Signature sig = Signature.getInstance("SHA1withDSA", "SUN");
//        sig.initVerify(pubKey);
//        FileInputStream datafis = new FileInputStream(file);
//        BufferedInputStream bufin1 = new BufferedInputStream(datafis);
//        byte[] buffer1 = new byte[1024];
//        int len1;
//        while (bufin1.available() != 0) {
//            len1 = bufin1.read(buffer1);
//            sig.update(buffer1, 0, len1);
//        }
//        bufin1.close();
//        boolean verifies = sig.verify(sigToVerify);
//        System.out.println("Signature verifies: " + verifies);
//    }

}