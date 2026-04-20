package com.example.oopproject.Class_UI;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.oopproject.core.GameManager;
import com.example.oopproject.playerJob.CrewLocation;
import com.example.oopproject.playerJob.CrewMember;
import com.example.oopproject.Class_UI.fragment.CrewListFragment;
import com.example.oopproject.R;

import java.util.List;

public class QuartersActivity extends AppCompatActivity {

    private CrewListFragment listFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quarters);

        listFragment = (CrewListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_quarters_list);
        
        // Use the new setLocation method instead of setArguments
        if (listFragment != null) {
            listFragment.setLocation(CrewLocation.QUARTERS);
        }

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
        findViewById(R.id.btn_move_to_simulator).setOnClickListener(v -> moveSelected(CrewLocation.SIMULATOR));
        findViewById(R.id.btn_move_to_mission).setOnClickListener(v -> moveSelected(CrewLocation.MISSION_CONTROL));
        findViewById(R.id.btn_heal).setOnClickListener(v -> healSelected());
    }

    private void healSelected() {
        List<CrewMember> selected = listFragment.getSelectedMembers();
        if (selected == null || selected.isEmpty()) {
            Toast.makeText(this, "No crew members selected", Toast.LENGTH_SHORT).show();
            return;
        }
        
        GameManager gm = GameManager.getInstance();
        int totalCost = selected.size() * GameManager.COST_HEAL;
        
        if (gm.useEnergy(totalCost)) {
            for (CrewMember m : selected) {
                m.heal(20); // Heal 20 HP
            }
            Toast.makeText(this, "Healed " + selected.size() + " crew for " + totalCost + " energy!", Toast.LENGTH_SHORT).show();
            listFragment.refreshList();
        } else {
            Toast.makeText(this, "Not enough energy! (Need " + totalCost + ")", Toast.LENGTH_SHORT).show();
        }
    }

    private void moveSelected(CrewLocation newLocation) {
        List<CrewMember> selected = listFragment.getSelectedMembers();
        if (selected == null || selected.isEmpty()) {
            Toast.makeText(this, "No crew members selected", Toast.LENGTH_SHORT).show();
            return;
        }

        for (CrewMember m : selected) {
            m.setLocation(newLocation);
        }
        
        Toast.makeText(this, "Moved " + selected.size() + " crew to " + newLocation.name(), Toast.LENGTH_SHORT).show();
        listFragment.refreshList();
    }
}