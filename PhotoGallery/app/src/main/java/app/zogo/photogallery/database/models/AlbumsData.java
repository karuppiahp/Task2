package app.zogo.photogallery.database.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by karuppiah on 9/28/2016.
 */
@Table(name = "AlbumsData")
public class AlbumsData extends Model {
    // This is the unique id given by the server
    @Column(name = "remote_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public long remoteId;
    // This is a regular field
    @Column(name = "userId")
    public int userId;
    @Column(name = "id_")
    public int id;
    @Column(name = "title")
    public String title;

    // Make sure to have a default constructor for every ActiveAndroid model
    public AlbumsData(){
        super();
    }

    //List all items from AlbumsData table
    public static List<AlbumsData> getAllRow() {
        return new Select().from(AlbumsData.class).execute();
    }
}