package com.example.oopproject.playerJob;

public class Scientist extends CrewMember {
    private int[] boostProgression = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12};

    public Scientist(String id, int energy) {
        super(id, energy);
        maxHpProgression = new int[]{10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 65};
        damageProgression = new int[]{4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
        initStats();
    }

    public void boostAttack(CrewMember member) {
        // Increases Damage based on level progression
        member.damage += boostProgression[level];
    }

    @Override
    public String getSpecialSkillName() {
        return "Analyse";
    }

    @Override
    public String getSpecialSkillDescription() {
        return "Permanently boosts a crew member's damage by " + boostProgression[level] + ".";
    }
}