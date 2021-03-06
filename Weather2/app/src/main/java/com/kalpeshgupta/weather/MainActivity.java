package com.kalpeshgupta.weather;

import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    private EditText et1,et2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1 = findViewById(R.id.kelvin);
        tv2 = findViewById(R.id.celcius);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        switch(id)
        {
            case R.id.it1 : SearchDialog(); break;
        }
        return true;
    }

    public void SearchDialog(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog,null);
        dialogBuilder.setView(dialogView);
        et1 = (EditText)findViewById(R.id.city);
        et2 = (EditText)findViewById(R.id.coun);
        Button b1 = (Button) findViewById(R.id.but1);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GetTemperature().execute(et1.toString(),et2.toString());
            }
        });



    }
    private class GetTemperature extends AsyncTask<String, String, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this,"Json Data is Downloading",Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(String... voids) {
            HttpHandler sh = new HttpHandler();
            String url = "http://api.openweathermap.org/data/2.5/weather?q=" + voids[0] + "," + voids[1] + "&appid=fd04edf8988d8b3c25febc0874545232";
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

