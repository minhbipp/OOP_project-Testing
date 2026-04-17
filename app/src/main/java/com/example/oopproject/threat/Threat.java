package com.example.oopproject.threat;

import com.example.oopproject.playerJob.CrewMember;

public abstract class Threat {
    private String name;
    private int health;
    private int maxHealth;
    protected int damage;
    protected int resilience;
    protected String type = "combat";

    public Threat(String name, int maxHealth, int damage, int resilience) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.damage = damage;
        this.resilience = resilience;
    }

    public abstract void retaliate(CrewMember target);

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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

    public int getDamage() {
        return damage;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public abstract void takeDamage(int incomingDamage, CrewMember attacker);

    public void takeNormalDamage(int incomingDamage) {
        int actualDamage = Math.max(0, incomingDamage - resilience);
        this.health = Math.max(0, this.health - actualDamage);
    }
}
