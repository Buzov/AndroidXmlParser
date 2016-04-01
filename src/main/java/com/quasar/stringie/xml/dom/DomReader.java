package com.quasar.stringie.xml.dom;

import com.quasar.stringie.util.Converter;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import org.xml.sax.SAXException;

/**
 *
 * @author Artur
 */
public class DomReader {

    public static final int NUMBER_LANGUAGE = 2;
    public static Map<String, Map<String, Element>> bigMap = new TreeMap<>();

    public static void getStringsMapFromXML(File file, int position) {
        Map<String, Element> map;

        if (bigMap.containsKey(file.getName())) {
            map = bigMap.get(file.getName());
        } else {
            map = new TreeMap<>();
            bigMap.put(file.getName(), map);
        }

        Document doc = null; // ссылка на объект "документ"
        // Создаем "построитель документов" с помощью "фабричного метода":
        DocumentBuilder db;
        try {
            db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            doc = db.parse(file);
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(DomReader.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Находим корневой тег:
        Node rootNode = doc.getDocumentElement();
        // Просматриваем все "дочерние" теги:
        for (int i = 0; i < rootNode.getChildNodes().getLength(); i++) {
            Node currentNode = rootNode.getChildNodes().item(i);
            String nameNode = currentNode.getNodeName();
            if ("string".equals(nameNode)) {
                // Просматриваем все атрибуты:

                String nameString = null;
                boolean translatable = true;
                String stringValue = null;
                for (int j = 0; j < currentNode.getAttributes().getLength(); j++) {
                    Node nodeAttr = currentNode.getAttributes().item(j);
                    String nameAttribute = nodeAttr.getNodeName();
                    stringValue = currentNode.getTextContent();
                    if (null != nameAttribute) {
                        switch (nameAttribute) {
                            case "name": {
                                nameString = nodeAttr.getNodeValue();
                                break;
                            }
                            case "translatable": {
                                String value = nodeAttr.getNodeValue();
                                if ("false".equals(value)) {
                                    translatable = false;
                                }
                                break;
                            }
                        }
                    }
                }

                Element element = null;
                if (map.containsKey(nameString)) {
                    element = map.get(nameString);
                } else {
                    element = new Element();
                    element.setIsTranslete(translatable);
                    element.setName(nameString);
                    ArrayList<String> list = new ArrayList<>(NUMBER_LANGUAGE);
                    for (int j = 0; j < NUMBER_LANGUAGE; j++) {
                        list.add(null);
                    }
                    element.setValuesList(list);
                    map.put(nameString, element);
                }

                List<String> list = map.get(element.getName()).getValuesList();
                list.set(position, stringValue);

        }
    }

}

public static void main(String[] args) throws Exception {

    }

    public static Map<String, Map<String, List<String>>> getBigMap(boolean translatable) {
        return Converter.convertMapWithElementsToBigMap(bigMap, translatable);
    }
}
