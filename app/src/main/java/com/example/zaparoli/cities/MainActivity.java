package com.example.zaparoli.cities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //BOTÃO LOGIN
        Button buttonEntrar = (Button) findViewById(R.id.buttonEntrar);
        buttonEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView CPF = (TextView)findViewById(R.id.editTextCPF);
                TextView Password = (TextView)findViewById(R.id.editTextPassword);

                String Login = CPF.getText().toString();
                String Senha = CPF.getText().toString();

                if(Login.equals("123") && Senha.equals("123")){
                    alert("Login Efetuado");
                    Intent it = new Intent(MainActivity.this, MapsActivity.class);
                    startActivity(it);
                } else {
                    alert("CPF ou Senha Incorretos");
                }
            }
        });

        //BOTÃO REGISTRAR
        Button buttonSign = (Button)findViewById(R.id.buttonSign);
        buttonSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(it);

            }
        });


    }

    private void alert(String s){
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

}
