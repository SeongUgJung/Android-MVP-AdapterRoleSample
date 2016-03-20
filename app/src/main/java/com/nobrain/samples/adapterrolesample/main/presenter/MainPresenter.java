package com.nobrain.samples.adapterrolesample.main.presenter;

public interface MainPresenter {

    void loadPhotos(int page);

    void onPhotoItemClick(int position);

    void removePhoto(int position);

    void onPhotoItemLongClick(int position);

    interface View {

        void refresh();

        void showFlickerItemActionDialog(int position, String url);

        void showFlickerImageDialog(String url);
    }
}
