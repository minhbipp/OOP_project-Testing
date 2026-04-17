package com.example.oopproject.ui;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.oopproject.core.GameManager;
import com.example.oopproject.playerJob.CrewMember;
import com.example.oopproject.playerJob.Engineer;
import com.example.oopproject.playerJob.Medic;
import com.example.oopproject.playerJob.Pilot;
import com.example.oopproject.playerJob.Scientist;
import com.example.oopproject.playerJob.Soldier;
import com.example.oopproject.ui.view.SimplePieChartView;
import com.example.oopproject.R;

import java.util.ArrayList;
import java.util.List;

public class StatisticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        GameManager gm = GameManager.getInstance();

        TextView textCrew = findViewById(R.id.stat_total_crew);
        TextView textXp = findViewById(R.id.stat_total_xp);
        TextView textMissions = findViewById(R.id.stat_total_missions);
        TextView textWins = findViewById(R.id.stat_total_wins);
        TextView textTraining = findViewById(R.id.stat_total_training);

        textCrew.setText("Total Crew: " + gm.getTotalCrewCount());
        textXp.setText("Total Crew XP: " + gm.getTotalCrewXp());
        textMissions.setText("Missions Run: " + gm.totalMissions);
        textWins.setText("Total Wins: " + gm.totalWins);
        textTraining.setText("Training Sessions: " + gm.getTotalTrainingSessions());

        setupChart(gm);

        findViewById(R.id.btn_crew_reference).setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(this, CrewReferenceActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.btn_stats_back).setOnClickListener(v -> finish());
    }

    private void setupChart(GameManager gm) {
        SimplePieChartView chartView = findViewById(R.id.simple_pie_chart);

        int soldiers = 0, medics = 0, pilots = 0, engineers = 0, scientists = 0;
        for (CrewMember m : gm.getCrewList()) {
            if (m instanceof Soldier) soldiers++;
            else if (m instanceof Medic) medics++;
            else if (m instanceof Pilot) pilots++;
            else if (m instanceof Engineer) engineers++;
            else if (m instanceof Scientist) scientists++;
        }

        List<SimplePieChartView.PieEntry> entries = new ArrayList<>();
        if (soldiers > 0) entries.add(new SimplePieChartView.PieEntry("Soldier", soldiers));
        if (medics > 0) entries.add(new SimplePieChartView.PieEntry("Medic", medics));
        if (pilots > 0) entries.add(new SimplePieChartView.PieEntry("Pilot", pilots));
        if (engineers > 0) entries.add(new SimplePieChartView.PieEntry("Engineer", engineers));
        if (scientists > 0) entries.add(new SimplePieChartView.PieEntry("Scientist", scientists));

        if (entries.isEmpty()) {
            entries.add(new SimplePieChartView.PieEntry("Empty", 1));
        }

        chartView.setData(entries);
    }
}
