package android_academy.erger.task5.api;

import android.app.Application;

public class App extends Application {

    private Api api;

    @Override
    public void onCreate() {
        super.onCreate();

        api = new NetworkModule(this).getApi();
    }

    public Api getApi() {
        return api;
    }
}
