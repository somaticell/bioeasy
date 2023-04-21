package cn.com.bioeasy.healty.app.healthapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.home.bean.SecondClassItem;
import java.util.List;

public class SecondClassAdapter extends BaseAdapter {
    private Context context;
    private List<SecondClassItem> list;

    public SecondClassAdapter(Context context2, List<SecondClassItem> list2) {
        this.context = context2;
        this.list = list2;
    }

    public int getCount() {
        if (this.list == null) {
            return 0;
        }
        return this.list.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(this.context).inflate(R.layout.right_listview_item, (ViewGroup) null);
            holder.nameTV = (TextView) convertView.findViewById(R.id.right_item_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.nameTV.setText(this.list.get(position).getName());
        return convertView;
    }

    private class ViewHolder {
        TextView nameTV;

        private ViewHolder() {
        }
    }
}
