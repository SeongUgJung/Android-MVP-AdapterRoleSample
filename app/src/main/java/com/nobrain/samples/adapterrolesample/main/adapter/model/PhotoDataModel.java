package com.nobrain.samples.adapterrolesample.main.adapter.model;

import com.nobrain.samples.adapterrolesample.network.domain.Photo;

public interface PhotoDataModel {
    void add(Photo photo);
    Photo remove(int position);
    Photo getPhoto(int position);

    int getSize();
}
