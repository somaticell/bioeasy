package cn.sharesdk.framework.authorize;

import android.content.Context;
import android.content.Intent;
import com.mob.tools.FakeActivity;

/* compiled from: AbstractAuthorizeActivity */
public class a extends FakeActivity {
    protected AuthorizeHelper a;

    public void a(AuthorizeHelper authorizeHelper) {
        this.a = authorizeHelper;
        super.show(authorizeHelper.getPlatform().getContext(), (Intent) null);
    }

    public void show(Context context, Intent i) {
        throw new RuntimeException("This method is deprecated, use show(AuthorizeHelper, Intent) instead");
    }

    public AuthorizeHelper a() {
        return this.a;
    }
}
