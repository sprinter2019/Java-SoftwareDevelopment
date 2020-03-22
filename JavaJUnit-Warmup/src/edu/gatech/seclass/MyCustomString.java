package edu.gatech.seclass;

import java.util.HashMap;
import java.util.Map;


public class MyCustomString implements MyCustomStringInterface{

    private String customString=null;

    //Getter method
    public String getString(){
        return customString;
    }

    //Setter method
    public void setString(String string){
        this.customString=string;
    }

    // Find the most common character
    public char mostCommonChar() throws NullPointerException {
        String str=this.customString.toLowerCase();
        char mostFrequent='X';
        int maxFrequent=0;
        Map<Character, Integer> map = new HashMap<>();
        int charFrequency[]= new int[256];

        for (int i=0; i<str.length(); i++){

            char ch = str.charAt(i);

            if (Character.isLetterOrDigit(ch)){
                charFrequency[ch]++;
            }
        }

        for (int i=0; i<str.length();i++){
            if (maxFrequent<charFrequency[str.charAt(i)]){
                maxFrequent=charFrequency[str.charAt(i)];
                mostFrequent=str.charAt(i);
            }
        }

       // System.out.println(mostFrequent);

        return mostFrequent;
    }

    public String filterLetters (char n, boolean more) throws NullPointerException, IllegalArgumentException {
        String filteredString = this.customString;

        if ((!Character.isLetter(n))&&(this.customString.length()!=0)){
            throw new IllegalArgumentException();
        }

        if (more) {
            for (char ch= Character.toUpperCase(n); ch<='Z'; ch++){
                String filter="(?i)"+Character.toString(ch);
                filteredString=filteredString.replaceAll(filter,"");
            }
        }
        if (!more) {
            for (char ch= Character.toUpperCase(n); ch>='A'; ch--){
                String filter="(?i)"+Character.toString(ch);
                filteredString=filteredString.replaceAll(filter,"");
            }
        }

        //System.out.println(filteredString);
        return filteredString;
    }

    public void convertDigitsToProductsInSubstring(int startPosition, int endPosition) throws IllegalArgumentException, MyIndexOutOfBoundsException {

        if ((startPosition<1) || (startPosition>endPosition)){
            throw new IllegalArgumentException();
        }

        try{
            String str = this.customString;

             if ((endPosition>str.length())&&(startPosition>=1)&&(endPosition>=startPosition)){
                throw new MyIndexOutOfBoundsException();
            }

            String head=str.substring(0, startPosition-1);
            String tail=str.substring(endPosition);

            String convertedString;
            StringBuilder filter=new StringBuilder();
            int product=1;
            for (int i=startPosition-1; i<endPosition;i++){
                if(Character.isDigit(str.charAt(i))){
                    product*=Character.getNumericValue(str.charAt(i));
                    if((!Character.isDigit(str.charAt(i+1)))||(i==endPosition-1)){

                        filter.append(Integer.toString(product));
                        product=1;
                    }
                }
                else {
                    filter.append(str.charAt(i));
                }
            }

            convertedString=head+filter.toString()+tail;

            setString(convertedString);

            //System.out.println(convertedString);
        } catch (NullPointerException e){
            throw new MyIndexOutOfBoundsException();
        }
    }

}
