package app.zogo.photogallery.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import app.zogo.photogallery.R;
import app.zogo.photogallery.adapter.AlbumsAdapter;
import app.zogo.photogallery.database.models.AlbumsData;
import app.zogo.photogallery.model.Albums;
import app.zogo.photogallery.rest.ApiClientService;
import app.zogo.photogallery.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by karuppiah on 9/27/2016.
 */
public class AlbumsActivity extends AppCompatActivity {

    private static final String TAG = AlbumsActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private TextView txtForHeader;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.albums);

        txtForHeader = (TextView) findViewById(R.id.toolbar_title);
        progressBar = (ProgressBar) findViewById(R.id.progressBarMain);

        //Recyclerview initialize
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());
        txtForHeader.setText("Albums");


        //Albums list fetch from AlbumsData table
        AlbumsData albumData = new AlbumsData();
        List<AlbumsData> albumsDataList = albumData.getAllRow();
        //check if album list present or not
        if(albumsDataList.size() > 0) {
            List<Albums> albumsList = new ArrayList<>();
            for(int i=0; i<albumsDataList.size(); i++) {
                Albums albums = new Albums();
                albums.setId(albumsDataList.get(i).id);
                albums.setUserId(albumsDataList.get(i).userId);
                albums.setTitle(albumsDataList.get(i).title);
                albumsList.add(albums);//save albums from db to pojo class
            }
            //recylerview adapter class called
            recyclerView.setAdapter(new AlbumsAdapter(albumsList, R.layout.albums_list_item, getApplicationContext()));
            progressBar.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.VISIBLE);
            ApiInterface apiService =
                    ApiClientService.getClient().create(ApiInterface.class);

            Call<List<Albums>> call = apiService.getAlbumsList();
            call.enqueue(new Callback<List<Albums>>() {
                @Override
                public void onResponse(Call<List<Albums>> call, Response<List<Albums>> response) {
                    List<Albums> albums = response.body();//save the response in Albums class
                    for (int i = 0; i < albums.size(); i++) {
                        //AlbumsData table is called to save the datas from service
                        AlbumsData albumsData = new AlbumsData();
                        albumsData.remoteId = i;
                        albumsData.userId = albums.get(i).getUserId();
                        albumsData.id = albums.get(i).getId();
                        albumsData.title = albums.get(i).getTitle();
                        albumsData.save();
                    }

                    //recyclerview adapter class is called.
                    recyclerView.setAdapter(new AlbumsAdapter(albums, R.layout.albums_list_item, getApplicationContext()));
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<List<Albums>> call, Throwable t) {
                    // Log error here since request failed
                    Log.e(TAG, t.toString());
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
    }
}
