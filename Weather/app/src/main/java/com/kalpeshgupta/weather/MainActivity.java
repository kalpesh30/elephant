package com.kalpeshgupta.weather;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Formatter;

public class MainActivity extends AppCompatActivity {

    int cl = 0;
    String place;
    private String TAG = MainActivity.class.getSimpleName();
    private TextView tv1,tv2, tv3;
    private String temperature,ID;
    private EditText et1,et2;
    private Double temp;
   // private SwipeRefreshLayout slayout;
    //private Switch sw1;

    /*@Override
    public boolean OnCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu1,menu);
        return true;

    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1 = findViewById(R.id.kelvin);
        tv2 = findViewById(R.id.celcius);
        tv3 = findViewById(R.id.tv1) ;
        /*sw1 = findViewById(R.id.sw_1);
        //Log.v("text ON ->", getString(R.string.but_On));
        sw1.setTextOn(getString(R.string.but_On)) ;
        //Log.v("text ON ->", getString(R.string.but_off));
        sw1.setTextOff(getString(R.string.but_off)); ;*/
        /*slayout = findViewById(R.id.swipe1) ;
        slayout.setColorSchemeResources(R.color.colorAccent);
        slayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new GetTemperature().execute(et1.getText().toString(), et2.getText().toString());
                slayout.setRefreshing(false);
            }
        });*/
        /*MenuItem pop = new Menu();
        pop.getItem(R.menu.menu1);
        onOptionsItemSelected(pop.getItem(R.id.it1));*/
        place = getIntent().getExtras().getString("place");
       /* StringBuilder s2 = new StringBuilder();
        StringBuilder s3 = new StringBuilder();
        int i;
        for(i = 0 ; i<s1.length(); i++)
        {
            if(s1.charAt(i) == '_')
                break;
            s2.append(s1.charAt(i));
        }
        for(i++;i<s1.length();i++)
        {
            s3.append(s1.charAt(i));
        }*/
        new GetTemperature().execute(place);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu1,menu);
        return true;
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
        final AlertDialog dialog = dialogBuilder.create();

        et1 = dialogView.findViewById(R.id.city);
        et2 = dialogView.findViewById(R.id.coun);
        Button b1 = dialogView.findViewById(R.id.but1);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                cl++;
                new GetTemperature().execute(et1.getText().toString(), et2.getText().toString());
            }
        });
        dialog.show();
    }


    private class GetTemperature extends AsyncTask<String, String, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this,"Json Data is Downloading",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Void doInBackground(String... voids) {
            HttpHandler sh = new HttpHandler();
            Log.i("City" , voids[0])/*+","+voids[1])*/;
            String url;
            if(cl == 1) {
                url = "http://api.openweathermap.org/data/2.5/weather?q=" + voids[0] + "," + voids[1] + "&appid=fd04edf8988d8b3c25febc0874545232";
            }
            else
                url = "http://api.openweathermap.org/data/2.5/weather?q=" + voids[0] + "&appid=fd04edf8988d8b3c25febc0874545232";

            String jsonStr = sh.makeServiceCall(url);
            Log.e(TAG,"Response from URL : " + jsonStr);
            if(jsonStr != null){
                try{
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONObject temp = jsonObj.getJSONObject("main");
                    JSONArray id = jsonObj.getJSONArray("weather") ;
                    Log.e("id data","-> Activity id received" );
                    JSONObject idobj = id.getJSONObject(0) ;
                    ID = idobj.getString("id") ;
                    Log.e("id data","-> Activity id received" + ID);

                    temperature = temp.getString("temp");
                    Log.v("tag - >","I was here");
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
            TextView tv4;
            tv4 = findViewById(R.id.tv4) ;
            Integer id = Integer.parseInt(ID) ;
            String t1 = Checkicon(id) ;
            //setTypeface(tv4);

            String s1 = "fonts/weathericons-regular-webfont.ttf" ;
            Typeface tf = Typeface.createFromAsset(getAssets(),s1) ;
            Log.e("Typeface created",tf.toString()) ;
            tv4.setTypeface(tf);

            tv4.setText(t1) ;
            degCelcius = degCelcius - 273.15 ;
            Formatter fmt = new Formatter();
            if(cl == 1) {
                tv3.setText("The Temperature of " + et1.getText().toString() + ", " + et2.getText().toString() + " is ");
            }
            else
                tv3.setText("The Temperature of " + place  + " is ");
                tv2.setText(fmt.format("%.3f",degCelcius).toString() + " deg. Celcius");
            tv1.setText(temperature + " Kelvin");
        }
    }

    private void setTypeface(TextView tv)
    {
        String s1 = "fonts/weathericons-regular-webfont.ttf" ;
        Typeface tf = Typeface.createFromAsset(getAssets(),s1) ;
        Log.e("Typeface created",tf.toString()) ;
        tv.setTypeface(tf);
    }

    String Checkicon (int n)
    {
        String icon;
        switch(n) {
            case 200:
                icon = getString(R.string.wi_owm_200);
                break;
            case 201:
                icon = getString(R.string.wi_owm_201);
                break;
            case 202:
                icon = getString(R.string.wi_owm_202);
                break;
            case 210:
                icon = getString(R.string.wi_owm_210);
                break;
            case 211:
                icon = getString(R.string.wi_owm_211);
                break;
            case 212:
                icon = getString(R.string.wi_owm_212);
                break;
            case 221:
                icon = getString(R.string.wi_owm_221);
                break;
            case 230:
                icon = getString(R.string.wi_owm_230);
                break;
            case 231:
                icon = getString(R.string.wi_owm_231);
                break;
            case 232:
                icon = getString(R.string.wi_owm_232);
                break;
            case 300:
                icon = getString(R.string.wi_owm_300);
                break;
            case 301:
                icon = getString(R.string.wi_owm_301);
                break;
            case 302:
                icon = getString(R.string.wi_owm_302);
                break;
            case 310:
                icon = getString(R.string.wi_owm_310);
                break;
            case 311:
                icon = getString(R.string.wi_owm_311);
                break;
            case 312:
                icon = getString(R.string.wi_owm_312);
                break;
            case 313:
                icon = getString(R.string.wi_owm_313);
                break;
            case 314:
                icon = getString(R.string.wi_owm_314);
                break;
            case 321:
                icon = getString(R.string.wi_owm_321);
                break;
            case 500:
                icon = getString(R.string.wi_owm_500);
                break;
            case 501:
                icon = getString(R.string.wi_owm_501);
                break;
            case 502:
                icon = getString(R.string.wi_owm_502);
                break;
            case 503:
                icon = getString(R.string.wi_owm_503);
                break;
            case 504:
                icon = getString(R.string.wi_owm_504);
                break;
            case 511:
                icon = getString(R.string.wi_owm_511);
                break;
            case 520:
                icon = getString(R.string.wi_owm_520);
                break;
            case 521:
                icon = getString(R.string.wi_owm_521);
                break;
            case 522:
                icon = getString(R.string.wi_owm_522);
                break;
            case 531:
                icon = getString(R.string.wi_owm_531);
                break;
            case 600:
                icon = getString(R.string.wi_owm_600);
                break;
            case 601:
                icon = getString(R.string.wi_owm_601);
                break;
            case 602:
                icon = getString(R.string.wi_owm_602);
                break;
            case 611:
                icon = getString(R.string.wi_owm_611);
                break;
            case 612:
                icon = getString(R.string.wi_owm_612);
                break;
            case 615:
                icon = getString(R.string.wi_owm_615);
                break;
            case 616:
                icon = getString(R.string.wi_owm_616);
                break;
            case 620:
                icon = getString(R.string.wi_owm_620);
                break;
            case 621:
                icon = getString(R.string.wi_owm_621);
                break;
            case 622:
                icon = getString(R.string.wi_owm_622);
                break;
            case 701:
                icon = getString(R.string.wi_owm_701);
                break;
            case 711:
                icon = getString(R.string.wi_owm_711);
                break;
            case 721:
                icon = getString(R.string.wi_owm_721);
                break;
            case 731:
                icon = getString(R.string.wi_owm_731);
                break;
            case 741:
                icon = getString(R.string.wi_owm_741);
                break;
            case 761:
                icon = getString(R.string.wi_owm_761);
                break;
            case 762:
                icon = getString(R.string.wi_owm_762);
                break;
            case 771:
                icon = getString(R.string.wi_owm_771);
                break;
            case 781:
                icon = getString(R.string.wi_owm_781);
                break;
            case 800:
                icon = getString(R.string.wi_owm_800);
                break;
            case 801:
                icon = getString(R.string.wi_owm_801);
                break;
            case 802:
                icon = getString(R.string.wi_owm_802);
                break;
            case 803:
                icon = getString(R.string.wi_owm_803);
                break;
            case 804:
                icon = getString(R.string.wi_owm_804);
                break;
            case 900:
                icon = getString(R.string.wi_owm_900);
                break;
            case 901:
                icon = getString(R.string.wi_owm_901);
                break;
            case 902:
                icon = getString(R.string.wi_owm_902);
                break;
            case 903:
                icon = getString(R.string.wi_owm_903);
                break;
            case 904:
                icon = getString(R.string.wi_owm_904);
                break;
            case 905:
                icon = getString(R.string.wi_owm_905);
                break;
            case 906:
                icon = getString(R.string.wi_owm_906);
                break;
            case 957:
                icon = getString(R.string.wi_owm_957);
                break;
            default:
                icon = Integer.toString(n);
                break;
        }
     return icon ;
    }

}

