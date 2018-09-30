package android_academy.erger.task3;

import android.net.Uri;

public class Actor {

    private final String name;
    private final Uri smallPhoto;
    private final Uri largePhoto;
    private final String biography;

    public Actor(String name, Uri smallPhoto, Uri largePhoto, String biography) {
        this.name = name;
        this.smallPhoto = smallPhoto;
        this.largePhoto = largePhoto;
        this.biography = biography;
    }

    public String getName() {
        return name;
    }

    public Uri getSmallPhoto() {
        return smallPhoto;
    }

    public Uri getLargePhoto() {
        return largePhoto;
    }

    public String getBiography() {
        return biography;
    }

    @Override
    public String toString() {
        return "Actor: " + name +
                "\nphoto: " + smallPhoto +
                "\nlarge photo: " + largePhoto +
                "\nbiography: " + biography;
    }
}