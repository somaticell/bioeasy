package jxl.biff.formula;

import jxl.biff.IntegerHelper;

class RangeSeparator extends BinaryOperator implements ParsedThing {
    public String getSymbol() {
        return ":";
    }

    /* access modifiers changed from: package-private */
    public Token getToken() {
        return Token.RANGE;
    }

    /* access modifiers changed from: package-private */
    public int getPrecedence() {
        return 1;
    }

    /* access modifiers changed from: package-private */
    public byte[] getBytes() {
        setVolatile();
        setOperandAlternateCode();
        byte[] funcBytes = super.getBytes();
        byte[] bytes = new byte[(funcBytes.length + 3)];
        System.arraycopy(funcBytes, 0, bytes, 3, funcBytes.length);
        bytes[0] = Token.MEM_FUNC.getCode();
        IntegerHelper.getTwoBytes(funcBytes.length, bytes, 1);
        return bytes;
    }
}
