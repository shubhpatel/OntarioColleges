package com.centennial.colleges.ontario.ontariocolleges;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class BranchAdapter extends RecyclerView.Adapter<BranchAdapter.MyViewHolder> {

    private Context mContext;
    private List<Colleges.College.Branch> branchList;

    public BranchAdapter(Context mContext, List<Colleges.College.Branch> branchList) {
        this.mContext = mContext;
        this.branchList = branchList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.branch_card_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Colleges.College.Branch branch = branchList.get(position);
        holder.title.setText(branch.getBranchname() + " Campus");
        holder.subtitle.setText(branch.getAddress());
    }

    @Override
    public int getItemCount() {
        return branchList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, subtitle;

        public MyViewHolder
                (View view) {
            super(view);
            title = view.findViewById(R.id.tv_main);
            subtitle = view.findViewById(R.id.tv_other);
        }
    }
}

