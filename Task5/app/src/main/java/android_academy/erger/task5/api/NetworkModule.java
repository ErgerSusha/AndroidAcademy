package android_academy.erger.task5.api;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import static android_academy.erger.task5.api.Api.PAGE;
import static android_academy.erger.task5.api.Constants.API_HOST;
import static android_academy.erger.task5.api.Constants.API_KEY;

public class NetworkModule {

    private static final long HTTP_CACHE_SIZE = 1024 * 1024 * 24L;

    private final Api api;

    private int pageNumber = 0;

    public NetworkModule(Context context) {
        final Gson gson = provideGson();
        final Interceptor authInterceptor = provideAuthRequestInterceptor();
        final Interceptor pageInterceptor = providePageRequestInterceptor();
        final OkHttpClient okHttpClient = provideOkHttp(context.getCacheDir(), authInterceptor, pageInterceptor);
        final Retrofit retrofit = provideRetrofit(okHttpClient, gson);
        api = provideApi(retrofit);

        providePicasso(context, okHttpClient);
    }

    public Api getApi() {
        return api;
    }

    private OkHttpClient provideOkHttp(
            File cacheDir,
            Interceptor authInterceptor,
            Interceptor pageInterceptor
    ) {
        return new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(authInterceptor)
                .addInterceptor(pageInterceptor)
                .cache(new Cache(new File(cacheDir, "http"), HTTP_CACHE_SIZE))
                .build();
    }

    private Gson provideGson() {
        return new GsonBuilder().create();
    }

    private Retrofit provideRetrofit(
            OkHttpClient okHttp,
            Gson gson
    ) {
        return new Retrofit.Builder() //
                .baseUrl(API_HOST)
                .addConverterFactory(new EnvelopingConverter())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttp)
                .build();
    }

    private Api provideApi(Retrofit retrofit) {
        return retrofit.create(Api.class);
    }

    private Interceptor provideAuthRequestInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                final Request originalRequest = chain.request();
                final HttpUrl newUrl = originalRequest.url().newBuilder()
                        .addQueryParameter("apikey", API_KEY)
                        .build();

                final Request newRequest = originalRequest.newBuilder()
                        .url(newUrl)
                        .addHeader("Accept", "application/json")
                        .build();

                return chain.proceed(newRequest);
            }
        };
    }

    private Interceptor providePageRequestInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                final Request originalRequest = chain.request();
                final HttpUrl originalHttpUrl = originalRequest.url();

                final HttpUrl newUrl = originalHttpUrl.newBuilder()
                        .addQueryParameter(PAGE, String.valueOf(++pageNumber))
                        .build();

                final Request newRequest = originalRequest.newBuilder()
                        .url(newUrl)
                        .build();

                return chain.proceed(newRequest);
            }
        };
    }

    private Picasso providePicasso(
            Context context,
            OkHttpClient httpClient
    ) {
        final Picasso picasso = new Picasso.Builder(context)
                //.indicatorsEnabled(BuildConfig.DEBUG)
                .downloader(new OkHttp3Downloader(httpClient))
                .build();

        // Setup global Picasso instance.
        Picasso.setSingletonInstance(picasso);
        return picasso;
    }
}
