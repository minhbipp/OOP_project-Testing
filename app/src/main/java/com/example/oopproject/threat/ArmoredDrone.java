package com.example.oopproject.threat;

import com.example.oopproject.playerJob.CrewMember;
import com.example.oopproject.playerJob.Engineer;

public class ArmoredDrone extends Threat {
    private double resistance;

    public ArmoredDrone(String name, int maxHealth, int damage, int resilience) {
        super(name, maxHealth, damage, resilience);
        this.resistance = 0.8;
    }

    @Override
    public void retaliate(CrewMember target) {
        // Armored Drone retaliates harder against Engineers
        int finalDamage = this.damage;
        if (target instanceof com.example.oopproject.playerJob.Engineer) {
            finalDamage += 3;
        }
        target.takeDamage(finalDamage);
    }

    @Override
    public void takeDamage(int incomingDamage, CrewMember attacker) {
        int actualDamage = incomingDamage;
        if (attacker instanceof com.example.oopproject.playerJob.Engineer) {
            actualDamage = (int)(incomingDamage * (1 - resistance));
        }
        takeNormalDamage(actualDamage);
    }
}