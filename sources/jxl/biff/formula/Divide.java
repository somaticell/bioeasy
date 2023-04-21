package jxl.biff.formula;

class Divide extends BinaryOperator implements ParsedThing {
    public String getSymbol() {
        return "/";
    }

    /* access modifiers changed from: package-private */
    public Token getToken() {
        return Token.DIVIDE;
    }

    /* access modifiers changed from: package-private */
    public int getPrecedence() {
        return 3;
    }
}
