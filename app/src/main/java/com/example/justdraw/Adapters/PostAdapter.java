package com.example.justdraw.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.justdraw.R;
import com.example.justdraw.Utilities.PostModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.media.CamcorderProfile.get;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    List<PostModel> postModelsList;
    Context c;
    Picasso picasso;


    public PostAdapter(Context context, List<PostModel> postList) {
        this.postModelsList = postList;
        this.c = context;

    }

    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.post_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PostModel load = postModelsList.get(position);
        holder.tvname.setText(load.getName());
      //  Picasso.get().load(load.getImgUrl()).into(holder.imageView);
        Glide.with(c).load(load.getImgUrl()).into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return postModelsList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView tvname;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.post_img);
            tvname=(TextView)itemView.findViewById(R.id.post_name);
        }
    }


}

