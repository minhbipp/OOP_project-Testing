package com.example.oopproject.Class_UI.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oopproject.R;
import com.example.oopproject.playerJob.CrewMember;

import java.util.ArrayList;
import java.util.List;

public class SelectableCrewAdapter extends RecyclerView.Adapter<SelectableCrewAdapter.ViewHolder> {

    private List<CrewMember> members;
    private List<CrewMember> selectedMembers = new ArrayList<>();
    private int maxSelection = -1;

    public SelectableCrewAdapter(List<CrewMember> members) {
        this.members = members;
    }

    public void setMaxSelection(int max) {
        this.maxSelection = max;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_crew_selectable, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CrewMember member = members.get(position);
        holder.textName.setText(member.getId());
        holder.textRole.setText(member.getClass().getSimpleName());
        holder.textLevel.setText("Lv: " + member.getLevel());
        holder.progressHp.setMax(member.getMaxHp());
        holder.progressHp.setProgress(member.getHp());
        holder.textStats.setText("HP: " + member.getHp() + "/" + member.getMaxHp() + " | ATK: " + member.getAttackPower() + " | XP: " + member.getExp());

        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(selectedMembers.contains(member));

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (maxSelection != -1 && selectedMembers.size() >= maxSelection) {
                    holder.checkBox.setChecked(false);
                    return;
                }
                if (!selectedMembers.contains(member)) {
                    selectedMembers.add(member);
                }
            } else {
                selectedMembers.remove(member);
            }
        });
    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    public List<CrewMember> getSelectedMembers() {
        return selectedMembers;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textName, textRole, textLevel, textStats;
        ProgressBar progressHp;
        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.text_name);
            textRole = itemView.findViewById(R.id.text_role);
            textLevel = itemView.findViewById(R.id.text_level);
            textStats = itemView.findViewById(R.id.text_stats);
            progressHp = itemView.findViewById(R.id.progress_hp);
            checkBox = itemView.findViewById(R.id.checkbox_select);
        }
    }
}