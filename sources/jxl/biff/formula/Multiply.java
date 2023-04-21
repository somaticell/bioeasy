package jxl.biff.formula;

class Multiply extends BinaryOperator implements ParsedThing {
    public String getSymbol() {
        return "*";
    }

    /* access modifiers changed from: package-private */
    public Token getToken() {
        return Token.MULTIPLY;
    }

    /* access modifiers changed from: package-private */
    public int getPrecedence() {
        return 3;
    }
}
