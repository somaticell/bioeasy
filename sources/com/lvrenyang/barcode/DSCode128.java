package com.lvrenyang.barcode;

import java.util.ArrayList;
import java.util.List;

public class DSCode128 {
    private static final int CODE128_INDEX_CODEB = 100;
    private static final int CODE128_INDEX_CODEC = 99;
    private static final int CODE128_INDEX_STARTB = 104;
    private static final int CODE128_INDEX_STARTC = 105;
    private static final int CODE128_INDEX_STOP = 106;
    private static final char CODE128_MODE_B = 'b';
    private static final char CODE128_MODE_C = 'c';
    private static final String[] code128_pattern_string = {"11011001100", "11001101100", "11001100110", "10010011000", "10010001100", "10001001100", "10011001000", "10011000100", "10001100100", "11001001000", "11001000100", "11000100100", "10110011100", "10011011100", "10011001110", "10111001100", "10011101100", "10011100110", "11001110010", "11001011100", "11001001110", "11011100100", "11001110100", "11101101110", "11101001100", "11100101100", "11100100110", "11101100100", "11100110100", "11100110010", "11011011000", "11011000110", "11000110110", "10100011000", "10001011000", "10001000110", "10110001000", "10001101000", "10001100010", "11010001000", "11000101000", "11000100010", "10110111000", "10110001110", "10001101110", "10111011000", "10111000110", "10001110110", "11101110110", "11010001110", "11000101110", "11011101000", "11011100010", "11011101110", "11101011000", "11101000110", "11100010110", "11101101000", "11101100010", "11100011010", "11101111010", "11001000010", "11110001010", "10100110000", "10100001100", "10010110000", "10010000110", "10000101100", "10000100110", "10110010000", "10110000100", "10011010000", "10011000010", "10000110100", "10000110010", "11000010010", "11001010000", "11110111010", "11000010100", "10001111010", "10100111100", "10010111100", "10010011110", "10111100100", "10011110100", "10011110010", "11110100100", "11110010100", "11110010010", "11011011110", "11011110110", "11110110110", "10101111000", "10100011110", "10001011110", "10111101000", "10111100010", "11110101000", "11110100010", "10111011110", "10111101110", "11101011110", "11110101110", "11010000100", "11010010000", "11010011100", "1100011101011"};

    class CodeUnit {
        public char mode;
        public String str;

        public CodeUnit(char mode2, String str2) {
            this.mode = mode2;
            this.str = str2;
        }
    }

    public boolean[] Encode(String s) {
        List<CodeUnit> splits = new ArrayList<>();
        int i = 0;
        while (i < s.length()) {
            if (i + 1 < s.length()) {
                char c1 = s.charAt(i);
                char c2 = s.charAt(i + 1);
                if (c1 >= '0' && c1 <= '9' && c2 >= '0' && c2 <= '9') {
                    splits.add(new CodeUnit(CODE128_MODE_C, "" + c1 + c2));
                    i += 2;
                } else if (c1 < ' ' || c1 > 127) {
                    i++;
                } else {
                    splits.add(new CodeUnit(CODE128_MODE_B, "" + c1));
                    i++;
                }
            } else {
                char c12 = s.charAt(i);
                if (c12 < ' ' || c12 > 127) {
                    i++;
                } else {
                    splits.add(new CodeUnit(CODE128_MODE_B, "" + c12));
                    i++;
                }
            }
        }
        List<CodeUnit> units = new ArrayList<>();
        for (int i2 = 0; i2 < splits.size(); i2++) {
            if (units.size() == 0) {
                units.add(new CodeUnit(splits.get(i2).mode, splits.get(i2).str));
            } else {
                CodeUnit unit = units.get(units.size() - 1);
                if (splits.get(i2).mode == unit.mode) {
                    unit.str += splits.get(i2).str;
                } else {
                    units.add(new CodeUnit(splits.get(i2).mode, splits.get(i2).str));
                }
            }
        }
        List<Integer> ids = new ArrayList<>();
        for (int i3 = 0; i3 < units.size(); i3++) {
            CodeUnit unit2 = units.get(i3);
            if (i3 == 0) {
                if (unit2.mode == 'c') {
                    ids.add(105);
                } else if (unit2.mode == 'b') {
                    ids.add(104);
                }
            } else if (unit2.mode == 'c') {
                ids.add(99);
            } else if (unit2.mode == 'b') {
                ids.add(100);
            }
            if (unit2.mode == 'c') {
                for (int j = 0; j < unit2.str.length(); j += 2) {
                    ids.add(Integer.valueOf(((unit2.str.charAt(j) - '0') * 10) + (unit2.str.charAt(j + 1) - '0')));
                }
            } else if (unit2.mode == 'b') {
                for (int j2 = 0; j2 < unit2.str.length(); j2++) {
                    ids.add(Integer.valueOf(unit2.str.charAt(j2) - ' '));
                }
            }
        }
        int check = 0;
        for (int i4 = 0; i4 < ids.size(); i4++) {
            if (i4 == 0) {
                check = ids.get(i4).intValue();
            } else {
                check += ids.get(i4).intValue() * i4;
            }
        }
        ids.add(Integer.valueOf(check % 103));
        ids.add(106);
        String scode = "";
        for (int i5 = 0; i5 < ids.size(); i5++) {
            scode = scode + code128_pattern_string[ids.get(i5).intValue()];
        }
        boolean[] bcode = new boolean[scode.length()];
        for (int i6 = 0; i6 < scode.length(); i6++) {
            bcode[i6] = scode.charAt(i6) == '1';
        }
        return bcode;
    }
}
