package com.example.gadsleaderboard.Adapter;

import android.annotation.SuppressLint;
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
import com.example.gadsleaderboard.Models.Skills;
import com.example.gadsleaderboard.R;

import java.util.List;

public class SkillsAdapter extends RecyclerView.Adapter<SkillsAdapter.MyViewHolder> {

    Context context;
    private List<Skills> skills;
    private int lastPosition = 0;

    public SkillsAdapter(Context context, List<Skills> skills) {
        this.context = context;
        this.skills = skills;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_skills, viewGroup, false);
        return new MyViewHolder(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int position) {

        myViewHolder.user_name.setText(skills.get(position).getName());
        int score = skills.get(position).getScore();
        String country = skills.get(position).getCountry();
        String image = skills.get(position).getBadgeUrl();

        myViewHolder.score.setText(new StringBuilder()
                .append(score)
                .append(" ")
                .append(context.getString(R.string.skills_score))
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
        return skills.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView user_name, score, country;
        ImageView badge;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            user_name = itemView.findViewById(R.id.nameskills);
            score = itemView.findViewById(R.id.skills);
            badge = itemView.findViewById(R.id.imageskills);
            country = itemView.findViewById(R.id.countryskills);
        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull MyViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();

    }
}