package jxl.biff.formula;

class Minus extends StringOperator {
    /* access modifiers changed from: package-private */
    public Operator getBinaryOperator() {
        return new Subtract();
    }

    /* access modifiers changed from: package-private */
    public Operator getUnaryOperator() {
        return new UnaryMinus();
    }
}
