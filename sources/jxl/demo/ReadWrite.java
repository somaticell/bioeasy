package jxl.demo;

import com.alibaba.fastjson.asm.Opcodes;
import common.Logger;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.biff.DisplayFormat;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.read.biff.BiffException;
import jxl.write.DateFormat;
import jxl.write.DateFormats;
import jxl.write.DateTime;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableHyperlink;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class ReadWrite {
    static Class class$jxl$demo$ReadWrite;
    private static Logger logger;
    private File inputWorkbook;
    private File outputWorkbook;

    static {
        Class cls;
        if (class$jxl$demo$ReadWrite == null) {
            cls = class$("jxl.demo.ReadWrite");
            class$jxl$demo$ReadWrite = cls;
        } else {
            cls = class$jxl$demo$ReadWrite;
        }
        logger = Logger.getLogger(cls);
    }

    static Class class$(String x0) {
        try {
            return Class.forName(x0);
        } catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError(x1.getMessage());
        }
    }

    public ReadWrite(String input, String output) {
        this.inputWorkbook = new File(input);
        this.outputWorkbook = new File(output);
        logger.setSuppressWarnings(Boolean.getBoolean("jxl.nowarnings"));
        logger.info(new StringBuffer().append("Input file:  ").append(input).toString());
        logger.info(new StringBuffer().append("Output file:  ").append(output).toString());
    }

    public void readWrite() throws IOException, BiffException, WriteException {
        logger.info("Reading...");
        Workbook w1 = Workbook.getWorkbook(this.inputWorkbook);
        Sheet sheet = w1.getSheet(0);
        logger.info("Copying...");
        WritableWorkbook w2 = Workbook.createWorkbook(this.outputWorkbook, w1);
        if (this.inputWorkbook.getName().equals("jxlrwtest.xls")) {
            modify(w2);
        }
        w2.write();
        w2.close();
        logger.info("Done");
    }

    private void modify(WritableWorkbook w) throws WriteException {
        logger.info("Modifying...");
        WritableSheet sheet = w.getSheet("modified");
        sheet.getWritableCell(1, 3).setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD)));
        sheet.getWritableCell(1, 4).setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, UnderlineStyle.SINGLE)));
        sheet.getWritableCell(1, 5).setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 10)));
        WritableCell cell = sheet.getWritableCell(1, 6);
        if (cell.getType() == CellType.LABEL) {
            Label lc = (Label) cell;
            lc.setString(new StringBuffer().append(lc.getString()).append(" - mod").toString());
        }
        sheet.getWritableCell(1, 9).setCellFormat(new WritableCellFormat((DisplayFormat) new NumberFormat("#.0000000")));
        sheet.getWritableCell(1, 10).setCellFormat(new WritableCellFormat((DisplayFormat) new NumberFormat("0.####E0")));
        sheet.getWritableCell(1, 11).setCellFormat(WritableWorkbook.NORMAL_STYLE);
        WritableCell cell2 = sheet.getWritableCell(1, 12);
        if (cell2.getType() == CellType.NUMBER) {
            ((Number) cell2).setValue(42.0d);
        }
        WritableCell cell3 = sheet.getWritableCell(1, 13);
        if (cell3.getType() == CellType.NUMBER) {
            Number n = (Number) cell3;
            n.setValue(n.getValue() + 0.1d);
        }
        sheet.getWritableCell(1, 16).setCellFormat(new WritableCellFormat((DisplayFormat) new DateFormat("dd MMM yyyy HH:mm:ss")));
        WritableCell cell4 = sheet.getWritableCell(1, 17);
        WritableCellFormat writableCellFormat = new WritableCellFormat(DateFormats.FORMAT9);
        cell4.setCellFormat(writableCellFormat);
        WritableCell cell5 = sheet.getWritableCell(1, 18);
        if (cell5.getType() == CellType.DATE) {
            Calendar cal = Calendar.getInstance();
            cal.set(1998, 1, 18, 11, 23, 28);
            ((DateTime) cell5).setDate(cal.getTime());
        }
        WritableCell cell6 = sheet.getWritableCell(1, 22);
        if (cell6.getType() == CellType.NUMBER) {
            ((Number) cell6).setValue(6.8d);
        }
        WritableCell cell7 = sheet.getWritableCell(1, 29);
        if (cell7.getType() == CellType.LABEL) {
            ((Label) cell7).setString("Modified string contents");
        }
        sheet.insertRow(34);
        sheet.removeRow(38);
        sheet.insertColumn(9);
        sheet.removeColumn(11);
        sheet.removeRow(43);
        sheet.insertRow(43);
        WritableHyperlink[] hyperlinks = sheet.getWritableHyperlinks();
        for (int i = 0; i < hyperlinks.length; i++) {
            WritableHyperlink wh = hyperlinks[i];
            if (wh.getColumn() == 1 && wh.getRow() == 39) {
                try {
                    wh.setURL(new URL("http://www.andykhan.com/jexcelapi/index.html"));
                } catch (MalformedURLException e) {
                    logger.warn(e.toString());
                }
            } else if (wh.getColumn() == 1 && wh.getRow() == 40) {
                wh.setFile(new File("../jexcelapi/docs/overview-summary.html"));
            } else if (wh.getColumn() == 1 && wh.getRow() == 41) {
                wh.setFile(new File("d:/home/jexcelapi/docs/jxl/package-summary.html"));
            } else if (wh.getColumn() == 1 && wh.getRow() == 44) {
                sheet.removeHyperlink(wh);
            }
        }
        WritableCell c = sheet.getWritableCell(5, 30);
        WritableCellFormat writableCellFormat2 = new WritableCellFormat(c.getCellFormat());
        writableCellFormat2.setBackground(Colour.RED);
        c.setCellFormat(writableCellFormat2);
        sheet.addCell(new Label(0, 49, "Modified merged cells"));
        ((Number) sheet.getWritableCell(0, 70)).setValue(9.0d);
        ((Number) sheet.getWritableCell(0, 71)).setValue(10.0d);
        ((Number) sheet.getWritableCell(0, 73)).setValue(4.0d);
        sheet.addCell(new Formula(1, 80, "ROUND(COS(original!B10),2)"));
        sheet.addCell(new Formula(1, 83, "value1+value2"));
        sheet.addCell(new Formula(1, 84, "AVERAGE(value1,value1*4,value2)"));
        sheet.addCell(new Label(0, 88, "Some copied cells", writableCellFormat));
        sheet.addCell(new Label(0, 89, "Number from B9"));
        sheet.addCell(sheet.getWritableCell(1, 9).copyTo(1, 89));
        sheet.addCell(new Label(0, 90, "Label from B4 (modified format)"));
        sheet.addCell(sheet.getWritableCell(1, 3).copyTo(1, 90));
        sheet.addCell(new Label(0, 91, "Date from B17"));
        sheet.addCell(sheet.getWritableCell(1, 16).copyTo(1, 91));
        sheet.addCell(new Label(0, 92, "Boolean from E16"));
        sheet.addCell(sheet.getWritableCell(4, 15).copyTo(1, 92));
        sheet.addCell(new Label(0, 93, "URL from B40"));
        sheet.addCell(sheet.getWritableCell(1, 39).copyTo(1, 93));
        for (int i2 = 0; i2 < 6; i2++) {
            sheet.addCell(new Number(1, i2 + 94, ((double) (i2 + 1)) + (((double) i2) / 8.0d)));
        }
        sheet.addCell(new Label(0, 100, "Formula from B27"));
        sheet.addCell(sheet.getWritableCell(1, 26).copyTo(1, 100));
        sheet.addCell(new Label(0, 101, "A brand new formula"));
        sheet.addCell(new Formula(1, 101, "SUM(B94:B96)"));
        sheet.addCell(new Label(0, 102, "A copy of it"));
        sheet.addCell(sheet.getWritableCell(1, 101).copyTo(1, 102));
        sheet.removeImage(sheet.getImage(1));
        sheet.addImage(new WritableImage(1.0d, 116.0d, 2.0d, 9.0d, new File("resources/littlemoretonhall.png")));
        ((Label) sheet.getWritableCell(0, 156)).setString("Label text modified");
        sheet.getWritableCell(0, 157).getWritableCellFeatures().setComment("modified comment text");
        sheet.getWritableCell(0, Opcodes.IFLE).getWritableCellFeatures().removeComment();
    }
}
