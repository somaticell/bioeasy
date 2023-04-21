package jxl.write.biff;

public class ColumnsExceededException extends JxlWriteException {
    public ColumnsExceededException() {
        super(maxColumnsExceeded);
    }
}
