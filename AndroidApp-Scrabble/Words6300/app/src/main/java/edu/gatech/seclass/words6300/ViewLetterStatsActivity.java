package edu.gatech.seclass.words6300;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ViewLetterStatsActivity extends AppCompatActivity {

    private TextView textViewPrintLetterStats;
    private DatabaseAccessHelper databaseAccessHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_letter_stats);

        databaseAccessHelper = DatabaseAccessHelper.getInstance(this);

        textViewPrintLetterStats = findViewById(R.id.textViewPrintLetterStats);
        textViewPrintLetterStats.setMovementMethod(new ScrollingMovementMethod());
        textViewPrintLetterStats.setText(databaseAccessHelper.getLetterStatsAsString());
    }

}
