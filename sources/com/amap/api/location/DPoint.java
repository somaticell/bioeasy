package com.amap.api.location;

public class DPoint {
    private double a;
    private double b;

    public DPoint() {
    }

    public DPoint(double d, double d2) {
        double d3 = 90.0d;
        double d4 = -180.0d;
        double d5 = -90.0d;
        double d6 = d2 <= 180.0d ? d2 : 180.0d;
        d4 = d6 >= -180.0d ? d6 : d4;
        d3 = d <= 90.0d ? d : d3;
        d5 = d3 >= -90.0d ? d3 : d5;
        this.a = d4;
        this.b = d5;
    }

    public double getLongitude() {
        return this.a;
    }

    public void setLongitude(double d) {
        double d2 = 180.0d;
        double d3 = -180.0d;
        if (d <= 180.0d) {
            d2 = d;
        }
        if (d2 >= -180.0d) {
            d3 = d2;
        }
        this.a = d3;
    }

    public double getLatitude() {
        return this.b;
    }

    public void setLatitude(double d) {
        double d2 = 90.0d;
        double d3 = -90.0d;
        if (d <= 90.0d) {
            d2 = d;
        }
        if (d2 >= -90.0d) {
            d3 = d2;
        }
        this.b = d3;
    }
}
