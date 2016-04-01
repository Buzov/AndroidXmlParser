package com.quasar.stringie.util;

import com.quasar.stringie.xml.dom.Element;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Artur
 */
public class Converter {

    public static List<List<String>> convertBigMapToBigList(Map<String, Map<String, List<String>>> bigMap) {
        List<List<String>> bigList = new ArrayList<>();
        for (Map.Entry<String, Map<String, List<String>>> m : bigMap.entrySet()) {
            String pathToFile = m.getKey();

            System.out.println(pathToFile);
            for (Map.Entry<String, List<String>> col : m.getValue().entrySet()) {
                List<String> listOfStrings = new ArrayList<>();
                listOfStrings.add(pathToFile);
                //System.out.print("\t");
                //System.out.print(col.getKey() + " = ");
                listOfStrings.add(col.getKey());
                for (String s : col.getValue()) {
                    System.out.print(s + " ");
                    listOfStrings.add(s);
                }
                //System.out.println("");
                bigList.add(listOfStrings);
            }

        }
        return bigList;
    }

    public static Map<String, Map<String, List<String>>> convertMapWithElementsToBigMap(Map<String, Map<String, Element>> bigMap, boolean translatable) {
        Map<String, Map<String, List<String>>> mapTemp = new TreeMap<>();
        for (Map.Entry<String, Map<String, Element>> m : bigMap.entrySet()) {
            String pathToFile = m.getKey();
            Map<String, List<String>> map;

            if (mapTemp.containsKey(pathToFile)) {
                map = mapTemp.get(pathToFile);
            } else {
                map = new TreeMap<>();
                mapTemp.put(pathToFile, map);
            }
            for (Map.Entry<String, Element> col : m.getValue().entrySet()) {
                String stringName = col.getKey();
                if (col.getValue().isIsTranslete() || translatable) {
                    if (!map.containsKey(stringName)) {
                        map.put(stringName, col.getValue().getValuesList());
                    }
                }
            }
        }
        return mapTemp;
    }
}
