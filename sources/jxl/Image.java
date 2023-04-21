package jxl;

import java.io.File;

public interface Image {
    double getColumn();

    double getHeight();

    byte[] getImageData();

    File getImageFile();

    double getRow();

    double getWidth();
}
