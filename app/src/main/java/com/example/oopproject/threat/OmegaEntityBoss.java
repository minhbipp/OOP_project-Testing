package com.example.oopproject.threat;

import com.example.oopproject.playerJob.CrewMember;

public class OmegaEntityBoss extends Threat {

    public OmegaEntityBoss(String name, int maxHealth, int damage, int resilience) {
        super(name, maxHealth, damage, resilience);
    }

    @Override
    public void retaliate(CrewMember target) {
        // Final Boss deals overwhelming damage
        target.takeDamage(this.damage);
    }

    @Override
    public void takeDamage(int incomingDamage, CrewMember attacker) {
        // Final Boss damage logic
        takeNormalDamage(incomingDamage);
    }
}
