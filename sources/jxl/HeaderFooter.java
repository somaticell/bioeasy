package jxl;

import jxl.biff.HeaderFooter;

public final class HeaderFooter extends jxl.biff.HeaderFooter {

    public static class Contents extends HeaderFooter.Contents {
        Contents() {
        }

        Contents(String s) {
            super(s);
        }

        Contents(Contents copy) {
            super((HeaderFooter.Contents) copy);
        }

        public void append(String txt) {
            super.append(txt);
        }

        public void toggleBold() {
            super.toggleBold();
        }

        public void toggleUnderline() {
            super.toggleUnderline();
        }

        public void toggleItalics() {
            super.toggleItalics();
        }

        public void toggleStrikethrough() {
            super.toggleStrikethrough();
        }

        public void toggleDoubleUnderline() {
            super.toggleDoubleUnderline();
        }

        public void toggleSuperScript() {
            super.toggleSuperScript();
        }

        public void toggleSubScript() {
            super.toggleSubScript();
        }

        public void toggleOutline() {
            super.toggleOutline();
        }

        public void toggleShadow() {
            super.toggleShadow();
        }

        public void setFontName(String fontName) {
            super.setFontName(fontName);
        }

        public boolean setFontSize(int size) {
            return super.setFontSize(size);
        }

        public void appendPageNumber() {
            super.appendPageNumber();
        }

        public void appendTotalPages() {
            super.appendTotalPages();
        }

        public void appendDate() {
            super.appendDate();
        }

        public void appendTime() {
            super.appendTime();
        }

        public void appendWorkbookName() {
            super.appendWorkbookName();
        }

        public void appendWorkSheetName() {
            super.appendWorkSheetName();
        }

        public void clear() {
            super.clear();
        }

        public boolean empty() {
            return super.empty();
        }
    }

    public HeaderFooter() {
    }

    public HeaderFooter(HeaderFooter hf) {
        super((jxl.biff.HeaderFooter) hf);
    }

    public HeaderFooter(String s) {
        super(s);
    }

    public String toString() {
        return super.toString();
    }

    public Contents getRight() {
        return (Contents) super.getRightText();
    }

    public Contents getCentre() {
        return (Contents) super.getCentreText();
    }

    public Contents getLeft() {
        return (Contents) super.getLeftText();
    }

    public void clear() {
        super.clear();
    }

    /* access modifiers changed from: protected */
    public HeaderFooter.Contents createContents() {
        return new Contents();
    }

    /* access modifiers changed from: protected */
    public HeaderFooter.Contents createContents(String s) {
        return new Contents(s);
    }

    /* access modifiers changed from: protected */
    public HeaderFooter.Contents createContents(HeaderFooter.Contents c) {
        return new Contents((Contents) c);
    }
}
