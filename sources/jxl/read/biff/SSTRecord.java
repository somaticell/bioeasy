package jxl.read.biff;

import common.Assert;
import jxl.WorkbookSettings;
import jxl.biff.IntegerHelper;
import jxl.biff.RecordData;
import jxl.biff.StringHelper;

class SSTRecord extends RecordData {
    private int[] continuationBreaks;
    private String[] strings;
    private int totalStrings;
    private int uniqueStrings;

    /* renamed from: jxl.read.biff.SSTRecord$1  reason: invalid class name */
    static class AnonymousClass1 {
    }

    private static class ByteArrayHolder {
        public byte[] bytes;

        private ByteArrayHolder() {
        }

        ByteArrayHolder(AnonymousClass1 x0) {
            this();
        }
    }

    private static class BooleanHolder {
        public boolean value;

        private BooleanHolder() {
        }

        BooleanHolder(AnonymousClass1 x0) {
            this();
        }
    }

    public SSTRecord(Record t, Record[] continuations, WorkbookSettings ws) {
        super(t);
        int totalRecordLength = 0;
        for (Record length : continuations) {
            totalRecordLength += length.getLength();
        }
        byte[] data = new byte[(totalRecordLength + getRecord().getLength())];
        System.arraycopy(getRecord().getData(), 0, data, 0, getRecord().getLength());
        int pos = 0 + getRecord().getLength();
        this.continuationBreaks = new int[continuations.length];
        for (int i = 0; i < continuations.length; i++) {
            Record r = continuations[i];
            System.arraycopy(r.getData(), 0, data, pos, r.getLength());
            this.continuationBreaks[i] = pos;
            pos += r.getLength();
        }
        this.totalStrings = IntegerHelper.getInt(data[0], data[1], data[2], data[3]);
        this.uniqueStrings = IntegerHelper.getInt(data[4], data[5], data[6], data[7]);
        this.strings = new String[this.uniqueStrings];
        readStrings(data, 8, ws);
    }

    private void readStrings(byte[] data, int offset, WorkbookSettings ws) {
        String s;
        int pos = offset;
        int formattingRuns = 0;
        int extendedRunLength = 0;
        for (int i = 0; i < this.uniqueStrings; i++) {
            int numChars = IntegerHelper.getInt(data[pos], data[pos + 1]);
            int pos2 = pos + 2;
            byte optionFlags = data[pos2];
            int pos3 = pos2 + 1;
            boolean extendedString = (optionFlags & 4) != 0;
            boolean richString = (optionFlags & 8) != 0;
            if (richString) {
                formattingRuns = IntegerHelper.getInt(data[pos3], data[pos3 + 1]);
                pos3 += 2;
            }
            if (extendedString) {
                extendedRunLength = IntegerHelper.getInt(data[pos3], data[pos3 + 1], data[pos3 + 2], data[pos3 + 3]);
                pos3 += 4;
            }
            boolean asciiEncoding = (optionFlags & 1) == 0;
            ByteArrayHolder bah = new ByteArrayHolder((AnonymousClass1) null);
            BooleanHolder bh = new BooleanHolder((AnonymousClass1) null);
            bh.value = asciiEncoding;
            pos = pos3 + getChars(data, bah, pos3, bh, numChars);
            if (bh.value) {
                s = StringHelper.getString(bah.bytes, numChars, 0, ws);
            } else {
                s = StringHelper.getUnicodeString(bah.bytes, numChars, 0);
            }
            this.strings[i] = s;
            if (richString) {
                pos += formattingRuns * 4;
            }
            if (extendedString) {
                pos += extendedRunLength;
            }
            if (pos > data.length) {
                Assert.verify(false, "pos exceeds record length");
            }
        }
    }

    private int getChars(byte[] source, ByteArrayHolder bah, int pos, BooleanHolder ascii, int numChars) {
        int charsRead;
        int i = 0;
        boolean spansBreak = false;
        if (ascii.value) {
            bah.bytes = new byte[numChars];
        } else {
            bah.bytes = new byte[(numChars * 2)];
        }
        while (i < this.continuationBreaks.length && !spansBreak) {
            if (pos > this.continuationBreaks[i] || bah.bytes.length + pos <= this.continuationBreaks[i]) {
                spansBreak = false;
            } else {
                spansBreak = true;
            }
            if (!spansBreak) {
                i++;
            }
        }
        if (!spansBreak) {
            System.arraycopy(source, pos, bah.bytes, 0, bah.bytes.length);
            return bah.bytes.length;
        }
        int breakpos = this.continuationBreaks[i];
        System.arraycopy(source, pos, bah.bytes, 0, breakpos - pos);
        int bytesRead = breakpos - pos;
        if (ascii.value) {
            charsRead = bytesRead;
        } else {
            charsRead = bytesRead / 2;
        }
        return bytesRead + getContinuedString(source, bah, bytesRead, i, ascii, numChars - charsRead);
    }

    private int getContinuedString(byte[] source, ByteArrayHolder bah, int destPos, int contBreakIndex, BooleanHolder ascii, int charsLeft) {
        int length;
        int chars;
        int length2;
        int length3;
        int breakpos = this.continuationBreaks[contBreakIndex];
        int bytesRead = 0;
        while (charsLeft > 0) {
            Assert.verify(contBreakIndex < this.continuationBreaks.length, "continuation break index");
            if (ascii.value && source[breakpos] == 0) {
                if (contBreakIndex == this.continuationBreaks.length - 1) {
                    length3 = charsLeft;
                } else {
                    length3 = Math.min(charsLeft, (this.continuationBreaks[contBreakIndex + 1] - breakpos) - 1);
                }
                System.arraycopy(source, breakpos + 1, bah.bytes, destPos, length3);
                destPos += length3;
                bytesRead += length3 + 1;
                charsLeft -= length3;
                ascii.value = true;
            } else if (!ascii.value && source[breakpos] != 0) {
                if (contBreakIndex == this.continuationBreaks.length - 1) {
                    length2 = charsLeft * 2;
                } else {
                    length2 = Math.min(charsLeft * 2, (this.continuationBreaks[contBreakIndex + 1] - breakpos) - 1);
                }
                System.arraycopy(source, breakpos + 1, bah.bytes, destPos, length2);
                destPos += length2;
                bytesRead += length2 + 1;
                charsLeft -= length2 / 2;
                ascii.value = false;
            } else if (ascii.value || source[breakpos] != 0) {
                byte[] oldBytes = bah.bytes;
                bah.bytes = new byte[((destPos * 2) + (charsLeft * 2))];
                for (int j = 0; j < destPos; j++) {
                    bah.bytes[j * 2] = oldBytes[j];
                }
                int destPos2 = destPos * 2;
                if (contBreakIndex == this.continuationBreaks.length - 1) {
                    length = charsLeft * 2;
                } else {
                    length = Math.min(charsLeft * 2, (this.continuationBreaks[contBreakIndex + 1] - breakpos) - 1);
                }
                System.arraycopy(source, breakpos + 1, bah.bytes, destPos2, length);
                destPos = destPos2 + length;
                bytesRead += length + 1;
                charsLeft -= length / 2;
                ascii.value = false;
            } else {
                if (contBreakIndex == this.continuationBreaks.length - 1) {
                    chars = charsLeft;
                } else {
                    chars = Math.min(charsLeft, (this.continuationBreaks[contBreakIndex + 1] - breakpos) - 1);
                }
                for (int j2 = 0; j2 < chars; j2++) {
                    bah.bytes[destPos] = source[breakpos + j2 + 1];
                    destPos += 2;
                }
                bytesRead += chars + 1;
                charsLeft -= chars;
                ascii.value = false;
            }
            contBreakIndex++;
            if (contBreakIndex < this.continuationBreaks.length) {
                breakpos = this.continuationBreaks[contBreakIndex];
            }
        }
        return bytesRead;
    }

    public String getString(int index) {
        Assert.verify(index < this.uniqueStrings);
        return this.strings[index];
    }
}
