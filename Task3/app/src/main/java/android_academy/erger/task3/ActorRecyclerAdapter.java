package android_academy.erger.task3;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import java.util.List;

public class ActorRecyclerAdapter extends RecyclerView.Adapter<ActorRecyclerAdapter.ViewHolder> {

    @NonNull
    private final List<Actor> actors;
    @NonNull
    private final Context context;
    private final LayoutInflater inflater;
    private final ItemClickListener itemClickListener;

    public ActorRecyclerAdapter(@NonNull List<Actor> actors,
                                @NonNull Context context,
                                ItemClickListener itemClickListener) {
        this.actors = actors;
        this.context = context;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ActorRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.list_item_actor, parent, false), itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ActorRecyclerAdapter.ViewHolder holder, int position) {
        Actor actor = actors.get(position);
        Glide.with(context)
                .load(actor.getSmallPhoto())
                .apply(new RequestOptions().centerCrop())
                .into(holder.actorPhoto);
    }

    @Override
    public int getItemCount() {
        return actors.size();
    }

    public interface ItemClickListener {
        void onItemClick(int position, View v);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView actorPhoto;

        ViewHolder(@NonNull View itemView, @Nullable final ItemClickListener itemClickListener) {
            super(itemView);
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (itemClickListener != null && position != RecyclerView.NO_POSITION) {
                    itemClickListener.onItemClick(position, v);
                }
            });
            actorPhoto = itemView.findViewById(R.id.actor_photo);
        }
    }
}
