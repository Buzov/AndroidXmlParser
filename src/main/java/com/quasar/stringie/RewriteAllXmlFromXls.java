package com.quasar.stringie;

import com.quasar.stringie.exel.ExelReader;
import com.quasar.stringie.exel.ExelType;
import com.quasar.stringie.fileio.ListerOnlyXmlWithStrings;
import com.quasar.stringie.xml.dom.DomWriterFromReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.TransformerException;

/**
 *
 * @author Artur
 */
public class RewriteAllXmlFromXls extends AbstractXmlWorker{

    public static final String pathToEnXml = "d:\\Projects\\Dipocket\\Android\\trunk\\DiPocketApp\\res\\values\\";
    public static final String pathToPlXml = "d:\\Projects\\Dipocket\\Android\\trunk\\DiPocketApp\\res\\values-pl\\";
    
//    public static final String pathToEnXml = "D:\\Projects\\Dipocket\\Android\\test\\values\\";
//    public static final String pathToPlXml = "D:\\Projects\\Dipocket\\Android\\test\\values-pl\\";
    
    public static final String[] paths = {pathToEnXml, pathToPlXml};
    public static final int NAME_FILE_POSITION = 0;
    public static final int NAME_STRING_POSITION = 1;

    public static void main(String[] args) throws FileNotFoundException, TransformerException {
        ExelReader exelReader = new ExelReader(ExelType.XLSX);
        Map<String, Map<String, List<String>>> bigMap = new TreeMap<>();
        try {
            List<List<String>> listAllStrings = exelReader.readOnlyStrings("c:\\Users\\artur\\Desktop\\PROD_PROD_PROD\\App_text_and_localization.xlsx");
            for (List<String> row : listAllStrings) {
                if (!row.isEmpty()) {
                    String nameFile = row.get(NAME_FILE_POSITION);

                    if (nameFile != null) {
                        if (!bigMap.containsKey(nameFile)) {
                            Map<String, List<String>> map = new TreeMap<>();
                            bigMap.put(nameFile, map);
                        }

                        Map<String, List<String>> map = bigMap.get(nameFile);
                        String nameString = row.get(NAME_STRING_POSITION);
                        if (nameString != null) {
                            if (!map.containsKey(nameString)) {
                                List<String> list = new ArrayList();
                                map.put(nameString, list);
                            }

                            List<String> list = map.get(nameString);

                            for (int i = 0; i < paths.length; i++) {
                                list.add(row.get(i + 2));
                            }
                        }
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(RewriteAllXmlFromXls.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<File> listFile = ListerOnlyXmlWithStrings.getFilesList(pathToEnXml);
        DomWriterFromReader domWriter = new DomWriterFromReader(bigMap);
        for (File f : listFile) {
            domWriter.getStringsMapFromXML(f, 0);
            domWriter.getStringsMapFromXML(new File(pathToPlXml + f.getName()), 1);
        }
        
        //printMap(bigMap);
        List<List<String>> bigList = convertMapToList(bigMap);
//        ExelWriter exelWriter = new ExelWriter(ExelType.XLS);
//        try {
//            exelWriter.write(bigList, "C:/Users/artur/Desktop/DiPocket/", "Strings_tran_my____.xls");
//        } catch (IOException ex) {
//            Logger.getLogger(RewriteAllXmlFromXls.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

}
