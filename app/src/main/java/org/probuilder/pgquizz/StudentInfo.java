package org.probuilder.pgquizz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StudentInfo extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner classSpinner,interestedSubSpinner;
    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListener;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> subjectList;
    private String fullName,email,password,number;
    private Button register_Btn;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;
    private EditText mobileNumber;
    private String chooseClass;
    private String chooseSubject;
    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);

//getting data from previous activity

        fullName=getIntent().getStringExtra("Fullname");
        email=getIntent().getStringExtra("Email");
        password=getIntent().getStringExtra("Password");

//auth verify
        firebaseAuth=FirebaseAuth.getInstance();



//Testing
//Toast.makeText(this, fullName+email+password, Toast.LENGTH_SHORT).show();


//find from xml

        classSpinner=findViewById(R.id.classSpinner);
        interestedSubSpinner=findViewById(R.id.interestedSubSpinner);
        register_Btn=findViewById(R.id.register_Btn);
        progressBar=findViewById(R.id.progressBar);
        mobileNumber=findViewById(R.id.mobileNumber);


        databaseReference= FirebaseDatabase.getInstance().getReference("SubSpinner");
        subjectList=new ArrayList<>();
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,subjectList);
        interestedSubSpinner.setAdapter(adapter);

        //calling function...........
        retrieveSubjectFromFirebase();


        ArrayAdapter<CharSequence> classAdapter= ArrayAdapter.createFromResource(this,R.array.classes, android.R.layout.simple_spinner_item);
        classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        classSpinner.setAdapter(classAdapter);
        classSpinner.setOnItemSelectedListener(this);

        register_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void retrieveSubjectFromFirebase(){
        valueEventListener=databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // data here

                for(DataSnapshot item:snapshot.getChildren()){
                    subjectList.add(item.getValue().toString());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void checkValidation() {

        chooseClass=classSpinner.getSelectedItem().toString();
        chooseSubject=interestedSubSpinner.getSelectedItem().toString();
        number=mobileNumber.getText().toString();


        if(chooseClass==null){
            Toast.makeText(this, "Plz select class", Toast.LENGTH_SHORT).show();
        }else if(chooseSubject==null){
            Toast.makeText(this, "Plz select subject", Toast.LENGTH_SHORT).show();
        }
        else
        {
            progressBar.setVisibility(View.VISIBLE);
            register_Btn.setEnabled(false);
            register_Btn.setVisibility(View.GONE);

            firebaseAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if(task.isSuccessful()) {

                                currentUserId=firebaseAuth.getCurrentUser().getUid();

                                Toast.makeText(StudentInfo.this, "Hey "+fullName+" your account created plz login !", Toast.LENGTH_LONG).show();
                                //store new user data
                                storeNewUsersData();
                                Intent intent=new Intent(StudentInfo.this,LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }else
                            {
                                progressBar.setVisibility(View.INVISIBLE);
                                String error=task.getException().getMessage();
                                Toast.makeText(StudentInfo.this,error,Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void storeNewUsersData()
    {
        FirebaseDatabase rootNode=FirebaseDatabase.getInstance();
        DatabaseReference reference=rootNode.getReference("Userdata");

        StudentHelper newUser=new StudentHelper(fullName,email,number,password,chooseClass,chooseSubject);
        reference.child(currentUserId).setValue(newUser);



    }
}