package cz.education.exchange;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Environment;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class SecondActivity  extends AppCompatActivity {
    private static final String TAG = "MMMMM";
    final static String appFileName = "appFile.txt";
    final static String appDirName = "/appDir/";
    final static String appDirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + appDirName;
    String base;
    String date;
    EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_layout);

        editText = findViewById(R.id.second_layout_edittext);

        Intent intent = getIntent();

        String jsonString = intent.getStringExtra("jsonObject");
        String symbol = intent.getStringExtra("symbol");
        String hodnota = intent.getStringExtra("hodnota");
        String kolik = intent.getStringExtra("kolik");
        String exchanged = intent.getStringExtra("exchanged");
        try {
            JSONObject jObj = new JSONObject(jsonString);
            base = jObj.getString("base");
            Log.d("Base", base);
            date = jObj.getString("date");
            Log.d("Date", date);

            JSONObject rateData = jObj.getJSONObject("rates");
            hodnota = rateData.getString(symbol);
            Log.d("Kurz",hodnota );


            editText.setText(date+ ": " + base + " ==> " +  symbol );


        } catch (JSONException e) {
            e.printStackTrace();
        }



    }


}