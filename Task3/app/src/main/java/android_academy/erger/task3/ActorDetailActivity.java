package android_academy.erger.task3;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class ActorDetailActivity extends AppCompatActivity {

    private static final String ACTOR_NAME_EXTRA = "NAME";
    private static final String ACTOR_LARGE_PHOTO_EXTRA = "LARGE_PHOTO";
    private static final String ACTOR_BIO_EXTRA = "BIO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actor_detail);

        Intent startDetailsIntent = getIntent();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(startDetailsIntent.getStringExtra(ACTOR_NAME_EXTRA));
        }

        ImageView actorLargePhoto = findViewById(R.id.large_photo);
        Glide.with(this)
                .load((Uri)startDetailsIntent.getParcelableExtra(ACTOR_LARGE_PHOTO_EXTRA))
                .apply(new RequestOptions().centerCrop())
                .into(actorLargePhoto);

        TextView actorBio = findViewById(R.id.bio);
        actorBio.setText(startDetailsIntent.getStringExtra(ACTOR_BIO_EXTRA));
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            actorBio.setMovementMethod(new ScrollingMovementMethod());
    }

    public static void start(Context context, Actor actor) {
        Intent startDetailsIntent = new Intent(context, ActorDetailActivity.class);

        startDetailsIntent.putExtra(ACTOR_BIO_EXTRA, actor.getBiography());
        startDetailsIntent.putExtra(ACTOR_LARGE_PHOTO_EXTRA, actor.getLargePhoto());
        startDetailsIntent.putExtra(ACTOR_NAME_EXTRA, actor.getName());

        context.startActivity(startDetailsIntent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
