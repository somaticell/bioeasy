package bolts;

import java.util.ArrayList;
import java.util.List;

public class AggregateException extends Exception {
    private static final long serialVersionUID = 1;
    private Throwable[] causes;

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public AggregateException(java.lang.String r3, java.lang.Throwable[] r4) {
        /*
            r2 = this;
            r1 = 0
            if (r4 == 0) goto L_0x0014
            int r0 = r4.length
            if (r0 <= 0) goto L_0x0014
            r0 = 0
            r0 = r4[r0]
        L_0x0009:
            r2.<init>(r3, r0)
            if (r4 == 0) goto L_0x0016
            int r0 = r4.length
            if (r0 <= 0) goto L_0x0016
        L_0x0011:
            r2.causes = r4
            return
        L_0x0014:
            r0 = r1
            goto L_0x0009
        L_0x0016:
            r4 = r1
            goto L_0x0011
        */
        throw new UnsupportedOperationException("Method not decompiled: bolts.AggregateException.<init>(java.lang.String, java.lang.Throwable[]):void");
    }

    @Deprecated
    public AggregateException(List<Exception> errors) {
        this("There were multiple errors.", (Throwable[]) errors.toArray(new Exception[errors.size()]));
    }

    @Deprecated
    public List<Exception> getErrors() {
        List<Exception> errors = new ArrayList<>();
        if (this.causes != null) {
            for (Throwable cause : this.causes) {
                if (cause instanceof Exception) {
                    errors.add((Exception) cause);
                } else {
                    errors.add(new Exception(cause));
                }
            }
        }
        return errors;
    }

    public Throwable[] getCauses() {
        return this.causes;
    }
}
