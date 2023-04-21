package com.tencent.mm.opensdk.a.a;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.tencent.mm.opensdk.b.d;
import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.constants.ConstantsAPI;

public final class a {

    /* renamed from: com.tencent.mm.opensdk.a.a.a$a  reason: collision with other inner class name */
    public static class C0026a {
        public String c;
        public Bundle d;
        public String e;
        public String f;
        public long g;
    }

    public static boolean a(Context context, C0026a aVar) {
        if (context == null) {
            Log.e("MicroMsg.SDK.MMessage", "send fail, invalid argument");
            return false;
        } else if (d.a(aVar.f)) {
            Log.e("MicroMsg.SDK.MMessage", "send fail, action is null");
            return false;
        } else {
            String str = null;
            if (!d.a(aVar.e)) {
                str = aVar.e + ".permission.MM_MESSAGE";
            }
            Intent intent = new Intent(aVar.f);
            if (aVar.d != null) {
                intent.putExtras(aVar.d);
            }
            String packageName = context.getPackageName();
            intent.putExtra(ConstantsAPI.SDK_VERSION, Build.SDK_INT);
            intent.putExtra(ConstantsAPI.APP_PACKAGE, packageName);
            intent.putExtra(ConstantsAPI.CONTENT, aVar.c);
            intent.putExtra(ConstantsAPI.APP_SUPORT_CONTENT_TYPE, aVar.g);
            intent.putExtra(ConstantsAPI.CHECK_SUM, b.a(aVar.c, Build.SDK_INT, packageName));
            context.sendBroadcast(intent, str);
            Log.d("MicroMsg.SDK.MMessage", "send mm message, intent=" + intent + ", perm=" + str);
            return true;
        }
    }
}
