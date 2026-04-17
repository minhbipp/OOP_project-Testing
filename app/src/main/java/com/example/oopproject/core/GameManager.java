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

public class GameManager implements Serializable {
    private static GameManager instance;
    private Map<Integer, CrewMember> crewMap = new HashMap<>();
    private int nextId = 1;
    
    private static final String FILE_NAME = "game_save.dat";

    // Statistics
    public int totalMissions = 0;
    public int totalWins = 0;
    public int totalRecruited = 0;

    // Day & Energy System
    public int currentDay = 1;
    public int energy = 25;
    public static final int MAX_ENERGY = 25;
    
    public static final int COST_RECRUIT = 5;
    public static final int COST_COMBAT = 5;
    public static final int COST_HEAL = 2;
    public static final int COST_TRAIN = 4;

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
        return currentDay == 10 || currentDay == 20 || currentDay == 30 || currentDay == 40;
    }

    public boolean isGameOver() {
        return currentDay > 40;
    }

    private GameManager() {}

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