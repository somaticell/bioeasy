package jxl.demo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import jxl.Cell;
import jxl.CellType;
import jxl.FormulaCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.biff.CellReferenceHelper;
import jxl.biff.formula.FormulaException;

public class Formulas {
    public Formulas(Workbook w, OutputStream out, String encoding) throws IOException {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out, (encoding == null || !encoding.equals("UnicodeBig")) ? "UTF8" : encoding));
            ArrayList parseErrors = new ArrayList();
            for (int sheet = 0; sheet < w.getNumberOfSheets(); sheet++) {
                Sheet s = w.getSheet(sheet);
                bw.write(s.getName());
                bw.newLine();
                for (int i = 0; i < s.getRows(); i++) {
                    Cell[] row = s.getRow(i);
                    for (Cell c : row) {
                        if (c.getType() == CellType.NUMBER_FORMULA || c.getType() == CellType.STRING_FORMULA || c.getType() == CellType.BOOLEAN_FORMULA || c.getType() == CellType.DATE_FORMULA || c.getType() == CellType.FORMULA_ERROR) {
                            System.out.println(new StringBuffer().append("cell type ").append(c.getClass().getName()).toString());
                            FormulaCell nfc = (FormulaCell) c;
                            StringBuffer sb = new StringBuffer();
                            CellReferenceHelper.getCellReference(c.getColumn(), c.getRow(), sb);
                            try {
                                bw.write(new StringBuffer().append("Formula in ").append(sb.toString()).append(" value:  ").append(c.getContents()).toString());
                                bw.flush();
                                bw.write(new StringBuffer().append(" formula: ").append(nfc.getFormula()).toString());
                                bw.flush();
                                bw.newLine();
                            } catch (FormulaException e) {
                                bw.newLine();
                                parseErrors.add(new StringBuffer().append(s.getName()).append('!').append(sb.toString()).append(": ").append(e.getMessage()).toString());
                            }
                        }
                    }
                }
            }
            bw.flush();
            bw.close();
            if (parseErrors.size() > 0) {
                System.err.println();
                System.err.println(new StringBuffer().append("There were ").append(parseErrors.size()).append(" errors").toString());
                Iterator i2 = parseErrors.iterator();
                while (i2.hasNext()) {
                    System.err.println(i2.next());
                }
            }
        } catch (UnsupportedEncodingException e2) {
            System.err.println(e2.toString());
        }
    }
}
