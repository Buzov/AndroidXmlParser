package com.quasar.stringie.exel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Artur
 */
public class ExelWriter {

    private ExelType exelType = null;
    //private static Workbook workbook;
    /*private static Sheet sheet;
     private static Row row;
     private static Cell cell;
     */
    private static Font font;
    private static CellStyle cellstyleFillRed;
    private static CellStyle cellstyleDef;
    private static CellStyle cellstyle_2;
    private static CellStyle cellstyle_3;
    private static CellStyle cellstyle_4;
    private static CellStyle cellstyle_5;

    public ExelWriter(ExelType exelType) {
        this.exelType = exelType;
    }

    /**
     *
     * @param list
     * @param path
     * @param name
     * @throws java.io.FileNotFoundException
     */
    public void write(List<List<String>> list, String path, String name) throws FileNotFoundException, IOException {
        Workbook workbook = getWorkbook();
        Sheet sheet = getSheet(workbook, name);

        for (int i = 0; i < list.size(); i++) {
            Row row = getRow(i, sheet);
            for (int j = 0; j < list.get(i).size(); j++) {
                if (i == list.size() - 1) {
                    sheet.setColumnWidth(j, 10000);
                    //sheet.autoSizeColumn(j);
                }
                Cell c = getCell(row, j);
                String s = list.get(i).get(j);
                if (s != null) {
                    s = s.trim();
                    c.setCellStyle(getCellStyleDef(null, workbook));
                } else {
                    c.setCellStyle(getCellStyle(null, workbook));
                }
                c.setCellValue(s);
            }
        }
        try (FileOutputStream fs = new FileOutputStream(path + name)) {
            workbook.write(fs);
        }
        workbook.close();
    }

    public ExelType getExelType() {
        return exelType;
    }

    private Workbook getWorkbook() {
        Workbook workbook = null;
        switch (getExelType()) {
            case XLS:
                workbook = new HSSFWorkbook();
                break;
            case XLSX:
                workbook = new XSSFWorkbook();
                break;
        }
        return workbook;
    }

    private Sheet getSheet(Workbook workbook, String name) {

        Sheet sheet = workbook.createSheet(WorkbookUtil.createSafeSheetName(name));
        // Set the width (in units of 1/256th of a character width)
        /*sheet.setColumnWidth(1, 5000);*/

        /*sheet.addMergedRegion(new CellRangeAddress(0, 4, 4, 8));*/
        return sheet;
    }

    private Row getRow(int rowN, /*float height,*/ Sheet sheet) {
        Row row = sheet.createRow(rowN);
        /*row.setHeightInPoints(height);*/

        return row;
    }

    private Cell getCell(Row row, int cellN/*, CellStyle style*/) {
        Cell cell = row.createCell(cellN);
        /*cell.setCellStyle(style);*/
        return cell;
    }

    private CellStyle getCellStyle(Font font, Workbook workbook) {
        if (cellstyleFillRed == null) {
            cellstyleFillRed = workbook.createCellStyle();
            cellstyleFillRed.setFillPattern(CellStyle.SOLID_FOREGROUND);
            cellstyleFillRed.setFillForegroundColor(IndexedColors.RED.getIndex());
            cellstyleFillRed.setFillBackgroundColor(IndexedColors.GREEN.getIndex());
            //cellstyle_fill_red.setAlignment(CellStyle.ALIGN_CENTER);
            cellstyleFillRed.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
            cellstyleFillRed.setWrapText(true);
            //cellstyle_fill_red.setWrapText(true);
            //cellstyle_fill_red.setBorderBottom(CellStyle.BORDER_DASH_DOT_DOT);
            //cellstyle_fill_red.setBottomBorderColor(IndexedColors.GREEN.getIndex());
            if (font != null) {
                cellstyleFillRed.setFont(font);
            }
        }
        return cellstyleFillRed;
    }
    
    private CellStyle getCellStyleDef(Font font, Workbook workbook) {
        if (cellstyleDef == null) {
            cellstyleDef = workbook.createCellStyle();
            cellstyleDef.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
            cellstyleDef.setWrapText(true);
            if (font != null) {
                cellstyleDef.setFont(font);
            }
        }
        return cellstyleDef;
    }

    private Font getFont(Workbook workbook) {
        if (font == null) {
            font = workbook.createFont();
            font.setFontName("Word");
            font.setFontHeightInPoints((short) 25);
            font.setBold(true);
            font.setStrikeout(true);
            font.setUnderline(Font.U_SINGLE);
            font.setColor(IndexedColors.RED.getIndex());
        }
        return font;
    }

    public static void main(String[] args) throws IOException {
        List<List<Object>> list = new ArrayList<>();
        for (int i = 0; i < 65000; i++) {
            List<Object> l = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                l.add("" + i + j + "word" + "abcdfgeklomprqzx");
            }
            list.add(l);
        }
        ExelWriter ew = new ExelWriter(ExelType.XLSX);

            //ew.write(list, "./word.xlsx", "word");
    }

}
