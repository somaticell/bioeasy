package com.lvrenyang.qrcode;

import com.alibaba.fastjson.asm.Opcodes;
import java.util.ArrayList;
import java.util.List;

class RSBlock {
    private static final int[][] RS_BLOCK_TABLE;
    private int dataCount;
    private int totalCount;

    static {
        int[][] iArr = new int[Opcodes.IF_ICMPNE][];
        iArr[0] = new int[]{1, 26, 19};
        iArr[1] = new int[]{1, 26, 16};
        iArr[2] = new int[]{1, 26, 13};
        iArr[3] = new int[]{1, 26, 9};
        iArr[4] = new int[]{1, 44, 34};
        iArr[5] = new int[]{1, 44, 28};
        iArr[6] = new int[]{1, 44, 22};
        iArr[7] = new int[]{1, 44, 16};
        iArr[8] = new int[]{1, 70, 55};
        iArr[9] = new int[]{1, 70, 44};
        iArr[10] = new int[]{2, 35, 17};
        iArr[11] = new int[]{2, 35, 13};
        iArr[12] = new int[]{1, 100, 80};
        iArr[13] = new int[]{2, 50, 32};
        iArr[14] = new int[]{2, 50, 24};
        iArr[15] = new int[]{4, 25, 9};
        iArr[16] = new int[]{1, 134, 108};
        iArr[17] = new int[]{2, 67, 43};
        iArr[18] = new int[]{2, 33, 15, 2, 34, 16};
        iArr[19] = new int[]{2, 33, 11, 2, 34, 12};
        iArr[20] = new int[]{2, 86, 68};
        iArr[21] = new int[]{4, 43, 27};
        iArr[22] = new int[]{4, 43, 19};
        iArr[23] = new int[]{4, 43, 15};
        iArr[24] = new int[]{2, 98, 78};
        iArr[25] = new int[]{4, 49, 31};
        iArr[26] = new int[]{2, 32, 14, 4, 33, 15};
        iArr[27] = new int[]{4, 39, 13, 1, 40, 14};
        iArr[28] = new int[]{2, 121, 97};
        iArr[29] = new int[]{2, 60, 38, 2, 61, 39};
        iArr[30] = new int[]{4, 40, 18, 2, 41, 19};
        iArr[31] = new int[]{4, 40, 14, 2, 41, 15};
        iArr[32] = new int[]{2, 146, 116};
        iArr[33] = new int[]{3, 58, 36, 2, 59, 37};
        iArr[34] = new int[]{4, 36, 16, 4, 37, 17};
        iArr[35] = new int[]{4, 36, 12, 4, 37, 13};
        iArr[36] = new int[]{2, 86, 68, 2, 87, 69};
        iArr[37] = new int[]{4, 69, 43, 1, 70, 44};
        iArr[38] = new int[]{6, 43, 19, 2, 44, 20};
        iArr[39] = new int[]{6, 43, 15, 2, 44, 16};
        iArr[40] = new int[]{4, 101, 81};
        iArr[41] = new int[]{1, 80, 50, 4, 81, 51};
        iArr[42] = new int[]{4, 50, 22, 4, 51, 23};
        iArr[43] = new int[]{3, 36, 12, 8, 37, 13};
        iArr[44] = new int[]{2, 116, 92, 2, 117, 93};
        iArr[45] = new int[]{6, 58, 36, 2, 59, 37};
        iArr[46] = new int[]{4, 46, 20, 6, 47, 21};
        iArr[47] = new int[]{7, 42, 14, 4, 43, 15};
        iArr[48] = new int[]{4, 133, 107};
        iArr[49] = new int[]{8, 59, 37, 1, 60, 38};
        iArr[50] = new int[]{8, 44, 20, 4, 45, 21};
        iArr[51] = new int[]{12, 33, 11, 4, 34, 12};
        iArr[52] = new int[]{3, 145, 115, 1, 146, 116};
        iArr[53] = new int[]{4, 64, 40, 5, 65, 41};
        iArr[54] = new int[]{11, 36, 16, 5, 37, 17};
        iArr[55] = new int[]{11, 36, 12, 5, 37, 13};
        iArr[56] = new int[]{5, 109, 87, 1, 110, 88};
        iArr[57] = new int[]{5, 65, 41, 5, 66, 42};
        iArr[58] = new int[]{5, 54, 24, 7, 55, 25};
        iArr[59] = new int[]{11, 36, 12};
        iArr[60] = new int[]{5, 122, 98, 1, 123, 99};
        iArr[61] = new int[]{7, 73, 45, 3, 74, 46};
        iArr[62] = new int[]{15, 43, 19, 2, 44, 20};
        iArr[63] = new int[]{3, 45, 15, 13, 46, 16};
        iArr[64] = new int[]{1, 135, 107, 5, 136, 108};
        iArr[65] = new int[]{10, 74, 46, 1, 75, 47};
        iArr[66] = new int[]{1, 50, 22, 15, 51, 23};
        iArr[67] = new int[]{2, 42, 14, 17, 43, 15};
        iArr[68] = new int[]{5, 150, 120, 1, Opcodes.DCMPL, 121};
        iArr[69] = new int[]{9, 69, 43, 4, 70, 44};
        iArr[70] = new int[]{17, 50, 22, 1, 51, 23};
        iArr[71] = new int[]{2, 42, 14, 19, 43, 15};
        iArr[72] = new int[]{3, 141, 113, 4, 142, 114};
        iArr[73] = new int[]{3, 70, 44, 11, 71, 45};
        iArr[74] = new int[]{17, 47, 21, 4, 48, 22};
        iArr[75] = new int[]{9, 39, 13, 16, 40, 14};
        iArr[76] = new int[]{3, 135, 107, 5, 136, 108};
        iArr[77] = new int[]{3, 67, 41, 13, 68, 42};
        iArr[78] = new int[]{15, 54, 24, 5, 55, 25};
        iArr[79] = new int[]{15, 43, 15, 10, 44, 16};
        iArr[80] = new int[]{4, 144, 116, 4, 145, 117};
        iArr[81] = new int[]{17, 68, 42};
        iArr[82] = new int[]{17, 50, 22, 6, 51, 23};
        iArr[83] = new int[]{19, 46, 16, 6, 47, 17};
        iArr[84] = new int[]{2, 139, 111, 7, 140, 112};
        iArr[85] = new int[]{17, 74, 46};
        iArr[86] = new int[]{7, 54, 24, 16, 55, 25};
        iArr[87] = new int[]{34, 37, 13};
        iArr[88] = new int[]{4, Opcodes.DCMPL, 121, 5, 152, 122};
        iArr[89] = new int[]{4, 75, 47, 14, 76, 48};
        iArr[90] = new int[]{11, 54, 24, 14, 55, 25};
        iArr[91] = new int[]{16, 45, 15, 14, 46, 16};
        iArr[92] = new int[]{6, 147, 117, 4, Opcodes.LCMP, 118};
        iArr[93] = new int[]{6, 73, 45, 14, 74, 46};
        iArr[94] = new int[]{11, 54, 24, 16, 55, 25};
        iArr[95] = new int[]{30, 46, 16, 2, 47, 17};
        iArr[96] = new int[]{8, 132, 106, 4, 133, 107};
        iArr[97] = new int[]{8, 75, 47, 13, 76, 48};
        iArr[98] = new int[]{7, 54, 24, 22, 55, 25};
        iArr[99] = new int[]{22, 45, 15, 13, 46, 16};
        iArr[100] = new int[]{10, 142, 114, 2, 143, 115};
        iArr[101] = new int[]{19, 74, 46, 4, 75, 47};
        iArr[102] = new int[]{28, 50, 22, 6, 51, 23};
        iArr[103] = new int[]{33, 46, 16, 4, 47, 17};
        iArr[104] = new int[]{8, 152, 122, 4, Opcodes.IFEQ, 123};
        iArr[105] = new int[]{22, 73, 45, 3, 74, 46};
        iArr[106] = new int[]{8, 53, 23, 26, 54, 24};
        iArr[107] = new int[]{12, 45, 15, 28, 46, 16};
        iArr[108] = new int[]{3, 147, 117, 10, Opcodes.LCMP, 118};
        iArr[109] = new int[]{3, 73, 45, 23, 74, 46};
        iArr[110] = new int[]{4, 54, 24, 31, 55, 25};
        iArr[111] = new int[]{11, 45, 15, 31, 46, 16};
        iArr[112] = new int[]{7, 146, 116, 7, 147, 117};
        iArr[113] = new int[]{21, 73, 45, 7, 74, 46};
        iArr[114] = new int[]{1, 53, 23, 37, 54, 24};
        iArr[115] = new int[]{19, 45, 15, 26, 46, 16};
        iArr[116] = new int[]{5, 145, 115, 10, 146, 116};
        iArr[117] = new int[]{19, 75, 47, 10, 76, 48};
        iArr[118] = new int[]{15, 54, 24, 25, 55, 25};
        iArr[119] = new int[]{23, 45, 15, 25, 46, 16};
        iArr[120] = new int[]{13, 145, 115, 3, 146, 116};
        iArr[121] = new int[]{2, 74, 46, 29, 75, 47};
        iArr[122] = new int[]{42, 54, 24, 1, 55, 25};
        iArr[123] = new int[]{23, 45, 15, 28, 46, 16};
        iArr[124] = new int[]{17, 145, 115};
        iArr[125] = new int[]{10, 74, 46, 23, 75, 47};
        iArr[126] = new int[]{10, 54, 24, 35, 55, 25};
        iArr[127] = new int[]{19, 45, 15, 35, 46, 16};
        iArr[128] = new int[]{17, 145, 115, 1, 146, 116};
        iArr[129] = new int[]{14, 74, 46, 21, 75, 47};
        iArr[130] = new int[]{29, 54, 24, 19, 55, 25};
        iArr[131] = new int[]{11, 45, 15, 46, 46, 16};
        iArr[132] = new int[]{13, 145, 115, 6, 146, 116};
        iArr[133] = new int[]{14, 74, 46, 23, 75, 47};
        iArr[134] = new int[]{44, 54, 24, 7, 55, 25};
        iArr[135] = new int[]{59, 46, 16, 1, 47, 17};
        iArr[136] = new int[]{12, Opcodes.DCMPL, 121, 7, 152, 122};
        iArr[137] = new int[]{12, 75, 47, 26, 76, 48};
        iArr[138] = new int[]{39, 54, 24, 14, 55, 25};
        iArr[139] = new int[]{22, 45, 15, 41, 46, 16};
        iArr[140] = new int[]{6, Opcodes.DCMPL, 121, 14, 152, 122};
        iArr[141] = new int[]{6, 75, 47, 34, 76, 48};
        iArr[142] = new int[]{46, 54, 24, 10, 55, 25};
        iArr[143] = new int[]{2, 45, 15, 64, 46, 16};
        iArr[144] = new int[]{17, 152, 122, 4, Opcodes.IFEQ, 123};
        iArr[145] = new int[]{29, 74, 46, 14, 75, 47};
        iArr[146] = new int[]{49, 54, 24, 10, 55, 25};
        iArr[147] = new int[]{24, 45, 15, 46, 46, 16};
        iArr[148] = new int[]{4, 152, 122, 18, Opcodes.IFEQ, 123};
        iArr[149] = new int[]{13, 74, 46, 32, 75, 47};
        iArr[150] = new int[]{48, 54, 24, 14, 55, 25};
        iArr[151] = new int[]{42, 45, 15, 32, 46, 16};
        iArr[152] = new int[]{20, 147, 117, 4, Opcodes.LCMP, 118};
        iArr[153] = new int[]{40, 75, 47, 7, 76, 48};
        iArr[154] = new int[]{43, 54, 24, 22, 55, 25};
        iArr[155] = new int[]{10, 45, 15, 67, 46, 16};
        iArr[156] = new int[]{19, Opcodes.LCMP, 118, 6, Opcodes.FCMPL, 119};
        iArr[157] = new int[]{18, 75, 47, 31, 76, 48};
        iArr[158] = new int[]{34, 54, 24, 34, 55, 25};
        iArr[159] = new int[]{20, 45, 15, 61, 46, 16};
        RS_BLOCK_TABLE = iArr;
    }

    private RSBlock(int totalCount2, int dataCount2) {
        this.totalCount = totalCount2;
        this.dataCount = dataCount2;
    }

    public int getDataCount() {
        return this.dataCount;
    }

    public int getTotalCount() {
        return this.totalCount;
    }

    public static RSBlock[] getRSBlocks(int typeNumber, int errorCorrectLevel) {
        int[] rsBlock = getRsBlockTable(typeNumber, errorCorrectLevel);
        int length = rsBlock.length / 3;
        List<RSBlock> list = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            int count = rsBlock[(i * 3) + 0];
            int totalCount2 = rsBlock[(i * 3) + 1];
            int dataCount2 = rsBlock[(i * 3) + 2];
            for (int j = 0; j < count; j++) {
                list.add(new RSBlock(totalCount2, dataCount2));
            }
        }
        return (RSBlock[]) list.toArray(new RSBlock[list.size()]);
    }

    private static int[] getRsBlockTable(int typeNumber, int errorCorrectLevel) {
        switch (errorCorrectLevel) {
            case 0:
                return RS_BLOCK_TABLE[((typeNumber - 1) * 4) + 1];
            case 1:
                try {
                    return RS_BLOCK_TABLE[((typeNumber - 1) * 4) + 0];
                } catch (Exception e) {
                    break;
                }
            case 2:
                return RS_BLOCK_TABLE[((typeNumber - 1) * 4) + 3];
            case 3:
                return RS_BLOCK_TABLE[((typeNumber - 1) * 4) + 2];
        }
        throw new IllegalArgumentException("tn:" + typeNumber + "/ecl:" + errorCorrectLevel);
    }
}
