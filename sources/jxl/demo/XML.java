package jxl.demo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.CellFormat;
import jxl.format.Colour;
import jxl.format.Font;
import jxl.format.Pattern;

public class XML {
    private String encoding;
    private OutputStream out;
    private Workbook workbook;

    public XML(Workbook w, OutputStream out2, String enc, boolean f) throws IOException {
        this.encoding = enc;
        this.workbook = w;
        this.out = out2;
        if (this.encoding == null || !this.encoding.equals("UnicodeBig")) {
            this.encoding = "UTF8";
        }
        if (f) {
            writeFormattedXML();
        } else {
            writeXML();
        }
    }

    private void writeXML() throws IOException {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(this.out, this.encoding));
            bw.write("<?xml version=\"1.0\" ?>");
            bw.newLine();
            bw.write("<!DOCTYPE workbook SYSTEM \"workbook.dtd\">");
            bw.newLine();
            bw.newLine();
            bw.write("<workbook>");
            bw.newLine();
            for (int sheet = 0; sheet < this.workbook.getNumberOfSheets(); sheet++) {
                Sheet s = this.workbook.getSheet(sheet);
                bw.write("  <sheet>");
                bw.newLine();
                bw.write(new StringBuffer().append("    <name><![CDATA[").append(s.getName()).append("]]></name>").toString());
                bw.newLine();
                for (int i = 0; i < s.getRows(); i++) {
                    bw.write(new StringBuffer().append("    <row number=\"").append(i).append("\">").toString());
                    bw.newLine();
                    Cell[] row = s.getRow(i);
                    for (int j = 0; j < row.length; j++) {
                        if (row[j].getType() != CellType.EMPTY) {
                            bw.write(new StringBuffer().append("      <col number=\"").append(j).append("\">").toString());
                            bw.write(new StringBuffer().append("<![CDATA[").append(row[j].getContents()).append("]]>").toString());
                            bw.write("</col>");
                            bw.newLine();
                        }
                    }
                    bw.write("    </row>");
                    bw.newLine();
                }
                bw.write("  </sheet>");
                bw.newLine();
            }
            bw.write("</workbook>");
            bw.newLine();
            bw.flush();
            bw.close();
        } catch (UnsupportedEncodingException e) {
            System.err.println(e.toString());
        }
    }

    private void writeFormattedXML() throws IOException {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(this.out, this.encoding));
            bw.write("<?xml version=\"1.0\" ?>");
            bw.newLine();
            bw.write("<!DOCTYPE workbook SYSTEM \"formatworkbook.dtd\">");
            bw.newLine();
            bw.newLine();
            bw.write("<workbook>");
            bw.newLine();
            for (int sheet = 0; sheet < this.workbook.getNumberOfSheets(); sheet++) {
                Sheet s = this.workbook.getSheet(sheet);
                bw.write("  <sheet>");
                bw.newLine();
                bw.write(new StringBuffer().append("    <name><![CDATA[").append(s.getName()).append("]]></name>").toString());
                bw.newLine();
                for (int i = 0; i < s.getRows(); i++) {
                    bw.write(new StringBuffer().append("    <row number=\"").append(i).append("\">").toString());
                    bw.newLine();
                    Cell[] row = s.getRow(i);
                    for (int j = 0; j < row.length; j++) {
                        if (row[j].getType() != CellType.EMPTY || row[j].getCellFormat() != null) {
                            CellFormat format = row[j].getCellFormat();
                            bw.write(new StringBuffer().append("      <col number=\"").append(j).append("\">").toString());
                            bw.newLine();
                            bw.write("        <data>");
                            bw.write(new StringBuffer().append("<![CDATA[").append(row[j].getContents()).append("]]>").toString());
                            bw.write("</data>");
                            bw.newLine();
                            if (row[j].getCellFormat() != null) {
                                bw.write(new StringBuffer().append("        <format wrap=\"").append(format.getWrap()).append("\"").toString());
                                bw.newLine();
                                bw.write(new StringBuffer().append("                align=\"").append(format.getAlignment().getDescription()).append("\"").toString());
                                bw.newLine();
                                bw.write(new StringBuffer().append("                valign=\"").append(format.getVerticalAlignment().getDescription()).append("\"").toString());
                                bw.newLine();
                                bw.write(new StringBuffer().append("                orientation=\"").append(format.getOrientation().getDescription()).append("\"").toString());
                                bw.write(">");
                                bw.newLine();
                                Font font = format.getFont();
                                bw.write(new StringBuffer().append("          <font name=\"").append(font.getName()).append("\"").toString());
                                bw.newLine();
                                bw.write(new StringBuffer().append("                point_size=\"").append(font.getPointSize()).append("\"").toString());
                                bw.newLine();
                                bw.write(new StringBuffer().append("                bold_weight=\"").append(font.getBoldWeight()).append("\"").toString());
                                bw.newLine();
                                bw.write(new StringBuffer().append("                italic=\"").append(font.isItalic()).append("\"").toString());
                                bw.newLine();
                                bw.write(new StringBuffer().append("                underline=\"").append(font.getUnderlineStyle().getDescription()).append("\"").toString());
                                bw.newLine();
                                bw.write(new StringBuffer().append("                colour=\"").append(font.getColour().getDescription()).append("\"").toString());
                                bw.newLine();
                                bw.write(new StringBuffer().append("                script=\"").append(font.getScriptStyle().getDescription()).append("\"").toString());
                                bw.write(" />");
                                bw.newLine();
                                if (!(format.getBackgroundColour() == Colour.DEFAULT_BACKGROUND && format.getPattern() == Pattern.NONE)) {
                                    bw.write(new StringBuffer().append("          <background colour=\"").append(format.getBackgroundColour().getDescription()).append("\"").toString());
                                    bw.newLine();
                                    bw.write(new StringBuffer().append("                      pattern=\"").append(format.getPattern().getDescription()).append("\"").toString());
                                    bw.write(" />");
                                    bw.newLine();
                                }
                                if (!(format.getBorder(Border.TOP) == BorderLineStyle.NONE && format.getBorder(Border.BOTTOM) == BorderLineStyle.NONE && format.getBorder(Border.LEFT) == BorderLineStyle.NONE && format.getBorder(Border.RIGHT) == BorderLineStyle.NONE)) {
                                    bw.write(new StringBuffer().append("          <border top=\"").append(format.getBorder(Border.TOP).getDescription()).append("\"").toString());
                                    bw.newLine();
                                    bw.write(new StringBuffer().append("                  bottom=\"").append(format.getBorder(Border.BOTTOM).getDescription()).append("\"").toString());
                                    bw.newLine();
                                    bw.write(new StringBuffer().append("                  left=\"").append(format.getBorder(Border.LEFT).getDescription()).append("\"").toString());
                                    bw.newLine();
                                    bw.write(new StringBuffer().append("                  right=\"").append(format.getBorder(Border.RIGHT).getDescription()).append("\"").toString());
                                    bw.write(" />");
                                    bw.newLine();
                                }
                                if (!format.getFormat().getFormatString().equals("")) {
                                    bw.write("          <format_string string=\"");
                                    bw.write(format.getFormat().getFormatString());
                                    bw.write("\" />");
                                    bw.newLine();
                                }
                                bw.write("        </format>");
                                bw.newLine();
                            }
                            bw.write("      </col>");
                            bw.newLine();
                        }
                    }
                    bw.write("    </row>");
                    bw.newLine();
                }
                bw.write("  </sheet>");
                bw.newLine();
            }
            bw.write("</workbook>");
            bw.newLine();
            bw.flush();
            bw.close();
        } catch (UnsupportedEncodingException e) {
            System.err.println(e.toString());
        }
    }
}
