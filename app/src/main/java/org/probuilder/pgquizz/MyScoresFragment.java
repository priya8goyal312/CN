package org.probuilder.pgquizz;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MyScoresFragment extends Fragment {

    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference ref=database.getReference();

    private Dialog loadingDialog;
    private RecyclerView recyclerView;
    private List<MyScoreModel> list;
    private FirebaseAuth firebaseAuth;
    private String currentUserId;

    private ScoreAdapter scoreAdapter;

    public MyScoresFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_my_scores, container, false);
        firebaseAuth=FirebaseAuth.getInstance();
        currentUserId=firebaseAuth.getCurrentUser().getUid();



        Toolbar toolbar=view.findViewById(R.id.scoreToolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("My Scores");

        loadingDialog=new Dialog(view.getContext());
        loadingDialog.setContentView(R.layout.loading);
        loadingDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        loadingDialog.setCancelable(false);

        recyclerView=view.findViewById(R.id.rv_score);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);

//        list=new ArrayList<>();
//
//        final MyScoreAdapter adapter=new MyScoreAdapter(list);
//        recyclerView.setAdapter(adapter);
//
//        loadingDialog.show();
//
//        ref.child("StudentScore").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                //values from database
//                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
//                    list.add(dataSnapshot1.getValue(MyScoreModel.class));
//                    Toast.makeText(getContext(), dataSnapshot1.getValue().toString(), Toast.LENGTH_SHORT).show();
//                }
//
//                //data refresh
//                adapter.notifyDataSetChanged();
//                loadingDialog.dismiss();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                Toast.makeText(container.getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//                loadingDialog.dismiss();
//                getActivity().finish();
//
//            }
//        });


        FirebaseRecyclerOptions<ScoreModel> options =
                new FirebaseRecyclerOptions.Builder<ScoreModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("StudentScore"), ScoreModel.class)
                        .build();

        scoreAdapter=new ScoreAdapter(options);
        recyclerView.setAdapter(scoreAdapter);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        scoreAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        scoreAdapter.stopListening();
    }
}