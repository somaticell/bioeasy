package jxl.read.biff;

public class BiffRecordReader {
    private File file;
    private Record record;

    public BiffRecordReader(File f) {
        this.file = f;
    }

    public boolean hasNext() {
        return this.file.hasNext();
    }

    public Record next() {
        this.record = this.file.next();
        return this.record;
    }

    public int getPos() {
        return (this.file.getPos() - this.record.getLength()) - 4;
    }
}
