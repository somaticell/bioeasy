package cn.com.bioeasy.healty.app.healthapp.modules.home.view;

import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.internal.DebouncingOnClickListener;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.home.view.NutritionCalculationActivity;

public class NutritionCalculationActivity$$ViewBinder<T extends NutritionCalculationActivity> implements ButterKnife.ViewBinder<T> {
    public void bind(ButterKnife.Finder finder, final T target, Object source) {
        target.tvTitle = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_title, "field 'tvTitle'"), R.id.tv_title, "field 'tvTitle'");
        target.mAcText = (AutoCompleteTextView) finder.castView((View) finder.findRequiredView(source, R.id.ac_txt, "field 'mAcText'"), R.id.ac_txt, "field 'mAcText'");
        target.mTvHeat = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.heat, "field 'mTvHeat'"), R.id.heat, "field 'mTvHeat'");
        target.mTvHeatS = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.heat_s, "field 'mTvHeatS'"), R.id.heat_s, "field 'mTvHeatS'");
        target.mTvProtein = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.protein, "field 'mTvProtein'"), R.id.protein, "field 'mTvProtein'");
        target.mTvProteinS = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.protein_s, "field 'mTvProteinS'"), R.id.protein_s, "field 'mTvProteinS'");
        target.mTvFat = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.fat, "field 'mTvFat'"), R.id.fat, "field 'mTvFat'");
        target.mTvFatS = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.fat_s, "field 'mTvFatS'"), R.id.fat_s, "field 'mTvFatS'");
        target.mTvTangshui = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tangshui, "field 'mTvTangshui'"), R.id.tangshui, "field 'mTvTangshui'");
        target.mTvTangshuiS = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tangshui_s, "field 'mTvTangshuiS'"), R.id.tangshui_s, "field 'mTvTangshuiS'");
        target.mTvCalciun = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.calcium, "field 'mTvCalciun'"), R.id.calcium, "field 'mTvCalciun'");
        target.mTvCalciunS = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.calcium_s, "field 'mTvCalciunS'"), R.id.calcium_s, "field 'mTvCalciunS'");
        target.mTvPhosphorus = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.phosphorus, "field 'mTvPhosphorus'"), R.id.phosphorus, "field 'mTvPhosphorus'");
        target.mTvPhosphorusS = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.phosphorus_s, "field 'mTvPhosphorusS'"), R.id.phosphorus_s, "field 'mTvPhosphorusS'");
        target.mTvTie = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tie, "field 'mTvTie'"), R.id.tie, "field 'mTvTie'");
        target.mTvTieS = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tie_s, "field 'mTvTieS'"), R.id.tie_s, "field 'mTvTieS'");
        target.mTvBo = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.bo, "field 'mTvBo'"), R.id.bo, "field 'mTvBo'");
        target.mTvBoS = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.bo_s, "field 'mTvBoS'"), R.id.bo_s, "field 'mTvBoS'");
        target.mTvA = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.wei_a, "field 'mTvA'"), R.id.wei_a, "field 'mTvA'");
        target.mTvAS = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.wei_a_s, "field 'mTvAS'"), R.id.wei_a_s, "field 'mTvAS'");
        target.mTvB1 = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.wei_b1, "field 'mTvB1'"), R.id.wei_b1, "field 'mTvB1'");
        target.mTvB1S = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.wei_b1_s, "field 'mTvB1S'"), R.id.wei_b1_s, "field 'mTvB1S'");
        target.mTvB2 = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.wei_b2, "field 'mTvB2'"), R.id.wei_b2, "field 'mTvB2'");
        target.mTvB2S = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.wei_b2_s, "field 'mTvB2S'"), R.id.wei_b2_s, "field 'mTvB2S'");
        target.mTvC = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.wei_c, "field 'mTvC'"), R.id.wei_c, "field 'mTvC'");
        target.mTvCS = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.wei_c_s, "field 'mTvCS'"), R.id.wei_c_s, "field 'mTvCS'");
        target.mLlResult = (LinearLayout) finder.castView((View) finder.findRequiredView(source, R.id.calculation_result, "field 'mLlResult'"), R.id.calculation_result, "field 'mLlResult'");
        target.metInputCount = (EditText) finder.castView((View) finder.findRequiredView(source, R.id.et_input, "field 'metInputCount'"), R.id.et_input, "field 'metInputCount'");
        ((View) finder.findRequiredView(source, R.id.iv_back, "method 'onClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
        ((View) finder.findRequiredView(source, R.id.cacle, "method 'onClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
    }

    public void unbind(T target) {
        target.tvTitle = null;
        target.mAcText = null;
        target.mTvHeat = null;
        target.mTvHeatS = null;
        target.mTvProtein = null;
        target.mTvProteinS = null;
        target.mTvFat = null;
        target.mTvFatS = null;
        target.mTvTangshui = null;
        target.mTvTangshuiS = null;
        target.mTvCalciun = null;
        target.mTvCalciunS = null;
        target.mTvPhosphorus = null;
        target.mTvPhosphorusS = null;
        target.mTvTie = null;
        target.mTvTieS = null;
        target.mTvBo = null;
        target.mTvBoS = null;
        target.mTvA = null;
        target.mTvAS = null;
        target.mTvB1 = null;
        target.mTvB1S = null;
        target.mTvB2 = null;
        target.mTvB2S = null;
        target.mTvC = null;
        target.mTvCS = null;
        target.mLlResult = null;
        target.metInputCount = null;
    }
}
