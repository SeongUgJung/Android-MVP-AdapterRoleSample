package com.nobrain.samples.adapterrolesample.main.presenter;

import android.support.annotation.NonNull;
import android.support.test.runner.AndroidJUnit4;

import com.nobrain.samples.adapterrolesample.main.adapter.model.PhotoDataModel;
import com.nobrain.samples.adapterrolesample.network.FlickerPhotos;
import com.nobrain.samples.adapterrolesample.network.RetrofitCreator;
import com.nobrain.samples.adapterrolesample.network.domain.Photo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.jayway.awaitility.Awaitility.await;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class MainPresenterImplTest {

    private MainPresenter mainPresenter;

    private MainPresenter.View view;
    private PhotoDataModel photoDataModel;

    @Before
    public void setUp() throws Exception {
        view = mock(MainPresenter.View.class);
        photoDataModel = mock(PhotoDataModel.class);
        mainPresenter = new MainPresenterImpl(view, new FlickerPhotos(RetrofitCreator.provideRetrofit()), photoDataModel);
    }


    @Test
    public void testLoadPhotos() throws Exception {

        final boolean[] finish = {false};
        doAnswer(invocationOnMock -> {
            finish[0] = true;
            return invocationOnMock;
        }).when(view).refresh();

        mainPresenter.loadPhotos(1);

        await().until(() -> finish[0]);

        verify(photoDataModel, atLeastOnce()).add(any());
        verify(view).refresh();
    }

    @Test
    public void testOnPhotoItemClick() throws Exception {
        int position = 0;
        // Given
        Photo photo = getPhoto();
        when(photoDataModel.getPhoto(position)).thenReturn(photo);

        // when
        mainPresenter.onPhotoItemClick(position);

        // then
        verify(view).showFlickerItemActionDialog(eq(position), any());
    }

    @Test
    public void testRemovePhoto() throws Exception {
        mainPresenter.removePhoto(0);
        verify(photoDataModel).remove(eq(0));
        verify(view).refresh();
    }

    @Test
    public void testOnPhotoItemLongClick() throws Exception {

        //Given
        int position = 0;
        Photo photo = getPhoto();
        when(photoDataModel.getPhoto(position)).thenReturn(photo);

        // When
        mainPresenter.onPhotoItemLongClick(position);

        // Then
        verify(view).showFlickerImageDialog(anyString());
    }

    @NonNull
    private Photo getPhoto() {
        Photo photo = new Photo();
        photo.setFarm(1);
        photo.setId("id");
        photo.setIsfamily(2);
        photo.setIsfriend(3);
        photo.setIspublic(4);
        photo.setOwner("owner");
        photo.setSecret("secret");
        photo.setServer("server");
        photo.setTitle("title");
        return photo;
    }
}