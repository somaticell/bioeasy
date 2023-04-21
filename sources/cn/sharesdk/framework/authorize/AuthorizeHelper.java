package cn.sharesdk.framework.authorize;

import cn.sharesdk.framework.Platform;

public interface AuthorizeHelper {
    AuthorizeListener getAuthorizeListener();

    String getAuthorizeUrl();

    b getAuthorizeWebviewClient(e eVar);

    Platform getPlatform();

    String getRedirectUri();

    SSOListener getSSOListener();

    d getSSOProcessor(c cVar);
}
