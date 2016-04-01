package com.quasar.stringie;

import com.quasar.stringie.util.Converter;
import com.quasar.stringie.util.Printer;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Artur
 */
public class AbstractXmlWorker {
    public static void printMap(Map<String, Map<String, List<String>>> bigMap) {
        Printer.printMap(bigMap);
    }

    public static List<List<String>> convertMapToList(Map<String, Map<String, List<String>>> bigMap) {
        return Converter.convertBigMapToBigList(bigMap);
    }
}
