package cn.com.bioeasy.healty.app.healthapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import cn.com.bioeasy.app.utils.PinyinHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SearchAdapter2<T> extends BaseAdapter implements Filterable {
    public static final int ALL = -1;
    private int mFieldId = 0;
    private SearchAdapter2<T>.ArrayFilter mFilter;
    private LayoutInflater mInflater;
    /* access modifiers changed from: private */
    public final Object mLock = new Object();
    /* access modifiers changed from: private */
    public List<T> mObjects;
    /* access modifiers changed from: private */
    public ArrayList<T> mOriginalValues;
    private int mResource;
    /* access modifiers changed from: private */
    public int maxMatch = 10;
    /* access modifiers changed from: private */
    public List<Set<String>> pinyinList;

    public SearchAdapter2(Context context, int textViewResourceId, T[] objects, int maxMatch2) {
        init(context, textViewResourceId, 0, Arrays.asList(objects));
        this.pinyinList = getHanziSpellList(objects);
        this.maxMatch = maxMatch2;
    }

    public SearchAdapter2(Context context, int textViewResourceId, List<T> objects, int maxMatch2) {
        init(context, textViewResourceId, 0, objects);
        this.pinyinList = getHanziSpellList(objects);
        this.maxMatch = maxMatch2;
    }

    private void init(Context context, int resource, int textViewResourceId, List<T> objects) {
        this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        this.mResource = resource;
        this.mObjects = objects;
        this.mFieldId = textViewResourceId;
    }

    private List<Set<String>> getHanziSpellList(T[] hanzi) {
        List<Set<String>> listSet = new ArrayList<>();
        for (T obj : hanzi) {
            listSet.add(PinyinHelper.getPinyin(obj.toString()));
        }
        return listSet;
    }

    private List<Set<String>> getHanziSpellList(List<T> hanzi) {
        List<Set<String>> listSet = new ArrayList<>();
        for (int i = 0; i < hanzi.size(); i++) {
            listSet.add(PinyinHelper.getPinyin(hanzi.get(i).toString()));
        }
        return listSet;
    }

    public int getCount() {
        return this.mObjects.size();
    }

    public T getItem(int position) {
        return this.mObjects.get(position);
    }

    public int getPosition(T item) {
        return this.mObjects.indexOf(item);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        return createViewFromResource(position, convertView, parent, this.mResource);
    }

    private View createViewFromResource(int position, View convertView, ViewGroup parent, int resource) {
        View view;
        TextView text;
        if (convertView == null) {
            view = this.mInflater.inflate(resource, parent, false);
        } else {
            view = convertView;
        }
        try {
            if (this.mFieldId == 0) {
                text = (TextView) view;
            } else {
                text = (TextView) view.findViewById(this.mFieldId);
            }
            text.setText(getItem(position).toString());
            return view;
        } catch (ClassCastException e) {
            Log.e("ArrayAdapter", "You must supply a resource ID for a TextView");
            throw new IllegalStateException("ArrayAdapter requires the resource ID to be a TextView", e);
        }
    }

    public Filter getFilter() {
        if (this.mFilter == null) {
            this.mFilter = new ArrayFilter();
        }
        return this.mFilter;
    }

    private class ArrayFilter extends Filter {
        private ArrayFilter() {
        }

        /* access modifiers changed from: protected */
        public Filter.FilterResults performFiltering(CharSequence prefix) {
            Filter.FilterResults results = new Filter.FilterResults();
            if (SearchAdapter2.this.mOriginalValues == null) {
                synchronized (SearchAdapter2.this.mLock) {
                    ArrayList unused = SearchAdapter2.this.mOriginalValues = new ArrayList(SearchAdapter2.this.mObjects);
                }
            }
            if (prefix == null || prefix.length() == 0) {
                synchronized (SearchAdapter2.this.mLock) {
                    ArrayList<T> list = new ArrayList<>(SearchAdapter2.this.mOriginalValues);
                    results.values = list;
                    results.count = list.size();
                }
            } else {
                String prefixString = prefix.toString().toLowerCase();
                ArrayList<T> hanzi = SearchAdapter2.this.mOriginalValues;
                int count = hanzi.size();
                Set<T> newValues = new HashSet<>(count);
                for (int i = 0; i < count; i++) {
                    T value = hanzi.get(i);
                    String valueText = value.toString().toLowerCase();
                    for (String obj : (Set) SearchAdapter2.this.pinyinList.get(i)) {
                        if (obj.toString().toLowerCase().indexOf(prefixString) != -1) {
                            newValues.add(value);
                        } else if (valueText.indexOf(prefixString) != -1) {
                            newValues.add(value);
                        }
                    }
                    if (SearchAdapter2.this.maxMatch > 0 && newValues.size() > SearchAdapter2.this.maxMatch - 1) {
                        break;
                    }
                }
                List<T> list2 = SearchAdapter2.this.Set2List(newValues);
                results.values = list2;
                results.count = list2.size();
            }
            return results;
        }

        /* access modifiers changed from: protected */
        public void publishResults(CharSequence constraint, Filter.FilterResults results) {
            List unused = SearchAdapter2.this.mObjects = (List) results.values;
            if (results.count > 0) {
                SearchAdapter2.this.notifyDataSetChanged();
            } else {
                SearchAdapter2.this.notifyDataSetInvalidated();
            }
        }
    }

    public <T> Set<T> List2Set(List<T> tList) {
        return new HashSet<>(tList);
    }

    public <T> List<T> Set2List(Set<T> oSet) {
        return new ArrayList<>(oSet);
    }
}
