package jxl.demo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import jxl.Cell;
import jxl.CellFeatures;
import jxl.CellReferenceHelper;
import jxl.Sheet;
import jxl.Workbook;

public class Features {
    public Features(Workbook w, OutputStream out, String encoding) throws IOException {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out, (encoding == null || !encoding.equals("UnicodeBig")) ? "UTF8" : encoding));
            new ArrayList();
            for (int sheet = 0; sheet < w.getNumberOfSheets(); sheet++) {
                Sheet s = w.getSheet(sheet);
                bw.write(s.getName());
                bw.newLine();
                for (int i = 0; i < s.getRows(); i++) {
                    Cell[] row = s.getRow(i);
                    for (Cell c : row) {
                        if (c.getCellFeatures() != null) {
                            CellFeatures features = c.getCellFeatures();
                            StringBuffer sb = new StringBuffer();
                            CellReferenceHelper.getCellReference(c.getColumn(), c.getRow(), sb);
                            bw.write(new StringBuffer().append("Cell ").append(sb.toString()).append(" contents:  ").append(c.getContents()).toString());
                            bw.flush();
                            bw.write(new StringBuffer().append(" comment: ").append(features.getComment()).toString());
                            bw.flush();
                            bw.newLine();
                        }
                    }
                }
            }
            bw.flush();
            bw.close();
        } catch (UnsupportedEncodingException e) {
            System.err.println(e.toString());
        }
    }
}
