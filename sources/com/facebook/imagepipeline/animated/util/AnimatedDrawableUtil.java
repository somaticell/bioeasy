package com.facebook.imagepipeline.animated.util;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Build;
import java.util.Arrays;

public class AnimatedDrawableUtil {
    private static final int FRAME_DURATION_MS_FOR_MIN = 100;
    private static final int MIN_FRAME_DURATION_MS = 11;

    public void appendMemoryString(StringBuilder sb, int kiloBytes) {
        if (kiloBytes < 1024) {
            sb.append(kiloBytes);
            sb.append("KB");
            return;
        }
        sb.append(kiloBytes / 1024);
        sb.append(".");
        sb.append((kiloBytes % 1024) / 100);
        sb.append("MB");
    }

    public void fixFrameDurations(int[] frameDurationMs) {
        for (int i = 0; i < frameDurationMs.length; i++) {
            if (frameDurationMs[i] < 11) {
                frameDurationMs[i] = 100;
            }
        }
    }

    public int getTotalDurationFromFrameDurations(int[] frameDurationMs) {
        int totalMs = 0;
        for (int i : frameDurationMs) {
            totalMs += i;
        }
        return totalMs;
    }

    public int[] getFrameTimeStampsFromDurations(int[] frameDurationsMs) {
        int[] frameTimestampsMs = new int[frameDurationsMs.length];
        int accumulatedDurationMs = 0;
        for (int i = 0; i < frameDurationsMs.length; i++) {
            frameTimestampsMs[i] = accumulatedDurationMs;
            accumulatedDurationMs += frameDurationsMs[i];
        }
        return frameTimestampsMs;
    }

    public int getFrameForTimestampMs(int[] frameTimestampsMs, int timestampMs) {
        int index = Arrays.binarySearch(frameTimestampsMs, timestampMs);
        if (index < 0) {
            return ((-index) - 1) - 1;
        }
        return index;
    }

    @SuppressLint({"NewApi"})
    public int getSizeOfBitmap(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= 19) {
            return bitmap.getAllocationByteCount();
        }
        if (Build.VERSION.SDK_INT >= 12) {
            return bitmap.getByteCount();
        }
        return bitmap.getWidth() * bitmap.getHeight() * 4;
    }

    public static boolean isOutsideRange(int startFrame, int endFrame, int frameNumber) {
        if (startFrame == -1 || endFrame == -1) {
            return true;
        }
        if (startFrame <= endFrame) {
            if (frameNumber < startFrame || frameNumber > endFrame) {
                return true;
            }
            return false;
        } else if (frameNumber >= startFrame || frameNumber <= endFrame) {
            return false;
        } else {
            return true;
        }
    }
}
