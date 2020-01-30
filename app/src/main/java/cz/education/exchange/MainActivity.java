package cz.education.exchange;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {


    private TextView f;

    private void getJson(){
        String url = "https://api.exchangeratesapi.io/latest?base=CZK";

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
            JSONObject obj = new JSONObject(jsonString);

            JSONObject rateData = obj.getJSONObject("rates");
            String czk = rateData.getString("CZK");
            String cad = rateData.getString("CAD");
            String pln = rateData.getString("PLN");
            String aud = rateData.getString("AUD");
            String mxn = rateData.getString("MXN");
            String usd = rateData.getString("USD");
            String nok = rateData.getString("NOK");
            String nzd = rateData.getString("NZD");
            String rub = rateData.getString("RUB");
            String hrk = rateData.getString("HRK");
            String jpy = rateData.getString("JPY");
            String eur = rateData.getString("EUR");
            String gbp = rateData.getString("GBP");




            Log.d("Rates",rateData.toString() );
            Log.d("CZK",czk );

            String base = obj.getString("base");
            Log.d("Base", base);
            String date = obj.getString("date");
            Log.d("Date", date);
            f.setText(base);

        } catch (Throwable t) {
            Log.e("My App", "Could not parse malformed JSON: \"" + jsonString + "\"");
        }


    }



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


        EditText mEdit = (EditText) findViewById(R.id.rated);
        mEdit.setEnabled(false);
        f = findViewById(R.id.from);
        getJson();


        Log.i("HEllo World","whats'up");
    }

}
