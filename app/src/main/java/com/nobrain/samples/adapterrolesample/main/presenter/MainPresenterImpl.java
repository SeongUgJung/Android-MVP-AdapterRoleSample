package com.nobrain.samples.adapterrolesample.main.presenter;

import com.nobrain.samples.adapterrolesample.main.adapter.model.PhotoDataModel;
import com.nobrain.samples.adapterrolesample.network.FlickerPhotos;
import com.nobrain.samples.adapterrolesample.network.domain.Photo;
import com.nobrain.samples.adapterrolesample.network.domain.RecentPhoto;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainPresenterImpl implements MainPresenter {
    private View view;
    private FlickerPhotos flickerPhotos;
    private PhotoDataModel photoDataModel;

    @Inject
    public MainPresenterImpl(View view, FlickerPhotos flickerPhotos, PhotoDataModel photoDataModel) {
        this.view = view;
        this.flickerPhotos = flickerPhotos;
        this.photoDataModel = photoDataModel;
    }

    @Override
    public void loadPhotos(int page) {
        Observable<RecentPhoto> flickerRecent = flickerPhotos.getFlickerRecent(page);
        flickerRecent
                .subscribeOn(Schedulers.io())
                .map(RecentPhoto::getPhotos)
                .filter(photos -> photos.getPhoto() != null && !photos.getPhoto().isEmpty())
                .flatMap(photos -> Observable.from(photos.getPhoto()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(photoDataModel::add, Throwable::printStackTrace, view::refresh);

    }

    @Override
    public void onPhotoItemClick(int position) {
        Photo photo = photoDataModel.getPhoto(position);
        String url = getUrl(photo);

        view.showFlickerItemActionDialog(position, url);
    }

    @Override
    public void removePhoto(int position) {
        photoDataModel.remove(position);
        view.refresh();
    }

    @Override
    public void onPhotoItemLongClick(int position) {
        Photo photo = photoDataModel.getPhoto(position);
        String url = getUrl(photo);

        view.showFlickerImageDialog(url);
    }

    private String getUrl(Photo photo) {
        String farmId = String.valueOf(photo.getFarm());
        String serverId = photo.getServer();
        String id = photo.getId();
        String secret = photo.getSecret();
        return String.format("https://farm%s.staticflickr.com/%s/%s_%s.jpg", farmId, serverId, id, secret);
    }
}
