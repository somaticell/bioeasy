package jxl.write.biff;

import jxl.biff.FontRecord;
import jxl.format.Font;
import jxl.write.WriteException;

public class WritableFontRecord extends FontRecord {
    protected WritableFontRecord(String fn, int ps, int bold, boolean it, int us, int ci, int ss) {
        super(fn, ps, bold, it, us, ci, ss);
    }

    protected WritableFontRecord(Font f) {
        super(f);
    }

    /* access modifiers changed from: protected */
    public void setPointSize(int pointSize) throws WriteException {
        if (isInitialized()) {
            throw new JxlWriteException(JxlWriteException.formatInitialized);
        }
        super.setFontPointSize(pointSize);
    }

    /* access modifiers changed from: protected */
    public void setBoldStyle(int boldStyle) throws WriteException {
        if (isInitialized()) {
            throw new JxlWriteException(JxlWriteException.formatInitialized);
        }
        super.setFontBoldStyle(boldStyle);
    }

    /* access modifiers changed from: protected */
    public void setItalic(boolean italic) throws WriteException {
        if (isInitialized()) {
            throw new JxlWriteException(JxlWriteException.formatInitialized);
        }
        super.setFontItalic(italic);
    }

    /* access modifiers changed from: protected */
    public void setUnderlineStyle(int us) throws WriteException {
        if (isInitialized()) {
            throw new JxlWriteException(JxlWriteException.formatInitialized);
        }
        super.setFontUnderlineStyle(us);
    }

    /* access modifiers changed from: protected */
    public void setColour(int colour) throws WriteException {
        if (isInitialized()) {
            throw new JxlWriteException(JxlWriteException.formatInitialized);
        }
        super.setFontColour(colour);
    }

    /* access modifiers changed from: protected */
    public void setScriptStyle(int scriptStyle) throws WriteException {
        if (isInitialized()) {
            throw new JxlWriteException(JxlWriteException.formatInitialized);
        }
        super.setFontScriptStyle(scriptStyle);
    }

    /* access modifiers changed from: protected */
    public void setStruckout(boolean os) throws WriteException {
        if (isInitialized()) {
            throw new JxlWriteException(JxlWriteException.formatInitialized);
        }
        super.setFontStruckout(os);
    }
}
