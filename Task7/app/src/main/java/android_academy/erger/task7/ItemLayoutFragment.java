package android_academy.erger.task7;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import java.util.List;
import android_academy.erger.task7.entities.Collection;
import android_academy.erger.task7.entities.Photo;

public class ItemLayoutFragment extends Fragment implements View.OnClickListener{

    private final static String URI_ID = "IMAGE_URI";
    private View previewLayout;
    private View detailsLayout;
    private ImageView imageView;
    private RecyclerView recyclerView;
    private Collection collection;

    public static ItemLayoutFragment newInstance(int id) {
        ItemLayoutFragment itemLayoutFragment = new ItemLayoutFragment();
        Bundle args = new Bundle();
        args.putInt(URI_ID, id);
        itemLayoutFragment.setArguments(args);
        return itemLayoutFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int collectionId = 0;
        if (getArguments() != null) {
            collectionId = getArguments().getInt(URI_ID);
        }
        collection = CollectionsRepository.getInstance().getCollection(collectionId);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_item_layout, container, false);

        previewLayout = root.findViewById(R.id.preview_layout);
        previewLayout.setOnClickListener(this);

        detailsLayout = root.findViewById(R.id.details_layout);

        root.findViewById(R.id.close_details_button).setOnClickListener(this);
        root.findViewById(R.id.visit_collection_button).setOnClickListener(this);

        ((TextView)root.findViewById(R.id.collection_number_text_view)).setText(collection.getTitle());
        ((TextView)root.findViewById(R.id.collection_description_text_view)).setText(collection.getDescription());


        imageView = root.findViewById(R.id.page_image_view);

        recyclerView = root.findViewById(R.id.details_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        showPreview();

        return root;
    }

    private void showDetails() {
        previewLayout.setVisibility(View.GONE);
        detailsLayout.setVisibility(View.VISIBLE);
        recyclerView.setAdapter(new RecyclerViewAdapter(collection.getPreviewPhotos()));
    }

    private void showPreview() {
        detailsLayout.setVisibility(View.GONE);
        previewLayout.setVisibility(View.VISIBLE);
        Glide.with(this)
                .load(Uri.parse(collection.getPreviewPhotos().get(0).getUrls().getSmall()))
                .apply(new RequestOptions().placeholder(R.drawable.ic_no_image))
                .into(imageView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.preview_layout:
                showDetails();
                break;
            case R.id.close_details_button:
                showPreview();
                break;
            case R.id.visit_collection_button: {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(collection.getLinks().getHtml()));
                startActivity(browserIntent);
                break;
            }
        }
    }

    public class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {
        private List<Photo> photos;

        RecyclerViewAdapter(List<Photo> photos){
            this.photos = photos;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_layout, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            if (getContext() != null) {
                Glide.with(getContext())
                        .load(Uri.parse(photos.get(position).getUrls().getSmall()))
                        .apply(new RequestOptions().centerCrop())
                        .into(holder.imageView);
            }
        }

        @Override
        public int getItemCount() {
            return photos.size();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView;
        }
    }
}
