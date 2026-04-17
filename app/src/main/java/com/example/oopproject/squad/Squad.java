package com.example.oopproject.squad;

import com.example.oopproject.playerJob.CrewMember;

import java.util.ArrayList;
import java.util.List;

public class Squad {

    private List<CrewMember> members;
    private int maxSize;

    public Squad(int maxSize) {
        this.members = new ArrayList<>();
        this.maxSize = maxSize;
    }

    public void addMember(CrewMember member) {
        if (isFull()) {
            System.out.println("Squad is full! Cannot add " + member.getId());
            return;
        }
        members.add(member);
    }

    public void removeMember(CrewMember member) {
        members.remove(member);
    }

    public List<CrewMember> getMembers() {
        return members;
    }

    public List<CrewMember> getAliveMembers() {
        List<CrewMember> alive = new ArrayList<>();
        for (CrewMember member : members) {
            if (member.isAlive()) {
                alive.add(member);
            }
        }
        return alive;
    }

    public List<CrewMember> getDeadMembers() {
        List<CrewMember> dead = new ArrayList<>();
        for (CrewMember member : members) {
            if (!member.isAlive()) {
                dead.add(member);
            }
        }
        return dead;
    }

    public boolean isFull() {
        return members.size() >= maxSize;
    }

    public boolean isEmpty() {
        return members.isEmpty();
    }
}