package jxl.read.biff;

import jxl.Cell;

public interface Formula extends Cell {
    byte[] getFormulaData();
}
