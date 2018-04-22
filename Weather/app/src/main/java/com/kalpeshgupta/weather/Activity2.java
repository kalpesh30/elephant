package com.kalpeshgupta.weather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Activity2 extends AppCompatActivity {

    EditText city,country;
    Button bt1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        city = findViewById(R.id.citnm) ;
        country = findViewById(R.id.countnm) ;
        bt1 = findViewById(R.id.but5) ;
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity2.this,MainActivity.class) ;
                Log.i("param" , city.getText().toString() + "," + country.getText().toString());
                intent.putExtra("place",city.getText().toString() + "," + country.getText().toString()) ;
                startActivity(intent);
            }
        });


    }
}
