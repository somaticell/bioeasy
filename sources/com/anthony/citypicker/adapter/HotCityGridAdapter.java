package com.anthony.citypicker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.anthony.citypicker.R;
import java.util.ArrayList;
import java.util.List;

public class HotCityGridAdapter extends BaseAdapter {
    private List<String> mCities = new ArrayList();
    private Context mContext;

    public static class HotCityViewHolder {
        TextView name;
    }

    public HotCityGridAdapter(Context context) {
        this.mContext = context;
        this.mCities.add("北京");
        this.mCities.add("上海");
        this.mCities.add("广州");
        this.mCities.add("深圳");
        this.mCities.add("杭州");
        this.mCities.add("南京");
        this.mCities.add("天津");
        this.mCities.add("武汉");
        this.mCities.add("重庆");
    }

    public int getCount() {
        if (this.mCities == null) {
            return 0;
        }
        return this.mCities.size();
    }

    public String getItem(int position) {
        if (this.mCities == null) {
            return null;
        }
        return this.mCities.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View view, ViewGroup parent) {
        HotCityViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(this.mContext).inflate(R.layout.item_hot_city_gridview, parent, false);
            holder = new HotCityViewHolder();
            holder.name = (TextView) view.findViewById(R.id.tv_hot_city_name);
            view.setTag(holder);
        } else {
            holder = (HotCityViewHolder) view.getTag();
        }
        holder.name.setText(this.mCities.get(position));
        return view;
    }
}
