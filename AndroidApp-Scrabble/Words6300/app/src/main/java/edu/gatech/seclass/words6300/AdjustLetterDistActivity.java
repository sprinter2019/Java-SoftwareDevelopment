package edu.gatech.seclass.words6300;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class AdjustLetterDistActivity extends AppCompatActivity {

    private Spinner spinnerLetter;
    private EditText editTextLetterPt;
    private EditText editTextNumTiles;
    private DatabaseAccessHelper databaseAccessHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjust_letter_dist);
        databaseAccessHelper = DatabaseAccessHelper.getInstance(this);

        spinnerLetter = findViewById(R.id.spinnerLetter);
        editTextLetterPt = findViewById(R.id.editTextLetterPt);
        editTextNumTiles = findViewById(R.id.editTextNumTiles);

        setupSpinner(databaseAccessHelper);
    }

    public void adjustLetterDist(View view) {

        spinnerLetter = findViewById(R.id.spinnerLetter);
        editTextLetterPt = findViewById(R.id.editTextLetterPt);
        editTextNumTiles = findViewById(R.id.editTextNumTiles);
        boolean hasError = false;
        if (editTextLetterPt.getText().toString() == null || editTextLetterPt.getText().toString().isEmpty() || Integer.parseInt(editTextLetterPt.getText().toString()) <= 0) {
            hasError = true;
            if (editTextLetterPt.getText().toString() == null || editTextLetterPt.getText().toString().isEmpty()) {
                editTextLetterPt.setError("Enter a value!");
            } else {
                editTextLetterPt.setError("Value must be greater than 0!");
            }
        }
        if (editTextNumTiles.getText().toString() == null || editTextNumTiles.getText().toString().isEmpty() || Integer.parseInt(editTextNumTiles.getText().toString()) <= 0) {
            hasError = true;
            if (editTextNumTiles.getText().toString() == null || editTextNumTiles.getText().toString().isEmpty()) {
                editTextNumTiles.setError("Enter a value!");
            } else {
                editTextNumTiles.setError("Value must be greater than 0!");
            }
        }
        if (hasError)
            return;

        int letterPt = Integer.parseInt(editTextLetterPt.getText().toString());
        int numTiles = Integer.parseInt(editTextNumTiles.getText().toString());
        String response = databaseAccessHelper.updateLetterSettings(spinnerLetter.getSelectedItem().toString(), numTiles, letterPt);
        Toast.makeText(this, response, Toast.LENGTH_LONG).show();


     /*   Intent intent = new Intent(this, AdjustActivity.class);
        startActivity(intent);*/
    }


    private void setupSpinner(final DatabaseAccessHelper databaseAccessHelper) {
        List<Character> alphabet = databaseAccessHelper.getAlphabet();
        ArrayAdapter<Character> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, alphabet);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerLetter.setAdapter(dataAdapter);
        spinnerLetter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Character c = (Character) parent.getItemAtPosition(position);
                LetterSetting setting = databaseAccessHelper.getLetterSetting(c);
                editTextLetterPt.setText("" + setting.getPoints());
                editTextNumTiles.setText("" + setting.getFrequency());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }
}
