package okhttp3.internal.http;

import android.support.v7.widget.ActivityChooserView;

public final class HeaderParser {
    public static int skipUntil(String input, int pos, String characters) {
        while (pos < input.length() && characters.indexOf(input.charAt(pos)) == -1) {
            pos++;
        }
        return pos;
    }

    public static int skipWhitespace(String input, int pos) {
        while (pos < input.length() && ((c = input.charAt(pos)) == ' ' || c == 9)) {
            pos++;
        }
        return pos;
    }

    public static int parseSeconds(String value, int defaultValue) {
        try {
            long seconds = Long.parseLong(value);
            if (seconds > 2147483647L) {
                return ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
            }
            if (seconds < 0) {
                return 0;
            }
            return (int) seconds;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private HeaderParser() {
    }
}
