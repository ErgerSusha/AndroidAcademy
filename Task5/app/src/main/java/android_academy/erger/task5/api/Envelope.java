package android_academy.erger.task5.api;

import com.google.gson.annotations.SerializedName;

public class Envelope<T> {

    @SerializedName("Search")
    private T data;

    public T getData() {
        return data;
    }
}
