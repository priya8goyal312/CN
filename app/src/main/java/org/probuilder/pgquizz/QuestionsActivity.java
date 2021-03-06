package org.probuilder.pgquizz;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class QuestionsActivity extends AppCompatActivity {

    public static final String FILE_NAME = "PGQUIZZ";
    public static final String KEY_NAME = "QUESTION";


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();


    private TextView question, no_indicator;
    private FloatingActionButton likeBtn;
    private LinearLayout optionConatiner;
    private Button shareBtn, nextBtn;
    private int count = 0;
    private List<QuestionModel> list;
    private int postion = 0;
    private int score = 0;
    private String category;
    private int setNo;
    private Dialog loadingDialog;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Gson gson;
    private List<QuestionModel> favList;
    private int matchedQuestionPosition;

    public static int setName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        Toolbar toolbar = findViewById(R.id.questionToolbar);
        setSupportActionBar(toolbar);

        question = findViewById(R.id.question);
        no_indicator = findViewById(R.id.no_indicator);
        likeBtn = findViewById(R.id.like_btn);
        optionConatiner = findViewById(R.id.options_container);
        shareBtn = findViewById(R.id.share_btn);
        nextBtn = findViewById(R.id.next_btn);

        setName=getIntent().getIntExtra("setNo",0);

        sharedPreferences = getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        gson = new Gson();

//        //calling getFav method.................
            getFav();
//
//        //like question ...........
        likeBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                if (modelMatch()){
                    favList.remove(matchedQuestionPosition);
                    likeBtn.setImageDrawable(getDrawable(R.drawable.like));
                }else {
                    favList.add(list.get(postion));
                    likeBtn.setImageDrawable(getDrawable(R.drawable.wholelike));
                }

            }
        });



        category = getIntent().getStringExtra("category");

        Toast.makeText(this, category, Toast.LENGTH_SHORT).show();
        setNo = getIntent().getIntExtra("setNo", 1);



        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.loading);
        loadingDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        loadingDialog.setCancelable(false);


        list = new ArrayList<>();
        loadingDialog.show();

        ref.child("SETS").child(category).child("questions").orderByChild("setNo").equalTo(setNo).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override


            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // data

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    list.add(snapshot.getValue(QuestionModel.class));

                }
                if (list.size() > 0) {

                    for (int i = 0; i < 4; i++) {
                        optionConatiner.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public void onClick(View v) {
                                checkAnswer((Button) v);
                            }
                        });
                    }

                    playAnim(question, 0, list.get(postion).getQuestion());

                    nextBtn.setOnClickListener(new View.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                        @Override
                        public void onClick(View v) {
                            nextBtn.setEnabled(false);
                            nextBtn.setAlpha(0.7f);
                            enabledOption(true);
                            postion++;
                            if (postion == list.size()) {
                                //// score activity
                                Intent goToScoreIntent = new Intent(QuestionsActivity.this, ScoreActivity.class);
                                goToScoreIntent.putExtra("score", score);
                                goToScoreIntent.putExtra("subName", category);
                                goToScoreIntent.putExtra("setName", setNo);
                                goToScoreIntent.putExtra("total", list.size());
                                startActivity(goToScoreIntent);
                                finish();
                                return;
                            }
                            count = 0;
                            playAnim(question, 0, list.get(postion).getQuestion());
                        }
                    });

                    shareBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String body=list.get(postion).getQuestion() + "\n" +
                                    list.get(postion).getOptionA() + "\n" +
                                    list.get(postion).getOptionB() + "\n" +
                                    list.get(postion).getOptionC() + "\n" +
                                    list.get(postion).getOptionD();


                            Intent shareIntent=new Intent(Intent.ACTION_SEND);
                            shareIntent.setType("text/plain");
                            shareIntent.putExtra(Intent.EXTRA_SUBJECT,"PG QUIZZ");
                            shareIntent.putExtra(Intent.EXTRA_TEXT,body);
                            startActivity(Intent.createChooser(shareIntent,"Share via"));
                        }
                    });
                } else {
                    finish();
                    Toast.makeText(QuestionsActivity.this, "No questions", Toast.LENGTH_SHORT).show();
                }
                loadingDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(QuestionsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                loadingDialog.dismiss();
                finish();

            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        // save kar rahe hai question fav me................

        saveFav();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void checkAnswer(Button selectedOption) {

        enabledOption(false);

        nextBtn.setEnabled(true);
        nextBtn.setAlpha(1);
        if (selectedOption.getText().toString().equals(list.get(postion).getCorrectANS())) {
            //correct option
            score++;
            selectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF82C631")));

        } else {
            //incorrect
            selectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ff0000")));
            Button correctoption = (Button) optionConatiner.findViewWithTag(list.get(postion).getCorrectANS());
            selectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF82C631")));

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void enabledOption(boolean enable) {
        for (int i = 0; i < 4; i++) {
            optionConatiner.getChildAt(i).setEnabled(enable);
            if (enable) {
                optionConatiner.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#989898")));
            }
        }
    }

    private void playAnim(final View view, final int value, final String data) {

        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100)
                .setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

                if (value == 0 && count < 4) {


                    String option = "";

                    if (count == 0) {
                        option = list.get(postion).getOptionA();
                    } else if (count == 1) {
                        option = list.get(postion).getOptionB();

                    } else if (count == 2) {
                        option = list.get(postion).getOptionC();

                    } else if (count == 3) {
                        option = list.get(postion).getOptionD();

                    }


                    playAnim(optionConatiner.getChildAt(count), 0, option);
                    count++;
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onAnimationEnd(Animator animation) {
                ////data changed
                if (value == 0) {

                    try {
                        ((TextView) view).setText(data);
                        no_indicator.setText(postion + 1 + "/" + list.size());
                        if (modelMatch()){
                            likeBtn.setImageDrawable(getDrawable(R.drawable.wholelike));

                        }else {
                            likeBtn.setImageDrawable(getDrawable(R.drawable.like));

                        }
                    } catch (ClassCastException ex) {

                        ((Button) view).setText(data);
                    }
                    view.setTag(data);
                    playAnim(view, 1, data);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }

    private void getFav(){
        String json=sharedPreferences.getString(KEY_NAME,"");
        Type type=new TypeToken<List<QuestionModel>>(){}.getType();

        favList=gson.fromJson(json,type);

        if (favList==null)
        {
            favList=new ArrayList<>();
        }
    }

    private boolean modelMatch(){
        boolean matched=false;
        int i=0;
        for(QuestionModel model: favList){
            if(model.getQuestion().equals(list.get(postion).getQuestion())
                && model.getCorrectANS().equals(list.get(postion).getCorrectANS())
                && model.getSetNo()==list.get(postion).getSetNo()){
                matched=true;
                matchedQuestionPosition=i;
            }
            i++;
        }
        return matched;
    }

    private void saveFav(){
        String json=gson.toJson(favList);

        editor.putString(KEY_NAME,json);
        editor.commit();
    }



}