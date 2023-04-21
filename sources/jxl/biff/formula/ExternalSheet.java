package jxl.biff.formula;

import jxl.read.biff.BOFRecord;

public interface ExternalSheet {
    int getExternalSheetIndex(int i);

    int getExternalSheetIndex(String str);

    String getExternalSheetName(int i);

    int getLastExternalSheetIndex(int i);

    int getLastExternalSheetIndex(String str);

    BOFRecord getWorkbookBof();
}
