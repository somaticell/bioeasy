package cn.com.bioeasy.healty.app.healthapp.modules.mall.view;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import cn.com.bioeasy.app.utils.StringUtil;
import cn.com.bioeasy.app.widgets.abslistview.CommonAdapter;
import cn.com.bioeasy.app.widgets.abslistview.ViewHolder;
import cn.com.bioeasy.app.widgets.util.ToastUtils;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.base.BaseDialog;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.GoodsModalSpec;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.GoodsSpec;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.GoodsWithBLOBs;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.ShopCardItem;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.tagview.Tag;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.tagview.TagListView;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.tagview.TagView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GoodsSelectDialog extends BaseDialog implements TagListView.OnTagClickListener {
    @Bind({2131231245})
    Button btnMinus;
    @Bind({2131231243})
    Button btnPlus;
    private GoodsSpec currentSelectSpec;
    private String currentSpecKeys;
    private Context cxt;
    @Bind({2131231244})
    EditText edtCount;
    @Bind({2131231237})
    ImageView goodIcon;
    private GoodsWithBLOBs goods;
    @Bind({2131231241})
    ListView listview;
    @Bind({2131231239})
    TextView mtvType;
    private int selectCount = 1;
    private Map<String, Integer> selectSpecVal = new HashMap();
    CommonAdapter<GoodsModalSpec> specAdapter;
    private int specCount;
    private int stockCount = 1;
    @Bind({2131231238})
    TextView tvGoodPrice;

    public GoodsSelectDialog(Context context, GoodsWithBLOBs goods2) {
        super(context, R.style.my_dialog);
        this.goods = goods2;
        this.stockCount = this.goods.getStockCount();
    }

    /* access modifiers changed from: protected */
    public int getLayoutId() {
        return R.layout.dialog_shopping_select;
    }

    /* access modifiers changed from: protected */
    public void initView(View view, Context context) {
        this.cxt = context;
    }

    private void initSpec() {
        Glide.with(this.cxt).load(this.goods.getIcon()).centerCrop().placeholder((int) R.drawable.ic_split_graph).override(80, 80).error((int) R.drawable.ic_split_graph).into(this.goodIcon);
        this.specAdapter = new CommonAdapter<GoodsModalSpec>(this.cxt, R.layout.fragment_spproduct_list_filter_item, this.goods.getModel().getSpecs()) {
            /* access modifiers changed from: protected */
            public void convert(ViewHolder holder, GoodsModalSpec specsBean, int position) {
                TagListView tagListView = (TagListView) holder.getView(R.id.filter_taglstv);
                tagListView.setTagViewBackgroundRes(R.drawable.tag_button_bg_unchecked);
                tagListView.setTagViewTextColorRes(R.color.color_font_gray);
                tagListView.setOnTagClickListener(GoodsSelectDialog.this);
                List<String> list = Arrays.asList(specsBean.getValue().split("\n"));
                List<Tag> tagList = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    Tag tag = new Tag();
                    String id = specsBean.getId() + "" + i;
                    tag.setId(Integer.parseInt(id));
                    tag.setTypeValue(id);
                    tag.setValue(id);
                    tag.setTitle(list.get(i));
                    tag.setKey(String.valueOf(specsBean.getId()));
                    tagList.add(tag);
                }
                tagListView.setTags(tagList);
                holder.setText(R.id.filter_title_txtv, specsBean.getName());
            }
        };
        if (this.goods.getSpecs() != null && this.goods.getSpecs().size() > 0) {
            this.specCount = this.goods.getModel().getSpecs().size();
        }
        if (this.specCount > 0) {
            this.mtvType.setVisibility(0);
            this.listview.setAdapter(this.specAdapter);
            this.tvGoodPrice.setText(String.format(this.cxt.getString(R.string.product_price), new Object[]{this.goods.getShopPrice()}));
            return;
        }
        this.mtvType.setVisibility(8);
        this.tvGoodPrice.setText(String.format(this.cxt.getString(R.string.product_price), new Object[]{this.goods.getShopPrice()}));
    }

    @OnClick({2131231240, 2131231246, 2131231245, 2131231243})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                hide();
                return;
            case R.id.cart_plus_btn:
                dealCount(1);
                return;
            case R.id.cart_minus_btn:
                dealCount(-1);
                return;
            case R.id.bt_addcart:
                if (this.selectSpecVal.size() < this.specCount) {
                    ToastUtils.showToast((int) R.string.ORDER_PRODUCT_SPEC);
                    return;
                } else {
                    addShopCart();
                    return;
                }
            default:
                return;
        }
    }

    private void dealCount(int count) {
        this.selectCount = Integer.parseInt(this.edtCount.getText().toString().trim());
        this.selectCount += count;
        this.selectCount = this.selectCount <= 0 ? 1 : this.selectCount;
        this.selectCount = this.selectCount > this.stockCount ? this.stockCount : this.selectCount;
        this.edtCount.setText(String.valueOf(this.selectCount));
    }

    private synchronized void addShopCart() {
        String price;
        ShopCardItem item = new ShopCardItem();
        item.setGoodId(this.goods.getId());
        item.setGoodSpecKeys(this.currentSpecKeys);
        String specNames = this.currentSelectSpec != null ? this.currentSelectSpec.getSpecNames() : "";
        int specId = this.currentSelectSpec != null ? this.currentSelectSpec.getId() : 0;
        if (this.currentSelectSpec != null) {
            price = this.currentSelectSpec.getPrice();
        } else {
            price = this.goods.getShopPrice();
        }
        item.setPrice(price);
        item.setGoodSpecKeyNames(specNames);
        item.setGoodsSpecId(specId);
        item.setAmount(this.selectCount);
        item.save();
        hide();
    }

    public void onTagClick(TagView tagView, Tag tag) {
        this.selectSpecVal.put(tag.getKey(), Integer.valueOf(tag.getId()));
        if (this.selectSpecVal.size() == this.specCount) {
            refreshPrice();
            this.mtvType.setVisibility(8);
        }
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        initSpec();
    }

    private void refreshPrice() {
        List<Integer> specList = new ArrayList<>(this.selectSpecVal.values());
        Collections.sort(specList);
        String specKeys = "";
        for (int i = 0; i < specList.size(); i++) {
            specKeys = specKeys + specList.get(i);
            if (i < specList.size() - 1) {
                specKeys = specKeys + "_";
            }
        }
        this.currentSpecKeys = specKeys;
        Iterator<GoodsSpec> it = this.goods.getSpecs().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            GoodsSpec specBean = it.next();
            if (specKeys.equals(specBean.getSpecKeys())) {
                this.currentSelectSpec = specBean;
                break;
            }
        }
        if (this.currentSelectSpec != null) {
            if (!StringUtil.isNullOrEmpty(this.currentSelectSpec.getStockCount())) {
                this.stockCount = Integer.parseInt(this.currentSelectSpec.getStockCount());
            }
            this.tvGoodPrice.setText("￥" + String.valueOf(this.selectCount * Integer.parseInt(this.currentSelectSpec.getPrice())));
            return;
        }
        this.stockCount = this.goods.getStockCount();
        this.tvGoodPrice.setText("￥" + String.valueOf(this.selectCount * Integer.parseInt(this.goods.getCostPrice())));
    }
}
