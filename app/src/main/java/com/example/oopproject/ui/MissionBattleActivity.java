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
    private Button btnAttack, btnDefend, btnSkill;

    private ImageView imgThreat, imgCrew1, imgCrew2, imgCrew3, imgCrew4, imgCrew5, imgBackground;
    private ProgressBar progressCrew1, progressCrew2, progressCrew3, progressCrew4, progressCrew5;
    private TextView textCrew1Name, textCrew2Name, textCrew3Name, textCrew4Name, textCrew5Name;
    private View containerCrew3, containerCrew4, containerCrew5;
    private MediaPlayer mediaPlayer;

    private List<CrewMember> squad = new ArrayList<>();
    private Threat threat;
    private int currentMemberIndex = 0;
    private StringBuilder logBuilder = new StringBuilder();
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_battle);

        initViews();
        loadSquad();
        initThreat();
        initMusic();
        
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
        btnDefend = findViewById(R.id.btn_defend);
        btnSkill = findViewById(R.id.btn_skill);

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
        btnDefend.setOnClickListener(v -> handleDefend());
        btnSkill.setOnClickListener(v -> handleSkill());
    }

    private void initMusic() {
        // Music initialization logic...
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    private void initThreat() {
        GameManager gm = GameManager.getInstance();
        
        int dayBonusHp = (gm.currentDay / 5) * 3;
        int dayBonusAtk = (int) ((gm.currentDay / 5) * 0.6);
        
        int hp = 20 + (gm.totalMissions * 3) + dayBonusHp;
        int atk = 4 + (int) (gm.totalMissions * 0.3) + dayBonusAtk;
        int res = 1 + (gm.totalMissions / 7);
        
        String name = "Asteroid Storm";
        if (gm.currentDay == 10) {
            hp = (int) (hp * 1.3);
            atk = (int) (atk * 1.3);
            res = (int) (res * 1.2);
            name = "NEBULA STALKER (BOSS)";
            threat = new VoidReaperBoss(name, hp, atk, res);
        } else if (gm.currentDay == 20) {
            hp = (int) (hp * 1.6);
            atk = (int) (atk * 1.6);
            res = (int) (res * 1.3);
            name = "VOID REAPER (BOSS)";
            threat = new VoidReaperBoss(name, hp, atk, res);
        } else if (gm.currentDay == 30) {
            hp = (int) (hp * 1.9);
            atk = (int) (atk * 1.9);
            res = (int) (res * 1.6);
            name = "STAR DEVOURER (BOSS)";
            threat = new StarDevourerBoss(name, hp, atk, res);
        } else if (gm.currentDay == 40) {
            hp = (int) (hp * 2.2);
            atk = (int) (atk * 2.2);
            res = (int) (res * 1.9);
            name = "THE OMEGA ENTITY (FINAL BOSS)";
            threat = new OmegaEntityBoss(name, hp, atk, res);
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
        
        if (GameManager.getInstance().isBossDay()) {
            imgThreat.setImageResource(R.drawable.threat_boss);
        } else {
            imgThreat.setImageResource(android.R.drawable.ic_delete); // Default threat icon
        }
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
            m.hasUsedDeathsDoor = false; // Reset Death's Door for the mission
            if (m instanceof Soldier) {
                ((Soldier) m).resetEnrage();
            }
        }
        addLog("Mission Started against " + threat.getName());
        nextTurn();
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

        CrewMember active = squad.get(currentMemberIndex);
        if (!active.isAlive()) {
            currentMemberIndex = (currentMemberIndex + 1) % squad.size();
            nextTurn();
            return;
        }

        layoutActions.setVisibility(View.VISIBLE);
        textActiveMember.setText(active.getId() + "'s Turn (HP: " + active.getHp() + ")");

        if (active.skillCooldown > 0) {
            active.skillCooldown--;
        }

        String skillText = "Skill: " + active.getSpecialSkillName() + " - " + active.getSpecialSkillDescription();
        if (active.skillCooldown > 0) {
            btnSkill.setEnabled(false);
            skillText += " (Cooldown: " + active.skillCooldown + ")";
        } else {
            btnSkill.setEnabled(true);
        }
        textSkillInfo.setText(skillText);
    }

    private void handleAttack() {
        layoutActions.setVisibility(View.GONE);
        CrewMember active = squad.get(currentMemberIndex);
        
        if (active instanceof Soldier) {
            ((Soldier) active).enrage();
        }

        double randomness = Math.random() * 3;
        int specBonus = 0;
        if (threat.getType().equals("repair") && active instanceof Engineer) specBonus = 2;
        if (threat.getType().equals("scientific") && active instanceof Scientist) specBonus = 2;

        int damage = (int)((active.getAttackPower() + active.getExp() + randomness + specBonus) - threat.getResilience());
        damage = Math.max(0, damage);
        
        threat.takeNormalDamage(damage);
        addLog(active.getId() + " attacks! Damage: " + damage);

        if (active instanceof Pilot) {
            ((Pilot) active).extraAttack(threat);
            addLog(active.getId() + " triggers Extra Attack!");
        }
        
        updateThreatUI();
        
        if (threat.isDefeated()) {
            new Handler().postDelayed(() -> endMission(true), 1000);
        } else {
            new Handler().postDelayed(() -> threatRetaliate(active), 800);
        }
    }

    private void handleDefend() {
        layoutActions.setVisibility(View.GONE);
        CrewMember active = squad.get(currentMemberIndex);
        active.isDefending = true;
        
        // Final Polish: Defending restores a small amount of HP
        int tacticalHeal = 2 + (active.getLevel() / 2);
        active.heal(tacticalHeal);
        
        addLog(active.getId() + " defends and recovers " + tacticalHeal + " HP.");
        updateAllCrewUI();

        new Handler().postDelayed(() -> threatRetaliate(active), 800);
    }

    private void handleSkill() {
        layoutActions.setVisibility(View.GONE);
        CrewMember active = squad.get(currentMemberIndex);
        
        if (active instanceof Medic) {
            CrewMember target = findMostWounded();
            if (target != null) {
                ((Medic) active).heal(target);
                addLog(active.getId() + " heals " + target.getId());
                updateAllCrewUI();
            }
        } else if (active instanceof Scientist) {
            CrewMember target = findMostWounded();
            if (target != null) {
                ((Scientist) active).boostAttack(target);
                addLog(active.getId() + " boosts attack of " + target.getId());
            }
        } else if (active instanceof Engineer) {
            CrewMember target = findMostWounded();
            if (target != null) {
                ((Engineer) active).boostDefense(target);
                addLog(active.getId() + " boosts defense of " + target.getId());
                updateAllCrewUI();
            }
        } else {
            int damage = (int)(((active.getAttackPower() + active.getExp()) * 1.5) - threat.getResilience());
            damage = Math.max(0, damage);
            threat.takeNormalDamage(damage);
            addLog(active.getId() + " uses Special Ability! Damage: " + damage);
        }
        
        active.skillCooldown = 2;
        updateThreatUI();
        
        if (threat.isDefeated()) {
            new Handler().postDelayed(() -> endMission(true), 1000);
        } else {
            new Handler().postDelayed(() -> threatRetaliate(active), 800);
        }
    }

    private CrewMember findMostWounded() {
        CrewMember target = null;
        int minHp = Integer.MAX_VALUE;
        for (CrewMember m : squad) {
            if (m.isAlive() && m.getHp() < m.getMaxHp() && m.getHp() < minHp) {
                minHp = m.getHp();
                target = m;
            }
        }
        if (target == null) {
            for (CrewMember m : squad) {
                if (m.isAlive()) return m;
            }
        }
        return target;
    }

    private void updateAllCrewUI() {
        if (squad.size() >= 1) updateCrewUI(squad.get(0), progressCrew1, imgCrew1);
        if (squad.size() >= 2) updateCrewUI(squad.get(1), progressCrew2, imgCrew2);
        if (squad.size() >= 3) updateCrewUI(squad.get(2), progressCrew3, imgCrew3);
        if (squad.size() >= 4) updateCrewUI(squad.get(3), progressCrew4, imgCrew4);
        if (squad.size() >= 5) updateCrewUI(squad.get(4), progressCrew5, imgCrew5);
    }

    private void threatRetaliate(CrewMember active) {
        if (threat.isDefeated()) return;
        threat.retaliate(active);
        addLog(threat.getName() + " retaliates!");
        updateCrewUI(active, getProgressForMember(active), getImageForMember(active));

        if (!active.isAlive()) {
            addLog(active.getId() + " has fallen!");
        }

        currentMemberIndex = (currentMemberIndex + 1) % squad.size();
        new Handler().postDelayed(this::nextTurn, 1000);
    }

    private ProgressBar getProgressForMember(CrewMember member) {
        int index = squad.indexOf(member);
        if (index == 0) return progressCrew1;
        if (index == 1) return progressCrew2;
        if (index == 2) return progressCrew3;
        if (index == 3) return progressCrew4;
        return progressCrew5;
    }

    private ImageView getImageForMember(CrewMember member) {
        int index = squad.indexOf(member);
        if (index == 0) return imgCrew1;
        if (index == 1) return imgCrew2;
        if (index == 2) return imgCrew3;
        if (index == 3) return imgCrew4;
        return imgCrew5;
    }

    private boolean allCrewDead() {
        for (CrewMember m : squad) {
            if (m.isAlive()) return false;
        }
        return true;
    }

    private void endMission(boolean success) {
        layoutActions.setVisibility(View.GONE);
        GameManager gm = GameManager.getInstance();
        gm.totalMissions++;
        
        if (success) {
            addLog("VICTORY! Threat neutralized.");
            gm.totalWins++;
            // Reward energy for victory
            gm.energy = Math.min(GameManager.MAX_ENERGY, gm.energy + 2);
            
            for (CrewMember m : squad) {
                if (m.isAlive()) {
                    m.victoriesCount++;
                    m.gainExp(6); // Increased XP from 3 to 6
                }
                m.missionsCount++;
            }

            if (gm.currentDay == 40) {
                addLog("THE OMEGA ENTITY IS DESTROYED! THE COLONY IS SAVED!");
                new Handler().postDelayed(() -> {
                    Toast.makeText(this, "ULTIMATE VICTORY! You saved the colony!", Toast.LENGTH_LONG).show();
                    finish();
                }, 3000);
                return;
            }
            
            if (gm.isBossDay()) {
                addLog("BOSS DEFEATED! Preparing for next day...");
                gm.nextDay();
            }

        } else {
            addLog("MISSION FAILED.");
            if (gm.isBossDay()) {
                addLog("THE COLONY HAS FALLEN...");
                new Handler().postDelayed(() -> {
                    Toast.makeText(this, "GAME OVER - The Boss destroyed the colony", Toast.LENGTH_LONG).show();
                    GameManager.resetGame(this);
                    Intent intent = new Intent(this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }, 3000);
                return;
            }
            for (CrewMember m : squad) {
                m.missionsCount++;
                if (!m.isAlive()) {
                    m.setHp(1); // Revive at 1 health after combat
                    m.enterMedbay();
                }
            }
        }
        
        new Handler().postDelayed(this::finish, 2000);
    }
}