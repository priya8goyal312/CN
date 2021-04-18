package org.probuilder.pgquizz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class StudentRegisterActivity extends AppCompatActivity {

    private Button nextBtn;
    private ImageView backBtnNormalUser1stRegistartion;
    private EditText fullName;
    private EditText email;
    private EditText password;
    private EditText confPassword;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);

        //calling findVariable fun........
        findVariable();

        //find from xml


        // ClickListener set on nextBtn
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });
    }

    private void checkValidation() {
        String name=fullName.getText().toString();
        String mail=email.getText().toString();
        final String pass=password.getText().toString();
        String confPass=confPassword.getText().toString();

        if (TextUtils.isEmpty(name))
        {
            fullName.setError("Enter Name");
        }
        else if (TextUtils.isEmpty(pass))
        {
            password.setError("Enter Password");
        }
        else if (TextUtils.isEmpty(confPass))
        {
            confPassword.setError("Enter Password ");
        }
        else if (TextUtils.isEmpty(mail))
        {
            email.setError("Enter Email");
        }
        else if (!(pass.length()>=6))
        {
            Toast.makeText(StudentRegisterActivity.this,"Password must be more than 6",Toast.LENGTH_SHORT).show();
        }
        else if (!(pass.length()>=6))
        {
            Toast.makeText(StudentRegisterActivity.this,"Password must be more than 6",Toast.LENGTH_SHORT).show();
        }
        else if (!pass.equals(confPass))
        {
            Toast.makeText(StudentRegisterActivity.this,"Password not matched",Toast.LENGTH_SHORT).show();
        }
        else if (!(email.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")))
        {
            email.setError("Enter Valid email");
        }
        else
        {

            // data passed to next activity that is studentInfo......
            progressBar.setVisibility(View.VISIBLE);
            nextBtn.setEnabled(false);
            nextBtn.setVisibility(View.GONE);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run()
                {
                    Intent goToProfileIntent=new Intent(StudentRegisterActivity.this,StudentInfo.class);
                    goToProfileIntent.putExtra("Fullname",fullName.getText().toString());
                    goToProfileIntent.putExtra("Email",email.getText().toString());
                    goToProfileIntent.putExtra("Password",password.getText().toString());
                    startActivity(goToProfileIntent);
                    finish();

                }
            },3500);

        }
    }

    private void findVariable() {
        nextBtn=findViewById(R.id.nextBtn);
        fullName=findViewById(R.id.fullName);
        email=findViewById(R.id.mail);
        password=findViewById(R.id.password);
        confPassword=findViewById(R.id.confPassword);
        progressBar=findViewById(R.id.progressBar);
    }
}