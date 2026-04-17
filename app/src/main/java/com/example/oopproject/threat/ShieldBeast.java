package com.example.oopproject.threat;

import com.example.oopproject.playerJob.CrewMember;
import com.example.oopproject.playerJob.Soldier;

public class ShieldBeast extends Threat {
    private double resistance;

    public ShieldBeast(String name, int maxHealth, int damage, int resilience) {
        super(name, maxHealth, damage, resilience);
        this.resistance = 0.8;
    }

    @Override
    public void retaliate(CrewMember target) {
        // Shield Beast retaliates harder against Soldiers
        int finalDamage = this.damage;
        if (target instanceof com.example.oopproject.playerJob.Soldier) {
            finalDamage += 4;
        }
        target.takeDamage(finalDamage);
    }

    @Override
    public void takeDamage(int incomingDamage, CrewMember attacker) {
        int actualDamage = incomingDamage;
        if (attacker instanceof com.example.oopproject.playerJob.Soldier) {
            actualDamage = (int)(incomingDamage * (1 - resistance));
        }
        takeNormalDamage(actualDamage);
    }
}