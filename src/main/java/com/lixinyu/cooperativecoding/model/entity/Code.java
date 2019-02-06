package com.lixinyu.cooperativecoding.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties(value = {"project"})
public class Code {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int code_id;
    private String code_title;

    //@Lob : 设置MYSQL中content的字段类型为longtext(存储更多字符串)
    @Lob
    private String content;
	private String mode;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "project")
	private Project project;

    private boolean executable;

    public Code() {

    }

    public Code(String code_title, String content, String mode, Project project, boolean executable) {
        //this.code_id = code_id;
        this.code_title = code_title;
        this.content = content;
        this.mode = mode;
        this.project = project;
        this.executable = executable;
    }

    public String getCode_title() {
        return code_title;
    }

    public void setCode_title(String code_title) {
        this.code_title = code_title;
    }

    public int getCode_id() {
		return code_id;
	}

    public void setCode_id(int code_id) {
		this.code_id = code_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public boolean isExecutable() {
        return executable;
    }

    public void setExecutable(boolean executable) {
        this.executable = executable;
    }


}