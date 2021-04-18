package org.probuilder.pgquizz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FavoriteQuestionActivity extends AppCompatActivity {

    public static final String FILE_NAME = "PGQUIZZ";
    public static final String KEY_NAME = "QUESTION";


    private RecyclerView recyclerView;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Gson gson;
    private List<QuestionModel> favList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_question);

        Toolbar toolbar=findViewById(R.id.favoriteToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Favorites");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView=findViewById(R.id.rv_favorite);

        sharedPreferences = getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        gson = new Gson();

//        //calling getFav method.................
        getFav();

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);


//        Static data hai ...............................

//        List<QuestionModel> list=new ArrayList<>();
//        list.add(new QuestionModel("What is ancient name of india?","","","","","India",0));
//        list.add(new QuestionModel("What is ancient name of india?","","","","","India",0));
//        list.add(new QuestionModel("What is ancient name of india?","","","","","India",0));
//        list.add(new QuestionModel("What is ancient name of india?","","","","","India",0));
//        list.add(new QuestionModel("What is ancient name of india?","","","","","India",0));
//        list.add(new QuestionModel("What is ancient name of india?","","","","","India",0));



        FavoriteAdapter adapter=new FavoriteAdapter(favList);
        recyclerView.setAdapter(adapter);




    }

    @Override
    protected void onPause() {
        super.onPause();
        // save kar rahe hai question fav me................

        saveFav();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
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

    private void saveFav(){
        String json=gson.toJson(favList);

        editor.putString(KEY_NAME,json);
        editor.commit();
    }

}