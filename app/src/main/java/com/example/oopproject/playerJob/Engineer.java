package com.example.oopproject.playerJob;

public class Engineer extends CrewMember {

    public Engineer(String id, int energy) {
        super(id, energy);
        maxHpProgression = new int[]{15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 75};
        damageProgression = new int[]{2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        initStats();
    }

    public void boostDefense(CrewMember member) {
        // Increases a crew member's max HP by 20%
        int boost = (int)(member.getMaxHp() * 0.2);
        member.setMaxHp(member.getMaxHp() + boost);
        member.setHp(member.getHp() + boost); // Give them the health to match the new cap
    }

    @Override
    public String getSpecialSkillName() {
        return "Reinforce";
    }

    @Override
    public String getSpecialSkillDescription() {
        return "Increases a crew member's Max HP by 20% for the rest of the mission.";
    }
}