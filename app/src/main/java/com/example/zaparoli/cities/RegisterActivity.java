package com.example.zaparoli.cities;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;


public class RegisterActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextSenha;
    private Button buttonCadastrar;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

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
                    //Email is Empty
                    Toast.makeText(this, "Please enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    //Password is Empty
                    Toast.makeText(this, "Please enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressDialog.setMessage("Registering User");
                progressDialog.show();

                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(RegisterActivity.this, "Registro Efetuado",Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(RegisterActivity.this, "Registro Incorreto", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
}
