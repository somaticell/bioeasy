package cn.com.bioeasy.healty.app.healthapp.adapter;

import dagger.MembersInjector;
import dagger.internal.Factory;

public final class ScrollImagesViewPager_Factory implements Factory<ScrollImagesViewPager> {
    static final /* synthetic */ boolean $assertionsDisabled = (!ScrollImagesViewPager_Factory.class.desiredAssertionStatus());
    private final MembersInjector<ScrollImagesViewPager> membersInjector;

    public ScrollImagesViewPager_Factory(MembersInjector<ScrollImagesViewPager> membersInjector2) {
        if ($assertionsDisabled || membersInjector2 != null) {
            this.membersInjector = membersInjector2;
            return;
        }
        throw new AssertionError();
    }

    public ScrollImagesViewPager get() {
        ScrollImagesViewPager instance = new ScrollImagesViewPager();
        this.membersInjector.injectMembers(instance);
        return instance;
    }

    public static Factory<ScrollImagesViewPager> create(MembersInjector<ScrollImagesViewPager> membersInjector2) {
        return new ScrollImagesViewPager_Factory(membersInjector2);
    }
}
