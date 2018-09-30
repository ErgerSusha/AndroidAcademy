package android_academy.erger.task7.api;

import java.util.List;

import android_academy.erger.task7.entities.Collection;
import retrofit2.Call;
import retrofit2.http.GET;

public interface UnsplashService {
    @GET("/collections/curated/?client_id=563ae14ff629753e14225bc724974f442b1565598d5e9b803216226fc5ba7c2a")
    Call<List<Collection>> getFeaturedCollections();
}
