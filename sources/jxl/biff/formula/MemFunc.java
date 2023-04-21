package jxl.biff.formula;

import java.util.Stack;
import jxl.biff.IntegerHelper;

class MemFunc extends Operand implements ParsedThing {
    private int length;
    private ParseItem[] subExpression;

    public int read(byte[] data, int pos) {
        this.length = IntegerHelper.getInt(data[pos], data[pos + 1]);
        return 2;
    }

    public void getOperands(Stack s) {
    }

    public void getString(StringBuffer buf) {
        if (this.subExpression.length == 1) {
            this.subExpression[0].getString(buf);
        } else if (this.subExpression.length == 2) {
            this.subExpression[1].getString(buf);
            buf.append(':');
            this.subExpression[0].getString(buf);
        }
    }

    /* access modifiers changed from: package-private */
    public byte[] getBytes() {
        return null;
    }

    /* access modifiers changed from: package-private */
    public int getPrecedence() {
        return 5;
    }

    public int getLength() {
        return this.length;
    }

    public void setSubExpression(ParseItem[] pi) {
        this.subExpression = pi;
    }
}
