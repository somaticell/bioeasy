package jxl;

import java.text.DateFormat;
import java.util.Date;

public interface DateCell extends Cell {
    Date getDate();

    DateFormat getDateFormat();

    boolean isTime();
}
