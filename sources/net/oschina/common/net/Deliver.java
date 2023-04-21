package net.oschina.common.net;

import net.oschina.common.net.io.StrParam;

public abstract class Deliver<Client> {
    Client client;

    public abstract <T> T executeGet(boolean z, Callback<T> callback, String str, Object obj, StrParam... strParamArr);

    public abstract <T> T executePost(boolean z, Callback<T> callback, String str, Object obj, StrParam... strParamArr);

    public Client getClient() {
        return this.client;
    }
}
