package com.example.zaparoli.cities;

import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        //BOTÃO LOGIN
        Button buttonEntrar = (Button) findViewById(R.id.buttonEntrar);
        buttonEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
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

    public void Login() {

        TextView CPF = (TextView)findViewById(R.id.editTextCPF);
        TextView Password = (TextView)findViewById(R.id.editTextPassword);

        String Login = CPF.getText().toString();
        String Senha = Password.getText().toString();

        mAuth.signInWithEmailAndPassword(Login, Senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Login Efetuado", Toast.LENGTH_SHORT).show();

                    Intent it = new Intent(MainActivity.this, MapsActivity.class);
                    startActivity(it);
                } else {
                    Toast.makeText(MainActivity.this, "E-mail ou Senha Incorreto", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
