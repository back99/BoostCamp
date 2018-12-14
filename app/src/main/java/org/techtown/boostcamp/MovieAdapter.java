package org.techtown.boostcamp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    Context context;
    ArrayList<MovieItem> items=new ArrayList<MovieItem>();

    OnItemClickListener listener;

    public static interface OnItemClickListener{
        public void onItemClick(ViewHolder holder, View view,int position);
    }

    public MovieAdapter(Context context){ this.context=context; }

    public int getItemCount(){return items.size();}

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView=inflater.inflate(R.layout.movie_item,parent,false);

        return new ViewHolder(itemView);
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovieItem item=items.get(position);
        holder.setItem(item);

        holder.setOnItemClickListener(listener);
    }



    public void addItem(MovieItem item){
        items.add(item);
    }

    public void addItems(ArrayList<MovieItem> items){
        this.items=items;
    }

    public MovieItem getItem(int position){
        return items.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;

    }


    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        TextView textView2;
        TextView textView3;
        TextView textView4;
        ImageView imageView;
        RatingBar ratingBar;


        OnItemClickListener listener;

        public ViewHolder(View itemView) {
            super(itemView);

            textView=(TextView) itemView.findViewById(R.id.textView);
            textView2=(TextView) itemView.findViewById(R.id.textView2);
            textView3=(TextView) itemView.findViewById(R.id.textView3);
            textView4=(TextView) itemView.findViewById(R.id.textView4);
            imageView=(ImageView) itemView.findViewById(R.id.imageView);
            ratingBar=(RatingBar) itemView.findViewById(R.id.ratingBar);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();

                    if(listener!=null){
                        listener.onItemClick(ViewHolder.this,v,position);
                    }
                }
            });
        }

        public void setItem(MovieItem item){
            textView.setText(item.getTitle());
            textView2.setText(item.getPubDate());
            textView3.setText(item.getDirector());
            textView4.setText(item.getActor());
            ratingBar.setRating(item.getUserRating());
            ImageLoadtask task=new ImageLoadtask(item.getImage(),imageView);
            task.execute();


        }

        public void setOnItemClickListener(OnItemClickListener listener){
            this.listener=listener;
        }

    }
}
