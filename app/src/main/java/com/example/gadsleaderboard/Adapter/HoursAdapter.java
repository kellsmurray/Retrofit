package com.example.gadsleaderboard.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gadsleaderboard.Models.Hours;
import com.example.gadsleaderboard.R;

import java.util.List;

public class HoursAdapter extends RecyclerView.Adapter<HoursAdapter.MyViewHolder> {

    Context context;
    private List<Hours> hours;
    private int lastPosition = 0;

    public HoursAdapter(Context context, List<Hours> hours) {
        this.context = context;
        this.hours = hours;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_hours, viewGroup, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {

        myViewHolder.user_name.setText(hours.get(position).getName());
        int lhours = hours.get(position).getHours();
        String country = hours.get(position).getCountry();
        String image = hours.get(position).getBadgeUrl();
        myViewHolder.lhours.setText(new StringBuilder()
                .append(lhours)
                .append(" ")
                .append(context.getString(R.string.lhrs))
                .append(" ")
                .append(country).toString());
        // holder.score.setText("learning hours, " + country);
        if(!image.isEmpty()){
            Glide.with(context).load(image).into(myViewHolder.badge);
        }

        Animation animation = AnimationUtils.loadAnimation(context,
                (position > lastPosition) ? R.anim.up_from_bottom
                        : R.anim.down_from_top);
        myViewHolder.itemView.startAnimation(animation);
        lastPosition = position;
    }

    @Override
    public int getItemCount() {
        return hours.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView user_name, lhours, country;
        ImageView badge;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            user_name = itemView.findViewById(R.id.namehours);
            lhours = itemView.findViewById(R.id.hours);
            badge = itemView.findViewById(R.id.imagehours);
            country = itemView.findViewById(R.id.countryhours);
        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull MyViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }
}