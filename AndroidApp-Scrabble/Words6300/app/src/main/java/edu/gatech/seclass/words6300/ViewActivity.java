package edu.gatech.seclass.words6300;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
    }

    public void viewGameScores(View view) {
        Intent intent = new Intent(this, ViewGameScoreActivity.class);
        startActivity(intent);
    }

    public void viewLetterStatistics(View view) {
        Intent intent = new Intent(this, ViewLetterStatsActivity.class);
        startActivity(intent);
    }

    public void viewWordBank(View view) {
        Intent intent = new Intent(this, ViewWordBankActivity.class);
        startActivity(intent);
    }
}
