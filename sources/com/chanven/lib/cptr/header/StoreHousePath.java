package com.chanven.lib.cptr.header;

import android.util.SparseArray;
import java.util.ArrayList;

public class StoreHousePath {
    private static final SparseArray<float[]> sPointList = new SparseArray<>();

    static {
        float[][] LETTERS = {new float[]{24.0f, 0.0f, 1.0f, 22.0f, 1.0f, 22.0f, 1.0f, 72.0f, 24.0f, 0.0f, 47.0f, 22.0f, 47.0f, 22.0f, 47.0f, 72.0f, 1.0f, 48.0f, 47.0f, 48.0f}, new float[]{0.0f, 0.0f, 0.0f, 72.0f, 0.0f, 0.0f, 37.0f, 0.0f, 37.0f, 0.0f, 47.0f, 11.0f, 47.0f, 11.0f, 47.0f, 26.0f, 47.0f, 26.0f, 38.0f, 36.0f, 38.0f, 36.0f, 0.0f, 36.0f, 38.0f, 36.0f, 47.0f, 46.0f, 47.0f, 46.0f, 47.0f, 61.0f, 47.0f, 61.0f, 38.0f, 71.0f, 37.0f, 72.0f, 0.0f, 72.0f}, new float[]{47.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 72.0f, 0.0f, 72.0f, 47.0f, 72.0f}, new float[]{0.0f, 0.0f, 0.0f, 72.0f, 0.0f, 0.0f, 24.0f, 0.0f, 24.0f, 0.0f, 47.0f, 22.0f, 47.0f, 22.0f, 47.0f, 48.0f, 47.0f, 48.0f, 23.0f, 72.0f, 23.0f, 72.0f, 0.0f, 72.0f}, new float[]{0.0f, 0.0f, 0.0f, 72.0f, 0.0f, 0.0f, 47.0f, 0.0f, 0.0f, 36.0f, 37.0f, 36.0f, 0.0f, 72.0f, 47.0f, 72.0f}, new float[]{0.0f, 0.0f, 0.0f, 72.0f, 0.0f, 0.0f, 47.0f, 0.0f, 0.0f, 36.0f, 37.0f, 36.0f}, new float[]{47.0f, 23.0f, 47.0f, 0.0f, 47.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 72.0f, 0.0f, 72.0f, 47.0f, 72.0f, 47.0f, 72.0f, 47.0f, 48.0f, 47.0f, 48.0f, 24.0f, 48.0f}, new float[]{0.0f, 0.0f, 0.0f, 72.0f, 0.0f, 36.0f, 47.0f, 36.0f, 47.0f, 0.0f, 47.0f, 72.0f}, new float[]{0.0f, 0.0f, 47.0f, 0.0f, 24.0f, 0.0f, 24.0f, 72.0f, 0.0f, 72.0f, 47.0f, 72.0f}, new float[]{47.0f, 0.0f, 47.0f, 72.0f, 47.0f, 72.0f, 24.0f, 72.0f, 24.0f, 72.0f, 0.0f, 48.0f}, new float[]{0.0f, 0.0f, 0.0f, 72.0f, 47.0f, 0.0f, 3.0f, 33.0f, 3.0f, 38.0f, 47.0f, 72.0f}, new float[]{0.0f, 0.0f, 0.0f, 72.0f, 0.0f, 72.0f, 47.0f, 72.0f}, new float[]{0.0f, 0.0f, 0.0f, 72.0f, 0.0f, 0.0f, 24.0f, 23.0f, 24.0f, 23.0f, 47.0f, 0.0f, 47.0f, 0.0f, 47.0f, 72.0f}, new float[]{0.0f, 0.0f, 0.0f, 72.0f, 0.0f, 0.0f, 47.0f, 72.0f, 47.0f, 72.0f, 47.0f, 0.0f}, new float[]{0.0f, 0.0f, 0.0f, 72.0f, 0.0f, 72.0f, 47.0f, 72.0f, 47.0f, 72.0f, 47.0f, 0.0f, 47.0f, 0.0f, 0.0f, 0.0f}, new float[]{0.0f, 0.0f, 0.0f, 72.0f, 0.0f, 0.0f, 47.0f, 0.0f, 47.0f, 0.0f, 47.0f, 36.0f, 47.0f, 36.0f, 0.0f, 36.0f}, new float[]{0.0f, 0.0f, 0.0f, 72.0f, 0.0f, 72.0f, 23.0f, 72.0f, 23.0f, 72.0f, 47.0f, 48.0f, 47.0f, 48.0f, 47.0f, 0.0f, 47.0f, 0.0f, 0.0f, 0.0f, 24.0f, 28.0f, 47.0f, 71.0f}, new float[]{0.0f, 0.0f, 0.0f, 72.0f, 0.0f, 0.0f, 47.0f, 0.0f, 47.0f, 0.0f, 47.0f, 36.0f, 47.0f, 36.0f, 0.0f, 36.0f, 0.0f, 37.0f, 47.0f, 72.0f}, new float[]{47.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 36.0f, 0.0f, 36.0f, 47.0f, 36.0f, 47.0f, 36.0f, 47.0f, 72.0f, 47.0f, 72.0f, 0.0f, 72.0f}, new float[]{0.0f, 0.0f, 47.0f, 0.0f, 24.0f, 0.0f, 24.0f, 72.0f}, new float[]{0.0f, 0.0f, 0.0f, 72.0f, 0.0f, 72.0f, 47.0f, 72.0f, 47.0f, 72.0f, 47.0f, 0.0f}, new float[]{0.0f, 0.0f, 24.0f, 72.0f, 24.0f, 72.0f, 47.0f, 0.0f}, new float[]{0.0f, 0.0f, 0.0f, 72.0f, 0.0f, 72.0f, 24.0f, 49.0f, 24.0f, 49.0f, 47.0f, 72.0f, 47.0f, 72.0f, 47.0f, 0.0f}, new float[]{0.0f, 0.0f, 47.0f, 72.0f, 47.0f, 0.0f, 0.0f, 72.0f}, new float[]{0.0f, 0.0f, 24.0f, 23.0f, 47.0f, 0.0f, 24.0f, 23.0f, 24.0f, 23.0f, 24.0f, 72.0f}, new float[]{0.0f, 0.0f, 47.0f, 0.0f, 47.0f, 0.0f, 0.0f, 72.0f, 0.0f, 72.0f, 47.0f, 72.0f}};
        float[][] NUMBERS = {new float[]{0.0f, 0.0f, 0.0f, 72.0f, 0.0f, 72.0f, 47.0f, 72.0f, 47.0f, 72.0f, 47.0f, 0.0f, 47.0f, 0.0f, 0.0f, 0.0f}, new float[]{24.0f, 0.0f, 24.0f, 72.0f}, new float[]{0.0f, 0.0f, 47.0f, 0.0f, 47.0f, 0.0f, 47.0f, 36.0f, 47.0f, 36.0f, 0.0f, 36.0f, 0.0f, 36.0f, 0.0f, 72.0f, 0.0f, 72.0f, 47.0f, 72.0f}, new float[]{0.0f, 0.0f, 47.0f, 0.0f, 47.0f, 0.0f, 47.0f, 36.0f, 47.0f, 36.0f, 0.0f, 36.0f, 47.0f, 36.0f, 47.0f, 72.0f, 47.0f, 72.0f, 0.0f, 72.0f}, new float[]{0.0f, 0.0f, 0.0f, 36.0f, 0.0f, 36.0f, 47.0f, 36.0f, 47.0f, 0.0f, 47.0f, 72.0f}, new float[]{0.0f, 0.0f, 0.0f, 36.0f, 0.0f, 36.0f, 47.0f, 36.0f, 47.0f, 36.0f, 47.0f, 72.0f, 47.0f, 72.0f, 0.0f, 72.0f, 0.0f, 0.0f, 47.0f, 0.0f}, new float[]{0.0f, 0.0f, 0.0f, 72.0f, 0.0f, 72.0f, 47.0f, 72.0f, 47.0f, 72.0f, 47.0f, 36.0f, 47.0f, 36.0f, 0.0f, 36.0f}, new float[]{0.0f, 0.0f, 47.0f, 0.0f, 47.0f, 0.0f, 47.0f, 72.0f}, new float[]{0.0f, 0.0f, 0.0f, 72.0f, 0.0f, 72.0f, 47.0f, 72.0f, 47.0f, 72.0f, 47.0f, 0.0f, 47.0f, 0.0f, 0.0f, 0.0f, 0.0f, 36.0f, 47.0f, 36.0f}, new float[]{47.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 36.0f, 0.0f, 36.0f, 47.0f, 36.0f, 47.0f, 0.0f, 47.0f, 72.0f}};
        for (int i = 0; i < LETTERS.length; i++) {
            sPointList.append(i + 65, LETTERS[i]);
        }
        for (int i2 = 0; i2 < LETTERS.length; i2++) {
            sPointList.append(i2 + 65 + 32, LETTERS[i2]);
        }
        for (int i3 = 0; i3 < NUMBERS.length; i3++) {
            sPointList.append(i3 + 48, NUMBERS[i3]);
        }
        addChar(' ', new float[0]);
        addChar('-', new float[]{0.0f, 36.0f, 47.0f, 36.0f});
        addChar('.', new float[]{24.0f, 60.0f, 24.0f, 72.0f});
    }

    public static void addChar(char c, float[] points) {
        sPointList.append(c, points);
    }

    public static ArrayList<float[]> getPath(String str) {
        return getPath(str, 1.0f, 14);
    }

    public static ArrayList<float[]> getPath(String str, float scale, int gapBetweenLetter) {
        ArrayList<float[]> list = new ArrayList<>();
        float offsetForWidth = 0.0f;
        for (int i = 0; i < str.length(); i++) {
            int pos = str.charAt(i);
            if (sPointList.indexOfKey(pos) != -1) {
                float[] points = sPointList.get(pos);
                int pointCount = points.length / 4;
                for (int j = 0; j < pointCount; j++) {
                    float[] line = new float[4];
                    for (int k = 0; k < 4; k++) {
                        float l = points[(j * 4) + k];
                        if (k % 2 == 0) {
                            line[k] = (l + offsetForWidth) * scale;
                        } else {
                            line[k] = l * scale;
                        }
                    }
                    list.add(line);
                }
                offsetForWidth += (float) (gapBetweenLetter + 57);
            }
        }
        return list;
    }
}
