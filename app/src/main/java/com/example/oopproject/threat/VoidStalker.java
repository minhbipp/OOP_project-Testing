package com.example.oopproject.threat;

import com.example.oopproject.playerJob.CrewMember;

public class VoidStalker extends Threat {
    private double resistance;

    public VoidStalker(String name, int maxHealth, int damage, int resilience) {
        super(name, maxHealth, damage, resilience);
        this.resistance = 0.35;
    }

    @Override
    public void retaliate(CrewMember target) {
        // Void Stalker has standard retaliation but ignores some resilience (simulated)
        int finalDamage = this.damage + 1;
        target.takeDamage(finalDamage);
    }

    @Override
    public void takeDamage(int incomingDamage, CrewMember attacker) {
        int actualDamage = (int)(incomingDamage * (1 - resistance));
        takeNormalDamage(actualDamage);
    }
}