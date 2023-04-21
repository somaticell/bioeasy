package com.anthony.citypicker.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.alipay.sdk.cons.a;
import com.anthony.citypicker.R;
import com.anthony.citypicker.model.City;
import com.anthony.citypicker.model.LocateState;
import com.anthony.citypicker.utils.PinyinUtils;
import com.anthony.citypicker.view.WrapHeightGridView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CityListAdapter extends BaseAdapter {
    private static final int VIEW_TYPE_COUNT = 3;
    private LayoutInflater inflater;
    private HashMap<String, Integer> letterIndexes;
    /* access modifiers changed from: private */
    public int locateState = 111;
    /* access modifiers changed from: private */
    public String locatedCity;
    private List<City> mCities;
    private Context mContext;
    /* access modifiers changed from: private */
    public OnCityClickListener onCityClickListener;
    private String[] sections;

    public static class CityViewHolder {
        TextView letter;
        TextView name;
    }

    public interface OnCityClickListener {
        void onCityClick(String str);

        void onLocateClick();
    }

    public CityListAdapter(Context mContext2, List<City> mCities2) {
        this.mContext = mContext2;
        this.mCities = mCities2;
        this.inflater = LayoutInflater.from(mContext2);
        mCities2 = mCities2 == null ? new ArrayList<>() : mCities2;
        mCities2.add(0, new City("定位", "0"));
        mCities2.add(1, new City("热门", a.e));
        int size = mCities2.size();
        this.letterIndexes = new HashMap<>();
        this.sections = new String[size];
        int index = 0;
        while (index < size) {
            String currentLetter = PinyinUtils.getFirstLetter(mCities2.get(index).getPinyin());
            if (!TextUtils.equals(currentLetter, index >= 1 ? PinyinUtils.getFirstLetter(mCities2.get(index - 1).getPinyin()) : "")) {
                this.letterIndexes.put(currentLetter, Integer.valueOf(index));
                this.sections[index] = currentLetter;
            }
            index++;
        }
    }

    public void updateLocateState(int state, String city) {
        this.locateState = state;
        this.locatedCity = city;
        notifyDataSetChanged();
    }

    public int getLetterPosition(String letter) {
        Integer integer = this.letterIndexes.get(letter);
        if (integer == null) {
            return -1;
        }
        return integer.intValue();
    }

    public int getViewTypeCount() {
        return 3;
    }

    public int getItemViewType(int position) {
        if (position < 2) {
            return position;
        }
        return 2;
    }

    public int getCount() {
        if (this.mCities == null) {
            return 0;
        }
        return this.mCities.size();
    }

    public City getItem(int position) {
        if (this.mCities == null) {
            return null;
        }
        return this.mCities.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View view, ViewGroup parent) {
        CityViewHolder holder;
        switch (getItemViewType(position)) {
            case 0:
                View view2 = this.inflater.inflate(R.layout.view_locate_city, parent, false);
                ViewGroup container = (ViewGroup) view2.findViewById(R.id.layout_locate);
                TextView state = (TextView) view2.findViewById(R.id.tv_located_city);
                switch (this.locateState) {
                    case 111:
                        state.setText(this.mContext.getString(R.string.locating));
                        break;
                    case 666:
                        state.setText(R.string.located_failed);
                        break;
                    case LocateState.SUCCESS:
                        state.setText(this.locatedCity);
                        break;
                }
                container.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if (CityListAdapter.this.locateState == 666) {
                            if (CityListAdapter.this.onCityClickListener != null) {
                                CityListAdapter.this.onCityClickListener.onLocateClick();
                            }
                        } else if (CityListAdapter.this.locateState == 888 && CityListAdapter.this.onCityClickListener != null) {
                            CityListAdapter.this.onCityClickListener.onCityClick(CityListAdapter.this.locatedCity);
                        }
                    }
                });
                return view2;
            case 1:
                View view3 = this.inflater.inflate(R.layout.view_hot_city, parent, false);
                WrapHeightGridView gridView = (WrapHeightGridView) view3.findViewById(R.id.gridview_hot_city);
                final HotCityGridAdapter hotCityGridAdapter = new HotCityGridAdapter(this.mContext);
                gridView.setAdapter(hotCityGridAdapter);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        if (CityListAdapter.this.onCityClickListener != null) {
                            CityListAdapter.this.onCityClickListener.onCityClick(hotCityGridAdapter.getItem(position));
                        }
                    }
                });
                return view3;
            case 2:
                if (view == null) {
                    view = this.inflater.inflate(R.layout.item_city_listview, parent, false);
                    holder = new CityViewHolder();
                    holder.letter = (TextView) view.findViewById(R.id.tv_item_city_listview_letter);
                    holder.name = (TextView) view.findViewById(R.id.tv_item_city_listview_name);
                    view.setTag(holder);
                } else {
                    holder = (CityViewHolder) view.getTag();
                }
                if (position < 1) {
                    return view;
                }
                final String city = this.mCities.get(position).getName();
                holder.name.setText(city);
                String currentLetter = PinyinUtils.getFirstLetter(this.mCities.get(position).getPinyin());
                if (!TextUtils.equals(currentLetter, position >= 1 ? PinyinUtils.getFirstLetter(this.mCities.get(position - 1).getPinyin()) : "")) {
                    holder.letter.setVisibility(0);
                    holder.letter.setText(currentLetter);
                } else {
                    holder.letter.setVisibility(8);
                }
                holder.name.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if (CityListAdapter.this.onCityClickListener != null) {
                            CityListAdapter.this.onCityClickListener.onCityClick(city);
                        }
                    }
                });
                return view;
            default:
                return view;
        }
    }

    public void setOnCityClickListener(OnCityClickListener listener) {
        this.onCityClickListener = listener;
    }
}
