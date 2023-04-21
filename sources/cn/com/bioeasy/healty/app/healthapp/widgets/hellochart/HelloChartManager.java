package cn.com.bioeasy.healty.app.healthapp.widgets.hellochart;

import android.content.Context;
import android.graphics.Color;
import cn.com.bioeasy.healty.app.healthapp.modules.test.bean.TestResultItem;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

@Singleton
public class HelloChartManager {
    private static List<AxisValue> mAxisXValues = new ArrayList();

    @Inject
    public HelloChartManager() {
        for (int i = 0; i < 1600; i++) {
            mAxisXValues.add(new AxisValue((float) i).setLabel(String.valueOf(i)));
        }
    }

    public void initData(Context cxt, LineChartView lineChartView, List<TestResultItem> resultItems, TestResultItem cItem) {
        List<PointValue> mPointValues = new ArrayList<>();
        mPointValues.add(new PointValue(0.0f, 0.0f));
        for (int i = 0; i <= 1600; i++) {
            mPointValues.add(new PointValue(0.0f, (float) i));
        }
        mPointValues.add(new PointValue(0.0f, 0.0f));
        if (cItem != null) {
            mPointValues.add(new PointValue((float) (cItem.getPosition().intValue() - 100), 0.0f));
            mPointValues.add(new PointValue((float) cItem.getPosition().intValue(), (float) (cItem.getHeight().intValue() + 800)));
            mPointValues.add(new PointValue((float) (cItem.getPosition().intValue() + 100), 0.0f));
        }
        for (TestResultItem resultItem : resultItems) {
            mPointValues.add(new PointValue((float) (resultItem.getPosition().intValue() - 100), 0.0f));
            mPointValues.add(new PointValue((float) resultItem.getPosition().intValue(), (float) (resultItem.getHeight().intValue() + 800)));
            mPointValues.add(new PointValue((float) (resultItem.getPosition().intValue() + 100), 0.0f));
        }
        mPointValues.add(new PointValue(1600.0f, 0.0f));
        drawChart(cxt, lineChartView, mPointValues, new ArrayList());
    }

    public void initData(Context cxt, LineChartView lineChartView, List<Integer> dataY, List<TestResultItem> resultItems, TestResultItem cItem) {
        List<PointValue> mPointValues = new ArrayList<>();
        int size = dataY.size() > 0 ? dataY.size() : 1600;
        int i = 0;
        while (i < size) {
            int value = dataY.size() <= i ? 0 : dataY.get(i).intValue();
            if (value > 6000) {
                value = 0;
            }
            mPointValues.add(new PointValue((float) i, (float) value));
            i++;
        }
        List<PointValue> mResultPoints = new ArrayList<>();
        if (cItem != null) {
            mResultPoints.add(new PointValue((float) cItem.getPosition().intValue(), (float) (cItem.getHeight().intValue() > 6000 ? 0 : cItem.getHeight().intValue())));
        }
        if (resultItems != null && resultItems.size() > 0) {
            for (TestResultItem item : resultItems) {
                mResultPoints.add(new PointValue((float) item.getPosition().intValue(), (float) (item.getHeight().intValue() > 6000 ? 0 : item.getHeight().intValue())));
            }
        }
        drawChart(cxt, lineChartView, mPointValues, mResultPoints);
    }

    private void drawChart(Context cxt, LineChartView lineChart, List<PointValue> mPointValues, List<PointValue> resultPoints) {
        Line line = new Line(mPointValues).setColor(Color.parseColor("#FFCD41"));
        List<Line> lines = new ArrayList<>();
        line.setShape(ValueShape.CIRCLE);
        line.setCubic(true);
        line.setStrokeWidth(2);
        line.setFilled(true);
        line.setHasLines(true);
        line.setHasPoints(false);
        lines.add(line);
        if (resultPoints != null && resultPoints.size() > 0) {
            for (PointValue resultPoint : resultPoints) {
                List<PointValue> pointVals = new ArrayList<>();
                int i = 0;
                while (true) {
                    if (i > ((int) mPointValues.get((int) resultPoint.getX()).getY())) {
                        break;
                    }
                    pointVals.add(new PointValue(resultPoint.getX(), (float) i));
                    i++;
                }
                Line pointLine = new Line(pointVals).setColor(Color.parseColor("#FFCD41"));
                pointLine.setShape(ValueShape.CIRCLE);
                pointLine.setCubic(false);
                pointLine.setStrokeWidth(2);
                pointLine.setFilled(false);
                pointLine.setHasLabels(false);
                pointLine.setHasLines(true);
                pointLine.setHasPoints(false);
                lines.add(pointLine);
            }
        }
        LineChartData data = new LineChartData();
        data.setLines(lines);
        Axis axisX = new Axis();
        axisX.setHasTiltedLabels(true);
        axisX.setTextColor(Color.parseColor("#D6D6D9"));
        axisX.setTextSize(11);
        axisX.setMaxLabelChars(7);
        axisX.setValues(mAxisXValues);
        data.setAxisXBottom(axisX);
        axisX.setHasLines(true);
        Axis axisY = new Axis();
        axisY.setTextSize(11);
        axisY.setMaxLabelChars(7);
        axisY.setLineColor(Color.parseColor("#FFCD41"));
        data.setAxisYLeft(axisY);
        lineChart.setInteractive(true);
        lineChart.setZoomType(ZoomType.HORIZONTAL_AND_VERTICAL);
        lineChart.setMaxZoom(1.0f);
        lineChart.setLineChartData(data);
        lineChart.setVisibility(0);
        Viewport v = new Viewport(lineChart.getMaximumViewport());
        v.left = 0.0f;
        v.right = 7.0f;
        lineChart.setCurrentViewport(v);
    }
}
