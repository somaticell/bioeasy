package com.orhanobut.logger;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

final class LoggerPrinter implements Printer {
    private static final int ASSERT = 7;
    private static final String BOTTOM_BORDER = "╚════════════════════════════════════════════════════════════════════════════════════════";
    private static final char BOTTOM_LEFT_CORNER = '╚';
    private static final int CHUNK_SIZE = 4000;
    private static final int DEBUG = 3;
    private static final String DEFAULT_TAG = "PRETTYLOGGER";
    private static final String DOUBLE_DIVIDER = "════════════════════════════════════════════";
    private static final int ERROR = 6;
    private static final char HORIZONTAL_DOUBLE_LINE = '║';
    private static final int INFO = 4;
    private static final int JSON_INDENT = 2;
    private static final String MIDDLE_BORDER = "╟────────────────────────────────────────────────────────────────────────────────────────";
    private static final char MIDDLE_CORNER = '╟';
    private static final int MIN_STACK_OFFSET = 3;
    private static final String SINGLE_DIVIDER = "────────────────────────────────────────────";
    private static final String TOP_BORDER = "╔════════════════════════════════════════════════════════════════════════════════════════";
    private static final char TOP_LEFT_CORNER = '╔';
    private static final int VERBOSE = 2;
    private static final int WARN = 5;
    private final ThreadLocal<Integer> localMethodCount = new ThreadLocal<>();
    private final ThreadLocal<String> localTag = new ThreadLocal<>();
    private final Settings settings = new Settings();
    private String tag;

    public LoggerPrinter() {
        init(DEFAULT_TAG);
    }

    public Settings init(String tag2) {
        if (tag2 == null) {
            throw new NullPointerException("tag may not be null");
        } else if (tag2.trim().length() == 0) {
            throw new IllegalStateException("tag may not be empty");
        } else {
            this.tag = tag2;
            return this.settings;
        }
    }

    public Settings getSettings() {
        return this.settings;
    }

    public Printer t(String tag2, int methodCount) {
        if (tag2 != null) {
            this.localTag.set(tag2);
        }
        this.localMethodCount.set(Integer.valueOf(methodCount));
        return this;
    }

    public void d(String message, Object... args) {
        log(3, (Throwable) null, message, args);
    }

    public void d(Object object) {
        String message;
        if (object.getClass().isArray()) {
            message = Arrays.deepToString((Object[]) object);
        } else {
            message = object.toString();
        }
        log(3, (Throwable) null, message, new Object[0]);
    }

    public void e(String message, Object... args) {
        e((Throwable) null, message, args);
    }

    public void e(Throwable throwable, String message, Object... args) {
        log(6, throwable, message, args);
    }

    public void w(String message, Object... args) {
        log(5, (Throwable) null, message, args);
    }

    public void i(String message, Object... args) {
        log(4, (Throwable) null, message, args);
    }

    public void v(String message, Object... args) {
        log(2, (Throwable) null, message, args);
    }

    public void wtf(String message, Object... args) {
        log(7, (Throwable) null, message, args);
    }

    public void json(String json) {
        if (Helper.isEmpty(json)) {
            d("Empty/Null json content");
            return;
        }
        try {
            String json2 = json.trim();
            if (json2.startsWith("{")) {
                d(new JSONObject(json2).toString(2));
            } else if (json2.startsWith("[")) {
                d(new JSONArray(json2).toString(2));
            } else {
                e("Invalid Json", new Object[0]);
            }
        } catch (JSONException e) {
            e("Invalid Json", new Object[0]);
        }
    }

    public void xml(String xml) {
        if (Helper.isEmpty(xml)) {
            d("Empty/Null xml content");
            return;
        }
        try {
            Source xmlInput = new StreamSource(new StringReader(xml));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty("indent", "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(xmlInput, xmlOutput);
            d(xmlOutput.getWriter().toString().replaceFirst(">", ">\n"));
        } catch (TransformerException e) {
            e("Invalid xml", new Object[0]);
        }
    }

    public synchronized void log(int priority, String tag2, String message, Throwable throwable) {
        if (this.settings.getLogLevel() != LogLevel.NONE) {
            if (!(throwable == null || message == null)) {
                message = message + " : " + Helper.getStackTraceString(throwable);
            }
            if (throwable != null && message == null) {
                message = Helper.getStackTraceString(throwable);
            }
            if (message == null) {
                message = "No message/exception is set";
            }
            int methodCount = getMethodCount();
            if (Helper.isEmpty(message)) {
                message = "Empty/NULL log message";
            }
            logTopBorder(priority, tag2);
            logHeaderContent(priority, tag2, methodCount);
            byte[] bytes = message.getBytes();
            int length = bytes.length;
            if (length <= 4000) {
                if (methodCount > 0) {
                    logDivider(priority, tag2);
                }
                logContent(priority, tag2, message);
                logBottomBorder(priority, tag2);
            } else {
                if (methodCount > 0) {
                    logDivider(priority, tag2);
                }
                for (int i = 0; i < length; i += 4000) {
                    logContent(priority, tag2, new String(bytes, i, Math.min(length - i, 4000)));
                }
                logBottomBorder(priority, tag2);
            }
        }
    }

    public void resetSettings() {
        this.settings.reset();
    }

    private synchronized void log(int priority, Throwable throwable, String msg, Object... args) {
        if (this.settings.getLogLevel() != LogLevel.NONE) {
            log(priority, getTag(), createMessage(msg, args), throwable);
        }
    }

    private void logTopBorder(int logType, String tag2) {
        logChunk(logType, tag2, TOP_BORDER);
    }

    private void logHeaderContent(int logType, String tag2, int methodCount) {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        if (this.settings.isShowThreadInfo()) {
            logChunk(logType, tag2, "║ Thread: " + Thread.currentThread().getName());
            logDivider(logType, tag2);
        }
        String level = "";
        int stackOffset = getStackOffset(trace) + this.settings.getMethodOffset();
        if (methodCount + stackOffset > trace.length) {
            methodCount = (trace.length - stackOffset) - 1;
        }
        for (int i = methodCount; i > 0; i--) {
            int stackIndex = i + stackOffset;
            if (stackIndex < trace.length) {
                StringBuilder builder = new StringBuilder();
                builder.append("║ ").append(level).append(getSimpleClassName(trace[stackIndex].getClassName())).append(".").append(trace[stackIndex].getMethodName()).append(" ").append(" (").append(trace[stackIndex].getFileName()).append(":").append(trace[stackIndex].getLineNumber()).append(")");
                level = level + "   ";
                logChunk(logType, tag2, builder.toString());
            }
        }
    }

    private void logBottomBorder(int logType, String tag2) {
        logChunk(logType, tag2, BOTTOM_BORDER);
    }

    private void logDivider(int logType, String tag2) {
        logChunk(logType, tag2, MIDDLE_BORDER);
    }

    private void logContent(int logType, String tag2, String chunk) {
        String[] lines = chunk.split(System.getProperty("line.separator"));
        int length = lines.length;
        for (int i = 0; i < length; i++) {
            logChunk(logType, tag2, "║ " + lines[i]);
        }
    }

    private void logChunk(int logType, String tag2, String chunk) {
        String finalTag = formatTag(tag2);
        switch (logType) {
            case 2:
                this.settings.getLogAdapter().v(finalTag, chunk);
                return;
            case 4:
                this.settings.getLogAdapter().i(finalTag, chunk);
                return;
            case 5:
                this.settings.getLogAdapter().w(finalTag, chunk);
                return;
            case 6:
                this.settings.getLogAdapter().e(finalTag, chunk);
                return;
            case 7:
                this.settings.getLogAdapter().wtf(finalTag, chunk);
                return;
            default:
                this.settings.getLogAdapter().d(finalTag, chunk);
                return;
        }
    }

    private String getSimpleClassName(String name) {
        return name.substring(name.lastIndexOf(".") + 1);
    }

    private String formatTag(String tag2) {
        if (Helper.isEmpty(tag2) || Helper.equals(this.tag, tag2)) {
            return this.tag;
        }
        return this.tag + "-" + tag2;
    }

    private String getTag() {
        String tag2 = this.localTag.get();
        if (tag2 == null) {
            return this.tag;
        }
        this.localTag.remove();
        return tag2;
    }

    private String createMessage(String message, Object... args) {
        return (args == null || args.length == 0) ? message : String.format(message, args);
    }

    private int getMethodCount() {
        Integer count = this.localMethodCount.get();
        int result = this.settings.getMethodCount();
        if (count != null) {
            this.localMethodCount.remove();
            result = count.intValue();
        }
        if (result >= 0) {
            return result;
        }
        throw new IllegalStateException("methodCount cannot be negative");
    }

    private int getStackOffset(StackTraceElement[] trace) {
        for (int i = 3; i < trace.length; i++) {
            String name = trace[i].getClassName();
            if (!name.equals(LoggerPrinter.class.getName()) && !name.equals(Logger.class.getName())) {
                return i - 1;
            }
        }
        return -1;
    }
}
