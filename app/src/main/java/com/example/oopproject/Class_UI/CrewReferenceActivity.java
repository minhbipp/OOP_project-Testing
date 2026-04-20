package com.example.oopproject.Class_UI;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.oopproject.R;

public class CrewReferenceActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crew_reference);

        findViewById(R.id.btn_ref_back).setOnClickListener(v -> finish());
    }
}