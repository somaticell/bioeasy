package jxl.biff.formula;

class Percent extends UnaryOperator implements ParsedThing {
    public String getSymbol() {
        return "%";
    }

    public void getString(StringBuffer buf) {
        getOperands()[0].getString(buf);
        buf.append(getSymbol());
    }

    /* access modifiers changed from: package-private */
    public Token getToken() {
        return Token.PERCENT;
    }

    /* access modifiers changed from: package-private */
    public int getPrecedence() {
        return 5;
    }
}
