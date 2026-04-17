package com.example.oopproject.storage;

import com.example.oopproject.playerJob.*;

import java.util.ArrayList;
import java.util.List;

public class Storage {

    private static final int MAX_CREW = 10;
    private static final int MAX_ENERGY = 100;
    private List<CrewMember> crewList = new ArrayList<>();

    public enum Role {
        PILOT, ENGINEER, MEDIC, SCIENTIST, SOLDIER
    }

    public CrewMember createCrewMember(Role role) {
        if (crewList.size() >= MAX_CREW) {
            System.out.println("Storage is full! Cannot create more crew members.");
            return null;
        }

        // Count how many of this role already exist to generate unique ID
        int count = 0;
        for (CrewMember m : crewList) {
            if (m.getClass().getSimpleName().equalsIgnoreCase(role.name())) {
                count++;
            }
        }

        // Generate unique ID e.g. "Pilot1", "Pilot2", "Soldier1"
        String id = role.name().charAt(0) + role.name().substring(1).toLowerCase() + (count + 1);

        CrewMember member;
        switch (role) {
            case PILOT:
                member = new Pilot(id, MAX_ENERGY);
                break;
            case ENGINEER:
                member = new Engineer(id, MAX_ENERGY);
                break;
            case MEDIC:
                member = new Medic(id, MAX_ENERGY);
                break;
            case SCIENTIST:
                member = new Scientist(id, MAX_ENERGY);
                break;
            case SOLDIER:
                member = new Soldier(id, MAX_ENERGY);
                break;
            default:
                throw new IllegalArgumentException("Unknown role: " + role);
        }

        crewList.add(member);
        System.out.println("Created crew member: " + id);
        return member;
    }

    public List<CrewMember> getCrewList() {
        return crewList;
    }

    public void removeCrew(CrewMember member) {
        crewList.remove(member);
        System.out.println(member.getId() + " removed from storage.");
    }

    public boolean isFull() {
        return crewList.size() >= MAX_CREW;
    }

    public int getCrewCount() {
        return crewList.size();
    }
}