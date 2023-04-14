package com.example.mymovie;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class myAdapter extends RecyclerView.Adapter<myAdapter.ViewHolder>{
    private Context context;
    private ArrayList<ModelClass1>modelClass1;

    public myAdapter(Context context, ArrayList<ModelClass1> modelClass1) {
        this.context = context;
        this.modelClass1 = modelClass1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_layout1,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtTitle1.setText(modelClass1.get(position).getmTitle());
        holder.txtReleasedDate1.setText(modelClass1.get(position).getmReleasedDate());
        Picasso.get().load("https://image.tmdb.org/t/p/w500/"+modelClass1.get(position).getmImage()).into(holder.imageView1);
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,DetailActivity.class);
                intent.putExtra("image", "https://image.tmdb.org/t/p/w500/" + modelClass1.get(holder.getAdapterPosition()).getmImage());
                intent.putExtra("date",modelClass1.get(holder.getAdapterPosition()).getmReleasedDate());
                intent.putExtra("title",modelClass1.get(holder.getAdapterPosition()).getmTitle());
                intent.putExtra("view",modelClass1.get(holder.getAdapterPosition()).getOverView());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelClass1.size();
    }
    public void searchmyModalClass(ArrayList<ModelClass1>searchList){
     modelClass1=searchList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView1;
        private RelativeLayout relativeLayout;
        private TextView txtTitle1,txtReleasedDate1,txtTtitleDisplay;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView1 = itemView.findViewById(R.id.imgView1);
            txtTitle1= itemView.findViewById(R.id.txtTitle1);
            txtReleasedDate1= itemView.findViewById(R.id.txtReleasedDate1);
            relativeLayout = itemView.findViewById(R.id.RelativeLayout);

        }
    }
}
