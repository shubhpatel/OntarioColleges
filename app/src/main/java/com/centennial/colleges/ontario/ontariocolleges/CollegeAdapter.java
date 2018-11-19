package com.centennial.colleges.ontario.ontariocolleges;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class CollegeAdapter extends RecyclerView.Adapter<CollegeAdapter.MyViewHolder> {

    private Context mContext;
    private Colleges colleges;

    public CollegeAdapter(Context mContext, Colleges colleges) {
        this.mContext = mContext;
        this.colleges = colleges;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.college_card_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Colleges.College college = colleges.collegeList.get(position);
        holder.title.setText(college.getName() + " College");
        holder.subtitle.setText(college.branches.size() + " Branches");
        Glide.with(mContext).load(college.logo_url).into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return colleges.collegeList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, subtitle;
        public ImageView thumbnail;

        public MyViewHolder
                (View view) {
            super(view);
            title = view.findViewById(R.id.tv_main);
            subtitle = view.findViewById(R.id.tv_other);
            thumbnail = view.findViewById(R.id.iv_logo);
        }
    }


}

