package com.example.oopproject.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oopproject.R;
import com.example.oopproject.playerJob.CrewMember;

import java.util.List;

public class CrewAdapter extends RecyclerView.Adapter<CrewAdapter.CrewViewHolder> {

    private List<CrewMember> crewMembers;

    public CrewAdapter(List<CrewMember> crewMembers) {
        this.crewMembers = crewMembers;
    }

    @NonNull
    @Override
    public CrewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_crew_member, parent, false);
        return new CrewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CrewViewHolder holder, int position) {
        CrewMember member = crewMembers.get(position);
        holder.textId.setText("Name: " + member.getId());
        holder.textClass.setText("Class: " + member.getClass().getSimpleName());
        holder.textLevel.setText("Level: " + member.getLevel());
        
        holder.progressHp.setMax(member.getMaxHp());
        holder.progressHp.setProgress(member.getHp());
        holder.textHp.setText("HP: " + member.getHp() + "/" + member.getMaxHp());
        
        holder.textStats.setText("Energy: " + member.getEnergy() + " | ATK: " + member.getAttackPower());
    }

    @Override
    public int getItemCount() {
        return crewMembers.size();
    }

    static class CrewViewHolder extends RecyclerView.ViewHolder {
        TextView textId, textClass, textLevel, textHp, textStats;
        ProgressBar progressHp;

        public CrewViewHolder(@NonNull View itemView) {
            super(itemView);
            textId = itemView.findViewById(R.id.text_id);
            textClass = itemView.findViewById(R.id.text_class);
            textLevel = itemView.findViewById(R.id.text_level);
            textHp = itemView.findViewById(R.id.text_hp);
            textStats = itemView.findViewById(R.id.text_stats);
            progressHp = itemView.findViewById(R.id.progress_hp);
        }
    }
}