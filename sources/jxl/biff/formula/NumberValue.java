package jxl.biff.formula;

abstract class NumberValue extends Operand implements ParsedThing {
    public abstract double getValue();

    protected NumberValue() {
    }

    public void getString(StringBuffer buf) {
        buf.append(Double.toString(getValue()));
    }
}
