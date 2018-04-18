package com.kalpeshgupta.getextra;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn;
        btn = (Button) findViewById(R.id.button) ;
        final EditText et1;
        et1 = findViewById(R.id.et1) ;

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String text = et1.getText().toString() ;
                Intent intent = new Intent(MainActivity.this,Activity2.class) ;
                intent.putExtra("TEXT1",text) ;
                startActivity(intent);
            }
        });

    }
}
