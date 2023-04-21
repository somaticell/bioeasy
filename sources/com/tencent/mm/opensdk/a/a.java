package com.tencent.mm.opensdk.a;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.tencent.mm.opensdk.a.a.b;
import com.tencent.mm.opensdk.b.d;
import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.constants.ConstantsAPI;

public final class a {

    /* renamed from: com.tencent.mm.opensdk.a.a$a  reason: collision with other inner class name */
    public static class C0025a {
        public String a;
        public String b;
        public String c;
        public Bundle d;
        public int flags = -1;

        public final String toString() {
            return "targetPkgName:" + this.a + ", targetClassName:" + this.b + ", content:" + this.c + ", flags:" + this.flags + ", bundle:" + this.d;
        }
    }

    public static boolean a(Context context, C0025a aVar) {
        if (context == null) {
            Log.e("MicroMsg.SDK.MMessageAct", "send fail, invalid argument");
            return false;
        } else if (d.a(aVar.a)) {
            Log.e("MicroMsg.SDK.MMessageAct", "send fail, invalid targetPkgName, targetPkgName = " + aVar.a);
            return false;
        } else {
            if (d.a(aVar.b)) {
                aVar.b = aVar.a + ".wxapi.WXEntryActivity";
            }
            Log.d("MicroMsg.SDK.MMessageAct", "send, targetPkgName = " + aVar.a + ", targetClassName = " + aVar.b);
            Intent intent = new Intent();
            intent.setClassName(aVar.a, aVar.b);
            if (aVar.d != null) {
                intent.putExtras(aVar.d);
            }
            String packageName = context.getPackageName();
            intent.putExtra(ConstantsAPI.SDK_VERSION, Build.SDK_INT);
            intent.putExtra(ConstantsAPI.APP_PACKAGE, packageName);
            intent.putExtra(ConstantsAPI.CONTENT, aVar.c);
            intent.putExtra(ConstantsAPI.CHECK_SUM, b.a(aVar.c, Build.SDK_INT, packageName));
            if (aVar.flags == -1) {
                intent.addFlags(268435456).addFlags(134217728);
            } else {
                intent.setFlags(aVar.flags);
            }
            try {
                context.startActivity(intent);
                Log.d("MicroMsg.SDK.MMessageAct", "send mm message, intent=" + intent);
                return true;
            } catch (Exception e) {
                Log.e("MicroMsg.SDK.MMessageAct", "send fail, ex = " + e.getMessage());
                return false;
            }
        }
    }
}
