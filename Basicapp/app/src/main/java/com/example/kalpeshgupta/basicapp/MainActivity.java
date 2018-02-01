package com.example.kalpeshgupta.basicapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationBuilderWithBuilderAccessor;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.nio.channels.Channel;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private String Operand1;
    private String Operand2;

    private TextView tvCalculate;

    Button btn;

    public String getOperand1() {
        return Operand1;
    }

    public void setOperand1(String operand1) {
        Operand1 = operand1;
    }

    public String getOperand2() {
        return Operand2;
    }

    public void setOperand2(String operand2) {
        Operand2 = operand2;
    }

    EditText etoperand1;
    EditText etoperand2;
    String title,body;
    Spinner myspin;
    NotificationHelper helper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Spinner spinner = findViewById(R.id.spi_operator);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Operators, android.R.layout.simple_spinner_dropdown_item);
                adapter.setDropDownViewResource(R.layout.customer_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tvCalculate = findViewById(R.id.tv_result);
        spinner.setAdapter(adapter);
        btn = findViewById(R.id.but1);
        //myspin = findViewById(R.id.spi_operator);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn.setVisibility(View.INVISIBLE);
            String text = spinner.getSelectedItem().toString();

            getInput();
            switch(text)
            {
                case "+" : tvCalculate.setText(getSum().toString()); break;
                case "-" : tvCalculate.setText(getsubtract().toString());break;
                case "*" : tvCalculate.setText(getmultiply().toString());break;
                case "/" : tvCalculate.setText(getdivide().toString());break;
                case "%" : tvCalculate.setText(getmodulo().toString());break;
                case "^" : tvCalculate.setText(getpower().toString());break;
            }

                // prepare intent which is triggered if the
// notification is selected

                Intent intent = new Intent(this, NotificationReceiver.class);
// use System.currentTimeMillis() to have a unique ID for the pending intent
                PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

// build notification
// the addAction re-use the same intent to keep the example short
                Notification n  = new Notification.Builder(this)
                        .setContentTitle("New mail from " + "test@gmail.com")
                        .setContentText("Subject")
                        .setSmallIcon(R.drawable.icon)
                        .setContentIntent(pIntent)
                        .setAutoCancel(true)
                        .addAction(R.drawable.icon, "Call", pIntent)
                        .addAction(R.drawable.icon, "More", pIntent)
                        .addAction(R.drawable.icon, "And more", pIntent).build();


                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                notificationManager.notify(0, n);

               /* NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                // The id of the channel.
                String id = "my_channel_01";
                // The user-visible name of the channel.
                CharSequence name = getString(R.string.channel_name);
                // The user-visible description of the channel.
                String description = getString(R.string.channel_description);
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = new NotificationChannel(id, name, importance);
                /       / Configure the notification channel.
                mChannel.setDescription(description);
                mChannel.enableLights(true);
            // Sets the notification light color for notifications posted to this
            // channel, if the device supports this feature.
                mChannel.setLightColor(Color.RED);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                mNotificationManager.createNotificationChannel(mChannel);*/


            }
        });
        etoperand1 = findViewById(R.id.et_operand1);
        etoperand2 = findViewById(R.id.et_operand2);
    }

    public void getInput()
    {

            setOperand1(etoperand1.getText().toString());
            setOperand2(etoperand2.getText().toString());
    }

    /*public void pOperands(View view)
    {

        TextView tvCalculate = findViewById(R.id.tv_result);
        tvCalculate.setText(getOperand1() + " " + getOperand2());

    }*/

    //@Override
    /*public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        TextView tvCalculate = findViewById(R.id.tv_result);
        Log.v("Spinner value selected","adapter view ");
        switch(adapterView.getItemAtPosition(position).toString()){
            case "+" : tvCalculate.setText(getSum().toString()); break;
            case "-" : tvCalculate.setText(getsubtract().toString());break;
            case "*" : tvCalculate.setText(getmultiply().toString());break;
            case "/" : tvCalculate.setText(getdivide().toString());break;
            case "%" : tvCalculate.setText(getmodulo().toString());break;
            case "^" : tvCalculate.setText(getpower().toString());break;
        }

    }*/

    private Double getSum(){
        return (Double.valueOf(getOperand1()) + Double.valueOf(getOperand2()));
    }

    private Double getmultiply(){
        return (Double.valueOf(getOperand1()) * Double.valueOf(getOperand2()));
    }
    private Double getsubtract(){
        return (Double.valueOf(getOperand1()) - Double.valueOf(getOperand2()));
    }

    private  Double getmodulo(){
        Double divres = 0.0;
        try {
            divres = Double.valueOf(getOperand1()) % Double.valueOf(getOperand2());
        }catch (RuntimeException re){
            Toast.makeText(this ,"division by 0 not possible",Toast.LENGTH_SHORT).show();
            re.printStackTrace();

        }
        return divres;
    }

    private Double getdivide(){

        Double divres = 0.0;
        try {
            divres = Double.valueOf(getOperand1()) / Double.valueOf(getOperand2());
        }catch (RuntimeException re){
            Toast.makeText(this ,"division by 0 not possible",Toast.LENGTH_SHORT).show();
         re.printStackTrace();

        }
        return divres;
    }

    private Double getpower(){
        return Math.pow(Double.valueOf(getOperand1()),Double.valueOf(getOperand1()));
    }

    //@Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}
