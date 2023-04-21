package com.baidu.platform.base;

import com.baidu.mapapi.search.core.SearchResult;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class d {
    protected SearchType a;

    public abstract SearchResult a(String str);

    public SearchType a() {
        return this.a;
    }

    public abstract void a(SearchResult searchResult, Object obj);

    public void a(SearchType searchType) {
        this.a = searchType;
    }

    /* access modifiers changed from: protected */
    public boolean a(String str, SearchResult searchResult, boolean z) {
        if (str != null) {
            try {
                if (str.length() > 0) {
                    JSONObject jSONObject = new JSONObject(str);
                    if (jSONObject == null) {
                        searchResult.error = SearchResult.ERRORNO.RESULT_NOT_FOUND;
                        return true;
                    }
                    int optInt = z ? jSONObject.optInt("status") : jSONObject.optInt("status_sp");
                    if (optInt == 0) {
                        return false;
                    }
                    switch (optInt) {
                        case 104:
                        case 105:
                        case 106:
                        case 107:
                        case 108:
                            searchResult.error = SearchResult.ERRORNO.PERMISSION_UNFINISHED;
                            return true;
                        case 200:
                        case 230:
                            searchResult.error = SearchResult.ERRORNO.KEY_ERROR;
                            return true;
                        default:
                            searchResult.error = SearchResult.ERRORNO.RESULT_NOT_FOUND;
                            return true;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                searchResult.error = SearchResult.ERRORNO.RESULT_NOT_FOUND;
                return true;
            }
        }
        searchResult.error = SearchResult.ERRORNO.SEARCH_SERVER_INTERNAL_ERROR;
        return true;
    }
}
