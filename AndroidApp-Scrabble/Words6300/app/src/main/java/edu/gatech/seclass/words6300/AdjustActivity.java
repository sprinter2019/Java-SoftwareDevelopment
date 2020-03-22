package edu.gatech.seclass.words6300;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class AdjustActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjust);
    }

    public void viewAdjustMaxTurns(View view) {
        Intent intent = new Intent(this, AdjustMaxTurnsActivity.class);
        startActivity(intent);
    }

    public void viewAdjustLetterSettings(View view) {
        Intent intent = new Intent(this, AdjustLetterDistActivity.class);
        startActivity(intent);
    }
}
