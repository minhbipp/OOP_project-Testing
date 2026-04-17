package com.example.oopproject.threat;

import com.example.oopproject.playerJob.CrewMember;
import com.example.oopproject.playerJob.Medic;

public class BiohazardEntity extends Threat {
    private double resistance;

    public BiohazardEntity(String name, int maxHealth, int damage, int resilience) {
        super(name, maxHealth, damage, resilience);
        this.resistance = 0.8;
    }

    @Override
    public void retaliate(CrewMember target) {
        // Biohazard Entity retaliates harder against Medics
        int finalDamage = this.damage;
        if (target instanceof com.example.oopproject.playerJob.Medic) {
            finalDamage += 3;
        }
        target.takeDamage(finalDamage);
    }

    @Override
    public void takeDamage(int incomingDamage, CrewMember attacker) {
        int actualDamage = incomingDamage;
        if (attacker instanceof com.example.oopproject.playerJob.Medic) {
            actualDamage = (int)(incomingDamage * (1 - resistance));
        }
        takeNormalDamage(actualDamage);
    }
}