package com.lixinyu.cooperativecoding.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Set;

@Entity
@JsonIgnoreProperties(value = {"users","projects"})
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int team_id;

    private String team_name;

    @OneToMany(mappedBy = "team", cascade = CascadeType.MERGE)
    private Set<User> users;

    @OneToMany(mappedBy = "team", cascade = CascadeType.MERGE)
    private Set<Project> projects;

    public Team() {
    }

    public Team(String team_name) {
        this.team_name = team_name;
    }


    public long getTeam_id() {
        return team_id;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }
}
