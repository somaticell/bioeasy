package jxl;

import common.Logger;
import java.util.HashMap;
import java.util.Locale;
import jxl.biff.CountryCode;
import jxl.biff.formula.FunctionNames;

public final class WorkbookSettings {
    static Class class$jxl$WorkbookSettings = null;
    private static final int defaultArrayGrowSize = 1048576;
    private static final int defaultInitialFileSize = 5242880;
    private static Logger logger;
    private int arrayGrowSize = 1048576;
    private int characterSet;
    private boolean drawingsDisabled;
    private String encoding;
    private String excelDisplayLanguage = CountryCode.USA.getCode();
    private String excelRegionalSettings = CountryCode.UK.getCode();
    private boolean formulaReferenceAdjustDisabled;
    private FunctionNames functionNames;
    private boolean gcDisabled;
    private boolean ignoreBlankCells;
    private int initialFileSize = defaultInitialFileSize;
    private Locale locale;
    private HashMap localeFunctionNames = new HashMap();
    private boolean namesDisabled;
    private boolean propertySetsDisabled;
    private boolean rationalizationDisabled;

    static {
        Class cls;
        if (class$jxl$WorkbookSettings == null) {
            cls = class$("jxl.WorkbookSettings");
            class$jxl$WorkbookSettings = cls;
        } else {
            cls = class$jxl$WorkbookSettings;
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

    public WorkbookSettings() {
        try {
            setSuppressWarnings(Boolean.getBoolean("jxl.nowarnings"));
            this.drawingsDisabled = Boolean.getBoolean("jxl.nodrawings");
            this.namesDisabled = Boolean.getBoolean("jxl.nonames");
            this.gcDisabled = Boolean.getBoolean("jxl.nogc");
            this.rationalizationDisabled = Boolean.getBoolean("jxl.norat");
            this.formulaReferenceAdjustDisabled = Boolean.getBoolean("jxl.noformulaadjust");
            this.propertySetsDisabled = Boolean.getBoolean("jxl.nopropertysets");
            this.ignoreBlankCells = Boolean.getBoolean("jxl.ignoreblanks");
            this.encoding = System.getProperty("file.encoding");
        } catch (SecurityException e) {
            logger.warn("Error accessing system properties.", e);
        }
        try {
            if (System.getProperty("jxl.lang") == null || System.getProperty("jxl.country") == null) {
                this.locale = Locale.getDefault();
            } else {
                this.locale = new Locale(System.getProperty("jxl.lang"), System.getProperty("jxl.country"));
            }
            if (System.getProperty("jxl.encoding") != null) {
                this.encoding = System.getProperty("jxl.encoding");
            }
        } catch (SecurityException e2) {
            logger.warn("Error accessing system properties.", e2);
            this.locale = Locale.getDefault();
        }
    }

    public void setArrayGrowSize(int sz) {
        this.arrayGrowSize = sz;
    }

    public int getArrayGrowSize() {
        return this.arrayGrowSize;
    }

    public void setInitialFileSize(int sz) {
        this.initialFileSize = sz;
    }

    public int getInitialFileSize() {
        return this.initialFileSize;
    }

    public boolean getDrawingsDisabled() {
        return this.drawingsDisabled;
    }

    public boolean getGCDisabled() {
        return this.gcDisabled;
    }

    public boolean getNamesDisabled() {
        return this.namesDisabled;
    }

    public void setNamesDisabled(boolean b) {
        this.namesDisabled = b;
    }

    public void setDrawingsDisabled(boolean b) {
        this.drawingsDisabled = b;
    }

    public void setRationalization(boolean r) {
        this.rationalizationDisabled = !r;
    }

    public boolean getRationalizationDisabled() {
        return this.rationalizationDisabled;
    }

    public void setPropertySets(boolean r) {
        this.propertySetsDisabled = !r;
    }

    public boolean getPropertySetsDisabled() {
        return this.propertySetsDisabled;
    }

    public void setSuppressWarnings(boolean w) {
        logger.setSuppressWarnings(w);
    }

    public boolean getFormulaAdjust() {
        return !this.formulaReferenceAdjustDisabled;
    }

    public void setFormulaAdjust(boolean b) {
        this.formulaReferenceAdjustDisabled = !b;
    }

    public void setLocale(Locale l) {
        this.locale = l;
    }

    public Locale getLocale() {
        return this.locale;
    }

    public String getEncoding() {
        return this.encoding;
    }

    public void setEncoding(String enc) {
        this.encoding = enc;
    }

    public FunctionNames getFunctionNames() {
        if (this.functionNames == null) {
            this.functionNames = (FunctionNames) this.localeFunctionNames.get(this.locale);
            if (this.functionNames == null) {
                this.functionNames = new FunctionNames(this.locale);
                this.localeFunctionNames.put(this.locale, this.functionNames);
            }
        }
        return this.functionNames;
    }

    public int getCharacterSet() {
        return this.characterSet;
    }

    public void setCharacterSet(int cs) {
        this.characterSet = cs;
    }

    public void setGCDisabled(boolean disabled) {
        this.gcDisabled = disabled;
    }

    public void setIgnoreBlanks(boolean ignoreBlanks) {
        this.ignoreBlankCells = ignoreBlanks;
    }

    public boolean getIgnoreBlanks() {
        return this.ignoreBlankCells;
    }

    public String getExcelDisplayLanguage() {
        return this.excelDisplayLanguage;
    }

    public String getExcelRegionalSettings() {
        return this.excelRegionalSettings;
    }

    public void setExcelDisplayLanguage(String code) {
        this.excelDisplayLanguage = code;
    }

    public void setExcelRegionalSettings(String code) {
        this.excelRegionalSettings = code;
    }
}
