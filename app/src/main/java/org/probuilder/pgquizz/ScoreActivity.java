package org.probuilder.pgquizz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;

import pl.droidsonroids.gif.GifImageView;

public class ScoreActivity extends AppCompatActivity{

    FirebaseDatabase database1=FirebaseDatabase.getInstance();
    DatabaseReference ref1=database1.getReference("Userdata");

    private TextView score,total;
    private Button doneBtn;



    private ImageView streamImage;

    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference ref=database.getReference("StudentScore");

    private FirebaseAuth firebaseAuth;
    private String currentUserId;
    private String subName;
    private int setName;
    private int setNo;
    private String scoreOfSub;

    private String sName;

    private String s,t;
    private float per;

    private Dialog dialog;
    private  String nameOfStudent;

    private LinearLayout streamLy,marksLy;


    private TextView feedBackText;
    private TextView perText;
    private TextView sayText;
    private Button okay;
    private GifImageView gif;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_score);

            ProfileFragment profileFragment=new ProfileFragment();

            sName=getIntent().getStringExtra("subName");





    //        dialog box of showing percentage

            dialog=new Dialog(this);
            dialog.setContentView(R.layout.score_show_popup);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            dialog.setCancelable(false);

            feedBackText=dialog.findViewById(R.id.feedBackText);
            perText=dialog.findViewById(R.id.perText);
            sayText=dialog.findViewById(R.id.sayText);
            okay=dialog.findViewById(R.id.okBtn);
            gif=dialog.findViewById(R.id.gif);

            streamImage=findViewById(R.id.streamImage);



            score=findViewById(R.id.score);
            total=findViewById(R.id.total);
    //        doneBtn=findViewById(R.id.doneBtn);
            firebaseAuth=FirebaseAuth.getInstance();

            currentUserId=firebaseAuth.getCurrentUser().getUid();



            score.setText(String.valueOf(getIntent().getIntExtra("score",0)));
            total.setText("OUT OF "+String.valueOf(getIntent().getIntExtra("total",0)));

    //        // getting deatils of subject
    //        SetsActivity setsActivity=new SetsActivity();
    //        QuestionsActivity questionsActivity=new QuestionsActivity();
    //        subName=setsActivity.getSubName();
    //        setName=questionsActivity.setName;

    //        Toast.makeText(ScoreActivity.this, sName, Toast.LENGTH_SHORT).show();

    //        score gained
            s=String.valueOf(getIntent().getIntExtra("score",0));

    //        total score
            t=String.valueOf(getIntent().getIntExtra("total",0));

    //      finding per
            per=(int)((Float.parseFloat(s)) / (Float.parseFloat(t)) * (100.0f));

            ref1.child(currentUserId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    nameOfStudent=snapshot.child("fullname").getValue(String.class);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });



            // setting data to dailog according to %
            if(per >= 80) {
                //100%
                ref1.child(currentUserId).addValueEventListener(new ValueEventListener() {
                        @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String name=snapshot.child("fullname").getValue(String.class);
                        perText.setText(Float.toString(per)+"%");
                        feedBackText.setText("Excellent "+name);
                        sayText.setText("Hey "+name+" you are bright student you can make carrier in this subject ");
                        okay.setText("See Stream");
                        dialog.show();

                        if(sName.equals("Maths")){

                            Toast.makeText(ScoreActivity.this, "hey "+sName, Toast.LENGTH_SHORT).show();
                            okay.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    streamImage.setVisibility(View.VISIBLE);
                                    streamImage.setImageResource(R.drawable.mathss);
                                    dialog.dismiss();
                                }
                            });

                        }else if(sName.equals("science")){

                            okay.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    streamImage.setVisibility(View.VISIBLE);
                                    streamImage.setImageResource(R.drawable.sciences);
                                    dialog.dismiss();

                                }
                            });

                        }else if(sName.equals("Commerce")){

                            okay.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    streamImage.setVisibility(View.VISIBLE);
                                    streamImage.setImageResource(R.drawable.commerces);
                                    dialog.dismiss();

                                }
                            });

                        }else if(sName.equals("Arts")){

                            okay.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    streamImage.setVisibility(View.VISIBLE);
                                    streamImage.setImageResource(R.drawable.arts);
                                    dialog.dismiss();

                                }
                            });

                        }else if(sName.equals("English")){

                        }




                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
            else if(per < 80 && per >= 70) {
                ref1.child(currentUserId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String name=snapshot.child("fullname").getValue(String.class);
                        gif.setImageResource(R.drawable.cry);
                        perText.setText(Float.toString(per)+"%");
                        feedBackText.setText("Average "+name);
                        sayText.setText("Hey "+name+" you are good student read books and attempt again ");
                        dialog.show();
                        okay.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
            else if(per < 70 && per >= 60){
                ref1.child(currentUserId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String name=snapshot.child("fullname").getValue(String.class);
                        gif.setImageResource(R.drawable.backcel);
                        perText.setText(Float.toString(per)+"%");
                        feedBackText.setText("Very good "+name);
                        sayText.setText("Hey "+name+" awesome you are bright student do little work on subject read books");
                        dialog.show();
                        okay.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
            else if(per < 60 && per >= 50){
                ref1.child(currentUserId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String name=snapshot.child("fullname").getValue(String.class);
                        gif.setImageResource(R.drawable.backcel);
                        perText.setText(Float.toString(per)+"%");
                        feedBackText.setText("Average "+name);
                        sayText.setText("Hey "+name+" you are good student read books and attempt again ");
                        dialog.show();
                        okay.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {


                    }
                });
            }
            else if(per < 50 && per >= 40){
                ref1.child(currentUserId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String name=snapshot.child("fullname").getValue(String.class);
                        gif.setImageResource(R.drawable.backcel);
                        perText.setText(Float.toString(per)+"%");
                        feedBackText.setText("Not bad "+name);
                        sayText.setText("Hey "+name+" you are good student read books and attempt again ");
                        dialog.show();
                        okay.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
            else{
                ref1.child(currentUserId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String name=snapshot.child("fullname").getValue(String.class);
                        gif.setImageResource(R.drawable.cry);
                        perText.setText(Float.toString(per)+"%");
                        feedBackText.setText("Very poor "+name);
                        sayText.setText("Hey "+name+" you need more hard work plz read books and attempt again ");
                        dialog.show();
                        okay.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }



    //        set exam details to firebase
            ScoreSetToFirebaseHelper helper=new ScoreSetToFirebaseHelper(subName,scoreOfSub,setName,per);
            ref.child(currentUserId+sName+setNo).setValue(helper);


    //        doneBtn.setOnClickListener(new View.OnClickListener() {
    //            @Override
    //            public void onClick(View v) {
    //                finish();
    //            }
    //        });

    }



}