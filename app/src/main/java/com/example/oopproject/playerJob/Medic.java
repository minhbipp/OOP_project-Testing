package com.example.oopproject.playerJob;

public class Medic extends CrewMember {
    private int[] healProgression = {3, 5, 8, 10, 12, 15, 18, 22, 26, 30, 35};

    public Medic(String id, int energy) {
        super(id, energy);
        maxHpProgression = new int[]{12, 16, 20, 24, 28, 32, 36, 40, 44, 48, 55};
        damageProgression = new int[]{2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        initStats();
    }

    public void heal(CrewMember member) {
        // Recovers HP based on level progression
        member.setHp(member.getHp() + healProgression[level]);
    }

    @Override
    public String getSpecialSkillName() {
        return "Heal";
    }

    @Override
    public String getSpecialSkillDescription() {
        return "Restores " + healProgression[level] + " HP to a wounded crew member.";
    }
}