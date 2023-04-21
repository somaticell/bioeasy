package cn.com.bioeasy.healty.app.healthapp.modules.mall.service;

import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.CityBean;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.DistrictBean;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.ProvinceBean;
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XmlParserHandler extends DefaultHandler {
    CityBean cityBean = new CityBean();
    DistrictBean districtBean = new DistrictBean();
    ProvinceBean provinceBean = new ProvinceBean();
    private List<ProvinceBean> provinceList = new ArrayList();

    public List<ProvinceBean> getDataList() {
        return this.provinceList;
    }

    public void startDocument() throws SAXException {
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("province")) {
            this.provinceBean = new ProvinceBean();
            this.provinceBean.setName(attributes.getValue(0));
            this.provinceBean.setCityList(new ArrayList());
        } else if (qName.equals("city")) {
            this.cityBean = new CityBean();
            this.cityBean.setName(attributes.getValue(0));
            this.cityBean.setDistrictList(new ArrayList());
        } else if (qName.equals("district")) {
            this.districtBean = new DistrictBean();
            this.districtBean.setName(attributes.getValue(0));
        }
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("district")) {
            this.cityBean.getDistrictList().add(this.districtBean);
        } else if (qName.equals("city")) {
            this.provinceBean.getCityList().add(this.cityBean);
        } else if (qName.equals("province")) {
            this.provinceList.add(this.provinceBean);
        }
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
    }
}
