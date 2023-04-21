package jxl.biff;

import common.Logger;

public abstract class HeaderFooter {
    private static final String BOLD_TOGGLE = "&B";
    private static final String CENTRE = "&C";
    private static final String DATE = "&D";
    private static final String DOUBLE_UNDERLINE_TOGGLE = "&E";
    private static final String ITALICS_TOGGLE = "&I";
    private static final String LEFT_ALIGN = "&L";
    private static final String OUTLINE_TOGGLE = "&O";
    private static final String PAGENUM = "&P";
    private static final String RIGHT_ALIGN = "&R";
    private static final String SHADOW_TOGGLE = "&H";
    private static final String STRIKETHROUGH_TOGGLE = "&S";
    private static final String SUBSCRIPT_TOGGLE = "&Y";
    private static final String SUPERSCRIPT_TOGGLE = "&X";
    private static final String TIME = "&T";
    private static final String TOTAL_PAGENUM = "&N";
    private static final String UNDERLINE_TOGGLE = "&U";
    private static final String WORKBOOK_NAME = "&F";
    private static final String WORKSHEET_NAME = "&A";
    static Class class$jxl$biff$HeaderFooter;
    private static Logger logger;
    private Contents centre;
    private Contents left;
    private Contents right;

    /* access modifiers changed from: protected */
    public abstract Contents createContents();

    /* access modifiers changed from: protected */
    public abstract Contents createContents(String str);

    /* access modifiers changed from: protected */
    public abstract Contents createContents(Contents contents);

    static {
        Class cls;
        if (class$jxl$biff$HeaderFooter == null) {
            cls = class$("jxl.biff.HeaderFooter");
            class$jxl$biff$HeaderFooter = cls;
        } else {
            cls = class$jxl$biff$HeaderFooter;
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

    protected static class Contents {
        private StringBuffer contents;

        protected Contents() {
            this.contents = new StringBuffer();
        }

        protected Contents(String s) {
            this.contents = new StringBuffer(s);
        }

        protected Contents(Contents copy) {
            this.contents = new StringBuffer(copy.getContents());
        }

        /* access modifiers changed from: protected */
        public String getContents() {
            return this.contents != null ? this.contents.toString() : "";
        }

        private void appendInternal(String txt) {
            if (this.contents == null) {
                this.contents = new StringBuffer();
            }
            this.contents.append(txt);
        }

        private void appendInternal(char ch) {
            if (this.contents == null) {
                this.contents = new StringBuffer();
            }
            this.contents.append(ch);
        }

        /* access modifiers changed from: protected */
        public void append(String txt) {
            appendInternal(txt);
        }

        /* access modifiers changed from: protected */
        public void toggleBold() {
            appendInternal(HeaderFooter.BOLD_TOGGLE);
        }

        /* access modifiers changed from: protected */
        public void toggleUnderline() {
            appendInternal(HeaderFooter.UNDERLINE_TOGGLE);
        }

        /* access modifiers changed from: protected */
        public void toggleItalics() {
            appendInternal(HeaderFooter.ITALICS_TOGGLE);
        }

        /* access modifiers changed from: protected */
        public void toggleStrikethrough() {
            appendInternal(HeaderFooter.STRIKETHROUGH_TOGGLE);
        }

        /* access modifiers changed from: protected */
        public void toggleDoubleUnderline() {
            appendInternal(HeaderFooter.DOUBLE_UNDERLINE_TOGGLE);
        }

        /* access modifiers changed from: protected */
        public void toggleSuperScript() {
            appendInternal(HeaderFooter.SUPERSCRIPT_TOGGLE);
        }

        /* access modifiers changed from: protected */
        public void toggleSubScript() {
            appendInternal(HeaderFooter.SUBSCRIPT_TOGGLE);
        }

        /* access modifiers changed from: protected */
        public void toggleOutline() {
            appendInternal(HeaderFooter.OUTLINE_TOGGLE);
        }

        /* access modifiers changed from: protected */
        public void toggleShadow() {
            appendInternal(HeaderFooter.SHADOW_TOGGLE);
        }

        /* access modifiers changed from: protected */
        public void setFontName(String fontName) {
            appendInternal("&\"");
            appendInternal(fontName);
            appendInternal('\"');
        }

        /* access modifiers changed from: protected */
        public boolean setFontSize(int size) {
            String fontSize;
            if (size < 1 || size > 99) {
                return false;
            }
            if (size < 10) {
                fontSize = new StringBuffer().append("0").append(size).toString();
            } else {
                fontSize = Integer.toString(size);
            }
            appendInternal('&');
            appendInternal(fontSize);
            return true;
        }

        /* access modifiers changed from: protected */
        public void appendPageNumber() {
            appendInternal(HeaderFooter.PAGENUM);
        }

        /* access modifiers changed from: protected */
        public void appendTotalPages() {
            appendInternal(HeaderFooter.TOTAL_PAGENUM);
        }

        /* access modifiers changed from: protected */
        public void appendDate() {
            appendInternal(HeaderFooter.DATE);
        }

        /* access modifiers changed from: protected */
        public void appendTime() {
            appendInternal(HeaderFooter.TIME);
        }

        /* access modifiers changed from: protected */
        public void appendWorkbookName() {
            appendInternal(HeaderFooter.WORKBOOK_NAME);
        }

        /* access modifiers changed from: protected */
        public void appendWorkSheetName() {
            appendInternal(HeaderFooter.WORKSHEET_NAME);
        }

        /* access modifiers changed from: protected */
        public void clear() {
            this.contents = null;
        }

        /* access modifiers changed from: protected */
        public boolean empty() {
            if (this.contents == null || this.contents.length() == 0) {
                return true;
            }
            return false;
        }
    }

    protected HeaderFooter() {
        this.left = createContents();
        this.right = createContents();
        this.centre = createContents();
    }

    protected HeaderFooter(HeaderFooter hf) {
        this.left = createContents(hf.left);
        this.right = createContents(hf.right);
        this.centre = createContents(hf.centre);
    }

    protected HeaderFooter(String s) {
        if (s == null || s.length() == 0) {
            this.left = createContents();
            this.right = createContents();
            this.centre = createContents();
            return;
        }
        int pos = 0;
        int leftPos = s.indexOf(LEFT_ALIGN);
        int rightPos = s.indexOf(RIGHT_ALIGN);
        int centrePos = s.indexOf(CENTRE);
        if (0 == leftPos) {
            if (centrePos != -1) {
                this.left = createContents(s.substring(2, centrePos));
                pos = centrePos;
            } else if (rightPos != -1) {
                this.left = createContents(s.substring(2, rightPos));
                pos = rightPos;
            } else {
                this.left = createContents(s.substring(2));
                pos = s.length();
            }
        }
        if (pos == centrePos || (leftPos == -1 && rightPos == -1 && centrePos == -1)) {
            if (rightPos != -1) {
                this.centre = createContents(s.substring(pos + 2, rightPos));
                pos = rightPos;
            } else {
                this.centre = createContents(s.substring(pos + 2));
                pos = s.length();
            }
        }
        if (pos == rightPos) {
            this.right = createContents(s.substring(pos + 2));
            int pos2 = s.length();
        }
        if (this.left == null) {
            this.left = createContents();
        }
        if (this.centre == null) {
            this.centre = createContents();
        }
        if (this.right == null) {
            this.right = createContents();
        }
    }

    public String toString() {
        StringBuffer hf = new StringBuffer();
        if (!this.left.empty()) {
            hf.append(LEFT_ALIGN);
            hf.append(this.left.getContents());
        }
        if (!this.centre.empty()) {
            hf.append(CENTRE);
            hf.append(this.centre.getContents());
        }
        if (!this.right.empty()) {
            hf.append(RIGHT_ALIGN);
            hf.append(this.right.getContents());
        }
        return hf.toString();
    }

    /* access modifiers changed from: protected */
    public Contents getRightText() {
        return this.right;
    }

    /* access modifiers changed from: protected */
    public Contents getCentreText() {
        return this.centre;
    }

    /* access modifiers changed from: protected */
    public Contents getLeftText() {
        return this.left;
    }

    /* access modifiers changed from: protected */
    public void clear() {
        this.left.clear();
        this.right.clear();
        this.centre.clear();
    }
}
