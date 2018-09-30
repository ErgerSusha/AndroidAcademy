package android_academy.erger.task5.api;

import java.util.List;
import android_academy.erger.task5.entities.Movie;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    String PAGE = "page";

    @GET("/")
    Call<List<Movie>> getSearch(@Query("s") String query);
}
