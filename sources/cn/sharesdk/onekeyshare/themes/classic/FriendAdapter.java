package cn.sharesdk.onekeyshare.themes.classic;

import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import com.mob.tools.gui.PullToRequestListAdapter;
import com.mob.tools.gui.PullToRequestView;
import com.mob.tools.utils.UIHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class FriendAdapter extends PullToRequestListAdapter implements PlatformActionListener {
    /* access modifiers changed from: private */
    public FriendListPage activity;
    /* access modifiers changed from: private */
    public int curPage;
    /* access modifiers changed from: private */
    public ArrayList<Following> follows;
    private boolean hasNext;
    private PRTHeader llHeader;
    private HashMap<String, Boolean> map;
    private final int pageCount = 15;
    private Platform platform;
    private float ratio;

    public static class Following {
        public String atName;
        public boolean checked;
        public String description;
        public String icon;
        public String screenName;
        public String uid;
    }

    public FriendAdapter(FriendListPage activity2, PullToRequestView view) {
        super(view);
        this.activity = activity2;
        this.curPage = -1;
        this.hasNext = true;
        this.map = new HashMap<>();
        this.follows = new ArrayList<>();
        getListView().setDivider(new ColorDrawable(-1381654));
    }

    public void setRatio(float ratio2) {
        this.ratio = ratio2;
        ListView listView = getListView();
        if (ratio2 < 1.0f) {
            ratio2 = 1.0f;
        }
        listView.setDividerHeight((int) ratio2);
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        getListView().setOnItemClickListener(listener);
    }

    public void setPlatform(Platform platform2) {
        this.platform = platform2;
        platform2.setPlatformActionListener(this);
    }

    private void next() {
        if (this.hasNext) {
            this.platform.listFriend(15, this.curPage + 1, (String) null);
        }
    }

    public void onComplete(Platform plat, int action, HashMap<String, Object> res) {
        final FollowersResult followersResult = parseFollowers(this.platform.getName(), res, this.map);
        if (followersResult == null) {
            UIHandler.sendEmptyMessage(0, new Handler.Callback() {
                public boolean handleMessage(Message msg) {
                    FriendAdapter.this.notifyDataSetChanged();
                    return false;
                }
            });
            return;
        }
        this.hasNext = followersResult.hasNextPage;
        if (followersResult.list != null && followersResult.list.size() > 0) {
            this.curPage++;
            Message msg = new Message();
            msg.what = 1;
            msg.obj = followersResult.list;
            UIHandler.sendMessage(msg, new Handler.Callback() {
                public boolean handleMessage(Message msg) {
                    if (FriendAdapter.this.curPage <= 0) {
                        FriendAdapter.this.follows.clear();
                    }
                    FriendAdapter.this.follows.addAll(followersResult.list);
                    FriendAdapter.this.notifyDataSetChanged();
                    return false;
                }
            });
        }
    }

    private FollowersResult parseFollowers(String platform2, HashMap<String, Object> res, HashMap<String, Boolean> uidMap) {
        if (res == null || res.size() <= 0) {
            return null;
        }
        boolean hasNext2 = false;
        ArrayList<Following> data = new ArrayList<>();
        if ("SinaWeibo".equals(platform2)) {
            Iterator<HashMap<String, Object>> it = ((ArrayList) res.get("users")).iterator();
            while (it.hasNext()) {
                HashMap<String, Object> user = it.next();
                String uid = String.valueOf(user.get("id"));
                if (!uidMap.containsKey(uid)) {
                    Following following = new Following();
                    following.uid = uid;
                    following.screenName = String.valueOf(user.get("name"));
                    following.description = String.valueOf(user.get("description"));
                    following.icon = String.valueOf(user.get("profile_image_url"));
                    following.atName = following.screenName;
                    uidMap.put(following.uid, 1);
                    data.add(following);
                }
            }
            hasNext2 = ((Integer) res.get("total_number")).intValue() > uidMap.size();
        } else if ("TencentWeibo".equals(platform2)) {
            hasNext2 = ((Integer) res.get("hasnext")).intValue() == 0;
            Iterator<HashMap<String, Object>> it2 = ((ArrayList) res.get("info")).iterator();
            while (it2.hasNext()) {
                HashMap<String, Object> info = it2.next();
                String uid2 = String.valueOf(info.get("name"));
                if (!uidMap.containsKey(uid2)) {
                    Following following2 = new Following();
                    following2.screenName = String.valueOf(info.get("nick"));
                    following2.uid = uid2;
                    following2.atName = uid2;
                    Iterator<HashMap<String, Object>> it3 = ((ArrayList) info.get("tweet")).iterator();
                    if (it3.hasNext()) {
                        following2.description = String.valueOf(it3.next().get("text"));
                    }
                    following2.icon = String.valueOf(info.get("head")) + "/100";
                    uidMap.put(following2.uid, 1);
                    data.add(following2);
                }
            }
        } else if ("Facebook".equals(platform2)) {
            Iterator<HashMap<String, Object>> it4 = ((ArrayList) res.get("data")).iterator();
            while (it4.hasNext()) {
                HashMap<String, Object> d = it4.next();
                String uid3 = String.valueOf(d.get("id"));
                if (!uidMap.containsKey(uid3)) {
                    Following following3 = new Following();
                    following3.uid = uid3;
                    following3.atName = "[" + uid3 + "]";
                    following3.screenName = String.valueOf(d.get("name"));
                    HashMap<String, Object> picture = (HashMap) d.get("picture");
                    if (picture != null) {
                        following3.icon = String.valueOf(((HashMap) picture.get("data")).get("url"));
                    }
                    uidMap.put(following3.uid, 1);
                    data.add(following3);
                }
            }
            hasNext2 = ((HashMap) res.get("paging")).containsKey("next");
        } else if ("Twitter".equals(platform2)) {
            Iterator<HashMap<String, Object>> it5 = ((ArrayList) res.get("users")).iterator();
            while (it5.hasNext()) {
                HashMap<String, Object> user2 = it5.next();
                String uid4 = String.valueOf(user2.get("screen_name"));
                if (!uidMap.containsKey(uid4)) {
                    Following following4 = new Following();
                    following4.uid = uid4;
                    following4.atName = uid4;
                    following4.screenName = String.valueOf(user2.get("name"));
                    following4.description = String.valueOf(user2.get("description"));
                    following4.icon = String.valueOf(user2.get("profile_image_url"));
                    uidMap.put(following4.uid, 1);
                    data.add(following4);
                }
            }
        }
        FollowersResult ret = new FollowersResult();
        ret.list = data;
        ret.hasNextPage = hasNext2;
        return ret;
    }

    public void onError(Platform plat, int action, Throwable t) {
        t.printStackTrace();
    }

    public void onCancel(Platform plat, int action) {
        UIHandler.sendEmptyMessage(0, new Handler.Callback() {
            public boolean handleMessage(Message msg) {
                FriendAdapter.this.activity.finish();
                return false;
            }
        });
    }

    public Following getItem(int position) {
        return this.follows.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public int getCount() {
        if (this.follows == null) {
            return 0;
        }
        return this.follows.size();
    }

    public View getHeaderView() {
        if (this.llHeader == null) {
            this.llHeader = new PRTHeader(getContext());
        }
        return this.llHeader;
    }

    public void onPullDown(int percent) {
        this.llHeader.onPullDown(percent);
    }

    public void onRefresh() {
        this.llHeader.onRequest();
        this.curPage = -1;
        this.hasNext = true;
        this.map.clear();
        next();
    }

    public void onReversed() {
        this.llHeader.reverse();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = new FriendListItem(parent.getContext(), this.ratio);
        }
        ((FriendListItem) convertView).update(getItem(position), isFling());
        if (position == getCount() - 1) {
            next();
        }
        return convertView;
    }

    private static class FollowersResult {
        public boolean hasNextPage;
        public ArrayList<Following> list;

        private FollowersResult() {
            this.hasNextPage = false;
        }
    }

    public View getFooterView() {
        LinearLayout footerView = new LinearLayout(getContext());
        footerView.setMinimumHeight(10);
        return footerView;
    }
}
