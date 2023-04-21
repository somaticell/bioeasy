package com.baidu.location;

import android.os.Parcel;
import android.os.Parcelable;
import com.baidu.location.Address;
import com.baidu.location.f.j;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class BDLocation implements Parcelable {
    public static final String BDLOCATION_BD09LL_TO_GCJ02 = "bd09ll2gcj";
    public static final String BDLOCATION_BD09_TO_GCJ02 = "bd092gcj";
    public static final String BDLOCATION_GCJ02_TO_BD09 = "bd09";
    public static final String BDLOCATION_GCJ02_TO_BD09LL = "bd09ll";
    public static final String BDLOCATION_WGS84_TO_GCJ02 = "gps2gcj";
    public static final Parcelable.Creator<BDLocation> CREATOR = new a();
    public static final int GPS_ACCURACY_BAD = 3;
    public static final int GPS_ACCURACY_GOOD = 1;
    public static final int GPS_ACCURACY_MID = 2;
    public static final int GPS_ACCURACY_UNKNOWN = 0;
    public static final int GPS_RECTIFY_INDOOR = 1;
    public static final int GPS_RECTIFY_NONE = 0;
    public static final int GPS_RECTIFY_OUTDOOR = 2;
    public static final int INDOOR_LOCATION_NEARBY_SURPPORT_TRUE = 2;
    public static final int INDOOR_LOCATION_SOURCE_BLUETOOTH = 4;
    public static final int INDOOR_LOCATION_SOURCE_MAGNETIC = 2;
    public static final int INDOOR_LOCATION_SOURCE_SMALLCELLSTATION = 8;
    public static final int INDOOR_LOCATION_SOURCE_UNKNOWN = 0;
    public static final int INDOOR_LOCATION_SOURCE_WIFI = 1;
    public static final int INDOOR_LOCATION_SURPPORT_FALSE = 0;
    public static final int INDOOR_LOCATION_SURPPORT_TRUE = 1;
    public static final int INDOOR_NETWORK_STATE_HIGH = 2;
    public static final int INDOOR_NETWORK_STATE_LOW = 0;
    public static final int INDOOR_NETWORK_STATE_MIDDLE = 1;
    public static final int LOCATION_WHERE_IN_CN = 1;
    public static final int LOCATION_WHERE_OUT_CN = 0;
    public static final int LOCATION_WHERE_UNKNOW = 2;
    public static final int OPERATORS_TYPE_MOBILE = 1;
    public static final int OPERATORS_TYPE_TELECOMU = 3;
    public static final int OPERATORS_TYPE_UNICOM = 2;
    public static final int OPERATORS_TYPE_UNKONW = 0;
    public static final int TypeCacheLocation = 65;
    public static final int TypeCriteriaException = 62;
    public static final int TypeGpsLocation = 61;
    public static final int TypeNetWorkException = 63;
    public static final int TypeNetWorkLocation = 161;
    public static final int TypeNone = 0;
    public static final int TypeOffLineLocation = 66;
    public static final int TypeOffLineLocationFail = 67;
    public static final int TypeOffLineLocationNetworkFail = 68;
    public static final int TypeServerCheckKeyError = 505;
    public static final int TypeServerDecryptError = 162;
    public static final int TypeServerError = 167;
    public static final int USER_INDDOR_TRUE = 1;
    public static final int USER_INDOOR_FALSE = 0;
    public static final int USER_INDOOR_UNKNOW = -1;
    private int A;
    private String B;
    private int C;
    private String D;
    private int E;
    private int F;
    private int G;
    private int H;
    private String I;
    private String J;
    private String K;
    private List<Poi> L;
    private String M;
    private String N;
    private HashMap<String, String> O;
    private int P;
    private int Q;
    private int a;
    private String b;
    private double c;
    private double d;
    private boolean e;
    private double f;
    private boolean g;
    private float h;
    private boolean i;
    private float j;
    private boolean k;
    private int l;
    private float m;
    private String n;
    private boolean o;
    private String p;
    private String q;
    private String r;
    private String s;
    private boolean t;
    private Address u;
    private String v;
    private String w;
    private String x;
    private boolean y;
    private int z;

    public BDLocation() {
        this.a = 0;
        this.b = null;
        this.c = Double.MIN_VALUE;
        this.d = Double.MIN_VALUE;
        this.e = false;
        this.f = Double.MIN_VALUE;
        this.g = false;
        this.h = 0.0f;
        this.i = false;
        this.j = 0.0f;
        this.k = false;
        this.l = -1;
        this.m = -1.0f;
        this.n = null;
        this.o = false;
        this.p = null;
        this.q = null;
        this.r = null;
        this.s = null;
        this.t = false;
        this.u = new Address.Builder().build();
        this.v = null;
        this.w = null;
        this.x = null;
        this.y = false;
        this.z = 0;
        this.A = 1;
        this.B = null;
        this.D = "";
        this.E = -1;
        this.F = 0;
        this.G = 2;
        this.H = 0;
        this.I = null;
        this.J = null;
        this.K = null;
        this.L = null;
        this.M = null;
        this.N = null;
        this.O = new HashMap<>();
        this.P = 0;
        this.Q = 0;
    }

    private BDLocation(Parcel parcel) {
        this.a = 0;
        this.b = null;
        this.c = Double.MIN_VALUE;
        this.d = Double.MIN_VALUE;
        this.e = false;
        this.f = Double.MIN_VALUE;
        this.g = false;
        this.h = 0.0f;
        this.i = false;
        this.j = 0.0f;
        this.k = false;
        this.l = -1;
        this.m = -1.0f;
        this.n = null;
        this.o = false;
        this.p = null;
        this.q = null;
        this.r = null;
        this.s = null;
        this.t = false;
        this.u = new Address.Builder().build();
        this.v = null;
        this.w = null;
        this.x = null;
        this.y = false;
        this.z = 0;
        this.A = 1;
        this.B = null;
        this.D = "";
        this.E = -1;
        this.F = 0;
        this.G = 2;
        this.H = 0;
        this.I = null;
        this.J = null;
        this.K = null;
        this.L = null;
        this.M = null;
        this.N = null;
        this.O = new HashMap<>();
        this.P = 0;
        this.Q = 0;
        this.a = parcel.readInt();
        this.b = parcel.readString();
        this.c = parcel.readDouble();
        this.d = parcel.readDouble();
        this.f = parcel.readDouble();
        this.h = parcel.readFloat();
        this.j = parcel.readFloat();
        this.l = parcel.readInt();
        this.m = parcel.readFloat();
        this.v = parcel.readString();
        this.z = parcel.readInt();
        this.w = parcel.readString();
        this.x = parcel.readString();
        this.B = parcel.readString();
        String readString = parcel.readString();
        String readString2 = parcel.readString();
        String readString3 = parcel.readString();
        String readString4 = parcel.readString();
        String readString5 = parcel.readString();
        String readString6 = parcel.readString();
        parcel.readString();
        String readString7 = parcel.readString();
        String readString8 = parcel.readString();
        this.u = new Address.Builder().country(readString7).countryCode(readString8).province(readString).city(readString2).cityCode(readString6).district(readString3).street(readString4).streetNumber(readString5).adcode(parcel.readString()).build();
        boolean[] zArr = new boolean[7];
        this.C = parcel.readInt();
        this.D = parcel.readString();
        this.q = parcel.readString();
        this.r = parcel.readString();
        this.s = parcel.readString();
        this.A = parcel.readInt();
        this.M = parcel.readString();
        this.E = parcel.readInt();
        this.F = parcel.readInt();
        this.G = parcel.readInt();
        this.H = parcel.readInt();
        this.I = parcel.readString();
        this.J = parcel.readString();
        this.K = parcel.readString();
        this.P = parcel.readInt();
        this.N = parcel.readString();
        this.Q = parcel.readInt();
        try {
            parcel.readBooleanArray(zArr);
            this.e = zArr[0];
            this.g = zArr[1];
            this.i = zArr[2];
            this.k = zArr[3];
            this.o = zArr[4];
            this.t = zArr[5];
            this.y = zArr[6];
        } catch (Exception e2) {
        }
        ArrayList arrayList = new ArrayList();
        parcel.readList(arrayList, Poi.class.getClassLoader());
        if (arrayList.size() == 0) {
            this.L = null;
        } else {
            this.L = arrayList;
        }
    }

    /* synthetic */ BDLocation(Parcel parcel, a aVar) {
        this(parcel);
    }

    public BDLocation(BDLocation bDLocation) {
        int i2 = 0;
        this.a = 0;
        this.b = null;
        this.c = Double.MIN_VALUE;
        this.d = Double.MIN_VALUE;
        this.e = false;
        this.f = Double.MIN_VALUE;
        this.g = false;
        this.h = 0.0f;
        this.i = false;
        this.j = 0.0f;
        this.k = false;
        this.l = -1;
        this.m = -1.0f;
        this.n = null;
        this.o = false;
        this.p = null;
        this.q = null;
        this.r = null;
        this.s = null;
        this.t = false;
        this.u = new Address.Builder().build();
        this.v = null;
        this.w = null;
        this.x = null;
        this.y = false;
        this.z = 0;
        this.A = 1;
        this.B = null;
        this.D = "";
        this.E = -1;
        this.F = 0;
        this.G = 2;
        this.H = 0;
        this.I = null;
        this.J = null;
        this.K = null;
        this.L = null;
        this.M = null;
        this.N = null;
        this.O = new HashMap<>();
        this.P = 0;
        this.Q = 0;
        this.a = bDLocation.a;
        this.b = bDLocation.b;
        this.c = bDLocation.c;
        this.d = bDLocation.d;
        this.e = bDLocation.e;
        this.f = bDLocation.f;
        this.g = bDLocation.g;
        this.h = bDLocation.h;
        this.i = bDLocation.i;
        this.j = bDLocation.j;
        this.k = bDLocation.k;
        this.l = bDLocation.l;
        this.m = bDLocation.m;
        this.n = bDLocation.n;
        this.o = bDLocation.o;
        this.p = bDLocation.p;
        this.t = bDLocation.t;
        this.u = new Address.Builder().country(bDLocation.u.country).countryCode(bDLocation.u.countryCode).province(bDLocation.u.province).city(bDLocation.u.city).cityCode(bDLocation.u.cityCode).district(bDLocation.u.district).street(bDLocation.u.street).streetNumber(bDLocation.u.streetNumber).adcode(bDLocation.u.adcode).build();
        this.v = bDLocation.v;
        this.w = bDLocation.w;
        this.x = bDLocation.x;
        this.A = bDLocation.A;
        this.z = bDLocation.z;
        this.y = bDLocation.y;
        this.B = bDLocation.B;
        this.C = bDLocation.C;
        this.D = bDLocation.D;
        this.q = bDLocation.q;
        this.r = bDLocation.r;
        this.s = bDLocation.s;
        this.E = bDLocation.E;
        this.F = bDLocation.F;
        this.G = bDLocation.F;
        this.H = bDLocation.H;
        this.I = bDLocation.I;
        this.J = bDLocation.J;
        this.K = bDLocation.K;
        this.P = bDLocation.P;
        this.N = bDLocation.N;
        if (bDLocation.L == null) {
            this.L = null;
        } else {
            ArrayList arrayList = new ArrayList();
            while (true) {
                int i3 = i2;
                if (i3 >= bDLocation.L.size()) {
                    break;
                }
                Poi poi = bDLocation.L.get(i3);
                arrayList.add(new Poi(poi.getId(), poi.getName(), poi.getRank()));
                i2 = i3 + 1;
            }
            this.L = arrayList;
        }
        this.M = bDLocation.M;
        this.O = bDLocation.O;
        this.Q = bDLocation.Q;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:159:0x0471, code lost:
        r1 = false;
        r10 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:190:0x04c1, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:192:?, code lost:
        r1.printStackTrace();
        r1 = null;
        r0 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:203:0x04f6, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:205:?, code lost:
        r0.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:218:0x052c, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0150, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:220:?, code lost:
        r0.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0151, code lost:
        r0.printStackTrace();
        r14.a = 0;
        r14.o = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:245:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:246:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x016a, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x016b, code lost:
        r0.printStackTrace();
        r14.a = 0;
        r14.o = false;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x016a A[ExcHandler: Error (r0v45 'e' java.lang.Error A[CUSTOM_DECLARE]), Splitter:B:4:0x009d] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:214:0x0520=Splitter:B:214:0x0520, B:17:0x0144=Splitter:B:17:0x0144} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public BDLocation(java.lang.String r15) {
        /*
            r14 = this;
            r14.<init>()
            r0 = 0
            r14.a = r0
            r0 = 0
            r14.b = r0
            r0 = 1
            r14.c = r0
            r0 = 1
            r14.d = r0
            r0 = 0
            r14.e = r0
            r0 = 1
            r14.f = r0
            r0 = 0
            r14.g = r0
            r0 = 0
            r14.h = r0
            r0 = 0
            r14.i = r0
            r0 = 0
            r14.j = r0
            r0 = 0
            r14.k = r0
            r0 = -1
            r14.l = r0
            r0 = -1082130432(0xffffffffbf800000, float:-1.0)
            r14.m = r0
            r0 = 0
            r14.n = r0
            r0 = 0
            r14.o = r0
            r0 = 0
            r14.p = r0
            r0 = 0
            r14.q = r0
            r0 = 0
            r14.r = r0
            r0 = 0
            r14.s = r0
            r0 = 0
            r14.t = r0
            com.baidu.location.Address$Builder r0 = new com.baidu.location.Address$Builder
            r0.<init>()
            com.baidu.location.Address r0 = r0.build()
            r14.u = r0
            r0 = 0
            r14.v = r0
            r0 = 0
            r14.w = r0
            r0 = 0
            r14.x = r0
            r0 = 0
            r14.y = r0
            r0 = 0
            r14.z = r0
            r0 = 1
            r14.A = r0
            r0 = 0
            r14.B = r0
            java.lang.String r0 = ""
            r14.D = r0
            r0 = -1
            r14.E = r0
            r0 = 0
            r14.F = r0
            r0 = 2
            r14.G = r0
            r0 = 0
            r14.H = r0
            r0 = 0
            r14.I = r0
            r0 = 0
            r14.J = r0
            r0 = 0
            r14.K = r0
            r0 = 0
            r14.L = r0
            r0 = 0
            r14.M = r0
            r0 = 0
            r14.N = r0
            java.util.HashMap r0 = new java.util.HashMap
            r0.<init>()
            r14.O = r0
            r0 = 0
            r14.P = r0
            r0 = 0
            r14.Q = r0
            if (r15 == 0) goto L_0x009c
            java.lang.String r0 = ""
            boolean r0 = r15.equals(r0)
            if (r0 == 0) goto L_0x009d
        L_0x009c:
            return
        L_0x009d:
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            r0.<init>(r15)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            java.lang.String r1 = "result"
            org.json.JSONObject r1 = r0.getJSONObject(r1)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            java.lang.String r2 = "error"
            java.lang.String r2 = r1.getString(r2)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            int r2 = java.lang.Integer.parseInt(r2)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            r14.setLocType(r2)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            java.lang.String r3 = "time"
            java.lang.String r1 = r1.getString(r3)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            r14.setTime(r1)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            r1 = 61
            if (r2 != r1) goto L_0x0176
            java.lang.String r1 = "content"
            org.json.JSONObject r0 = r0.getJSONObject(r1)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            java.lang.String r1 = "point"
            org.json.JSONObject r1 = r0.getJSONObject(r1)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            java.lang.String r2 = "y"
            java.lang.String r2 = r1.getString(r2)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            double r2 = java.lang.Double.parseDouble(r2)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            r14.setLatitude(r2)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            java.lang.String r2 = "x"
            java.lang.String r1 = r1.getString(r2)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            double r2 = java.lang.Double.parseDouble(r1)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            r14.setLongitude(r2)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            java.lang.String r1 = "radius"
            java.lang.String r1 = r0.getString(r1)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            float r1 = java.lang.Float.parseFloat(r1)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            r14.setRadius(r1)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            java.lang.String r1 = "s"
            java.lang.String r1 = r0.getString(r1)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            float r1 = java.lang.Float.parseFloat(r1)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            r14.setSpeed(r1)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            java.lang.String r1 = "d"
            java.lang.String r1 = r0.getString(r1)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            float r1 = java.lang.Float.parseFloat(r1)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            r14.setDirection(r1)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            java.lang.String r1 = "n"
            java.lang.String r1 = r0.getString(r1)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            int r1 = java.lang.Integer.parseInt(r1)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            r14.setSatelliteNumber(r1)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            java.lang.String r1 = "h"
            boolean r1 = r0.has(r1)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            if (r1 == 0) goto L_0x012f
            java.lang.String r1 = "h"
            double r2 = r0.getDouble(r1)     // Catch:{ Exception -> 0x05a3, Error -> 0x016a }
            r14.setAltitude(r2)     // Catch:{ Exception -> 0x05a3, Error -> 0x016a }
        L_0x012f:
            java.lang.String r1 = "in_cn"
            boolean r1 = r0.has(r1)     // Catch:{ Exception -> 0x0161, Error -> 0x016a }
            if (r1 == 0) goto L_0x015c
            java.lang.String r1 = "in_cn"
            java.lang.String r0 = r0.getString(r1)     // Catch:{ Exception -> 0x0161, Error -> 0x016a }
            int r0 = java.lang.Integer.parseInt(r0)     // Catch:{ Exception -> 0x0161, Error -> 0x016a }
            r14.setLocationWhere(r0)     // Catch:{ Exception -> 0x0161, Error -> 0x016a }
        L_0x0144:
            int r0 = r14.A     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            if (r0 != 0) goto L_0x0163
            java.lang.String r0 = "wgs84"
            r14.setCoorType(r0)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            goto L_0x009c
        L_0x0150:
            r0 = move-exception
            r0.printStackTrace()
            r0 = 0
            r14.a = r0
            r0 = 0
            r14.o = r0
            goto L_0x009c
        L_0x015c:
            r0 = 1
            r14.setLocationWhere(r0)     // Catch:{ Exception -> 0x0161, Error -> 0x016a }
            goto L_0x0144
        L_0x0161:
            r0 = move-exception
            goto L_0x0144
        L_0x0163:
            java.lang.String r0 = "gcj02"
            r14.setCoorType(r0)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            goto L_0x009c
        L_0x016a:
            r0 = move-exception
            r0.printStackTrace()
            r0 = 0
            r14.a = r0
            r0 = 0
            r14.o = r0
            goto L_0x009c
        L_0x0176:
            r1 = 161(0xa1, float:2.26E-43)
            if (r2 != r1) goto L_0x0544
            java.lang.String r1 = "content"
            org.json.JSONObject r11 = r0.getJSONObject(r1)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            java.lang.String r0 = "point"
            org.json.JSONObject r0 = r11.getJSONObject(r0)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            java.lang.String r1 = "y"
            java.lang.String r1 = r0.getString(r1)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            double r2 = java.lang.Double.parseDouble(r1)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            r14.setLatitude(r2)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            java.lang.String r1 = "x"
            java.lang.String r0 = r0.getString(r1)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            double r0 = java.lang.Double.parseDouble(r0)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            r14.setLongitude(r0)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            java.lang.String r0 = "radius"
            java.lang.String r0 = r11.getString(r0)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            float r0 = java.lang.Float.parseFloat(r0)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            r14.setRadius(r0)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            java.lang.String r0 = "sema"
            boolean r0 = r11.has(r0)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            if (r0 == 0) goto L_0x0247
            java.lang.String r0 = "sema"
            org.json.JSONObject r1 = r11.getJSONObject(r0)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            java.lang.String r0 = "aptag"
            boolean r0 = r1.has(r0)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            if (r0 == 0) goto L_0x01d3
            java.lang.String r0 = "aptag"
            java.lang.String r0 = r1.getString(r0)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            boolean r2 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            if (r2 != 0) goto L_0x0214
            r14.q = r0     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
        L_0x01d3:
            java.lang.String r0 = "aptagd"
            boolean r0 = r1.has(r0)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            if (r0 == 0) goto L_0x021b
            java.lang.String r0 = "aptagd"
            org.json.JSONObject r0 = r1.getJSONObject(r0)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            java.lang.String r2 = "pois"
            org.json.JSONArray r2 = r0.getJSONArray(r2)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            java.util.ArrayList r3 = new java.util.ArrayList     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            r3.<init>()     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            r0 = 0
        L_0x01ed:
            int r4 = r2.length()     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            if (r0 >= r4) goto L_0x0219
            org.json.JSONObject r4 = r2.getJSONObject(r0)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            java.lang.String r5 = "pname"
            java.lang.String r5 = r4.getString(r5)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            java.lang.String r6 = "pid"
            java.lang.String r6 = r4.getString(r6)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            java.lang.String r7 = "pr"
            double r8 = r4.getDouble(r7)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            com.baidu.location.Poi r4 = new com.baidu.location.Poi     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            r4.<init>(r6, r5, r8)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            r3.add(r4)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            int r0 = r0 + 1
            goto L_0x01ed
        L_0x0214:
            java.lang.String r0 = ""
            r14.q = r0     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            goto L_0x01d3
        L_0x0219:
            r14.L = r3     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
        L_0x021b:
            java.lang.String r0 = "poiregion"
            boolean r0 = r1.has(r0)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            if (r0 == 0) goto L_0x0231
            java.lang.String r0 = "poiregion"
            java.lang.String r0 = r1.getString(r0)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            boolean r2 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            if (r2 != 0) goto L_0x0231
            r14.r = r0     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
        L_0x0231:
            java.lang.String r0 = "regular"
            boolean r0 = r1.has(r0)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            if (r0 == 0) goto L_0x0247
            java.lang.String r0 = "regular"
            java.lang.String r0 = r1.getString(r0)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            boolean r1 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            if (r1 != 0) goto L_0x0247
            r14.s = r0     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
        L_0x0247:
            java.lang.String r0 = "addr"
            boolean r0 = r11.has(r0)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            if (r0 == 0) goto L_0x04cb
            r9 = 0
            r8 = 0
            r7 = 0
            r6 = 0
            r5 = 0
            r4 = 0
            r3 = 0
            r2 = 0
            r0 = 0
            r10 = 0
            java.lang.String r1 = "addr"
            org.json.JSONObject r1 = r11.getJSONObject(r1)     // Catch:{ Exception -> 0x0470, Error -> 0x016a }
            r10 = 1
            r13 = r1
            r1 = r10
            r10 = r13
        L_0x0263:
            if (r10 == 0) goto L_0x0477
            java.lang.String r9 = ""
            java.lang.String r8 = ""
            java.lang.String r7 = ""
            java.lang.String r6 = ""
            java.lang.String r5 = ""
            java.lang.String r4 = ""
            java.lang.String r3 = ""
            java.lang.String r2 = ""
            java.lang.String r0 = ""
            java.lang.String r12 = "city"
            boolean r12 = r10.has(r12)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            if (r12 == 0) goto L_0x0285
            java.lang.String r6 = "city"
            java.lang.String r6 = r10.getString(r6)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
        L_0x0285:
            java.lang.String r12 = "city_code"
            boolean r12 = r10.has(r12)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            if (r12 == 0) goto L_0x0293
            java.lang.String r5 = "city_code"
            java.lang.String r5 = r10.getString(r5)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
        L_0x0293:
            java.lang.String r12 = "country"
            boolean r12 = r10.has(r12)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            if (r12 == 0) goto L_0x02a1
            java.lang.String r9 = "country"
            java.lang.String r9 = r10.getString(r9)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
        L_0x02a1:
            java.lang.String r12 = "country_code"
            boolean r12 = r10.has(r12)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            if (r12 == 0) goto L_0x02af
            java.lang.String r8 = "country_code"
            java.lang.String r8 = r10.getString(r8)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
        L_0x02af:
            java.lang.String r12 = "province"
            boolean r12 = r10.has(r12)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            if (r12 == 0) goto L_0x02bd
            java.lang.String r7 = "province"
            java.lang.String r7 = r10.getString(r7)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
        L_0x02bd:
            java.lang.String r12 = "district"
            boolean r12 = r10.has(r12)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            if (r12 == 0) goto L_0x02cb
            java.lang.String r4 = "district"
            java.lang.String r4 = r10.getString(r4)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
        L_0x02cb:
            java.lang.String r12 = "street"
            boolean r12 = r10.has(r12)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            if (r12 == 0) goto L_0x02d9
            java.lang.String r3 = "street"
            java.lang.String r3 = r10.getString(r3)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
        L_0x02d9:
            java.lang.String r12 = "street_number"
            boolean r12 = r10.has(r12)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            if (r12 == 0) goto L_0x02e7
            java.lang.String r2 = "street_number"
            java.lang.String r2 = r10.getString(r2)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
        L_0x02e7:
            java.lang.String r12 = "adcode"
            boolean r12 = r10.has(r12)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            if (r12 == 0) goto L_0x05a6
            java.lang.String r0 = "adcode"
            java.lang.String r0 = r10.getString(r0)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            r13 = r1
            r1 = r0
            r0 = r13
        L_0x02f8:
            if (r0 == 0) goto L_0x032c
            com.baidu.location.Address$Builder r0 = new com.baidu.location.Address$Builder     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            r0.<init>()     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            com.baidu.location.Address$Builder r0 = r0.country(r9)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            com.baidu.location.Address$Builder r0 = r0.countryCode(r8)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            com.baidu.location.Address$Builder r0 = r0.province(r7)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            com.baidu.location.Address$Builder r0 = r0.city(r6)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            com.baidu.location.Address$Builder r0 = r0.cityCode(r5)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            com.baidu.location.Address$Builder r0 = r0.district(r4)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            com.baidu.location.Address$Builder r0 = r0.street(r3)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            com.baidu.location.Address$Builder r0 = r0.streetNumber(r2)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            com.baidu.location.Address$Builder r0 = r0.adcode(r1)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            com.baidu.location.Address r0 = r0.build()     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            r14.u = r0     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            r0 = 1
            r14.o = r0     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
        L_0x032c:
            java.lang.String r0 = "floor"
            boolean r0 = r11.has(r0)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            if (r0 == 0) goto L_0x0347
            java.lang.String r0 = "floor"
            java.lang.String r0 = r11.getString(r0)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            r14.v = r0     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            java.lang.String r0 = r14.v     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            if (r0 == 0) goto L_0x0347
            r0 = 0
            r14.v = r0     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
        L_0x0347:
            java.lang.String r0 = "indoor"
            boolean r0 = r11.has(r0)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            if (r0 == 0) goto L_0x0366
            java.lang.String r0 = "indoor"
            java.lang.String r0 = r11.getString(r0)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            boolean r1 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            if (r1 != 0) goto L_0x0366
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            int r0 = r0.intValue()     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            r14.setUserIndoorState(r0)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
        L_0x0366:
            java.lang.String r0 = "loctp"
            boolean r0 = r11.has(r0)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            if (r0 == 0) goto L_0x0381
            java.lang.String r0 = "loctp"
            java.lang.String r0 = r11.getString(r0)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            r14.B = r0     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            java.lang.String r0 = r14.B     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            if (r0 == 0) goto L_0x0381
            r0 = 0
            r14.B = r0     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
        L_0x0381:
            java.lang.String r0 = "bldgid"
            boolean r0 = r11.has(r0)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            if (r0 == 0) goto L_0x039c
            java.lang.String r0 = "bldgid"
            java.lang.String r0 = r11.getString(r0)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            r14.w = r0     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            java.lang.String r0 = r14.w     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            if (r0 == 0) goto L_0x039c
            r0 = 0
            r14.w = r0     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
        L_0x039c:
            java.lang.String r0 = "bldg"
            boolean r0 = r11.has(r0)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            if (r0 == 0) goto L_0x03b7
            java.lang.String r0 = "bldg"
            java.lang.String r0 = r11.getString(r0)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            r14.x = r0     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            java.lang.String r0 = r14.x     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            if (r0 == 0) goto L_0x03b7
            r0 = 0
            r14.x = r0     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
        L_0x03b7:
            java.lang.String r0 = "ibav"
            boolean r0 = r11.has(r0)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            if (r0 == 0) goto L_0x03ce
            java.lang.String r0 = "ibav"
            java.lang.String r0 = r11.getString(r0)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            boolean r1 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            if (r1 == 0) goto L_0x04d4
            r0 = 0
            r14.z = r0     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
        L_0x03ce:
            java.lang.String r0 = "indoorflags"
            boolean r0 = r11.has(r0)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            if (r0 == 0) goto L_0x04fa
            java.lang.String r0 = "indoorflags"
            org.json.JSONObject r0 = r11.getJSONObject(r0)     // Catch:{ Exception -> 0x04f6, Error -> 0x016a }
            java.lang.String r1 = "area"
            boolean r1 = r0.has(r1)     // Catch:{ Exception -> 0x04f6, Error -> 0x016a }
            if (r1 == 0) goto L_0x03f8
            java.lang.String r1 = "area"
            java.lang.String r1 = r0.getString(r1)     // Catch:{ Exception -> 0x04f6, Error -> 0x016a }
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch:{ Exception -> 0x04f6, Error -> 0x016a }
            int r1 = r1.intValue()     // Catch:{ Exception -> 0x04f6, Error -> 0x016a }
            if (r1 != 0) goto L_0x04ed
            r1 = 2
            r14.setIndoorLocationSurpport(r1)     // Catch:{ Exception -> 0x04f6, Error -> 0x016a }
        L_0x03f8:
            java.lang.String r1 = "support"
            boolean r1 = r0.has(r1)     // Catch:{ Exception -> 0x04f6, Error -> 0x016a }
            if (r1 == 0) goto L_0x0411
            java.lang.String r1 = "support"
            java.lang.String r1 = r0.getString(r1)     // Catch:{ Exception -> 0x04f6, Error -> 0x016a }
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch:{ Exception -> 0x04f6, Error -> 0x016a }
            int r1 = r1.intValue()     // Catch:{ Exception -> 0x04f6, Error -> 0x016a }
            r14.setIndoorLocationSource(r1)     // Catch:{ Exception -> 0x04f6, Error -> 0x016a }
        L_0x0411:
            java.lang.String r1 = "inbldg"
            boolean r1 = r0.has(r1)     // Catch:{ Exception -> 0x04f6, Error -> 0x016a }
            if (r1 == 0) goto L_0x0421
            java.lang.String r1 = "inbldg"
            java.lang.String r1 = r0.getString(r1)     // Catch:{ Exception -> 0x04f6, Error -> 0x016a }
            r14.I = r1     // Catch:{ Exception -> 0x04f6, Error -> 0x016a }
        L_0x0421:
            java.lang.String r1 = "inbldgid"
            boolean r1 = r0.has(r1)     // Catch:{ Exception -> 0x04f6, Error -> 0x016a }
            if (r1 == 0) goto L_0x0431
            java.lang.String r1 = "inbldgid"
            java.lang.String r1 = r0.getString(r1)     // Catch:{ Exception -> 0x04f6, Error -> 0x016a }
            r14.J = r1     // Catch:{ Exception -> 0x04f6, Error -> 0x016a }
        L_0x0431:
            java.lang.String r1 = "polygon"
            boolean r1 = r0.has(r1)     // Catch:{ Exception -> 0x04f6, Error -> 0x016a }
            if (r1 == 0) goto L_0x0442
            java.lang.String r1 = "polygon"
            java.lang.String r1 = r0.getString(r1)     // Catch:{ Exception -> 0x04f6, Error -> 0x016a }
            r14.setIndoorSurpportPolygon(r1)     // Catch:{ Exception -> 0x04f6, Error -> 0x016a }
        L_0x0442:
            java.lang.String r1 = "ret_fields"
            boolean r1 = r0.has(r1)     // Catch:{ Exception -> 0x04f6, Error -> 0x016a }
            if (r1 == 0) goto L_0x04fa
            java.lang.String r1 = "ret_fields"
            java.lang.String r0 = r0.getString(r1)     // Catch:{ Exception -> 0x052c, Error -> 0x016a }
            java.lang.String r1 = "\\|"
            java.lang.String[] r1 = r0.split(r1)     // Catch:{ Exception -> 0x052c, Error -> 0x016a }
            int r2 = r1.length     // Catch:{ Exception -> 0x052c, Error -> 0x016a }
            r0 = 0
        L_0x0458:
            if (r0 >= r2) goto L_0x04fa
            r3 = r1[r0]     // Catch:{ Exception -> 0x052c, Error -> 0x016a }
            java.lang.String r4 = "="
            java.lang.String[] r3 = r3.split(r4)     // Catch:{ Exception -> 0x052c, Error -> 0x016a }
            r4 = 0
            r4 = r3[r4]     // Catch:{ Exception -> 0x052c, Error -> 0x016a }
            r5 = 1
            r3 = r3[r5]     // Catch:{ Exception -> 0x052c, Error -> 0x016a }
            java.util.HashMap<java.lang.String, java.lang.String> r5 = r14.O     // Catch:{ Exception -> 0x052c, Error -> 0x016a }
            r5.put(r4, r3)     // Catch:{ Exception -> 0x052c, Error -> 0x016a }
            int r0 = r0 + 1
            goto L_0x0458
        L_0x0470:
            r1 = move-exception
            r1 = 0
            r13 = r1
            r1 = r10
            r10 = r13
            goto L_0x0263
        L_0x0477:
            java.lang.String r1 = "addr"
            java.lang.String r1 = r11.getString(r1)     // Catch:{ Exception -> 0x04c1, Error -> 0x016a }
            java.lang.String r10 = ","
            java.lang.String[] r1 = r1.split(r10)     // Catch:{ Exception -> 0x04c1, Error -> 0x016a }
            int r10 = r1.length     // Catch:{ Exception -> 0x04c1, Error -> 0x016a }
            if (r10 <= 0) goto L_0x0489
            r12 = 0
            r7 = r1[r12]     // Catch:{ Exception -> 0x04c1, Error -> 0x016a }
        L_0x0489:
            r12 = 1
            if (r10 <= r12) goto L_0x048f
            r12 = 1
            r6 = r1[r12]     // Catch:{ Exception -> 0x04c1, Error -> 0x016a }
        L_0x048f:
            r12 = 2
            if (r10 <= r12) goto L_0x0495
            r12 = 2
            r4 = r1[r12]     // Catch:{ Exception -> 0x04c1, Error -> 0x016a }
        L_0x0495:
            r12 = 3
            if (r10 <= r12) goto L_0x049b
            r12 = 3
            r3 = r1[r12]     // Catch:{ Exception -> 0x04c1, Error -> 0x016a }
        L_0x049b:
            r12 = 4
            if (r10 <= r12) goto L_0x04a1
            r12 = 4
            r2 = r1[r12]     // Catch:{ Exception -> 0x04c1, Error -> 0x016a }
        L_0x04a1:
            r12 = 5
            if (r10 <= r12) goto L_0x04a7
            r12 = 5
            r5 = r1[r12]     // Catch:{ Exception -> 0x04c1, Error -> 0x016a }
        L_0x04a7:
            r12 = 6
            if (r10 <= r12) goto L_0x04ad
            r12 = 6
            r9 = r1[r12]     // Catch:{ Exception -> 0x04c1, Error -> 0x016a }
        L_0x04ad:
            r12 = 7
            if (r10 <= r12) goto L_0x04b3
            r12 = 7
            r8 = r1[r12]     // Catch:{ Exception -> 0x04c1, Error -> 0x016a }
        L_0x04b3:
            r12 = 8
            if (r10 <= r12) goto L_0x04bb
            r10 = 8
            r0 = r1[r10]     // Catch:{ Exception -> 0x04c1, Error -> 0x016a }
        L_0x04bb:
            r1 = 1
            r13 = r1
            r1 = r0
            r0 = r13
            goto L_0x02f8
        L_0x04c1:
            r1 = move-exception
            r1.printStackTrace()     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            r1 = 0
            r13 = r1
            r1 = r0
            r0 = r13
            goto L_0x02f8
        L_0x04cb:
            r0 = 0
            r14.o = r0     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            r0 = 0
            r14.setAddrStr(r0)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            goto L_0x032c
        L_0x04d4:
            java.lang.String r1 = "0"
            boolean r1 = r0.equals(r1)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            if (r1 == 0) goto L_0x04e1
            r0 = 0
            r14.z = r0     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            goto L_0x03ce
        L_0x04e1:
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            int r0 = r0.intValue()     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            r14.z = r0     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            goto L_0x03ce
        L_0x04ed:
            r2 = 1
            if (r1 != r2) goto L_0x03f8
            r1 = 1
            r14.setIndoorLocationSurpport(r1)     // Catch:{ Exception -> 0x04f6, Error -> 0x016a }
            goto L_0x03f8
        L_0x04f6:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
        L_0x04fa:
            java.lang.String r0 = "gpscs"
            boolean r0 = r11.has(r0)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            if (r0 == 0) goto L_0x0531
            java.lang.String r0 = "gpscs"
            int r0 = r11.getInt(r0)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            r14.setGpsCheckStatus(r0)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
        L_0x050b:
            java.lang.String r0 = "in_cn"
            boolean r0 = r11.has(r0)     // Catch:{ Exception -> 0x053b, Error -> 0x016a }
            if (r0 == 0) goto L_0x0536
            java.lang.String r0 = "in_cn"
            java.lang.String r0 = r11.getString(r0)     // Catch:{ Exception -> 0x053b, Error -> 0x016a }
            int r0 = java.lang.Integer.parseInt(r0)     // Catch:{ Exception -> 0x053b, Error -> 0x016a }
            r14.setLocationWhere(r0)     // Catch:{ Exception -> 0x053b, Error -> 0x016a }
        L_0x0520:
            int r0 = r14.A     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            if (r0 != 0) goto L_0x053d
            java.lang.String r0 = "wgs84"
            r14.setCoorType(r0)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            goto L_0x009c
        L_0x052c:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ Exception -> 0x04f6, Error -> 0x016a }
            goto L_0x04fa
        L_0x0531:
            r0 = 0
            r14.setGpsCheckStatus(r0)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            goto L_0x050b
        L_0x0536:
            r0 = 1
            r14.setLocationWhere(r0)     // Catch:{ Exception -> 0x053b, Error -> 0x016a }
            goto L_0x0520
        L_0x053b:
            r0 = move-exception
            goto L_0x0520
        L_0x053d:
            java.lang.String r0 = "gcj02"
            r14.setCoorType(r0)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            goto L_0x009c
        L_0x0544:
            r1 = 66
            if (r2 == r1) goto L_0x054c
            r1 = 68
            if (r2 != r1) goto L_0x0599
        L_0x054c:
            java.lang.String r1 = "content"
            org.json.JSONObject r0 = r0.getJSONObject(r1)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            java.lang.String r1 = "point"
            org.json.JSONObject r1 = r0.getJSONObject(r1)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            java.lang.String r2 = "y"
            java.lang.String r2 = r1.getString(r2)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            double r2 = java.lang.Double.parseDouble(r2)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            r14.setLatitude(r2)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            java.lang.String r2 = "x"
            java.lang.String r1 = r1.getString(r2)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            double r2 = java.lang.Double.parseDouble(r1)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            r14.setLongitude(r2)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            java.lang.String r1 = "radius"
            java.lang.String r1 = r0.getString(r1)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            float r1 = java.lang.Float.parseFloat(r1)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            r14.setRadius(r1)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            java.lang.String r1 = "isCellChanged"
            java.lang.String r0 = r0.getString(r1)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            boolean r0 = java.lang.Boolean.parseBoolean(r0)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            r14.a(r0)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            java.lang.String r0 = "gcj02"
            r14.setCoorType(r0)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            goto L_0x009c
        L_0x0599:
            r0 = 167(0xa7, float:2.34E-43)
            if (r2 != r0) goto L_0x009c
            r0 = 2
            r14.setLocationWhere(r0)     // Catch:{ Exception -> 0x0150, Error -> 0x016a }
            goto L_0x009c
        L_0x05a3:
            r1 = move-exception
            goto L_0x012f
        L_0x05a6:
            r13 = r1
            r1 = r0
            r0 = r13
            goto L_0x02f8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.location.BDLocation.<init>(java.lang.String):void");
    }

    private void a(Boolean bool) {
        this.t = bool.booleanValue();
    }

    public int describeContents() {
        return 0;
    }

    public String getAdCode() {
        return this.u.adcode;
    }

    public String getAddrStr() {
        return this.u.address;
    }

    public Address getAddress() {
        return this.u;
    }

    public double getAltitude() {
        return this.f;
    }

    public String getBuildingID() {
        return this.w;
    }

    public String getBuildingName() {
        return this.x;
    }

    public String getCity() {
        return this.u.city;
    }

    public String getCityCode() {
        return this.u.cityCode;
    }

    public String getCoorType() {
        return this.n;
    }

    public String getCountry() {
        return this.u.country;
    }

    public String getCountryCode() {
        return this.u.countryCode;
    }

    @Deprecated
    public float getDerect() {
        return this.m;
    }

    public float getDirection() {
        return this.m;
    }

    public String getDistrict() {
        return this.u.district;
    }

    public String getFloor() {
        return this.v;
    }

    public int getGpsAccuracyStatus() {
        return this.P;
    }

    public int getGpsCheckStatus() {
        return this.Q;
    }

    public int getIndoorLocationSource() {
        return this.H;
    }

    public int getIndoorLocationSurpport() {
        return this.F;
    }

    public String getIndoorLocationSurpportBuidlingID() {
        return this.J;
    }

    public String getIndoorLocationSurpportBuidlingName() {
        return this.I;
    }

    public int getIndoorNetworkState() {
        return this.G;
    }

    public String getIndoorSurpportPolygon() {
        return this.K;
    }

    public double getLatitude() {
        return this.c;
    }

    public int getLocType() {
        return this.a;
    }

    public String getLocTypeDescription() {
        return this.M;
    }

    public String getLocationDescribe() {
        return this.q;
    }

    public String getLocationID() {
        return this.N;
    }

    public int getLocationWhere() {
        return this.A;
    }

    public double getLongitude() {
        return this.d;
    }

    public String getNetworkLocationType() {
        return this.B;
    }

    public int getOperators() {
        return this.C;
    }

    public List<Poi> getPoiList() {
        return this.L;
    }

    public String getProvince() {
        return this.u.province;
    }

    public float getRadius() {
        return this.j;
    }

    public String getRetFields(String str) {
        return this.O.get(str);
    }

    public int getSatelliteNumber() {
        this.k = true;
        return this.l;
    }

    @Deprecated
    public String getSemaAptag() {
        return this.q;
    }

    public float getSpeed() {
        return this.h;
    }

    public String getStreet() {
        return this.u.street;
    }

    public String getStreetNumber() {
        return this.u.streetNumber;
    }

    public String getTime() {
        return this.b;
    }

    public int getUserIndoorState() {
        return this.E;
    }

    public boolean hasAddr() {
        return this.o;
    }

    public boolean hasAltitude() {
        return this.e;
    }

    public boolean hasRadius() {
        return this.i;
    }

    public boolean hasSateNumber() {
        return this.k;
    }

    public boolean hasSpeed() {
        return this.g;
    }

    public boolean isCellChangeFlag() {
        return this.t;
    }

    public boolean isIndoorLocMode() {
        return this.y;
    }

    public int isParkAvailable() {
        return this.z;
    }

    public void setAddr(Address address) {
        if (address != null) {
            this.u = address;
            this.o = true;
        }
    }

    public void setAddrStr(String str) {
        this.p = str;
        if (str == null) {
            this.o = false;
        } else {
            this.o = true;
        }
    }

    public void setAltitude(double d2) {
        this.f = d2;
        this.e = true;
    }

    public void setBuildingID(String str) {
        this.w = str;
    }

    public void setBuildingName(String str) {
        this.x = str;
    }

    public void setCoorType(String str) {
        this.n = str;
    }

    public void setDirection(float f2) {
        this.m = f2;
    }

    public void setFloor(String str) {
        this.v = str;
    }

    public void setGpsAccuracyStatus(int i2) {
        this.P = i2;
    }

    public void setGpsCheckStatus(int i2) {
        this.Q = i2;
    }

    public void setIndoorLocMode(boolean z2) {
        this.y = z2;
    }

    public void setIndoorLocationSource(int i2) {
        this.H = i2;
    }

    public void setIndoorLocationSurpport(int i2) {
        this.F = i2;
    }

    public void setIndoorNetworkState(int i2) {
        this.G = i2;
    }

    public void setIndoorSurpportPolygon(String str) {
        this.K = str;
    }

    public void setLatitude(double d2) {
        this.c = d2;
    }

    public void setLocType(int i2) {
        this.a = i2;
        switch (i2) {
            case 61:
                setLocTypeDescription("GPS location successful!");
                setUserIndoorState(0);
                return;
            case 62:
                setLocTypeDescription("Location failed beacuse we can not get any loc information!");
                return;
            case 63:
            case 67:
                setLocTypeDescription("Offline location failed, please check the net (wifi/cell)!");
                return;
            case 66:
                setLocTypeDescription("Offline location successful!");
                return;
            case 161:
                setLocTypeDescription("NetWork location successful!");
                return;
            case 162:
                setLocTypeDescription("NetWork location failed because baidu location service can not decrypt the request query, please check the so file !");
                return;
            case 167:
                setLocTypeDescription("NetWork location failed because baidu location service can not caculate the location!");
                return;
            case 505:
                setLocTypeDescription("NetWork location failed because baidu location service check the key is unlegal, please check the key in AndroidManifest.xml !");
                return;
            default:
                setLocTypeDescription("UnKnown!");
                return;
        }
    }

    public void setLocTypeDescription(String str) {
        this.M = str;
    }

    public void setLocationDescribe(String str) {
        this.q = str;
    }

    public void setLocationID(String str) {
        this.N = str;
    }

    public void setLocationWhere(int i2) {
        this.A = i2;
    }

    public void setLongitude(double d2) {
        this.d = d2;
    }

    public void setNetworkLocationType(String str) {
        this.B = str;
    }

    public void setOperators(int i2) {
        this.C = i2;
    }

    public void setParkAvailable(int i2) {
        this.z = i2;
    }

    public void setPoiList(List<Poi> list) {
        this.L = list;
    }

    public void setRadius(float f2) {
        this.j = f2;
        this.i = true;
    }

    public void setSatelliteNumber(int i2) {
        this.l = i2;
    }

    public void setSpeed(float f2) {
        this.h = f2;
        this.g = true;
    }

    public void setTime(String str) {
        this.b = str;
        setLocationID(j.a(str));
    }

    public void setUserIndoorState(int i2) {
        this.E = i2;
    }

    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeInt(this.a);
        parcel.writeString(this.b);
        parcel.writeDouble(this.c);
        parcel.writeDouble(this.d);
        parcel.writeDouble(this.f);
        parcel.writeFloat(this.h);
        parcel.writeFloat(this.j);
        parcel.writeInt(this.l);
        parcel.writeFloat(this.m);
        parcel.writeString(this.v);
        parcel.writeInt(this.z);
        parcel.writeString(this.w);
        parcel.writeString(this.x);
        parcel.writeString(this.B);
        parcel.writeString(this.u.province);
        parcel.writeString(this.u.city);
        parcel.writeString(this.u.district);
        parcel.writeString(this.u.street);
        parcel.writeString(this.u.streetNumber);
        parcel.writeString(this.u.cityCode);
        parcel.writeString(this.u.address);
        parcel.writeString(this.u.country);
        parcel.writeString(this.u.countryCode);
        parcel.writeString(this.u.adcode);
        parcel.writeInt(this.C);
        parcel.writeString(this.D);
        parcel.writeString(this.q);
        parcel.writeString(this.r);
        parcel.writeString(this.s);
        parcel.writeInt(this.A);
        parcel.writeString(this.M);
        parcel.writeInt(this.E);
        parcel.writeInt(this.F);
        parcel.writeInt(this.G);
        parcel.writeInt(this.H);
        parcel.writeString(this.I);
        parcel.writeString(this.J);
        parcel.writeString(this.K);
        parcel.writeInt(this.P);
        parcel.writeString(this.N);
        parcel.writeInt(this.Q);
        parcel.writeBooleanArray(new boolean[]{this.e, this.g, this.i, this.k, this.o, this.t, this.y});
        parcel.writeList(this.L);
    }
}
