package com.lixinyu.cooperativecoding.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.boot.autoconfigure.web.ResourceProperties;

import javax.persistence.*;
import java.util.Set;

@Entity
@JsonIgnoreProperties(value = {"project"})
public class User {

    @Id
    private String username;
    private String name;
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;


    private String password;


    private String avatar;
    private String role;


    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    private long lastHeartbeat=0;
    private boolean active;
    public User(){
    }

    public User(User user){
        this.username = user.getUsername();
        this.team = user.getTeam();
        this.name = user.getName();
        this.password = user.getPassword();
        this.role = user.getRole();
        this.active = user.getActive();
        this.avatar = user.getAvatar();
    }

    public User(String username, Team team, String name, String role) {

        this.username = username;
        this.team = team;
        this.name = name;
        this.password = username;
        this.role = role;
        this.avatar = "img/mine.jpg";
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public long getLastHeartbeat() {
        return lastHeartbeat;
    }

    public void setLastHeartbeat(long lastHeartbeat) {
        this.lastHeartbeat = lastHeartbeat;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}