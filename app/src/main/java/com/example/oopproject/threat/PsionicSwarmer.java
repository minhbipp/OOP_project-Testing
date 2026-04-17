package com.example.oopproject.threat;

import com.example.oopproject.playerJob.CrewMember;
import com.example.oopproject.playerJob.Scientist;

public class PsionicSwarmer extends Threat {
    private double resistance;

    public PsionicSwarmer(String name, int maxHealth, int damage, int resilience) {
        super(name, maxHealth, damage, resilience);
        this.resistance = 0.8;
    }

    @Override
    public void retaliate(CrewMember target) {
        // Psionic Swarmer retaliates harder against Scientists
        int finalDamage = this.damage;
        if (target instanceof com.example.oopproject.playerJob.Scientist) {
            finalDamage += 3;
        }
        target.takeDamage(finalDamage);
    }

    @Override
    public void takeDamage(int incomingDamage, CrewMember attacker) {
        int actualDamage = incomingDamage;
        if (attacker instanceof com.example.oopproject.playerJob.Scientist) {
            actualDamage = (int)(incomingDamage * (1 - resistance));
        }
        takeNormalDamage(actualDamage);
    }
}