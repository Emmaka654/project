package org.example.typeOfFiles;

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
import java.io.FileWriter;
import java.io.IOException;

public class XML {
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
                            CalculationNumberResults calculation = getCalculationResults(prefixExpressionOperation, str);
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

    public static CalculationNumberResults getCalculationResults(PrefixExpressionOperation prefixExpressionOperation, String str) {
        return prefixExpressionOperation.calculation(str);
    }
}
