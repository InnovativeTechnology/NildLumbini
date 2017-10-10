package ourproject.nildlumbini;

/**
 * Created by Ramesh on 9/23/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ramesh on 12/16/2016.
 */

public class Item_Adap extends RecyclerView.Adapter<Item_Adap.ViewHolder>
{
    Context context;
    List<RetrieveData> retrieve = new ArrayList<>();
    String name;
    public Item_Adap(List<RetrieveData> retrieves, Context context)
    {
        this.retrieve=retrieves;
        this.context =context;
    }

    public Item_Adap(List<RetrieveData> retrieves, Context context, String option) {
        this.retrieve=retrieves;
        this.context =context;
        this.name = option;
    }

    public Item_Adap.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item;
        if(name == "userProfile") {
            item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_profile, parent, false);
        }else {
            item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        }
        return  new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final RetrieveData retrieve1 = retrieve.get(position);
        holder.name.setText(retrieve1.name);
        holder.option.setText(retrieve1.option);
        holder.date.setText(retrieve1.date);
        holder.title.setText(retrieve1.title);
        holder.article.setText(retrieve1.article);
        Picasso.with(context).load(retrieve1.imgUrl).resize(200, Display.DEFAULT_DISPLAY).into(holder.img);

    }

    @Override
    public int getItemCount() {
        return retrieve.size();
    }
    public static class ViewHolder extends  RecyclerView.ViewHolder {
        TextView name,option,date,title;
        TextView article ;
        ImageView img;
        public ViewHolder(View itemView) {
            super(itemView);

            article= (TextView) itemView.findViewById(R.id.article);
            name= (TextView) itemView.findViewById(R.id.name);
            option= (TextView) itemView.findViewById(R.id.option);
            date= (TextView) itemView.findViewById(R.id.date);
            title= itemView.findViewById(R.id.title);
            img= itemView.findViewById(R.id.image);

        }
    }
    public void getFilter(List<RetrieveData> arrayList)
    {
        retrieve= new ArrayList<>();
        retrieve.addAll(arrayList);
        notifyDataSetChanged();
    }
}