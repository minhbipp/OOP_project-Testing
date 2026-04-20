package com.example.oopproject.Class_UI;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.oopproject.R;
import com.example.oopproject.core.GameManager;

public class DifficultySelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // If a game already exists and we're not restarting, skip to Home
        GameManager.loadGame(this);
        if (GameManager.getInstance().getTotalCrewCount() > 0) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_difficulty_selection);

        findViewById(R.id.btn_easy).setOnClickListener(v -> selectDifficulty(GameManager.Difficulty.EASY));
        findViewById(R.id.btn_medium).setOnClickListener(v -> selectDifficulty(GameManager.Difficulty.MEDIUM));
        findViewById(R.id.btn_hard).setOnClickListener(v -> selectDifficulty(GameManager.Difficulty.HARD));
        findViewById(R.id.btn_insane).setOnClickListener(v -> selectDifficulty(GameManager.Difficulty.INSANE));
    }

    private void selectDifficulty(GameManager.Difficulty difficulty) {
        GameManager.getInstance().setDifficulty(difficulty);
        GameManager.getInstance().saveGame(this);
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
}