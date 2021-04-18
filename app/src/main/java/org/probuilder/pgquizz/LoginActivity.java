package org.probuilder.pgquizz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {


    private TextView goToRegister;
    private EditText loginEmail;
    private EditText loginPassword;
    private Button loginBtn;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;
    private String mail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//      find from xml
        goToRegister=findViewById(R.id.goToRegister);
        loginEmail=findViewById(R.id.loginMail);
        loginPassword=findViewById(R.id.loginPassword);
        loginBtn=findViewById(R.id.loginBtn);
        progressBar=findViewById(R.id.progressBar);

//        auth
        firebaseAuth=FirebaseAuth.getInstance();

//    email editText give to mail String variable
        mail=loginEmail.getText().toString();


        goToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToRegisterIntent=new Intent(LoginActivity.this,StudentRegisterActivity.class);
                startActivity(goToRegisterIntent);
                finish();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                checkValidation();
            }
        });
    }

    private void checkValidation()
    {

        String pass=loginPassword.getText().toString();
        final String mail=loginEmail.getText().toString();

        if (TextUtils.isEmpty(mail))
        {
            loginEmail.setError("Enter Email");
        }
        else if (!(loginEmail.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")))
        {
            loginEmail.setError("Enter Valid email");
        }
        else if (TextUtils.isEmpty(pass))
        {
            loginPassword.setError("Enter Password");
        }
        else if (!(pass.length()>=6))
        {
            loginPassword.setError("Password must be 6 Charater");
        }
        else
        {
//            Allrigth

            progressBar.setVisibility(View.VISIBLE);
            loginBtn.setEnabled(false);
            loginBtn.setVisibility(View.GONE);

            firebaseAuth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {
                    if(task.isSuccessful())
                    {

                        Intent intent=new Intent(LoginActivity.this,CounselingActivity.class);
                        intent.putExtra("mail",mail);
                        startActivity(intent);
                        Toast.makeText(LoginActivity.this, "Login Successfully !", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else
                    {
                        loginBtn.setEnabled(true);
                        loginBtn.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        loginBtn.setEnabled(true);
                        String error=task.getException().getMessage();
                        Toast.makeText(LoginActivity.this,error,Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}