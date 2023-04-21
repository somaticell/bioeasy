package cn.com.bioeasy.healty.app.healthapp.modules.user.bean;

public class PhoneBean {
    private String reason;
    private ResultBean result;
    private String resultcode;

    public String getResultcode() {
        return this.resultcode;
    }

    public void setResultcode(String resultcode2) {
        this.resultcode = resultcode2;
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(String reason2) {
        this.reason = reason2;
    }

    public ResultBean getResult() {
        return this.result;
    }

    public void setResult(ResultBean result2) {
        this.result = result2;
    }

    public static class ResultBean {
        private String areacode;
        private String card;
        private String city;
        private String company;
        private String province;
        private String zip;

        public String getProvince() {
            return this.province;
        }

        public void setProvince(String province2) {
            this.province = province2;
        }

        public String getCity() {
            return this.city;
        }

        public void setCity(String city2) {
            this.city = city2;
        }

        public String getAreacode() {
            return this.areacode;
        }

        public void setAreacode(String areacode2) {
            this.areacode = areacode2;
        }

        public String getZip() {
            return this.zip;
        }

        public void setZip(String zip2) {
            this.zip = zip2;
        }

        public String getCompany() {
            return this.company;
        }

        public void setCompany(String company2) {
            this.company = company2;
        }

        public String getCard() {
            return this.card;
        }

        public void setCard(String card2) {
            this.card = card2;
        }
    }
}
