package com.example.oopproject.threat;

import com.example.oopproject.playerJob.CrewMember;
import com.example.oopproject.playerJob.Pilot;

public class StealthFighter extends Threat {

    public StealthFighter(String name, int maxHealth, int damage, int resilience) {
        super(name, maxHealth, damage, resilience);
    }

    @Override
    public void retaliate(CrewMember target) {
        // Stealth Fighter specifically retaliates harder against Pilots
        int finalDamage = this.damage;
        if (target instanceof Pilot) {
            finalDamage += 2;
        }
        target.takeDamage(finalDamage);
    }

}