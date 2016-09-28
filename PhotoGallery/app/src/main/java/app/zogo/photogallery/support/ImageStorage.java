package app.zogo.photogallery.support;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by karuppiah on 9/28/2016.
 */
public class ImageStorage {

    static String strURL;
    static String folderName;
    static String fileName;
    Bitmap bitmap=null;

    // pass image url and Pos for example i:
    public ImageStorage(String url, String folderName, String fileName)
    {
        this.strURL=url;
        this.folderName=folderName;
        this.fileName=fileName;
    }

    public static String downloadBitmapImage()
    {
        InputStream input;
        String stored = null;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            URL url = new URL (strURL);
            input = url.openStream();
            byte[] buffer = new byte[1500];
            File sdcard = Environment.getExternalStorageDirectory() ;
            File folder = new File(sdcard.getAbsoluteFile(), "." + folderName);//the dot makes this directory hidden to the user
            folder.mkdir();
            File file = new File(folder.getAbsoluteFile(), folderName + "thumb" + ".jpg") ;
            OutputStream output = new FileOutputStream (file);
            try
            {
                int bytesRead = 0;
                while ((bytesRead = input.read(buffer, 0, buffer.length)) >= 0)
                {
                    output.write(buffer, 0, bytesRead);
                }
                stored = "success";
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
        return stored;
    }

    /*Bitmap readBitmapImage()
    {
        String imageInSD = "/sdcard/mac/"+strURL;
        BitmapFactory.Options bOptions = new BitmapFactory.Options();
        bOptions.inTempStorage = new byte[16*1024];

        bitmap = BitmapFactory.decodeFile(imageInSD,bOptions);

        return bitmap;
    }*/


    public static String saveToSdCard(Bitmap bitmap, String filename, String folderName) {

        String stored = null;

        File sdcard = Environment.getExternalStorageDirectory() ;

        File folder = new File(sdcard.getAbsoluteFile(), "." + folderName);//the dot makes this directory hidden to the user
        folder.mkdir();
        File file = new File(folder.getAbsoluteFile(), folderName + "thumb" + ".jpg") ;
        if (file.exists())
            return stored ;

        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            stored = "success";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stored;
    }

    public static ArrayList<String> getImage(String folderName) {

        File mediaImage = null;
        String decoded = "";
        ArrayList<String> fileArrays = new ArrayList<String>();// list of file paths
        File[] listFile;
        try {
            String root = Environment.getExternalStorageDirectory().toString();
            File myDir = new File(root);
            if (!myDir.exists())
                return null;

        //    mediaImage = new File(myDir.getPath() + "/." + folderName + "/"+imagename);
            /*String uri = Uri.fromFile(mediaImage).toString();
            decoded = Uri.decode(uri);
            Log.e("decoded url??",""+decoded);*/
            mediaImage = new File(myDir.getPath() + "/." + folderName + "/thumb");
            if (mediaImage.isDirectory())
            {
                listFile = mediaImage.listFiles();


                for (int i = 0; i < listFile.length; i++)
                {

                    Log.e("absolute path??",""+listFile[i].getAbsolutePath());
                    fileArrays.add(listFile[i].getAbsolutePath());

                }
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return fileArrays;
    }

    public static void imageDownload(Context ctx, String url, String folderName, String title){
        Log.e("Url image??",""+url);
        Picasso.with(ctx)
                .load(url)
                .into(getTarget(url, folderName,title));
    }

    //target to save
    private static Target getTarget(final String url, final String folderName, final String title){
        Target target = new Target(){

            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {

                        /*File sdcard = Environment.getExternalStorageDirectory() ;
                        File folder = new File(sdcard.getAbsoluteFile(), "." + folderName + "/thumb");//the dot makes this directory hidden to the user
                        folder.mkdir();*/
                    //    File file = new File(folder.getAbsoluteFile(), folderName + "thumb" + ".jpg") ;
                        /*File fileDir = new File(Environment.getExternalStorageDirectory().getPath() + "/" + folderName + "/thumb" );
                        fileDir.mkdirs();
                        File file = new File(fileDir.toString(), title +".jpg");*/
                        File file = new File(Environment.getExternalStorageDirectory().getPath() + "five.jpg" );
                        try {
                            file.createNewFile();
                            FileOutputStream ostream = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, ostream);
                            ostream.flush();
                            ostream.close();
                        } catch (IOException e) {
                            Log.e("IOException", e.getLocalizedMessage());
                        }
                    }
                }).start();

            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        return target;
    }
}
