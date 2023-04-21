package jxl.biff;

import common.Logger;
import jxl.biff.drawing.Comment;
import jxl.write.biff.CellValue;

public class BaseCellFeatures {
    static Class class$jxl$biff$BaseCellFeatures = null;
    private static final double defaultCommentHeight = 4.0d;
    private static final double defaultCommentWidth = 3.0d;
    public static Logger logger;
    private String comment;
    private Comment commentDrawing;
    private double commentHeight;
    private double commentWidth;
    private CellValue writableCell;

    static {
        Class cls;
        if (class$jxl$biff$BaseCellFeatures == null) {
            cls = class$("jxl.biff.BaseCellFeatures");
            class$jxl$biff$BaseCellFeatures = cls;
        } else {
            cls = class$jxl$biff$BaseCellFeatures;
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

    protected BaseCellFeatures() {
    }

    public BaseCellFeatures(BaseCellFeatures cf) {
        this.comment = cf.comment;
        this.commentWidth = cf.commentWidth;
        this.commentHeight = cf.commentHeight;
    }

    /* access modifiers changed from: protected */
    public String getComment() {
        return this.comment;
    }

    public double getCommentWidth() {
        return this.commentWidth;
    }

    public double getCommentHeight() {
        return this.commentHeight;
    }

    public final void setWritableCell(CellValue wc) {
        this.writableCell = wc;
    }

    public void setReadComment(String s, double w, double h) {
        this.comment = s;
        this.commentWidth = w;
        this.commentHeight = h;
    }

    public void setComment(String s) {
        setComment(s, defaultCommentWidth, defaultCommentHeight);
    }

    public void setComment(String s, double width, double height) {
        this.comment = s;
        this.commentWidth = width;
        this.commentHeight = height;
        if (this.commentDrawing != null) {
            this.commentDrawing.setCommentText(s);
            this.commentDrawing.setWidth(width);
            this.commentDrawing.setWidth(height);
        }
    }

    public void removeComment() {
        this.comment = null;
        if (this.commentDrawing != null) {
            this.writableCell.removeComment(this.commentDrawing);
            this.commentDrawing = null;
        }
    }

    public final void setCommentDrawing(Comment c) {
        this.commentDrawing = c;
    }

    public final Comment getCommentDrawing() {
        return this.commentDrawing;
    }
}
