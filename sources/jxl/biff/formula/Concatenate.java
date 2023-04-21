package jxl.biff.formula;

import com.alipay.sdk.sys.a;

class Concatenate extends BinaryOperator implements ParsedThing {
    public String getSymbol() {
        return a.b;
    }

    /* access modifiers changed from: package-private */
    public Token getToken() {
        return Token.CONCAT;
    }

    /* access modifiers changed from: package-private */
    public int getPrecedence() {
        return 3;
    }
}
