package com.quasar.stringie.fileio.google;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс для скачивания файла с google drive
 *
 * @author Artur
 */
public class MainGoogleDownloader {

    /**
     * id таблицы на google drive
     */
    private static final String ID_TABLE = "18Q9VOaB-i5IkGBcSDZ3d5vBl0JyPqWEcmh4X5TyBQEM";
    /**
     * url для скачивания таблицы с google drive
     */
    private static final String URL = "https://docs.google.com/spreadsheets/d/%s/export?format=xlsx";
    private static final String URL_TO_FILE = String.format(URL, ID_TABLE);
    private static final String PATH_TO_FILE = "D:/App_text_and_localization.xlsx";

    public static void main(String[] args) {
        try {
            dowloadXlsFile();
        } catch (GoogleException ex) {
            Logger.getLogger(MainGoogleDownloader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static InputStream getStream(String url) {
        if (url == null) {
            url = URL_TO_FILE;
        }
        try (InputStream is = new URL(url).openStream();) {
            return is;
        } catch (Exception ex) {

        }
        return null;
    }

    public static void dowloadXlsFile() throws GoogleException {
        dowloadXlsFile(null, null);
    }

    public static void dowloadXlsFile(String path, String url) throws GoogleException {
        if (path == null) {
            path = PATH_TO_FILE;
        }
        if (url == null) {
            url = URL_TO_FILE;
        }
        
        try (
            InputStream is = new URL(url).openStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            FileOutputStream fout = new FileOutputStream(path)) {

            final byte data[] = new byte[1024];
            int count;
            while ((count = bis.read(data, 0, 1024)) != -1) {
                fout.write(data, 0, count);
            }
            
        } catch (Exception ex) {
            throw new GoogleException();
        }

    }

}
