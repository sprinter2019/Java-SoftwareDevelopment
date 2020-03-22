package edu.gatech.seclass.words6300;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ViewWordBankActivity extends AppCompatActivity {

    private TextView textViewPrintWordBank;
    private DatabaseAccessHelper databaseAccessHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_word_bank);

        databaseAccessHelper = DatabaseAccessHelper.getInstance(this);

        textViewPrintWordBank = findViewById(R.id.textViewPrintWordBank);
        textViewPrintWordBank.setMovementMethod(new ScrollingMovementMethod());
        textViewPrintWordBank.setText(databaseAccessHelper.getWordBankAsString());

    }
}
