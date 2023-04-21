package cn.com.bioeasy.app.widgets;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import cn.com.bioeasy.app.commonlib.R;
import cn.com.bioeasy.app.widgets.abslistview.CommonAdapter;

public class SearchView extends LinearLayout implements View.OnClickListener {
    private ImageView btnBack;
    private Button btnSearch;
    /* access modifiers changed from: private */
    public EditText etInput;
    /* access modifiers changed from: private */
    public ImageView ivDelete;
    /* access modifiers changed from: private */
    public ListView lvTips;
    /* access modifiers changed from: private */
    public CommonAdapter<String> mAutoCompleteAdapter;
    private Context mContext;
    /* access modifiers changed from: private */
    public SearchViewListener mListener;

    public interface SearchViewListener {
        void onRefreshAutoComplete(String str);

        void onSearch(String str);
    }

    public void setSearchViewListener(SearchViewListener listener) {
        this.mListener = listener;
    }

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        LayoutInflater.from(context).inflate(R.layout.search_layout, this);
        initViews();
    }

    private void initViews() {
        this.etInput = (EditText) findViewById(R.id.search_et_input);
        this.ivDelete = (ImageView) findViewById(R.id.search_iv_delete);
        this.btnBack = (ImageView) findViewById(R.id.search_btn_back);
        this.lvTips = (ListView) findViewById(R.id.search_lv_tips);
        this.btnSearch = (Button) findViewById(R.id.search_btn);
        this.lvTips.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String text = SearchView.this.lvTips.getAdapter().getItem(i).toString();
                SearchView.this.etInput.setText(text);
                SearchView.this.etInput.setSelection(text.length());
                SearchView.this.notifyStartSearching(text);
            }
        });
        this.ivDelete.setOnClickListener(this);
        this.btnBack.setOnClickListener(this);
        this.btnSearch.setOnClickListener(this);
        this.etInput.addTextChangedListener(new EditChangedListener());
        this.etInput.setOnClickListener(this);
        this.etInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId != 3) {
                    return true;
                }
                SearchView.this.notifyStartSearching(SearchView.this.etInput.getText().toString());
                return true;
            }
        });
    }

    /* access modifiers changed from: private */
    public void notifyStartSearching(String text) {
        setLvTipsVisible(false);
        if (this.mListener != null) {
            this.mListener.onSearch(this.etInput.getText().toString());
        }
        ((InputMethodManager) this.mContext.getSystemService("input_method")).toggleSoftInput(0, 2);
    }

    public void setSearchText(String text) {
        this.etInput.setText(text);
        this.etInput.setSelection(text.length());
        notifyStartSearching(text);
    }

    public void setAutoCompleteAdapter(CommonAdapter<String> adapter) {
        this.mAutoCompleteAdapter = adapter;
    }

    private class EditChangedListener implements TextWatcher {
        private EditChangedListener() {
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (!"".equals(charSequence.toString())) {
                SearchView.this.ivDelete.setVisibility(0);
                if (!(SearchView.this.mAutoCompleteAdapter == null || SearchView.this.lvTips.getAdapter() == SearchView.this.mAutoCompleteAdapter)) {
                    SearchView.this.lvTips.setAdapter(SearchView.this.mAutoCompleteAdapter);
                }
            }
            if (SearchView.this.mListener != null) {
                SearchView.this.mListener.onRefreshAutoComplete(charSequence + "");
            }
        }

        public void afterTextChanged(Editable editable) {
        }
    }

    public void setLvTipsVisible(boolean visible) {
        this.lvTips.setVisibility(visible ? 0 : 8);
    }

    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.search_iv_delete) {
            this.etInput.setText("");
            this.ivDelete.setVisibility(8);
            if (this.mListener != null) {
                this.mListener.onRefreshAutoComplete("");
            }
        } else if (i == R.id.search_btn_back) {
            ((Activity) this.mContext).finish();
        } else if (i == R.id.search_btn) {
            notifyStartSearching(this.etInput.getText().toString());
        }
    }
}
