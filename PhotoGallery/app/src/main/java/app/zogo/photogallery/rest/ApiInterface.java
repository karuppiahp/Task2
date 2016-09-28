package app.zogo.photogallery.rest;

import java.util.List;

import app.zogo.photogallery.model.Albums;
import app.zogo.photogallery.model.Photos;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by karuppiah on 9/27/2016.
 */
public interface ApiInterface {
    @GET("albums")
    Call<List<Albums>> getAlbumsList();

    @GET("photos")
    Call<List<Photos>> getPhotosList(@Query("albumId") int id);
}
