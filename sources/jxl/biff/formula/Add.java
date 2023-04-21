package jxl.biff.formula;

class Add extends BinaryOperator implements ParsedThing {
    public String getSymbol() {
        return "+";
    }

    /* access modifiers changed from: package-private */
    public Token getToken() {
        return Token.ADD;
    }

    /* access modifiers changed from: package-private */
    public int getPrecedence() {
        return 4;
    }
}
