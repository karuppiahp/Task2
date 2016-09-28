package app.zogo.photogallery.database.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by karuppiah on 9/28/2016.
 */
@Table(name = "PhotosData")
public class PhotosData extends Model {
    // This is the unique id given by the server
    @Column(name = "remote_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public long remoteId;
    // This is a regular field
    @Column(name = "albumId")
    public int albumId;
    @Column(name = "id_")
    public int id;
    @Column(name = "title")
    public String title;

    // Make sure to have a default constructor for every ActiveAndroid model
    public PhotosData(){
        super();
    }

    //List all items from PhotosData table
    public static List<PhotosData> getAllRow() {
        return new Select().from(PhotosData.class).execute();
    }
}
