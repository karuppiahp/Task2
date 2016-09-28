package app.zogo.photogallery.support;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

/**
 * Created by karuppiah on 9/28/2016.
 */
public class DownloadAndReadImage {
    String strURL;
    int pos;
    Bitmap bitmap=null;

    // pass image url and Pos for example i:
    public DownloadAndReadImage(String url, int position)
    {
        this.strURL=url;
        this.pos=position;
        getBitmapImage();
    }

    public  Bitmap getBitmapImage()
    {
        downloadBitmapImage();
        return readBitmapImage();
    }

    void downloadBitmapImage()
    {
        InputStream input;
        try {
            URL url = new URL (strURL);
            input = url.openStream();
            byte[] buffer = new byte[1500];
            OutputStream output = new FileOutputStream("/sdcard/"+pos+".png");
            try
            {
                int bytesRead = 0;
                while ((bytesRead = input.read(buffer, 0, buffer.length)) >= 0)
                {
                    output.write(buffer, 0, bytesRead);
                }
            }
            finally
            {
                output.close();
                buffer=null;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    Bitmap readBitmapImage()
    {
        String imageInSD = "/sdcard/mac/"+strURL;
        BitmapFactory.Options bOptions = new BitmapFactory.Options();
        bOptions.inTempStorage = new byte[16*1024];

        bitmap = BitmapFactory.decodeFile(imageInSD,bOptions);

        return bitmap;
    }

}
