package net.oschina.common.widget;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.widget.TextView;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.oschina.common.adapter.TextWatcherAdapter;
import net.oschina.common.utils.Logger;

public class RichEditText extends AppCompatEditText {
    public static boolean DEBUG = false;
    public static final String MATCH_MENTION = "@([^@^\\s^:^,^;^'，'^'；'^>^<]{1,})";
    public static final String MATCH_TOPIC = "#.+?#";
    private static final String TAG = RichEditText.class.getName();
    private OnKeyArrivedListener mOnKeyArrivedListener;
    /* access modifiers changed from: private */
    public final TagSpanTextWatcher mTagSpanTextWatcher = new TagSpanTextWatcher();

    public interface OnKeyArrivedListener {
        boolean onMentionKeyArrived(RichEditText richEditText);

        boolean onTopicKeyArrived(RichEditText richEditText);
    }

    public RichEditText(Context context) {
        super(context);
        init();
    }

    public RichEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RichEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        addTextChangedListener(this.mTagSpanTextWatcher);
    }

    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        return new ZanyInputConnection(super.onCreateInputConnection(outAttrs), true);
    }

    public void setText(CharSequence text, TextView.BufferType type) {
        super.setText(matchTopic(matchMention(new SpannableString(text))), type);
    }

    /* access modifiers changed from: protected */
    public void onSelectionChanged(int selStart, int selEnd) {
        log("onSelectionChanged:" + selStart + " " + selEnd);
        Editable message = getText();
        if (selStart == selEnd) {
            TagSpan[] list = (TagSpan[]) message.getSpans(selStart - 1, selStart, TagSpan.class);
            if (list.length > 0) {
                TagSpan span = list[0];
                int spanStart = message.getSpanStart(span);
                int spanEnd = message.getSpanEnd(span);
                log("onSelectionChanged#Yes:" + spanStart + " " + spanEnd);
                if (Math.abs(selStart - spanStart) > Math.abs(selStart - spanEnd)) {
                    Selection.setSelection(message, spanEnd);
                    replaceCacheTagSpan(message, span, false);
                    return;
                }
                Selection.setSelection(message, spanStart);
            }
        } else {
            TagSpan[] list2 = (TagSpan[]) message.getSpans(selStart, selEnd, TagSpan.class);
            if (list2.length != 0) {
                int start = selStart;
                int end = selEnd;
                for (TagSpan span2 : list2) {
                    int spanStart2 = message.getSpanStart(span2);
                    int spanEnd2 = message.getSpanEnd(span2);
                    if (spanStart2 < start) {
                        start = spanStart2;
                    }
                    if (spanEnd2 > end) {
                        end = spanEnd2;
                    }
                }
                if (!(start == selStart && end == selEnd)) {
                    Selection.setSelection(message, start, end);
                    log("onSelectionChanged#No:" + start + " " + end);
                }
            } else {
                return;
            }
        }
        replaceCacheTagSpan(message, (TagSpan) null, false);
    }

    public boolean onTextContextMenuItem(int id) {
        ClipData.Item item;
        if (id == 16908322) {
            ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService("clipboard");
            if (clipboard.hasPrimaryClip() && (item = clipboard.getPrimaryClip().getItemAt(0)) != null) {
                String paste = item.coerceToText(getContext()).toString().trim();
                if (this.mTagSpanTextWatcher != null && this.mTagSpanTextWatcher.checkCommit(paste)) {
                    paste = " " + paste;
                }
                getText().replace(getSelectionStart(), getSelectionEnd(), matchTopic(matchMention(new SpannableString(paste))));
                return true;
            }
        }
        return super.onTextContextMenuItem(id);
    }

    public void setOnKeyArrivedListener(OnKeyArrivedListener listener) {
        this.mOnKeyArrivedListener = listener;
    }

    /* access modifiers changed from: protected */
    public boolean callToMention() {
        OnKeyArrivedListener listener = this.mOnKeyArrivedListener;
        return listener == null || listener.onMentionKeyArrived(this);
    }

    /* access modifiers changed from: protected */
    public boolean callToTopic() {
        OnKeyArrivedListener listener = this.mOnKeyArrivedListener;
        return listener == null || listener.onTopicKeyArrived(this);
    }

    private void replaceCacheTagSpan(Editable message, TagSpan span, boolean targetDelState) {
        if (this.mTagSpanTextWatcher != null) {
            this.mTagSpanTextWatcher.replaceSpan(message, span, targetDelState);
        }
    }

    private String filterDirty(String str) {
        return str.replace("#", "").replace("@", "").replace(" ", "");
    }

    private void replaceLastChar(@NonNull String chr, SpannableString spannable) {
        Editable msg = getText();
        int selStart = getSelectionStart();
        int selEnd = getSelectionEnd();
        int selStartBefore = selStart - 1;
        if (selStart == selEnd && selStart > 0 && chr.equals(msg.subSequence(selStartBefore, selEnd).toString()) && ((TagSpan[]) msg.getSpans(selStartBefore, selEnd, TagSpan.class)).length == 0) {
            selStart = selStartBefore;
        }
        if (selStart < 0) {
            selStart = 0;
        }
        if (selEnd < 0) {
            selEnd = 0;
        }
        msg.replace(selStart, selEnd, spannable);
    }

    public void appendMention(String... mentions) {
        if (mentions != null && mentions.length != 0) {
            String mentionStr = "";
            for (String mention : mentions) {
                if (mention != null) {
                    String mention2 = mention.trim();
                    if (!TextUtils.isEmpty(mention2)) {
                        String mention3 = filterDirty(mention2);
                        if (!TextUtils.isEmpty(mention3)) {
                            mentionStr = mentionStr + String.format("@%s ", new Object[]{mention3});
                        }
                    }
                }
            }
            if (!TextUtils.isEmpty(mentionStr)) {
                SpannableString spannable = new SpannableString(mentionStr);
                matchMention(spannable);
                replaceLastChar("@", spannable);
            }
        }
    }

    public void appendTopic(String... topics) {
        if (topics != null && topics.length != 0) {
            String topicStr = "";
            for (String topic : topics) {
                if (topic != null) {
                    String topic2 = topic.trim();
                    if (!TextUtils.isEmpty(topic2)) {
                        String topic3 = filterDirty(topic2);
                        if (!TextUtils.isEmpty(topic3)) {
                            topicStr = topicStr + String.format("#%s# ", new Object[]{topic3});
                        }
                    }
                }
            }
            if (!TextUtils.isEmpty(topicStr)) {
                SpannableString spannable = new SpannableString(topicStr);
                matchTopic(spannable);
                replaceLastChar("#", spannable);
            }
        }
    }

    private class ZanyInputConnection extends InputConnectionWrapper {
        ZanyInputConnection(InputConnection target, boolean mutable) {
            super(target, mutable);
        }

        public boolean sendKeyEvent(KeyEvent event) {
            if (event.getAction() == 0 && event.getKeyCode() == 67 && !RichEditText.this.mTagSpanTextWatcher.checkKeyDel()) {
                return false;
            }
            return super.sendKeyEvent(event);
        }

        public boolean deleteSurroundingText(int beforeLength, int afterLength) {
            if (beforeLength != 1 || afterLength != 0) {
                return super.deleteSurroundingText(beforeLength, afterLength);
            }
            if (!sendKeyEvent(new KeyEvent(0, 67)) || !sendKeyEvent(new KeyEvent(1, 67))) {
                return false;
            }
            return true;
        }

        public boolean commitText(CharSequence text, int newCursorPosition) {
            return checkCommitWithCacheTagSpan(text) && super.commitText(text, newCursorPosition);
        }

        public boolean setComposingText(CharSequence text, int newCursorPosition) {
            return checkCommitWithCacheTagSpan(text) && super.setComposingText(text, newCursorPosition);
        }

        private boolean checkCommitWithCacheTagSpan(CharSequence text) {
            if ("@".equals(text)) {
                return RichEditText.this.callToMention();
            }
            if ("#".equals(text)) {
                return RichEditText.this.callToTopic();
            }
            if (!RichEditText.this.mTagSpanTextWatcher.checkCommit(text)) {
                return true;
            }
            super.commitText(" ", 1);
            return true;
        }
    }

    private class TagSpanTextWatcher extends TextWatcherAdapter {
        private TagSpan willDelSpan;

        private TagSpanTextWatcher() {
        }

        /* access modifiers changed from: package-private */
        public void replaceSpan(Editable message, TagSpan span, boolean targetDelState) {
            if (span != null) {
                span.changeRemoveState(targetDelState, message);
            }
            if (this.willDelSpan != span) {
                TagSpan cacheSpan = this.willDelSpan;
                if (cacheSpan != null) {
                    cacheSpan.changeRemoveState(false, message);
                }
                this.willDelSpan = span;
            }
        }

        /* access modifiers changed from: package-private */
        public boolean checkKeyDel() {
            TagSpan span;
            int selStart = RichEditText.this.getSelectionStart();
            int selEnd = RichEditText.this.getSelectionEnd();
            Editable message = RichEditText.this.getText();
            RichEditText.log("TagSpanTextWatcher#checkKeyDel:" + selStart + " " + selEnd);
            if (selStart == selEnd) {
                int start = selStart - 1;
                if (start < 0) {
                    start = 0;
                }
                TagSpan[] list = (TagSpan[]) message.getSpans(start, start + 1, TagSpan.class);
                if (list.length > 0 && (span = list[0]) == this.willDelSpan) {
                    if (span.willRemove) {
                        return true;
                    }
                    span.changeRemoveState(true, message);
                    return false;
                }
            }
            replaceSpan(message, (TagSpan) null, false);
            return true;
        }

        /* access modifiers changed from: package-private */
        public boolean checkCommit(CharSequence s) {
            if (this.willDelSpan == null) {
                return false;
            }
            this.willDelSpan.willRemove = false;
            this.willDelSpan = null;
            if (s == null || s.length() <= 0 || " ".equals(s.subSequence(0, 1))) {
                return false;
            }
            return true;
        }

        public void afterTextChanged(Editable s) {
            TagSpan span = this.willDelSpan;
            RichEditText.log("TagSpanTextWatcher#willRemove#span:" + (span == null ? "null" : span.toString()));
            if (span != null && span.willRemove) {
                int start = s.getSpanStart(span);
                int end = s.getSpanEnd(span);
                s.removeSpan(span);
                if (start != end) {
                    s.delete(start, end);
                }
            }
        }
    }

    public static Spannable matchMention(Spannable spannable) {
        Matcher matcher = Pattern.compile(MATCH_MENTION).matcher(spannable.toString());
        while (matcher.find()) {
            String str = matcher.group();
            int matcherStart = matcher.start();
            int matcherEnd = matcher.end();
            spannable.setSpan(new TagSpan(str), matcherStart, matcherEnd, 33);
            log("matchMention:" + str + " " + matcherStart + " " + matcherEnd);
        }
        return spannable;
    }

    public static Spannable matchTopic(Spannable spannable) {
        Matcher matcher = Pattern.compile(MATCH_TOPIC).matcher(spannable.toString());
        while (matcher.find()) {
            String str = matcher.group();
            int matcherStart = matcher.start();
            int matcherEnd = matcher.end();
            spannable.setSpan(new TagSpan(str), matcherStart, matcherEnd, 33);
            log("matchTopic:" + str + " " + matcherStart + " " + matcherEnd);
        }
        return spannable;
    }

    /* access modifiers changed from: private */
    public static void log(String msg) {
        if (DEBUG) {
            Logger.e(TAG, msg);
        }
    }

    public static class TagSpan extends ForegroundColorSpan implements Parcelable {
        public static final Parcelable.Creator<TagSpan> CREATOR = new Parcelable.Creator<TagSpan>() {
            public TagSpan createFromParcel(Parcel in) {
                return new TagSpan(in);
            }

            public TagSpan[] newArray(int size) {
                return new TagSpan[size];
            }
        };
        private String value;
        public boolean willRemove;

        public TagSpan(String value2) {
            super(-14364833);
            this.value = value2;
        }

        public TagSpan(Parcel src) {
            super(src);
            this.value = src.readString();
        }

        public int describeContents() {
            return 0;
        }

        public void updateDrawState(TextPaint ds) {
            ds.setFakeBoldText(true);
            if (this.willRemove) {
                ds.setColor(-1);
                ds.bgColor = -14364833;
                return;
            }
            super.updateDrawState(ds);
        }

        /* access modifiers changed from: package-private */
        public void changeRemoveState(boolean willRemove2, Editable message) {
            if (this.willRemove != willRemove2) {
                this.willRemove = willRemove2;
                int cacheSpanStart = message.getSpanStart(this);
                int cacheSpanEnd = message.getSpanEnd(this);
                if (cacheSpanStart >= 0 && cacheSpanEnd >= cacheSpanStart) {
                    message.setSpan(this, cacheSpanStart, cacheSpanEnd, 33);
                }
            }
        }

        public String getValue() {
            return this.value;
        }

        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeString(this.value);
        }

        public String toString() {
            return "TagSpan{value='" + this.value + '\'' + ", willRemove=" + this.willRemove + '}';
        }
    }
}
