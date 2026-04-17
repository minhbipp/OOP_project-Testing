package com.example.oopproject.playerJob;

import com.example.oopproject.threat.Threat;

import java.io.Serializable;

public abstract class CrewMember implements Serializable {
    protected String id;
    protected int exp;
    protected int level;
    protected int energy;

    protected int hp;
    protected int maxHp;
    protected int damage;
    protected int resilience = 0;
    protected CrewLocation location = CrewLocation.QUARTERS;

    // Statistics
    public int missionsCount = 0;
    public int victoriesCount = 0;
    public int trainingSessions = 0;
    public int skillCooldown = 0;
    public boolean hasUsedDeathsDoor = false;

    public boolean isDefending = false;

    // Stat progressions initialized by subclasses: [Lv0, Lv1, Lv2, Lv3]
    protected int[] maxHpProgression;
    protected int[] damageProgression;

    public CrewMember(String id, int energy) {
        this.id = id;
        this.energy = energy;
        this.level = 0;
        this.exp = 0;
    }

    public void takeDamage(int incomingDamage) {
        if (isDefending) {
            incomingDamage /= 2;
            isDefending = false;
        }
        int actualDamage = Math.max(0, incomingDamage - resilience);
        
        if (this.hp - actualDamage <= 0 && !hasUsedDeathsDoor) {
            this.hp = 1;
            hasUsedDeathsDoor = true;
        } else {
            this.hp = Math.max(0, this.hp - actualDamage);
        }
    }

    public void heal(int amount) {
        this.hp = Math.min(maxHp, this.hp + amount);
    }

    public void enterMedbay() {
        this.location = CrewLocation.MEDBAY;
        // Penalty changed: No longer loses level, but resets exp to 0
        this.exp = 0;
        regenerate();
    }

    public void regenerate() {
        this.hp = this.maxHp;
    }

    public CrewLocation getLocation() {
        return location;
    }

    public void setLocation(CrewLocation location) {
        this.location = location;
    }

    protected void initStats() {
        this.maxHp = maxHpProgression[0];
        this.hp = this.maxHp;
        this.damage = damageProgression[0];
    }

    public void gainExp(int amount) {
        int[] xpRequired = {3, 5, 10, 15, 20, 30, 45, 60, 80, 100, 0};
        this.exp += amount;

        while (level < 10 && exp >= xpRequired[level]) {
            exp -= xpRequired[level];
            level++;
            updateStatsForLevel();
        }

        if (level == 10) {
            exp = 0;
        }
    }

    protected void updateStatsForLevel() {
        int oldMaxHp = maxHp;
        maxHp = maxHpProgression[level];
        damage = damageProgression[level];
        hp += Math.max(0, maxHp - oldMaxHp);
    }

    public void attack(Threat threat) {
        threat.takeDamage(this.damage, this);
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public abstract String getSpecialSkillName();
    public abstract String getSpecialSkillDescription();

    // --- Getters & Setters ---
    public String getId() { return id; }
    public int getExp() { return exp; }
    public int getLevel() { return level; }
    public int getEnergy() { return energy; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public int getDamage() { return damage; }
    public int getResilience() { return resilience; }

    public int getAttackPower() { return damage; }

    public void setExp(int exp) { this.exp = exp; }
    public void setLevel(int level) { this.level = level; }
    public void setEnergy(int energy) { this.energy = energy; }
    public void setHp(int hp) { this.hp = Math.max(0, Math.min(hp, maxHp)); }
    public void setMaxHp(int maxHp) { this.maxHp = maxHp; }
    public void setDamage(int damage) { this.damage = damage; }
    public void setResilience(int resilience) { this.resilience = resilience; }
}