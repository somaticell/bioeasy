package cn.com.bioeasy.healty.app.healthapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.AddressBean;
import java.util.List;

public class AddressAdapter extends BaseAdapter {
    public static final String EDITING = "编辑";
    public static final String FINISH_EDITING = "完成";
    public AddressListener addressListener;
    private Context cxt;
    /* access modifiers changed from: private */
    public List<AddressBean> lists;
    private int type;

    public interface AddressListener {
        void deleteAddress(int i);

        void updateAddress(AddressBean addressBean);
    }

    public void setAddressListener(AddressListener listener) {
        this.addressListener = listener;
    }

    public AddressAdapter(Context cxt2, List<AddressBean> lists2) {
        this.cxt = cxt2;
        this.lists = lists2;
    }

    public void addData(List<AddressBean> lists2) {
        this.lists = lists2;
        notifyDataSetChanged();
    }

    public void refreshType(int type2) {
        this.type = type2;
        notifyDataSetChanged();
    }

    public int getCount() {
        if (this.lists == null) {
            return 0;
        }
        return this.lists.size();
    }

    public Object getItem(int position) {
        return this.lists.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        boolean z = false;
        ViewHolder viewHolder = new ViewHolder();
        final AddressBean addressBean = this.lists.get(position);
        View convertView2 = LayoutInflater.from(this.cxt).inflate(R.layout.address_items, parent, false);
        TextView unused = viewHolder.mtvName = (TextView) convertView2.findViewById(R.id.tv_person);
        TextView unused2 = viewHolder.mtvPhone = (TextView) convertView2.findViewById(R.id.tv_phone);
        TextView unused3 = viewHolder.mtvAddress = (TextView) convertView2.findViewById(R.id.tv_address);
        RelativeLayout unused4 = viewHolder.rlManager = (RelativeLayout) convertView2.findViewById(R.id.rl_manager);
        TextView unused5 = viewHolder.tvDelete = (TextView) convertView2.findViewById(R.id.tv_delete);
        TextView unused6 = viewHolder.tvDefaultAddress = (TextView) convertView2.findViewById(R.id.tv_default_address);
        viewHolder.tvDefaultAddress.setVisibility(addressBean.getIsDefault() == 0 ? 0 : 8);
        CheckBox unused7 = viewHolder.cbSelect = (CheckBox) convertView2.findViewById(R.id.id_cb_select_child);
        TextView unused8 = viewHolder.tvEdit = (TextView) convertView2.findViewById(R.id.tv_edit);
        if (this.type == 1) {
            viewHolder.rlManager.setVisibility(0);
        } else {
            viewHolder.rlManager.setVisibility(8);
        }
        viewHolder.mtvName.setText(addressBean.getConsignee());
        viewHolder.mtvPhone.setText(addressBean.getMobile());
        viewHolder.mtvAddress.setText(addressBean.getProvince() + addressBean.getCity() + addressBean.getDistrict() + addressBean.getAddress());
        viewHolder.tvDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (AddressAdapter.this.addressListener != null) {
                    AddressAdapter.this.addressListener.deleteAddress(addressBean.getId());
                }
            }
        });
        CheckBox access$600 = viewHolder.cbSelect;
        if (addressBean.getIsDefault() == 1) {
            z = true;
        }
        access$600.setChecked(z);
        viewHolder.tvEdit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (AddressAdapter.this.addressListener != null) {
                    AddressAdapter.this.addressListener.updateAddress((AddressBean) AddressAdapter.this.lists.get(position));
                }
            }
        });
        return convertView2;
    }

    public class ViewHolder {
        /* access modifiers changed from: private */
        public CheckBox cbSelect;
        /* access modifiers changed from: private */
        public TextView mtvAddress;
        /* access modifiers changed from: private */
        public TextView mtvName;
        /* access modifiers changed from: private */
        public TextView mtvPhone;
        /* access modifiers changed from: private */
        public RelativeLayout rlManager;
        /* access modifiers changed from: private */
        public TextView tvDefaultAddress;
        /* access modifiers changed from: private */
        public TextView tvDelete;
        /* access modifiers changed from: private */
        public TextView tvEdit;

        public ViewHolder() {
        }
    }
}
