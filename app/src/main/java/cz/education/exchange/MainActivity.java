package cz.education.exchange;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import android.util.Log;
import android.os.Environment;
import java.io.File;
import java.io.FileOutputStream;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MMMMM";
    final static String appFileName = "appFile.txt";
    final static String appDirName = "/appDir/";
    final static String appDirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + appDirName;


    EditText rated;
    EditText howMuch;
    EditText from;
    Spinner spinner;
    Button button;
    String symbol = "CZK";
    double kolik;
    String hodnota;

    //JSON
    JSONObject obj;
    String base;
    String date;
    double kurz;
    double exchanged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        //inicializce
        rated = (EditText) findViewById(R.id.rated);
        rated.setEnabled(false);
        howMuch = (EditText) findViewById(R.id.howMuch);
        howMuch.setText("999");
        from = (EditText) findViewById(R.id.from);
        from.setText("EUR");
        spinner = (Spinner) findViewById(R.id.spinner);
        symbol = spinner.getSelectedItem().toString();
        button = (Button) findViewById(R.id.button);

        kolik = howMuchToVar();

        Log.d("TEST - spinner", symbol);
        Log.d("TEST - Kolik", Double.toString(kolik));
        getJson();

    }

    public double howMuchToVar(){
        String tmp = howMuch.getText().toString();
        tmp = tmp.replace(" ", "");
        if(tmp.isEmpty()){
            tmp="0";
        }
        double var = Double.parseDouble(tmp);

            return var;

    }


    public void myMoreButtonClick(View v)
    {
        Toast.makeText(this, "Button \"MORE\" has been clicked.", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, SecondActivity.class);
        if(obj!=null){

        //write your code here

        }else{
        //json is null

            Toast.makeText(this, "Firstly tap Rate button, sir", Toast.LENGTH_LONG).show();
        }
        intent.putExtra("jsonObject", obj.toString());
        intent.putExtra("symbol", symbol);
        intent.putExtra("hodnota", hodnota);
        intent.putExtra("kolik", kolik);
        intent.putExtra("rated", exchanged);
        startActivity(intent);
    }




    public void myButtonClick(View v)
    {
        //hodnota inputu
        kolik = howMuchToVar();

        //vybrana mena
        symbol = spinner.getSelectedItem().toString();

        getJson();


    }


    private void getJson(){

        String url = "https://api.exchangeratesapi.io/latest?symbols=" + symbol;

        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                parseJsonData(string); // Pošle získané data do parseru
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), volleyError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue rQueue = Volley.newRequestQueue(MainActivity.this);
        rQueue.add(request);
    }

    public void parseJsonData(String jsonString){

        try {
            //vytvori JSONobj ze stringu
            obj = new JSONObject(jsonString);


            base = obj.getString("base");
            Log.d("Base", base);
            date = obj.getString("date");
            Log.d("Date", date);

            JSONObject rateData = obj.getJSONObject("rates");
            hodnota = rateData.getString(symbol);
            Log.d("Kurz",hodnota );

            kurz = Double.parseDouble(hodnota);

            exchanged = kolik * kurz;

            rated.setText(Double.toString(exchanged));

            Toast.makeText(this, "Kurz: "+kurz + symbol, Toast.LENGTH_LONG).show();



        } catch (Throwable t) {
            Log.e("My App", "Could not parse malformed JSON: \"" + jsonString + "\"");
        }


    }




}



