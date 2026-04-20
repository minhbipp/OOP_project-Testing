package com.example.oopproject.playerJob;

public class Engineer extends CrewMember {

    public Engineer(String id, int energy) {
        super(id, energy);
        maxHpProgression = new int[]{15, 20, 25, 30};
        damageProgression = new int[]{2, 3, 4, 5};
        initStats();
    }
    @Override
    public String getSpecialSkillName() {
        return "boostDefense";
    }
}