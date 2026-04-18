package com.example.oopproject.playerJob;

public class Scientist extends CrewMember {
    private int[] boostProgression = {1, 2, 3, 4};

    public Scientist(String id, int energy) {
        super(id, energy);
        maxHpProgression = new int[]{10, 15, 20, 25};
        damageProgression = new int[]{2, 3, 4, 5};
        initStats();
    }

    public void boostAttack(CrewMember member) {
        if (sp >= 1) {
            useSp(1);
            // Logic for "for 2 turns" needs to be handled in the battle loop/buff system
            // For now, we apply the boost.
            member.damage += boostProgression[level];
        }
    }

    @Override
    public String getSpecialSkillName() {
        return "boostAttack";
    }

    @Override
    public String getSpecialSkillDescription() {
        return "(-1SP) Increase " + boostProgression[level] + " Atk to 1 selected crew for 2 turns";
    }
}