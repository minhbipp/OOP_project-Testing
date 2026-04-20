package com.example.oopproject.storage;

import java.io.Serializable;

public class ConsumableItem implements Serializable {
    public enum ItemType {
        ATK_POTION,    // gain 2Atk in 3 turns for 1 crew (15%)
        HP_POTION,     // gain 3 max HP for 1 crew (15%)
        DEF_POTION     // reduce 1 dmg taken for 1 crew (15%)
    }

    private String name;
    private ItemType type;
    private String description;

    public ConsumableItem(ItemType type) {
        this.type = type;
        switch (type) {
            case ATK_POTION:
                this.name = "Attack Potion";
                this.description = "Gain +2 Attack for the rest of this battle.";
                break;
            case HP_POTION:
                this.name = "HP Potion";
                this.description = "Instantly heal 5 HP.";
                break;
            case DEF_POTION:
                this.name = "Defense Potion";
                this.description = "Gain +2 Resilience for the rest of this battle.";
                break;
        }
    }

    public String getName() { return name; }
    public ItemType getType() { return type; }
}