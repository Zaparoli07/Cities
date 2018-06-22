package com.example.zaparoli.cities;

import android.*;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;

    private ProgressDialog progressDialog;

    private final int LOCATION_REQUEST_CODE = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

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

        Permission(Manifest.permission.ACCESS_FINE_LOCATION, LOCATION_REQUEST_CODE);

    }

    public void Login() {

        TextView CPF = (TextView)findViewById(R.id.editTextCPF);
        TextView Password = (TextView)findViewById(R.id.editTextPassword);

        String Login = CPF.getText().toString();
        String Senha = Password.getText().toString();

        progressDialog.setMessage("Aguarde...");
        progressDialog.show();


        mAuth.signInWithEmailAndPassword(Login, Senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {


                    Toast.makeText(MainActivity.this, "Login Efetuado", Toast.LENGTH_SHORT).show();

                    Intent it = new Intent(MainActivity.this, NavigationActivity.class);
                    startActivity(it);

                    progressDialog.dismiss();
                } else {
                    Toast.makeText(MainActivity.this, "E-mail ou Senha Incorretos", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }
            }
        });
    }

    private void Permission(String permission, int requestCode){
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
            //Não existe permissão
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        }else{
            //Tem permissão
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case LOCATION_REQUEST_CODE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
        }
    }
}
