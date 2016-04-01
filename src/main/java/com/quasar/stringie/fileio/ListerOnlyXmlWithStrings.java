package com.quasar.stringie.fileio;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Artur
 */
public class ListerOnlyXmlWithStrings {
    
    public static void main(String[] args) {
        for(File f : getFilesList("/Users/artur/Desktop/trunk/DiPocketApp/res/values/")) {
            try {
                System.out.println(f.getCanonicalPath());
            } catch (IOException ex) {
                Logger.getLogger(ListerOnlyXmlWithStrings.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static List<File> getFilesList(String pathname) {
        List<File> xmlFileList = new ArrayList<>();
        try {
            List<File> temp = Lister.getFilesList(pathname);
            for(File f : temp) {
                if(f.getName().contains("strings")) {
                    xmlFileList.add(f);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ListerOnlyXmlWithStrings.class.getName()).log(Level.SEVERE, null, ex);
        }
        return xmlFileList;
    }
}
