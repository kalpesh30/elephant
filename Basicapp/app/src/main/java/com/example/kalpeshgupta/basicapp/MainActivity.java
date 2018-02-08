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
import java.lang.Math;

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

    public Double getOperand1() {return Double.valueOf(Operand1);
    }

    public void setOperand1(String operand1) {
        Operand1 = operand1;
    }

    public Double getOperand2() {
        return Double.valueOf(Operand2);
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
                        getSum();
                        sendNotification(n1,"Addition",getOperand1().toString() + "+" + getOperand2().toString() + " = " + resultexp());
                        break;
                    case "-":
                        getsubtract();
                        sendNotification(n2,"Substraction",getOperand1().toString() + "-" + getOperand2().toString() + " = " + resultexp());
                        break;
                    case "*":
                        getmultiply();
                        sendNotification(n3,"Multiplication",getOperand1().toString() + "*" + getOperand2().toString() + " = " + resultexp());
                        break;
                    case "/":
                        getdivide();
                        sendNotification(n4,"Division",getOperand1().toString() + "/" + getOperand2().toString() + " = " + resultexp());
                        break;
                    case "%":
                        getmodulo();
                        sendNotification(n5,"modulo",getOperand1().toString() + "%" + getOperand2().toString() + " = " + resultexp());
                        break;
                    case "^":
                        getpower();
                        sendNotification(n6,"power",getOperand1().toString() + "^" + getOperand2().toString() + " = " + resultexp());
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


    private boolean getType(){
        boolean c = (getOperand1() - Math.floor(getOperand1()) == 0) && (getOperand2() - Math.floor(getOperand2()) == 0);
        return c;

    }


    private void getSum()
    {
        Double d1;
        d1 = getOperand1() + getOperand2();
        if(getType())
        {
            tvCalculate.setText(Integer.toString(d1.intValue()));
        }
        else
            tvCalculate.setText(d1.toString());
    }

    private void getmultiply(){
        Double d1;
        d1 = getOperand1() * getOperand2();
        if(getType())
        {
            tvCalculate.setText(Integer.toString(d1.intValue()));
        }
        else
            tvCalculate.setText(d1.toString());
    }
    private void getsubtract(){
        Double d1;
        d1 = getOperand1() - getOperand2();
        if(getType())
        {
            tvCalculate.setText(Integer.toString(d1.intValue()));
        }
        else
            tvCalculate.setText(d1.toString());
    }

    private  void getmodulo(){
        Double divres = 0.0;
        try {
            divres = getOperand1() % getOperand2();
        }catch (RuntimeException re){
            Toast.makeText(this ,"division by 0 not possible",Toast.LENGTH_SHORT).show();
            re.printStackTrace();

        }
        if(divres - Math.floor(divres) == 0)
        {
            tvCalculate.setText(Integer.toString(divres.intValue()));
        }
        else
            tvCalculate.setText(divres.toString());
    }

    private void getdivide(){

        Double divres = 0.0;
        try {
            divres = getOperand1() / getOperand2();
        }catch (RuntimeException re){
            Toast.makeText(this ,"division by 0 not possible",Toast.LENGTH_SHORT).show();
         re.printStackTrace();

        }

        if(divres - Math.floor(divres) == 0)
        {
            tvCalculate.setText(Integer.toString(divres.intValue()));
        }
        else
            tvCalculate.setText(divres.toString());
    }

    private void getpower(){
        if(getType())
            tvCalculate.setText(Long.toString((long) Math.pow(getOperand1(),getOperand2())));
        else
            tvCalculate.setText(String.valueOf(Math.pow(getOperand1(),getOperand1())));
    }

    //@Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}
