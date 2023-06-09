package com.baidu.mapapi.cloud;

import cn.com.bioeasy.healty.app.healthapp.constant.ActionConstant;
import com.alipay.android.phone.mrpc.core.Headers;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.baidu.platform.comapi.util.CoordTrans;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CloudRgcResult {
    public AddressCompents addressCompents;
    public String customLocationDescription;
    public List<CloudPoiInfo> customPois;
    public String formattedAddress;
    public LatLng location;
    public String message;
    public List<PoiInfo> pois;
    public String recommendedLocationDescription;
    public int status;

    public class AddressCompents {
        public int adminAreaCode;
        public String city;
        public String country;
        public String countryCode;
        public String district;
        public String province;
        public String street;
        public String streetNumber;

        public AddressCompents() {
        }

        /* access modifiers changed from: package-private */
        public void a(JSONObject jSONObject) throws JSONException {
            if (jSONObject != null) {
                this.country = jSONObject.optString("country");
                this.province = jSONObject.optString("province");
                this.city = jSONObject.optString("city");
                this.district = jSONObject.optString("district");
                this.street = jSONObject.optString("street");
                this.streetNumber = jSONObject.optString("street_number");
                this.adminAreaCode = jSONObject.optInt("admin_area_code");
                this.countryCode = jSONObject.optString("country_code");
            }
        }
    }

    public class PoiInfo {
        public String address;
        public String direction;
        public int distance;
        public LatLng location;
        public String name;
        public String tag;
        public String uid;

        public PoiInfo() {
        }

        public void parseFromJSON(JSONObject jSONObject) throws JSONException {
            if (jSONObject != null) {
                this.name = jSONObject.optString("name");
                this.uid = jSONObject.optString("id");
                this.address = jSONObject.optString(ActionConstant.ORDER_ADDRESS);
                this.tag = jSONObject.optString("tag");
                JSONObject optJSONObject = jSONObject.optJSONObject(Headers.LOCATION);
                if (optJSONObject != null) {
                    this.location = new LatLng(optJSONObject.optDouble("lat"), optJSONObject.optDouble("lng"));
                    if (SDKInitializer.getCoordType() == CoordType.GCJ02) {
                        this.location = CoordTrans.baiduToGcj(this.location);
                    }
                }
                this.direction = jSONObject.optString("direction");
                this.distance = jSONObject.optInt("distance");
            }
        }
    }

    public void parseFromJSON(JSONObject jSONObject) throws JSONException {
        try {
            this.status = jSONObject.optInt("status");
            this.message = jSONObject.optString("message");
            if (this.status == 6 || this.status == 7 || this.status == 8 || this.status == 9) {
                this.status = 1;
            }
            if (this.status == 0) {
                JSONObject optJSONObject = jSONObject.optJSONObject(Headers.LOCATION);
                if (optJSONObject != null) {
                    this.location = new LatLng(optJSONObject.optDouble("lat"), optJSONObject.optDouble("lng"));
                    if (SDKInitializer.getCoordType() == CoordType.GCJ02) {
                        this.location = CoordTrans.baiduToGcj(this.location);
                    }
                }
                JSONObject optJSONObject2 = jSONObject.optJSONObject("address_component");
                if (optJSONObject2 != null) {
                    this.addressCompents = new AddressCompents();
                    this.addressCompents.a(optJSONObject2);
                }
                this.formattedAddress = jSONObject.optString("formatted_address");
                JSONArray optJSONArray = jSONObject.optJSONArray("pois");
                if (optJSONArray != null) {
                    this.pois = new ArrayList();
                    for (int i = 0; i < optJSONArray.length(); i++) {
                        JSONObject optJSONObject3 = optJSONArray.optJSONObject(i);
                        if (optJSONObject3 != null) {
                            PoiInfo poiInfo = new PoiInfo();
                            poiInfo.parseFromJSON(optJSONObject3);
                            this.pois.add(poiInfo);
                        }
                    }
                }
                JSONArray optJSONArray2 = jSONObject.optJSONArray("custom_pois");
                if (optJSONArray2 != null) {
                    this.customPois = new ArrayList();
                    for (int i2 = 0; i2 < optJSONArray2.length(); i2++) {
                        JSONObject optJSONObject4 = optJSONArray2.optJSONObject(i2);
                        if (optJSONObject4 != null) {
                            CloudPoiInfo cloudPoiInfo = new CloudPoiInfo();
                            cloudPoiInfo.b(optJSONObject4);
                            this.customPois.add(cloudPoiInfo);
                        }
                    }
                }
                this.customLocationDescription = jSONObject.optString("custom_location_description");
                this.recommendedLocationDescription = jSONObject.optString("recommended_location_description");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
