package com.example.oopproject.ui;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.oopproject.core.GameManager;
import com.example.oopproject.playerJob.CrewMember;
import com.example.oopproject.playerJob.Engineer;
import com.example.oopproject.playerJob.Medic;
import com.example.oopproject.playerJob.Pilot;
import com.example.oopproject.playerJob.Scientist;
import com.example.oopproject.playerJob.Soldier;
import com.example.oopproject.R;

public class RecruitActivity extends AppCompatActivity {

    private EditText editName;
    private RadioGroup radioGroupRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruit);

        editName = findViewById(R.id.edit_name);
        radioGroupRole = findViewById(R.id.radio_group_role);

        findViewById(R.id.btn_create).setOnClickListener(v -> createCrewMember());
        findViewById(R.id.btn_cancel).setOnClickListener(v -> finish());
    }

    private void createCrewMember() {
        String name = editName.getText().toString().trim();
        if (name.isEmpty()) {
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show();
            return;
        }

        int selectedId = radioGroupRole.getCheckedRadioButtonId();
        CrewMember member = null;
        int initialEnergy = 100;

        if (selectedId == R.id.radio_soldier) {
            member = new Soldier(name, initialEnergy);
        } else if (selectedId == R.id.radio_medic) {
            member = new Medic(name, initialEnergy);
        } else if (selectedId == R.id.radio_engineer) {
            member = new Engineer(name, initialEnergy);
        } else if (selectedId == R.id.radio_pilot) {
            member = new Pilot(name, initialEnergy);
        } else if (selectedId == R.id.radio_scientist) {
            member = new Scientist(name, initialEnergy);
        }

        if (member != null) {
            if (GameManager.getInstance().useEnergy(GameManager.COST_RECRUIT)) {
                GameManager.getInstance().addCrewMember(member);
                Toast.makeText(this, "Recruited " + name, Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Not enough energy!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please select a specialization", Toast.LENGTH_SHORT).show();
        }
    }
}