package com.example.oopproject.ui;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oopproject.core.GameManager;
import com.example.oopproject.playerJob.CrewMember;
import com.example.oopproject.playerJob.Engineer;
import com.example.oopproject.playerJob.Medic;
import com.example.oopproject.playerJob.Soldier;
import com.example.oopproject.ui.adapter.CrewAdapter;
import com.example.oopproject.R;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_crew);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        GameManager gm = GameManager.getInstance();
        gm.loadGame(this);

        if (gm.getCrewList().isEmpty()) {
            // Initial crew if new game
            gm.addCrewMember(new Soldier("Sgt. Marcus", 100));
            gm.addCrewMember(new Medic("Dr. Halsey", 80));
            gm.addCrewMember(new Engineer("Isaac", 90));
            gm.saveGame(this);
        }

        CrewAdapter adapter = new CrewAdapter(gm.getCrewList());
        recyclerView.setAdapter(adapter);

        Button btnRestart = findViewById(R.id.btn_restart);
        btnRestart.setOnClickListener(v -> {
            GameManager.resetGame(this);
            // Refresh current activity to show reset state
            finish();
            startActivity(getIntent());
            Toast.makeText(this, "Game Restarted", Toast.LENGTH_SHORT).show();
        });
    }
}