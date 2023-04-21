package org.achartengine.chart;

import android.graphics.Canvas;
import android.graphics.Paint;
import java.io.Serializable;
import java.util.List;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

public class CombinedXYChart extends XYChart {
    private XYCombinedChartDef[] chartDefinitions;
    private XYChart[] mCharts;
    private Class<?>[] xyChartTypes = {TimeChart.class, LineChart.class, CubicLineChart.class, BarChart.class, BubbleChart.class, ScatterChart.class, RangeBarChart.class, RangeStackedBarChart.class};

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v1, resolved type: java.lang.Class<?>[]} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public CombinedXYChart(org.achartengine.model.XYMultipleSeriesDataset r12, org.achartengine.renderer.XYMultipleSeriesRenderer r13, org.achartengine.chart.CombinedXYChart.XYCombinedChartDef[] r14) {
        /*
            r11 = this;
            r11.<init>(r12, r13)
            r8 = 8
            java.lang.Class[] r8 = new java.lang.Class[r8]
            r9 = 0
            java.lang.Class<org.achartengine.chart.TimeChart> r10 = org.achartengine.chart.TimeChart.class
            r8[r9] = r10
            r9 = 1
            java.lang.Class<org.achartengine.chart.LineChart> r10 = org.achartengine.chart.LineChart.class
            r8[r9] = r10
            r9 = 2
            java.lang.Class<org.achartengine.chart.CubicLineChart> r10 = org.achartengine.chart.CubicLineChart.class
            r8[r9] = r10
            r9 = 3
            java.lang.Class<org.achartengine.chart.BarChart> r10 = org.achartengine.chart.BarChart.class
            r8[r9] = r10
            r9 = 4
            java.lang.Class<org.achartengine.chart.BubbleChart> r10 = org.achartengine.chart.BubbleChart.class
            r8[r9] = r10
            r9 = 5
            java.lang.Class<org.achartengine.chart.ScatterChart> r10 = org.achartengine.chart.ScatterChart.class
            r8[r9] = r10
            r9 = 6
            java.lang.Class<org.achartengine.chart.RangeBarChart> r10 = org.achartengine.chart.RangeBarChart.class
            r8[r9] = r10
            r9 = 7
            java.lang.Class<org.achartengine.chart.RangeStackedBarChart> r10 = org.achartengine.chart.RangeStackedBarChart.class
            r8[r9] = r10
            r11.xyChartTypes = r8
            r11.chartDefinitions = r14
            int r4 = r14.length
            org.achartengine.chart.XYChart[] r8 = new org.achartengine.chart.XYChart[r4]
            r11.mCharts = r8
            r1 = 0
        L_0x0039:
            if (r1 >= r4) goto L_0x00ad
            org.achartengine.chart.XYChart[] r8 = r11.mCharts     // Catch:{ Exception -> 0x00ae }
            r9 = r14[r1]     // Catch:{ Exception -> 0x00ae }
            java.lang.String r9 = r9.getType()     // Catch:{ Exception -> 0x00ae }
            org.achartengine.chart.XYChart r9 = r11.getXYChart((java.lang.String) r9)     // Catch:{ Exception -> 0x00ae }
            r8[r1] = r9     // Catch:{ Exception -> 0x00ae }
        L_0x0049:
            org.achartengine.chart.XYChart[] r8 = r11.mCharts
            r8 = r8[r1]
            if (r8 != 0) goto L_0x006e
            java.lang.IllegalArgumentException r8 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = "Unknown chart type "
            java.lang.StringBuilder r9 = r9.append(r10)
            r10 = r14[r1]
            java.lang.String r10 = r10.getType()
            java.lang.StringBuilder r9 = r9.append(r10)
            java.lang.String r9 = r9.toString()
            r8.<init>(r9)
            throw r8
        L_0x006e:
            org.achartengine.model.XYMultipleSeriesDataset r5 = new org.achartengine.model.XYMultipleSeriesDataset
            r5.<init>()
            org.achartengine.renderer.XYMultipleSeriesRenderer r6 = new org.achartengine.renderer.XYMultipleSeriesRenderer
            r6.<init>()
            r8 = r14[r1]
            int[] r0 = r8.getSeriesIndex()
            int r3 = r0.length
            r2 = 0
        L_0x0080:
            if (r2 >= r3) goto L_0x0095
            r7 = r0[r2]
            org.achartengine.model.XYSeries r8 = r12.getSeriesAt(r7)
            r5.addSeries(r8)
            org.achartengine.renderer.SimpleSeriesRenderer r8 = r13.getSeriesRendererAt(r7)
            r6.addSeriesRenderer(r8)
            int r2 = r2 + 1
            goto L_0x0080
        L_0x0095:
            double r8 = r13.getBarSpacing()
            r6.setBarSpacing(r8)
            float r8 = r13.getPointSize()
            r6.setPointSize(r8)
            org.achartengine.chart.XYChart[] r8 = r11.mCharts
            r8 = r8[r1]
            r8.setDatasetRenderer(r5, r6)
            int r1 = r1 + 1
            goto L_0x0039
        L_0x00ad:
            return
        L_0x00ae:
            r8 = move-exception
            goto L_0x0049
        */
        throw new UnsupportedOperationException("Method not decompiled: org.achartengine.chart.CombinedXYChart.<init>(org.achartengine.model.XYMultipleSeriesDataset, org.achartengine.renderer.XYMultipleSeriesRenderer, org.achartengine.chart.CombinedXYChart$XYCombinedChartDef[]):void");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v1, resolved type: java.lang.Class<?>[]} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public CombinedXYChart(org.achartengine.model.XYMultipleSeriesDataset r4, org.achartengine.renderer.XYMultipleSeriesRenderer r5, org.achartengine.chart.CombinedXYChart.XYCombinedChartDef[] r6, org.achartengine.chart.XYChart[] r7) {
        /*
            r3 = this;
            r3.<init>(r4, r5)
            r0 = 8
            java.lang.Class[] r0 = new java.lang.Class[r0]
            r1 = 0
            java.lang.Class<org.achartengine.chart.TimeChart> r2 = org.achartengine.chart.TimeChart.class
            r0[r1] = r2
            r1 = 1
            java.lang.Class<org.achartengine.chart.LineChart> r2 = org.achartengine.chart.LineChart.class
            r0[r1] = r2
            r1 = 2
            java.lang.Class<org.achartengine.chart.CubicLineChart> r2 = org.achartengine.chart.CubicLineChart.class
            r0[r1] = r2
            r1 = 3
            java.lang.Class<org.achartengine.chart.BarChart> r2 = org.achartengine.chart.BarChart.class
            r0[r1] = r2
            r1 = 4
            java.lang.Class<org.achartengine.chart.BubbleChart> r2 = org.achartengine.chart.BubbleChart.class
            r0[r1] = r2
            r1 = 5
            java.lang.Class<org.achartengine.chart.ScatterChart> r2 = org.achartengine.chart.ScatterChart.class
            r0[r1] = r2
            r1 = 6
            java.lang.Class<org.achartengine.chart.RangeBarChart> r2 = org.achartengine.chart.RangeBarChart.class
            r0[r1] = r2
            r1 = 7
            java.lang.Class<org.achartengine.chart.RangeStackedBarChart> r2 = org.achartengine.chart.RangeStackedBarChart.class
            r0[r1] = r2
            r3.xyChartTypes = r0
            r3.chartDefinitions = r6
            r3.mCharts = r7
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.achartengine.chart.CombinedXYChart.<init>(org.achartengine.model.XYMultipleSeriesDataset, org.achartengine.renderer.XYMultipleSeriesRenderer, org.achartengine.chart.CombinedXYChart$XYCombinedChartDef[], org.achartengine.chart.XYChart[]):void");
    }

    private XYChart getXYChart(String type) throws IllegalAccessException, InstantiationException {
        XYChart chart = null;
        int length = this.xyChartTypes.length;
        for (int i = 0; i < length && chart == null; i++) {
            XYChart newChart = (XYChart) this.xyChartTypes[i].newInstance();
            if (type.equals(newChart.getChartType())) {
                chart = newChart;
            }
        }
        return chart;
    }

    public void drawSeries(Canvas canvas, Paint paint, List<Float> points, XYSeriesRenderer seriesRenderer, float yAxisValue, int seriesIndex, int startIndex) {
        XYChart chart = getXYChart(seriesIndex);
        chart.setScreenR(getScreenR());
        chart.setCalcRange(getCalcRange(this.mDataset.getSeriesAt(seriesIndex).getScaleNumber()), 0);
        chart.drawSeries(canvas, paint, points, seriesRenderer, yAxisValue, getChartSeriesIndex(seriesIndex), startIndex);
    }

    /* access modifiers changed from: protected */
    public ClickableArea[] clickableAreasForPoints(List<Float> points, List<Double> values, float yAxisValue, int seriesIndex, int startIndex) {
        return getXYChart(seriesIndex).clickableAreasForPoints(points, values, yAxisValue, getChartSeriesIndex(seriesIndex), startIndex);
    }

    /* access modifiers changed from: protected */
    public void drawSeries(XYSeries series, Canvas canvas, Paint paint, List<Float> pointsList, XYSeriesRenderer seriesRenderer, float yAxisValue, int seriesIndex, XYMultipleSeriesRenderer.Orientation or, int startIndex) {
        XYChart chart = getXYChart(seriesIndex);
        chart.setScreenR(getScreenR());
        chart.setCalcRange(getCalcRange(this.mDataset.getSeriesAt(seriesIndex).getScaleNumber()), 0);
        chart.drawSeries(series, canvas, paint, pointsList, seriesRenderer, yAxisValue, getChartSeriesIndex(seriesIndex), or, startIndex);
    }

    public int getLegendShapeWidth(int seriesIndex) {
        return getXYChart(seriesIndex).getLegendShapeWidth(getChartSeriesIndex(seriesIndex));
    }

    public void drawLegendShape(Canvas canvas, SimpleSeriesRenderer renderer, float x, float y, int seriesIndex, Paint paint) {
        getXYChart(seriesIndex).drawLegendShape(canvas, renderer, x, y, getChartSeriesIndex(seriesIndex), paint);
    }

    public String getChartType() {
        return "Combined";
    }

    private XYChart getXYChart(int seriesIndex) {
        for (int i = 0; i < this.chartDefinitions.length; i++) {
            if (this.chartDefinitions[i].containsSeries(seriesIndex)) {
                return this.mCharts[i];
            }
        }
        throw new IllegalArgumentException("Unknown series with index " + seriesIndex);
    }

    private int getChartSeriesIndex(int seriesIndex) {
        for (int i = 0; i < this.chartDefinitions.length; i++) {
            if (this.chartDefinitions[i].containsSeries(seriesIndex)) {
                return this.chartDefinitions[i].getChartSeriesIndex(seriesIndex);
            }
        }
        throw new IllegalArgumentException("Unknown series with index " + seriesIndex);
    }

    public static class XYCombinedChartDef implements Serializable {
        private int[] seriesIndex;
        private String type;

        public XYCombinedChartDef(String type2, int... seriesIndex2) {
            this.type = type2;
            this.seriesIndex = seriesIndex2;
        }

        public boolean containsSeries(int seriesIndex2) {
            return getChartSeriesIndex(seriesIndex2) >= 0;
        }

        public int getChartSeriesIndex(int seriesIndex2) {
            for (int i = 0; i < getSeriesIndex().length; i++) {
                if (this.seriesIndex[i] == seriesIndex2) {
                    return i;
                }
            }
            return -1;
        }

        public String getType() {
            return this.type;
        }

        public int[] getSeriesIndex() {
            return this.seriesIndex;
        }
    }
}
