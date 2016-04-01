package com.quasar.stringie.xml.dom;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.xml.sax.SAXException;

/**
 *
 * @author Artur
 */
public class DomWriterFromReader {

    public static final int NUMBER_LANGUAGE = 2;
    public static Map<String, Map<String, List<String>>> bigMap = new TreeMap<>();

    public DomWriterFromReader(Map<String, Map<String, List<String>>> bigMap) {
        this.bigMap = bigMap;
    }

    public void getStringsMapFromXML(File file, int position) throws FileNotFoundException, TransformerConfigurationException, TransformerException {
        Map<String, List<String>> map;

        if (bigMap.containsKey(file.getName())) {
            map = bigMap.get(file.getName());
        } else {
            return;
        }

        Document doc = null; // ссылка на объект "документ"
        // Создаем "построитель документов" с помощью "фабричного метода":
        DocumentBuilder db;
        try {
            db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            doc = db.parse(file);
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(DomWriterFromReader.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Находим корневой тег:
        Node rootNode = doc.getDocumentElement();
        // Просматриваем все "дочерние" теги:
        for (int i = 0; i < rootNode.getChildNodes().getLength(); i++) {
            Node currentNode = rootNode.getChildNodes().item(i);
            String nameNode = currentNode.getNodeName();
            Element element = new Element();
            if ("string".equals(nameNode)) {
                // Просматриваем все атрибуты:
                for (int j = 0; j < currentNode.getAttributes().getLength(); j++) {
                    Node nodeAttr = currentNode.getAttributes().item(j);
                    element.setValue(currentNode.getTextContent());
                    String nameAttribute = nodeAttr.getNodeName();
                    if (nameAttribute != null) {
                        switch (nameAttribute) {
                            case "name": {
                                String value = nodeAttr.getNodeValue();
                                element.setName(value);
                                //System.out.print(value + " = ");
                                break;
                            }
                            case "translatable": {
                                String value = nodeAttr.getNodeValue();
                                if (!"false".equals(value)) {
                                    element.setIsTranslete(true);
                                }
                                break;
                            }
                        }
                    }
                }

                if (map.containsKey(element.getName())) {
                    List<String> list = map.get(element.getName());
                    if (!element.getValue().equals(list.get(position))) {
                        //element.setValue(list.get(position));
                        currentNode.setTextContent(list.get(position));
                    }
                }

            }
        }
        
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        // Запись в файл:
        transformer.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(file)));

    }

    public static void main(String[] args) throws Exception {

    }

    public static Map<String, Map<String, List<String>>> getBigMap() {
        return bigMap;
    }

    public static void setBigMap(Map<String, Map<String, List<String>>> bigMap) {
        DomWriterFromReader.bigMap = bigMap;
    }

}
