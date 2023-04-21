package com.facebook.imageutils;

import com.alibaba.fastjson.asm.Opcodes;
import com.facebook.common.logging.FLog;
import java.io.IOException;
import java.io.InputStream;

class TiffUtil {
    private static final Class<?> TAG = TiffUtil.class;
    public static final int TIFF_BYTE_ORDER_BIG_END = 1296891946;
    public static final int TIFF_BYTE_ORDER_LITTLE_END = 1229531648;
    public static final int TIFF_TAG_ORIENTATION = 274;
    public static final int TIFF_TYPE_SHORT = 3;

    TiffUtil() {
    }

    public static int getAutoRotateAngleFromOrientation(int orientation) {
        switch (orientation) {
            case 1:
                return 0;
            case 3:
                return Opcodes.GETFIELD;
            case 6:
                return 90;
            case 8:
                return 270;
            default:
                FLog.i(TAG, "Unsupported orientation");
                return 0;
        }
    }

    public static int readOrientationFromTIFF(InputStream is, int length) throws IOException {
        TiffHeader tiffHeader = new TiffHeader();
        int length2 = readTiffHeader(is, length, tiffHeader);
        int toSkip = tiffHeader.firstIfdOffset - 8;
        if (length2 == 0 || toSkip > length2) {
            return 0;
        }
        is.skip((long) toSkip);
        return getOrientationFromTiffEntry(is, moveToTiffEntryWithTag(is, length2 - toSkip, tiffHeader.isLittleEndian, TIFF_TAG_ORIENTATION), tiffHeader.isLittleEndian);
    }

    private static class TiffHeader {
        int byteOrder;
        int firstIfdOffset;
        boolean isLittleEndian;

        private TiffHeader() {
        }
    }

    private static int readTiffHeader(InputStream is, int length, TiffHeader tiffHeader) throws IOException {
        if (length <= 8) {
            return 0;
        }
        tiffHeader.byteOrder = StreamProcessor.readPackedInt(is, 4, false);
        int length2 = length - 4;
        if (tiffHeader.byteOrder == 1229531648 || tiffHeader.byteOrder == 1296891946) {
            tiffHeader.isLittleEndian = tiffHeader.byteOrder == 1229531648;
            tiffHeader.firstIfdOffset = StreamProcessor.readPackedInt(is, 4, tiffHeader.isLittleEndian);
            int length3 = length2 - 4;
            if (tiffHeader.firstIfdOffset >= 8 && tiffHeader.firstIfdOffset - 8 <= length3) {
                return length3;
            }
            FLog.e(TAG, "Invalid offset");
            return 0;
        }
        FLog.e(TAG, "Invalid TIFF header");
        return 0;
    }

    private static int moveToTiffEntryWithTag(InputStream is, int length, boolean isLittleEndian, int tagToFind) throws IOException {
        if (length < 14) {
            return 0;
        }
        int numEntries = StreamProcessor.readPackedInt(is, 2, isLittleEndian);
        int length2 = length - 2;
        while (true) {
            int numEntries2 = numEntries;
            numEntries = numEntries2 - 1;
            if (numEntries2 <= 0 || length2 < 12) {
                return 0;
            }
            int length3 = length2 - 2;
            if (StreamProcessor.readPackedInt(is, 2, isLittleEndian) == tagToFind) {
                return length3;
            }
            is.skip(10);
            length2 = length3 - 10;
        }
    }

    private static int getOrientationFromTiffEntry(InputStream is, int length, boolean isLittleEndian) throws IOException {
        if (length < 10 || StreamProcessor.readPackedInt(is, 2, isLittleEndian) != 3 || StreamProcessor.readPackedInt(is, 4, isLittleEndian) != 1) {
            return 0;
        }
        int readPackedInt = StreamProcessor.readPackedInt(is, 2, isLittleEndian);
        int readPackedInt2 = StreamProcessor.readPackedInt(is, 2, isLittleEndian);
        return readPackedInt;
    }
}
