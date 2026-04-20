package com.example.oopproject.Class_UI;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.oopproject.core.GameManager;
import com.example.oopproject.playerJob.CrewLocation;
import com.example.oopproject.R;

public class HomeActivity extends AppCompatActivity {

    private TextView textQuarters, textSimulator, textMission, textDay, textEnergy, textCredits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Load existing game data
        GameManager.loadGame(this);

        textQuarters = findViewById(R.id.text_quarters_count);
        textSimulator = findViewById(R.id.text_simulator_count);
        textMission = findViewById(R.id.text_mission_count);
        textDay = findViewById(R.id.text_day);
        textEnergy = findViewById(R.id.text_energy);
        textCredits = findViewById(R.id.text_credits);

        findViewById(R.id.btn_recruit).setOnClickListener(v -> {
            startActivity(new Intent(this, RecruitActivity.class));
        });
        
        findViewById(R.id.btn_quarters).setOnClickListener(v -> startActivity(new Intent(this, QuartersActivity.class)));
        findViewById(R.id.btn_simulator).setOnClickListener(v -> startActivity(new Intent(this, SimulatorActivity.class)));
        findViewById(R.id.btn_mission_control).setOnClickListener(v -> startActivity(new Intent(this, MissionControlActivity.class)));
        
        findViewById(R.id.btn_next_day).setOnClickListener(v -> {
            GameManager.getInstance().nextDay();
            updateCounts();
            checkBossWarning();
        });

        findViewById(R.id.btn_stats).setOnClickListener(v -> {
            startActivity(new Intent(this, StatisticsActivity.class));
        });

        findViewById(R.id.btn_shop).setOnClickListener(v -> {
            startActivity(new Intent(this, ShopActivity.class));
        });

        findViewById(R.id.btn_restart).setOnClickListener(v -> {
            GameManager.resetGame(this);
            Intent intent = new Intent(this, DifficultySelectionActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            android.widget.Toast.makeText(this, "Game Restarted", android.widget.Toast.LENGTH_SHORT).show();
        });
        
        checkBossWarning();
    }

    private void checkBossWarning() {
        GameManager gm = GameManager.getInstance();
        if (gm.currentDay == 9 || gm.currentDay == 19 || gm.currentDay == 29) {
            android.widget.Toast.makeText(this, "WARNING: BOSS ENCOUNTER TOMORROW!", android.widget.Toast.LENGTH_LONG).show();
        } else if (gm.isBossDay()) {
            android.widget.Toast.makeText(this, "BOSS IS HERE! Prepare your 5 best crew!", android.widget.Toast.LENGTH_LONG).show();
        }

        if (gm.isGameOver()) {
            if (gm.getCrewList().isEmpty()) {
                showGameOverDialog("GAME OVER", "All crew members have been lost. The mission failed.");
            } else if (gm.currentDay > 30) {
                showGameOverDialog("VICTORY!", "Congratulations! You have defeated the Omega Entity and survived all 30 days!");
            }
        }
    }

    private void showGameOverDialog(String title, String message) {
        new android.app.AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message + "\n\nWould you like to restart?")
                .setCancelable(false)
                .setPositiveButton("Restart", (dialog, which) -> {
                    GameManager.resetGame(this);
                    Intent intent = new Intent(this, DifficultySelectionActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("Exit", (dialog, which) -> finish())
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCounts();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Auto-save when leaving the home screen
        GameManager.getInstance().saveGame(this);
    }

    private void updateCounts() {
        GameManager gm = GameManager.getInstance();
        textQuarters.setText("Crew in Quarters: " + gm.getCrewAt(CrewLocation.QUARTERS).size());
        textSimulator.setText("Crew in Simulator: " + gm.getCrewAt(CrewLocation.SIMULATOR).size());
        textMission.setText("Crew in Mission Control: " + gm.getCrewAt(CrewLocation.MISSION_CONTROL).size());
        textDay.setText("Day: " + gm.currentDay);
        textEnergy.setText("Energy: " + gm.energy + "/" + GameManager.MAX_ENERGY);
        textCredits.setText("CR: " + gm.credits);

        boolean isBoss = gm.isBossDay();
        findViewById(R.id.btn_recruit).setEnabled(!isBoss);
        findViewById(R.id.btn_quarters).setEnabled(!isBoss);
        findViewById(R.id.btn_simulator).setEnabled(!isBoss);
        findViewById(R.id.btn_next_day).setEnabled(!isBoss);
        findViewById(R.id.btn_stats).setEnabled(!isBoss);
    }
}