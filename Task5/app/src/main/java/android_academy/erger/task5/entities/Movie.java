package android_academy.erger.task5.entities;

import com.google.gson.annotations.SerializedName;

public class Movie {

    @SerializedName("Title")
    private String title;
    @SerializedName("Poster")
    private String poster;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}