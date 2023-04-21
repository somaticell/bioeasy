package jxl.biff.formula;

class UnaryPlus extends UnaryOperator implements ParsedThing {
    public String getSymbol() {
        return "+";
    }

    /* access modifiers changed from: package-private */
    public Token getToken() {
        return Token.UNARY_PLUS;
    }

    /* access modifiers changed from: package-private */
    public int getPrecedence() {
        return 2;
    }
}
