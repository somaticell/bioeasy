package jxl;

import java.text.NumberFormat;

public interface NumberCell extends Cell {
    NumberFormat getNumberFormat();

    double getValue();
}
