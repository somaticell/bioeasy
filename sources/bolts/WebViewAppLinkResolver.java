package bolts;

import android.content.Context;
import android.net.Uri;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import bolts.AppLink;
import com.alipay.sdk.util.h;
import com.facebook.common.util.UriUtil;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WebViewAppLinkResolver implements AppLinkResolver {
    private static final String KEY_AL_VALUE = "value";
    private static final String KEY_ANDROID = "android";
    private static final String KEY_APP_NAME = "app_name";
    private static final String KEY_CLASS = "class";
    private static final String KEY_PACKAGE = "package";
    private static final String KEY_SHOULD_FALLBACK = "should_fallback";
    private static final String KEY_URL = "url";
    private static final String KEY_WEB = "web";
    private static final String KEY_WEB_URL = "url";
    private static final String META_TAG_PREFIX = "al";
    private static final String PREFER_HEADER = "Prefer-Html-Meta-Tags";
    private static final String TAG_EXTRACTION_JAVASCRIPT = "javascript:boltsWebViewAppLinkResolverResult.setValue((function() {  var metaTags = document.getElementsByTagName('meta');  var results = [];  for (var i = 0; i < metaTags.length; i++) {    var property = metaTags[i].getAttribute('property');    if (property && property.substring(0, 'al:'.length) === 'al:') {      var tag = { \"property\": metaTags[i].getAttribute('property') };      if (metaTags[i].hasAttribute('content')) {        tag['content'] = metaTags[i].getAttribute('content');      }      results.push(tag);    }  }  return JSON.stringify(results);})())";
    /* access modifiers changed from: private */
    public final Context context;

    public WebViewAppLinkResolver(Context context2) {
        this.context = context2;
    }

    public Task<AppLink> getAppLinkFromUrlInBackground(final Uri url) {
        final Capture<String> content = new Capture<>();
        final Capture<String> contentType = new Capture<>();
        return Task.callInBackground(new Callable<Void>() {
            public Void call() throws Exception {
                URL currentURL = new URL(url.toString());
                URLConnection connection = null;
                while (currentURL != null) {
                    connection = currentURL.openConnection();
                    if (connection instanceof HttpURLConnection) {
                        ((HttpURLConnection) connection).setInstanceFollowRedirects(true);
                    }
                    connection.setRequestProperty(WebViewAppLinkResolver.PREFER_HEADER, WebViewAppLinkResolver.META_TAG_PREFIX);
                    connection.connect();
                    if (connection instanceof HttpURLConnection) {
                        HttpURLConnection httpConnection = (HttpURLConnection) connection;
                        if (httpConnection.getResponseCode() < 300 || httpConnection.getResponseCode() >= 400) {
                            currentURL = null;
                        } else {
                            currentURL = new URL(httpConnection.getHeaderField("Location"));
                            httpConnection.disconnect();
                        }
                    } else {
                        currentURL = null;
                    }
                }
                try {
                    content.set(WebViewAppLinkResolver.readFromConnection(connection));
                    contentType.set(connection.getContentType());
                } finally {
                    if (connection instanceof HttpURLConnection) {
                        ((HttpURLConnection) connection).disconnect();
                    }
                }
            }
        }).onSuccessTask(new Continuation<Void, Task<JSONArray>>() {
            public Task<JSONArray> then(Task<Void> task) throws Exception {
                final Task<TResult>.TaskCompletionSource create = Task.create();
                WebView webView = new WebView(WebViewAppLinkResolver.this.context);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setNetworkAvailable(false);
                webView.setWebViewClient(new WebViewClient() {
                    private boolean loaded = false;

                    private void runJavaScript(WebView view) {
                        if (!this.loaded) {
                            this.loaded = true;
                            view.loadUrl(WebViewAppLinkResolver.TAG_EXTRACTION_JAVASCRIPT);
                        }
                    }

                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        runJavaScript(view);
                    }

                    public void onLoadResource(WebView view, String url) {
                        super.onLoadResource(view, url);
                        runJavaScript(view);
                    }
                });
                webView.addJavascriptInterface(new Object() {
                    @JavascriptInterface
                    public void setValue(String value) {
                        try {
                            create.trySetResult(new JSONArray(value));
                        } catch (JSONException e) {
                            create.trySetError(e);
                        }
                    }
                }, "boltsWebViewAppLinkResolverResult");
                String inferredContentType = null;
                if (contentType.get() != null) {
                    inferredContentType = ((String) contentType.get()).split(h.b)[0];
                }
                webView.loadDataWithBaseURL(url.toString(), (String) content.get(), inferredContentType, (String) null, (String) null);
                return create.getTask();
            }
        }, Task.UI_THREAD_EXECUTOR).onSuccess(new Continuation<JSONArray, AppLink>() {
            public AppLink then(Task<JSONArray> task) throws Exception {
                return WebViewAppLinkResolver.makeAppLinkFromAlData(WebViewAppLinkResolver.parseAlData(task.getResult()), url);
            }
        });
    }

    /* access modifiers changed from: private */
    public static Map<String, Object> parseAlData(JSONArray dataArray) throws JSONException {
        Map<String, Object> child;
        Map<String, Object> al = new HashMap<>();
        for (int i = 0; i < dataArray.length(); i++) {
            JSONObject tag = dataArray.getJSONObject(i);
            String[] nameComponents = tag.getString("property").split(":");
            if (nameComponents[0].equals(META_TAG_PREFIX)) {
                Map<String, Object> root = al;
                for (int j = 1; j < nameComponents.length; j++) {
                    List<Map<String, Object>> children = (List) root.get(nameComponents[j]);
                    if (children == null) {
                        children = new ArrayList<>();
                        root.put(nameComponents[j], children);
                    }
                    if (children.size() > 0) {
                        child = children.get(children.size() - 1);
                    } else {
                        child = null;
                    }
                    if (child == null || j == nameComponents.length - 1) {
                        child = new HashMap<>();
                        children.add(child);
                    }
                    root = child;
                }
                if (tag.has(UriUtil.LOCAL_CONTENT_SCHEME)) {
                    if (tag.isNull(UriUtil.LOCAL_CONTENT_SCHEME)) {
                        root.put(KEY_AL_VALUE, (Object) null);
                    } else {
                        root.put(KEY_AL_VALUE, tag.getString(UriUtil.LOCAL_CONTENT_SCHEME));
                    }
                }
            }
        }
        return al;
    }

    private static List<Map<String, Object>> getAlList(Map<String, Object> map, String key) {
        List<Map<String, Object>> result = (List) map.get(key);
        if (result == null) {
            return Collections.emptyList();
        }
        return result;
    }

    /* access modifiers changed from: private */
    public static AppLink makeAppLinkFromAlData(Map<String, Object> appLinkDict, Uri destination) {
        List<AppLink.Target> targets = new ArrayList<>();
        List<Map<String, Object>> platformMapList = (List) appLinkDict.get(KEY_ANDROID);
        if (platformMapList == null) {
            platformMapList = Collections.emptyList();
        }
        for (Map<String, Object> platformMap : platformMapList) {
            List<Map<String, Object>> urls = getAlList(platformMap, "url");
            List<Map<String, Object>> packages = getAlList(platformMap, KEY_PACKAGE);
            List<Map<String, Object>> classes = getAlList(platformMap, KEY_CLASS);
            List<Map<String, Object>> appNames = getAlList(platformMap, KEY_APP_NAME);
            int maxCount = Math.max(urls.size(), Math.max(packages.size(), Math.max(classes.size(), appNames.size())));
            int i = 0;
            while (i < maxCount) {
                targets.add(new AppLink.Target((String) (packages.size() > i ? packages.get(i).get(KEY_AL_VALUE) : null), (String) (classes.size() > i ? classes.get(i).get(KEY_AL_VALUE) : null), tryCreateUrl((String) (urls.size() > i ? urls.get(i).get(KEY_AL_VALUE) : null)), (String) (appNames.size() > i ? appNames.get(i).get(KEY_AL_VALUE) : null)));
                i++;
            }
        }
        Uri webUrl = destination;
        List<Map<String, Object>> webMapList = (List) appLinkDict.get(KEY_WEB);
        if (webMapList != null && webMapList.size() > 0) {
            Map<String, Object> webMap = webMapList.get(0);
            List<Map<String, Object>> urls2 = (List) webMap.get("url");
            List<Map<String, Object>> shouldFallbacks = (List) webMap.get(KEY_SHOULD_FALLBACK);
            if (shouldFallbacks != null && shouldFallbacks.size() > 0) {
                if (Arrays.asList(new String[]{"no", "false", "0"}).contains(((String) shouldFallbacks.get(0).get(KEY_AL_VALUE)).toLowerCase())) {
                    webUrl = null;
                }
            }
            if (!(webUrl == null || urls2 == null || urls2.size() <= 0)) {
                webUrl = tryCreateUrl((String) urls2.get(0).get(KEY_AL_VALUE));
            }
        }
        return new AppLink(destination, targets, webUrl);
    }

    private static Uri tryCreateUrl(String urlString) {
        if (urlString == null) {
            return null;
        }
        return Uri.parse(urlString);
    }

    /* access modifiers changed from: private */
    public static String readFromConnection(URLConnection connection) throws IOException {
        InputStream stream;
        if (connection instanceof HttpURLConnection) {
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            try {
                stream = connection.getInputStream();
            } catch (Exception e) {
                stream = httpConnection.getErrorStream();
            }
        } else {
            stream = connection.getInputStream();
        }
        try {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            while (true) {
                int read = stream.read(buffer);
                if (read == -1) {
                    break;
                }
                output.write(buffer, 0, read);
            }
            String charset = connection.getContentEncoding();
            if (charset == null) {
                String[] arr$ = connection.getContentType().split(h.b);
                int len$ = arr$.length;
                int i$ = 0;
                while (true) {
                    if (i$ >= len$) {
                        break;
                    }
                    String part = arr$[i$].trim();
                    if (part.startsWith("charset=")) {
                        charset = part.substring("charset=".length());
                        break;
                    }
                    i$++;
                }
                if (charset == null) {
                    charset = "UTF-8";
                }
            }
            return new String(output.toByteArray(), charset);
        } finally {
            stream.close();
        }
    }
}
