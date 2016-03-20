package com.nobrain.samples.adapterrolesample.main.view;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nobrain.samples.adapterrolesample.R;
import com.nobrain.samples.adapterrolesample.main.adapter.FlickerPhotoAdapter;
import com.nobrain.samples.adapterrolesample.main.adapter.view.PhotoAdapterView;
import com.nobrain.samples.adapterrolesample.main.dagger.DaggerMainComponent;
import com.nobrain.samples.adapterrolesample.main.dagger.MainModule;
import com.nobrain.samples.adapterrolesample.main.presenter.MainPresenter;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainPresenter.View {

    private static final int FIRST_PAGE = 1;
    @Bind(R.id.lv_main)
    RecyclerView lvMain;

    @Inject
    PhotoAdapterView photoAdapterView;
    @Inject
    MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        FlickerPhotoAdapter adapter = new FlickerPhotoAdapter(MainActivity.this);
        DaggerMainComponent.builder()
                .mainModule(new MainModule(this, adapter))
                .build()
                .inject(this);

        lvMain.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));
        lvMain.setAdapter(adapter);
        adapter.setOnRecyclerItemClickListener((adapter1, position) -> {
            mainPresenter.onPhotoItemClick(position);
        });
        adapter.setOnRecyclerItemLongClickListener((adapter1, position) -> {
            mainPresenter.onPhotoItemLongClick(position);
            return true;
        });

        mainPresenter.loadPhotos(FIRST_PAGE);
    }


    @Override
    public void refresh() {
        photoAdapterView.refresh();
    }

    @Override
    public void showFlickerItemActionDialog(int position, String url) {
        String[] menus = {"Copy", "Show Browser", "Delete"};
        new AlertDialog.Builder(MainActivity.this)
                .setItems(menus, (dialog, which) -> {
                    switch (which) {
                        case 0:
                            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                            clipboardManager.setPrimaryClip(ClipData.newPlainText("", url));
                            break;
                        case 1:
                            Intent contentIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            Intent chooserIntent = Intent.createChooser(contentIntent, "Choose WebBrowser");
                            startActivity(chooserIntent);
                            break;
                        case 2:
                            mainPresenter.removePhoto(position);
                            break;

                    }
                })
                .create()
                .show();
    }

    @Override
    public void showFlickerImageDialog(String url) {

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_photo_detail, null);

        new AlertDialog.Builder(MainActivity.this)
                .setView(view)
                .create().show();

        ImageView imageView = (ImageView) view.findViewById(R.id.iv_photo_detail);
        Glide.with(MainActivity.this)
                .load(url)
                .into(imageView);


    }
}
