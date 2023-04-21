package jxl.biff.formula;

class Equal extends BinaryOperator implements ParsedThing {
    public String getSymbol() {
        return "=";
    }

    /* access modifiers changed from: package-private */
    public Token getToken() {
        return Token.EQUAL;
    }

    /* access modifiers changed from: package-private */
    public int getPrecedence() {
        return 5;
    }
}
