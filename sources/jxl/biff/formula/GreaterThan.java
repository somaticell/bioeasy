package jxl.biff.formula;

class GreaterThan extends BinaryOperator implements ParsedThing {
    public String getSymbol() {
        return ">";
    }

    /* access modifiers changed from: package-private */
    public Token getToken() {
        return Token.GREATER_THAN;
    }

    /* access modifiers changed from: package-private */
    public int getPrecedence() {
        return 5;
    }
}
