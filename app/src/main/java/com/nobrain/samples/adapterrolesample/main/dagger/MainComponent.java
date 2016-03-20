package com.nobrain.samples.adapterrolesample.main.dagger;

import com.nobrain.samples.adapterrolesample.main.view.MainActivity;

import dagger.Component;

@Component(modules = {MainModule.class})
public interface MainComponent {
    void inject(MainActivity mainActivity);
}
