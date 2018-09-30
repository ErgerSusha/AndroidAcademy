package android_academy.erger.task5;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import android_academy.erger.task5.entities.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ImageViewHolder> {

    private List<Movie> items = new ArrayList<>();

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ImageViewHolder(inflater.inflate(R.layout.list_item_film, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        final Movie movie = items.get(position);
        Picasso.get()
                .load(movie.getPoster())
                .fit()
                .centerCrop()
                .into(holder.poster);

        holder.title.setText(movie.getTitle());
    }

    public void replaceWith(List<Movie> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {

        ImageView poster;
        TextView title;

        ImageViewHolder(View itemView) {
            super(itemView);

            poster = itemView.findViewById(R.id.film_poster);
            title = itemView.findViewById(R.id.film_title);
        }
    }
}
