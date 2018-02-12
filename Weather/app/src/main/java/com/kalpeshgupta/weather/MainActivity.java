package com.kalpeshgupta.weather;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Formatter;

import static java.lang.Integer.parseInt;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();
    private TextView tv1,tv2;
    private String temperature;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1 = findViewById(R.id.kelvin);
        tv2 = findViewById(R.id.celcius);
        new GetTemperature().execute();
    }

    private class GetTemperature extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this,"Json Data is Downloading",Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();
            String url = "http://api.openweathermap.org/data/2.5/weather?q=London,uk&appid=fd04edf8988d8b3c25febc0874545232";
            String jsonStr = sh.makeServiceCall(url);
            Log.e(TAG,"Response from URL : " + jsonStr);
            if(jsonStr != null){
                try{
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONObject temp = jsonObj.getJSONObject("main");
                    temperature = temp.getString("temp");
                }catch (final JSONException e)
                {
                    Log.e(TAG, "JSON parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"JSON parsing error: " + e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }else {
                Log.e(TAG,"Couldn't get Json from the server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"Couldn't get JSON from server. Check LogCat for possible errors! ",Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            Double degCelcius = Double.parseDouble(temperature);
            degCelcius = degCelcius - 273.15 ;
            Formatter fmt = new Formatter();
            tv2.setText(fmt.format("%.3f",degCelcius).toString() + " deg. Celcius");
            tv1.setText(temperature + " Kelvin");
        }
    }
}

