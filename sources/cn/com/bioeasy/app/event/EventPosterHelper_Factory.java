package cn.com.bioeasy.app.event;

import com.squareup.otto.Bus;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class EventPosterHelper_Factory implements Factory<EventPosterHelper> {
    static final /* synthetic */ boolean $assertionsDisabled = (!EventPosterHelper_Factory.class.desiredAssertionStatus());
    private final Provider<Bus> busProvider;

    public EventPosterHelper_Factory(Provider<Bus> busProvider2) {
        if ($assertionsDisabled || busProvider2 != null) {
            this.busProvider = busProvider2;
            return;
        }
        throw new AssertionError();
    }

    public EventPosterHelper get() {
        return new EventPosterHelper(this.busProvider.get());
    }

    public static Factory<EventPosterHelper> create(Provider<Bus> busProvider2) {
        return new EventPosterHelper_Factory(busProvider2);
    }
}
