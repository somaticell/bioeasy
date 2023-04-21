package cn.com.bioeasy.healty.app.healthapp.modules.mall.view;

import android.content.Context;
import android.view.View;
import butterknife.Bind;
import butterknife.OnClick;
import cn.com.bioeasy.app.widgets.wheel.OnWheelChangedListener;
import cn.com.bioeasy.app.widgets.wheel.WheelView;
import cn.com.bioeasy.app.widgets.wheel.adapters.ArrayWheelAdapter;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.base.BaseDialog;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.AreaBean;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.CityBean;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.DistrictBean;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.ProvinceBean;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.service.XmlParserHandler;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class SelectAreaDialog extends BaseDialog implements OnWheelChangedListener {
    Context cxt;
    protected Map<String, String[]> mCitisDatasMap;
    @Bind({2131231144})
    WheelView mCity;
    protected String mCurrentCityName;
    protected String mCurrentDistrictName = "";
    protected String mCurrentProviceName;
    @Bind({2131231216})
    WheelView mDistrict;
    protected Map<String, String[]> mDistrictDatasMap;
    @Bind({2131231215})
    WheelView mProvince;
    protected String[] mProvinceDatas;
    public SendData sendDatas;

    public interface SendData {
        void send(AreaBean areaBean);
    }

    public void obtainData(SendData sendData) {
        this.sendDatas = sendData;
    }

    public SelectAreaDialog(Context context) {
        super(context, R.style.my_dialog);
    }

    /* access modifiers changed from: protected */
    public int getLayoutId() {
        return R.layout.dialog_city;
    }

    /* access modifiers changed from: protected */
    public void initView(View view, Context context) {
        this.cxt = context;
        setUpListener();
        setUpData();
    }

    private void setUpListener() {
        this.mProvince.addChangingListener(this);
        this.mCity.addChangingListener(this);
        this.mDistrict.addChangingListener(this);
    }

    @OnClick({2131231213, 2131231214})
    public void onClick(View view) {
        if (view.getId() != R.id.btn_confire) {
            dismiss();
        } else if (!this.mCurrentProviceName.isEmpty() || !this.mCurrentCityName.isEmpty() || !this.mCurrentDistrictName.isEmpty()) {
            AreaBean bean = new AreaBean();
            bean.setProvice(this.mCurrentProviceName);
            bean.setCity(this.mCurrentCityName);
            bean.setDistrict(this.mCurrentDistrictName);
            if (this.sendDatas != null) {
                this.sendDatas.send(bean);
                dismiss();
            }
        }
    }

    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == this.mProvince) {
            updateCities();
        } else if (wheel == this.mCity) {
            updateAreas();
        } else if (wheel == this.mDistrict) {
            this.mCurrentDistrictName = this.mDistrictDatasMap.get(this.mCurrentCityName)[newValue];
        }
    }

    private void setUpData() {
        this.mCitisDatasMap = new HashMap();
        this.mDistrictDatasMap = new HashMap();
        initProvinceDatas();
        this.mProvince.setViewAdapter(new ArrayWheelAdapter(this.cxt, this.mProvinceDatas));
        this.mProvince.setVisibleItems(7);
        this.mCity.setVisibleItems(7);
        this.mDistrict.setVisibleItems(7);
        updateCities();
        updateAreas();
    }

    /* access modifiers changed from: protected */
    public void initProvinceDatas() {
        try {
            InputStream input = this.cxt.getAssets().open("province_data.xml");
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
            List<ProvinceBean> provinceList = handler.getDataList();
            if (provinceList != null && !provinceList.isEmpty()) {
                this.mCurrentProviceName = provinceList.get(0).getName();
                List<CityBean> cityList = provinceList.get(0).getCityList();
                if (cityList != null && !cityList.isEmpty()) {
                    this.mCurrentCityName = cityList.get(0).getName();
                    this.mCurrentDistrictName = cityList.get(0).getDistrictList().get(0).getName();
                }
            }
            this.mProvinceDatas = new String[provinceList.size()];
            for (int i = 0; i < provinceList.size(); i++) {
                this.mProvinceDatas[i] = provinceList.get(i).getName();
                List<CityBean> cityList2 = provinceList.get(i).getCityList();
                String[] cityNames = new String[cityList2.size()];
                for (int j = 0; j < cityList2.size(); j++) {
                    cityNames[j] = cityList2.get(j).getName();
                    List<DistrictBean> districtList = cityList2.get(j).getDistrictList();
                    String[] distrinctNameArray = new String[districtList.size()];
                    DistrictBean[] distrinctArray = new DistrictBean[districtList.size()];
                    for (int k = 0; k < districtList.size(); k++) {
                        if (districtList.get(k) == null) {
                            throw new RuntimeException();
                        }
                        DistrictBean districtModel = new DistrictBean(districtList.get(k).getName());
                        distrinctArray[k] = districtModel;
                        distrinctNameArray[k] = districtModel.getName();
                    }
                    this.mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
                }
                if (this.mCitisDatasMap == null) {
                    this.mCitisDatasMap = new HashMap();
                }
                this.mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void updateAreas() {
        this.mCurrentCityName = this.mCitisDatasMap.get(this.mCurrentProviceName)[this.mCity.getCurrentItem()];
        String[] areas = this.mDistrictDatasMap.get(this.mCurrentCityName);
        if (areas == null) {
            areas = new String[]{""};
        }
        this.mDistrict.setViewAdapter(new ArrayWheelAdapter(this.cxt, areas));
        this.mDistrict.setCurrentItem(0);
    }

    private void updateCities() {
        this.mCurrentProviceName = this.mProvinceDatas[this.mProvince.getCurrentItem()];
        String[] cities = this.mCitisDatasMap.get(this.mCurrentProviceName);
        if (cities == null) {
            cities = new String[]{""};
        }
        this.mCity.setViewAdapter(new ArrayWheelAdapter(this.cxt, cities));
        this.mCity.setCurrentItem(0);
        updateAreas();
    }
}
