package cn.com.bioeasy.healty.app.healthapp.modules.home.view;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.app.utils.StringUtil;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.adapter.SearchAdapter2;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.injection.component.ActivityComponent;
import cn.com.bioeasy.healty.app.healthapp.modules.home.bean.NutritionItem;
import java.util.HashMap;
import java.util.Map;
import jxl.Sheet;
import jxl.Workbook;

public class NutritionCalculationActivity extends BIBaseActivity {
    private static final double BASE_WEIGHT = 100.0d;
    /* access modifiers changed from: private */
    public SearchAdapter2<String> autoCompleteAdapter;
    private double inputFoodWeight;
    /* access modifiers changed from: private */
    public String itemType;
    @Bind({2131231016})
    AutoCompleteTextView mAcText;
    @Bind({2131231477})
    LinearLayout mLlResult;
    @Bind({2131231500})
    TextView mTvA;
    @Bind({2131231501})
    TextView mTvAS;
    @Bind({2131231503})
    TextView mTvB1;
    @Bind({2131231504})
    TextView mTvB1S;
    @Bind({2131231505})
    TextView mTvB2;
    @Bind({2131231506})
    TextView mTvB2S;
    @Bind({2131231497})
    TextView mTvBo;
    @Bind({2131231498})
    TextView mTvBoS;
    @Bind({2131231507})
    TextView mTvC;
    @Bind({2131231508})
    TextView mTvCS;
    @Bind({2131231491})
    TextView mTvCalciun;
    @Bind({2131231492})
    TextView mTvCalciunS;
    @Bind({2131231486})
    TextView mTvFat;
    @Bind({2131231487})
    TextView mTvFatS;
    @Bind({2131231480})
    TextView mTvHeat;
    @Bind({2131231481})
    TextView mTvHeatS;
    @Bind({2131231493})
    TextView mTvPhosphorus;
    @Bind({2131231494})
    TextView mTvPhosphorusS;
    @Bind({2131231483})
    TextView mTvProtein;
    @Bind({2131231484})
    TextView mTvProteinS;
    @Bind({2131231489})
    TextView mTvTangshui;
    @Bind({2131231490})
    TextView mTvTangshuiS;
    @Bind({2131231495})
    TextView mTvTie;
    @Bind({2131231496})
    TextView mTvTieS;
    @Bind({2131231018})
    EditText metInputCount;
    private Map<String, NutritionItem> nutritionItemMap;
    private Map<Integer, Double> standardValueMap;
    @Bind({2131231008})
    TextView tvTitle;

    /* access modifiers changed from: protected */
    public void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    /* access modifiers changed from: protected */
    public BasePresenter getPresenter() {
        return null;
    }

    /* access modifiers changed from: protected */
    public int getContentViewID() {
        return R.layout.activity_nutrition_calculation;
    }

    @OnClick({2131231050, 2131231019})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cacle:
                if (this.metInputCount.getText().toString().trim().isEmpty() || this.mAcText.getText().toString().trim().isEmpty()) {
                    showMessage((int) R.string.select_no_none);
                    return;
                } else {
                    calculation();
                    return;
                }
            case R.id.iv_back:
                finish();
                return;
            default:
                return;
        }
    }

    private void calculation() {
        this.inputFoodWeight = Double.parseDouble(this.metInputCount.getText().toString());
        if (this.nutritionItemMap.containsKey(this.itemType)) {
            NutritionItem item = this.nutritionItemMap.get(this.itemType);
            showProgress(getString(R.string.in_calculation));
            this.mTvHeat.setText(String.format("%.2f", new Object[]{Double.valueOf((item.getHeatStandard() / BASE_WEIGHT) * this.inputFoodWeight)}));
            this.mTvHeatS.setText(String.format("%.2f", new Object[]{Double.valueOf((((item.getHeatStandard() / BASE_WEIGHT) * this.inputFoodWeight) * BASE_WEIGHT) / this.standardValueMap.get(0).doubleValue())}) + "%");
            this.mTvProtein.setText(String.format("%.2f", new Object[]{Double.valueOf((item.getProteinStandard() / BASE_WEIGHT) * this.inputFoodWeight)}));
            this.mTvProteinS.setText(String.format("%.2f", new Object[]{Double.valueOf((((item.getProteinStandard() / BASE_WEIGHT) * this.inputFoodWeight) * BASE_WEIGHT) / this.standardValueMap.get(1).doubleValue())}) + "%");
            this.mTvFat.setText(String.format("%.2f", new Object[]{Double.valueOf((item.getFatStandard() / BASE_WEIGHT) * this.inputFoodWeight)}));
            this.mTvFatS.setText(String.format("%.2f", new Object[]{Double.valueOf((((item.getFatStandard() / BASE_WEIGHT) * this.inputFoodWeight) * BASE_WEIGHT) / this.standardValueMap.get(2).doubleValue())}) + "%");
            this.mTvTangshui.setText(String.format("%.2f", new Object[]{Double.valueOf((item.getcStandard() / BASE_WEIGHT) * this.inputFoodWeight)}));
            this.mTvTangshuiS.setText(String.format("%.2f", new Object[]{Double.valueOf((((item.getcStandard() / BASE_WEIGHT) * this.inputFoodWeight) * BASE_WEIGHT) / this.standardValueMap.get(3).doubleValue())}) + "%");
            this.mTvCalciun.setText(String.format("%.2f", new Object[]{Double.valueOf((item.getCalciumStandard() / BASE_WEIGHT) * this.inputFoodWeight)}));
            this.mTvCalciunS.setText(String.format("%.2f", new Object[]{Double.valueOf((((item.getCalciumStandard() / BASE_WEIGHT) * this.inputFoodWeight) * BASE_WEIGHT) / this.standardValueMap.get(4).doubleValue())}) + "%");
            this.mTvPhosphorus.setText(String.format("%.2f", new Object[]{Double.valueOf((item.getPhosphorusStandard() / BASE_WEIGHT) * this.inputFoodWeight)}));
            this.mTvPhosphorusS.setText(String.format("%.2f", new Object[]{Double.valueOf((((item.getPhosphorusStandard() / BASE_WEIGHT) * this.inputFoodWeight) * BASE_WEIGHT) / this.standardValueMap.get(5).doubleValue())}) + "%");
            this.mTvTie.setText(String.format("%.2f", new Object[]{Double.valueOf((item.getTieStandard() / BASE_WEIGHT) * this.inputFoodWeight)}));
            this.mTvTieS.setText(String.format("%.2f", new Object[]{Double.valueOf((((item.getTieStandard() / BASE_WEIGHT) * this.inputFoodWeight) * BASE_WEIGHT) / this.standardValueMap.get(6).doubleValue())}) + "%");
            this.mTvBo.setText(String.format("%.2f", new Object[]{Double.valueOf((item.getBoStandard() / BASE_WEIGHT) * this.inputFoodWeight)}));
            this.mTvBoS.setText("-");
            this.mTvA.setText(String.format("%.2f", new Object[]{Double.valueOf((item.getWeiAStandard() / BASE_WEIGHT) * this.inputFoodWeight)}));
            this.mTvAS.setText(String.format("%.2f", new Object[]{Double.valueOf((((item.getWeiAStandard() / BASE_WEIGHT) * this.inputFoodWeight) * BASE_WEIGHT) / this.standardValueMap.get(8).doubleValue())}) + "%");
            this.mTvB1.setText(String.format("%.2f", new Object[]{Double.valueOf((item.getWeiB1Standard() / BASE_WEIGHT) * this.inputFoodWeight)}));
            this.mTvB1S.setText(String.format("%.2f", new Object[]{Double.valueOf((((item.getWeiB1Standard() / BASE_WEIGHT) * this.inputFoodWeight) * BASE_WEIGHT) / this.standardValueMap.get(9).doubleValue())}) + "%");
            this.mTvB2.setText(String.format("%.2f", new Object[]{Double.valueOf((item.getWeiB2Standard() / BASE_WEIGHT) * this.inputFoodWeight)}));
            this.mTvB2S.setText(String.format("%.2f", new Object[]{Double.valueOf((((item.getHeatStandard() / BASE_WEIGHT) * this.inputFoodWeight) * BASE_WEIGHT) / this.standardValueMap.get(10).doubleValue())}) + "%");
            this.mTvC.setText(String.format("%.2f", new Object[]{Double.valueOf((item.getWeiCStandard() / BASE_WEIGHT) * this.inputFoodWeight)}));
            this.mTvCS.setText(String.format("%.2f", new Object[]{Double.valueOf((((item.getWeiCStandard() / BASE_WEIGHT) * this.inputFoodWeight) * BASE_WEIGHT) / this.standardValueMap.get(11).doubleValue())}) + "%");
            this.mLlResult.setVisibility(0);
            hideProgress();
        }
    }

    /* access modifiers changed from: protected */
    public void initViewsAndEvents() {
        this.tvTitle.setText(getResources().getString(R.string.nutrition_calculation));
        this.nutritionItemMap = getExcelData("Nutrition.xls", 0);
        this.autoCompleteAdapter = new SearchAdapter2<>((Context) this, 17367043, (T[]) (String[]) this.nutritionItemMap.keySet().toArray(new String[0]), -1);
        this.mAcText.setAdapter(this.autoCompleteAdapter);
        this.mAcText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String unused = NutritionCalculationActivity.this.itemType = (String) NutritionCalculationActivity.this.autoCompleteAdapter.getItem(position);
            }
        });
    }

    public Map<String, NutritionItem> getExcelData(String xlsName, int index) {
        Map<String, NutritionItem> map = new HashMap<>();
        Workbook workbook = null;
        try {
            workbook = Workbook.getWorkbook(this.context.getAssets().open(xlsName));
            Sheet sheet = workbook.getSheet(index);
            int sheetRows = sheet.getRows();
            this.standardValueMap = new HashMap();
            for (int i = 2; i <= 13; i++) {
                String valStr = sheet.getCell(i, 3).getContents();
                this.standardValueMap.put(Integer.valueOf(i - 2), Double.valueOf(StringUtil.isNullOrEmpty(valStr) ? 0.0d : Double.parseDouble(valStr)));
            }
            for (int i2 = 4; i2 < sheetRows; i2++) {
                NutritionItem bean = new NutritionItem();
                String itemName = sheet.getCell(1, i2).getContents();
                if (!StringUtil.isNullOrEmpty(itemName)) {
                    bean.setHeatStandard(Double.parseDouble(sheet.getCell(2, i2).getContents()));
                    bean.setProteinStandard(Double.parseDouble(sheet.getCell(3, i2).getContents()));
                    bean.setFatStandard(Double.parseDouble(sheet.getCell(4, i2).getContents()));
                    bean.setcStandard(Double.parseDouble(sheet.getCell(5, i2).getContents()));
                    bean.setCalciumStandard(Double.parseDouble(sheet.getCell(6, i2).getContents()));
                    bean.setPhosphorusStandard(Double.parseDouble(sheet.getCell(7, i2).getContents()));
                    bean.setTieStandard(Double.parseDouble(sheet.getCell(8, i2).getContents()));
                    bean.setBoStandard(Double.parseDouble(sheet.getCell(9, i2).getContents()));
                    bean.setWeiAStandard(Double.parseDouble(sheet.getCell(10, i2).getContents()));
                    bean.setWeiB1Standard(Double.parseDouble(sheet.getCell(11, i2).getContents()));
                    bean.setWeiB2Standard(Double.parseDouble(sheet.getCell(12, i2).getContents()));
                    bean.setWeiCStandard(Double.parseDouble(sheet.getCell(13, i2).getContents()));
                    map.put(itemName, bean);
                }
            }
            workbook.close();
            if (workbook != null) {
                workbook.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (workbook != null) {
                workbook.close();
            }
        } catch (Throwable th) {
            if (workbook != null) {
                workbook.close();
            }
            throw th;
        }
        return map;
    }
}
