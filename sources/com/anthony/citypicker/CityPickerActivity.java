package com.anthony.citypicker;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.anthony.citypicker.adapter.CityListAdapter;
import com.anthony.citypicker.adapter.ResultListAdapter;
import com.anthony.citypicker.db.DBManager;
import com.anthony.citypicker.model.City;
import com.anthony.citypicker.model.LocateState;
import com.anthony.citypicker.utils.StringUtils;
import com.anthony.citypicker.utils.ToastUtils;
import com.anthony.citypicker.view.SideLetterBar;
import java.util.List;

public class CityPickerActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String KEY_PICKED_CITY = "picked_city";
    public static final int REQUEST_CODE_PICK_CITY = 2333;
    private ImageView backBtn;
    /* access modifiers changed from: private */
    public ImageView clearBtn;
    /* access modifiers changed from: private */
    public DBManager dbManager;
    /* access modifiers changed from: private */
    public ViewGroup emptyView;
    private List<City> mAllCities;
    /* access modifiers changed from: private */
    public CityListAdapter mCityAdapter;
    private SideLetterBar mLetterBar;
    /* access modifiers changed from: private */
    public ListView mListView;
    /* access modifiers changed from: private */
    public AMapLocationClient mLocationClient;
    /* access modifiers changed from: private */
    public ResultListAdapter mResultAdapter;
    /* access modifiers changed from: private */
    public ListView mResultListView;
    private EditText searchBox;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);
        initData();
        initView();
        initLocation();
    }

    private void initLocation() {
        this.mLocationClient = new AMapLocationClient(this);
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        option.setOnceLocation(true);
        this.mLocationClient.setLocationOption(option);
        this.mLocationClient.setLocationListener(new AMapLocationListener() {
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation == null) {
                    return;
                }
                if (aMapLocation.getErrorCode() == 0) {
                    String city = aMapLocation.getCity();
                    String district = aMapLocation.getDistrict();
                    Log.e("onLocationChanged", "city: " + city);
                    Log.e("onLocationChanged", "district: " + district);
                    CityPickerActivity.this.mCityAdapter.updateLocateState(LocateState.SUCCESS, StringUtils.extractLocation(city, district));
                    return;
                }
                CityPickerActivity.this.mCityAdapter.updateLocateState(666, (String) null);
            }
        });
        this.mLocationClient.startLocation();
    }

    private void initData() {
        this.dbManager = new DBManager(this);
        this.dbManager.copyDBFile();
        this.mAllCities = this.dbManager.getAllCities();
        this.mCityAdapter = new CityListAdapter(this, this.mAllCities);
        this.mCityAdapter.setOnCityClickListener(new CityListAdapter.OnCityClickListener() {
            public void onCityClick(String name) {
                CityPickerActivity.this.back(name);
            }

            public void onLocateClick() {
                Log.e("onLocateClick", "重新定位...");
                CityPickerActivity.this.mCityAdapter.updateLocateState(111, (String) null);
                CityPickerActivity.this.mLocationClient.startLocation();
            }
        });
        this.mResultAdapter = new ResultListAdapter(this, (List<City>) null);
    }

    private void initView() {
        this.mListView = (ListView) findViewById(R.id.listview_all_city);
        this.mListView.setAdapter(this.mCityAdapter);
        this.mLetterBar = (SideLetterBar) findViewById(R.id.side_letter_bar);
        this.mLetterBar.setOverlay((TextView) findViewById(R.id.tv_letter_overlay));
        this.mLetterBar.setOnLetterChangedListener(new SideLetterBar.OnLetterChangedListener() {
            public void onLetterChanged(String letter) {
                CityPickerActivity.this.mListView.setSelection(CityPickerActivity.this.mCityAdapter.getLetterPosition(letter));
            }
        });
        this.searchBox = (EditText) findViewById(R.id.et_search);
        this.searchBox.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                String keyword = s.toString();
                if (TextUtils.isEmpty(keyword)) {
                    CityPickerActivity.this.clearBtn.setVisibility(8);
                    CityPickerActivity.this.emptyView.setVisibility(8);
                    CityPickerActivity.this.mResultListView.setVisibility(8);
                    return;
                }
                CityPickerActivity.this.clearBtn.setVisibility(0);
                CityPickerActivity.this.mResultListView.setVisibility(0);
                List<City> result = CityPickerActivity.this.dbManager.searchCity(keyword);
                if (result == null || result.size() == 0) {
                    CityPickerActivity.this.emptyView.setVisibility(0);
                    return;
                }
                CityPickerActivity.this.emptyView.setVisibility(8);
                CityPickerActivity.this.mResultAdapter.changeData(result);
            }
        });
        this.emptyView = (ViewGroup) findViewById(R.id.empty_view);
        this.mResultListView = (ListView) findViewById(R.id.listview_search_result);
        this.mResultListView.setAdapter(this.mResultAdapter);
        this.mResultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                CityPickerActivity.this.back(CityPickerActivity.this.mResultAdapter.getItem(position).getName());
            }
        });
        this.clearBtn = (ImageView) findViewById(R.id.iv_search_clear);
        this.backBtn = (ImageView) findViewById(R.id.back);
        this.clearBtn.setOnClickListener(this);
        this.backBtn.setOnClickListener(this);
    }

    /* access modifiers changed from: private */
    public void back(String city) {
        ToastUtils.showToast((Context) this, "点击的城市：" + city);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.iv_search_clear) {
            this.searchBox.setText("");
            this.clearBtn.setVisibility(8);
            this.emptyView.setVisibility(8);
            this.mResultListView.setVisibility(8);
        } else if (v.getId() == R.id.back) {
            finish();
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        this.mLocationClient.stopLocation();
    }
}
