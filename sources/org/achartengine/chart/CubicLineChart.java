package org.achartengine.chart;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import java.util.List;
import org.achartengine.model.Point;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

public class CubicLineChart extends LineChart {
    public static final String TYPE = "Cubic";
    private float mFirstMultiplier;
    private PathMeasure mPathMeasure;
    private float mSecondMultiplier;

    public CubicLineChart() {
        this.mFirstMultiplier = 0.33f;
        this.mSecondMultiplier = 1.0f - this.mFirstMultiplier;
    }

    public CubicLineChart(XYMultipleSeriesDataset dataset, XYMultipleSeriesRenderer renderer, float smoothness) {
        super(dataset, renderer);
        this.mFirstMultiplier = smoothness;
        this.mSecondMultiplier = 1.0f - this.mFirstMultiplier;
    }

    /* access modifiers changed from: protected */
    public void drawPath(Canvas canvas, List<Float> points, Paint paint, boolean circular) {
        int nextIndex;
        int nextNextIndex;
        Path p = new Path();
        p.moveTo(points.get(0).floatValue(), points.get(1).floatValue());
        int length = points.size();
        if (circular) {
            length -= 4;
        }
        Point p1 = new Point();
        Point p2 = new Point();
        Point p3 = new Point();
        for (int i = 0; i < length; i += 2) {
            if (i + 2 < length) {
                nextIndex = i + 2;
            } else {
                nextIndex = i;
            }
            if (i + 4 < length) {
                nextNextIndex = i + 4;
            } else {
                nextNextIndex = nextIndex;
            }
            calc(points, p1, i, nextIndex, this.mSecondMultiplier);
            p2.setX(points.get(nextIndex).floatValue());
            p2.setY(points.get(nextIndex + 1).floatValue());
            calc(points, p3, nextIndex, nextNextIndex, this.mFirstMultiplier);
            p.cubicTo(p1.getX(), p1.getY(), p2.getX(), p2.getY(), p3.getX(), p3.getY());
        }
        this.mPathMeasure = new PathMeasure(p, false);
        if (circular) {
            for (int i2 = length; i2 < length + 4; i2 += 2) {
                p.lineTo(points.get(i2).floatValue(), points.get(i2 + 1).floatValue());
            }
            p.lineTo(points.get(0).floatValue(), points.get(1).floatValue());
        }
        canvas.drawPath(p, paint);
    }

    private void calc(List<Float> points, Point result, int index1, int index2, float multiplier) {
        float p1x = points.get(index1).floatValue();
        float p1y = points.get(index1 + 1).floatValue();
        result.setX(((points.get(index2).floatValue() - p1x) * multiplier) + p1x);
        result.setY(((points.get(index2 + 1).floatValue() - p1y) * multiplier) + p1y);
    }

    /* access modifiers changed from: protected */
    public void drawPoints(Canvas canvas, Paint paint, List<Float> pointsList, XYSeriesRenderer seriesRenderer, float yAxisValue, int seriesIndex, int startIndex) {
        ScatterChart pointsChart;
        if (isRenderPoints(seriesRenderer) && (pointsChart = getPointsChart()) != null) {
            int length = (int) this.mPathMeasure.getLength();
            int pointsLength = pointsList.size();
            float[] coords = new float[2];
            for (int i = 0; i < length; i++) {
                this.mPathMeasure.getPosTan((float) i, coords, (float[]) null);
                double prevDiff = Double.MAX_VALUE;
                boolean ok = true;
                for (int j = 0; j < pointsLength && ok; j += 2) {
                    double diff = (double) Math.abs(pointsList.get(j).floatValue() - coords[0]);
                    if (diff < 1.0d) {
                        pointsList.set(j + 1, Float.valueOf(coords[1]));
                        prevDiff = diff;
                    }
                    if (prevDiff > diff) {
                        ok = true;
                    } else {
                        ok = false;
                    }
                }
            }
            pointsChart.drawSeries(canvas, paint, pointsList, seriesRenderer, yAxisValue, seriesIndex, startIndex);
        }
    }

    public String getChartType() {
        return TYPE;
    }
}
