package org.probuilder.pgquizz;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import org.w3c.dom.Text;

public class ScoreAdapter extends FirebaseRecyclerAdapter<ScoreModel,ScoreAdapter.myViewHolder> {



    public ScoreAdapter(@NonNull FirebaseRecyclerOptions<ScoreModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int i, @NonNull ScoreModel scoreModel) {

        holder.subName.setText(scoreModel.getSubName());
//        holder.setNo.setText(scoreModel.getSetName());
//        holder.perStudent.setText(scoreModel.getPer());
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.marks_gain_list,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        TextView subName,setNo,perStudent;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            subName=(TextView)itemView.findViewById(R.id.subName);
            setNo=(TextView)itemView.findViewById(R.id.setName);
            perStudent=(TextView)itemView.findViewById(R.id.perStudent);
        }
    }
}
