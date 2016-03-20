package com.nobrain.samples.adapterrolesample.network;

import com.nobrain.samples.adapterrolesample.BuildConfig;
import com.nobrain.samples.adapterrolesample.network.domain.RecentPhoto;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

@Singleton
public class FlickerPhotos {

    private Retrofit retrofit;

    @Inject
    public FlickerPhotos(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    public Observable<RecentPhoto> getFlickerRecent(int page) {
        return retrofit.create(FlickerInteresting.class)
                .getInterestingness(page);
    }

    interface FlickerInteresting {
        @GET("?format=json&nojsoncallback=1&method=flickr.interestingness.getList&api_key=" + BuildConfig.FLICKER_API_KEY)
        Observable<RecentPhoto> getInterestingness(@Query("page") int page);
    }

}

