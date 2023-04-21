package jxl.biff.formula;

class Subtract extends BinaryOperator implements ParsedThing {
    public String getSymbol() {
        return "-";
    }

    /* access modifiers changed from: package-private */
    public Token getToken() {
        return Token.SUBTRACT;
    }

    /* access modifiers changed from: package-private */
    public int getPrecedence() {
        return 4;
    }
}
