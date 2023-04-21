package jxl.biff.drawing;

import common.Logger;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import jxl.WorkbookSettings;
import jxl.biff.IntegerHelper;
import jxl.write.biff.File;

public class SheetDrawingWriter {
    static Class class$jxl$biff$drawing$SheetDrawingWriter;
    private static Logger logger;
    private Chart[] charts = new Chart[0];
    private ArrayList drawings;
    private boolean drawingsModified;
    private WorkbookSettings workbookSettings;

    static {
        Class cls;
        if (class$jxl$biff$drawing$SheetDrawingWriter == null) {
            cls = class$("jxl.biff.drawing.SheetDrawingWriter");
            class$jxl$biff$drawing$SheetDrawingWriter = cls;
        } else {
            cls = class$jxl$biff$drawing$SheetDrawingWriter;
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

    public SheetDrawingWriter(WorkbookSettings ws) {
    }

    public void setDrawings(ArrayList dr, boolean mod) {
        this.drawings = dr;
        this.drawingsModified = mod;
    }

    public void write(File outputFile) throws IOException {
        if (this.drawings.size() != 0 || this.charts.length != 0) {
            boolean modified = this.drawingsModified;
            int numImages = this.drawings.size();
            Iterator i = this.drawings.iterator();
            while (i.hasNext() && !modified) {
                if (((DrawingGroupObject) i.next()).getOrigin() != Origin.READ) {
                    modified = true;
                }
            }
            if (numImages > 0 && !modified && !((DrawingGroupObject) this.drawings.get(0)).isFirst()) {
                modified = true;
            }
            if (numImages == 0 && this.charts.length == 1 && this.charts[0].getMsoDrawingRecord() == null) {
                modified = false;
            }
            if (!modified) {
                writeUnmodified(outputFile);
                return;
            }
            Object[] spContainerData = new Object[(this.charts.length + numImages)];
            int length = 0;
            EscherContainer firstSpContainer = null;
            for (int i2 = 0; i2 < numImages; i2++) {
                EscherContainer spc = ((DrawingGroupObject) this.drawings.get(i2)).getSpContainer();
                byte[] data = spc.getData();
                spContainerData[i2] = data;
                if (i2 == 0) {
                    firstSpContainer = spc;
                } else {
                    length += data.length;
                }
            }
            for (int i3 = 0; i3 < this.charts.length; i3++) {
                EscherContainer spContainer = this.charts[i3].getSpContainer();
                byte[] data2 = spContainer.setHeaderData(spContainer.getBytes());
                spContainerData[i3 + numImages] = data2;
                if (i3 == 0 && numImages == 0) {
                    firstSpContainer = spContainer;
                } else {
                    length += data2.length;
                }
            }
            DgContainer dgContainer = new DgContainer();
            dgContainer.add(new Dg(this.charts.length + numImages));
            SpgrContainer spgrContainer = new SpgrContainer();
            SpContainer spContainer2 = new SpContainer();
            spContainer2.add(new Spgr());
            spContainer2.add(new Sp(ShapeType.MIN, 1024, 5));
            spgrContainer.add(spContainer2);
            spgrContainer.add(firstSpContainer);
            dgContainer.add(spgrContainer);
            byte[] firstMsoData = dgContainer.getData();
            IntegerHelper.getFourBytes(IntegerHelper.getInt(firstMsoData[4], firstMsoData[5], firstMsoData[6], firstMsoData[7]) + length, firstMsoData, 4);
            IntegerHelper.getFourBytes(IntegerHelper.getInt(firstMsoData[28], firstMsoData[29], firstMsoData[30], firstMsoData[31]) + length, firstMsoData, 28);
            if (numImages > 0 && ((DrawingGroupObject) this.drawings.get(0)).isFormObject()) {
                byte[] msodata2 = new byte[(firstMsoData.length - 8)];
                System.arraycopy(firstMsoData, 0, msodata2, 0, msodata2.length);
                firstMsoData = msodata2;
            }
            outputFile.write(new MsoDrawingRecord(firstMsoData));
            if (numImages > 0) {
                ((DrawingGroupObject) this.drawings.get(0)).writeAdditionalRecords(outputFile);
            } else {
                Chart chart = this.charts[0];
                outputFile.write(chart.getObjRecord());
                outputFile.write(chart);
            }
            for (int i4 = 1; i4 < spContainerData.length; i4++) {
                byte[] bytes = (byte[]) spContainerData[i4];
                if (i4 < numImages && ((DrawingGroupObject) this.drawings.get(i4)).isFormObject()) {
                    byte[] bytes2 = new byte[(bytes.length - 8)];
                    System.arraycopy(bytes, 0, bytes2, 0, bytes2.length);
                    bytes = bytes2;
                }
                outputFile.write(new MsoDrawingRecord(bytes));
                if (i4 < numImages) {
                    ((DrawingGroupObject) this.drawings.get(i4)).writeAdditionalRecords(outputFile);
                } else {
                    Chart chart2 = this.charts[i4 - numImages];
                    outputFile.write(chart2.getObjRecord());
                    outputFile.write(chart2);
                }
            }
            Iterator i5 = this.drawings.iterator();
            while (i5.hasNext()) {
                ((DrawingGroupObject) i5.next()).writeTailRecords(outputFile);
            }
        }
    }

    private void writeUnmodified(File outputFile) throws IOException {
        if (this.charts.length != 0 || this.drawings.size() != 0) {
            if (this.charts.length == 0 && this.drawings.size() != 0) {
                Iterator i = this.drawings.iterator();
                while (i.hasNext()) {
                    DrawingGroupObject d = (DrawingGroupObject) i.next();
                    outputFile.write(d.getMsoDrawingRecord());
                    d.writeAdditionalRecords(outputFile);
                }
                Iterator i2 = this.drawings.iterator();
                while (i2.hasNext()) {
                    ((DrawingGroupObject) i2.next()).writeTailRecords(outputFile);
                }
            } else if (this.drawings.size() != 0 || this.charts.length == 0) {
                int numDrawings = this.drawings.size();
                int length = 0;
                EscherContainer[] spContainers = new EscherContainer[(this.charts.length + numDrawings)];
                boolean[] isFormObject = new boolean[(this.charts.length + numDrawings)];
                for (int i3 = 0; i3 < numDrawings; i3++) {
                    DrawingGroupObject d2 = (DrawingGroupObject) this.drawings.get(i3);
                    spContainers[i3] = d2.getSpContainer();
                    if (i3 > 0) {
                        length += spContainers[i3].getLength();
                    }
                    if (d2.isFormObject()) {
                        isFormObject[i3] = true;
                    }
                }
                for (int i4 = 0; i4 < this.charts.length; i4++) {
                    spContainers[i4 + numDrawings] = this.charts[i4].getSpContainer();
                    length += spContainers[i4 + numDrawings].getLength();
                }
                DgContainer dgContainer = new DgContainer();
                dgContainer.add(new Dg(this.charts.length + numDrawings));
                SpgrContainer spgrContainer = new SpgrContainer();
                SpContainer spContainer = new SpContainer();
                spContainer.add(new Spgr());
                spContainer.add(new Sp(ShapeType.MIN, 1024, 5));
                spgrContainer.add(spContainer);
                spgrContainer.add(spContainers[0]);
                dgContainer.add(spgrContainer);
                byte[] firstMsoData = dgContainer.getData();
                IntegerHelper.getFourBytes(IntegerHelper.getInt(firstMsoData[4], firstMsoData[5], firstMsoData[6], firstMsoData[7]) + length, firstMsoData, 4);
                IntegerHelper.getFourBytes(IntegerHelper.getInt(firstMsoData[28], firstMsoData[29], firstMsoData[30], firstMsoData[31]) + length, firstMsoData, 28);
                if (isFormObject[0]) {
                    byte[] cbytes = new byte[(firstMsoData.length - 8)];
                    System.arraycopy(firstMsoData, 0, cbytes, 0, cbytes.length);
                    firstMsoData = cbytes;
                }
                outputFile.write(new MsoDrawingRecord(firstMsoData));
                ((DrawingGroupObject) this.drawings.get(0)).writeAdditionalRecords(outputFile);
                for (int i5 = 1; i5 < spContainers.length; i5++) {
                    byte[] bytes2 = spContainers[i5].setHeaderData(spContainers[i5].getBytes());
                    if (isFormObject[i5]) {
                        byte[] cbytes2 = new byte[(bytes2.length - 8)];
                        System.arraycopy(bytes2, 0, cbytes2, 0, cbytes2.length);
                        bytes2 = cbytes2;
                    }
                    outputFile.write(new MsoDrawingRecord(bytes2));
                    if (i5 < numDrawings) {
                        ((DrawingGroupObject) this.drawings.get(i5)).writeAdditionalRecords(outputFile);
                    } else {
                        Chart chart = this.charts[i5 - numDrawings];
                        outputFile.write(chart.getObjRecord());
                        outputFile.write(chart);
                    }
                }
                Iterator i6 = this.drawings.iterator();
                while (i6.hasNext()) {
                    ((DrawingGroupObject) i6.next()).writeTailRecords(outputFile);
                }
            } else {
                for (Chart curChart : this.charts) {
                    if (curChart.getMsoDrawingRecord() != null) {
                        outputFile.write(curChart.getMsoDrawingRecord());
                    }
                    if (curChart.getObjRecord() != null) {
                        outputFile.write(curChart.getObjRecord());
                    }
                    outputFile.write(curChart);
                }
            }
        }
    }

    public void setCharts(Chart[] ch) {
        this.charts = ch;
    }

    public Chart[] getCharts() {
        return this.charts;
    }
}
