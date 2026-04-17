package com.example.oopproject.threat;

import com.example.oopproject.playerJob.CrewMember;

public class VoidReaperBoss extends Threat {

    public VoidReaperBoss(String name, int maxHealth, int damage, int resilience) {
        super(name, maxHealth, damage, resilience);
    }

    @Override
    public void retaliate(CrewMember target) {
        // Boss retaliation: heavy damage
        target.takeDamage(this.damage);
    }

    @Override
    public void takeDamage(int incomingDamage, CrewMember attacker) {
        // Boss might have some damage reduction or mechanics
        takeNormalDamage(incomingDamage);
    }
}
