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

    private static final int n1 = 1100;
    private static final int n2 = 1101;
    private static final int n3= 1102;
    private static final int n4 = 1103 ;
    private static final int n5 = 1104;
    private static final int n6 = 1105;

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
    //Spinner myspin;
    private NotificationHelper helper;



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
        helper = new NotificationHelper(this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //btn.setVisibility(View.INVISIBLE);
                String text = spinner.getSelectedItem().toString();

                getInput();
                switch (text) {
                    case "+":
                        tvCalculate.setText(getSum().toString());
                        sendNotification(n1,"Addition",getOperand1() + "+" + getOperand2() + " = " + resultexp());
                        break;
                    case "-":
                        tvCalculate.setText(getsubtract().toString());
                        sendNotification(n2,"Substraction",getOperand1() + "-" + getOperand2() + " = " + resultexp());
                        break;
                    case "*":
                        tvCalculate.setText(getmultiply().toString());
                        sendNotification(n3,"Multiplication",getOperand1() + "*" + getOperand2() + " = " + resultexp());
                        break;
                    case "/":
                        tvCalculate.setText(getdivide().toString());
                        sendNotification(n4,"Division",getOperand1() + "/" + getOperand2() + " = " + resultexp());
                        break;
                    case "%":
                        tvCalculate.setText(getmodulo().toString());
                        sendNotification(n5,"modulo",getOperand1() + "%" + getOperand2() + " = " + resultexp());
                        break;
                    case "^":
                        tvCalculate.setText(getpower().toString());
                        sendNotification(n6,"power",getOperand1() + "^" + getOperand2() + " = " + resultexp());
                        break;
                }

            }
        });


        etoperand1 = findViewById(R.id.et_operand1);
        etoperand2 = findViewById(R.id.et_operand2);
    }

    public void sendNotification(int id,String title,String body){
        try{
            Notification.Builder nb = helper.getNotification1(title +" Operation Performed",body);
            if(nb != null) {
                helper.Notify(id,nb);
            }
        }catch(NullPointerException npe){
            npe.printStackTrace();
        }
    }

    private String resultexp()
    {
        String s;
        s = tvCalculate.getText().toString();
        return s;
    }

    public void getInput()
    {

            setOperand1(etoperand1.getText().toString());
            setOperand2(etoperand2.getText().toString());
    }


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
