package com.example.oopproject.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.oopproject.R;
import com.example.oopproject.core.GameManager;
import com.example.oopproject.playerJob.CrewLocation;
import com.example.oopproject.playerJob.CrewMember;
import com.example.oopproject.ui.fragment.CrewListFragment;

import java.util.List;

public class MissionControlActivity extends AppCompatActivity {

    private CrewListFragment listFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_control);

        listFragment = (CrewListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_mission_list);
        
        // Config
        if (listFragment != null) {
            listFragment.setLocation(CrewLocation.MISSION_CONTROL);
        }

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
        findViewById(R.id.btn_launch_mission).setOnClickListener(v -> launchMission());
        findViewById(R.id.btn_back_to_quarters).setOnClickListener(v -> moveAll(CrewLocation.QUARTERS));
    }

    private void launchMission() {
        List<CrewMember> members = GameManager.getInstance().getCrewAt(CrewLocation.MISSION_CONTROL);
        
        GameManager gm = GameManager.getInstance();
        boolean isBoss = gm.isBossDay();
        int maxAllowed = isBoss ? 5 : 3;

        if (members.isEmpty()) {
            Toast.makeText(this, "Move crew to Mission Control first", Toast.LENGTH_SHORT).show();
            return;
        }

        if (members.size() > maxAllowed) {
            Toast.makeText(this, "Too many crew members! Max is " + maxAllowed, Toast.LENGTH_SHORT).show();
            return;
        }

        if (gm.energy < GameManager.COST_COMBAT) {
            Toast.makeText(this, "Need " + GameManager.COST_COMBAT + " energy to start mission!", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, MissionBattleActivity.class);
        for (int i = 0; i < members.size(); i++) {
            intent.putExtra("member" + (i + 1) + "_id", members.get(i).getId());
        }
        
        startActivity(intent);
    }

    private void moveAll(CrewLocation newLocation) {
        List<CrewMember> members = GameManager.getInstance().getCrewAt(CrewLocation.MISSION_CONTROL);
        for (CrewMember m : members) {
            m.setLocation(newLocation);
        }
        
        Toast.makeText(this, "Moved all to " + newLocation.name(), Toast.LENGTH_SHORT).show();
        listFragment.refreshList();
    }
}