package cz.education.projectpma;


import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    class Rates extends AsyncTask<String,Void,String>{ // Stringg - URL, void, String - navratova hodnota

        @Override
        protected String doInBackground(String... address) {
            //String.. znamena, ze muze byt poslano vice adres, tvari se jako pole
            try {
                URL url = new URL(address[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                //nastavuje connection na adresu
                connection.connect();

                //ziskani dat z url
                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);

                //ziskani dat a vraceni jako string
                int data = isr.read();
                String content = "";
                char ch;
                while (data != 0) {
                    ch = (char) data;
                    content = content + ch;
                    data = isr.read();
                }
                Log.i("Content",content);
                return content;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("HEllo World","whats'up");

        String content;
        Rates rates =  new Rates();
        try {
            content = rates.execute("https://openweathermap.org/data/2.5/weather?q=London&appid=b6907d289e10d714a6e88b30761fae22").get();
            //test
            Log.d("contentData",content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}