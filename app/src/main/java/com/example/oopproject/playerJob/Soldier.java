package com.example.oopproject.playerJob;

public class Soldier extends CrewMember {
    private int[] enrageAtkBuff = {1, 2, 3, 5, 7, 9, 11, 14, 17, 20, 25};
    private boolean hasEnraged = false;

    public Soldier(String id, int energy) {
        super(id, energy);
        maxHpProgression = new int[]{15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 75};
        damageProgression = new int[]{7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 30};
        initStats();
    }

    public void enrage() {
        // When HP is below 50%, increase self Atk (Activate once)
        if (!hasEnraged && getHp() < (maxHp * 0.5)) {
            damage += enrageAtkBuff[level];
            hasEnraged = true;
        }
    }

    public void resetEnrage() {
        hasEnraged = false;
    }

    @Override
    public String getSpecialSkillName() {
        return "Enrage";
    }

    @Override
    public String getSpecialSkillDescription() {
        return "Increases damage by " + enrageAtkBuff[level] + " when HP is below 50%.";
    }
}