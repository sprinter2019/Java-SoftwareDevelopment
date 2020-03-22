package edu.gatech.seclass.words6300;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ViewGameScoreActivity extends AppCompatActivity implements
        MyRecyclerViewAdapter.ItemClickListener {

    private RecyclerView recyclerViewScores;
    private DatabaseAccessHelper databaseAccessHelper;

    private MyRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_game_score);

        databaseAccessHelper = DatabaseAccessHelper.getInstance(this);

        recyclerViewScores = (RecyclerView) findViewById(R.id.RecyclerViewScores);

        DividerItemDecoration divider = new DividerItemDecoration(recyclerViewScores.getContext(), DividerItemDecoration.VERTICAL);
        recyclerViewScores.addItemDecoration(divider);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewScores.setLayoutManager(layoutManager);

        List<String> records = databaseAccessHelper.getGameStatisticsAsList();

        mAdapter = new MyRecyclerViewAdapter(this, records);
        if (mAdapter.getItemCount() != 0) {
            recyclerViewScores.setAdapter(mAdapter);
            mAdapter.setClickListener(this);
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, ViewGameScoreInstanceActivity.class);
        intent.putExtra("RECORD_ID", position);
        intent.putExtra("RECORD_SCORE", mAdapter.getItem(position));
        startActivity(intent);
    }
}
