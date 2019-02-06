package com.lixinyu.cooperativecoding.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Set;

@Entity
@JsonIgnoreProperties(value = {"codes"})

public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int project_id;

    //项目名称
    private String project_name;

    @OneToMany(mappedBy = "project", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<Code> codes;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "team_id")
    private Team team;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "maintainer")
    private User maintainer;

    //在线人数
    private int online = 0;

    public Project() {
    }

    public long getProject_id() {
        return project_id;
    }

    public Project(String project_name, Team team) {
//        this.project_id = project_id;
        this.project_name = project_name;
        this.team = team;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public Set<Code> getCodes() {
        return codes;
    }

    public void setCodes(Set<Code> codes) {
        this.codes = codes;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public User getMaintainer() {
        return maintainer;
    }

    public void setMaintainer(User maintainer) {
        this.maintainer = maintainer;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }
}