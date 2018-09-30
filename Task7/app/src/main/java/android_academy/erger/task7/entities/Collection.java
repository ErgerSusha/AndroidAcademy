package android_academy.erger.task7.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Collection {
    private int id;
    private String title;
    private String description;
    @SerializedName("preview_photos")
    private List<Photo> previewPhotos;
    private CollectionLinks links;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<Photo> getPreviewPhotos() {
        return previewPhotos;
    }

    public CollectionLinks getLinks() {
        return links;
    }
}
