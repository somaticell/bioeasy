package jxl.biff;

import jxl.Sheet;

public interface WorkbookMethods {
    String getName(int i);

    int getNameIndex(String str);

    Sheet getReadSheet(int i);
}
