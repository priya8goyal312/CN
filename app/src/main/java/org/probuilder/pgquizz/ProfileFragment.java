package org.probuilder.pgquizz;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference ref=database.getReference("Userdata");
    //root access from data bases variables .................

    private ImageButton logoutBtn;
    private TextView personName,personMail,personPhone,perosnClass,personSub;
    private String name,mail,phone,classOfPerson,sub;
    private String currentUserId;

    private Dialog loadingDialog;


    public ProfileFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_profile, container, false);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            currentUserId=user.getUid();
        } else {
            // No user is signed in
        }



        //find from ProfileFragment................
        logoutBtn=view.findViewById(R.id.logoutBtn);
        personName=view.findViewById(R.id.personName);
        personMail=view.findViewById(R.id.personMail);
        personPhone=view.findViewById(R.id.personPhone);
        perosnClass=view.findViewById(R.id.personClass);
        personSub=view.findViewById(R.id.personSub);

        loadingDialog=new Dialog(view.getContext());
        loadingDialog.setContentView(R.layout.loading);
        loadingDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        loadingDialog.setCancelable(false);


        logoutBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

    loadingDialog.show();


     ref.child(currentUserId).addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {


             personName.setText(snapshot.child("fullname").getValue(String.class));
             personMail.setText(snapshot.child("mail").getValue(String.class));
             personPhone.setText("+91 "+snapshot.child("mobile").getValue(String.class));
             perosnClass.setText(snapshot.child("classChoose").getValue(String.class));
             personSub.setText(snapshot.child("subChoose").getValue(String.class));

             loadingDialog.dismiss();

         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {

         }
     });



        return view;
    }
}