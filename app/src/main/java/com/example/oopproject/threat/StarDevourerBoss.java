package com.example.oopproject.threat;

import com.example.oopproject.playerJob.CrewMember;

public class StarDevourerBoss extends Threat {

    public StarDevourerBoss(String name, int maxHealth, int damage, int resilience) {
        super(name, maxHealth, damage, resilience);
    }

    @Override
    public void retaliate(CrewMember target) {
        // Star Devourer deals massive damage
        target.takeDamage(this.damage);
    }

    @Override
    public void takeDamage(int incomingDamage, CrewMember attacker) {
        // Can add unique mechanics here
        takeNormalDamage(incomingDamage);
    }
}
