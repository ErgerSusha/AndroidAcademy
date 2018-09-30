package android_academy.erger.task7.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UnsplashApiHelper {
    private Retrofit retrofit;

    public UnsplashApiHelper() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.unsplash.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public UnsplashService service() {
        return retrofit.create(UnsplashService.class);
    }
}
