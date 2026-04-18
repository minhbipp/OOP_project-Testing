package com.example.oopproject.playerJob;

import com.example.oopproject.threat.Threat;

public class Pilot extends CrewMember {
    private double[] extraAttackPercent = {0.20, 0.25, 0.30, 0.40};

    public Pilot(String id, int energy) {
        super(id, energy);
        maxHpProgression = new int[]{15, 20, 25, 30};
        damageProgression = new int[]{5, 8, 10, 15};
        initStats();
    }

    public void extraAttack(Threat target) {
        // Deals % of damage as an additional attack
        if (sp >= 1) {
            useSp(1);
            int extraDmg = (int) (damage * extraAttackPercent[level]);
            target.takeNormalDamage(extraDmg);
        }
    }

    @Override
    public String getSpecialSkillName() {
        return "extraAttack";
    }

    @Override
    public String getSpecialSkillDescription() {
        return "(-1SP) performs an additional attack = " + (int)(extraAttackPercent[level] * 100) + "% Atk to a random threat.";
    }
}