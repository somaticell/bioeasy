package com.baidu.location;

public final class Address {
    public final String adcode;
    public final String address;
    public final String city;
    public final String cityCode;
    public final String country;
    public final String countryCode;
    public final String district;
    public final String province;
    public final String street;
    public final String streetNumber;

    public static class Builder {
        private static final String BEI_JING = "北京";
        private static final String CHONG_QIN = "重庆";
        private static final String SHANG_HAI = "上海";
        private static final String TIAN_JIN = "天津";
        /* access modifiers changed from: private */
        public String mAdcode = null;
        /* access modifiers changed from: private */
        public String mAddress = null;
        /* access modifiers changed from: private */
        public String mCity = null;
        /* access modifiers changed from: private */
        public String mCityCode = null;
        /* access modifiers changed from: private */
        public String mCountry = null;
        /* access modifiers changed from: private */
        public String mCountryCode = null;
        /* access modifiers changed from: private */
        public String mDistrict = null;
        /* access modifiers changed from: private */
        public String mProvince = null;
        /* access modifiers changed from: private */
        public String mStreet = null;
        /* access modifiers changed from: private */
        public String mStreetNumber = null;

        public Builder adcode(String str) {
            this.mAdcode = str;
            return this;
        }

        public Address build() {
            StringBuffer stringBuffer = new StringBuffer();
            if (this.mCountry != null) {
                stringBuffer.append(this.mCountry);
            }
            if (this.mProvince != null) {
                stringBuffer.append(this.mProvince);
            }
            if (!(this.mProvince == null || this.mCity == null || this.mProvince.equals(this.mCity))) {
                stringBuffer.append(this.mCity);
            }
            if (this.mDistrict != null) {
                if (this.mCity == null) {
                    stringBuffer.append(this.mDistrict);
                } else if (!this.mCity.equals(this.mDistrict)) {
                    stringBuffer.append(this.mDistrict);
                }
            }
            if (this.mStreet != null) {
                stringBuffer.append(this.mStreet);
            }
            if (this.mStreetNumber != null) {
                stringBuffer.append(this.mStreetNumber);
            }
            if (stringBuffer.length() > 0) {
                this.mAddress = stringBuffer.toString();
            }
            return new Address(this);
        }

        public Builder city(String str) {
            this.mCity = str;
            return this;
        }

        public Builder cityCode(String str) {
            this.mCityCode = str;
            return this;
        }

        public Builder country(String str) {
            this.mCountry = str;
            return this;
        }

        public Builder countryCode(String str) {
            this.mCountryCode = str;
            return this;
        }

        public Builder district(String str) {
            this.mDistrict = str;
            return this;
        }

        public Builder province(String str) {
            this.mProvince = str;
            return this;
        }

        public Builder street(String str) {
            this.mStreet = str;
            return this;
        }

        public Builder streetNumber(String str) {
            this.mStreetNumber = str;
            return this;
        }
    }

    private Address(Builder builder) {
        this.country = builder.mCountry;
        this.countryCode = builder.mCountryCode;
        this.province = builder.mProvince;
        this.city = builder.mCity;
        this.cityCode = builder.mCityCode;
        this.district = builder.mDistrict;
        this.street = builder.mStreet;
        this.streetNumber = builder.mStreetNumber;
        this.address = builder.mAddress;
        this.adcode = builder.mAdcode;
    }
}
