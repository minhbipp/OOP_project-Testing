package com.example.oopproject.ui;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oopproject.core.GameManager;
import com.example.oopproject.playerJob.CrewLocation;
import com.example.oopproject.playerJob.CrewMember;
import com.example.oopproject.ui.fragment.CrewListFragment;
import com.example.oopproject.R;

import java.util.List;

public class SimulatorActivity extends AppCompatActivity {

    private CrewListFragment listFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulator);

        listFragment = (CrewListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_simulator_list);
        
        // Config
        if (listFragment != null) {
            listFragment.setLocation(CrewLocation.SIMULATOR);
        }

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
        findViewById(R.id.btn_train).setOnClickListener(v -> trainSelected());
        findViewById(R.id.btn_move_to_quarters).setOnClickListener(v -> moveSelected(CrewLocation.QUARTERS));
    }

    private void trainSelected() {
        if (listFragment == null) return;
        List<CrewMember> members = listFragment.getSelectedMembers();
        
        if (members.isEmpty()) {
            Toast.makeText(this, "No crew selected", Toast.LENGTH_SHORT).show();
            return;
        }

        GameManager gm = GameManager.getInstance();
        int totalCost = 0;
        for (CrewMember m : members) {
            // Recommendation: Cost increases with level
            totalCost += GameManager.COST_TRAIN + (m.getLevel() * 2);
        }

        if (gm.useEnergy(totalCost)) {
            for (CrewMember m : members) {
                m.gainExp(2); 
                m.trainingSessions++;
                
                // Recommendation: Reduced healing in Simulator (Lv0=1, Lv10=4)
                int healAmount = 1 + (m.getLevel() / 3);
                m.heal(healAmount);
            }
            Toast.makeText(this, "Trained " + members.size() + " crew for " + totalCost + " energy!", Toast.LENGTH_SHORT).show();
            listFragment.refreshList();
        } else {
            Toast.makeText(this, "Not enough energy! (Need " + totalCost + ")", Toast.LENGTH_SHORT).show();
        }
    }

    private void moveSelected(CrewLocation newLocation) {
        if (listFragment == null) return;
        List<CrewMember> members = listFragment.getSelectedMembers();
        
        if (members.isEmpty()) {
            Toast.makeText(this, "No crew selected", Toast.LENGTH_SHORT).show();
            return;
        }

        for (CrewMember m : members) {
            m.setLocation(newLocation);
        }
        
        Toast.makeText(this, "Moved " + members.size() + " crew to " + newLocation.name(), Toast.LENGTH_SHORT).show();
        listFragment.refreshList();
    }
}