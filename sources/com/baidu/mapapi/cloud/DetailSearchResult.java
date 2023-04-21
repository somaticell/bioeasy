package com.baidu.mapapi.cloud;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailSearchResult extends BaseSearchResult {
    public CloudPoiInfo poiInfo;

    public void parseFromJSON(JSONObject jSONObject) throws JSONException {
        JSONArray optJSONArray;
        JSONObject optJSONObject;
        super.parseFromJSON(jSONObject);
        if (this.status == 0 && (optJSONArray = jSONObject.optJSONArray("contents")) != null && (optJSONObject = optJSONArray.optJSONObject(0)) != null) {
            this.poiInfo = new CloudPoiInfo();
            this.poiInfo.a(optJSONObject);
        }
    }
}
