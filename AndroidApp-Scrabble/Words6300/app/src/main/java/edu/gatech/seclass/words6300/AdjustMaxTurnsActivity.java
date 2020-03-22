package edu.gatech.seclass.words6300;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class AdjustMaxTurnsActivity extends AppCompatActivity {

    private EditText editTextMaxTurns;
    private DatabaseAccessHelper databaseAccessHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjust_max_turns);
        databaseAccessHelper = DatabaseAccessHelper.getInstance(this);
        editTextMaxTurns = (EditText) findViewById(R.id.editTextMaxTurns);
    }

    public void adjustMaxTurn(View view) {
        editTextMaxTurns = (EditText) findViewById(R.id.editTextMaxTurns);
        if (editTextMaxTurns.getText().toString() == null || editTextMaxTurns.getText().toString().isEmpty()) {
            editTextMaxTurns.setError("Enter a value!");
            return;
        }
        int maxTurn = Integer.parseInt(editTextMaxTurns.getText().toString());

        if (maxTurn <= 0) {
            editTextMaxTurns.setError("Value must be greater than 0!");
        } else {
            String response = databaseAccessHelper.updateMaxTurnSettings(maxTurn);
            Toast.makeText(this, response, Toast.LENGTH_LONG).show();

        }
    }


}
