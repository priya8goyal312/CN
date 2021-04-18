package org.probuilder.pgquizz;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.viewHolder> {

    private List<QuestionModel> list;

    public FavoriteAdapter(List<QuestionModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_item,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        holder.setData(list.get(position).getQuestion(),list.get(position).getCorrectANS(),position);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class viewHolder extends RecyclerView.ViewHolder{

        private TextView question,answer;
        private ImageButton deleteBtn;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            question=itemView.findViewById(R.id.question);
            answer=itemView.findViewById(R.id.answer);
            deleteBtn=itemView.findViewById(R.id.deleteBtn);
        }

        private void setData(String question, String answer, final int postion) {
            this.question.setText("Question        :  "+question);
            this.answer.setText("Correct Ans   :  "+answer);

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.remove(postion);
                    notifyItemRemoved(postion);
                }
            });

        }
    }
}
