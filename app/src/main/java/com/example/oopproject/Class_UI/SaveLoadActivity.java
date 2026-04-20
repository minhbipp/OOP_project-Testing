package com.example.oopproject.Class_UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.oopproject.R;
import com.example.oopproject.core.GameManager;
import com.example.oopproject.core.SaveData;
import com.example.oopproject.core.SaveManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SaveLoadActivity extends AppCompatActivity {
    private boolean isSaveMode;
    private RecyclerView recyclerView;
    private SaveAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_load);

        isSaveMode = getIntent().getBooleanExtra("isSaveMode", false);
        TextView title = findViewById(R.id.text_title);
        title.setText(isSaveMode ? "Save Game" : "Load Game");

        recyclerView = findViewById(R.id.recycler_save_slots);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        loadSlots();

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
    }

    private void loadSlots() {
        List<SaveData> saves = SaveManager.getAllSaves(this);
        adapter = new SaveAdapter(saves);
        recyclerView.setAdapter(adapter);
    }

    private class SaveAdapter extends RecyclerView.Adapter<SaveAdapter.ViewHolder> {
        private final List<SaveData> saves;

        public SaveAdapter(List<SaveData> saves) {
            this.saves = saves;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_save_slot, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            int slot = position + 1;
            SaveData data = saves.get(position);

            if (data == null) {
                holder.textName.setText("Slot " + slot + ": Empty");
                holder.textDetails.setText("No data");
                holder.btnAction.setText(isSaveMode ? "Save" : "Load");
                holder.btnAction.setEnabled(isSaveMode);
            } else {
                holder.textName.setText("Slot " + slot + ": Day " + data.currentDay);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                String dateStr = sdf.format(new Date(data.timestamp));
                holder.textDetails.setText("Credits: " + data.credits + " | " + dateStr);
                holder.btnAction.setText(isSaveMode ? "Overwrite" : "Load");
                holder.btnAction.setEnabled(true);
            }

            holder.btnAction.setOnClickListener(v -> {
                if (isSaveMode) {
                    SaveManager.save(SaveLoadActivity.this, slot, "Save " + slot);
                    Toast.makeText(SaveLoadActivity.this, "Game Saved to Slot " + slot, Toast.LENGTH_SHORT).show();
                    loadSlots();
                } else {
                    SaveData loadedData = SaveManager.load(SaveLoadActivity.this, slot);
                    if (loadedData != null) {
                        GameManager.getInstance().loadFromSaveData(loadedData);
                        Toast.makeText(SaveLoadActivity.this, "Game Loaded from Slot " + slot, Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);
                        finish();
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return 10;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView textName, textDetails;
            Button btnAction;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                textName = itemView.findViewById(R.id.text_slot_name);
                textDetails = itemView.findViewById(R.id.text_slot_details);
                btnAction = itemView.findViewById(R.id.btn_action);
            }
        }
    }
}
