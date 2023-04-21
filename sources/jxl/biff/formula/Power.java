package jxl.biff.formula;

class Power extends BinaryOperator implements ParsedThing {
    public String getSymbol() {
        return "^";
    }

    /* access modifiers changed from: package-private */
    public Token getToken() {
        return Token.POWER;
    }

    /* access modifiers changed from: package-private */
    public int getPrecedence() {
        return 1;
    }
}
