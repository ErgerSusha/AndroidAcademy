package android_academy.erger.task3;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_actors);

        RecyclerView list = findViewById(R.id.actorRecyclerView);
        list.addItemDecoration(new MarginItemDecoration(getResources().getDimensionPixelOffset(R.dimen.margin_actor_photo)));

        GridLayoutManager layoutManager;
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            layoutManager = new GridLayoutManager(this, 2);
        else
            layoutManager = new GridLayoutManager(this, 4);
        list.setLayoutManager(layoutManager);

        List<Actor> actors = DataUtil.generateActors();
        ActorRecyclerAdapter adapter = new ActorRecyclerAdapter(actors, this,
                (((position, v) -> ActorDetailActivity.start(this, actors.get(position)))));
        list.setAdapter(adapter);
    }
}