package com.quasar.stringie.xml.dom;

import java.util.List;

/**
 *
 * @author Artur
 */
public class Element {
    private boolean isTranslete = true;
    private String name;
    private String value;
    
    private List<String> valuesList;

    public boolean isIsTranslete() {
        return isTranslete;
    }

    public void setIsTranslete(boolean isTranslete) {
        this.isTranslete = isTranslete;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    } 

    public List<String> getValuesList() {
        return valuesList;
    }

    public void setValuesList(List<String> valuesList) {
        this.valuesList = valuesList;
    }
}
