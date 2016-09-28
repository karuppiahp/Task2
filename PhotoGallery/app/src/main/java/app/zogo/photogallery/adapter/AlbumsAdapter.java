package app.zogo.photogallery.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import app.zogo.photogallery.R;
import app.zogo.photogallery.activity.PhotosActivity;
import app.zogo.photogallery.model.Albums;

/**
 * Created by karuppiah on 9/27/2016.
 */
public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.AlbumsViewHolder> {

    private List<Albums> albums;
    private int          rowLayout;
    private Context      context;


    public static class AlbumsViewHolder extends RecyclerView.ViewHolder {
        TextView     albumTitle;
        LinearLayout layForItems;

        public AlbumsViewHolder(View v) {
            super(v);
            albumTitle = (TextView) v.findViewById(R.id.title);
            layForItems = (LinearLayout) v.findViewById(R.id.layForItems);
        }
    }

    public AlbumsAdapter(List<Albums> albums, int rowLayout, Context context) {
        this.albums = albums;
        this.rowLayout = rowLayout; //layout
        this.context = context;
    }

    @Override
    public AlbumsAdapter.AlbumsViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new AlbumsViewHolder(view);
    }


    @Override
    public void onBindViewHolder(AlbumsViewHolder holder, final int position) {
        holder.albumTitle.setText(albums.get(position).getTitle()); //set title from albums list

        //text item clicks redirect to photo gallery activity.
        holder.layForItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PhotosActivity.class);
                intent.putExtra("id", albums.get(position).getId());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }
}