package jxl.biff.formula;

class LessThan extends BinaryOperator implements ParsedThing {
    public String getSymbol() {
        return "<";
    }

    /* access modifiers changed from: package-private */
    public Token getToken() {
        return Token.LESS_THAN;
    }

    /* access modifiers changed from: package-private */
    public int getPrecedence() {
        return 5;
    }
}
