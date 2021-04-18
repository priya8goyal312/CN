package org.probuilder.pgquizz;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class FavoriteFragment extends Fragment {

    public static final String FILE_NAME = "PGQUIZZ";
    public static final String KEY_NAME = "QUESTION";

    private RecyclerView recyclerView;
    private RelativeLayout emptyView;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Gson gson;
    private List<QuestionModel> favList;


    public FavoriteFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.fragment_favorite, container, false);

        Toolbar toolbar=view.findViewById(R.id.favoriteToolbar);
        emptyView=view.findViewById(R.id.empty_view);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Favorites");

        recyclerView=view.findViewById(R.id.rv_favorite);

        sharedPreferences = getActivity().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        gson = new Gson();

        //        //calling getFav method.................
        getFav();

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);

        FavoriteAdapter adapter=new FavoriteAdapter(favList);
        recyclerView.setAdapter(adapter);


        checkList();

        return view;

    }

    private void checkList() {


        if (favList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
        else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // save kar rahe hai question fav me................

        saveFav();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==android.R.id.home){
            getActivity().finish();
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