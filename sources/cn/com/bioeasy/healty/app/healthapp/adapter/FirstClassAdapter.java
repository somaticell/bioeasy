package cn.com.bioeasy.healty.app.healthapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.home.bean.FirstClassItem;
import java.util.List;

public class FirstClassAdapter extends BaseAdapter {
    private Context context;
    private List<FirstClassItem> list;
    private int selectedPosition = 0;

    public FirstClassAdapter(Context context2, List<FirstClassItem> list2) {
        this.context = context2;
        this.list = list2;
    }

    public int getCount() {
        if (this.list == null) {
            return 0;
        }
        return this.list.size();
    }

    public Object getItem(int i) {
        return null;
    }

    public long getItemId(int i) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.left_listview_item, (ViewGroup) null);
            holder = new ViewHolder();
            holder.nameTV = (TextView) convertView.findViewById(R.id.left_item_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position == this.selectedPosition) {
            convertView.setBackgroundResource(R.color.popup_right_bg);
        } else {
            convertView.setBackgroundResource(R.drawable.selector_left_normal);
        }
        holder.nameTV.setText(this.list.get(position).getName());
        holder.nameTV.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        return convertView;
    }

    public void setSelectedPosition(int selectedPosition2) {
        this.selectedPosition = selectedPosition2;
    }

    public int getSelectedPosition() {
        return this.selectedPosition;
    }

    private class ViewHolder {
        TextView nameTV;

        private ViewHolder() {
        }
    }
}
