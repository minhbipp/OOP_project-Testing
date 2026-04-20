package com.example.oopproject.Class_UI;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.oopproject.core.GameManager;
import com.example.oopproject.storage.ConsumableItem;
import com.example.oopproject.R;

public class ShopActivity extends AppCompatActivity {

    private TextView textCredits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        textCredits = findViewById(R.id.text_credits_display);
        updateCreditsDisplay();

        findViewById(R.id.btn_buy_atk).setOnClickListener(v -> buyItem(ConsumableItem.ItemType.ATK_POTION));
        findViewById(R.id.btn_buy_hp).setOnClickListener(v -> buyItem(ConsumableItem.ItemType.HP_POTION));
        findViewById(R.id.btn_buy_def).setOnClickListener(v -> buyItem(ConsumableItem.ItemType.DEF_POTION));

        findViewById(R.id.btn_close_shop).setOnClickListener(v -> finish());
    }

    private void buyItem(ConsumableItem.ItemType type) {
        GameManager gm = GameManager.getInstance();
        if (gm.credits >= 1) {
            ConsumableItem item = new ConsumableItem(type);
            if (gm.addItem(item)) {
                gm.useCredits(1);
                updateCreditsDisplay();
                Toast.makeText(this, "Bought " + item.getName(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Inventory full for this item!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Not enough credits!", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateCreditsDisplay() {
        textCredits.setText("CREDITS: " + GameManager.getInstance().credits);
    }
}