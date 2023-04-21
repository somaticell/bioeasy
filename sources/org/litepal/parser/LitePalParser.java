package org.litepal.parser;

import android.content.res.AssetManager;
import android.content.res.Resources;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import org.litepal.LitePalApplication;
import org.litepal.exceptions.ParseConfigurationFileException;
import org.litepal.util.Const;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class LitePalParser {
    static final String ATTR_CLASS = "class";
    static final String ATTR_VALUE = "value";
    static final String NODE_CASES = "cases";
    static final String NODE_DB_NAME = "dbname";
    static final String NODE_LIST = "list";
    static final String NODE_MAPPING = "mapping";
    static final String NODE_STORAGE = "storage";
    static final String NODE_VERSION = "version";
    private static LitePalParser parser;

    public static LitePalConfig parseLitePalConfiguration() {
        if (parser == null) {
            parser = new LitePalParser();
        }
        return parser.usePullParse();
    }

    private void useSAXParser() {
        try {
            XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
            xmlReader.setContentHandler(new LitePalContentHandler());
            xmlReader.parse(new InputSource(getConfigInputStream()));
        } catch (Resources.NotFoundException e) {
            throw new ParseConfigurationFileException(ParseConfigurationFileException.CAN_NOT_FIND_LITEPAL_FILE);
        } catch (SAXException e2) {
            throw new ParseConfigurationFileException(ParseConfigurationFileException.FILE_FORMAT_IS_NOT_CORRECT);
        } catch (ParserConfigurationException e3) {
            throw new ParseConfigurationFileException(ParseConfigurationFileException.PARSE_CONFIG_FAILED);
        } catch (IOException e4) {
            throw new ParseConfigurationFileException(ParseConfigurationFileException.IO_EXCEPTION);
        }
    }

    private LitePalConfig usePullParse() {
        try {
            LitePalConfig litePalConfig = new LitePalConfig();
            XmlPullParser xmlPullParser = XmlPullParserFactory.newInstance().newPullParser();
            xmlPullParser.setInput(getConfigInputStream(), "UTF-8");
            for (int eventType = xmlPullParser.getEventType(); eventType != 1; eventType = xmlPullParser.next()) {
                String nodeName = xmlPullParser.getName();
                switch (eventType) {
                    case 2:
                        if (!NODE_DB_NAME.equals(nodeName)) {
                            if (!NODE_VERSION.equals(nodeName)) {
                                if (!NODE_MAPPING.equals(nodeName)) {
                                    if (!NODE_CASES.equals(nodeName)) {
                                        if (!NODE_STORAGE.equals(nodeName)) {
                                            break;
                                        } else {
                                            litePalConfig.setStorage(xmlPullParser.getAttributeValue("", ATTR_VALUE));
                                            break;
                                        }
                                    } else {
                                        litePalConfig.setCases(xmlPullParser.getAttributeValue("", ATTR_VALUE));
                                        break;
                                    }
                                } else {
                                    litePalConfig.addClassName(xmlPullParser.getAttributeValue("", ATTR_CLASS));
                                    break;
                                }
                            } else {
                                litePalConfig.setVersion(Integer.parseInt(xmlPullParser.getAttributeValue("", ATTR_VALUE)));
                                break;
                            }
                        } else {
                            litePalConfig.setDbName(xmlPullParser.getAttributeValue("", ATTR_VALUE));
                            break;
                        }
                }
            }
            return litePalConfig;
        } catch (XmlPullParserException e) {
            throw new ParseConfigurationFileException(ParseConfigurationFileException.FILE_FORMAT_IS_NOT_CORRECT);
        } catch (IOException e2) {
            throw new ParseConfigurationFileException(ParseConfigurationFileException.IO_EXCEPTION);
        }
    }

    private InputStream getConfigInputStream() throws IOException {
        AssetManager assetManager = LitePalApplication.getContext().getAssets();
        String[] fileNames = assetManager.list("");
        if (fileNames != null && fileNames.length > 0) {
            for (String fileName : fileNames) {
                if (Const.Config.CONFIGURATION_FILE_NAME.equalsIgnoreCase(fileName)) {
                    return assetManager.open(fileName, 3);
                }
            }
        }
        throw new ParseConfigurationFileException(ParseConfigurationFileException.CAN_NOT_FIND_LITEPAL_FILE);
    }
}
