package cn.com.bioeasy.healty.app.healthapp.data;

import android.content.Context;
import cn.com.bioeasy.app.file.FileUtil;
import cn.com.bioeasy.healty.app.healthapp.cache.CacheKeys;
import cn.com.bioeasy.healty.app.healthapp.cache.CacheManager;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.CategoryNode;
import cn.com.bioeasy.healty.app.healthapp.modules.test.bean.TestStrip;
import cn.com.bioeasy.healty.app.healthapp.modules.test.bean.TestStripCategory;
import cn.com.bioeasy.healty.app.healthapp.modules.user.bean.UserInfo;
import com.baidu.location.Address;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DataManager {
    private Address address;
    @Inject
    CacheManager cacheManager;
    private Map<Integer, List<TestStrip>> categoryItemMap;
    private List<TestStripCategory> categoryList;
    private Context context;
    private String exportFilePath;
    private List<CategoryNode> goodsCategoryList;
    private double latitude;
    private double longitude;
    private Map<Integer, TestStripCategory> stripCategoryMap;
    private Map<Integer, TestStrip> stripMap;
    private UserInfo userInfo;
    private VersionInfo versionInfo;

    @Inject
    public DataManager(Context context2) {
        this.context = context2;
    }

    public VersionInfo getVersionInfo() {
        return this.versionInfo;
    }

    public void setVersionInfo(VersionInfo versionInfo2) {
        this.cacheManager.saveObject(CacheKeys.APP_VERSION_INFO, versionInfo2);
        this.versionInfo = versionInfo2;
    }

    public UserInfo getUserInfo() {
        return this.userInfo;
    }

    public void setUserInfo(UserInfo userInfo2) {
        if (userInfo2 == null) {
            this.cacheManager.removeCache(CacheKeys.USER_LOGIN_INFO);
        } else {
            this.cacheManager.saveObject(CacheKeys.USER_LOGIN_INFO, userInfo2);
        }
        this.userInfo = userInfo2;
    }

    public Map<Integer, List<TestStrip>> getCategoryItemMap() {
        return this.categoryItemMap;
    }

    public void setCategoryItemMap(Map<Integer, List<TestStrip>> categoryItemMap2) {
        this.categoryItemMap = categoryItemMap2;
        this.cacheManager.saveObject(CacheKeys.TEST_STRIP_CATEGORY_MAP, categoryItemMap2);
    }

    public List<TestStripCategory> getCategoryList() {
        return this.categoryList != null ? this.categoryList : new ArrayList();
    }

    public void setCategoryList(List<TestStripCategory> categoryList2) {
        this.cacheManager.saveObject(CacheKeys.TEST_STRIP_CATEGORY_LIST, categoryList2);
        this.categoryList = categoryList2;
    }

    public TestStripCategory getTestStripCategory(int categoryId) {
        if (this.stripCategoryMap == null || !this.stripCategoryMap.containsKey(Integer.valueOf(categoryId))) {
            return null;
        }
        return this.stripCategoryMap.get(Integer.valueOf(categoryId));
    }

    public Map<Integer, TestStrip> getStripMap() {
        return this.stripMap;
    }

    public void setStripMap(Map<Integer, TestStrip> stripMap2) {
        this.cacheManager.saveObject(CacheKeys.TEST_STRIP_MAP, stripMap2);
        this.stripMap = stripMap2;
    }

    public TestStrip getTestStrip(int id) {
        if (this.stripMap == null || !this.stripMap.containsKey(Integer.valueOf(id))) {
            return null;
        }
        return this.stripMap.get(Integer.valueOf(id));
    }

    public void initialize() {
        this.userInfo = (UserInfo) this.cacheManager.getObject(CacheKeys.USER_LOGIN_INFO, UserInfo.class);
        this.versionInfo = (VersionInfo) this.cacheManager.getObject(CacheKeys.APP_VERSION_INFO, VersionInfo.class);
        this.categoryItemMap = (Map) this.cacheManager.getObject(CacheKeys.TEST_STRIP_CATEGORY_MAP, HashMap.class);
        this.categoryList = (List) this.cacheManager.getObject(CacheKeys.TEST_STRIP_CATEGORY_LIST, ArrayList.class);
        this.stripCategoryMap = new HashMap();
        if (this.categoryList != null) {
            for (TestStripCategory category : this.categoryList) {
                this.stripCategoryMap.put(Integer.valueOf(category.getId()), category);
            }
        }
        this.stripMap = (Map) this.cacheManager.getObject(CacheKeys.TEST_STRIP_MAP, HashMap.class);
        this.goodsCategoryList = (List) this.cacheManager.getObject(CacheKeys.GOODS_CATEGORY_LIST, ArrayList.class);
        this.exportFilePath = FileUtil.getDataFileDir(this.context).getPath() + "/1.Pocket Reader/";
        File file = new File(this.exportFilePath);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    public String getExportFilePath() {
        return this.exportFilePath;
    }

    public void clear(String[] keyList) {
        for (String key : keyList) {
            this.cacheManager.saveObject(key, (Object) null);
        }
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address2) {
        this.address = address2;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude2) {
        this.latitude = latitude2;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longitude2) {
        this.longitude = longitude2;
    }

    public List<CategoryNode> getGoodsCategoryList() {
        return this.goodsCategoryList;
    }

    public void setGoodsCategoryList(List<CategoryNode> goodsCategoryList2) {
        this.cacheManager.saveObject(CacheKeys.GOODS_CATEGORY_LIST, goodsCategoryList2);
        this.goodsCategoryList = goodsCategoryList2;
    }
}
