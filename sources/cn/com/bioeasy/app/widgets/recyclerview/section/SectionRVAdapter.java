package cn.com.bioeasy.app.widgets.recyclerview.section;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cn.com.bioeasy.app.widgets.recyclerview.base.ViewHolder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class SectionRVAdapter extends RecyclerView.Adapter<ViewHolder> {
    public static final int VIEW_TYPE_FAILED = 4;
    public static final int VIEW_TYPE_FOOTER = 1;
    public static final int VIEW_TYPE_HEADER = 0;
    public static final int VIEW_TYPE_ITEM_LOADED = 2;
    public static final int VIEW_TYPE_LOADING = 3;
    private static final int VIEW_TYPE_QTY = 5;
    private Context mContext;
    private HashMap<String, Integer> sectionViewTypeNumbers;
    private LinkedHashMap<String, Section> sections;
    private int viewTypeCount = 0;

    public SectionRVAdapter(Context context) {
        this.mContext = context;
        this.sections = new LinkedHashMap<>();
        this.sectionViewTypeNumbers = new HashMap<>();
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = null;
        for (Map.Entry<String, Integer> entry : this.sectionViewTypeNumbers.entrySet()) {
            if (viewType >= entry.getValue().intValue() && viewType < entry.getValue().intValue() + 5) {
                Section section = this.sections.get(entry.getKey());
                switch (viewType - entry.getValue().intValue()) {
                    case 0:
                        Integer resId = section.getHeaderResourceId();
                        if (resId != null) {
                            viewHolder = section.getHeaderViewHolder(this.mContext, LayoutInflater.from(parent.getContext()).inflate(resId.intValue(), parent, false));
                            break;
                        } else {
                            throw new NullPointerException("Missing 'header' resource id");
                        }
                    case 1:
                        Integer resId2 = section.getFooterResourceId();
                        if (resId2 != null) {
                            viewHolder = section.getFooterViewHolder(this.mContext, LayoutInflater.from(parent.getContext()).inflate(resId2.intValue(), parent, false));
                            break;
                        } else {
                            throw new NullPointerException("Missing 'footer' resource id");
                        }
                    case 2:
                        viewHolder = section.getItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(section.getItemResourceId(), parent, false), viewType);
                        break;
                    case 3:
                        Integer resId3 = section.getLoadingResourceId();
                        if (resId3 != null) {
                            viewHolder = section.getLoadingViewHolder(this.mContext, LayoutInflater.from(parent.getContext()).inflate(resId3.intValue(), parent, false));
                            break;
                        } else {
                            throw new NullPointerException("Missing 'loading state' resource id");
                        }
                    case 4:
                        Integer resId4 = section.getFailedResourceId();
                        if (resId4 != null) {
                            viewHolder = section.getFailedViewHolder(this.mContext, LayoutInflater.from(parent.getContext()).inflate(resId4.intValue(), parent, false));
                            break;
                        } else {
                            throw new NullPointerException("Missing 'failed state' resource id");
                        }
                    default:
                        throw new IllegalArgumentException("Invalid viewType");
                }
            }
        }
        return viewHolder;
    }

    public void addSection(String tag, Section section) {
        this.sections.put(tag, section);
        this.sectionViewTypeNumbers.put(tag, Integer.valueOf(this.viewTypeCount));
        this.viewTypeCount += 5;
    }

    public String addSection(Section section) {
        String tag = UUID.randomUUID().toString();
        addSection(tag, section);
        return tag;
    }

    public Section getSection(String tag) {
        return this.sections.get(tag);
    }

    public void removeSection(String tag) {
        this.sections.remove(tag);
    }

    public void removeAllSections() {
        this.sections.clear();
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        int currentPos = 0;
        for (Map.Entry<String, Section> entry : this.sections.entrySet()) {
            Section section = entry.getValue();
            if (section.isVisible()) {
                int sectionTotal = section.getSectionItemsTotal();
                if (position < currentPos || position > (currentPos + sectionTotal) - 1) {
                    currentPos += sectionTotal;
                } else if (section.hasHeader() && position == currentPos) {
                    getSectionForPosition(position).onBindHeaderViewHolder(holder);
                    return;
                } else if (!section.hasFooter() || position != (currentPos + sectionTotal) - 1) {
                    getSectionForPosition(position).onBindContentViewHolder(holder, getSectionPosition(position));
                    return;
                } else {
                    getSectionForPosition(position).onBindFooterViewHolder(holder);
                    return;
                }
            }
        }
        throw new IndexOutOfBoundsException("Invalid position");
    }

    public int getItemCount() {
        int count = 0;
        for (Map.Entry<String, Section> entry : this.sections.entrySet()) {
            Section section = entry.getValue();
            if (section.isVisible()) {
                count += section.getSectionItemsTotal();
            }
        }
        return count;
    }

    public int getItemViewType(int position) {
        int currentPos = 0;
        for (Map.Entry<String, Section> entry : this.sections.entrySet()) {
            Section section = entry.getValue();
            if (section.isVisible()) {
                int sectionTotal = section.getSectionItemsTotal();
                if (position < currentPos || position > (currentPos + sectionTotal) - 1) {
                    currentPos += sectionTotal;
                } else {
                    int viewType = this.sectionViewTypeNumbers.get(entry.getKey()).intValue();
                    if (section.hasHeader() && position == currentPos) {
                        return viewType;
                    }
                    if (section.hasFooter() && position == (currentPos + sectionTotal) - 1) {
                        return viewType + 1;
                    }
                    switch (section.getState()) {
                        case LOADED:
                            return viewType + 2;
                        case LOADING:
                            return viewType + 3;
                        case FAILED:
                            return viewType + 4;
                        default:
                            throw new IllegalStateException("Invalid state");
                    }
                }
            }
        }
        throw new IndexOutOfBoundsException("Invalid position");
    }

    public int getSectionItemViewType(int position) {
        return getItemViewType(position) % 5;
    }

    public Section getSectionForPosition(int position) {
        int currentPos = 0;
        for (Map.Entry<String, Section> entry : this.sections.entrySet()) {
            Section section = entry.getValue();
            if (section.isVisible()) {
                int sectionTotal = section.getSectionItemsTotal();
                if (position >= currentPos && position <= (currentPos + sectionTotal) - 1) {
                    return section;
                }
                currentPos += sectionTotal;
            }
        }
        throw new IndexOutOfBoundsException("Invalid position");
    }

    public int getSectionPosition(int position) {
        int currentPos = 0;
        for (Map.Entry<String, Section> entry : this.sections.entrySet()) {
            Section section = entry.getValue();
            if (section.isVisible()) {
                int sectionTotal = section.getSectionItemsTotal();
                if (position < currentPos || position > (currentPos + sectionTotal) - 1) {
                    currentPos += sectionTotal;
                } else {
                    return (position - currentPos) - (section.hasHeader() ? 1 : 0);
                }
            }
        }
        throw new IndexOutOfBoundsException("Invalid position");
    }

    public LinkedHashMap<String, Section> getSectionsMap() {
        return this.sections;
    }

    public static class EmptyViewHolder extends ViewHolder {
        public EmptyViewHolder(Context context, View itemView) {
            super(context, itemView);
        }
    }
}
