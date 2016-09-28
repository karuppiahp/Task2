package app.zogo.photogallery.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by karuppiah on 9/27/2016.
 */
public class ApiClientService {

    public static final String   BASE_URL = "https://jsonplaceholder.typicode.com/";
    private static      Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
