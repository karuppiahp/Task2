package app.zogo.photogallery.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import app.zogo.photogallery.R;

/**
 * Created by karuppiah on 9/28/2016.
 */
public class PhotoFullActivity extends AppCompatActivity {

    private ImageView imgView;
    private TextView txtForHeader;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo);

        txtForHeader = (TextView) findViewById(R.id.toolbar_title);
        progressBar = (ProgressBar) findViewById(R.id.progressBarMain);
        imgView = (ImageView) findViewById(R.id.imgForPhoto);
        String url = getIntent().getExtras().getString("ImageUrl");
        txtForHeader.setText("Photo View");

        ImageLoader imageLoader = ImageLoader.getInstance();
        progressBar.setVisibility(View.VISIBLE);
        imageLoader.loadImage(url, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                imgView.setImageBitmap(loadedImage);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
