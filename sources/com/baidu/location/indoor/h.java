package com.baidu.location.indoor;

import android.os.Message;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.a.a;
import com.baidu.location.d.e;

class h implements BDLocationListener {
    final /* synthetic */ g a;

    h(g gVar) {
        this.a = gVar;
    }

    public void onReceiveLocation(BDLocation bDLocation) {
        if (!(bDLocation == null || this.a.ad == null)) {
            if (bDLocation.getAddrStr() == null && this.a.ad.getAddrStr() != null) {
                bDLocation.setAddr(this.a.ad.getAddress());
                bDLocation.setAddrStr(this.a.ad.getAddrStr());
            }
            if (bDLocation.getPoiList() == null && this.a.ad.getPoiList() != null) {
                bDLocation.setPoiList(this.a.ad.getPoiList());
            }
            if (bDLocation.getLocationDescribe() == null && this.a.ad.getLocationDescribe() != null) {
                bDLocation.setLocationDescribe(this.a.ad.getLocationDescribe());
            }
        }
        if (bDLocation != null && !e.a().j()) {
            bDLocation.setUserIndoorState(1);
            bDLocation.setIndoorNetworkState(this.a.ac);
            a.a().a(bDLocation);
        } else if (bDLocation != null && e.a().j() && this.a.ae) {
            bDLocation.setUserIndoorState(1);
            bDLocation.setIndoorNetworkState(this.a.ac);
            a.a().a(bDLocation);
        }
        if (bDLocation != null && bDLocation.getNetworkLocationType().equals("ml")) {
            Message obtainMessage = this.a.c.obtainMessage(801);
            obtainMessage.obj = bDLocation;
            obtainMessage.sendToTarget();
        }
    }
}
