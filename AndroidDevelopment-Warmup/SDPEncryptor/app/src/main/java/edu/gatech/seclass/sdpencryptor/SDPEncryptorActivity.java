package edu.gatech.seclass.sdpencryptor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;
import android.widget.EditText;

public class SDPEncryptorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void handleClick(View view){
        //boolean checked;
        //checked = ((Button) view).isChecked();

        EditText txt1= (EditText) findViewById(R.id.messageInput);
        EditText txt2= (EditText) findViewById(R.id.keyNumber);
        EditText txt3= (EditText) findViewById(R.id.incrementNumber);
        EditText txtCiphered= (EditText) findViewById(R.id.cipherText);

        String textToCipher= txt1.getText().toString();
        Integer key=Integer.parseInt(txt2.getText().toString());
        Integer increment=Integer.parseInt(txt3.getText().toString());

        boolean error=false;

        if((textToCipher.length()==0)||(!textToCipher.matches(".*[a-zA-Z].*"))){
            error=true;
            txt1.setError("Invalid Message");
        }

        if((key<1)||key>26){
            error=true;
            txt2.setError("Invalid Key Number");
        }

        if((increment<1)||increment>26){
            error=true;
            txt3.setError("Invalid Increment Number");
        }

        if(error){
            txtCiphered.setText("");
        }
        else{
            String cipheredText = convertText(textToCipher, key, increment);
            txtCiphered.setText(cipheredText);
        }

    }


    public String convertText(String textToCipher, Integer key, Integer increment){
        String convertedString="";

        int shift = key;
        for(int i=0; i<textToCipher.length();i++){
            if(Character.isLetter(textToCipher.charAt(i))){
                if(Character.isLowerCase(textToCipher.charAt(i))) {
                    convertedString += Character.toString((char) (((textToCipher.charAt(i) - 'a' + shift) % 26) + 'a'));
                }
                else{
                    convertedString += Character.toString((char) (((textToCipher.charAt(i) - 'A' + shift) % 26) + 'A'));
                }

                shift+=increment;
            }
            else{
                convertedString+=textToCipher.charAt(i);
            }
        }

        return convertedString;
    }

}
