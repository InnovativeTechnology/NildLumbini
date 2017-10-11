package ourproject.nildlumbini;

/**
 * Created by Ramesh on 9/23/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
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


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
    public static String activityName;
    String t="";
    public Item_Adap(List<RetrieveData> retrieves, Context context)
    {
        this.retrieve=retrieves;
        this.context =context;
    }

    public Item_Adap(List<RetrieveData> retrieves, Context context, String option,String t) {
        this.retrieve=retrieves;
        this.context =context;
        this.activityName = option;
        this.t= t;
    }

    public Item_Adap.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item;
        if(activityName == "userProfile" && t.equals("e")) {
            item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_profile, parent, false);
        }else {
            item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        }
        return  new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final RetrieveData retrieve1 = retrieve.get(position);
        holder.name.setText(retrieve1.name);
        holder.option.setText(retrieve1.option);
        holder.date.setText(retrieve1.date);
        holder.title.setText(retrieve1.title);
        holder.article.setText(retrieve1.article);
        Picasso.with(context).load(retrieve1.imgUrl).resize(200, Display.DEFAULT_DISPLAY).into(holder.img);

        if(activityName == "userProfile"&& t.equals("e")){


            holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(context, "Long pressed",Toast.LENGTH_LONG).show();
                    return false;
                }
            });

            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Edit pressed",Toast.LENGTH_LONG).show();
                }
            });



            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                    Toast.makeText(context, "Delete pressed "+retrieve1.userIds,Toast.LENGTH_LONG).show();
                    database.child(retrieve1.userIds).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(context, "Delete pressed",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return retrieve.size();
    }
    public static class ViewHolder extends  RecyclerView.ViewHolder {
        TextView name,option,date,title;
        TextView article ;
        ImageView img;

        CardView cardView;
        ImageView edit;
        ImageView delete;

        public ViewHolder(View itemView) {
            super(itemView);

            article= (TextView) itemView.findViewById(R.id.article);
            name= (TextView) itemView.findViewById(R.id.name);
            option= (TextView) itemView.findViewById(R.id.option);
            date= (TextView) itemView.findViewById(R.id.date);
            title= itemView.findViewById(R.id.title);
            img= itemView.findViewById(R.id.image);

            if(activityName == "userProfile"){
                cardView = itemView.findViewById(R.id.card);
                edit = itemView.findViewById(R.id.edt);
                delete = itemView.findViewById(R.id.dlt);
            }

        }
    }
    public void getFilter(List<RetrieveData> arrayList)
    {
        retrieve= new ArrayList<>();
        retrieve.addAll(arrayList);
        notifyDataSetChanged();
    }
}