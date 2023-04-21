package jxl.biff.formula;

import cn.sharesdk.framework.d;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Array;
import jxl.biff.WorkbookMethods;

class Yylex {
    private final int YYINITIAL;
    private final int YYSTRING;
    private final int YY_BOL;
    private final int YY_BUFFER_SIZE;
    private final int YY_END;
    private final int YY_EOF;
    private final int YY_E_INTERNAL;
    private final int YY_E_MATCH;
    private final int YY_F;
    private final int YY_NOT_ACCEPT;
    private final int YY_NO_ANCHOR;
    private final int YY_NO_STATE;
    private final int YY_START;
    private boolean emptyString;
    private ExternalSheet externalSheet;
    private WorkbookMethods nameTable;
    private int[] yy_acpt;
    private boolean yy_at_bol;
    private char[] yy_buffer;
    private int yy_buffer_end;
    private int yy_buffer_index;
    private int yy_buffer_read;
    private int yy_buffer_start;
    private int[] yy_cmap;
    private boolean yy_eof_done;
    private String[] yy_error_string;
    private boolean yy_last_was_cr;
    private int yy_lexical_state;
    private int[][] yy_nxt;
    private BufferedReader yy_reader;
    private int[] yy_rmap;
    private final int[] yy_state_dtrans;
    private int yychar;
    private int yyline;

    /* access modifiers changed from: package-private */
    public int getPos() {
        return this.yychar;
    }

    /* access modifiers changed from: package-private */
    public void setExternalSheet(ExternalSheet es) {
        this.externalSheet = es;
    }

    /* access modifiers changed from: package-private */
    public void setNameTable(WorkbookMethods nt) {
        this.nameTable = nt;
    }

    Yylex(Reader reader) {
        this();
        if (reader == null) {
            throw new Error("Error: Bad input stream initializer.");
        }
        this.yy_reader = new BufferedReader(reader);
    }

    Yylex(InputStream instream) {
        this();
        if (instream == null) {
            throw new Error("Error: Bad input stream initializer.");
        }
        this.yy_reader = new BufferedReader(new InputStreamReader(instream));
    }

    private Yylex() {
        this.YY_BUFFER_SIZE = 512;
        this.YY_F = -1;
        this.YY_NO_STATE = -1;
        this.YY_NOT_ACCEPT = 0;
        this.YY_START = 1;
        this.YY_END = 2;
        this.YY_NO_ANCHOR = 4;
        this.YY_BOL = 65536;
        this.YY_EOF = 65537;
        this.yy_eof_done = false;
        this.YYSTRING = 1;
        this.YYINITIAL = 0;
        this.yy_state_dtrans = new int[]{0, 28};
        this.yy_last_was_cr = false;
        this.YY_E_INTERNAL = 0;
        this.YY_E_MATCH = 1;
        this.yy_error_string = new String[]{"Error: Internal error.\n", "Error: Unmatched input.\n"};
        this.yy_acpt = new int[]{0, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 0, 4, 4, 4, 4, 4, 0, 4, 0, 4, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 4, 0, 0, 4, 0};
        this.yy_cmap = unpackFromString(1, 65538, "29:8,14:3,29:21,14,16,28,15,11,15:2,13,26,27,3,1,8,2,10,4,9:10,17,15,7,6,5,15:2,23,12:3,21,22,12:5,24,12:5,19,25,18,20,12:5,15:5,29,12:26,29,15,29,15,29:65409,0:2")[0];
        this.yy_rmap = unpackFromString(1, 77, "0,1,2,1,2:2,3,2,4,2,5,2:2,1:3,2:3,6,7,1,8,9,10,11,10,12,13,1,14,15,10,16,17,18,19,20,21,22,23,24,8,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,11,42,43,44,45,46,47,12,48,17,49,50,51,2,52,53,54")[0];
        this.yy_nxt = unpackFromString(55, 30, "1,2,3,4,5,6,7,8,9,10,30,36,30,38,11,73:2,12,71,30:3,76,30:3,13,14,15,-1:32,73,-1,73:10,40,73:2,74,73:9,-1:5,73,-1,73:3,16,73:6,40,73:2,74,73:9,-1:5,73,-1,73:2,17,18,73:6,40,73:2,74,73:9,-1:5,73,-1,73:6,10,42,73:2,40,73:2,74,73:9,-1:5,73,-1,73:6,19,73:3,40,73:2,74,48,73:8,-1:5,73,-1,73:6,31,32,43,32,40,73:2,74,73,32:8,21,-1:4,73,-1,73:6,22,73:3,40,73:2,74,73:9,-1:5,73,-1,73:6,23,73:3,40,73:2,74,58,73:8,-1:5,73,-1,73:6,39,32,73,32,40,73:2,74,73,32:8,21,-1:4,73,-1,73:6,25,73:3,40,73:2,74,73:9,-1:5,73,-1,73:6,27,73:3,40,73:2,74,73:9,-1:4,1,35:27,29,35,-1,73,-1,73:6,19,20,43,20,40,73:2,74,73,20:8,21,-1:4,73,-1,73:6,31,39,73,39,40,73:2,74,48,39:8,-1:13,33,-1:7,61,-1:21,34,-1:21,35:27,-1,35,-1,73,-1,73:7,44,73,44,40,73:2,74,73,44:8,-1:5,73,-1,73:6,39,32,73,32,40,73:2,74,73,32:3,24,32:4,21,-1:4,73,-1,73:10,-1,73:12,-1:5,73,-1,73:6,39:2,73,39,40,73:2,74,73,39:8,-1:20,45,-1:14,73,-1,73:6,39,32,73,32,40,73:2,74,73,32:3,26,32:4,21,-1:4,73,-1,73:6,19,73:3,40,73:2,74,73:9,-1:5,73,-1,73:6,19,49,43,49,40,73:2,74,73,49:8,-1:14,50,51,50,-1:5,50:8,-1:5,73,-1,73:6,23,52,53,52,40,73:2,74,73,52:8,-1:5,73,-1,73:7,46,73,46,40,73:2,74,73,46:8,-1:5,73,-1,73:7,54,55,54,40,73:2,74,73,54:8,-1:5,73,-1,73:6,19,73,43,73,40,73:2,74,73:9,-1:13,33,56,57,56,-1:5,56:8,-1:14,50,-1,50,-1:5,50:8,-1:5,73,-1,73:6,23,73,53,73,40,73:2,74,73:9,-1:5,73,-1,73:6,23,73:3,40,73:2,74,73:9,-1:5,73,-1,73:6,25,59,60,59,40,73:2,74,73,59:8,-1:5,73,-1,73:7,54,73,54,40,73:2,74,73,54:8,-1:13,33,-1,57,-1:27,33,-1:21,73,-1,73:7,62,63,62,40,73:2,74,73,62:8,-1:5,73,-1,73:6,25,73,60,73,40,73:2,74,73:9,-1:14,64,65,64,-1:5,64:8,-1:5,73,-1,73:6,27,66,67,66,40,73:2,74,73,66:8,-1:5,73,-1,73:7,62,73,62,40,73:2,74,73,62:8,-1:13,34,68,69,68,-1:5,68:8,-1:14,64,-1,64,-1:5,64:8,-1:5,73,-1,73:6,27,73,67,73,40,73:2,74,73:9,-1:13,34,-1,69,-1:19,73,-1,73:6,31,32,43,32,40,73:2,74,73,32:2,37,32:5,21,-1:4,73,-1,73:6,19,20,43,20,40,73:2,74,73,20,70,20:6,21,-1:4,73,-1,73:6,39,32,73,32,40,73:2,74,73,32:7,41,21,-1:4,73,-1,73:7,46,47,46,40,73:2,74,73,46:8,-1:5,73,-1,73:6,31,32,43,32,40,73:2,74,73,32:6,72,32,21,-1:4,73,-1,73:6,19,20,43,20,40,73:2,74,73,20:5,75,20:2,21,-1:3");
        this.yy_buffer = new char[512];
        this.yy_buffer_read = 0;
        this.yy_buffer_index = 0;
        this.yy_buffer_start = 0;
        this.yy_buffer_end = 0;
        this.yychar = 0;
        this.yyline = 0;
        this.yy_at_bol = true;
        this.yy_lexical_state = 0;
    }

    private void yybegin(int state) {
        this.yy_lexical_state = state;
    }

    private int yy_advance() throws IOException {
        if (this.yy_buffer_index < this.yy_buffer_read) {
            char[] cArr = this.yy_buffer;
            int i = this.yy_buffer_index;
            this.yy_buffer_index = i + 1;
            return cArr[i];
        }
        if (this.yy_buffer_start != 0) {
            int i2 = this.yy_buffer_start;
            int j = 0;
            while (i2 < this.yy_buffer_read) {
                this.yy_buffer[j] = this.yy_buffer[i2];
                i2++;
                j++;
            }
            this.yy_buffer_end -= this.yy_buffer_start;
            this.yy_buffer_start = 0;
            this.yy_buffer_read = j;
            this.yy_buffer_index = j;
            int next_read = this.yy_reader.read(this.yy_buffer, this.yy_buffer_read, this.yy_buffer.length - this.yy_buffer_read);
            if (-1 == next_read) {
                return 65537;
            }
            this.yy_buffer_read += next_read;
        }
        while (this.yy_buffer_index >= this.yy_buffer_read) {
            if (this.yy_buffer_index >= this.yy_buffer.length) {
                this.yy_buffer = yy_double(this.yy_buffer);
            }
            int next_read2 = this.yy_reader.read(this.yy_buffer, this.yy_buffer_read, this.yy_buffer.length - this.yy_buffer_read);
            if (-1 == next_read2) {
                return 65537;
            }
            this.yy_buffer_read += next_read2;
        }
        char[] cArr2 = this.yy_buffer;
        int i3 = this.yy_buffer_index;
        this.yy_buffer_index = i3 + 1;
        return cArr2[i3];
    }

    private void yy_move_end() {
        if (this.yy_buffer_end > this.yy_buffer_start && 10 == this.yy_buffer[this.yy_buffer_end - 1]) {
            this.yy_buffer_end--;
        }
        if (this.yy_buffer_end > this.yy_buffer_start && 13 == this.yy_buffer[this.yy_buffer_end - 1]) {
            this.yy_buffer_end--;
        }
    }

    private void yy_mark_start() {
        for (int i = this.yy_buffer_start; i < this.yy_buffer_index; i++) {
            if (10 == this.yy_buffer[i] && !this.yy_last_was_cr) {
                this.yyline++;
            }
            if (13 == this.yy_buffer[i]) {
                this.yyline++;
                this.yy_last_was_cr = true;
            } else {
                this.yy_last_was_cr = false;
            }
        }
        this.yychar = (this.yychar + this.yy_buffer_index) - this.yy_buffer_start;
        this.yy_buffer_start = this.yy_buffer_index;
    }

    private void yy_mark_end() {
        this.yy_buffer_end = this.yy_buffer_index;
    }

    private void yy_to_mark() {
        this.yy_buffer_index = this.yy_buffer_end;
        this.yy_at_bol = this.yy_buffer_end > this.yy_buffer_start && (13 == this.yy_buffer[this.yy_buffer_end + -1] || 10 == this.yy_buffer[this.yy_buffer_end + -1] || 2028 == this.yy_buffer[this.yy_buffer_end + -1] || 2029 == this.yy_buffer[this.yy_buffer_end + -1]);
    }

    private String yytext() {
        return new String(this.yy_buffer, this.yy_buffer_start, this.yy_buffer_end - this.yy_buffer_start);
    }

    private int yylength() {
        return this.yy_buffer_end - this.yy_buffer_start;
    }

    private char[] yy_double(char[] buf) {
        char[] newbuf = new char[(buf.length * 2)];
        for (int i = 0; i < buf.length; i++) {
            newbuf[i] = buf[i];
        }
        return newbuf;
    }

    private void yy_error(int code, boolean fatal) {
        System.out.print(this.yy_error_string[code]);
        System.out.flush();
        if (fatal) {
            throw new Error("Fatal Error.\n");
        }
    }

    private int[][] unpackFromString(int size1, int size2, String st) {
        int sequenceLength = 0;
        int sequenceInteger = 0;
        int[][] res = (int[][]) Array.newInstance(Integer.TYPE, new int[]{size1, size2});
        for (int i = 0; i < size1; i++) {
            for (int j = 0; j < size2; j++) {
                if (sequenceLength != 0) {
                    res[i][j] = sequenceInteger;
                    sequenceLength--;
                } else {
                    int commaIndex = st.indexOf(44);
                    String workString = commaIndex == -1 ? st : st.substring(0, commaIndex);
                    st = st.substring(commaIndex + 1);
                    int colonIndex = workString.indexOf(58);
                    if (colonIndex == -1) {
                        res[i][j] = Integer.parseInt(workString);
                    } else {
                        int sequenceLength2 = Integer.parseInt(workString.substring(colonIndex + 1));
                        sequenceInteger = Integer.parseInt(workString.substring(0, colonIndex));
                        res[i][j] = sequenceInteger;
                        sequenceLength = sequenceLength2 - 1;
                    }
                }
            }
        }
        return res;
    }

    public ParseItem yylex() throws IOException, FormulaException {
        int yy_lookahead;
        int yy_state = this.yy_state_dtrans[this.yy_lexical_state];
        int yy_last_accept_state = -1;
        boolean yy_initial = true;
        yy_mark_start();
        if (this.yy_acpt[yy_state] != 0) {
            yy_last_accept_state = yy_state;
            yy_mark_end();
        }
        while (true) {
            if (!yy_initial || !this.yy_at_bol) {
                yy_lookahead = yy_advance();
            } else {
                yy_lookahead = 65536;
            }
            int yy_next_state = this.yy_nxt[this.yy_rmap[yy_state]][this.yy_cmap[yy_lookahead]];
            if (65537 == yy_lookahead && true == yy_initial) {
                return null;
            }
            if (-1 != yy_next_state) {
                yy_state = yy_next_state;
                yy_initial = false;
                if (this.yy_acpt[yy_state] != 0) {
                    yy_last_accept_state = yy_state;
                    yy_mark_end();
                }
            } else if (-1 == yy_last_accept_state) {
                throw new Error("Lexical Error: Unmatched Input.");
            } else {
                if ((this.yy_acpt[yy_last_accept_state] & 2) != 0) {
                    yy_move_end();
                }
                yy_to_mark();
                switch (yy_last_accept_state) {
                    case -41:
                    case -40:
                    case -39:
                    case -38:
                    case -37:
                    case -36:
                    case -35:
                    case -34:
                    case -33:
                    case -32:
                    case -31:
                    case -30:
                    case -29:
                    case -28:
                    case -27:
                    case -26:
                    case -25:
                    case -24:
                    case -23:
                    case -22:
                    case -21:
                    case -20:
                    case -19:
                    case -18:
                    case -17:
                    case -16:
                    case d.ERROR_TOO_MANY_REQUESTS:
                    case d.ERROR_FILE_NOT_FOUND:
                    case d.ERROR_FILE:
                    case d.ERROR_BAD_URL:
                    case -11:
                    case -10:
                    case d.ERROR_REDIRECT_LOOP:
                    case d.ERROR_TIMEOUT:
                    case d.ERROR_IO:
                    case -6:
                    case -5:
                    case -4:
                    case -3:
                    case -2:
                    case -1:
                    case 1:
                    case 11:
                        break;
                    case 2:
                        return new Plus();
                    case 3:
                        return new Minus();
                    case 4:
                        return new Multiply();
                    case 5:
                        return new Divide();
                    case 6:
                        return new GreaterThan();
                    case 7:
                        return new Equal();
                    case 8:
                        return new LessThan();
                    case 9:
                        return new ArgumentSeparator();
                    case 10:
                        return new IntegerValue(yytext());
                    case 12:
                        return new RangeSeparator();
                    case 13:
                        return new OpenParentheses();
                    case 14:
                        return new CloseParentheses();
                    case 15:
                        this.emptyString = true;
                        yybegin(1);
                        break;
                    case 16:
                        return new GreaterEqual();
                    case 17:
                        return new NotEqual();
                    case 18:
                        return new LessEqual();
                    case 19:
                        return new CellReference(yytext());
                    case 20:
                        return new NameRange(yytext(), this.nameTable);
                    case 21:
                        return new StringFunction(yytext());
                    case 22:
                        return new DoubleValue(yytext());
                    case 23:
                        return new CellReference3d(yytext(), this.externalSheet);
                    case 24:
                        return new BooleanValue(yytext());
                    case 25:
                        return new Area(yytext());
                    case 26:
                        return new BooleanValue(yytext());
                    case 27:
                        return new Area3d(yytext(), this.externalSheet);
                    case 28:
                        this.emptyString = false;
                        return new StringValue(yytext());
                    case 29:
                        yybegin(0);
                        if (this.emptyString) {
                            return new StringValue("");
                        }
                        break;
                    case 31:
                        return new CellReference(yytext());
                    case 32:
                        return new NameRange(yytext(), this.nameTable);
                    case 33:
                        return new CellReference3d(yytext(), this.externalSheet);
                    case 34:
                        return new Area3d(yytext(), this.externalSheet);
                    case 35:
                        this.emptyString = false;
                        return new StringValue(yytext());
                    case 37:
                        return new NameRange(yytext(), this.nameTable);
                    case 39:
                        return new NameRange(yytext(), this.nameTable);
                    case 41:
                        return new NameRange(yytext(), this.nameTable);
                    case 70:
                        return new NameRange(yytext(), this.nameTable);
                    case 72:
                        return new NameRange(yytext(), this.nameTable);
                    case 75:
                        return new NameRange(yytext(), this.nameTable);
                    default:
                        yy_error(0, false);
                        break;
                }
                yy_initial = true;
                yy_state = this.yy_state_dtrans[this.yy_lexical_state];
                yy_last_accept_state = -1;
                yy_mark_start();
                if (this.yy_acpt[yy_state] != 0) {
                    yy_last_accept_state = yy_state;
                    yy_mark_end();
                }
            }
        }
    }
}
