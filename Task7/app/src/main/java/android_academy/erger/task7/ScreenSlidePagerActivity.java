package android_academy.erger.task7;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import android_academy.erger.task7.api.UnsplashApiHelper;
import android_academy.erger.task7.entities.Collection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScreenSlidePagerActivity extends FragmentActivity {

    private ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);

        pager = findViewById(R.id.pager);

        new UnsplashApiHelper().service().getFeaturedCollections().enqueue(new Callback<List<Collection>>() {
            @Override
            public void onResponse(@NonNull Call<List<Collection>> call, @NonNull Response<List<Collection>> response) {
                List<Collection> collections = response.body();
                if (collections != null) {
                    for (Collection collection : collections) {
                        CollectionsRepository.getInstance().saveCollection(collection);
                    }
                    collections = CollectionsRepository.getInstance().getCollections();

                    List<ItemLayoutFragment> fragments = new ArrayList<>();
                    for (Collection collection : collections) {
                        fragments.add(ItemLayoutFragment.newInstance(collection.getId()));
                    }
                    PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), fragments);

                    pager.setAdapter(adapter);
                } else {
                    showErrorToast();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Collection>> call, @NonNull Throwable t) {
                showErrorToast();
            }
        });
    }

    private void showErrorToast() {
        Toast.makeText(this, R.string.error_message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if (pager.getCurrentItem() == 0)
            super.onBackPressed();
        else
            pager.setCurrentItem(0);
    }

    private class PagerAdapter extends FragmentStatePagerAdapter {
        private List<ItemLayoutFragment> fragments;

        PagerAdapter(FragmentManager fm, List<ItemLayoutFragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}