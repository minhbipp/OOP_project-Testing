package com.example.oopproject.threat;

import com.example.oopproject.playerJob.CrewMember;

public abstract class Threat {
    private String name;
    private int health;
    private int maxHealth;
    protected int damage;
    protected int resilience;

    public Threat(String name, int maxHealth, int damage, int resilience) {
        double multiplier = com.example.oopproject.core.GameManager.getInstance().getDifficulty().getMultiplier();
        this.name = name;
        // Nerfed for balance: -50% HP, -60% Damage (40% remaining)
        this.maxHealth = (int) (maxHealth * multiplier * 0.5);
        this.health = this.maxHealth;
        this.damage = (int) (damage * multiplier * 0.4);
        this.resilience = (int) (resilience * multiplier);
    }

    public abstract void retaliate(CrewMember target);

    public int getResilience() {
        return resilience;
    }

    public boolean isDefeated() {
        return health <= 0;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getAttackPower() {
        return damage;
    }

    public abstract void takeDamage(int incomingDamage, CrewMember attacker);

    public void takeNormalDamage(int incomingDamage) {
        int actualDamage = Math.max(0, incomingDamage - resilience);
        this.health = Math.max(0, this.health - actualDamage);
    }
}
