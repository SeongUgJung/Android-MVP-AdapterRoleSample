package com.nobrain.samples.adapterrolesample.network.dagger;

import com.nobrain.samples.adapterrolesample.network.FlickerPhotos;
import com.nobrain.samples.adapterrolesample.network.RetrofitCreator;

import dagger.Module;
import dagger.Provides;

@Module
public class ApiModule {
    @Provides
    FlickerPhotos provideFlickerPhotos() {
        return new FlickerPhotos(RetrofitCreator.provideRetrofit());
    }


}
