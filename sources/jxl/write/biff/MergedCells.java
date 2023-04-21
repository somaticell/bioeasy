package jxl.write.biff;

import common.Assert;
import common.Logger;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import jxl.Cell;
import jxl.CellType;
import jxl.Range;
import jxl.biff.SheetRangeImpl;
import jxl.write.Blank;
import jxl.write.WritableSheet;
import jxl.write.WriteException;

class MergedCells {
    static Class class$jxl$write$biff$MergedCells = null;
    private static Logger logger = null;
    private static final int maxRangesPerSheet = 1020;
    private ArrayList ranges = new ArrayList();
    private WritableSheet sheet;

    static {
        Class cls;
        if (class$jxl$write$biff$MergedCells == null) {
            cls = class$("jxl.write.biff.MergedCells");
            class$jxl$write$biff$MergedCells = cls;
        } else {
            cls = class$jxl$write$biff$MergedCells;
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

    public MergedCells(WritableSheet ws) {
        this.sheet = ws;
    }

    /* access modifiers changed from: package-private */
    public void add(Range r) {
        this.ranges.add(r);
    }

    /* access modifiers changed from: package-private */
    public void insertRow(int row) {
        Iterator i = this.ranges.iterator();
        while (i.hasNext()) {
            ((SheetRangeImpl) i.next()).insertRow(row);
        }
    }

    /* access modifiers changed from: package-private */
    public void insertColumn(int col) {
        Iterator i = this.ranges.iterator();
        while (i.hasNext()) {
            ((SheetRangeImpl) i.next()).insertColumn(col);
        }
    }

    /* access modifiers changed from: package-private */
    public void removeColumn(int col) {
        Iterator i = this.ranges.iterator();
        while (i.hasNext()) {
            SheetRangeImpl sr = (SheetRangeImpl) i.next();
            if (sr.getTopLeft().getColumn() == col && sr.getBottomRight().getColumn() == col) {
                this.ranges.remove(this.ranges.indexOf(sr));
            } else {
                sr.removeColumn(col);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void removeRow(int row) {
        Iterator i = this.ranges.iterator();
        while (i.hasNext()) {
            SheetRangeImpl sr = (SheetRangeImpl) i.next();
            if (sr.getTopLeft().getRow() == row && sr.getBottomRight().getRow() == row) {
                i.remove();
            } else {
                sr.removeRow(row);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public Range[] getMergedCells() {
        Range[] cells = new Range[this.ranges.size()];
        for (int i = 0; i < cells.length; i++) {
            cells[i] = (Range) this.ranges.get(i);
        }
        return cells;
    }

    /* access modifiers changed from: package-private */
    public void unmergeCells(Range r) {
        int index = this.ranges.indexOf(r);
        if (index != -1) {
            this.ranges.remove(index);
        }
    }

    /* access modifiers changed from: package-private */
    public void checkIntersections() {
        ArrayList newcells = new ArrayList(this.ranges.size());
        Iterator mci = this.ranges.iterator();
        while (mci.hasNext()) {
            SheetRangeImpl r = (SheetRangeImpl) mci.next();
            Iterator i = newcells.iterator();
            boolean intersects = false;
            while (i.hasNext() && !intersects) {
                if (((SheetRangeImpl) i.next()).intersects(r)) {
                    logger.warn(new StringBuffer().append("Could not merge cells ").append(r).append(" as they clash with an existing set of merged cells.").toString());
                    intersects = true;
                }
            }
            if (!intersects) {
                newcells.add(r);
            }
        }
        this.ranges = newcells;
    }

    private void checkRanges() {
        int i = 0;
        while (i < this.ranges.size()) {
            try {
                SheetRangeImpl range = (SheetRangeImpl) this.ranges.get(i);
                Cell tl = range.getTopLeft();
                Cell br = range.getBottomRight();
                boolean found = false;
                for (int c = tl.getColumn(); c <= br.getColumn(); c++) {
                    for (int r = tl.getRow(); r <= br.getRow(); r++) {
                        if (this.sheet.getCell(c, r).getType() != CellType.EMPTY) {
                            if (!found) {
                                found = true;
                            } else {
                                logger.warn(new StringBuffer().append("Range ").append(range).append(" contains more than one data cell.  ").append("Setting the other cells to blank.").toString());
                                this.sheet.addCell(new Blank(c, r));
                            }
                        }
                    }
                }
                i++;
            } catch (WriteException e) {
                Assert.verify(false);
                return;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void write(File outputFile) throws IOException {
        if (this.ranges.size() != 0) {
            checkIntersections();
            checkRanges();
            if (this.ranges.size() < 1020) {
                outputFile.write(new MergedCellsRecord(this.ranges));
                return;
            }
            int numRecordsRequired = (this.ranges.size() / 1020) + 1;
            int pos = 0;
            for (int i = 0; i < numRecordsRequired; i++) {
                int numranges = Math.min(1020, this.ranges.size() - pos);
                ArrayList cells = new ArrayList(numranges);
                for (int j = 0; j < numranges; j++) {
                    cells.add(this.ranges.get(pos + j));
                }
                outputFile.write(new MergedCellsRecord(cells));
                pos += numranges;
            }
        }
    }
}
