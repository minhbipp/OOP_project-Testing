package com.example.oopproject.playerJob;

import com.example.oopproject.threat.Threat;

public class Pilot extends CrewMember {
    private double[] extraAttackPercent = {0.20, 0.25, 0.30, 0.40, 0.45, 0.50, 0.55, 0.60, 0.65, 0.70, 0.75};

    public Pilot(String id, int energy) {
        super(id, energy);
        maxHpProgression = new int[]{15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 70};
        damageProgression = new int[]{5, 8, 10, 15, 18, 21, 24, 27, 30, 33, 36};
        initStats();
    }

    public void extraAttack(Threat target) {
        // Deals % of damage as an additional attack
        int extraDmg = (int) (damage * extraAttackPercent[level]);
        target.takeNormalDamage(extraDmg);
    }

    @Override
    public String getSpecialSkillName() {
        return "Evasive Strike";
    }

    @Override
    public String getSpecialSkillDescription() {
        return "Grants a chance to perform an extra attack dealing " + (int)(extraAttackPercent[level] * 100) + "% damage.";
    }
}