package com.example.zaparoli.cities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;


public class RegisterActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextSenha;
    private Button buttonCadastrar;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    private static final String TAG = "Error Message";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        buttonCadastrar = (Button)findViewById(R.id.buttonRegistrar);

        editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        editTextSenha = (EditText)findViewById(R.id.editTextSenha);

        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Registrar();

            }
        });


        }
            private void Registrar(){
                String email = editTextEmail.getText().toString().trim();
                String password = editTextSenha.getText().toString().trim();



                if(TextUtils.isEmpty(email)){
                    Toast.makeText(this, "Informe seu E-mail", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(this, "Informe sua Senha", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressDialog.setMessage("Aguarde...");
                progressDialog.show();

                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){

                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    user.sendEmailVerification()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Log.d(TAG, "Email sent.");
                                                    }
                                                }
                                            });

                                    Toast.makeText(RegisterActivity.this, "Registro Efetuado com Sucesso",Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                    Intent it = new Intent(RegisterActivity.this, MainActivity.class);
                                    startActivity(it);
                                }else{
                                    Toast.makeText(RegisterActivity.this, "Falha no Cadastro", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            }
                        });

            }
}
