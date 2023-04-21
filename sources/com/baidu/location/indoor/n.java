package com.baidu.location.indoor;

import java.util.List;

public class n {
    private static double a = 6378137.0d;

    public static double a(double d, double d2) {
        if (d * d2 == -1.0d) {
            return 90.0d;
        }
        return Math.toDegrees(Math.atan(d2 - (d / (1.0d + (d * d2)))));
    }

    public static double a(double d, double d2, double d3, double d4) {
        double radians = Math.toRadians(d);
        Math.toRadians(d2);
        double radians2 = Math.toRadians(d3);
        Math.toRadians(d4);
        double radians3 = Math.toRadians(d4 - d2);
        double radians4 = Math.toRadians(d3 - d);
        double sin = Math.sin(radians4 / 2.0d);
        double sin2 = (Math.sin(radians3 / 2.0d) * Math.cos(radians) * Math.cos(radians2) * Math.sin(radians3 / 2.0d)) + (Math.sin(radians4 / 2.0d) * sin);
        return Math.atan2(Math.sqrt(sin2), Math.sqrt(1.0d - sin2)) * 2.0d * a;
    }

    public static double a(List<Float> list) {
        double floatValue = (double) list.get(0).floatValue();
        double d = floatValue;
        for (int i = 1; i < list.size(); i++) {
            double floatValue2 = ((double) list.get(i).floatValue()) - floatValue;
            floatValue = floatValue2 < -180.0d ? floatValue + floatValue2 + 360.0d : floatValue2 < 180.0d ? floatValue + floatValue2 : (floatValue + floatValue2) - 360.0d;
            d += floatValue;
        }
        return d / ((double) list.size());
    }

    public static double b(double d, double d2) {
        double d3 = d2 - d;
        return d3 > 180.0d ? d3 - 360.0d : d3 < -180.0d ? d3 + 360.0d : d3;
    }

    public static double b(double d, double d2, double d3, double d4) {
        double radians = Math.toRadians(d2);
        double radians2 = Math.toRadians(d);
        double radians3 = Math.toRadians(d3);
        double radians4 = Math.toRadians(d4) - radians;
        return (Math.toDegrees(Math.atan2(Math.sin(radians4) * Math.cos(radians3), (Math.cos(radians2) * Math.sin(radians3)) - (Math.cos(radians4) * (Math.sin(radians2) * Math.cos(radians3))))) + 360.0d) % 360.0d;
    }
}
