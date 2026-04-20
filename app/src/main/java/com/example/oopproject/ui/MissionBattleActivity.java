package com.example.oopproject.ui;

import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.oopproject.core.GameManager;
import com.example.oopproject.playerJob.CrewMember;
import com.example.oopproject.playerJob.Engineer;
import com.example.oopproject.playerJob.Medic;
import com.example.oopproject.playerJob.Pilot;
import com.example.oopproject.playerJob.Scientist;
import com.example.oopproject.playerJob.Soldier;
import com.example.oopproject.threat.OmegaEntityBoss;
import com.example.oopproject.threat.StarDevourerBoss;
import com.example.oopproject.threat.StealthFighter;
import com.example.oopproject.threat.Threat;
import com.example.oopproject.threat.VoidReaperBoss;
import com.example.oopproject.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.media.MediaPlayer;
import android.widget.ImageView;

public class MissionBattleActivity extends AppCompatActivity {

    private TextView textThreatName, textThreatHp, textLog, textActiveMember, textThreatStats, textSkillInfo;
    private ProgressBar progressThreatHp;
    private View layoutActions;
    private Button btnAttack, btnSkill, btnItems;

    private ImageView imgThreat, imgCrew1, imgCrew2, imgCrew3, imgCrew4, imgCrew5, imgBackground;
    private ProgressBar progressCrew1, progressCrew2, progressCrew3, progressCrew4, progressCrew5;
    private TextView textCrew1Name, textCrew2Name, textCrew3Name, textCrew4Name, textCrew5Name;
    private View containerCrew3, containerCrew4, containerCrew5;
    private MediaPlayer mediaPlayer;

    private List<CrewMember> squad = new ArrayList<>();
    private Threat threat;
    private int currentMemberIndex = 0;
    private boolean isCrewPhase = true;
    private StringBuilder logBuilder = new StringBuilder();
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_battle);

        initViews();
        loadSquad();
        initThreat();
        
        startMission();
    }

    private void initViews() {
        textThreatName = findViewById(R.id.text_threat_name);
        textThreatHp = findViewById(R.id.text_threat_hp);
        textLog = findViewById(R.id.text_mission_log);
        textActiveMember = findViewById(R.id.text_active_member);
        textThreatStats = findViewById(R.id.text_threat_stats);
        textSkillInfo = findViewById(R.id.text_skill_info);
        progressThreatHp = findViewById(R.id.progress_threat_hp);
        layoutActions = findViewById(R.id.layout_actions);
        btnAttack = findViewById(R.id.btn_attack);
        btnSkill = findViewById(R.id.btn_skill);
        btnItems = findViewById(R.id.btn_items);
        if (btnItems != null) btnItems.setOnClickListener(v -> handleItems());

        imgThreat = findViewById(R.id.img_threat);
        imgCrew1 = findViewById(R.id.img_crew1);
        imgCrew2 = findViewById(R.id.img_crew2);
        imgCrew3 = findViewById(R.id.img_crew3);
        imgCrew4 = findViewById(R.id.img_crew4);
        imgCrew5 = findViewById(R.id.img_crew5);
        imgBackground = findViewById(R.id.img_background);
        
        progressCrew1 = findViewById(R.id.progress_crew1_hp);
        progressCrew2 = findViewById(R.id.progress_crew2_hp);
        progressCrew3 = findViewById(R.id.progress_crew3_hp);
        progressCrew4 = findViewById(R.id.progress_crew4_hp);
        progressCrew5 = findViewById(R.id.progress_crew5_hp);
        
        textCrew1Name = findViewById(R.id.text_crew1_name);
        textCrew2Name = findViewById(R.id.text_crew2_name);
        textCrew3Name = findViewById(R.id.text_crew3_name);
        textCrew4Name = findViewById(R.id.text_crew4_name);
        textCrew5Name = findViewById(R.id.text_crew5_name);
        
        containerCrew3 = findViewById(R.id.container_crew3);
        containerCrew4 = findViewById(R.id.container_crew4);
        containerCrew5 = findViewById(R.id.container_crew5);

        btnAttack.setOnClickListener(v -> handleAttack());
        btnSkill.setOnClickListener(v -> handleSkill());
    }

    private void loadSquad() {
        String id1 = getIntent().getStringExtra("member1_id");
        String id2 = getIntent().getStringExtra("member2_id");
        String id3 = getIntent().getStringExtra("member3_id");
        String id4 = getIntent().getStringExtra("member4_id");
        String id5 = getIntent().getStringExtra("member5_id");
        
        List<String> ids = new ArrayList<>();
        if (id1 != null) ids.add(id1);
        if (id2 != null) ids.add(id2);
        if (id3 != null) ids.add(id3);
        if (id4 != null) ids.add(id4);
        if (id5 != null) ids.add(id5);
        
        for (CrewMember m : GameManager.getInstance().getCrewList()) {
            if (ids.contains(m.getId())) {
                squad.add(m);
            }
        }

        if (squad.size() >= 1) {
            textCrew1Name.setText(squad.get(0).getId());
            updateCrewUI(squad.get(0), progressCrew1, imgCrew1);
        }
        if (squad.size() >= 2) {
            textCrew2Name.setText(squad.get(1).getId());
            updateCrewUI(squad.get(1), progressCrew2, imgCrew2);
        }
        if (squad.size() >= 3) {
            if (containerCrew3 != null) containerCrew3.setVisibility(View.VISIBLE);
            textCrew3Name.setText(squad.get(2).getId());
            updateCrewUI(squad.get(2), progressCrew3, imgCrew3);
        }
        if (squad.size() >= 4) {
            if (containerCrew4 != null) containerCrew4.setVisibility(View.VISIBLE);
            textCrew4Name.setText(squad.get(3).getId());
            updateCrewUI(squad.get(3), progressCrew4, imgCrew4);
        }
        if (squad.size() >= 5) {
            if (containerCrew5 != null) containerCrew5.setVisibility(View.VISIBLE);
            textCrew5Name.setText(squad.get(4).getId());
            updateCrewUI(squad.get(4), progressCrew5, imgCrew5);
        }
    }

    private void updateCrewUI(CrewMember member, ProgressBar bar, ImageView img) {
        if (bar != null) {
            bar.setMax(member.getMaxHp());
            bar.setProgress(member.getHp());
        }
        if (img != null) {
            if (member instanceof Soldier) img.setImageResource(R.drawable.char_soldier);
            else if (member instanceof Medic) img.setImageResource(R.drawable.char_medic);
            else if (member instanceof Engineer) img.setImageResource(R.drawable.char_engineer);
            else if (member instanceof Pilot) img.setImageResource(R.drawable.char_pilot);
            else if (member instanceof Scientist) img.setImageResource(R.drawable.char_scientist);
        }
    }

    private void initThreat() {
        GameManager gm = GameManager.getInstance();
        
        int dayBonusHp = (gm.currentDay / 4) * 2;
        int hp = 30 + (gm.totalMissions * 2) + dayBonusHp;

        int atk;
        if (gm.currentDay < 10) {
            atk = 6 + random.nextInt(10); // 6 to 15 (nextInt(10) gives 0-9)
        } else {
            atk = 10 + random.nextInt(9); // 10 to 18 (nextInt(9) gives 0-8)
        }

        int res = 1 + (gm.totalMissions / 10);
        
        String name = "Asteroid Storm";
        if (gm.currentDay == 10) {
            name = "VOID REAPER (BOSS)";
            threat = new VoidReaperBoss(name, hp + 20, atk + 2, res);
        } else if (gm.currentDay == 20) {
            name = "STAR DEVOURER (BOSS)";
            threat = new StarDevourerBoss(name, hp + 30, atk + 3, res);
        } else if (gm.currentDay == 30) {
            name = "THE OMEGA ENTITY (FINAL BOSS)";
            threat = new OmegaEntityBoss(name, hp + 40, atk + 5, res);
        } else {
            threat = new StealthFighter(name, hp, atk, res);
        }
        
        textThreatName.setText("Threat: " + threat.getName());
        updateThreatUI();
    }

    private void updateThreatUI() {
        textThreatHp.setText("HP: " + threat.getHealth() + "/" + threat.getMaxHealth());
        textThreatStats.setText("ATK: " + threat.getAttackPower() + " | RES: " + threat.getResilience());
        progressThreatHp.setMax(threat.getMaxHealth());
        progressThreatHp.setProgress(threat.getHealth());
    }

    private void addLog(String message) {
        logBuilder.append("> ").append(message).append("\n");
        textLog.setText(logBuilder.toString());
    }

    private void startMission() {
        if (!GameManager.getInstance().useEnergy(GameManager.COST_COMBAT)) {
            Toast.makeText(this, "Not enough energy!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        for (CrewMember m : squad) {
            m.skillCooldown = 0;
            m.hasUsedDeathsDoor = false;
            m.heal(m.getMaxHp()); // Ensure full HP at start of mission
            if (m instanceof Soldier) ((Soldier) m).resetEnrage();
        }
        addLog("Mission Started against " + threat.getName());
        
        if (random.nextBoolean()) {
            addLog("Coin Flip: HEADS! Crew moves first.");
            isCrewPhase = true;
            currentMemberIndex = 0;
            nextTurn();
        } else {
            addLog("Coin Flip: TAILS! Threat moves first.");
            isCrewPhase = false;
            triggerThreatTurn();
        }
    }

    private void nextTurn() {
        if (threat.isDefeated()) {
            endMission(true);
            return;
        }
        if (allCrewDead()) {
            endMission(false);
            return;
        }
        if (!isCrewPhase) {
            triggerThreatTurn();
            return;
        }

        // Wrap or find next alive
        if (currentMemberIndex >= squad.size()) {
            currentMemberIndex = 0;
        }

        int startIdx = currentMemberIndex;
        int attempts = 0;
        while (!squad.get(currentMemberIndex).isAlive() && attempts < squad.size()) {
            currentMemberIndex = (currentMemberIndex + 1) % squad.size();
            attempts++;
        }

        CrewMember active = squad.get(currentMemberIndex);
        layoutActions.setVisibility(View.VISIBLE);
        textActiveMember.setText(active.getId() + "'s Turn (HP: " + active.getHp() + ")");
        
        if (active.skillCooldown > 0) active.skillCooldown--;
        
        btnSkill.setEnabled(active.skillCooldown == 0);
        textSkillInfo.setText("Skill: " + active.getSpecialSkillName() + (active.skillCooldown > 0 ? " (CD: " + active.skillCooldown + ")" : ""));
    }

    private void handleAttack() {
        layoutActions.setVisibility(View.GONE);
        CrewMember active = squad.get(currentMemberIndex);
        // Damage is already calculated minus resilience in takeNormalDamage, but handleAttack was doing it twice.
        // Let's pass the raw damage and let the threat handle its own resilience.
        int damage = active.getAttackPower();
        threat.takeNormalDamage(damage);
        
        // Calculate what was actually dealt for the log
        int actualDealt = Math.max(0, damage - threat.getResilience());
        addLog(active.getId() + " attacks! Damage: " + actualDealt);

        updateThreatUI();
        updateAllCrewUI();
        finishCrewMemberTurn();
    }

    private void handleSkill() {
        layoutActions.setVisibility(View.GONE);
        CrewMember active = squad.get(currentMemberIndex);
        if (active instanceof Medic) {
            CrewMember target = findMostWounded();
            ((Medic) active).heal(target);
            active.skillCooldown = 3; // Medic 3 turn CD
            addLog(active.getId() + " uses HEAL on " + target.getId() + ".");
        } else if (active instanceof Soldier) {
            Soldier s = (Soldier) active;
            if (s.canEnrage()) {
                s.enrage(java.util.Collections.singletonList(threat));
                active.skillCooldown = 5; // Soldier 5 turn CD
                addLog(active.getId() + " uses ENRAGE.");
            } else {
                addLog(active.getId() + " failed to ENRAGE!");
            }
        } else if (active instanceof Pilot) {
            ((Pilot) active).extraAttack(threat);
            active.skillCooldown = 2; // Pilot 2 turn CD
            addLog(active.getId() + " uses EXTRA ATTACK.");
        } else if (active instanceof Scientist) {
            CrewMember target = findMostWounded();
            target.tempAtkBonus += 2 + active.getLevel();
            active.skillCooldown = 2; // Scientist 2 turn CD
            addLog(active.getId() + " boosts " + target.getId() + "'s Attack!");
        } else if (active instanceof Engineer) {
            CrewMember target = findMostWounded();
            target.tempResilienceBonus += 1 + (active.getLevel() / 2);
            active.skillCooldown = 4; // Engineer 4 turn CD
            addLog(active.getId() + " boosts " + target.getId() + "'s Resilience!");
        }
        updateAllCrewUI();
        updateThreatUI();
        finishCrewMemberTurn();
    }

    private void handleItems() {
        List<com.example.oopproject.storage.ConsumableItem> inventory = GameManager.getInstance().getInventory();
        if (inventory.isEmpty()) {
            Toast.makeText(this, "No items!", Toast.LENGTH_SHORT).show();
            return;
        }
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Items");
        String[] names = new String[inventory.size()];
        for (int i = 0; i < inventory.size(); i++) names[i] = inventory.get(i).getName();
        builder.setItems(names, (d, w) -> {
            useItem(inventory.get(w));
        });
        builder.show();
    }

    private void useItem(com.example.oopproject.storage.ConsumableItem item) {
        CrewMember active = squad.get(currentMemberIndex);
        switch (item.getType()) {
            case ATK_POTION:
                active.tempAtkBonus += 2;
                addLog(active.getId() + " drinks Atk Potion! (+2 ATK for this battle)");
                break;
            case HP_POTION:
                active.heal(5);
                addLog(active.getId() + " drinks HP Potion! (+5 HP)");
                break;
            case DEF_POTION:
                active.tempResilienceBonus += 2;
                addLog(active.getId() + " drinks Def Potion! (+2 DEF for this battle)");
                break;
        }
        GameManager.getInstance().removeItem(item);
        updateAllCrewUI();
        finishCrewMemberTurn();
    }

    private void finishCrewMemberTurn() {
        currentMemberIndex++;
        isCrewPhase = false;
        new Handler().postDelayed(this::nextTurn, 800);
    }

    private void triggerThreatTurn() {
        if (threat.isDefeated()) {
            endMission(true);
            return;
        }
        List<CrewMember> alive = new ArrayList<>();
        for (CrewMember m : squad) if (m.isAlive()) alive.add(m);
        if (alive.isEmpty()) {
            endMission(false);
            return;
        }
        
        addLog("--- Threat Turn ---");
        CrewMember target = alive.get(random.nextInt(alive.size()));
        
        // 70% Hit Chance
        if (random.nextInt(100) < 70) {
            threat.retaliate(target);
            addLog(threat.getName() + " attacks " + target.getId() + "!");
        } else {
            addLog(threat.getName() + " attacks " + target.getId() + " but MISSES!");
        }

        updateAllCrewUI();
        
        isCrewPhase = true;
        new Handler().postDelayed(this::nextTurn, 1000);
    }

    private CrewMember findMostWounded() {
        CrewMember target = null;
        for (CrewMember m : squad) {
            if (m.isAlive()) {
                if (target == null || m.getHp() < target.getHp()) {
                    target = m;
                }
            }
        }
        return target != null ? target : squad.get(0);
    }

    private void updateAllCrewUI() {
        if (squad.size() >= 1) updateCrewUI(squad.get(0), progressCrew1, imgCrew1);
        if (squad.size() >= 2) updateCrewUI(squad.get(1), progressCrew2, imgCrew2);
        if (squad.size() >= 3) updateCrewUI(squad.get(2), progressCrew3, imgCrew3);
        if (squad.size() >= 4) updateCrewUI(squad.get(3), progressCrew4, imgCrew4);
        if (squad.size() >= 5) updateCrewUI(squad.get(4), progressCrew5, imgCrew5);
    }

    private boolean allCrewDead() {
        for (CrewMember m : squad) if (m.isAlive()) return false;
        return true;
    }

    private void endMission(boolean success) {
        layoutActions.setVisibility(View.GONE);
        GameManager gm = GameManager.getInstance();
        gm.totalMissions++;

        // Clear temporary mission buffs and bonuses
        for (CrewMember m : squad) {
            m.resetCombatBuffs();
        }

        if (success) {
            addLog("VICTORY!");
            gm.totalWins++;
            gm.addCredits(2);
            addLog("Earned 2 Credits!");
            com.example.oopproject.storage.ConsumableItem item = gm.rollForItemDrop();
            if (item != null) gm.addItem(item);
            for (CrewMember m : squad) if (m.isAlive()) m.gainExp(6);
        } else {
            addLog("FAILED.");
            for (CrewMember m : squad) { m.loseLevel(); if (!m.isAlive()) { m.setHp(1); m.enterMedbay(); } }
        }

        // If it's a boss day, move to the next day immediately after the fight
        if (gm.isBossDay()) {
            addLog("Boss encounter concluded. Moving to next day...");
            gm.nextDay();
        }

        new Handler().postDelayed(this::finish, 2000);
    }
}