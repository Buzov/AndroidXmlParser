package com.quasar.stringie.exel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author artur
 */
public class ValidatorXlsKeysWords {
    
    public static boolean validateWithoutException(List<List<String>> list){
        try {
            validate(list);
            return true;
        } catch (XlsStructureException ex) {
            return false;
        }
    }
    
    public static void validate(List<List<String>> list) throws XlsStructureException {
        Map<String, Integer> map = new HashMap<>();
        if(list == null) {
            throw new XlsStructureException();
        }
        for(List<String> l : list) {
            if(l == null) {
                throw new XlsStructureException();
            }
            String key = l.get(1);
            if(map.containsKey(key)) {
                map.put(key, map.get(key) + 1);
            } else {
                map.put(key, 1);
            }
        }
        boolean hasDuplicate = false;
        for(Map.Entry<String, Integer> e : map.entrySet()) {
            if(e.getValue() > 1) {
                System.out.println("Count " + e.getValue() + " " + e.getKey());
                hasDuplicate = true;
            }
        }
        if(hasDuplicate) {
            throw new XlsStructureException();
        }
    }
}
