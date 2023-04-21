package jxl.demo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import jxl.WorkbookSettings;
import jxl.biff.BaseCompoundFile;
import jxl.read.biff.BiffException;
import jxl.read.biff.CompoundFile;

class PropertySetsReader {
    private CompoundFile compoundFile;
    private BufferedWriter writer;

    public PropertySetsReader(File file, String propertySet, OutputStream os) throws IOException, BiffException {
        this.writer = new BufferedWriter(new OutputStreamWriter(os));
        FileInputStream fis = new FileInputStream(file);
        byte[] d = new byte[1048576];
        int bytesRead = fis.read(d);
        int pos = bytesRead;
        while (bytesRead != -1) {
            if (pos >= d.length) {
                byte[] newArray = new byte[(d.length + 1048576)];
                System.arraycopy(d, 0, newArray, 0, d.length);
                d = newArray;
            }
            bytesRead = fis.read(d, pos, d.length - pos);
            pos += bytesRead;
        }
        int bytesRead2 = pos + 1;
        this.compoundFile = new CompoundFile(d, new WorkbookSettings());
        fis.close();
        if (propertySet == null) {
            displaySets();
        } else {
            displayPropertySet(propertySet, os);
        }
    }

    /* access modifiers changed from: package-private */
    public void displaySets() throws IOException {
        String[] sets = this.compoundFile.getPropertySetNames();
        for (int i = 0; i < sets.length; i++) {
            BaseCompoundFile.PropertyStorage ps = this.compoundFile.getPropertySet(sets[i]);
            this.writer.write(Integer.toString(i));
            this.writer.write(") ");
            this.writer.write(sets[i]);
            this.writer.write("(type ");
            this.writer.write(Integer.toString(ps.type));
            this.writer.write(" size ");
            this.writer.write(Integer.toString(ps.size));
            this.writer.write(" prev ");
            this.writer.write(Integer.toString(ps.previous));
            this.writer.write(" next ");
            this.writer.write(Integer.toString(ps.next));
            this.writer.write(" child ");
            this.writer.write(Integer.toString(ps.child));
            this.writer.write(" start block ");
            this.writer.write(Integer.toString(ps.startBlock));
            this.writer.write(")");
            this.writer.newLine();
        }
        this.writer.flush();
        this.writer.close();
    }

    /* access modifiers changed from: package-private */
    public void displayPropertySet(String ps, OutputStream os) throws IOException, BiffException {
        if (ps.equalsIgnoreCase("SummaryInformation")) {
            ps = BaseCompoundFile.SUMMARY_INFORMATION_NAME;
        } else if (ps.equalsIgnoreCase("DocumentSummaryInformation")) {
            ps = BaseCompoundFile.DOCUMENT_SUMMARY_INFORMATION_NAME;
        } else if (ps.equalsIgnoreCase("CompObj")) {
            ps = BaseCompoundFile.COMP_OBJ_NAME;
        }
        os.write(this.compoundFile.getStream(ps));
    }
}
