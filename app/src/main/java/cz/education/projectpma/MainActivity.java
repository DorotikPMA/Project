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
            content = rates.execute("http://data.fixer.io/api/latest?access_key=360b47de32cb66af0262ed6d0c50863c&symbols=USD,AUD,CAD,PLN,MXN&format=1").get();
            //test
            Log.d("contentData",content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}