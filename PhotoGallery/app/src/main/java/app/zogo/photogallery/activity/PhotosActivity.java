package app.zogo.photogallery.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.zogo.photogallery.R;
import app.zogo.photogallery.adapter.PhotoGalleryAdapter;
import app.zogo.photogallery.database.models.PhotosData;
import app.zogo.photogallery.model.Photos;
import app.zogo.photogallery.rest.ApiClientService;
import app.zogo.photogallery.rest.ApiInterface;
import app.zogo.photogallery.support.ImageStorage;
import app.zogo.photogallery.support.SpacesItemDecoration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by karuppiah on 9/27/2016.
 */
public class PhotosActivity extends AppCompatActivity {

    private static final String TAG = PhotosActivity.class.getSimpleName();
    private int id;
    private RecyclerView rView;
    int spacing = 10; // 10px
    private TextView txtForHeader;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.albums);

        txtForHeader = (TextView) findViewById(R.id.toolbar_title);
        progressBar = (ProgressBar) findViewById(R.id.progressBarMain);

        id = getIntent().getExtras().getInt("id");//get album id from intent

        //recyclerView initialized with item decoration for divider
        rView = (RecyclerView)findViewById(R.id.recyclerView);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(new GridLayoutManager(PhotosActivity.this, 4));
        rView.addItemDecoration(new SpacesItemDecoration(spacing));
        txtForHeader.setText("Photo Gallery");

        if(ImageStorage.getImage(String.valueOf(id)).size() > 0) {
            ArrayList<String> filesArray = ImageStorage.getImage(String.valueOf(id));
            PhotosData photosData = new PhotosData();
            List<PhotosData> photosDataList = photosData.getAllRow();
            List<Photos> photosList = new ArrayList<>();
            for(int i=0; i<filesArray.size(); i++) {
                Photos photos = new Photos();
                photos.setAlbumId(id);
                photos.setId(photosDataList.get(i).id);
                photos.setTitle(photosDataList.get(i).title);
                photos.setThumbUrl(filesArray.get(i));
                photos.setUrl(filesArray.get(i));
                photosList.add(photos);
            }

            PhotoGalleryAdapter rcAdapter = new PhotoGalleryAdapter(PhotosActivity.this, photosList);
            rView.setAdapter(rcAdapter);

        } else {
            progressBar.setVisibility(View.VISIBLE);
            ApiInterface apiService =
                    ApiClientService.getClient().create(ApiInterface.class);

            Call<List<Photos>> call = apiService.getPhotosList(id);
            call.enqueue(new Callback<List<Photos>>() {
                @Override
                public void onResponse(Call<List<Photos>> call, Response<List<Photos>> response) {
                    List<Photos> photos = response.body(); //photos response fetched from sewrvice
                    for (int i = 0; i < photos.size(); i++) {
                        PhotosData photosData = new PhotosData();
                        photosData.remoteId = i;
                        photosData.albumId = id;
                        photosData.id = photos.get(i).getId();
                        photosData.title = photos.get(i).getTitle();
                        photosData.save();

                        String imageSep[] = photos.get(i).getThumbUrl().split(".it/");
                        String width = imageSep[1];
                        String widthSplit[] = width.split("/");
                        String widthValue = widthSplit[0];
                        String widthTxt = widthSplit[1];
                      //  String thumbUrl = "https://placeholdit.imgix.net/~text?txtsize=14&bg=" + widthTxt + "&txt=" + widthValue + "%C3%97" + widthValue + "&w=" + widthValue + "&h=" + widthValue;
                        String thumbUrl = "https://placeholdit.imgix.net/~text?txtsize=14&bg=" +widthTxt + "&txt=150%C3%97150&w=150&h=150";
                        ImageStorage.imageDownload(PhotosActivity.this, thumbUrl, String.valueOf(id), photos.get(i).getTitle());
                    }
                    PhotoGalleryAdapter rcAdapter = new PhotoGalleryAdapter(PhotosActivity.this, photos);
                    rView.setAdapter(rcAdapter);
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<List<Photos>> call, Throwable t) {
                    // Log error here since request failed
                    Log.e(TAG, t.toString());
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
    }
}
