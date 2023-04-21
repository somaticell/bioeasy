package jxl.write;

import java.text.DecimalFormat;
import jxl.biff.DisplayFormat;
import jxl.write.biff.NumberFormatRecord;

public class NumberFormat extends NumberFormatRecord implements DisplayFormat {
    public NumberFormat(String format) {
        super(format);
        new DecimalFormat(format);
    }
}
