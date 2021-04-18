package org.probuilder.pgquizz;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyScoreAdapter extends RecyclerView.Adapter<MyScoreAdapter.Viewholder> {

    private List<MyScoreModel> scoreList;

    public MyScoreAdapter(List<MyScoreModel> scoreList) {
        this.scoreList = scoreList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.marks_gain_list,parent,false);
        return new Viewholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        holder.title.setText(scoreList.get(position).getSubName());

    }

    @Override
    public int getItemCount() {
        return scoreList.size();
    }

    class Viewholder extends RecyclerView.ViewHolder
    {
        private CircleImageView imageView;
        private TextView title;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

//            imageView=itemView.findViewById(R.id.imageView);
//            title=itemView.findViewById(R.id.marks_list);
        }

    }

}
