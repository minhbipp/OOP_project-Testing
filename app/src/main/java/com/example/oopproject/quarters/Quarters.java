package com.example.oopproject.quarters;

import com.example.oopproject.playerJob.CrewMember;

import java.util.ArrayList;
import java.util.List;

public class Quarters {
    private List<CrewMember> members;

    public Quarters() {
        this.members = new ArrayList<>();
    }

    public void addCrewMember(CrewMember member) {
        members.add(member);
    }

    public List<CrewMember> getAvailableCrew() {
        List<CrewMember> available = new ArrayList<>();
        for (CrewMember member : members) {
            if (member.isAlive() && member.getEnergy() > 0) {
                available.add(member);
            }
        }
        return available;
    }
}
