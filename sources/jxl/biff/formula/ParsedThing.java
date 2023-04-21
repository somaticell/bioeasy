package jxl.biff.formula;

interface ParsedThing {
    int read(byte[] bArr, int i) throws FormulaException;
}
