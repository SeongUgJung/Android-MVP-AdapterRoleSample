package com.nobrain.samples.adapterrolesample.main.dagger;

import com.nobrain.samples.adapterrolesample.main.adapter.FlickerPhotoAdapter;
import com.nobrain.samples.adapterrolesample.main.adapter.model.PhotoDataModel;
import com.nobrain.samples.adapterrolesample.main.adapter.view.PhotoAdapterView;
import com.nobrain.samples.adapterrolesample.main.presenter.MainPresenter;
import com.nobrain.samples.adapterrolesample.main.presenter.MainPresenterImpl;
import com.nobrain.samples.adapterrolesample.network.dagger.ApiModule;

import dagger.Module;
import dagger.Provides;

@Module(includes = {ApiModule.class})
public class MainModule {

    private FlickerPhotoAdapter flickerPhotoAdapter;
    private MainPresenter.View view;

    public MainModule(MainPresenter.View view, FlickerPhotoAdapter flickerPhotoAdapter) {
        this.view = view;
        this.flickerPhotoAdapter = flickerPhotoAdapter;
    }

    @Provides
    PhotoDataModel providePhotoModel() {
        return flickerPhotoAdapter;
    }

    @Provides
    PhotoAdapterView providePhotoView() {
        return flickerPhotoAdapter;
    }

    @Provides
    MainPresenter provideMainPresenter(MainPresenterImpl mainPresenter) {
        return mainPresenter;
    }

    @Provides
    MainPresenter.View provideViewOfMainPresenter() {
        return view;
    }
}
