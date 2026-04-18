package com.example.oopproject.playerJob;

public class Soldier extends CrewMember {
    private int[] enrageAtkBuff = {1, 2, 3, 5};
    private boolean hasEnraged = false;

    public Soldier(String id, int energy) {
        super(id, energy);
        maxHpProgression = new int[]{15, 20, 25, 30};
        damageProgression = new int[]{4, 6, 8, 10};
        initStats();
    }

    public boolean canEnrage() {
        return !hasEnraged && getHp() < (maxHp * 0.5);
    }

    public void enrage(java.util.List<com.example.oopproject.threat.Threat> threats) {
        if (canEnrage()) {
            for (com.example.oopproject.threat.Threat t : threats) {
                t.takeNormalDamage(10);
            }
            damage += enrageAtkBuff[level];
            hasEnraged = true;
        }
    }

    public void resetEnrage() {
        hasEnraged = false;
    }

    @Override
    public String getSpecialSkillName() {
        return "enrage";
    }

    @Override
    public String getSpecialSkillDescription() {
        return "(-0SP) If HP < 50%, AOE 10 dmg to all and self Atk +" + enrageAtkBuff[level] + " (Once per battle)";
    }
}