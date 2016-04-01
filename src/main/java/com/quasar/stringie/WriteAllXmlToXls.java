package com.quasar.stringie;

import com.quasar.stringie.exel.ExelType;
import com.quasar.stringie.exel.ExelWriter;
import com.quasar.stringie.fileio.ListerOnlyXmlWithStrings;
import com.quasar.stringie.xml.dom.DomReader;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Artur
 */
public class WriteAllXmlToXls extends AbstractXmlWorker{
    
    public static final String pathToEnXml = "d:\\Projects\\Dipocket\\Android\\trunk\\DiPocketApp\\res\\values\\";
    public static final String pathToPlXml = "d:\\Projects\\Dipocket\\Android\\trunk\\DiPocketApp\\res\\values-pl\\";
    public static final boolean translatable = false;
    
    public static void main(String[] args) throws IOException {
        List<File> listFile = ListerOnlyXmlWithStrings.getFilesList(pathToEnXml);
        DomReader domReader = new DomReader();
        for (File f : listFile) {
            domReader.getStringsMapFromXML(f, 0);
            domReader.getStringsMapFromXML(new File(pathToPlXml + f.getName()), 1);
        }
        printMap(DomReader.getBigMap(translatable));
        
        Map<String, Map<String, List<String>>> bigMap = DomReader.getBigMap(translatable);
        List<List<String>> bigList = convertMapToList(DomReader.getBigMap(translatable));
        ExelWriter exelWriter = new ExelWriter(ExelType.XLS);
        
        exelWriter.write(bigList, "C:/Users/artur/Desktop/DiPocket/", "strings_project_final.xls");
    }
}
