package edu.gatech.seclass.words6300;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ViewGameScoreInstanceActivity extends AppCompatActivity {

    private TextView textViewScoreDetails;
    private TextView textViewMaxTurnsNumInView;
    private TextView textViewPrintLetterPtSetting;

    private DatabaseAccessHelper databaseAccessHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_game_score_instance);

        databaseAccessHelper = DatabaseAccessHelper.getInstance(this);

        Bundle extras = getIntent().getExtras();

        textViewScoreDetails = (TextView) findViewById(R.id.textViewScoreDetails);
        String scoreDetails = extras.getString("RECORD_SCORE");
        textViewScoreDetails.setText(scoreDetails);

        int gameID = Integer.parseInt(scoreDetails.substring(scoreDetails.lastIndexOf(',') + 1).trim());

        textViewMaxTurnsNumInView = (TextView) findViewById(R.id.textViewMaxTurnsNumInView);
        textViewMaxTurnsNumInView.setText(databaseAccessHelper.getMaxTurnAsString(gameID));

        textViewPrintLetterPtSetting = (TextView) findViewById(R.id.textViewPrintLetterPtSetting);
        textViewPrintLetterPtSetting.setMovementMethod(new ScrollingMovementMethod());
        textViewPrintLetterPtSetting.setText(databaseAccessHelper.getLetterSettingAsString(gameID));

    }
}
