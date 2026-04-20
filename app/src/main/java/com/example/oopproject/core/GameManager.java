package com.example.oopproject.core;

import com.example.oopproject.playerJob.CrewLocation;
import com.example.oopproject.playerJob.CrewMember;

import java.util.ArrayList;
import java.util.List;

import java.util.HashMap;
import java.util.Map;

import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import android.content.Context;

import com.example.oopproject.storage.ConsumableItem;

public class GameManager implements Serializable {
    private static GameManager instance;
    private Map<Integer, CrewMember> crewMap = new HashMap<>();
    private int nextId = 1;
    
    private List<ConsumableItem> inventory = new ArrayList<>();
    private static final Map<ConsumableItem.ItemType, Integer> ITEM_LIMITS = new HashMap<>();
    static {
        ITEM_LIMITS.put(ConsumableItem.ItemType.ATK_POTION, 5);
        ITEM_LIMITS.put(ConsumableItem.ItemType.HP_POTION, 5);
        ITEM_LIMITS.put(ConsumableItem.ItemType.DEF_POTION, 5);
    }
    
    private static final String FILE_NAME = "game_save.dat";

    public List<ConsumableItem> getInventory() {
        return inventory;
    }

    public boolean addItem(ConsumableItem item) {
        int count = 0;
        for (ConsumableItem i : inventory) {
            if (i.getType() == item.getType()) {
                count++;
            }
        }
        
        Integer limit = ITEM_LIMITS.get(item.getType());
        if (limit != null && count >= limit) {
            return false; // Limit reached
        }
        
        inventory.add(item);
        return true;
    }

    public void removeItem(ConsumableItem item) {
        inventory.remove(item);
    }

    public ConsumableItem rollForItemDrop() {
        double roll = Math.random() * 100;
        if (roll < 15) return new ConsumableItem(ConsumableItem.ItemType.ATK_POTION);
        if (roll < 30) return new ConsumableItem(ConsumableItem.ItemType.HP_POTION);
        if (roll < 45) return new ConsumableItem(ConsumableItem.ItemType.DEF_POTION);
        return null;
    }

    // Statistics
    public int totalMissions = 0;
    public int totalWins = 0;
    public int totalRecruited = 0;

    public enum Difficulty {
        EASY(1.0), MEDIUM(1.15), HARD(1.3), INSANE(1.50);
        private final double multiplier;
        Difficulty(double multiplier) { this.multiplier = multiplier; }
        public double getMultiplier() { return multiplier; }
    }
    private Difficulty difficulty = Difficulty.EASY;

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    // Day & Energy System
    public int currentDay = 1;
    public int energy = 25;
    public static final int MAX_ENERGY = 25;

    public int credits = 10;
    public static final int MAX_STORAGE = 6;
    
    public static final int COST_PILOT = 1;
    public static final int COST_MEDIC = 2;
    public static final int COST_SOLDIER = 2;
    public static final int COST_SCIENTIST = 3;
    public static final int COST_ENGINEER = 3;

    public static final int COST_COMBAT = 5;
    public static final int COST_HEAL = 2;
    public static final int COST_TRAIN = 4;
    public static final int COST_RECRUIT = 5;

    public boolean useCredits(int amount) {
        if (credits >= amount) {
            credits -= amount;
            return true;
        }
        return false;
    }

    public void addCredits(int amount) {
        this.credits += amount;
    }

    public boolean canAddCrew() {
        return crewMap.size() < MAX_STORAGE;
    }

    public boolean useEnergy(int amount) {
        if (energy >= amount) {
            energy -= amount;
            return true;
        }
        return false;
    }

    public void nextDay() {
        currentDay++;
        energy = MAX_ENERGY;
        
        // Final Polish: Auto-heal crew in Medbay and Quarters
        for (CrewMember m : crewMap.values()) {
            if (m.getLocation() == CrewLocation.MEDBAY) {
                m.heal((int) (m.getMaxHp() * 0.3)); // Heal 30% in Medbay
            } else if (m.getLocation() == CrewLocation.QUARTERS) {
                m.heal((int) (m.getMaxHp() * 0.1)); // Heal 10% in Quarters
            }
        }

        if (isBossDay()) {
            forceMoveToMissionControl();
        }
    }

    private void forceMoveToMissionControl() {
        for (CrewMember m : crewMap.values()) {
            if (m.getLocation() == CrewLocation.QUARTERS || m.getLocation() == CrewLocation.SIMULATOR) {
                m.setLocation(CrewLocation.MISSION_CONTROL);
            }
        }
    }

    public boolean isBossDay() {
        return currentDay == 10 || currentDay == 20 || currentDay == 30;
    }

    public boolean isGameOver() {
        return (currentDay > 30) || (currentDay > 1 && crewMap.isEmpty());
    }

    private GameManager() {
        // Initial state is empty; crew will be added by startNewGame or difficulty selection
    }

    public static synchronized GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    public static synchronized void resetGame(Context context) {
        instance = new GameManager();
        context.deleteFile(FILE_NAME);
    }

    public void saveGame(Context context) {
        try (FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadGame(Context context) {
        try (FileInputStream fis = context.openFileInput(FILE_NAME);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            instance = (GameManager) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<Integer, CrewMember> getCrewMap() { return crewMap; }
    public int getNextId() { return nextId; }
    public int getTotalMissions() { return totalMissions; }
    public int getTotalWins() { return totalWins; }
    public int getTotalRecruited() { return totalRecruited; }
    public int getCurrentDay() { return currentDay; }
    public int getEnergy() { return energy; }
    public int getCredits() { return credits; }

    public void loadFromSaveData(SaveData data) {
        this.crewMap = data.crewMap;
        this.nextId = data.nextId;
        this.inventory = data.inventory;
        this.totalMissions = data.totalMissions;
        this.totalWins = data.totalWins;
        this.totalRecruited = data.totalRecruited;
        this.difficulty = Difficulty.valueOf(data.difficulty);
        this.currentDay = data.currentDay;
        this.energy = data.energy;
        this.credits = data.credits;
    }

    public List<CrewMember> getCrewList() {
        return new ArrayList<>(crewMap.values());
    }

    public List<CrewMember> getCrewAt(CrewLocation location) {
        List<CrewMember> result = new ArrayList<>();
        for (CrewMember m : crewMap.values()) {
            if (m.getLocation() == location) {
                result.add(m);
            }
        }
        return result;
    }

    public int getTotalCrewCount() {
        return crewMap.size();
    }

    public int getTotalCrewXp() {
        int totalXp = 0;
        for (CrewMember m : crewMap.values()) {
            totalXp += m.getExp();
        }
        return totalXp;
    }

    public int getTotalTrainingSessions() {
        int total = 0;
        for (CrewMember m : crewMap.values()) {
            total += m.trainingSessions;
        }
        return total;
    }

    public void addCrewMember(CrewMember member) {
        crewMap.put(nextId++, member);
        totalRecruited++;
    }

    public void removeCrewMember(CrewMember member) {
        Integer keyToRemove = null;
        for (Map.Entry<Integer, CrewMember> entry : crewMap.entrySet()) {
            if (entry.getValue().equals(member)) {
                keyToRemove = entry.getKey();
                break;
            }
        }
        if (keyToRemove != null) {
            crewMap.remove(keyToRemove);
        }
    }
}