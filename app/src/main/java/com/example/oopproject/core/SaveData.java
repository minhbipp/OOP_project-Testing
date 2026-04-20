package com.example.oopproject.core;

import com.example.oopproject.playerJob.CrewMember;
import com.example.oopproject.storage.ConsumableItem;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class SaveData implements Serializable {
    private static final long serialVersionUID = 1L;
    public Map<Integer, CrewMember> crewMap;
    public int nextId;
    public List<ConsumableItem> inventory;
    public int totalMissions;
    public int totalWins;
    public int totalRecruited;
    public String difficulty;
    public int currentDay;
    public int energy;
    public int credits;
    public long timestamp;
    public String saveName;


    public SaveData(GameManager gm, String saveName) {
        this.crewMap = gm.getCrewMap();
        this.nextId = gm.getNextId();
        this.inventory = gm.getInventory();
        this.totalMissions = gm.getTotalMissions();
        this.totalWins = gm.getTotalWins();
        this.totalRecruited = gm.getTotalRecruited();
        this.difficulty = gm.getDifficulty().name();
        this.currentDay = gm.getCurrentDay();
        this.energy = gm.getEnergy();
        this.credits = gm.getCredits();
        this.timestamp = System.currentTimeMillis();
        this.saveName = saveName;
    }
}
