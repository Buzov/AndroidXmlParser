package com.quasar.stringie.util;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Artur
 */
public class Printer {

    public static void printMap(Map<String, Map<String, List<String>>> bigMap) {
        for (Map.Entry<String, Map<String, List<String>>> m : bigMap.entrySet()) {
            System.out.println(m.getKey());
            for (Map.Entry<String, List<String>> col : m.getValue().entrySet()) {
                System.out.print("\t");
                System.out.print(col.getKey() + " = ");
                for (String s : col.getValue()) {
                    System.out.print(s + " ");
                }
                System.out.println("");
            }
        }
    }
}
