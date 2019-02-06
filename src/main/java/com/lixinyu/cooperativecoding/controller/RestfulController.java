package com.lixinyu.cooperativecoding.controller;

import com.lixinyu.cooperativecoding.data.CodeRepository;
import com.lixinyu.cooperativecoding.data.ProjectRepository;
import com.lixinyu.cooperativecoding.data.TeamRepository;
import com.lixinyu.cooperativecoding.data.UserRepository;
import com.lixinyu.cooperativecoding.model.entity.Code;
import com.lixinyu.cooperativecoding.model.entity.Project;
import com.lixinyu.cooperativecoding.model.entity.Team;
import com.lixinyu.cooperativecoding.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Set;

@CrossOrigin(origins = "http://127.0.0.1")
@EnableGlobalMethodSecurity(prePostEnabled = true)
@PreAuthorize("hasAnyRole('User','Administrator')")
@RestController
public class RestfulController {
    private final UserRepository userRepository;
    private final CodeRepository codeRepository;
    private final ProjectRepository projectRepository;
    private final TeamRepository teamRepository;


    @Autowired
    public RestfulController(UserRepository userRepository, CodeRepository codeRepository, ProjectRepository projectRepository, TeamRepository teamRepository) {
        this.userRepository = userRepository;
        this.codeRepository = codeRepository;
        this.projectRepository = projectRepository;
        this.teamRepository = teamRepository;
    }


    @RequestMapping(value = "/code/all")
    public @ResponseBody
    ArrayList<Code> jread(Authentication authentication) {
        return codeRepository.findAllByProject(userRepository.findByUsername(authentication.getName()).get().getProject());
//        return userRepository.findByUsername(authentication.getName()).get().getProject().getCodes();
    }

    @RequestMapping(value = "/code/{code_id}")
    public @ResponseBody
    Code code(@PathVariable int code_id) {
        return codeRepository.findOne(code_id);
    }

    @RequestMapping(value = "/project")
    public @ResponseBody
    Project project(Authentication authentication) {
        Project project = userRepository.findByUsername(authentication.getName()).get().getProject();
//        int online = 0;
//        for (User user : userRepository.findAll()) {
//            if (System.currentTimeMillis() - user.getLastHeartbeat() < 15000) {
//                online++;
//            }
//        }
//        project.setOnline(online);
//
//        if (project.getMaintainer() != null) {
//            if (System.currentTimeMillis() - project.getMaintainer().getLastHeartbeat() > 10000) {
//                project.setMaintainer(null);
//            }
//        }
//
//        projectRepository.save(project);
        return project;
    }

    @RequestMapping(value = "/projects")
    public @ResponseBody
    ArrayList<Project> allProjects(@RequestParam("team_id") int team_id) {
        ArrayList<Project> projects = new ArrayList<>();
        System.out.println("team_id = " + team_id);
        if (team_id > 0) {
            projects = projectRepository.findAllByTeam(teamRepository.findOne(team_id));
        } else {
            projectRepository.findAll().forEach(projects::add);
        }

        return projects;
    }

    @GetMapping(value = "/users")
    public @ResponseBody
    ArrayList<User> allUsers() {
        ArrayList<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    @RequestMapping(value = "users/online")
    public @ResponseBody
    ArrayList<User> onlineUser(Authentication authentication) {
        ArrayList<User> users = new ArrayList<>();

        //User user = userRepository.findByUsername(authentication.getName()).get();
        for (User user : userRepository.findAll()) {
            if (System.currentTimeMillis() - user.getLastHeartbeat() < 15000) {
                if (user.getProject() == userRepository.findByUsername(authentication.getName()).get().getProject()) {
                    users.add(user);
                }
            }
        }
        return users;
    }

    @PostMapping(value = "/users")
    public @ResponseBody
    ArrayList<User> allUsers(@RequestParam(value = "team_id") int team_id) {
        ArrayList<User> users = new ArrayList<>();
        if (team_id > 0) {
            users = userRepository.findAllByTeam(teamRepository.findOne(team_id));
        } else {
            userRepository.findAll().forEach(users::add);
        }
        return users;
    }

    @RequestMapping(value = "/codes")
    public @ResponseBody
    ArrayList<Code> allCodes(@RequestParam("project_id") int project_id) {
        ArrayList<Code> codes = new ArrayList<>();
        if (project_id > 0) {
            codes = codeRepository.findAllByProject(projectRepository.findOne(project_id));
        } else {
            codeRepository.findAll().forEach(codes::add);
        }
        return codes;
    }


    @RequestMapping(value = "/project/all")
    public @ResponseBody
    ArrayList<Project> allProjects(Authentication authentication) {

        Team team = userRepository.findByUsername(authentication.getName()).get().getTeam();
        return projectRepository.findAllByTeam(team);
    }

    @RequestMapping(value = "/code/new")
    public @ResponseBody
    Code codeNew(@RequestParam("code_title") String code_title, Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName()).get();
        Project project = user.getProject();
        boolean executable = project.getCodes().size() == 0;

        String mode = code_title.split("\\.")[1];
        Code code = new Code(code_title, "", mode, project, executable);
        codeRepository.save(code);
        return code;
    }

    @RequestMapping(value = "/code/add")
    public @ResponseBody
    Code codeAdd(@RequestParam("code_title") String code_title, @RequestParam("project_id") String code_project) {
        Project project = projectRepository.findOne(Integer.parseInt(code_project));

        Set<Code> codes = project.getCodes();
        boolean executable = true;
        for (Code c : codes) {
            if (c.isExecutable()) {
                executable = false;
            }
        }

        String mode = code_title.split("\\.")[1];
//        Code code = new Code(code_title,"",mode,user.getProject(),user.getProject().getCodes().isEmpty());
        Code code = new Code(code_title, "", mode, project, executable);
        codeRepository.save(code);
        return code;
    }

    @GetMapping(value = "/user")
    public @ResponseBody
    User getUserInfo(Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName()).get();
        user.setLastHeartbeat(System.currentTimeMillis());
        userRepository.save(user);
        return user;
    }

    //Set project
    @RequestMapping(value = "/user/project")
    public @ResponseBody
    User setUserInfo(@RequestParam("project_id") int project_id, Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName()).get();
        Project project = projectRepository.findOne(project_id);

        user.setProject(project);
        userRepository.save(user);
        projectRepository.save(project);
        return user;
    }

    @RequestMapping(value = "/project/current")
    public @ResponseBody
    long cproject(Authentication authentication) {
        return userRepository.findByUsername(authentication.getName()).get().getProject().getProject_id();
    }

    @RequestMapping(value = "/teams")
    public @ResponseBody
    ArrayList<Team> allTeams() {
        ArrayList<Team> teams = new ArrayList<>();
        teamRepository.findAll().forEach(teams::add);
        return teams;
    }

    @PostMapping(value = "/project/take")
    public @ResponseBody
    Project takeProject(Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName()).get();
        user.setLastHeartbeat(System.currentTimeMillis());
        userRepository.save(user);
        Project project = user.getProject();
        //有人管理
        if (project.getMaintainer() != null) {
            if (System.currentTimeMillis() - project.getMaintainer().getLastHeartbeat() > 10000 | project.getMaintainer() == user) {
                project.setMaintainer(null);
                projectRepository.save(project);
            }
        } else {
            //无人管理 直接接管
            project.setMaintainer(user);
            projectRepository.save(project);
        }
        return project;
    }

    @PostMapping(value = "/code/update")
    public @ResponseBody
    Code codeupdate(@RequestParam("code_id") String code_id, @RequestParam("code_title") String code_title, @RequestParam("executable") boolean executable, @RequestParam("mode") String mode) {
        Code c = codeRepository.findOne(Integer.parseInt(code_id));
        c.setCode_title(code_title);
        c.setExecutable(executable);
        c.setMode(mode);
        codeRepository.save(c);
        return c;
    }

    @PostMapping(value = "/team/update")
    public @ResponseBody
    Team teamupdate(@RequestParam("team_id") String team_id, @RequestParam("team_name") String team_name) {
        Team team = teamRepository.findOne(Integer.parseInt(team_id));
        team.setTeam_name(team_name);

        teamRepository.save(team);
        return team;
    }

    @PostMapping(value = "/project/update")
    public @ResponseBody
    Project projectupdate(@RequestParam("project_id") String project_id, @RequestParam("project_name") String project_name) {
        Project project = projectRepository.findOne(Integer.parseInt(project_id));

        project.setProject_name(project_name);

        projectRepository.save(project);


        return project;
    }

    @PostMapping(value = "/user/update")
    public @ResponseBody
    User userupdate(@RequestParam("username") String username, @RequestParam("name") String name, @RequestParam("team_id") String team_id, @RequestParam("role") String role) {
        User user = userRepository.findByUsername(username).get();
        System.out.println(name);
        user.setName(name);
        user.setTeam(teamRepository.findOne(Integer.parseInt(team_id)));
//
        user.setRole(role);
//
        userRepository.save(user);
        return user;
    }

    @PostMapping(value = "/code/del")
    public @ResponseBody
    boolean codedelete(@RequestParam("code_id") int code_id) {
        Code c = codeRepository.findOne(code_id);
        codeRepository.delete(c);
        return codeRepository.findOne(code_id) == null;
    }

    @PostMapping(value = "/user/del")
    public @ResponseBody
    boolean userdelete(@RequestParam("username") String username) {
        boolean result;
        User user = userRepository.findByUsername(username).get();
        userRepository.delete(user);
        result = userRepository.findByUsername(username) != null;
        return result;
    }

    @PostMapping(value = "/project/del")
    public @ResponseBody
    boolean projdelete(@RequestParam("project_id") String project_id) {
        Project project = projectRepository.findOne(Integer.parseInt(project_id));
        projectRepository.delete(project);


        return projectRepository.findOne(Integer.parseInt(project_id)) == null;
    }

    @PostMapping(value = "/team/del")
    public @ResponseBody
    boolean teamdelete(@RequestParam("team_id") String team_id) {

        Team team = teamRepository.findOne(Integer.parseInt(team_id));
        teamRepository.delete(team);


        return teamRepository.findOne(Integer.parseInt(team_id)) == null;
    }

    @GetMapping(value = "/online/project/{project_id}")
    public int projectonlinecont(@PathVariable int project_id) {
        int count = 0;
        Project project  = projectRepository.findOne(project_id);
        for (User user : userRepository.findAll()) {
            if(user.getProject()==project && System.currentTimeMillis() - user.getLastHeartbeat() < 10000){
                count++;
            }
        }
        return count;
    }

    @GetMapping(value = "/online/{team_id}")
    public int teamonlinecont(@PathVariable int team_id) {
        int count = 0;

        Team team = teamRepository.findOne(team_id);
        for (User user : team.getUsers()) {
            if (System.currentTimeMillis() - user.getLastHeartbeat() < 10000) {
                count++;
            }
        }
        return count;
    }

    @GetMapping(value = "/team/total/{team_id}")
    public int teamonlineco(@PathVariable int team_id) {

        Team team = teamRepository.findOne(team_id);

        return team.getUsers().size();
    }

    @PostMapping(value = "/user/add")
    @ResponseBody
    public User addUser(@RequestParam("username") String username, @RequestParam("team_id") String team_id, @RequestParam("name") String name, @RequestParam("role") String role) {
        User user = new User(username, teamRepository.findOne(Integer.parseInt(team_id)), name, role);
        userRepository.save(user);
        return user;
    }

    @PostMapping(value = "/team/add")
    @ResponseBody
    public Team addTeam(@RequestParam("team_name") String team_name) {
        Team team = new Team(team_name);
        teamRepository.save(team);
        return team;
    }

    @PostMapping(value = "/project/add")
    @ResponseBody
    public Project addProject(@RequestParam("team_id") String team_id, @RequestParam("project_name") String project_name) {
        Project project = new Project(project_name, teamRepository.findOne(Integer.parseInt(team_id)));
        projectRepository.save(project);
        return project;
    }
}