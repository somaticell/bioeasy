package jxl.biff;

import common.Logger;

public class CountryCode {
    public static final CountryCode BELGIUM = new CountryCode(32, "BE", "Belgium");
    public static final CountryCode CANADA = new CountryCode(2, "CA", "Canada");
    public static final CountryCode CHINA = new CountryCode(86, "CN", "China");
    public static final CountryCode DENMARK = new CountryCode(45, "DK", "Denmark");
    public static final CountryCode FRANCE = new CountryCode(33, "FR", "France");
    public static final CountryCode GERMANY = new CountryCode(49, "DE", "Germany");
    public static final CountryCode GREECE = new CountryCode(30, "GR", "Greece");
    public static final CountryCode INDIA = new CountryCode(91, "IN", "India");
    public static final CountryCode ITALY = new CountryCode(39, "IT", "Italy");
    public static final CountryCode NETHERLANDS = new CountryCode(31, "NE", "Netherlands");
    public static final CountryCode NORWAY = new CountryCode(47, "NO", "Norway");
    public static final CountryCode PHILIPPINES = new CountryCode(63, "PH", "Philippines");
    public static final CountryCode SPAIN = new CountryCode(34, "ES", "Spain");
    public static final CountryCode SWEDEN = new CountryCode(46, "SE", "Sweden");
    public static final CountryCode SWITZERLAND = new CountryCode(41, "CH", "Switzerland");
    public static final CountryCode UK = new CountryCode(44, "UK", "United Kingdowm");
    public static final CountryCode UNKNOWN = new CountryCode(65535, "??", "Unknown");
    public static final CountryCode USA = new CountryCode(1, "US", "USA");
    static Class class$jxl$biff$CountryCode;
    private static CountryCode[] codes = new CountryCode[0];
    private static Logger logger;
    private String code;
    private String description;
    private int value;

    static {
        Class cls;
        if (class$jxl$biff$CountryCode == null) {
            cls = class$("jxl.biff.CountryCode");
            class$jxl$biff$CountryCode = cls;
        } else {
            cls = class$jxl$biff$CountryCode;
        }
        logger = Logger.getLogger(cls);
    }

    static Class class$(String x0) {
        try {
            return Class.forName(x0);
        } catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError(x1.getMessage());
        }
    }

    private CountryCode(int v, String c, String d) {
        this.value = v;
        this.code = c;
        this.description = d;
        CountryCode[] newcodes = new CountryCode[(codes.length + 1)];
        System.arraycopy(codes, 0, newcodes, 0, codes.length);
        newcodes[codes.length] = this;
        codes = newcodes;
    }

    private CountryCode(int v) {
        this.value = v;
        this.description = "Arbitrary";
        this.code = "??";
    }

    public int getValue() {
        return this.value;
    }

    public String getCode() {
        return this.code;
    }

    public static CountryCode getCountryCode(String s) {
        if (s == null || s.length() != 2) {
            logger.warn("Please specify two character ISO 3166 country code");
            return USA;
        }
        CountryCode code2 = UNKNOWN;
        for (int i = 0; i < codes.length && code2 == UNKNOWN; i++) {
            if (codes[i].code.equals(s)) {
                code2 = codes[i];
            }
        }
        return code2;
    }

    public static CountryCode createArbitraryCode(int i) {
        return new CountryCode(i);
    }
}
