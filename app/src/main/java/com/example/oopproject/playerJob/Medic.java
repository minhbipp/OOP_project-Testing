package com.example.oopproject.playerJob;

public class Medic extends CrewMember {
    private int[] healProgression = {3, 5, 8, 10};

    public Medic(String id, int energy) {
        super(id, energy);
        maxHpProgression = new int[]{5, 10, 15, 20};
        damageProgression = new int[]{2, 3, 4, 5};
        initStats();
    }

    public void heal(CrewMember member) {
        if (sp >= 1) {
            useSp(1);
            // Recovers HP based on level progression
            member.setHp(member.getHp() + healProgression[level]);
        }
    }

    @Override
    public String getSpecialSkillName() {
        return "heal";
    }

    @Override
    public String getSpecialSkillDescription() {
        return "(-1SP) Recover " + healProgression[level] + " HP for 1 selected crew.";
    }
}