package jxl.biff.formula;

class Plus extends StringOperator {
    /* access modifiers changed from: package-private */
    public Operator getBinaryOperator() {
        return new Add();
    }

    /* access modifiers changed from: package-private */
    public Operator getUnaryOperator() {
        return new UnaryPlus();
    }
}
