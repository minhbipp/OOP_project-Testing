package com.example.oopproject.core;

import android.content.Context;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SaveManager {
    private static final String SAVE_FILE_PREFIX = "save_slot_";
    private static final int MAX_SLOTS = 10;

    public static void save(Context context, int slot, String saveName) {
        if (slot < 1 || slot > MAX_SLOTS) return;
        
        GameManager gm = GameManager.getInstance();
        SaveData data = new SaveData(gm, saveName);
        
        String fileName = SAVE_FILE_PREFIX + slot + ".dat";
        try (FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SaveData load(Context context, int slot) {
        String fileName = SAVE_FILE_PREFIX + slot + ".dat";
        try (FileInputStream fis = context.openFileInput(fileName);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (SaveData) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    public static List<SaveData> getAllSaves(Context context) {
        List<SaveData> saves = new ArrayList<>();
        for (int i = 1; i <= MAX_SLOTS; i++) {
            saves.add(load(context, i));
        }
        return saves;
    }

    public static void deleteSave(Context context, int slot) {
        String fileName = SAVE_FILE_PREFIX + slot + ".dat";
        context.deleteFile(fileName);
    }
}
