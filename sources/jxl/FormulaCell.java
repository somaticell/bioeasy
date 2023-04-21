package jxl;

import jxl.biff.formula.FormulaException;

public interface FormulaCell extends Cell {
    String getFormula() throws FormulaException;
}
