package com.baidu.platform.core.c;

import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.platform.domain.c;

public class e extends com.baidu.platform.base.e {
    e(PoiDetailSearchOption poiDetailSearchOption) {
        a(poiDetailSearchOption);
    }

    public String a(c cVar) {
        return cVar.b();
    }

    /* access modifiers changed from: package-private */
    public void a(PoiDetailSearchOption poiDetailSearchOption) {
        this.a.a("uid", poiDetailSearchOption.mUid);
        this.a.a("output", "json");
        this.a.a("scope", "2");
    }
}
