package com.example.sargiskh.rateam.detail_view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sargiskh.rateam.R;
import com.example.sargiskh.rateam.detail_view.model.Branch;

import java.util.ArrayList;
import java.util.List;

public class DetailViewBranchListAdapter extends RecyclerView.Adapter<DetailViewBranchListAdapter.DataAdapterViewHolder> {

    public interface BranchSelectedInterface {
        void onBranchListItemClicked(Branch branch);
    }

    private BranchSelectedInterface branchSelectedInterface;

    private List<Branch> branchList = new ArrayList<>();
    private Context context;

    public DetailViewBranchListAdapter(BranchSelectedInterface branchSelectedInterface) {
        this.branchSelectedInterface = branchSelectedInterface;
    }

    public DetailViewBranchListAdapter(List<Branch> branchList, BranchSelectedInterface branchSelectedInterface) {
        this.branchList = branchList;
        this.branchSelectedInterface = branchSelectedInterface;
    }

    @NonNull
    @Override
    public DataAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_recycler_view_branch_detail_view, parent, false);
        return new DataAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DataAdapterViewHolder holder, int position) {
        holder.bindData(branchList.get(position));
    }

    @Override
    public int getItemCount() {
        return branchList.size();
    }

    public void updateData(List<Branch> branchList) {
        this.branchList = branchList;
        notifyDataSetChanged();
    }

    public class DataAdapterViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewBranchName;

        public DataAdapterViewHolder(View itemView) {
            super(itemView);
            textViewBranchName = itemView.findViewById(R.id.textViewBranchName);
        }

        public void bindData(final Branch branch) {
            String title = (branch.title.am != null && !branch.title.am.isEmpty()) ? branch.title.am : branch.title.ru;
            title = (title != null && !title.isEmpty()) ? title: branch.title.en;
            textViewBranchName.setText(title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    branchSelectedInterface.onBranchListItemClicked(branch);
                }
            });
        }
    }
}